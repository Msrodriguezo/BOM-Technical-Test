# 📮 Colección de Postman - BOM Service

Esta carpeta contiene todo lo necesario para probar el microservicio BOM Service con **Postman**.

## 📋 Archivos Incluidos

1. **BOM-Service.postman_collection.json** - Colección completa con todos los endpoints
2. **BOM-Service-Environment.postman_environment.json** - Variables pre-configuradas
3. **README.md** - Este archivo (instrucciones generales)
4. **QUICK_START.md** - Guía rápida (empieza aquí si es tu primera vez)

---

## 🚀 Instalación Rápida (2 minutos)

### Paso 1: Descargar Postman

Descarga desde: https://www.postman.com/downloads/

Soporta: Windows, Mac, Linux

### Paso 2: Importar Colección

1. **Abre Postman**
2. **File** → **Import**
3. **Selecciona**: `BOM-Service.postman_collection.json`
4. ¡Listo! Todos los endpoints están cargados

### Paso 3: Importar Environment

1. **File** → **Import**
2. **Selecciona**: `BOM-Service-Environment.postman_environment.json`
3. En el dropdown superior derecho, selecciona: **"BOM Service - Local"**

---

## 🔄 Configurar Variables de Entorno

### Opción A: Crear Environment Manualmente (2 minutos)

1. En Postman, haz clic en el ícono de **engranaje** (⚙️) > **"Environments"**
2. Haz clic en **"Create"** o **"+"**
3. Nombrelo: `BOM Service - Local`
4. Agrega esta variable:

| Variable | Value | Type |
|----------|-------|------|
| base_url | http://localhost:8080 | string |

5. Haz clic en **"Save"**

### Opción B: Importar Environment Pre-configurado (1 minuto) ⚡

1. En Postman, ve a **File** → **Import**
2. Selecciona: `BOM-Service-Environment.postman_environment.json`
3. ¡Listo! Ya tendrás todas las variables configuradas

### Seleccionar el Environment

- En la esquina superior derecha de Postman, selecciona **"BOM Service - Local"** del dropdown

**Variables incluidas**:
- `base_url`: URL del servidor
- `producto_id`: ID del producto (por defecto 1)
- `insumo_nombre`, `insumo_cantidad`, `unidad_medida_id`: Para agregar insumos
- `produccion_cantidad`: Cantidad a producir (por defecto 100)

---

## 🚀 Estructura de la Colección

La colección contiene 3 carpetas principales:

### 📦 **Productos**
Operaciones CRUD para productos:
- **Crear Producto**: POST `/api/productos`
  - Body: `{"nombreProducto": "Zapato Nike"}`
- **Obtener Producto**: GET `/api/productos/{id}`
- **Agregar Insumo**: POST `/api/productos/{id}/insumos`
  - Body: `{"nombre": "...", "cantidad": 2.5, "unidadDeMedidaId": 6}`

### 🏭 **Producción**
Cálculo de insumos para producción:
- **Calcular Insumos**: POST `/api/production/calculate`
  - Body: `{"productoId": 1, "cantidad": 100}`

### 🔗 **Flujo Completo - Ejemplo**
Ejemplo paso a paso que simula un caso real completo:
1. 📌 Crear un producto (Zapato)
2. 📌 Agregar insumo #1 (Cuero - 2 metros)
3. 📌 Agregar insumo #2 (Suela - 1 unidad)
4. 📌 Agregar insumo #3 (Cordones - 1 unidad)
5. 📌 Ver el producto completo (con todos insumos)
6. 📌 Calcular para 100 unidades de producción

**¡Recomendamos ejecutar este flujo primero!**

---

## 📊 Ejemplos de Requests

### Crear Producto
```json
POST {{base_url}}/api/productos
Content-Type: application/json

{
  "nombreProducto": "Zapato Nike Air Max"
}
```

**Respuesta (201)**:
```json
{
  "id": 1,
  "nombre": "Zapato Nike Air Max",
  "insumos": []
}
```

---

