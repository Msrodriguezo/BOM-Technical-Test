# BOM Service - Microservicio MRP

Un microservicio completo en Java/Spring Boot que implementa el módulo BOM (Bill of Materials) de un sistema MRP. Gestiona productos, insumos, unidades de medida y cálculos de materiales necesarios para la producción.

## 📋 Descripción

El sistema opera con los siguientes conceptos:

- **Producto**: Artículo final que se va a producir (ej. Zapato)
- **Insumo**: Recurso necesario para producir un producto (ej. Cuero, Suela)
- **Unidad de Medida**: Unidad en que se mide el insumo (ej. gramos, unidades)
- **BOM (Bill of Materials)**: Lista de insumos necesarios para fabricar 1 unidad de un producto

## 🔧 Requisitos Previos

- **Java 21** o superior
- **Maven 3.6.0** o superior
- **Git** (para clonar el repositorio)
- Un navegador web (para Swagger UI y H2 Console)

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd bom-service
```

### 2. Compilar el proyecto

```bash
mvn clean install
```
Esto descargará todas las dependencias y compilará el código.

## ▶️ Ejecución del Microservicio

### Opción 1: Ejecutar con Maven (Recomendado)

```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar el JAR

```bash
mvn clean package -DskipTests
java -jar target/bom-service-0.0.1-SNAPSHOT.jar
```

### Opción 3: Ejecutar con puerto personalizado

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### ⚙️ Ejecutar en Diferentes Ambientes

El proyecto soporta **3 ambientes** con configuraciones específicas:

- **Desarrollo** (default): H2 en memoria, Swagger habilitado, Logging DEBUG
- **Test**: H2 aislado, Swagger deshabilitado, Logging INFO
- **Producción**: PostgreSQL, Sin Swagger, Logging solo WARN/ERROR

**Para cambiar de ambiente**:
```bash
# Test
mvn test

# Producción
java -jar target/bom-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

📖 **Ver guía completa**: [ENVIRONMENTS.md](ENVIRONMENTS.md)

**La aplicación estará disponible en**: `http://localhost:8080`

## 🧪 Formas de Probar el Microservicio

### Opción A: Swagger UI (Interface Gráfica Interactiva) ⭐ RECOMENDADO

Una vez que la aplicación está ejecutándose:

1. Abre tu navegador en: **http://localhost:8080/swagger-ui.html**
2. Verás todos los endpoints disponibles organizados por categorías
3. Expande cualquier endpoint y haz clic en **"Try it out"**
4. Completa los parámetros y haz clic en **"Execute"**
5. Verás la respuesta en tiempo real

**Ventajas**:
- ✅ Interface visual amigable
- ✅ Documentación completa de cada endpoint
- ✅ Validación automática de datos
- ✅ Ejemplos de request/response

### Opción B: H2 Console (Base de Datos Gráfica)

Para consultar directamente la base de datos:

1. Abre: **http://localhost:8080/h2-console**
2. Aparecerá un formulario de login. Usa:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: (dejar vacío)
3. Haz clic en **"Connect"**
4. Ahora puedes ejecutar consultas SQL:

```sql
-- Ver todos los productos
SELECT * FROM PRODUCTO;

-- Ver todos los insumos
SELECT * FROM INSUMO;

-- Ver unidades de medida
SELECT * FROM UNIDADES_DE_MEDIDA;

-- Ver relación producto-insumo
SELECT * FROM PRODUCTO_INSUMO;
```

### Opción C: cURL en Terminal/Consola

Perfecto para automatización y testing:

```bash
# Crear un nuevo producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombreProducto": "Zapato"}'

# Obtener un producto por ID
curl http://localhost:8080/api/productos/1

# Agregar un insumo a un producto
curl -X POST http://localhost:8080/api/productos/1/insumos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Cuero",
    "cantidad": 2.5,
    "unidadDeMedidaId": 6
  }'

# Calcular insumos para producción
curl -X POST http://localhost:8080/api/production/calculate \
  -H "Content-Type: application/json" \
  -d '{
    "productoId": 1,
    "cantidad": 100
  }'
```

### Opción D: Postman (Cliente HTTP Profesional) 📮

**Postman** es un cliente HTTP profesional perfecto para testing de APIs. Hemos incluido una **colección completa lista para usar**.

#### ⚡ Pasos Rápidos para Usar Postman:

1. **Descargar Postman** desde https://www.postman.com/downloads/
2. **Abrir Postman** y hacer clic en **File → Import**
3. **Seleccionar el archivo**: `postman/BOM-Service.postman_collection.json`
4. **Automáticamente se importarán** 3 carpetas principales:
   - 📦 **Productos**: Crear, obtener, agregar insumos
   - 🏭 **Producción**: Calcular insumos para producción
   - 🔗 **Flujo Completo**: Ejemplo paso a paso (Crear → Agregar insumos → Calcular)

