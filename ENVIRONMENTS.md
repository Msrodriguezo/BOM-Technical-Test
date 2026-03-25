# 🌍 Configuración de Ambientes - BOM Service

Este documento explica cómo usar los diferentes perfiles de ambiente (Desarrollo, Test, Producción) en el microservicio BOM Service.

---

## 📋 Archivos de Configuración

El proyecto utiliza **Spring Boot Profiles** para manejar diferentes configuraciones por ambiente:

```
src/main/resources/
├── application.properties           # Configuración por defecto (DESARROLLO)
├── application-dev.properties       # Perfil: DESARROLLO
├── application-test.properties      # Perfil: TEST
└── application-prod.properties      # Perfil: PRODUCCIÓN
```

---

## 🚀 Cómo Ejecutar en Cada Ambiente

### 1️⃣ DESARROLLO (Por defecto)

**Características**:
- ✅ Base de datos: H2 en memoria
- ✅ Logging: DEBUG (muy detallado)
- ✅ Swagger UI: Habilitado
- ✅ H2 Console: Habilitado
- ✅ DevTools: Habilitado (recargar en cambios)

**Ejecutar**:
```bash
# Opción 1: Por defecto (no necesita especificar perfil)
mvn spring-boot:run

# Opción 2: Explícitamente
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Opción 3: Desde JAR compilado
mvn clean package -DskipTests
java -jar target/bom-service-0.0.1-SNAPSHOT.jar

# Opción 4: Con puerto personalizado
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

**Acceso**:
- 🎨 Swagger UI: http://localhost:8080/swagger-ui.html
- 📊 H2 Console: http://localhost:8080/h2-console
- 📝 API Docs: http://localhost:8080/api-docs

**Configuración**:
```properties
spring.profiles.active=dev
logging.level.com.michaelrodriguez.mrp=DEBUG      # Muy detallado
spring.jpa.show-sql=true                          # Muestra SQL
spring.h2.console.enabled=true                    # H2 habilitado
```

---

### 2️⃣ TEST (Pruebas Automatizadas)

**Características**:
- ✅ Base de datos: H2 en memoria (aislada)
- ✅ Logging: INFO (menos ruido)
- ✅ Swagger UI: Deshabilitado (más rápido)
- ✅ H2 Console: Deshabilitado
- ✅ Cache: Deshabilitado

**Ejecutar Tests**:
```bash
# Opción 1: Ejecutar todos los tests
mvn test

# Opción 2: Ejecutar test específico
mvn test -Dtest=ProductoServiceImplTest

# Opción 3: Activar perfil test explícitamente
mvn test -Dspring.profiles.active=test

# Opción 4: Desde IDE (IntelliJ/VS Code)
# Click derecho en clase de test → Run 'TestClass'
# Auto-detecta el perfil @ActiveProfiles("test")
```

**En código de Test**:
```java
@SpringBootTest
@ActiveProfiles("test")  // Activa application-test.properties
class ProductoServiceImplTest {
    // ...
}
```

**Configuración**:
```properties
spring.profiles.active=test
logging.level.com.michaelrodriguez.mrp=INFO      # Menos detallado
spring.jpa.show-sql=false                        # No muestra SQL
springdoc.swagger-ui.enabled=false                # Swagger deshabilitado
```

---

### 3️⃣ PRODUCCIÓN (Production)

**Características**:
- ✅ Base de datos: PostgreSQL (o cualquier BD relacional)
- ✅ Logging: WARNING (solo errores)
- ✅ Swagger UI: Deshabilitado (por seguridad)
- ✅ Logs en archivo: `/var/log/bom-service/application.log`
- ✅ Connection Pool: HikariCP optimizado para producción
- ✅ Validación DDL: `validate` (no crea/modifica esquema)

**Ejecutar en Producción**:
```bash
# Opción 1: Con variable de entorno
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=db.production.com
export DB_USERNAME=bom_user
export DB_PASSWORD=secure_password
java -jar bom-service-0.0.1-SNAPSHOT.jar

# Opción 2: Con argumentos en línea de comandos
java -jar bom-service-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --spring.datasource.url=jdbc:postgresql://db.prod:5432/bom_service \
  --spring.datasource.username=bom_user \
  --spring.datasource.password=secure_pass

# Opción 3: Desde un systemd service (recomendado)
[Service]
Environment="SPRING_PROFILES_ACTIVE=prod"
Environment="DB_HOST=db.production.com"
ExecStart=/usr/bin/java -jar /opt/bom-service/bom-service.jar
```

**Docker (recomendado para producción)**:
```dockerfile
# Archivo: Dockerfile
FROM openjdk:21-slim
COPY target/bom-service-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build y ejecutar
docker build -t bom-service:latest .
docker run -e SPRING_PROFILES_ACTIVE=prod \
           -e DB_HOST=postgres-container \
           -p 8080:8080 \
           bom-service:latest
