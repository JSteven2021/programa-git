\# DOCUMENTACIÓN DE LA API REST – TENISTORE



\## 1. Introducción



El presente documento describe la API REST desarrollada para el proyecto TeniStore, una aplicación web orientada a la gestión de productos de una tienda de tenis



La API permite realizar operaciones sobre los productos mediante solicitudes HTTP, facilitando la comunicación entre aplicaciones y la base de datos MySQL



\## 2. Objetivo



Desarrollar una API REST utilizando Java que permita consultar, registrar, actualizar y eliminar productos de TeniStore, manteniendo la información almacenada en la base de datos



\## 3. Tecnologías utilizadas



\- Java 21

\- Apache Tomcat 10.1.61

\- Maven

\- MySQL

\- JDBC

\- Gson para el manejo de datos JSON

\- Jakarta Servlet

\- Git y GitHub



\## 4. Dirección principal de la API



`http://localhost:8080/tenistore/api/productos`



\## 5. Servicios disponibles



\### 5.1. Listar productos



\- Método: GET

\- Ruta: `/api/productos`

\- Función: consultar todos los productos registrados

\- Respuesta: lista de productos en formato JSON



\### 5.2. Consultar producto por ID



\- Método: GET

\- Ruta: `/api/productos/{id}`

\- Función: buscar un producto específico mediante su identificador

\- Ejemplo: `/api/productos/10`



\### 5.3. Registrar producto



\- Método: POST

\- Ruta: `/api/productos`

\- Función: registrar un nuevo producto en la base de datos

\- Formato de envío: JSON



Ejemplo de datos:



```json

{

&#x20; "categoria": "deportivo",

&#x20; "codigo": "API003",

&#x20; "color": "Negro",

&#x20; "descripcion": "Producto de prueba de la API",

&#x20; "genero": "Unisex",

&#x20; "marca": "Nike",

&#x20; "material": "Textil",

&#x20; "nombre": "Tenis prueba API",

&#x20; "precio": 250000,

&#x20; "stock": 5,

&#x20; "talla": 40

}

```



\### 5.4. Actualizar producto



\- Método: PUT

\- Ruta: `/api/productos/{id}`

\- Función: modificar la información de un producto existente

\- Ejemplo: `/api/productos/12`



\### 5.5. Eliminar producto



\- Método: DELETE

\- Ruta: `/api/productos/{id}`

\- Función: eliminar un producto mediante su identificador

\- Ejemplo: `/api/productos/12`



\## 6. Estructura de los datos



Los productos manejan los siguientes campos:



\- id: identificador del producto

\- categoria: categoría

\- codigo: código del producto

\- color: color

\- descripcion: descripción

\- genero: género

\- marca: marca

\- material: material

\- nombre: nombre del producto

\- precio: precio

\- stock: cantidad disponible

\- talla: talla



\## 7. Pruebas realizadas



Durante el desarrollo se realizaron pruebas de los servicios de la API utilizando PowerShell y el navegador



| Método | Prueba | Resultado |

|---|---|---|

| GET | Listar productos | Correcto |

| GET | Consultar por ID | Correcto |

| POST | Registrar producto | Correcto |

| PUT | Actualizar producto | Correcto |

| DELETE | Eliminar producto | Correcto |



Se creó un producto de prueba con el código API003 y se verificó su registro, actualización y eliminación. Finalmente, se comprobó que los otros dos productos continuaban registrados



\## 8. Conclusión



Con el desarrollo de esta API REST se logró implementar la comunicación entre Java y MySQL para administrar los productos de TeniStore. Se comprobaron las operaciones principales de consulta, creación, actualización y eliminación, obteniendo resultados satisfactorios durante las pruebas realizadas

