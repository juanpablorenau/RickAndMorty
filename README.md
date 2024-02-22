# RickAndMorty

1. ¿Qué es el DataBinding?  

DataBinding permite vincular expresiones y variables a vistas XML, de modo que cuando los datos cambien, las vistas se actualicen automáticamente. Ésto ayuda a eliminar el código repetitivo y facilita mantener la interfaz de usuario de su aplicación sincronizada con los datos. El DataBinding es parte de la biblioteca de Android Jetpack, que es un conjunto de bibliotecas, herramientas y orientación para el desarrollo de aplicaciones de Android.

2. ¿Qué son las corrutinas? ¿Y las funciones suspend?  

Las corrutinas son una característica del lenguaje de programación Kotlin que le permite escribir código asincrónico de una manera más legible y concisa, sin usar devoluciones de llamada ni bloquear el hilo principal. En Android, las corrutinas se pueden usar para tareas como solicitudes de red, operaciones de bases de datos o cualquier otra operación que requiera mucho tiempo y que deba realizarse fuera del hilo principal.

Las funciones suspend son un tipo especial de funciones en Kotlin que se pueden pausar y reanudar más tarde, sin bloquear el hilo de llamada. Las funciones suspend se declara usando la palabra clave "suspend" al principio de una función y se puede llamar desde dentro de una rutina. Cuando se llama a una función suspend, puede devolver el control a la rutina de llamada, lo que permite que se ejecuten otras tareas mientras está suspendida. Cuando se completa la operación que realiza la función de suspensión, puede reanudar su ejecución y devolver el resultado a la rutina de llamada. Esto le permite escribir código asincrónico que se ve y se comporta como un código secuencial normal, lo que facilita su lógica y el mantenimiento.

3. Tenemos una app que llama a varios servicios REST para obtener  información de un usuario. El contrato de dicho servicio ha cambiado y ahora  algunos parámetros nos llegan con un formato diferente. ¿Qué ventajas  aporta la arquitectura de MVVM en este caso?  

MVVM separa la lógica de presentación de la lógica comercial, lo que facilita la gestión de cambios en el contrato de los servicios REST sin afectar la interfaz de usuario. En este caso, únicamente habría que cambiar los data class, que servían como modelo de datos de los elementos de la Api, y sus funciones .toModel() que transforman el modelo de datos de la Api al modelo de datos de la aplicación.

4. ¿Qué es un lateinit y en qué casos tiene sentido su uso?  

Lateinit es un modificador en Kotlin que se puede usar con propiedades (variables o campos) para indicar que la propiedad se inicializará más adelante en el código. Se utiliza para evitar las excepciones de puntero nulo que pueden ocurrir cuando se accede a una propiedad antes de que se haya inicializado. Algunos casos de uso comunes para lateinit incluyen:

_ Inicializar vistas en una actividad o fragmento de Android: puede usar lateinit para evitar inicializar vistas en el constructor o en el método onCreate, lo que puede hacer que el código sea más legible y fácil de mantener.

_ Inicializar dependencias en un modelo o presentador: puede usar lateinit para retrasar la inicialización de las dependencias hasta que se necesiten, lo que facilita probar el modelo o el presentador de forma aislada.

_ Inicializar propiedades que tienen un proceso de inicialización no trivial: si una propiedad requiere algún cálculo o configuración complejo antes de que pueda inicializarse, puede usar lateinit para retrasar su inicialización hasta que realmente se necesite.

<img width="366" alt="image" src="https://github.com/juanpablorenau/RickAndMorty/assets/86953862/8ded0e49-c4f5-4fd3-9a18-f02a07912ada">

