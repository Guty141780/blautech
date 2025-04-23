# Product Management App

Aplicación CRUD de productos construida con Angular, Spring Boot, API Gateway y MongoDB, orquestada completamente con Docker.

## Tecnologías Utilizadas

- **Frontend**: Angular 17
- **Backend**: Spring Boot 3 (Java 17)
- **API Gateway**: Spring Cloud Gateway
- **Base de Datos**: MongoDB
- **Contenedores**: Docker & Docker Compose
- **Servidor web frontend**: Nginx

## Estructura del Proyecto

```
frontend-app       # Aplicación Angular (frontend)
product-service    # Servicio de productos (backend)
api-gateway        # Gateway API (puerta de entrada)
docker-compose.yml # Orquestador de todos los servicios
```

## ¿Cómo levantar el proyecto?

1. Clonar el repositorio:

```bash
git clone 
```

2. Construir y levantar los contenedores:

```bash
docker-compose up --build
```

Esto levanta:
- MongoDB en el puerto `27017`
- API Gateway en `http://localhost:8080`
- Product Service en `http://localhost:8081`
- Frontend en `http://localhost:4200`

## Rutas principales

- `GET    /api/products` → Listar productos
- `POST   /api/products` → Crear producto
- `PUT    /api/products/{id}` → Actualizar producto
- `DELETE /api/products/{id}` → Eliminar producto

## 🧪 Prueba

Accedé a la app en tu navegador:

```
http://localhost:4200
```

Probá agregar, editar y eliminar productos desde la interfaz.

## Variables importantes

- El gateway enruta las peticiones de `/api/products` hacia el microservicio

## Notas

- Se aplicaron buenas prácticas como DTOs, logs estructurados y separación por capas.
- Incluye validaciones y manejo de errores.

---
