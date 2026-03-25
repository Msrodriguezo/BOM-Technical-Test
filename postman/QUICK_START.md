# 🚀 Quick Start - Usando Postman con BOM Service

## 1️⃣ Instalación Rápida (2 minutos)

### Paso 1: Descargar Postman
```
https://www.postman.com/downloads/
```

### Paso 2: Importar Colección
1. Abre Postman
2. **File** → **Import**
3. Selecciona: `BOM-Service.postman_collection.json`
4. ¡Listo! Todos los endpoints están disponibles

### Paso 3: Configurar Environment
1. Click en ⚙️ (arriba a la derecha)
2. **Create** nuevo Environment: `BOM Service - Local`
3. Agrega variable:
   - **base_url** = `http://localhost:8080`
4. **Save** y selecciona el environment

---

## 2️⃣ Probar Endpoints (Recomendado: Flujo Completo)

### ¡Sigue este flujo para probar TODO!

#### 📦 Paso 1: Crear un Producto
```
Carpeta: Flujo Completo - Ejemplo
Request: 1. Crear Producto - Zapato
Click: Send
Respuesta esperada: {"id": X, "nombre": "Zapato ADidas", "insumos": []}
```
**Nota el ID de respuesta** (ej: 2)

#### 2️⃣ Paso 2: Agregar Insumo - Cuero
```
Carpeta: Flujo Completo - Ejemplo
Request: 2. Agregar Insumo - Cuero
Modifica el URL si es necesario: /api/productos/{ID}/insumos
Click: Send
```

#### 3️⃣ Paso 3: Agregar Insumo - Suela
```
Carpeta: Flujo Completo - Ejemplo
Request: 3. Agregar Insumo - Suela
Modifica el URL si es necesario: /api/productos/{ID}/insumos
Click: Send
```

#### 4️⃣ Paso 4: Agregar Insumo - Cordones
```
Carpeta: Flujo Completo - Ejemplo
Request: 4. Agregar Insumo - Cordones
Modifica el URL si es necesario: /api/productos/{ID}/insumos
Click: Send
```

#### 5️⃣ Paso 5: Ver Producto Completo (con todos los insumos)
```
Carpeta: Flujo Completo - Ejemplo
Request: 5. Ver Producto Completo
Modifica el URL si es necesario: /api/productos/{ID}
Click: Send
Respuesta: Debes ver 3 insumos (Cuero, Suela, Cordones)
```

#### 6️⃣ Paso 6: Calcular para 100 Unidades
```
Carpeta: Flujo Completo - Ejemplo
Request: 6. Calcular para Producción de 100 Unidades
Click: Send
Respuesta: Verás los insumos multiplicados por 100
```

---

## 3️⃣ Referencias Rápidas de IDs

### Unidades de Medida (en campo `unidadDeMedidaId`)
```
1 = Gramos (g)
2 = Kilogramos (kg)
3 = Litros (L)
4 = Mililitros (ml)
5 = Unidades (un)
6 = Metros (m)
7 = Centímetros (cm)
```

---

## 4️⃣ Ejemplos de JSON para Copiar/Pegar

### Crear Producto
```json
{
  "nombreProducto": "Mi Producto"
}
```

### Agregar Insumo
```json
{
  "nombre": "Nombre del Insumo",
  "cantidad": 2.5,
  "unidadDeMedidaId": 6
}
```

### Calcular para Producción
```json
{
  "productoId": 1,
  "cantidad": 100
}
```

---

## 5️⃣ Troubleshooting

| Problema | Solución |
|----------|----------|
| "Cannot connect to http://localhost:8080" | ¿Servidor corriendo? Ejecuta `mvn spring-boot:run` |
| Error 404 en /api/productos/999 | Ese producto no existe. Crea uno primero |
| Error 409 "producto duplicado" | Producto con ese nombre ya existe. Usa otro nombre |
| Variables no funcionan | Selecciona el environment correcto en el dropdown |

---

## 6️⃣ Tips Avanzados en Postman

### Usar Variables en Body
```json
{
  "nombreProducto": "{{nombre_producto}}"
}
```

### Crear Variable desde Response
1. En un request, ve a **Tests**
2. Agrega:
   ```javascript
   var jsonData = pm.response.json();
   pm.globals.set("producto_id", jsonData.id);
   ```
3. Usa `{{producto_id}}` en siguientes requests

### Exportar Resultados
1. **File** → **Export as JSON**
2. Guarda todas las respuestas

---

¡Listo! Ya tienes todo para probar el BOM Service con Postman. 🎉
