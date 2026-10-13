# 📥 Bandeja de entrada — pega aquí la próxima clase

> **Flujo:** 1) borra lo de abajo y pega la transcripción de la clase (con los `[mm:ss]` si los trae) · 2) pega también capturas si las hay · 3) dile a Claude **"procesa la clase"** · 4) Claude crea `NN-nombre.md`, actualiza el [índice](../README.md) y deja esta bandeja vacía.

**Sección / número de clase:** _(ej. 01)_
**Título (opcional):** _
**Notas mías (opcional):** _

---

Este es un momento importante.

Este es el comienzo de una aventura de

ocho semanas que no te vas a creer.

Tanto si eres totalmente nuevo en la programación,

como si eres un ingeniero con experiencia, o

si ya has hecho este curso, has

visto que lo he actualizado y has

vuelto para ver las últimas novedades, tengo

algo muy interesante preparado para todos. Empecemos.

Me llamo Ed Donner.

Soy cofundador y director técnico de aistartupnebula.io.

Y antes de empezar, es habitual en

este tipo de situaciones explicaros paso a

paso lo que vamos a llevar a

cabo durante las próximas ocho semanas: los

objetivos, la estructura, los proyectos y cómo

nos prepararemos para alcanzar el éxito.

Pero no lo haremos. Estoy mintiendo.

Eso no es lo mío.

Quienes ya han asistido a mis cursos saben

que me gusta empezar con la práctica.

Vamos al grano.

En los primeros minutos de este curso, vamos

a probar los modelos de lenguaje grande (LLM),

que es de lo que trata todo esto.

Y habrá tiempo de sobra para volver

más tarde y hacer esas otras cosas.

Bueno, lo primero es lo primero: quiero

que abras una nueva ventana del navegador,

tu navegador favorito, sea cual sea.

Y quiero que entres en esta página web.

Es github.com/ed-donner/LLM_engineering, que

es el repositorio

de este curso.

Además, el enlace aparece en los recursos del curso.

Debería ser fácil de encontrar.

Este es el repositorio.

De momento no vamos a hacer nada con eso.

Lo menciono porque, si te desplazas hacia abajo en esta primera página,

podrás ver lo que se conoce como el archivo «readme» del curso.

Y el archivo «readme» contiene toda la información que necesitas saber.

Tiene enlaces a otras cosas.

Incluye enlaces a las instrucciones de configuración.

Además, puede que se vea un poco diferente a

lo que ves aquí, ya que actualizo constantemente el

archivo «readme» para que contenga la información más reciente.

Y lo primero que vamos a

hacer es seguir esta sección sobre

la gratificación inmediata que hay aquí.

Y siempre deberías consultar,

ante todo, la documentación,

porque estará totalmente actualizada.

Así que échale un vistazo.

Y ahora vamos a empezar con nuestra primera

incursión en los modelos de lenguaje grande (LLM).

Supongo que tú, como la mayoría de

la gente del planeta, conoces ChatGPT y

has interactuado con un modelo de lenguaje

grande (LLM) a través de ChatGPT y

quizá también con Claude, Gemini y otros.

Y lo que vamos a hacer ahora es interactuar

también con otra IA y otro modelo de lenguaje

grande (LLM), pero lo haremos ejecutándolo en nuestro ordenador.

Vamos a utilizar un modelo de lenguaje

grande (LLM) de código abierto creado por

otra persona, lo instalaremos en nuestros equipos

e interactuaremos con él desde allí.

Y, de alguna manera, resulta mucho más emocionante interactuar

con una IA que tú mismo gestionas, en

lugar de una que está en la nube.

Y para ello vamos a utilizar

este fabuloso producto llamado OLAMA.

Y vamos a hacerlo ahora mismo.

Y, como recordatorio, podrás seguir

todas estas instrucciones en el

archivo «léeme», en la

sección titulada «gratificación inmediata».

Pero voy a empezar por entrar en olama.com.

