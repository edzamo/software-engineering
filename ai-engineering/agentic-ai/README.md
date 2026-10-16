# AI Engineer — Agentic Track (curso)

Apuntes del curso de Udemy **AI Engineer Agentic Track: The Complete Agent & MCP Course** (Ed Donner). Formato: **veredicto por clase** + lo que vale, de junior a senior, con la analogía de la **cocina**. Al cerrar cada semana se **depura**.

> **Repo del curso:** <https://github.com/ed-donner/agents> · **Tu fork de práctica:** [`edzamo/agents`](https://github.com/edzamo/agents)

## 🧪 Dónde se practica y dónde se documenta

| | Este repo (`software-engineering`) | Fork de práctica (`agents`) |
|---|---|---|
| **Qué contiene** | Apuntes depurados, [catálogo de patrones](../patrones/README.md), [conceptos](../conceptos/ingenieria-de-contexto.md) | Los laboratorios del curso |
| **Ruta local** | `ai-engineering/agentic-ai/` | `~/Documents/Personal/github/agents` |
| **Proveedor de modelos** | — | **Gemini** (gratis, sin tarjeta) vía API compatible con OpenAI: solo cambia `base_url`, clave y modelo |

> 🔐 El `.env` está en el `.gitignore` del fork: nunca se sube. No subas tu `linkedin.pdf` ni `summary.txt` (git ya los sigue).

## 📚 Progreso

| Semana | Tema | Estado | Documento |
|---|---|---|---|
| **1** | 🟠 Fundamentos · gemelo digital | ✅ **Depurada** (17 % del curso) | [**semana-01.md**](semana-01.md) |
| 2 | 🔵 OpenAI Agents SDK | ✅ **Depurada** (33 % del curso) | [**semana-02.md**](semana-02.md) |
| 3 | 🔵 CrewAI | ✅ **Depurada** (50 % del curso) | [**semana-03.md**](semana-03.md) |
| 4 | 🔵 LangGraph | ⏳ Siguiente | |
| 5 | 🔵 Google ADK + A2A, AWS Strands, otros | ⏳ | |
| 6 | 🟠 MCP | ⏳ | |

> ➕ **Para agregar una clase:** pégala en [`clases/_pegar-clase-aqui.md`](clases/_pegar-clase-aqui.md), indica semana y día, y pide el veredicto.

## 🎯 Triage previsto de las semanas 2 a 6

> 🔎 **Hipótesis mía a partir de los títulos del temario**, sin haber visto las clases. Se corrige con cada transcripción.

| Semana | Verla | Probablemente saltar |
|---|---|---|
| **2 · OpenAI Agents SDK** | 🟢 Día 1 (conceptos) y día 3 (**Tools vs Agents, Guardrails**) | 🟡 Proyectos SDR y *deep research*: solo ver cómo aplican los patrones |
| **3 · CrewAI** | 🟡 Día 1 y 2 (conceptos y un agente) | 🔴 Proyectos *stock picker* y equipo de ingeniería, salvo curiosidad |
| **4 · LangGraph** | 🟢 Días 1 a 3 (conceptos, agente, herramientas y memoria): es el estándar en empresas | 🟡 *Sidekick* |
| **5 · Otros frameworks** | 🟢 Google ADK + **A2A** (protocolo entre agentes) | 🔴 Frameworks de Python y JS/TS ya vistos; 🟡 *Agent Loop* |
| **6 · MCP** | 🟢 **Toda** (arquitectura, servidor y cliente, servidores locales y remotos): es lo más cercano a tu trabajo | 🟡 Proyecto de *equity traders* |

El instructor dice que puedes hacer **semanas 1 y 2 y saltar a la 6**; la 6 exige haber visto la 2.

## 🗺️ El currículo del instructor (resumen)

1 AI Builder (n8n) · 2 AI Coder (**usar** agentes: Claude Code, Codex…) · 3 AI Leader (negocio) · 4 Core Track (LLMs, RAG) · **5 Agentic Track (este: construir agentes)** · 6 [Production Track](../llm-production/README.md) (desplegar). Para **usar** agentes en tu trabajo, el curso 2 es el relacionado; este enseña a **construirlos**.

## 🔗 Relacionado

[Roadmap AI Engineering](../README.md) · [Catálogo de patrones](../patrones/README.md) · [Ingeniería de contexto](../conceptos/ingenieria-de-contexto.md) · [`ia-agentes/`](../../ia-agentes)
