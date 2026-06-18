# Arranque con Docker — Instrucciones y credenciales

Archivos relevantes:
- `docker-compose.yml`
- `.env` (contiene credenciales usadas por los servicios)

Servicios y puertos principales (desarrollo):
- `db` (MySQL): 3306
- `app` (aplicación Spring Boot): 8080
- `gateway` (Spring Cloud Gateway): 8081
- `eureka` (Eureka Server): 8761

Credenciales por defecto (archivo `.env`):
- MYSQL_ROOT_PASSWORD: `rootpass`
- MYSQL_DATABASE: `db_biblioteca_dev`
- SPRING_DATASOURCE_USERNAME: `root`
- SPRING_DATASOURCE_PASSWORD: `rootpass`
- SPRING_DATASOURCE_URL: `jdbc:mysql://db:3306/db_biblioteca_dev?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`

Comandos rápidos:

```bash
# Construir y arrancar en background (arranca DB, Eureka, app y gateway)
docker-compose up --build -d

# Ver estado de contenedores
docker-compose ps

# Ver logs de un servicio (ej: app, gateway, eureka)
docker-compose logs --follow --tail 200 app
docker-compose logs --follow --tail 200 gateway
docker-compose logs --follow --tail 200 eureka

# Parar y eliminar contenedores y volúmenes (borra datos de la BD)
docker-compose down -v
```

Verificaciones útiles después del arranque:

```bash
# Eureka UI
curl http://localhost:8761/

# Lista de aplicaciones registradas en Eureka
curl http://localhost:8761/eureka/apps

# API a través del Gateway
curl http://localhost:8081/api/carreras

# OpenAPI (JSON) expuesto por la app y accesible vía gateway
curl http://localhost:8081/v3/api-docs

# Swagger UI (redirige a /doc/swagger-ui/index.html)
curl -I http://localhost:8081/doc/swagger-ui.html
```

Notas rápidas:
- El `gateway` se registra en Eureka y enruta dinámicamente usando `lb://<service-name>`. La aplicación principal se registra con el nombre `biblioteca.salas.duoc`.
- OpenAPI/Swagger está configurado para usar un `servers` relativo (`/`) y el Gateway proxifica `/v3/api-docs/**` y `/doc/**` para que el UI funcione desde `http://localhost:8081`.
- Versión de plataforma: Spring Boot `3.4.0` y Spring Cloud `2024.0.0` (alineadas para compatibilidad con Gateway y Eureka).

Notas de seguridad / despliegue:
- Cambia las credenciales del archivo `.env` antes de usar en producción.
- Habilita autenticación/seguridad para Eureka y el Gateway en entornos no confiables.
- Usa un gestor de secretos o variables de entorno del orquestador (Kubernetes, Docker Swarm) para producción.