Se escribe O-L-L-A-M-A.com y hay un

enlace en el archivo «readme».

Y así es como se ve.

Y, básicamente, puedes hacer lo mismo en un

PC, en un Mac o en Linux.

Va a funcionar igual en los

tres, lo cual es fabuloso.

Así pues, OLAMA, cuyo nombre es una especie

de homenaje a uno de los primeros modelos

de código abierto, llamado LLAMA, de Meta.

OLAMA es un producto que se ejecuta en tu

ordenador y que permite ejecutar un modelo de

forma muy rápida, integrada y eficiente a nivel

local, de modo que puedas interactuar con él.

Y lo primero que vas a hacer es venir aquí.

Ahora ve al botón de descarga que hay aquí. Pulsa en «Descargar».

Elige el sistema que tengas.

Pulsa en «Descargar» para tu sistema.

La descarga comenzará como de costumbre.

Y, una vez descargado,

lo abrirás haciendo clic

aquí y se abrirá.

Si utilizas un Mac, arrástralo

desde aquí hasta la carpeta

«Aplicaciones» y aparecerá allí.

Si utilizas un PC, también puedes

instalarlo de la forma habitual.

Y, una vez hecho esto, lo abres

desde la carpeta de aplicaciones, desde su

icono o desde el menú de inicio.

Abre OLAMA para que se muestre en una ventana independiente.

Y cuando lo hagas, debería

aparecer una pantalla como esta.

Es la interfaz de usuario de OLAMA con una pequeña ventana de chat.

Pero no lo vamos a usar.

Ahora vas a pulsar el botón

X, el botón de cerrar, para

cerrarlo, ya que vamos a interactuar

directamente con OLAMA en nuestro ordenador.

Y lo vamos a hacer utilizando algo

que se llama «línea de comandos».

Así que hay una forma diferente de hacerlo

en un PC y en un Mac.

Y es muy sencillo.

Y las instrucciones están en el archivo «readme».

Quizá te suene esto.

Si utilizas un Mac, ve

a «Aplicaciones» y «Utilidades» y

haz doble clic en «Terminal».

Si estás en un

PC, ve al menú

de inicio y escribe

«PowerShell» para abrir PowerShell.

Si utilizas Linux, ya sabes exactamente qué

hacer. Pero, en cualquier caso, sigue las

instrucciones para abrir una línea de comandos

y, a continuación, empezaremos a interactuar con

un modelo de lenguaje grande (LLM).

Y esto es lo que veo.

Y deberías ver algo bastante parecido.

Esto es en un Mac. En un

PC, aparecería la palabra «PowerShell», pero

es más o menos lo mismo.

Y lo primero que quiero que hagas es escribir OLAMA.

Y deberías ver una especie de pantalla de ayuda

con las cosas que puedes hacer con OLAMA.

Y si no lo ves, echa

un vistazo al archivo «readme».

Otra cosa que puedes hacer, solo para comprobarlo,

es ver si ya se está ejecutando.

Si escribes «OLAMA serve», que es la forma

de iniciarlo, debería aparecer un mensaje indicando que

la dirección ya está en uso, lo que

significa que ya se está ejecutando. Está bien.

Al hacer doble clic en

él, lo pusimos en marcha.

Y si te dice que se ha

iniciado, pues también está muy bien. Vale.

Así que tenemos OLAMA y

OLAMA está en funcionamiento.

Así que, a continuación, vamos a ejecutar OLAMA.

Vamos a hacer que descargue

un modelo de Internet y

lo ejecute en nuestro ordenador.

Y eso se hace con un sencillo comando: OLAMA run.

Y, a continuación, el nombre del

modelo que quieres que se descargue.

Y vamos a elegir un modelo creado

por Google que se llama Gemma.

Es la versión más pequeña

del Gemini, el modelo estrella.

Pero Gemma es la que han puesto a

disposición de todo el mundo de forma gratuita.