#### 🔧 Configuración de Variables en Postman:

1. Haz clic en el **ícono de engranaje** ⚙️ (esquina superior derecha)
2. Crea un nuevo **Environment** llamado `BOM Service - Local`
3. Agrega la variable:
   ```
   Clave: base_url
   Valor: http://localhost:8080
   Tipo: string
   ```
4. Haz clic en **Save** y selecciona este environment en el dropdown superior

#### 📚 Estructura de Requests en Postman:

**Carpeta: Productos**
- ✅ Crear Producto: `POST /api/productos`
- ✅ Obtener Producto: `GET /api/productos/{id}`
- ✅ Agregar Insumo: `POST /api/productos/{id}/insumos`

**Carpeta: Producción**
- ✅ Calcular Insumos: `POST /api/production/calculate`

**Carpeta: Flujo Completo - Ejemplo**
- 1️⃣ Crear Producto - Zapato
- 2️⃣ Agregar Cuero (2 metros)
- 3️⃣ Agregar Suela (1 unidad)
- 4️⃣ Agregar Cordones (1 unidad)
- 5️⃣ Ver Producto Completo
- 6️⃣ Calcular para 100 unidades

**Para más detalles y ejemplos, lee**: [postman/README.md](postman/README.md)

### Opción E: Insomnia (Alternativa a Postman)

1. Descarga desde https://insomnia.rest/
2. Crea una nueva Request
3. Puedes copiar el cURL de esta documentación o de Postman
4. Configura la variable base URL de la misma manera que en Postman

---

## 📊 Datos Iniciales

Al iniciar la aplicación, se cargan automáticamente las siguientes unidades de medida:

| ID | Nombre | Símbolo |
|---|---|---|
| 1 | Gramos | g |
| 2 | Kilogramos | kg |
| 3 | Litros | L |
| 4 | Mililitros | ml |
| 5 | Unidades | un |
| 6 | Metros | m |
| 7 | Centímetros | cm |

## 📚 Estructura del Proyecto

```
src/
├── main/java/com/michaelrodriguez/mrp/bom_service/
│   ├── controller/              # Controladores REST con Swagger
│   │   ├── IProductoController.java
│   │   ├── ProductoController.java
│   │   ├── IProductionController.java
│   │   └── ProductionController.java
│   ├── service/                 # Lógica de negocio e interfaces
│   │   ├── IProductoService.java
│   │   ├── ProductoServiceImpl.java
│   │   ├── IProductionCalculationService.java
│   │   ├── ProductionCalculationServiceImpl.java
│   │   └── DataInitializer.java
│   ├── mapper/                  # Transformación entity ↔ DTO
│   │   ├── ProductoMapper.java
│   │   ├── ProductoMapperImpl.java
│   │   ├── ProductionCalculationMapper.java
│   │   └── ProductionCalculationMapperImpl.java
│   ├── repository/              # Acceso a datos (JPA)
│   │   ├── ProductoRepository.java
│   │   ├── InsumoRepository.java
│   │   ├── ProductoInsumoRepository.java
│   │   └── UnidadDeMedidaRepository.java
│   ├── entity/                  # Entidades JPA
│   │   ├── Producto.java
│   │   ├── InsumoEntity.java
│   │   ├── ProductoInsumoEntity.java
│   │   └── UnidadDeMedidaEntity.java
│   ├── DTO/                     # Data Transfer Objects
│   │   ├── request/
│   │   │   ├── ProductoRequestDTO.java
│   │   │   ├── InsumoRequestDTO.java
│   │   │   └── ProductionRequestDTO.java
│   │   └── response/
│   │       ├── ProductoResponseDTO.java
│   │       ├── InsumoResponseDTO.java
│   │       └── ProductionCalculationResponseDTO.java
│   ├── exception/               # Manejo de excepciones
│   │   ├── ResourceNotFoundException.java
│   │   ├── DuplicateResourceException.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── ErrorResponseDTO.java
│   └── BomServiceApplication.java
├── resources/
│   └── application.properties   # Configuración de la aplicación
└── test/                        # Tests unitarios e integración
```

## 🔌 Endpoints de la API

### Productos

#### 1️⃣ Crear un Producto
```
POST /api/productos
Content-Type: application/json

{
  "nombreProducto": "Zapato Nike"
}
```

**Respuesta (201 Created)**:
```json
{
  "id": 1,
  "nombre": "Zapato Nike",
  "insumos": []
}
```

#### 2️⃣ Obtener un Producto
```
GET /api/productos/{productId}
```

