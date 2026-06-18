# Spring Cloud Gateway — instrucciones

Resumen:
- Módulo `gateway` en el repo: aplicación Spring Boot con Spring Cloud Gateway.
- Expone puerto `8081` y actúa como punto de entrada central.
- El `gateway` está configurado para usar descubrimiento de servicios (Eureka). Las rutas apuntan usando `lb://<service-name>` (p. ej. `lb://biblioteca.salas.duoc`) en lugar de URL estáticas.

Requisitos:
- El `eureka` debe estar disponible antes de arrancar el `gateway` para permitir registro y resolución de servicios.

Construir y arrancar con Docker Compose:

```bash
# Levanta DB, Eureka, la app y el gateway
docker-compose up --build -d
```

Comprobar estado:

```bash
docker-compose ps
docker-compose logs --tail 200 gateway
docker-compose logs --tail 200 eureka
```

Comportamiento de rutas:
- Las rutas principales expuestas por el gateway siguen `/api/**` y `/doc/**`.
- El Gateway resuelve servicios por nombre mediante Eureka. Ejemplo:

```yaml
# ejemplo de route (interno en properties/yaml)
# uri=lb://biblioteca.salas.duoc
```

Pruebas rápidas (desde host):

```bash
# API proxificada
curl http://localhost:8081/api/carreras

# OpenAPI JSON a través del gateway
curl http://localhost:8081/v3/api-docs

# Swagger UI (redirige dentro del gateway)
open http://localhost:8081/doc/swagger-ui.html
```

Notas:
- Asegúrate de que `spring.application.name` en la app está configurado como `biblioteca.salas.duoc` para que Eureka registre el servicio con ese nombre.
- CORS debe estar habilitado en la aplicación (o gestionado por el gateway) para que el Swagger UI pueda consumir las APIs desde el navegador.
- Para producción considera agregar circuit breakers, timeouts, seguridad (auth) y límites de rate en el gateway.
