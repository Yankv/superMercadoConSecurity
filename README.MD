# Super Mercado — API REST

API REST desarrollada con **Java Spring Boot** para la gestión y control de ventas de una cadena de supermercados.

## Descripción del proyecto

Una reconocida cadena de supermercados requirió digitalizar su sistema de control de ventas. Para ello se desarrolló una API REST que permite:

- Registrar productos con sus respectivos precios y stock.
- Gestionar las sucursales donde se venden los productos.
- Registrar ventas realizadas en una sucursal, especificando los productos vendidos y sus cantidades.
- Consultar rankings de productos más vendidos y sucursales con mayor número de transacciones.
- Obtener total de ingresos.

### Requisitos técnicos

- Utilizar Spring Boot con JPA para manejo de bases de datos.
  Base de datos relacional (PostgreSQL).
- Exponer endpoints RESTful para realizar CRUDS (GET, POST, PUT, DELETE o los métodos que se consideren necesarios).
- Utilizar DTOs para separar modelo de dominio y representación externa.
- Manejo adecuado de errores con Response Entity, códigos HTTP correctos (status code) y mensajes claros.
- Uso de lambdas o streams en al menos una operación del backend.
- Organización modular del proyecto (service, repository, controller).

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MapStruct (mapeo entre entidades y DTOs)
- Lombok
- PostgreSQL
- Maven

## Seguridad — Autenticación y autorización con JWT

La API implementa autenticación stateless basada en JSON Web Tokens (JWT) con soporte para refresh tokens persistidos en base de datos. A continuación se describe la arquitectura de seguridad aplicada.

### Flujo de autenticación

1. El cliente envía sus credenciales a `POST /api/auth/login`.
2. El servidor valida las credenciales, genera un `accessToken` de corta duración y un `refreshToken` persistido en base de datos, y los retorna en la respuesta.
3. Cada solicitud posterior debe incluir el `accessToken` en la cabecera `Authorization: Bearer <token>`.
4. El filtro `JwtAuthenticationFilter` intercepta cada solicitud, valida el token y establece el contexto de seguridad.
5. Cuando el `accessToken` expira, el cliente solicita uno nuevo enviando el `refreshToken` a `POST /api/auth/refresh`.
6. El `refreshToken` puede ser invalidado explícitamente mediante `POST /api/auth/logout`.

### Endpoints de autenticación

| Método | Endpoint            | Descripción                                      | Acceso  |
|--------|---------------------|--------------------------------------------------|---------|
| POST   | `/api/auth/register` | Registrar un nuevo usuario                      | Público |
| POST   | `/api/auth/login`    | Iniciar sesión y obtener tokens                 | Público |
| POST   | `/api/auth/refresh`  | Obtener un nuevo access token con refresh token | Público |
| POST   | `/api/auth/logout`   | Revocar el refresh token activo                 | Público |

### Componentes implementados

**`JwtService`**
Responsable de generar y validar access tokens. Los tokens incluyen claims personalizados: `id` del usuario y `rol`. La secret key se externaliza a `application.properties`.

**`RefreshTokenService`**
Gestiona el ciclo de vida de los refresh tokens en base de datos. Soporta múltiples sesiones simultáneas por usuario. Incluye una tarea programada (`@Scheduled`) para eliminar tokens expirados automáticamente.

**`JwtAuthenticationFilter`**
Filtro que se ejecuta antes de `UsernamePasswordAuthenticationFilter`. Extrae el token del header `Authorization`, lo valida y establece la autenticación en el `SecurityContextHolder`.

**`CustomUserDetails` y `CustomUserDetailsService`**
Adaptadores entre la entidad `Usuario` y el modelo de seguridad de Spring Security. `CustomUserDetails` expone `id`, `email` y autoridades basadas en el rol.

**`GlobalExceptionHandler`**
Manejo centralizado de excepciones mediante `@RestControllerAdvice`. Cubre errores de autenticación (`AuthException`), tokens inválidos o expirados (`TokenException`), validaciones de entrada y acceso denegado.

### Tokens

| Token         | Almacenamiento | Configuración                          |
|---------------|----------------|----------------------------------------|
| Access token  | Cliente        | `jwt.expiration-ms` en `application.properties` |
| Refresh token | Base de datos  | `jwt.refresh-expiration-ms` en `application.properties` |