Así que escribimos «Gemma».

Se trata de G-E-M-M-A.

Vamos a utilizar Gemma 3,

que es la última versión,

y luego pondremos dos puntos.

Y después de los dos puntos,

puedes elegir el tamaño que quieras.

Y vamos a elegir un tamaño realmente

pequeño, 270 M, lo que significa 270

millones de esas cosas llamadas «parámetros», de

las que hablaremos mucho en el futuro.

Y eso puede parecer una cifra bastante elevada;

270 millones de cualquier cosa suena a mucho.

Pero, en la mayoría de los casos, estas

cifras se cuentan en miles de millones.

Así que 270 millones es, de hecho, una cifra realmente minúscula.

Y cuando pulso Intro,

ahora se descarga Gemma.

Bueno, puede que esto te lleve un par de segundos.

A mí ya se me ha descargado.

Así que estaba allí.

Podría tardar un par de minutos si

tienes una conexión a Internet más lenta.

Debería ser bastante rápido.

Y, una vez hecho esto,

tendrás un cursor como este.

Eso significa que ya estás listo para interactuar con

este modelo que se está ejecutando en tu ordenador.

Así que deberíamos mantener bajas las expectativas.

Es un modelo muy pequeño,

pero aun así, empecemos.

Voy a decir: «Hola».

Mi primer mensaje a una IA en mi ordenador. Y me dice: «Hola».

¿En qué puedo ayudarte hoy? Diré que me

llamo Ed. Y pone: «Genial. Yo soy Ed».

No, ese es mi nombre.

Perfecto. Me llamo Ed.

Mira, se trata de un modelo muy pequeño, así que

no deberías tener grandes expectativas con respecto a él.

Lo único que vamos a hacer ahora

es elegir un modelo un poco más

grande y, con suerte, mantener una conversación

más sólida con un modelo más grande.

Vamos a intentarlo.

Así que ahora deberías dejar de hablar con Jemma.

Y para hacerlo en esa línea de comandos,

tienes que pulsar Control+D, lo que te

permite salir de la conversación con Jemma.

La tecla «Control» en un Mac no

es lo mismo que la tecla «Comando».

Ten cuidado con eso. Pulsa Control+D. Muy bien.

Y ahora volvamos a la página web olama.com.

Y verás que aquí, en la

barra de navegación principal, hay modelos.

Si haces clic ahí, aparecerá una

lista con todos los modelos

de código abierto que puedes

utilizar a través de Olama.

Y hay muchos.

Estábamos jugando con Jemma 3.

Y, Jemma 3, como puedes ver, también

está disponible en muchos tamaños diferentes.

Si solo te descargas Jemma 3, obtendrás

esta versión de aquí, la más reciente,

que ocupa 3,3 gigabytes. Es bastante grande.

Y también está el que acabamos de

utilizar, que es el Jemma 3270M,

que es mucho, mucho más pequeño.

Y hay una gran variedad de tamaños diferentes.

Y puedes probarlos tú mismo si tu

ordenador tiene suficiente espacio para guardarlos.

Y luego prueba a ver cómo

un modelo más grande es capaz

de mantener una conversación más sólida.

Pero, por ahora, vamos a centrarnos en los modelos.

Y propongo que elijamos una de estas opciones;

vamos a quedarnos con PHY 3 de Microsoft.

Vamos a probar la última versión de PHY 3.

Si tu ordenador tiene la capacidad suficiente para

ello, si dispones de 2,2 gigabytes libres en

el disco duro y si es lo

suficientemente rápido, entonces tú también lo verás.

Pero voy a darle una oportunidad a PHY 3.

Veamos cómo funciona. Pues allá vamos.

Escribo «olama run PHY 3».

La primera vez, la descarga tardará unos

minutos en completarse en tu ordenador, dependiendo

de tu conexión a Internet y

de la potencia de tu ordenador.

Y lo primero que voy

a hacer es saludar.

Veamos cómo funciona.

