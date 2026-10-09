# 📥 Bandeja de entrada — pega aquí la próxima clase

> **Flujo:** 1) borra lo de abajo y pega el resumen/transcripción de la clase (con los `[mm:ss]` si los trae) · 2) pega también capturas si las hay · 3) dile a Claude **"procesa la clase"** · 4) Claude crea `NN-nombre.md`, actualiza el [índice](../README.md) y deja esta bandeja vacía.

**Número de clase:** _(ej. 02)_
**Título (opcional):** _
**Notas mías (opcional):** _

---

<!-- Pega el contenido de la clase debajo de esta línea -->
Aprender a usar Claude Code desde la terminal te permite crear proyectos reales con inteligencia artificial sin salir de tu editor. Aquí verás cómo configurar el modelo, activar comandos de voz, conectar GitHub y administrar tu ventana de contexto, pensado para quienes empiezan a programar con IA y quieren resultados rápidos.

El punto de partida es simple: una carpeta vacía llamada Perseverancia y un editor de código como Cursor o VS Code. Desde la terminal integrada se hace el llamado a Claude Code, y ahí aparece el ícono, la versión y el modelo activo. En el ejemplo se corre Opus 5.5 con un millón de ventana de contexto y el esfuerzo en high, dentro de una cuenta de tipo team [00:14].

Qué son los slash commands en Claude Code

Los slash commands son el corazón de la interacción con la herramienta. Escribes un / y se despliega una lista de comandos disponibles.

Con ellos puedes cambiar la configuración de color de tu instancia, salir con /exit, exportar o acelerar tareas. Uno especialmente útil para principiantes es el de voz [01:30].

¿Para qué sirve el comando de voz en Claude Code? Permite dar instrucciones hablando en lugar de escribir. Es más rápido expresar tu intención en voz alta, y se activa oprimiendo la tecla de espacio una vez habilitado.

Mucha gente usaba soluciones externas como Super Whisper para dictar comandos. Ahora Claude ya soporta voz de forma nativa, así que comunicarte con tu instancia se vuelve mucho más ágil.

Cómo cambiar el modelo y el esfuerzo

El comando /model abre una ventana con todos los modelos disponibles [02:50]. En el ejemplo se elige un Sonnet 5.5, descrito como un modelo balanceado, nuevo y eficiente para tareas sencillas. Al seleccionarlo aparece una barra que indica el contexto disponible: en ese momento, 85% de espacio libre para conversar.

El comando /effort controla cuánto piensa la IA [07:20]. Y aquí viene lo interesante: el esfuerzo define el presupuesto de cómputo que le das al modelo.

Esfuerzo low: respuestas más rápidas, ideal para tareas sencillas.
Esfuerzo high o max: gasta muchos más tokens pensando y experimentando con hipótesis, solo para problemas muy complejos. Max puede costar casi cinco veces lo de un medium.
Ultra Code: toma el esfuerzo de max y genera clones de sí mismo, subagentes que orquesta para distintas tareas.
La recomendación es clara: reserva los esfuerzos altos para lo verdaderamente difícil y usa low cuando la tarea es simple.

Por qué el archivo claude.md es la memoria del proyecto

El archivo CLAUDE.md es lo primero que Claude Code lee al arrancar. Funciona como un readme, pero escrito para la inteligencia artificial.

Ahí defines quién es el asistente y de qué trata el proyecto. En otros modelos este archivo se llama agents.md, y Claude también soporta ese nombre en la sección del proyecto [05:40].

¿Qué es el archivo claude.md? Es el archivo de memoria que Claude Code lee primero para entender el contexto del proyecto. Describe el objetivo y guía el trabajo del modelo, similar a un readme pero orientado a la IA.

En la demostración se crea un CLAUDE.md con un párrafo conciso: ayudar a construir una simulación en 3D del Perseverance Rover de la NASA que explora Marte. El modelo llama a la herramienta Write y escribe el archivo en mayúsculas, que es el formato correcto [08:50]. Antes de eso, la herramienta Read permite ver un diff donde lo rojo es lo que se borró y lo verde lo nuevo que se va a escribir [06:10].

Cómo detener y navegar prompts en Claude Code

Si el modelo arranca una tarea que no querías, lo detienes con la tecla Escape. Presionando Escape dos veces puedes volver a prompts anteriores para corregir el rumbo [06:40]. El comando /clear limpia el contexto para empezar con una ventana limpia.

Cómo conectar Claude Code con GitHub

GitHub es donde viven los repositorios del código que escribes. Conectarlo a tu instancia tiene pasos concretos [10:10].

Instala Brew en tu Mac o Windows.
Conéctate a GitHub autorizando con tu login de GitHub.
Ejecuta /install github app para que Claude Code complete la conexión automáticamente.
También puedes pedirlo en lenguaje natural: decirle a Claude que se conecte con tu GitHub, darle el login y dejar que él haga el resto. Al correr /install github app aparece un menú para elegir el repositorio, en este caso el llamado Perseverancia, y con el permiso concedido queda todo listo para crear y guardar código.

Cómo administrar el contexto y los tokens

Mantener el contexto limpio es clave para obtener buenos resultados. La recomendación principal es una tarea por chat.

Si trabajas en una funcionalidad, manténla en ese chat y no mezcles con debugging de otra parte del código. Entre más organizado y limpio esté el chat en una sola tarea, mejor aprovechas la ventana de contexto [12:30].

El comando /usage muestra cuánto gastas en contexto y tokens, desglosado por modelo [13:40]. En el ejemplo, la sesión usaba 16% del presupuesto, la semana llevaba 72% y el reset ocurría el 3 de octubre a las 5:00 p.m. También detalla el consumo por skills (1%) y subagentes (1%), más servidores de MCP y conectores como Claude in Chrome, que explican por qué el uso no arranca en cero.

¿Cómo mantener el contexto de forma eficiente en Claude Code? Trabaja una sola tarea por chat y usa el comando /usage para monitorear tu consumo de tokens. Un chat limpio y enfocado conserva mejor la ventana de contexto.

Qué es el status line y cómo personalizarlo

El comando /status line crea ese pequeño dashboard que aparece en la parte inferior de la terminal [15:30]. Lo interesante es que llama a un background agent que se encarga solo de esa tarea, sin afectar tu ventana de contexto.

Ese dashboard puede mostrar la carpeta actual, el modelo en uso, el esfuerzo con un emoticon, una barra de uso de contexto y otra de presupuesto de tokens de la sesión de cinco horas, con el tiempo para resetearse. Y puedes sumarle elementos: en la demo se le pidió agregar un reloj con la hora local, y el subagente lo añadió con un emoticon y la hora exacta [16:40].

¿Qué comando de Claude Code vas a probar primero? Cuéntanos en los comentarios cómo te fue configurando tu propio dashboard.


###