### Roles

| Rol        | Descripción                                                       |
|------------|-------------------------------------------------------------------|
| `ADMIN`    | Acceso completo: lectura, escritura, estadísticas y gestión       |
| `EMPLOYEE` | Acceso de lectura y registro de ventas                            |

### Protección de endpoints

Los permisos se aplican a nivel de `SecurityFilterChain`:

- `/api/estadisticas/**` — solo `ADMIN`
- `GET /api/productos`, `GET /api/sucursales` — público
- `GET /api/productos/**`, `GET /api/sucursales/**`, `GET /api/ventas/**` — `EMPLOYEE` o `ADMIN`
- `POST /api/ventas/**` — `EMPLOYEE` o `ADMIN`
- `POST`, `PUT`, `PATCH`, `DELETE /api/**` — solo `ADMIN`

### CORS y cabeceras de seguridad HTTP

Se configuró una política de CORS con orígenes explícitos (nunca wildcard en producción).

---

## Endpoints principales

### Productos

| Método | Endpoint              | Descripción                |
|--------|-----------------------|----------------------------|
| GET    | `/api/productos`      | Listar todos los productos |
| GET    | `/api/productos/{id}` | Obtener producto por ID    |
| POST   | `/api/productos`      | Crear producto             |
| PUT    | `/api/productos/{id}` | Actualizar producto        |
| DELETE | `/api/productos/{id}` | Eliminar producto          |

### Sucursales

| Método | Endpoint               | Descripción                 |
|--------|------------------------|-----------------------------|
| GET    | `/api/sucursales`      | Listar todas las sucursales |
| GET    | `/api/sucursales/{id}` | Obtener sucursal por ID     |
| POST   | `/api/sucursales`      | Crear sucursal              |
| PUT    | `/api/sucursales/{id}` | Actualizar sucursal         |
| DELETE | `/api/sucursales/{id}` | Eliminar sucursal           |

### Ventas

| Método | Endpoint                                              | Descripción                                           |
|--------|-------------------------------------------------------|-------------------------------------------------------|
| GET    | `/api/ventas`                                         | Listar todas las ventas                               |
| GET    | `/api/ventas/filtrar?sucursalId=1&fecha=2026-05-03`   | Listar todas las ventas por id de la sucursal y fecha |
| GET    | `/api/ventas/{id}`                                    | Obtener venta por ID                                  |
| POST   | `/api/ventas`                                         | Registrar venta                                       |
| PUT    | `/api/ventas/{id}`                                    | Actualizar venta                                      |
| DELETE | `/api/ventas/{id}`                                    | Eliminar venta                                        |

### Estadísticas

| Método | Endpoint                                               | Descripción                                     |
|--------|--------------------------------------------------------|-------------------------------------------------|
| GET    | `/api/estadisticas/producto-mas-vendido`               | Producto con más unidades vendidas              |
| GET    | `/api/estadisticas/top-producto-mas-vendido?limite=10` | Top N productos más vendidos                    |
| GET    | `/api/estadisticas/sucursal-mas-ventas`                | Sucursal con más transacciones completadas      |
| GET    | `/api/estadisticas/top-sucursal-mas-ventas?limite=10`  | Top N sucursales con más ventas completadas     |
| GET    | `/api/estadisticas/resumen-ventas`                     | Total de ventas realizadas y total de ingresos  |

## Decisiones de diseño

- **MapStruct** se eligió como librería de mapeo por su generación de código en tiempo de compilación, lo que elimina el uso de reflection en runtime y garantiza que los errores de mapeo se detecten antes de ejecutar la aplicación.
- **Spring Projections** se utilizan para recibir los resultados de las consultas JPQL de rankings, evitando casteos manuales desde `Object[]` y permitiendo que MapStruct mapee directamente desde la interfaz al DTO.
- El ranking de sucursales y productos considera únicamente ventas con estado `PAGADA`, reflejando únicamente transacciones cerradas exitosamente.
- **Refresh tokens en base de datos** permiten revocarlos explícitamente en logout, detectar reutilización y soportar múltiples sesiones por usuario, a diferencia de un esquema puramente stateless.
- **Secret key externalizada** a `application.properties` para facilitar la rotación de claves por entorno sin modificar código fuente.