

<h1 align="center">Plataforma Hotel - Backend</h1>

<!-- Imagen central -->
<p align="center">
  <img src="https://github.com/user-attachments/assets/11392368-7809-4645-a0d9-7172cd600b0c" alt="logo" width="760"/>
</p>
<h2>Introducción</h2>
<p>
  <strong>Royal Suotes Hotel</strong> es el backend desarrollado con Spring Boot para la plataforma hotelera. Proporciona una API REST segura para gestionar usuarios, roles, habitaciones y reservas.
</p>

<ul>
  <li><strong>Fácil de usar</strong></li>
</ul>
<blockquote>
  Construido con <strong>Spring Boot</strong>, este backend es rápido de configurar y ejecutar. Gracias a su arquitectura RESTful y buenas prácticas de desarrollo, puedes enfocarte en las reglas de negocio sin preocuparte por la infraestructura técnica.
</blockquote>

<ul>
  <li><strong>Escalable y mantenible</strong></li>
</ul>
<blockquote>
  Gracias a su estructura modular, <strong>Plataforma Hotel</strong> es fácilmente escalable y mantenible. Puedes extender la lógica del sistema para incluir nuevas entidades, validaciones y servicios según las necesidades del hotel.
</blockquote>


<h2>✨ Características principales</h2>
<ul>
  <li><strong>Seguridad integrada</strong> con JWT y control de roles</li>
  <li>Gestión completa de <strong>habitaciones</strong>, <strong>servicios</strong> y <strong>reservas</strong></li>
  <li><strong>Arquitectura limpia</strong> y modular (Controladores, Servicios, Repositorios)</li>
  <li>Fácil integración con el frontend desarrollado en Angular</li>
</ul>

## 💻 Tecnologías utilizadas

### Frontend (Angular)
- Angular 17
- TypeScript
- Angular Router
- Angular Reactive Forms
- Bootstrap 5 (para el diseño responsivo)

### Backend (Spring Boot) 
- Spring Boot 3 (Java 17)
- Spring Data JPA para persistencia de datos
- Base de datos MySQL
- Seguridad y autenticación con **JWT (JSON Web Tokens)**
- Control de usuarios y roles (ADMIN / CLIENT)
- Controladores REST para exponer la API
- Manejo de excepciones y validaciones personalizadas
- CORS
- Railway

### Herramientas adicionales
- Visual Studio Code
- Postman (para pruebas de API)
- Git y GitHub
- Angular CLI


### Requisitos previos

- Java 17 instalado
- Maven instalado
- MySQL en ejecución y configurado

<h2>🚀 Instalación</h2>
<p>
  Sigue estos pasos para instalar el proyecto <strong>PlataformaHotel</strong> (Backend) en tu entorno local:
</p>

<ol>
  <li>
    <p><strong>Clona el repositorio</strong></p>
    <pre><code>git clone https://github.com/joseBC377/PlataformaHotel.git
cd PlataformaHotel</code></pre>
  </li>

  <li>
    <p><strong>Importa el proyecto en tu IDE favorito</strong></p>
    <p>Puedes usar <code>IntelliJ IDEA</code>, <code>Eclipse</code> o cualquier editor compatible con <strong>Spring Boot</strong> y <strong>Maven</strong>.</p>
  </li>

  <li>
    <p><strong>Configura la base de datos</strong></p>
    <p>Abre el archivo <code>src/main/resources/application.properties</code> y ajusta los valores según tu entorno:</p>
    <pre><code>
spring.datasource.url=jdbc:mysql://localhost:3306/plataforma_hotel
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
    </code></pre>
  </li>

  <li>
    <p><strong>Ejecuta el siguiente comando para compilar y descargar dependencias</strong></p>
    <pre><code>./mvnw clean install</code></pre>
    <p>O si estás en Windows:</p>
    <pre><code>mvnw.cmd clean install</code></pre>
  </li>
</ol>

<h2>🏃‍♂️ Ejecución</h2>
<p>
  Sigue estos pasos para ejecutar el proyecto <strong>PlataformaHotel</strong> en tu entorno local:
</p>

<ol>
  <li>
    <p><strong>Ejecuta la aplicación</strong></p>
    <pre><code>./mvnw spring-boot:run</code></pre>
    <p>O también puedes ejecutarlo directamente desde tu IDE (opción "Run Application").</p>
  </li>

  <li>
    <p>Una vez ejecutado, accede a la API en:</p>
    <pre><code>http://localhost:8081</code></pre>
  </li>
</ol>

<p>
  Asegúrate de tener instalado Java 17 o superior y MySQL corriendo localmente para que la aplicación funcione correctamente.
</p>

## 🔄 Conexión con el Frontend (Angular)
Este backend está diseñado para funcionar junto con el frontend del proyecto hotelero desarrollado en Angular.
Asegúrate de que el frontend esté corriendo correctamente en el puerto correspondiente (`4200`) y que las peticiones al backend estén configuradas con la URL `http://localhost:8081`.
📎 [🔗 Ver frontend en GitHub](https://github.com/joseBC377/PlataformaHotelFrontend)

## ✅ Recomendaciones de conexión
- Verifica que ambos proyectos (backend y frontend) estén ejecutándose al mismo tiempo.
- En Angular, asegúrate de que los servicios estén apuntando a `http://localhost:8081/api/...` según tus rutas definidas en el backend.

<pre><code class="language-java">
 http://localhost:4200
</code></pre>

<h2>🔧 Uso del sistema (Backend)</h2>
<p>Una vez ejecutado el backend en Spring Boot, podrás:</p>
<ul>
  <li>Proveer endpoints para registro y autenticación de usuarios (JWT).</li>
  <li>Gestionar habitaciones, reservas y usuarios mediante API REST.</li>
  <li>Interactuar con una base de datos MySQL (u otra definida en <code>application.properties</code>).</li>
  <li>Aplicar reglas de seguridad con Spring Security.</li>
  <li>Validar datos y retornar respuestas en formato JSON.</li>
</ul>

<h2>📁 Estructura del proyecto -  Backend</h2>

<pre>
PlataformaHotel/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── plataforma/
│   │   │           └── hotel/
│   │   │               ├── controller/     # Controladores REST
│   │   │               ├── service/        # Lógica de negocio
│   │   │               ├── model/          # Entidades (JPA)
│   │   │               ├── repository/     # Interfaces JPA (acceso a datos)
│   │   │               ├── config/         # Configuración de seguridad (JWT, filtros,CORS)
│   │   │               └── PlataformaHotelApplication.java  # Clase principal
│   │   └── resources/
│   │       ├── application.properties      # Configuración de conexión (DB, puerto)
│   │       └── static/                     # Archivos estáticos si se usan
│
├── pom.xml                                 # Dependencias del proyecto (Maven)
└── README.md                               # Documentación del backend
</pre>




