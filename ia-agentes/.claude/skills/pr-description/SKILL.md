---
name: pr-description
description: Arma el título y la descripción de un Pull Request (resumen + plan de pruebas) a partir de los commits y el diff de la rama actual contra la base. Úsalo antes de crear un PR con gh pr create, o para redactar la descripción a mano.
---

# PR Description

Procedimiento para redactar un PR a partir del estado real de la rama, no de memoria.

## Paso 1 — reunir el contexto real

```bash
git status
git branch --show-current
git log <rama-base>..HEAD --oneline
git diff <rama-base>...HEAD
```

Si no está claro cuál es la rama base, preguntá (`main`/`master`/`develop` — en un flujo GitFlow como el de [`gitflow-release-manager`](../../agent-harness/agents/gitflow-release-manager/instructions.md), una `feature/*` normalmente apunta a `develop`, no a `main`). Leé **todos** los commits del rango, no solo el último — el PR describe la rama completa.

## Paso 2 — título

Corto (idealmente bajo 70 caracteres), en el mismo idioma que el resto del repo. Resume el *resultado* del cambio, no el proceso ("Agregar clases de almacenamiento S3 y RDS a cloud-aws", no "Cambios varios en cloud-aws").

## Paso 3 — cuerpo, con esta estructura

```markdown
## Summary
- 1-3 bullets con lo esencial — qué cambia y por qué, no un listado de archivos.

## Test plan
- [ ] Checklist concreto de cómo se verificó el cambio (tests corridos, revisión manual, comando ejecutado).
```

- El **Summary** sale de leer el diff real, no de adivinar por los nombres de archivo — si el diff toca 3 carpetas por razones distintas, decilo explícito en vez de un bullet genérico.
- El **Test plan** tiene que ser verificable por quien revisa el PR — "probé que funciona" no alcanza; "corrí `python3 -m unittest discover -s agent-harness/tests`, 6/6 OK" sí.
- Si el repo es de documentación pura (como este) y no hay tests que correr, el test plan es igual de concreto pero distinto: "revisé que los links relativos no queden rotos", "verifiqué el diagrama Mermaid en preview".

## Paso 4 — mostrar el borrador antes de crear el PR

Igual que con el commit: mostrá el título + cuerpo armado, y esperá confirmación antes de correr `gh pr create`. Si el usuario ya pidió explícitamente crear el PR, usá el heredoc para pasar el body (evita problemas de escaping de saltos de línea):

```bash
gh pr create --title "el título" --body "$(cat <<'EOF'
## Summary
- ...

## Test plan
- [ ] ...
EOF
)"
```

No agregues `--reviewer` ni cambies el branch destino (`--base`) sin que te lo hayan pedido explícitamente.
