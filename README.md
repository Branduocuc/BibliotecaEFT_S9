# BibliotecaEFT_S9
Sistema de gestión de biblioteca desarrollado en Java con Swing, JDBC, MySQL, MVC, DAO, Singleton, hilos y sincronización.

La aplicación permite administrar libros, estudiantes, préstamos, devoluciones, consultas y reportes. También incluye autenticación de usuarios con distintos permisos según el rol.

## Funcionalidades

- Inicio de sesión de usuarios.
- Acceso según rol:
  - Bibliotecario.
  - Estudiante.
- Gestión de libros.
- Gestión de categorías.
- Gestión de estudiantes.
- Registro de préstamos.
- Registro de devoluciones.
- Control de stock de libros.
- Consulta de libros.
- Reportes:
  - Libros más prestados.
  - Historial por estudiante.
  - Libros actualmente prestados.

## Tecnologías utilizadas

- Java
- Java Swing / JFrame
- Maven
- MySQL
- JDBC
- MySQL Connector/J

## Patrones utilizados

El proyecto utiliza:

- MVC
- DAO
- Singleton

También se utilizan hilos y sincronización en el proceso de préstamos para evitar bloqueos de la interfaz y mantener la consistencia del stock.

## Base de datos

La aplicación utiliza una base de datos MySQL llamada:

`biblioteca`

Para crear y poblar la base de datos se deben ejecutar los scripts SQL en este orden:

1. `Script_crea_tablas_biblioteca.sql`
2. `Script_poblado_tablas_biblioteca.sql`

## Configuración de conexión

La conexión se realiza desde la clase:

`DatabaseConnection.java`

Configuración utilizada:

- Host: `localhost`
- Puerto: `3306`
- Base de datos: `biblioteca`
- Usuario: `root`

La contraseña no está guardada directamente en el código.

Se utiliza la variable de entorno:

`DB_PASSWORD`

En IntelliJ IDEA se puede configurar desde:

`Run > Edit Configurations > Environment variables`

Ejemplo:

`DB_PASSWORD=tu_contraseña_mysql`

## Cómo ejecutar el proyecto

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que MySQL esté iniciado.
3. Ejecutar los scripts SQL de creación y poblamiento.
4. Configurar la variable de entorno `DB_PASSWORD`.
5. Esperar que Maven cargue las dependencias.
6. Ejecutar la clase:

`cl.duoc.biblioteca.main.Main`

Al iniciar se abrirá la pantalla de autenticación.

## Estructura del proyecto

```text
src/main/java/cl/duoc/biblioteca

controller/
dao/
main/
model/
view/
