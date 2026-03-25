package com.michaelrodriguez.mrp.bom_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.michaelrodriguez.mrp.bom_service.DTO.request.InsumoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.request.ProductoRequestDTO;
import com.michaelrodriguez.mrp.bom_service.DTO.response.ProductoResponseDTO;
import com.michaelrodriguez.mrp.bom_service.entity.InsumoEntity;
import com.michaelrodriguez.mrp.bom_service.entity.Producto;
import com.michaelrodriguez.mrp.bom_service.entity.ProductoInsumoEntity;
import com.michaelrodriguez.mrp.bom_service.entity.UnidadDeMedidaEntity;
import com.michaelrodriguez.mrp.bom_service.exception.ResourceNotFoundException;
import com.michaelrodriguez.mrp.bom_service.exception.DuplicateResourceException;
import com.michaelrodriguez.mrp.bom_service.repository.ProductoRepository;
import com.michaelrodriguez.mrp.bom_service.repository.InsumoRepository;
import com.michaelrodriguez.mrp.bom_service.repository.UnidadDeMedidaRepository;
import com.michaelrodriguez.mrp.bom_service.mapper.ProductoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la gestión de Productos.
 * 
 * Responsabilidades:
 * - Crear nuevos productos validando unicidad
 * - Recuperar productos por ID
 * - Agregar insumos al Bill of Materials de un producto
 * - Manejar transacciones de base de datos
 * 
 * @author Michael Rodríguez
 * @version 1.0
 * @see IProductoService
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;
    private final InsumoRepository insumoRepository;
    private final UnidadDeMedidaRepository unidadDeMedidaRepository;
    private final ProductoMapper productoMapper;

    /**
     * Crea un nuevo producto validando que no exista otro con el mismo nombre.
     * 
     * Proceso:
     * 1. Valida que el producto no exista (por nombre único)
     * 2. Construye la entidad Producto
     * 3. Persiste en base de datos
     * 4. Mapea a DTO de respuesta
     * 
     * @param requestDTO DTO con datos del producto (nombre)
     * @return ProductoResponseDTO con el producto creado
     * @throws DuplicateResourceException si el producto ya existe
     */
    @Override
    public ProductoResponseDTO createProducto(ProductoRequestDTO requestDTO) {
        log.debug("Iniciando creación de producto: {}", requestDTO.getNombreProducto());
        
        if (productoRepository.existsByNombre(requestDTO.getNombreProducto())) {
            log.warn("Intento de crear producto duplicado: {}", requestDTO.getNombreProducto());
            throw new DuplicateResourceException(
                "El producto con nombre '" + requestDTO.getNombreProducto() + "' ya existe"
            );
        }

        Producto producto = Producto.builder()
                .nombre(requestDTO.getNombreProducto())
                .build();

        Producto savedProducto = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", savedProducto.getId());
        
        return productoMapper.mapToResponseDTO(savedProducto);
    }

    /**
     * Obtiene la lista completa de todos los productos registrados en el sistema.
     * 
     * @return lista de ProductoResponseDTO con todos los productos y sus insumos
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getAllProductos() {
        log.debug("Obteniendo lista completa de productos");
        
        List<Producto> productos = productoRepository.findAll();
        log.info("Se encontraron {} productos en el sistema", productos.size());
        
        // Mapea todos los productos a DTOs
        return productos.stream()
                .map(productoMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por su ID incluyendo toda su información de BOM.
     * 
     * @param productId el ID único del producto
     * @return ProductoResponseDTO con datos completos del producto
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO getProducto(Long productId) {
        log.debug("Buscando producto con ID: {}", productId);
        
        Producto producto = productoRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado con ID: {}", productId);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
                });
        
        log.debug("Producto encontrado: {}", producto.getNombre());
        return productoMapper.mapToResponseDTO(producto);
    }

    /**
     * Agrega un insumo al Bill of Materials de un producto.
     * 
     * Proceso:
     * 1. Valida que el producto existe
     * 2. Valida que la unidad de medida existe
     * 3. Obtiene o crea el insumo automáticamente
     * 4. Crea la relación producto-insumo
     * 5. Persiste los cambios
     * 
     * @param productId el ID del producto
     * @param requestDTO datos del insumo (nombre, cantidad, unidad de medida)
     * @return ProductoResponseDTO con el producto actualizado
     * @throws ResourceNotFoundException si producto o unidad no existen
     */
    @Override
    public ProductoResponseDTO addInsumoToProducto(Long productId, InsumoRequestDTO requestDTO) {
        log.debug("Agregando insumo {} al producto con ID: {}", requestDTO.getInsumo(), productId);
        
        // Obtener producto existente
        Producto producto = productoRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Producto no encontrado con ID: {}", productId);
                    return new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
                });

        // Validar unidad de medida
        UnidadDeMedidaEntity unidad = unidadDeMedidaRepository.findById(requestDTO.getUnidadId())
                .orElseThrow(() -> {
                    log.warn("Unidad de medida no encontrada con ID: {}", requestDTO.getUnidadId());
                    return new ResourceNotFoundException(
                        "Unidad de medida no encontrada con ID: " + requestDTO.getUnidadId()
                    );
                });

        // Obtener o crear insumo
        InsumoEntity insumo = insumoRepository.findByNombre(requestDTO.getInsumo())
                .orElseGet(() -> {
                    log.debug("Insumo no existe, creando nuevo: {}", requestDTO.getInsumo());
                    return insumoRepository.save(
                        InsumoEntity.builder()
                                .nombre(requestDTO.getInsumo())
                                .unidadDeMedida(unidad)
                                .build()
                    );
                });

        // Crear relación producto-insumo
        ProductoInsumoEntity productoInsumo = ProductoInsumoEntity.builder()
                .producto(producto)
                .insumo(insumo)
                .cantidad(requestDTO.getCantidad())
                .build();

        producto.addInsumo(productoInsumo);
        
        log.info("Insumo agregado al producto ID: {} - Insumo: {}, Cantidad: {}", 
            productId, requestDTO.getInsumo(), requestDTO.getCantidad());
        
        return productoMapper.mapToResponseDTO(producto);
    }
}