Hola, ¿en qué puedo ayudarte

hoy? Cuéntame algo curioso.

Allá vamos, allá vamos.

¿Sabías que la Torre Eiffel de París no

es tan estable sobre sus pilares como la

gente podría pensar? Es como si pudiéramos hacer

que algunas partes de ella se balancearan hasta

seis pies. ¿Qué te parece? Cuéntame otra.

Vale, el récord mundial de comer

el mayor número de perritos calientes

en una hora lo tiene Joey

Chestnut; eso sí que creía saberlo.

Como neoyorquino, esto me pasa todos los años. Pues ahí lo tienes.

Algunas curiosidades interesantes.

Espero que hayas aprendido algunas curiosidades nuevas en la asignatura PHY 3.

Y si pruebas diferentes modelos, puede

que descubras todo tipo de curiosidades.

Pues bien, este es el modelo PHY 3.

Y ahora vamos a probar una vez más.

Así que pulso Ctrl+D para poner fin a la conversación, así de sencillo.

Y ahora voy a escribir «olama run».

Y ahora voy a elegir el modelo más

grande que pueda funcionar en mi ordenador.

Y es posible que esto no funcione en tu ordenador.

Así que no te preocupes si necesitas tener

al menos 16 megabytes de RAM en tu

ordenador para que esto funcione, y unos

20 gigabytes libres en el disco duro.

No hace falta que lo ejecutes

si no quieres, pero quiero mostrarte

cómo funciona el último modelo de

OpenAI, llamado GPT-OSS, que es

la versión de código abierto

del modelo GPT de OpenAI.

Pues bien, este es un modelo grande y

antiguo, y te lo va a descargar.

Y eso podría llevar bastante tiempo.

Además, le va a dar un buen golpe a mi ordenador. Pues ahí lo tienes. Ya está en marcha.

Contamos con GPT-OSS.

De hecho, se trata de la versión con 20 000 millones de parámetros.

Así que, una vez más, es muchas veces mayor que PHY 3, que

a su vez es 16 veces mayor que Gemma 3. 270 millones.

Pues bien, este es un modelo grande.

Lo estamos ejecutando localmente.

Vamos a interactuar con él.


##

Así que ahora estoy trabajando con este potente modelo,

GPT-OSS, y espero que tú estés haciendo lo mismo

o estés utilizando un modelo más pequeño como PHY3

o una de las variantes de Gemma o quizás

algo de Lama, que está fabricado por Meta.

Y voy a tratar de usar esto para un verdadero propósito.

Voy a decir, hola, estoy tratando de aprender español.

Soy un principiante total.

Por favor, tener una conversación, una conversación

conmigo como mi tutor de español.

Muy bien, veamos cómo lo hace.

Y este es el tipo de cosas que

podrías ver en una aplicación comercial como Duolingo.

En primer lugar, observe que comienza describiendo su proceso

de pensamiento en este color diferente, en este color

gris aquí, y luego obtenemos la respuesta final.

Dice, hola, soy de tutor de Espanol.

Perdona mi horrible acento.

Te aseguro que se me dan bastante

mejor los LLM que el español.

Muy bien, y puedes ver que dice,

yo, hola, y quiere que responda.

Y así podría decir, hola, me llamo Ed.

Y luego puedes ver que dice, ¿de dónde eres? Así

que poco a poco te llevará a través del proceso

de tener una conversación de nivel principiante en español.

Y lo mejor de esto es que es bastante similar

al tipo de funcionalidad que puedes encontrar en una aplicación.

Así que quería hacer esto para mostrarte que, de

buenas a primeras, puedes empezar a obtener valor real

de la ejecución de un gran modelo lingüístico

de IA de código abierto localmente en tu

ordenador, incluso si hablas un español terrible como

yo, y espero que mejore con el tiempo.

La próxima vez que grabe esto, voy a tener un gran acento.

En cualquier caso, diviértete.

