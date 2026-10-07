---
name: git-feature-flow
description: Flujo Git simple "main + ramas de feature" para publicar cambios — rama con nombre concreto, commits con por qué, verificación según el módulo, push y Pull Request; nunca directo a main y nunca mergea (el merge lo hace el dueño). Úsalo cuando un cambio esté listo para guardarse o subirse, al cerrar una jornada, o cuando se pida "súbelo", "abre el PR" o "haz deploy". Los proyectos pueden añadir una capa propia encima (módulos, hosting, excepciones).
---

# git-feature-flow

Flujo deliberadamente simple: **`main` + ramas de feature**. Sin `develop`, ni ramas de release o hotfix. Una regla innegociable: **nada se commitea ni se sube directo a `main`** (en muchos proyectos cada push a `main` despliega a producción). Todo lo demás es libre: commitear, pushear ramas de feature y borrar ramas integradas no necesita pedir permiso.

## Flujo (una rama por jornada o por funcionalidad)
1. **Abrir la rama** desde `main` actualizado:
   ```bash
   git checkout main && git pull
   git checkout -b feat/<nombre-corto>    # o fix/<nombre-corto>
   ```
   Nombre concreto (`feat/precios-catalogo`), nunca genérico (`feat/cambios`). Si ya existe la rama de la jornada, sigue sobre ella. Un fix urgente sigue el mismo camino.
2. **Commits** libres durante el día, con mensaje que explique el *por qué* (skill `conventional-commit`) y la línea de atribución que use la sesión.
3. **Verificar según lo tocado** antes de subir: los comandos están en el `CLAUDE.md` del proyecto («Comandos»). Sin verificación declarada, al menos build/tests si existen.
4. **Push y PR:** `git push -u origin <rama>`; abrir el PR con el skill `pr-description`. Sin `gh`, usar el link de comparación que devuelve GitHub; nunca inventar la URL. Volver a `main` localmente para dejar el árbol limpio.
5. **Reportar** en 2-3 líneas: qué cambió y por qué, link del PR y qué verá el dueño (p. ej. Deploy Preview). **El merge lo hace el dueño.**
6. **Después del merge:** `git checkout main && git pull` y borrar la rama integrada (`git push origin --delete <rama>`, `git branch -d <rama>`). Con `-d` git se niega si quedan commits sin integrar: no forzar con `-D` sin preguntar.

## Lo que NO se hace
- Commit o push directo a `main`; force-push a `main`.
- Mergear un PR salvo pedido explícito en ese momento.
- Incluir en un commit `.env`, credenciales, datos reales de personas ni documentación interna ignorada; si aparecen en `git status`, parar y avisar.

## Verificación post-merge
`git fetch origin && git log origin/main --oneline -5`. Para confirmar que el hosting publicó, comprobar el sitio en vivo con `curl` buscando la evidencia concreta del cambio; no asumir.

## Protección de `main` (chequeo, no autoacción)
Es configuración del repo en GitHub: sin token de administrador no se automatiza. Explicar los pasos (Settings → Branches → regla para `main` → «Require a pull request before merging», «Do not allow force pushes», «Restrict deletions») y dejar decidir al dueño.

## Cuándo NO aplica
El usuario pide expresamente ir directo a `main` (excepción, no default); documentación pura que no afecta lo publicado (ante la duda, aplicar el flujo); auditorías y lecturas que no modifican archivos.
