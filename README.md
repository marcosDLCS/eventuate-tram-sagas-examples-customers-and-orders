# URJC Máster Cloud Apps<!-- omit in TOC -->

## Módulo III - Aplicaciones nativas de la nube - Aplicaciones nativas de la nube<!-- omit in TOC -->

### Práctica 1. Microservicios. Sagas<!-- omit in TOC -->

#### Tabla de contenidos<!-- omit in TOC -->

- [Instrucciones](#instrucciones)
- [Enunciado](#enunciado)
  - [Básico (para 7.5)](#b%c3%a1sico-para-75)
  - [Opcional (para el 10)](#opcional-para-el-10)
- [Formato de entrega](#formato-de-entrega)

#### Instrucciones

Aquí el [repositorio original](https://github.com/eventuate-tram/eventuate-tram-sagas-examples-customers-and-orders).

La documentación original se puede consultar en este [enlace](./ORIGINAL_README.adoc).

- No se necesita tener Gradle instalado ya que se hace uso del wrapper
- Java 8 es necesario

En primer lugar, para construir la aplicación:

```console
$ ./gradlew assemble
...
```

A continuación, lanzamos los servicios usando [Docker Compose](https://docs.docker.com/compose/):

```console
$  export DOCKER_HOST_IP=<IP>
...
$ ./gradlew mysqlComposeBuild
...
$ ./gradlew mysqlComposeUp
...
```

Nota:

1. Para lanzar la versión que usa Postgres: `./gradlew postgresComposeBuild` y `./gradlew postgresComposeUp`
2. Hay que definir `DOCKER_HOST_IP` antes de lanzar las tareas de *Docker Compose*.

Acceder a *[guide to setting DOCKER_HOST_IP](http://eventuate.io/docs/usingdocker.html)* para más información.

Ejemplo para Mac:

```console
$ sudo ifconfig lo0 alias 10.200.10.1/24  # (where 10.200.10.1 is some unused IP address)
...
$ export DOCKER_HOST_IP=10.200.10.1
...
```

Usando la aplicación (asumiendo ip **10.200.10.1**):

- **Orders Service** - http://10.200.10.1:8081/swagger-ui.html
- **Customers Service** - http://10.200.10.1:8082/swagger-ui.html
- **Products Service** - http://10.200.10.1:8083/swagger-ui.html

Gateway (asumiendo ip **10.200.10.1**):

- **Gateway Service** - http://10.200.10.1:9999/

Proyecto de **Postman**:

- [urjc_maca_sagas.postman_collection](./urjc_maca_sagas.postman_collection)

##### Partes completadas

- [x] Creación de **servicio de Productos**
- [x] Ampliación de las APIs de consulta
- [x] Creación del **servicio de Gateway**
- [x] Enrutado de las peticiones a través del servicio de Gateway
- [ ] Agregación de información de 2 **endpoints* en 1 sólo

#### Enunciado

El objetivo de esta práctica consiste en implementar una aplicación derivada de un ejemplo implementado con el *framework **Eventuate***. En concreto, se pide lo siguiente:

##### Básico (para 7.5)

- Añadir un **nuevo microservicio** a la [aplicación Orders - Consumers](https://github.com/eventuate-tram/eventuate-tram-sagas-examples-customers-and-orders).
- Este nuevo microservicio se encargará de **controlar el stock del producto que pide el usuario**, de forma que si el stock no es suficiente, el pedido se rechazará.
- Queda a libertad del alumno ampliar los datos del pedido para poder referenciar el producto y su stock.
- Los productos tendrán como **dato extra su nombre**.
- Será necesario **implementar la API REST** para el microservicio de productos de forma que se pueda **consultar su stock**.

##### Opcional (para el 10)

- Se deberá incluir un **API Gateway basado en Spring API Gateway** que permita obtener, con una única petición, información del pedido e información del producto en un pedido concreto
- Además se incluirá en el API Gateway el **enrutado de los servicios order, consumer y product**.

#### Formato de entrega

La práctica se entregará teniendo en cuenta los siguientes aspectos:

- La práctica se entregará como un fichero .zip con el código del los diferentes servicios y los ficheros auxiliares. El nombre del fichero .zip será el correo URJC del alumno (sin @alumnos.urjc.es).

Las prácticas se podrán realizar de forma individual o por parejas. En caso de que la práctica se haga por parejas:

- Sólo será entregada por uno de los alumnos
- El nombre del fichero .zip contendrá el correo de ambos alumnos separado por guión. Por ejemplo p.perezf2019-z.gonzalez2019.zip
