# Authorizer

Aplicación de consola para creación y autorización de transacciones a una cuenta.
* [Enunciado](https://drive.google.com/open?id=0B3VV0CP-a1SsVGk2UkpxdG0yQnluVENsSVl1d2RETUM5VUh3)

## Contenido

* Aplicación de consola que a partir de un input crea la cuenta y maneja las transacciones en memoria
* Test unitarios y funcionales

## Tecnologías utilizadas

* Java 8
* Gradle
* Spring Boot (2.1.10.RELEASE)
* JSON Jackson para la serialización/deserialización de objetos JSON.

## Estructura y comentarios

El proyecto fue estructurado y diseñado utilizando el patrón Clean Architecture. 
La lógica de negocio está centralizada en los casos de uso CreateAccount y ProcessTransaction. Cada uno de ellos tiene
a su vez, un caso de uso para las validaciones correspondientes.

Al ejecutar la aplicación se puede ingresar de a una linea, como así también en un stream de comandos.