Utilízalo, prueba diferentes modelos, intenta explorar

cómo los modelos de mayor tamaño

son capaces de ser más inteligentes

a la hora de interactuar contigo.

Vea estos modelos de tipo pensante, hablaremos mucho

más de ellos en los próximos días.

Familiarícese con esto y disfrute del hecho de

que está aprovechando la potencia de un

gran modelo lingüístico localmente en su ordenador.

Espero que hayas disfrutado mucho

probando diferentes modelos, experimentando y

enamorándote de la potencia de

estas cosas, pero tengo

que echarte para atrás.

Ha llegado el momento de que hablemos

de todo lo que les tengo

preparado para las próximas ocho semanas.

Y esta es una línea de tiempo que te mostraré con frecuencia.

Es una línea de tiempo que te muestra

dónde empiezas a la izquierda y las

ocho semanas que tengo reservadas para ti.

Mañana, voy a ir a través de

esto con mucho más detalle, pero habrá

esta semana, que será más fundamental.

La semana que viene hablaremos de modelos fronterizos.

La semana siguiente tratará sobre los modelos de código abierto.

A continuación, se trata de seleccionar el

modelo adecuado para la tarea en cuestión.

Entonces se tratará de RAG.

Profundizaremos en RAG, uno de los temas más candentes de la IA.

Luego afinaremos un modelo de frontera y

después afinaremos un modelo de código abierto.

Y, por último, presentaré la IA agéntica en la

última semana de nuestra serie de ocho semanas.

Y en ese momento, podrás ondear una

bandera y decir: ahora soy ingeniero LLM.

He alcanzado este hito hacia la maestría.

Y he creado un completo plan de estudios para

ti a lo largo de las próximas ocho semanas.

Y una de las cosas que más me gusta es

que se construye y se construye y se construye.

Empezamos cubriendo una gama de diferentes modelos, diferentes

LLM, de código abierto y de código cerrado.

Luego cubrimos un montón de kits de herramientas,

librerías, frameworks que utilizan esos LLMs, cosas

como Hugging Face, Gradio para interfaces de

usuario, Lang chain, pesos y sesgos, modal.com.

Y, a partir de ahí, aplicamos

distintas técnicas para obtener el

máximo rendimiento de los LLM.

Técnicas como la GAR, el ajuste fino y los agentes.

Pero, con diferencia, lo mejor de este campo es el edificio.

Creo firmemente en el aprendizaje práctico.

Creo que la mejor manera de

aprender es construyendo grandes proyectos comerciales.

Y eso lo haremos desde proyectos

multimodales hasta aplicaciones comerciales de consumo,

pasando por algo más técnico como

portar código de un lenguaje a

otro, o cosas que impliquen vectores

y formación con muchos gráficos.

Y, por último, un gran proyecto comercial al final.

Así que prepárate para una construcción muy jugosa.

Muy bien, antes de entrar

en materia, permítanme presentarme rápidamente.

Convencerte de que estoy realmente

cualificado para hablarte de esto.

Me llamo Ed Donner.

Soy cofundador y Director Técnico de

una empresa de IA llamada Nebula.

Puede visitarnos en nebula.io.

Y pasé la mayor parte de mi carrera

en J.P. Morgan, donde empecé en Londres, que

es de donde soy originalmente, y luego pasé

algún tiempo en Tokio, y luego me trasladé

a Nueva York, que es donde estoy ahora.

Y más recientemente, en J.P. Morgan,

fui director general y dirigí un

equipo tecnológico de unos 300

ingenieros de software y científicos.

Y antes de Nebula, fui fundador y CEO

de una startup de IA llamada Untappd.

Y esta foto es, este es un momento mágico para mí.

Esta es una foto de una valla publicitaria de

Times Square el día en que se anunció la

adquisición de mi startup de IA hace un

par de años, lo que fue realmente genial.

Y en caso de que no me creas, puedo demostrarlo.