### Agregar Insumo
```json
POST {{base_url}}/api/productos/1/insumos
Content-Type: application/json

{
  "nombre": "Cuero Premium",
  "cantidad": 2.5,
  "unidadDeMedidaId": 6
}
```

**Respuesta (200)**:
```json
{
  "id": 1,
  "nombre": "Zapato Nike Air Max",
  "insumos": [
    {
      "insumoId": 1,
      "insumo": "Cuero Premium",
      "cantidad": 2.5,
      "unidad": "Metros",
      "simboloUnidad": "m"
    }
  ]
}
```

---

### Calcular Insumos para Producción
```json
POST {{base_url}}/api/production/calculate
Content-Type: application/json

{
  "productoId": 1,
  "cantidad": 100
}
```

**Respuesta (200)**:
```json
{
  "producto": "Zapato Nike Air Max",
  "cantidad": 100,
  "insumos": [
    {
      "insumo": "Cuero Premium",
      "requerido": 250,
      "unidad": "Metros",
      "simboloUnidad": "m"
    }
  ]
}
```

---

## 📋 IDs de Unidades de Medida

Usa estos IDs en el campo `unidadDeMedidaId`:

| ID | Unidad | Símbolo | Tipo |
|---|---|---|---|
| 1 | Gramos | g | Peso |
| 2 | Kilogramos | kg | Peso |
| 3 | Litros | L | Volumen |
| 4 | Mililitros | ml | Volumen |
| 5 | Unidades | un | Cantidad |
| 6 | Metros | m | Longitud |
| 7 | Centímetros | cm | Longitud |

---

## 🔧 Troubleshooting

### Error: "Cannot connect to http://localhost:8080"
**Solución**: Verifica que el servidor esté corriendo
```bash
mvn spring-boot:run
```

### Error: 404 Not Found
**Solución**: El producto no existe. Crea uno primero con la request "Crear Producto"

### Error: 409 Conflict
**Solución**: El producto ya existe con ese nombre. Usa un nombre diferente.

### Error: Variables no funcionan
**Solución**: Asegúrate de haber seleccionado el environment correcto en el dropdown superior derecho

### Error: 400 Bad Request
**Solución**: Verifica que los datos en el body sean válidos:
- `nombreProducto` no puede estar vacío
- `cantidad` debe ser un número válido
- `unidadDeMedidaId` debe ser un ID válido (1-7)

---

## 💡 Tips Avanzados

### Automatizar Requests en Secuencia

Postman puede ejecutar requests automáticamente en orden:

1. Click en la carpeta "Flujo Completo - Ejemplo"
2. Click en el ícono **▶ Run** (flecha de play)
3. Postman ejecutará todos los requests en orden

### Usar Variables en Requests

En lugar de hardcodear IDs, usa variables:
```json
{
  "productoId": {{producto_id}},
  "cantidad": {{produccion_cantidad}}
}
```

### Guardar Respuestas como Variables

En un request, ve a **Tests** y agrega:
```javascript
var jsonData = pm.response.json();
pm.globals.set("producto_id", jsonData.id);
```

Luego usa `{{producto_id}}` en otros requests.

### Exportar Resultados

1. Click **File** → **Export as JSON**
2. Guarda todos los requests y respuestas

---

## 📚 Documentación Adicional

- **README Principal**: `../README.md`
- **Guía Rápida**: `QUICK_START.md`
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

---

## 🔐 Notas de Seguridad

- Esta colección es para **desarrollo local** solamente
- No contiene credenciales hardcodeadas (variable `base_url` configurable)
- En producción, agrega:
  - **Autenticación** (JWT, OAuth)
  - **HTTPS** (certifi SSL/TLS)
  - **Rate Limiting**
  - **Validación de entrada más estricta**

---

## 📞 ¿Necesitas Ayuda?

1. Lee **QUICK_START.md** para una guía rápida
2. Consulta la sección "Troubleshooting" arriba
3. Verifica que el servidor esté corriendo: `mvn spring-boot:run`
4. Revisa el README principal para más detalles

---

**Última actualización**: Marzo 2026  
**Autor**: Michael Rodríguez  
**Soporte**: Para reportar bugs, crea un issue en el repositorio
