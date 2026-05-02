# AGENDA_API

API REST para la gestión de agenda (actividades, salas, usuarios, dispositivos, roles, recursos y permisos), construida con Spring Boot.

## Stack

- Java 21
- Spring Boot 4
- Spring Web / Spring Security / OAuth2
- Spring Data JPA
- MySQL (local por defecto)
- Maven Wrapper (`mvnw`)
- OpenAPI/Swagger

## Requisitos

- JDK 21+
- Docker y Docker Compose (opcional, recomendado para base de datos)

## Configuración por perfiles

### `local` (por defecto)

- Puerto API: `8085`
- BD: MySQL en `localhost:3307`
- URL BD: `jdbc:mysql://localhost:3307/room_reservation_system`

El perfil activo por defecto está en `application.properties`:

- `spring.profiles.active=local`

### `prod`

Usa variables de entorno para base de datos y OAuth:

- `SPRING_DATASOURCE_URL`
- `DB_USER`
- `DB_PASSWORD`
- `FRONTEND_URL`
- `BACKEND_URL`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`
- `AWS_COGNITO_USER_POOL_ID`
- `AWS_COGNITO_CLIENT_ID`

## Arranque rápido (local)

1. Levantar MySQL con Docker:

```bash
docker compose up -d
```

2. Compilar:

```bash
./mvnw -q -DskipTests compile
```

3. Ejecutar la API:

```bash
./mvnw spring-boot:run
```

La API quedará disponible en:

- `http://localhost:8085`

## Swagger / OpenAPI

- UI: `http://localhost:8085/swagger-ui/index.html`
- JSON OpenAPI: `http://localhost:8085/v3/api-docs`

## Endpoints principales

Nota: se listan según los controllers actuales del proyecto.

### Activitats (`/activitats`)

- `GET /activitats/model`
- `GET /activitats`
- `GET /activitats/{id}`
- `GET /activitats/usuari/{idUsuari}`
- `POST /activitats`
- `DELETE /activitats/{id}`
- `DELETE /activitats`

### Salas (`/salas`)

- `GET /salas`
- `POST /salas`
- `PUT /salas/{id}`
- `DELETE /salas/{id}`

### Usuaris (`/usuaris`)

- `GET /usuaris`
- `GET /usuaris/actius/{actiu}`
- `GET /usuaris/profes`
- `POST /usuaris`
- `PUT /usuaris/{id}`
- `DELETE /usuaris/{id}`
- `POST /usuaris/token`

Formato relevante en respuesta de usuario:

- `permisos` devuelve una lista de objetos con:
- `recurso` (nombre del recurso)
- `valor` (máscara numérica de permisos)

Errores comunes (`400`) en usuarios:

- `Ya existe un usuario con ese correo` al crear o editar con un email ya existente

### Correos permitidos (`/correos-permitidos`)

- `GET /correos-permitidos`
- `GET /correos-permitidos/{email}`
- `POST /correos-permitidos`
- `DELETE /correos-permitidos/{id}`

### Dispositius (`/dispositius`)

- `GET /dispositius`
- `POST /dispositius`
- `PUT /dispositius/{mac}/heartbeat`
- `PUT /dispositius/{id}`
- `DELETE /dispositius/{id}`

### Roles (`/roles`)

- `GET /roles`
- `GET /roles/{id}`
- `POST /roles`
- `PUT /roles/{id}`
- `DELETE /roles/{id}`

### Recursos (`/recursos`)

- `GET /recursos`
- `GET /recursos/{id}`
- `POST /recursos`
- `PUT /recursos/{id}`
- `DELETE /recursos/{id}`

### Permisos (`/permisos`)

- `GET /permisos`
- `GET /permisos/{id}`
- `GET /permisos/rol/{rol}`
- `GET /permisos/recurs/{idRecurs}`
- `POST /permisos`
- `PUT /permisos/{id}`
- `DELETE /permisos/{id}`

## Seguridad

En estado actual, `prod` valida los JWT contra Cognito y `local` sigue con la configuración de desarrollo sin resource server activo.

## Estructura del proyecto

- `src/main/java/com/agenda/itic/controller`: controladores REST
- `src/main/java/com/agenda/itic/service`: lógica de negocio
- `src/main/java/com/agenda/itic/repository`: acceso a datos
- `src/main/java/com/agenda/itic/model`: entidades/modelos
- `src/main/java/com/agenda/itic/dto`: DTOs de request/response
- `src/main/resources`: configuración por entorno

## Comandos útiles

```bash
# Compilar sin tests
./mvnw -q -DskipTests compile

# Ejecutar tests
./mvnw test

# Generar artefacto
./mvnw -DskipTests package
```