Si entrecierras los ojos y miras con mucho cuidado,

cerca de la parte inferior derecha de la

imagen hay una pequeña foto mía, para demostrar

que era yo quien estaba con esta startup.

Pero ese fue mi momento en Times Square.

Y creo que es obligatorio con estas cosas tener

algún tipo de foto de la vida personal.

Así que aquí estoy yo delante de un avión que acabo de pilotar.

Y puede que pienses que lo

muestro porque soy muy hábil pilotando

aviones, pero es todo lo contrario.

Mi gran talento cuando se trata de todo lo

relacionado con LLM sólo es superado por mi total

incapacidad para hacer cualquier cosa que requiera coordinación mano-ojo.

Así que si estás entrando en un avión y

echas un vistazo a la cabina y ves que

soy yo el que está sujetando el yugo,

lo mejor es que busques un paracaídas rápidamente.

Pero si has llegado a algún curso online y

es sobre LLMs y ves que es este tío

el que da la charla sobre LLMs, pues enhorabuena.

Ese es mi punto dulce.

Ha venido al lugar adecuado.

Manos a la obra.

Mencionaré también que lo mejor de la

foto de abajo a la derecha

es que yo también vivo allí.

Curiosamente, no mucha gente vive en Times Square, pero yo

vivo como a una manzana de Times Square, justo detrás

de donde está esa guitarra del Hard Rock Cafe.

Estoy un poco detrás de allí y

estoy mirando hacia fuera en este momento.

Así que ahora mismo eres una especie de imagen

mía mirando hacia donde se juntan esas dos líneas.

Y el otro lugar donde estoy es en LinkedIn.

Y tengo que advertirte, voy a

hablar de esto como cien veces.

Me encanta conectar con la gente en LinkedIn.

Algunas personas son tímidas a la hora de conectarse en LinkedIn.

Son como, dime dónde nos conocimos

y puede que conecte contigo.

Yo no soy así en absoluto.

Si quieres conectar conmigo, estoy encantado.

Envíame una invitación y me conectaré.

Puedes saludarme y yo te devolveré el saludo.

No es un agente, realmente seré yo.

También puedes enviar la conexión y la aceptaré.

Aunque no se trata de un agente,

me reservo el derecho de copiar y

pegar de vez en cuando algunas de

mis respuestas, porque paso bastante de ellas.

Pero siempre, seguro, interactuaré contigo y me encanta.

Y también, si publicas información sobre el

curso, si publicas tu progreso a través

del curso o proyectos que construyes y

me etiquetas en tu publicación de LinkedIn,

entonces entraré y opinaré y añadiré algo.

Y me parece una forma fabulosa de amplificar

tus logros y también de conseguir que

otras personas de la comunidad, otros científicos

de datos, lo vean y respondan también.

Así que, por favor, hágalo generosamente.

A veces tardo unos

días, pero lo consigo.

Etiquétame y déjame opinar.

En resumen, soy ingeniero de software,

científico de datos, emprendedor y líder

en el campo de la IA.

Pero también hago otra cosa.

En 2024, un amigo me convenció para hacer un

curso Udemy enseñando a otras personas sobre IA.

Y fue, por supuesto, la primera versión de este

curso, LLM Ingeniería, e hice algunas otras desde entonces.

Y he tenido la suerte de que han tenido bastante

éxito y ahora hay más de un cuarto de

millón de personas que toman mis diferentes cursos Udemy.

Me encanta interactuar

con la gente.

También es una gran oportunidad para todos porque, como

digo, puedes publicar en LinkedIn, yo puedo opinar, y

tiene un efecto multiplicador en la comunidad para

que todo el mundo participe en tu viaje.

Y por eso te animo a que publiques

sobre ello y disfrutes del viaje, disfrutes de

la experiencia y la compartas con todos nosotros.

##

Y ahora, la pregunta más habitual que me hacen es: ¿cómo encajan los cursos entre sí? ¿En

