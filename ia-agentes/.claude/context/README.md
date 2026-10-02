# Contexto de proyecto

`PROJECT.md` es el contexto compartido de **este** proyecto: objetivo, alcance, componentes (backend/frontend/full-stack), stack por componente y backlog inicial. No es un paso ni un comando aparte: la Fase 0 de `/feature-implementation` lo crea ahí mismo, inline, la primera vez que la descripción recibida abarca más que una historia suelta (un sistema, una plataforma) — y lo confirma contigo antes de seguir. Para una feature puntual no se crea.

Una vez que existe, el orquestador lo lee en la Fase 0 de cada `/feature-implementation`/`/tdd-first` siguiente, así que no hace falta repetir el contexto general en cada historia — solo la historia puntual.

No se arrastra de un proyecto a otro al copiar el kit: es específico de este repo. Si no existe `PROJECT.md`, la Fase 0 sigue funcionando igual (detecta stack por archivos de build), solo que sin ese contexto previo.
