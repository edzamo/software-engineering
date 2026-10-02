# Métricas de ejecución (kaizen)

`runs.jsonl` es append-only (una línea JSON por ejecución de `/feature-implementation` o `/tdd-first`). Se versiona: es historia del proyecto, no estado efímero (a diferencia de `.claude/.tdd-state/`).

No se reescribe ni se reordena; solo se añade al final (`>>`). Si necesitas analizarlo, léelo, no lo edites a mano salvo para corregir una línea mal formada.

## Para qué sirve
Sin esto, no hay forma de saber qué reglas (ARCH-xxx, TDD-xxx, CR-xxx, SEC-xxx) generan más fricción real en el proyecto, cuáles casi nunca disparan (candidatas a relajar o aclarar) y cuántos ciclos cuesta en promedio cada fase. Es la base de datos del paso «Retrospectiva» en `CLAUDE.md`.

## Esquema por línea
```json
{
  "date": "AAAA-MM-DD",
  "pipeline": "feature-implementation | tdd-first",
  "feature": "descripción corta",
  "stack": {"language": "java", "version": "21", "framework": "spring-boot-mvc"},
  "architecture_style": "hexagonal | clean | onion",
  "phases": [
    {"id": "phase-1-architecture", "verdict": "APPROVED | APPROVED_WITH_WARNINGS | BLOCKED", "cycles": 1, "rules_triggered": ["ARCH-002"]}
  ],
  "final_verdict": "APPROVED | APPROVED_WITH_WARNINGS | BLOCKED | ESCALATED_TO_HUMAN",
  "blocked_cycles_total": 0,
  "exceptions": ["EXC-1"],
  "learning_proposals": 0
}
```

Campos mínimos si hay prisa: `date`, `pipeline`, `final_verdict`, `phases[].id`, `phases[].verdict`, `phases[].cycles`. El resto, mejor esfuerzo.

## Cómo se agrega (ejemplo)
```bash
printf '%s\n' '{"date":"2026-10-02","pipeline":"feature-implementation","final_verdict":"APPROVED","phases":[{"id":"phase-1-architecture","verdict":"APPROVED","cycles":1,"rules_triggered":[]}]}' >> .claude/metrics/runs.jsonl
```

## Revisar tendencias
```bash
# Reglas que más bloquean
jq -r '.phases[].rules_triggered[]?' .claude/metrics/runs.jsonl | sort | uniq -c | sort -rn
# Fases con más ciclos promedio
jq -r '.phases[] | "\(.id) \(.cycles)"' .claude/metrics/runs.jsonl | awk '{s[$1]+=$2; c[$1]++} END {for (k in s) print k, s[k]/c[k]}'
```