qué orden debo seguirlos? Permítame que se lo explique. Tengo seis cursos en mi plan de estudios.

El primero de ellos se llama AI Builder y trata sobre la creación de agentes y agentes de voz con

cualquier N y 11 labs. El segundo se llama AI Coder y trata sobre la creación de software y productos

utilizando código en la nube y otros agentes de codificación. Y la tercera se llama AI Leader y trata

sobre la entrega de proyectos de IA, la transformación de tu negocio con IA e incluso la creación de

un negocio de IA. Ese es AI Leader. Y los siguientes tres cursos son sobre ingeniería de IA. Está

el curso básico de ingeniero de IA, que trata sobre NLMs y APIs y NLMs de código abierto y

ajuste fino. Y luego está el curso de ingeniería de IA agéntica, que trata sobre bucles de agentes, agentes

de IA abiertos, SDK, MCP. Y luego está la pista de producción del ingeniero de IA, que despliega a

escala en AWS, GCP y Azure. Y esos son los seis cursos de mi plan de estudios de IA.

Y mi gran mensaje para ti es que los seis cursos se complementan entre sí. Hay poco

solapamiento entre ellos. Cada uno de ellos cubre temas diferentes. Y déjame decirte lo que serás

capaz de hacer después de completar cada uno. Después de AI Builder, serás capaz de crear

agentes y agentes de voz para tu negocio sin necesidad de escribir una línea de código.

Después de AI Coder, serás un experto en el uso de agentes de codificación como Claude Code para

entregar software con una velocidad asombrosa. AI Leader te posicionará para ofrecer un impacto comercial, tanto si trabajas

en una startup como en una empresa de Fortune 500. Y ahora, con el trío de ingeniería de

IA, el itinerario principal le permitirá seleccionar, aplicar y optimizar LLM para resolver problemas comerciales del mundo real.

Y la vía agéntica es lo mismo, pero esta vez con agentes autónomos de IA.

Y la pista de producción te permitirá tomar LLMs y agentes y desplegarlos en la nube

a escala con resiliencia y observabilidad y seguridad. He organizado los cursos para que haya algo

para todos. Pero en términos generales, los tres primeros cursos son los más adecuados para las

personas que son técnicos o no técnicos, una amplia audiencia. Los cursos cuarto y quinto son

más adecuados para personas técnicas o que aspiran a serlo. Estás preparado, tienes curiosidad, quieres aprender

a ser más técnico o ya lo eres. Y luego el sexto es mejor para alguien

que ya es técnico o que ya ha completado los cursos del uno al cinco. Así

es como están dispuestos. Y si has completado todo el programa, los seis cursos del plan

de estudios, y has entregado todos los proyectos asociados, entonces puedes llamarte a ti mismo un

ingeniero de IA competente y estás listo para asumir funciones como ingeniero de IA, ingeniero de

IA aplicada, ingeniero FDE, en la industria hoy en día, resolviendo problemas del mundo real con

LLMs y agentes. Y puedes tomar los cursos en el orden que desees. Son grandes

compañeros entre sí. En igualdad de condiciones, la progresión más natural es probablemente del uno

al seis, como se muestra aquí. Si yo estuviera empezando, probablemente elegiría ese orden, pero

tú decides. Elige el tema que te interese y sigue la progresión que tenga más

sentido para ti. Y como consejo profesional para ti, si miras el conjunto de conferencias

de este curso, si te desplazas hasta el final, la conferencia final es de hecho

un documento. Y si lo abres, verás que contiene un resumen de estos cursos y

enlaces especiales a los cursos. Es un buen recurso para tener a mano. Lo encontrarán

en la conferencia final. Y, de hecho, debo mencionar que ese recurso tiene algunas ofertas

especiales que sólo están disponibles para los estudiantes existentes. Así que definitivamente no querrás perdértelo.

De acuerdo. Con eso, has elegido un curso excelente. Encaja muy bien. Sigamos con ello.

##


