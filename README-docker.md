# Docker — instrucciones rápidas

Requisitos: Docker y Docker Compose instalados.

Construir y arrancar servicios (BD + Eureka + aplicación + gateway):

```bash
# Levanta DB, Eureka Server, la aplicación y el Gateway
docker-compose up --build -d
```

Esto hará:
- Levantar un contenedor `db` con MySQL 8 y la base `db_biblioteca_dev`.
- Construir la imagen de la aplicación usando Maven y arrancarla en el puerto `8080`.
- Construir y arrancar el `gateway` (puerto `8081`) y el `eureka` (puerto `8761`).

Variables importantes (definidas en `docker-compose.yml`):
- `SPRING_DATASOURCE_URL` — apunta al host `db` dentro de la red de Compose.
- `SPRING_PROFILES_ACTIVE` — usa el profile `dev` (usa `application-dev.properties`).
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` — URL donde los clientes buscan el servidor Eureka (por defecto `http://eureka:8761/eureka`).

Parar y eliminar contenedores y volúmenes:

```bash
docker-compose down -v
```

Comprobaciones rápidas:

```bash
# Accede a Eureka UI
open http://localhost:8761

# Accede a la API vía Gateway
curl http://localhost:8081/api/carreras

# OpenAPI JSON disponible a través del Gateway
curl http://localhost:8081/v3/api-docs
```