**Respuesta (200 OK)**:
```json
{
  "id": 1,
  "nombre": "Zapato Nike",
  "insumos": [
    {
      "insumoId": 1,
      "insumo": "Cuero",
      "cantidad": 2.5,
      "unidad": "Metros",
      "simboloUnidad": "m"
    }
  ]
}
```

#### 3️⃣ Agregar Insumo a un Producto
```
POST /api/productos/{productId}/insumos
Content-Type: application/json

{
  "nombre": "Cuero",
  "cantidad": 2.5,
  "unidadDeMedidaId": 6
}
```

**Respuesta (200 OK)**:
```json
{
  "id": 1,
  "nombre": "Zapato Nike",
  "insumos": [
    {
      "insumoId": 1,
      "insumo": "Cuero",
      "cantidad": 2.5,
      "unidad": "Metros",
      "simboloUnidad": "m"
    }
  ]
}
```

### Producción

#### 4️⃣ Calcular Insumos para Producción
```
POST /api/production/calculate
Content-Type: application/json

{
  "productoId": 1,
  "cantidad": 100
}
```

**Respuesta (200 OK)**:
```json
{
  "producto": "Zapato Nike",
  "cantidad": 100,
  "insumos": [
    {
      "insumo": "Cuero",
      "requerido": 250,
      "unidad": "Metros",
      "simboloUnidad": "m"
    }
  ]
}
```

## ⚡ Flujo Completo de Ejemplo

### Paso 1: Crear un Producto
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombreProducto": "Zapato"}'
```
Respuesta: `{"id": 1, "nombre": "Zapato", "insumos": []}`

### Paso 2: Agregar Insumos (Cuero)
```bash
curl -X POST http://localhost:8080/api/productos/1/insumos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Cuero", "cantidad": 2, "unidadDeMedidaId": 6}'
```

### Paso 3: Agregar Insumos (Suela)
```bash
curl -X POST http://localhost:8080/api/productos/1/insumos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Suela", "cantidad": 1, "unidadDeMedidaId": 5}'
```

### Paso 4: Calcular para producción de 100 unidades
```bash
curl -X POST http://localhost:8080/api/production/calculate \
  -H "Content-Type: application/json" \
  -d '{"productoId": 1, "cantidad": 100}'
```

Respuesta:
```json
{
  "producto": "Zapato",
  "cantidad": 100,
  "insumos": [
    {
      "insumo": "Cuero",
      "requerido": 200,
      "unidad": "Metros",
      "simboloUnidad": "m"
    },
    {
      "insumo": "Suela",
      "requerido": 100,
      "unidad": "Unidades",
      "simboloUnidad": "un"
    }
  ]
}
```

## 📋 Códigos de Respuesta HTTP

| Código | Significado | Ejemplo |
|--------|------------|---------|
| 201 | Creado exitosamente | POST de nuevo producto |
| 200 | Solicitud exitosa | GET, PUT |
| 400 | Datos inválidos | Faltan campos requeridos |
| 404 | No encontrado | Producto con ID inexistente |
| 409 | Conflicto | Producto duplicado |
| 500 | Error del servidor | Falla en base de datos |

## 🛠️ Stack Tecnológico

- **Framework**: Spring Boot 4.0.4
- **Lenguaje**: Java 21
- **Base de Datos**: H2 (en memoria)
- **ORM**: Hibernate JPA
- **Validación**: Jakarta Validation
- **Documentación API**: Springdoc OpenAPI (Swagger 3.0)
- **Build Tool**: Maven
- **Utilidades**: Lombok

## 🏗️ Patrones de Arquitectura

- **Repository Pattern**: Acceso a datos abstralizado
- **Service Layer Pattern**: Lógica de negocio separada
- **Mapper Pattern**: Transformación entity ↔ DTO
- **Interface Segregation**: Servicios basados en interfaces
- **Dependency Injection**: Inyección de dependencias Spring
- **Exception Handling**: Manejo centralizado de errores
- **REST API**: Arquitectura REST estándar

## 🔐 Configuración por Ambientes

El proyecto utiliza **Spring Boot Profiles** para gestionar diferentes configuraciones según el ambiente.

### Archivos de Configuración:

```
src/main/resources/
├── application.properties          # Base (se carga siempre)
├── application-dev.properties      # Perfil: DESARROLLO
├── application-test.properties     # Perfil: TEST
└── application-prod.properties     # Perfil: PRODUCCIÓN
```

### 📊 Comparativa Rápida:

| Aspecto | Desarrollo | Test | Producción |
|---------|-----------|------|-----------|
| Base de Datos | H2 | H2 | PostgreSQL |
| Logging | DEBUG | INFO | WARN |
| Swagger | ✅ Sí | ❌ No | ❌ No |
| H2 Console | ✅ Sí | ❌ No | ❌ No |

### 🚀 Activar Ambientes:

**Desarrollo** (por defecto - sin necesidad de hacer nada):
```bash
mvn spring-boot:run
```

**Test** (para pruebas automatizadas):
```bash
mvn test
```

**Producción** (con PostgreSQL):
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=tuhost.com
export DB_USERNAME=user
export DB_PASSWORD=pass
java -jar bom-service-0.0.1-SNAPSHOT.jar
```

