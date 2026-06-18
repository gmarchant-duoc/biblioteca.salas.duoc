# Docker — instrucciones rápidas

Requisitos: Docker y Docker Compose instalados.

Construir y arrancar servicios (BD + aplicación):

```bash
docker-compose up --build
```

Esto hará:
- Levantar un contenedor `db` con MySQL 8 y la base `db_biblioteca_dev`.
- Construir la imagen de la aplicación usando Maven y arrancarla en el puerto `8080`.

Variables importantes (definidas en `docker-compose.yml`):
- `SPRING_DATASOURCE_URL` — apunta al host `db` dentro de la red de Compose.
- `SPRING_PROFILES_ACTIVE` — usa el profile `dev` (usa `application-dev.properties`).

Parar y eliminar contenedores y volúmenes:

```bash
docker-compose down -v
```
