# IntelligentPlatform — Documentation Index

This directory contains all design and reference documentation for the IntelligentPlatform backend.

## Structure

```
docs/
├── architecture/           ← Backend design, DB schema, auth, entity model
│   ├── JWT_AUTH_DESIGN.md
│   ├── SCHEMA_MANAGEMENT.md
│   ├── document/           ← Document-flow design (brainstorm + use cases)
│   │   ├── brainstorm/
│   │   │   └── migrationWithClaude/
│   │   └── usecases/
│   │       ├── CrossDocumentOperation/
│   │       └── DocumentOperation/
│   └── logicManager/       ← Manager / service logic design
│       └── Search/
│
├── ui/                     ← Frontend design and guides
│   ├── devGuide/           ← Developer setup and patterns
│   ├── subFunctions/       ← Sub-function component specs
│   ├── typicalBugFix/      ← Common UI bug patterns and fixes
│   └── usecases/           ← UI-level use case documentation
│
├── local-setup/            ← Local environment setup guides
│
└── claude/                 ← Claude Code working notes (auto-generated)
    └── CODE_STYLE.md
```

## Migrating from ServiceDocument

Content from `../work/ServiceDocument/designDoc/` maps to this structure as follows:

| Old location | New location |
|---|---|
| `platform/foundation/` | `docs/architecture/` |
| `uiClient/` | `docs/ui/` |
| `localEnvironment/` | `docs/local-setup/` |
