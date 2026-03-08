## Mensaje para quienes continúan el proyecto

Hola.  

He recibido información sobre ustedes, así que supongo que su **anteproyecto fue aprobado** y que continuarán con el desarrollo de este proyecto.

### Sobre la arquitectura del proyecto

Es importante tener en cuenta que el sistema maneja una **arquitectura relativamente compleja**, por lo que en la última fase del desarrollo se inició un proceso de **refactorización del código** con los siguientes objetivos:

- **Reducir el acoplamiento del sistema**
- **Mejorar la mantenibilidad**
- **Facilitar futuras ampliaciones**

### Cambios importantes en la refactorización

Durante este proceso se tomaron algunas decisiones de diseño:

#### 1. Evitar relaciones de entidades directamente en Java
Se busca **dejar de manejar relaciones complejas entre entidades desde Java** (sobretodo joins que pueden convertirse en ciclos infinitos), ya que:

- Estas relaciones suelen manejarse **de forma más clara y controlada desde SQL**.
- Cuando se gestionan exclusivamente desde Java, el sistema puede volverse **más difícil de mantener y depurar**.

#### 2. Reducir el acoplamiento entre servicios
También se está trabajando para que los **servicios sean lo más independientes posible**.

Aunque este enfoque implica **escribir más código**, ofrece varias ventajas:

- Permite **modificar o corregir funcionalidades sin afectar otros componentes**.
- Facilita **extender el sistema en el futuro**.
- Mejora la **mantenibilidad general del proyecto**.

Este enfoque sigue una filosofía similar a **aplicar principios de la arquitectura hexagonal imagina usar microservicios dentro de una arquitectura monolítica**.

### Colaboración

Si el proyecto **no va a ser un fork**, me gustaría seguir colaborando en el desarrollo y mantenimiento de las bases del sistema.