```

**Acceso**:
- ❌ Swagger UI: Deshabilitado
- ❌ H2 Console: Deshabilitado
- 📊 Health Check: http://localhost:8080/actuator/health
- 📈 Métricas: http://localhost:8080/actuator/metrics

**Configuración**:
```properties
spring.profiles.active=prod
spring.datasource.url=jdbc:postgresql://db-host:5432/bom_service
logging.level.com.michaelrodriguez.mrp=WARN      # Solo warnings/errors
spring.jpa.show-sql=false                        # No muestra SQL
springdoc.swagger-ui.enabled=false                # Swagger DESHABILITADO
spring.jpa.hibernate.ddl-auto=validate            # No modifica esquema
```

---

## 📊 Comparativa de Ambientes

| Característica | Desarrollo | Test | Producción |
|---|---|---|---|
| **Base de Datos** | H2 en memoria | H2 en memoria | PostgreSQL/MySQL |
| **Logging** | DEBUG | INFO | WARN |
| **Swagger UI** | ✅ Habilitado | ❌ Deshabilitado | ❌ Deshabilitado |
| **H2 Console** | ✅ Habilitado | ❌ Deshabilitado | ❌ Deshabilitado |
| **DevTools** | ✅ Habilitado | ❌ Deshabilitado | ❌ Deshabilitado |
| **DDL Strategy** | create-drop | create-drop | validate |
| **Connection Pool** | Default | Default | HikariCP (20 máx) |
| **Caché** | No | No | Sí |
| **Logs en archivo** | No | No | Sí (/var/log/) |
| **show-sql** | true | false | false |

---

## 🔧 Configuración de Variables de Entorno

### Para Desarrollo
Sin necesidad de configurar nada, usa los defaults.

### Para Test
Automático usando `@ActiveProfiles("test")`

### Para Producción

**Linux/Mac**:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=db.production.com
export DB_PORT=5432
export DB_NAME=bom_service
export DB_USERNAME=bom_user
export DB_PASSWORD=your_secure_password
```

**Windows (CMD)**:
```cmd
set SPRING_PROFILES_ACTIVE=prod
set DB_HOST=db.production.com
set DB_USERNAME=bom_user
set DB_PASSWORD=your_secure_password
```

**Windows (PowerShell)**:
```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
$env:DB_HOST="db.production.com"
$env:DB_USERNAME="bom_user"
$env:DB_PASSWORD="your_secure_password"
```

---

## 🗄️ Preparar Base de Datos PostgreSQL (Producción)

### 1. Crear la base de datos
```sql
-- En PostgreSQL
CREATE DATABASE bom_service;
CREATE USER bom_user WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE bom_service TO bom_user;
```

### 2. Configurar HikariCP
En `application-prod.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

### 3. Ejecutar SQL de inicialización (si es necesario)
Crear un archivo `src/main/resources/schema.sql`:
```sql
-- Las tablas se crearán automáticamente (Hibernate)
-- Este archivo es solo si quieres DDL manual
```

---

## 📝 Ejemplo: Cambiar de Desarrollo a Producción

### Paso 1: Compilar JAR
```bash
mvn clean package -DskipTests -Pprod
```

### Paso 2: Transferir a servidor de producción
```bash
scp target/bom-service-0.0.1-SNAPSHOT.jar user@prod-server:/opt/bom-service/
```

### Paso 3: Configurar variables de entorno
```bash
ssh user@prod-server
cd /opt/bom-service
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=db.production.com
export DB_USERNAME=bom_user
export DB_PASSWORD=secure_pass
```

### Paso 4: Ejecutar
```bash
java -Xmx512m -Xms256m -jar bom-service-0.0.1-SNAPSHOT.jar
```

---

## 🐛 Troubleshooting

### Problema: "No application properties specified"
**Solución**: Spring Boot siempre usa las propiedades del perfil activo. Verifica:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Problema: "Cannot connect to database"
**Solución** para producción:
1. Verifica que PostgreSQL está corriendo
2. Valida credenciales en variables de entorno
3. Comprueba que el firewall permite conexión al puerto 5432

### Problema: "Spring application properties not loading"
**Solución**:
```bash
# En el JAR, verifica que los archivos estén en classpath
jar tf target/bom-service-0.0.1-SNAPSHOT.jar | grep application-.properties
```

### Problema: "Swagger no aparece en Producción"
**Solución**: Es intencional. Swagger está deshabilitado por seguridad. Usa cURL o Postman en su lugar.

---

## 📚 Documentación Relacionada

- [README.md](../README.md) - Guía general del proyecto
- [application-dev.properties](../src/main/resources/application-dev.properties) - Config desarrollo
- [application-test.properties](../src/main/resources/application-test.properties) - Config test
- [application-prod.properties](../src/main/resources/application-prod.properties) - Config producción

---

## 🎓 Referencia: Spring Boot Profiles

Spring Boot utiliza el mecanismo de **Profiles** para gestionar configuraciones por ambiente.

**Cómo funcionan**:
1. Se carga `application.properties` (configuración por defecto)
2. Se busca `application-{perfil}.properties` según el perfil activo
3. Las propiedades específicas del perfil sobrescriben las defaults

**En `@Configuration` Java**:
```java
@Configuration
@Profile("prod")
public class ProductionConfig {
    // Solo se carga en producción
}
```

**En `@Bean` Java**:
```java
@Bean
@Profile("!prod")  // Todos excepto producción
public DataSource dataSource() {
    return new H2DataSource();
}
```

---

## ✅ Checklist para Enviar a Producción

- [ ] Cambiar `spring.profiles.active=prod`
- [ ] Configurar PostgreSQL y base de datos
- [ ] Establecer variables de entorno seguras
- [ ] Deshabilitar Swagger (ya está en config)
- [ ] Habilitar HTTPS en API Gateway/Reverse Proxy
- [ ] Configurar backups de base de datos
- [ ] Configurar monitoreo y alertas
- [ ] Pruebas de carga antes de deploy
- [ ] Plan de rollback en caso de fallos
- [ ] Documentar procedimiento de deploy

---

**Última actualización**: Marzo 2026
