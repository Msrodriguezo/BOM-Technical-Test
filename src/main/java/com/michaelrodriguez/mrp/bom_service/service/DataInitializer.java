package com.michaelrodriguez.mrp.bom_service.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.michaelrodriguez.mrp.bom_service.entity.UnidadDeMedidaEntity;
import com.michaelrodriguez.mrp.bom_service.repository.UnidadDeMedidaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UnidadDeMedidaRepository unidadDeMedidaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos
        if (unidadDeMedidaRepository.count() > 0) {
            log.info("Las unidades de medida ya han sido inicializadas");
            return;
        }

        // Crear unidades de medida predefinidas
        UnidadDeMedidaEntity gramos = UnidadDeMedidaEntity.builder()
                .nombre("GRAMOS")
                .simbolo("g")
                .categoria("Peso")
                .build();

        UnidadDeMedidaEntity kilogramos = UnidadDeMedidaEntity.builder()
                .nombre("KILOGRAMOS")
                .simbolo("kg")
                .categoria("Peso")
                .build();

        UnidadDeMedidaEntity litros = UnidadDeMedidaEntity.builder()
                .nombre("LITROS")
                .simbolo("L")
                .categoria("Volumen")
                .build();

        UnidadDeMedidaEntity mililitros = UnidadDeMedidaEntity.builder()
                .nombre("MILILITROS")
                .simbolo("ml")
                .categoria("Volumen")
                .build();

        UnidadDeMedidaEntity unidades = UnidadDeMedidaEntity.builder()
                .nombre("UNIDADES")
                .simbolo("un")
                .categoria("Cantidad")
                .build();

        UnidadDeMedidaEntity metros = UnidadDeMedidaEntity.builder()
                .nombre("METROS")
                .simbolo("m")
                .categoria("Longitud")
                .build();

        UnidadDeMedidaEntity centimetros = UnidadDeMedidaEntity.builder()
                .nombre("CENTIMETROS")
                .simbolo("cm")
                .categoria("Longitud")
                .build();

        unidadDeMedidaRepository.save(gramos);
        unidadDeMedidaRepository.save(kilogramos);
        unidadDeMedidaRepository.save(litros);
        unidadDeMedidaRepository.save(mililitros);
        unidadDeMedidaRepository.save(unidades);
        unidadDeMedidaRepository.save(metros);
        unidadDeMedidaRepository.save(centimetros);

        log.info("Unidades de medida inicializadas correctamente");
    }
}
