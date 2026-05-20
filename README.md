# Documentación del Proyecto: Tienda de Informática

## 1. Ideas Previas
*   **Contexto**: Necesidad de una plataforma de comercio electrónico especializada en hardware y periféricos.
*   **Factores**: Escalabilidad, seguridad de datos (JWT) y experiencia de usuario fluida.
*   **Preguntas**: ¿Cómo gestionar el stock en tiempo real? ¿Cómo asegurar que solo administradores modifiquen el catálogo?

## 2. Matriz de Ideas
*   **Core**: Gestión de productos (CRUD), autenticación de usuarios, carrito de compras.
*   **Valor añadido**: Sistema de valoraciones, carga de imágenes dinámica, diseño responsivo con Bootstrap.

## 3. Introducción
Este proyecto consiste en una aplicación web completa (Full Stack) para la gestión y venta de productos informáticos. Combina la potencia de Spring Boot en el backend con la reactividad de Angular en el frontend.

## 4. Descripción del Proyecto
La plataforma permite a los usuarios navegar por un catálogo de productos, añadirlos a un carrito y gestionar sus perfiles. Los administradores tienen acceso a un panel privado para la gestión total del inventario y usuarios.

## 5. Motivación del Proyecto
Desarrollar una solución robusta que aplique los principios de Spring Data JPA y la arquitectura de componentes de Angular, enfocada en un sector con alta demanda técnica como es la informática.

## 6. Stack Tecnológico
*   **Backend**: Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA, Hibernate.
*   **Frontend**: Angular 17, TypeScript, Bootstrap 5, RxJS.
*   **Base de Datos**: MariaDB / MySQL.
*   **Herramientas**: Maven, Node.js, Git.

## 7. Análisis de Requisitos
### Actores y Roles
*   **Usuario Invitado**: Ver productos, registrarse.
*   **Usuario Cliente**: Ver productos, gestionar carrito, realizar pedidos.
*   **Administrador**: Gestión de productos (CRUD completo), subida de imágenes, gestión de usuarios.

### Requisitos Funcionales (RF)
*   RF1: Registro e inicio de sesión seguro (JWT).
*   RF2: Listado dinámico de productos.
*   RF3: Gestión de carrito de compras (añadir/quitar/total).
*   RF4: Panel de administración para inventario.
*   RF5: Subida de imágenes para productos.

### Requisitos no Funcionales (RNF)
*   RNF1: Seguridad mediante tokens expirables.
*   RNF2: Interfaz responsiva (móvil/desktop).
*   RNF3: Persistencia de datos íntegra con claves foráneas.

## 8. Diseño Preliminar
### Apariencia
Diseño moderno basado en una paleta de colores oscuros (Dark Mode) para el catálogo informático, utilizando tarjetas interactivas y componentes de navegación claros.

### Base de Datos
La base de datos `ProyectoBD` está normalizada para evitar redundancias.
*   **Diagrama ER**: Relaciones 1:N entre Usuarios y Pedidos, y N:M entre Productos y Cestas.

---

## Explicación Técnica de la API (Backend)
La API ha sido desarrollada bajo una arquitectura de capas:
1.  **Entidades**: Mapeo directo a tablas SQL usando JPA ([Producto.java](file:///c:/Users/W11/Desktop/proyApi/a/tienda-api/src/main/java/com/example/tiendaapi/entities/Producto.java)).
2.  **Repositorios**: Interfaces que extienden `JpaRepository` para operaciones CRUD automáticas.
3.  **DTOs & Mappers**: Desacoplamiento de la base de datos y la vista ([ProductoDTO.java](file:///c:/Users/W11/Desktop/proyApi/a/tienda-api/src/main/java/com/example/tiendaapi/dtos/ProductoDTO.java)).
4.  **Servicios**: Lógica de negocio, incluyendo la gestión de archivos para imágenes ([ProductoService.java](file:///c:/Users/W11/Desktop/proyApi/a/tienda-api/src/main/java/com/example/tiendaapi/services/ProductoService.java)).
5.  **Controladores**: Endpoints REST protegidos con `@PreAuthorize` ([ProductoController.java](file:///c:/Users/W11/Desktop/proyApi/a/tienda-api/src/main/java/com/example/tiendaapi/controllers/ProductoController.java)).
6.  **Seguridad**: Configuración de `SecurityFilterChain` para gestionar JWT y CORS ([SecurityConfig.java](file:///c:/Users/W11/Desktop/proyApi/a/tienda-api/src/main/java/com/example/tiendaapi/config/SecurityConfig.java)).

## Explicación de la Página de Inicio (Frontend)
El catálogo principal ([home.ts](file:///c:/Users/W11/Desktop/proyApi/a/tienda-frontend/src/app/features/home/home.ts)) es el corazón del frontend:
*   Consume el servicio de productos para mostrar una galería dinámica.
*   Utiliza **Pipes** para el formato de moneda (EUR).
*   Permite la interacción inmediata con el **CartService** para añadir productos sin recargar la página.
*   Muestra badges dinámicos según el tipo de producto (Dispositivo/Complemento).

---

## 9. Planificación y Gestión
*   **Resumen temporal**: Desarrollo incremental por sprints (Backend -> Security -> Frontend -> Admin).
*   **Entregables**: API funcional, App Angular, Script SQL, Documentación.
*   **Riesgos**: Gestión de tokens caducados, inconsistencia de stock.

## 10. Conclusión y Postmortem
El proyecto cumple satisfactoriamente con los requisitos de una tienda informática moderna. Como mejora futura, se plantea la integración de una pasarela de pago real (Stripe/PayPal) y un sistema de chat en vivo.
