---
name: git-auto-submit
description: Auto-commit and push local changes with a generated commit message. On master/main/develop branches, commits directly. On bugfix/{ticket_id} or feature/{ticket_id} branches, prefixes the commit title with the ticket ID.
---

Commit and push all current local changes to the remote branch.

The optional arguments are: `$ARGUMENTS`

If `$ARGUMENTS` is provided, use it as the commit message body/hint. Otherwise, generate one automatically from the diff.

---

## Step 1 — Detect the current branch

Run:
```
git rev-parse --abbrev-ref HEAD
```

Store the result as `BRANCH`.

---

## Step 2 — Check for changes to commit

Run:
```
git status --short
```

If there are **no changes** (output is empty), stop and report: "Nothing to commit on branch `<BRANCH>`."

---

## Step 3 — Gather the diff for summarisation

Run:
```
git diff HEAD
```

Read the diff output to understand what has changed. Use this to generate a concise commit title (≤ 72 characters) and a short body (2–5 bullet points) that describe **what** changed and **why**.

---

## Step 4 — Build the commit message

### Title rules

| Branch pattern | Title format |
|---|---|
| `master`, `main`, `develop` | `<Short descriptive title>` |
| `bugfix/<ticket_id>` | `[<ticket_id>] <Short descriptive title>` |
| `feature/<ticket_id>` | `[<ticket_id>] <Short descriptive title>` |

- Title must be ≤ 72 characters.
- Use imperative mood: "Add …", "Fix …", "Update …", "Remove …".
- If `$ARGUMENTS` was supplied by the user, incorporate it as the core of the title.

### Body rules

- Leave one blank line between the title and the body.
- Write 2–5 bullet points (using `-`) summarising the key changes found in the diff.
- Keep each bullet under 80 characters.

Example for `feature/PROJ-42`:
```
[PROJ-42] Add warehouse safety warn threshold settings

- Add threshold fields to WarehouseStoreSetting entity
- Expose new PUT /api/v1/warehouse/storeSettings/{uuid}/threshold endpoint
- Return 400 when threshold value is out of allowed range
```

---

## Step 5 — Stage all changes

Run:
```
git add -A
```

---

## Step 6 — Commit

Run (using a heredoc to preserve formatting):
```
git commit -m "$(cat <<'EOF'
<title>

<body>
EOF
)"
```

If the commit fails (e.g. pre-commit hook rejection), report the hook output and stop — do **not** retry or bypass hooks with `--no-verify`.

---

## Step 7 — Push to remote

Run:
```
git push origin <BRANCH>
```

If the remote branch does not exist yet, run:
```
git push --set-upstream origin <BRANCH>
```

---

## Step 8 — Report

Print a summary:
- **Branch**: `<BRANCH>`
- **Commit title**: `<title>`
- **Files changed**: the short list from `git status` before staging
- **Push status**: success or any error message
