# Conventional Commit

Procedimiento para escribir un mensaje de commit correcto sin inventar el formato — se adapta a lo que el repo ya viene haciendo, en vez de imponer un estilo ajeno.

## Paso 1 — detectar la convención real del repo

Antes de escribir nada, corré:

```bash
git log --oneline -15
```

Mirá si los mensajes siguen **Conventional Commits** (`feat:`, `fix:`, `refactor:`, `docs:`, `chore:`, con o sin `(scope)`) o si el repo usa un estilo **descriptivo libre** (verbo en infinitivo o imperativo, sin prefijo — ej. "Agregar sección de RDS a cloud-aws", "Actualizar índice del README"). Usá el mismo estilo que ya predomina — no le impongas Conventional Commits a un repo que nunca lo usó, ni al revés.

## Paso 2 — clasificar el cambio

Sea cual sea el estilo detectado, primero clasificá internamente qué tipo de cambio es (esto ordena el mensaje aunque no se use el prefijo explícito):

| Tipo | Cuándo |
|---|---|
| `feat` | Contenido/funcionalidad nueva que no existía. |
| `fix` | Corrección de algo que estaba mal (dato erróneo, link roto, bug). |
| `refactor` | Reorganización sin cambiar el resultado (mover archivos, renombrar). |
| `docs` | Solo documentación — el caso más común en un repo de solo-documentación. |
| `chore` | Configuración, `.gitignore`, tooling — nada de contenido. |
| `test` | Agregar o corregir tests. |

## Paso 3 — revisar el diff real, no solo los nombres de archivo

```bash
git status
git diff --staged
```

Si no hay nada en staging todavía, mostrá qué hay sin commitear (`git status`) y preguntá si hay que agregarlo, en vez de asumir `git add -A` — nunca agregues archivos que no fueron pedidos explícitamente.

## Paso 4 — armar el mensaje

- **Línea de resumen**: ≤72 caracteres, en el mismo idioma y modo verbal que ya usa el repo (revisado en el paso 1). Sin punto final.
- **Cuerpo** (opcional, solo si el resumen no alcanza para explicar el *por qué*): una línea en blanco, después el detalle — enfocado en la razón del cambio, no en repetir el diff línea por línea.
- Si el repo usa Conventional Commits: `tipo(scope): resumen` — el scope es la carpeta/módulo principal tocado (ej. `docs(cloud-aws): agregar clases de almacenamiento S3`).
- Si el repo usa estilo libre: el resumen solo, sin prefijo.

## Paso 5 — mostrar el mensaje antes de commitear

Nunca corras `git commit` directo con el mensaje armado — mostralo primero (en un heredoc si tiene cuerpo multilínea) y esperá confirmación, salvo que el usuario ya haya pedido explícitamente "commiteá con ese mensaje". Mismo criterio de "no publicar sin aprobación" que usa el agente [`gitflow-release-manager`](../../../agents/gitflow-release-manager/prompt.md) para push/merge — commitear es más reversible, pero el mensaje final lo valida la persona.