### 📚 Documentación Completa:

**Para más detalles sobre configuración de ambientes, lee**: [ENVIRONMENTS.md](ENVIRONMENTS.md)

Incluye:
- ✅ Configuración detallada de cada ambiente
- ✅ Variables de entorno para producción
- ✅ Cómo preparar PostgreSQL
- ✅ Docker para producción
- ✅ Troubleshooting

### ⚙️ Cambiar Puerto Temporalmente:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

O en el JAR:
```bash
java -jar app.jar --server.port=9090
```

## 📖 Documentación Adicional

- **API Docs JSON**: http://localhost:8080/api-docs
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **Actuator** (si está habilitado): http://localhost:8080/actuator

## 🐛 Troubleshooting

### El puerto 8080 ya está en uso
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### La aplicación no inicia
```bash
# Verificar Java version
java -version

# Limpiar caché de Maven
mvn clean
```

### No puedo conectar a H2
- Verifica que `spring.h2.console.enabled=true` en `application.properties`
- Comprueba que JDBC URL sea: `jdbc:h2:mem:testdb`

## 🤝 Contribución

Siéntete libre de reportar bugs y sugerir mejoras creando un pull request.

## 📜 Licencia

Este proyecto está bajo licencia MIT.

## 📧 Contacto

Para preguntas o sugerencias, contacta a michael@example.com

---

**Última actualización**: Marzo 2026
  "product": "Zapato",
  "quantity": 100,
  "materials": [
    {"material": "Cuero", "required": 200, "unit": "UNIDADES", "unitSymbol": "un"},
    {"material": "Suela", "required": 100, "unit": "UNIDADES", "unitSymbol": "un"},
    {"material": "Cordones", "required": 100, "unit": "UNIDADES", "unitSymbol": "un"}
  ]
}
```

## Manejo de Errores

La API devuelve códigos de estado HTTP apropiados:

- `200 OK`: Solicitud exitosa
- `201 Created`: Recurso creado exitosamente
- `400 Bad Request`: Datos de entrada inválidos
- `404 Not Found`: Recurso no encontrado
- `409 Conflict`: El recurso ya existe (ej. producto duplicado)
- `500 Internal Server Error`: Error del servidor

### Ejemplo de Respuesta de Error
```json
{
  "status": 404,
  "message": "Producto no encontrado con ID: 999",
  "timestamp": 1703010000000
}
```

## Tecnologías Utilizadas

- **Framework**: Spring Boot 4.0.4
- **ORM**: Hibernate JPA
- **Base de Datos**: H2 (En memoria)
- **Validación**: Spring Validation (Jakarta Validation)
- **Documentación API**: Springdoc OpenAPI (Swagger UI)
- **Utilidades**: Lombok
- **Build Tool**: Maven

## Consideraciones de Diseño

### Entidades
- **Producto**: Tabla principal de productos
- **InsumoEntity**: Insumos disponibles
- **UnidadDeMedidaEntity**: Unidades de medida (Peso, Volumen, etc.)
- **ProductoInsumoEntity**: Tabla intermedia para la relación M:M entre Producto e InsumoEntity

### Separación de Capas
1. **Controller**: Maneja solicitudes HTTP y validaciones básicas
2. **Service**: Contiene la lógica de negocio
3. **Repository**: Acceso a datos con Spring Data JPA
4. **Entity**: Modelos de datos mapeados a la BD

### Buenas Prácticas
- DTOs para transferencia de datos (no expone entidades JPA)
- Validaciones en DTOs y controladores
- Manejo centralizado de excepciones
- Relaciones lazy-loaded para optimizar rendimiento
- Documentación con Swagger/OpenAPI

## Mejoras Futuras

- [ ] Autenticación y autorización (JWT)
- [ ] Paginación en listados
- [ ] Actualización y eliminación de productos/insumos
- [ ] Reportes en PDF
- [ ] Persistencia en BD relacional (PostgreSQL, MySQL)
- [ ] Tests unitarios e integración
- [ ] Docker y Kubernetes

## Autor

Miembro del equipo: Michael Rodríguez

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.
