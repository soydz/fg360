# Fleet Guard 360

## 🚀 🛠️ Stack

![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?logo=postgresql&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?logo=swagger&logoColor=black)

## Authentication

### 💡 Descripción del Microservicio
Su propósito principal es la gestión centralizada de la identidad de los usuarios.
Su función crucial es **validar las credenciales** (usuario y contraseña) de los clientes.
Tras una autenticación exitosa, el servicio genera y devuelve un **JSON Web Token (JWT)**,
el cual actúa como prueba de identidad y autorización que los clientes utilizarán
para acceder a los demas microservicios.

## Prerrequisitos
### 🐘 1. Base de Datos PostgreSQL Operativa
Para asegurar la correcta ejecución y funcionalidad de la aplicación,
es **necesario** contar con un entorno de base de datos operativo antes
de iniciar el servicio.

#### **1.1. Instancia y Acceso**

1.  **Instancia Levantada:** La instancia del servidor de PostgreSQL debe estar **ejecutándose** (***running***).
2.  **Base de Datos Creada:** Debe existir una base de datos específica (cuyo nombre se especifica en la variable `SPRING_DATASOURCE_DATABASE`).
3.  **Usuario Configurado:** Debe existir un **usuario** dedicado con los permisos necesarios (lectura, escritura, creación de tablas/esquemas) para acceder a la base de datos (definido por `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`).


### 📂 Inicialización de Datos (`db-init`)

Para garantizar que la base de datos se inicialice correctamente
con los datos mínimos necesarios, el proyecto incluye la carpeta `db-init`.
Dentro de esta carpeta, encontrarás un **script de inicialización**
Este script es responsable de **poblar la base de datos** con usuarios iniciales,
roles predefinidos o cualquier otra información de configuración esencial
para el funcionamiento inmediato del microservicio.

Importante: Este script no se ejecuta automáticamente. 
El desarrollador debe ejecutar este script manualmente contra la base de datos
PostgreSQL antes de iniciar el microservicio para poblarla adecuadamente.

---

## 📚 Documentación de la API (Swagger)

Para facilitar la exploración, comprensión y prueba interactiva de los *endpoints* de este microservicio, se ha integrado **Swagger UI**.

### 🔗 Acceso a Swagger UI

Puedes acceder a la interfaz web de Swagger para visualizar toda la documentación de la API y realizar pruebas directamente desde tu navegador:

**URL de Acceso:**
[http://localhost:8089/swagger-ui/index.html](http://localhost:8089/swagger-ui/index.html)

> **Nota:** La URL asume que el microservicio está corriendo en el puerto por defecto `8089`. Si has modificado el puerto (usando la variable `AUTH_PORT` en el `.env`), ajusta la URL según corresponda (ej. `http://localhost:<AUTH_PORT>/swagger-ui/index.html`).

### ✨ Utilidad

Swagger UI te permite:
1.  **Explorar** todos los *endpoints* disponibles para la autenticación y gestión de tokens.
2.  **Ver** los parámetros de entrada requeridos, los modelos de respuesta y los códigos de estado HTTP.
3.  **Probar** las peticiones directamente en el navegador, incluyendo el envío de credenciales para obtener un **JWT**.

## 📄 Documentación del Archivo `.env`

Este archivo contiene las **variables de entorno** necesarias para la configuración
y ejecución de la aplicación, abarcando la configuración del servidor, la conexión
a la base de datos **PostgreSQL** y los parámetros para la seguridad **JWT**.

---

## ⚙️ Configuración del Servidor

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| **`AUTH_PORT`** | Define el **puerto** en el que se ejecutará el servicio de autenticación (o el servidor de la aplicación principal). | `8080` |

---

## 🐘 Configuración de Base de Datos PostgreSQL

Estas variables son cruciales para establecer la conexión con la base de datos **PostgreSQL**.

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| **`SPRING_DATASOURCE_USERNAME`** | El **nombre de usuario** para acceder a la base de datos. | `app_user` |
| **`SPRING_DATASOURCE_PASSWORD`** | La **contraseña** del usuario para la base de datos. | `SuperSecretPwd123` |
| **`SPRING_DATASOURCE_DATABASE`** | El **nombre de la base de datos** a la que se debe conectar. | `auth_db` |
| **`SPRING_DATASOURCE_PORT`** | El **puerto** en el que se está ejecutando el servicio de PostgreSQL. | `5432` |
| **`SPRING_DATASOURCE_HOST`** | El **host o dirección IP** donde se encuentra la base de datos. | `localhost` o `192.168.1.100` |
| **`SPRING_DATASOURCE_SCHEMA`** | El **nombre del esquema** dentro de la base de datos a utilizar. | `public` |
| **`SPRING_DATASOURCE_URL`** | La **URL de conexión completa** de la base de datos. A menudo se construye con las variables anteriores. | `jdbc:postgresql://localhost:5432/auth_db` |

---

## 🔒 Configuración JWT (JSON Web Token)

Estos parámetros son utilizados para la generación, firma y validación de los tokens **JWT** para la seguridad de la aplicación.

| Variable | Descripción | Recomendación |
| :--- | :--- | :--- |
| **`SECURITY_JWT_KEY_PRIVATE`** | **Clave privada secreta** de 256 bits (o más) para firmar digitalmente los tokens (HMAC256). | Debe ser una **cadena aleatoria y larga**; no exponerla. |
| **`SECURITY_JWT_USER_GENERATOR`** | El **emisor (`issuer`)** o nombre de la aplicación/servicio que genera el token. | Un identificador único de la aplicación, e.g., `auth-service` |
| **`SECURITY_JWT_EXPIRE`** | Tiempo de **expiración del token** en **milisegundos**. | `900000` (15 minutos) o `3600000` (1 hora) |

**Nota sobre la seguridad:** La clave `SECURITY_JWT_KEY_PRIVATE` debe ser manejada con la máxima precaución y nunca debe ser expuesta públicamente.

