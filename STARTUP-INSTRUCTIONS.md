# Arranque con Docker — Instrucciones y credenciales

Archivos relevantes:
- `docker-compose.yml`
- `.env` (contiene credenciales usadas por los servicios)

Credenciales por defecto (archivo `.env`):
- MYSQL_ROOT_PASSWORD: `rootpass`
- MYSQL_DATABASE: `db_biblioteca_dev`
- SPRING_DATASOURCE_USERNAME: `root`
- SPRING_DATASOURCE_PASSWORD: `rootpass`
- SPRING_DATASOURCE_URL: `jdbc:mysql://db:3306/db_biblioteca_dev?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`

Comandos rápidos:

```bash
# Construir y arrancar en background
docker-compose up --build -d

# Ver estado de contenedores
docker-compose ps

# Ver logs de la aplicación
docker-compose logs --follow --tail 200 app

# Parar y eliminar contenedores y volúmenes (borra datos de la BD)
docker-compose down -v
```

Acceso a la aplicación:
- Entrada principal (Gateway): `http://localhost:8081` — el Spring Cloud Gateway expone un único punto de entrada y reenvía rutas como `/api/**` y `/doc/**` hacia la aplicación.
- Acceso directo al contenedor `app`: `http://localhost:8080` (útil para depuración interna o cuando se accede desde otros contenedores en la red Docker).
- Documentación Swagger a través del Gateway: `http://localhost:8081/doc/swagger-ui.html`.

Nota: para que Swagger UI (desde el navegador) pueda invocar las APIs a través del Gateway es necesario:
- Que el `servers` del OpenAPI sea relativo (p.ej. `/`), y
- Que CORS esté habilitado en la aplicación para las rutas expuestas por el Gateway (desarrollo). Revisa `CorsConfig` y `SwaggerConfig` si hay problemas de "Failed to fetch" en el UI.

Notas de seguridad / despliegue:
- Cambia las credenciales del archivo `.env` antes de usar en producción.
- Usa un gestor de secretos o variables de entorno del orquestador (Kubernetes, Docker Swarm) para producción.
