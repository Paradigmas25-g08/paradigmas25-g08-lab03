# Aplicacion para parseo de feed y computo de entidades paradigmas25-g08-lab03

##Configuracion del entorno y ejecucion
La aplicacion utiliza maven para la compilacion, ejecucion y manejo de dependencias.

Para filtrar por la heuristica QuickHeuristic.
```sh
    make all
```
En caso de querer usar la otra heuristica disponible(RandomHeuristic), modificar en el Makefile HEURISTIC=q por HEURISTIC=r y utilizar el mismo comando. 
Como resultado se obtienen dos tablas, la primera contiene todas las entidades, su frecuencia, su categoria y su tema respectivamente. La segunda
contiene un conteo de la cantidad de entidades nombradas por su categoria.

Para solo parsear los feed y obtener los articulos por consola.
```sh
    make build
    make run
```
Como resultado se obtienen todos los articulos de los feed impresos de manera amigable, con su titulo, link, fecha de publicacion y un resumen.

##Decisiones de diseño
Dentro del content de algunos articulos se encuentran etiquetas html que empeoran la legibilidad del texto, por lo cual esto se removio del texto del cual 
se extraen las entidades, aunque por los diversos formatos del texto se computan algunas entidades erroneas.
Tambien modificamos los atributos de NamedEntity, ya que habia una jerarquia de clases tanto en temas como categorias, se decidio entonces agregar el atributo theme
NamedEntity en vez de que theme herede de NamedEntity. De una forma parecida de plantearon las categorias donde por ejemplo person si extiende NamedEntity
pero title, FirstName, y LastName son utilizados como tipos de datos y atributos de la clase Person.

##Conceptos Importantes
1. Dado un archivo .json de subscripciones, el modulo SubscriptionParser lo parsea para obtener una lista de subscripciones, que se utiliza para obtener una lista de urls unicos mediante los url base y sus parametros.
Una vez obtenida esa lista creamos un cluster de Spark donde cada worker se encargara de hacer la debida request a un url de la lista y parsear 
lo obtenido a un feed con sus articulos, haciendo uso de RssParser, asi el master recolecta todos los feed procesados.
Luego se procede a obtener una lista de todos los articulos de todos los feeds, y enviamos a cada worker un articulo para que procese sus entidades.
Una vez computadas todas la entidades se agrupan y se reduce esta agrupacion sumando las frecuencias de las entidades del mismo nombre repetidas en distintos articulos.
Finalmente con estos datos se imprime la tabla de entidades.

En caso de solo querer los feed, el flujo es el mismo solo que en vez de procesar las entidades se imprimen los feeds.

2. Se decidio usar Apache Spark ya que es un framework muy popular para programacion distribuida, que resuelve el problema de trabajar con grande volumenes de datos,
en nuestro caso particular surgio de la idea de que la cantidad de subscripciones crecio y esto aumento la latencia debido a que se hacen muchas mas request asi como el costo
computacional de procesar la entidades de muchos mas articulos. Fue precisamente en estos dos puntos en donde nos beneficiamos de este tipo de programacion al hacer
estas tareas en paralelo.

3. Las principales ventajas, como se menciono en el punto anteriro, fueron ganar tiempo de computo, es decir, lo que antes se realizaba sin spark ahora al crecer mucho en tamaño
estaba tardando demasiado tiempo. EN cambio al utilizar Spark el tiempo de ejecucion disminuyo significativamente, justamente al aprovechar hacer estas tareas secuenciales en
paralelo. Como desventaja se puede pensar que se necesita de mucho hardware para realmente sacarle provecho al framework, es decir que si bien es util en nuestro caso, si se
manejara mucha mas data y el costo computacional de la extraccion de los datos fuese mas alto, se necesitaria un cluster real de varias computadoras para poder realizar la tarea
eficientemente de manera no local.

4. En este Laboratorio se abarca el concepto de inversion de control, este refiere a que en lugar de que el propio código de la aplicación “mande” explícitamente sobre el flujo de
ejecución y la invocación de componentes, es el framework o entorno de ejecución quien decide cuándo y cómo llamar a nuestro código. Nosotros simplemente “registramos” o
“entregamos” trozos de lógica con los cuales le especificamos a Spark el "que" hacer (a traves de metodos como map() o reduceByKey()) que el framework invocará en el momento
oportuno(al invocar collect() u otras acciones como count() o saveAsTextFile()) es aqui donde el control de como y en que orden se procesan los datos pasa al control de Spark.
En particular los componentes de los que delegamos el control son los encargados de ejecutar las request, parsear los feed y computar las entidades de articulos. 

5. Consideramos que spark requiere que el codigo original tenga una integracion loose coupling, ya que de esta manera se puede sacar el maximo provecho a spark haciendo que solo
el modulo en el cual se lleva a cabo la sesion de spark es la unica que conoce que lo utilizando mientras que los modulos que procesan los datos o realizan transformaciones 
sobre estos funcionan son clases apartes, separando el que hacer con los datos del como y cuando hacerlo.

6. No afecto la estrutura significativamente, ya que nuestro laboratorio 2 tenia un acoplamiento debil lo cual permitio que los cambios minimos que se hicieron fueron para que
spark pudiera operar con algunas de nuestras clases (haciendolas serializables, trayendo los import correctos y agregando las dependencias correspondientes) y por desiciones a la hora de parsear nuevos tipos de xml.
 
