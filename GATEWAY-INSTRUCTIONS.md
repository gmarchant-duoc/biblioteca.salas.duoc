# Spring Cloud Gateway — instrucciones

Resumen:
- Módulo `gateway` en el repo: construye una pequeña Spring Boot app con Spring Cloud Gateway.
- Expone puerto `8081` y enruta peticiones `GET/POST ...` que coincidan con `Path=/api/**` hacia la aplicación principal (`app`) en `http://app:8080`.

Construir y arrancar con Docker Compose:

```bash
docker-compose up --build -d gateway app db
```

Comprobar estado:

```bash
docker-compose ps
docker-compose logs --tail 200 gateway
```

Rutas expuestas por defecto:
- `http://localhost:8081/api/...` → proxy hacia `http://localhost:8080/api/...` (la app dentro de Compose)

Configuración importante:
- El `gateway` usa `application-gateway.properties` en `gateway/src/main/resources`.
- Variables desde `.env` cargadas por `docker-compose`.

Pruebas rápidas:

```bash
curl http://localhost:8081/api/carreras
```

Notas:
- Para producción, añade circuit breakers, timeouts y autenticación en el gateway.
- Si quieres que el gateway actúe como único punto de entrada (reemplazando el puerto 8080 expuesto), cambia los mapeos de puertos en `docker-compose.yml`.
