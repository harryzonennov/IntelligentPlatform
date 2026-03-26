---
name: delete-module
description: Delete one or more obsolete modules and all their related code resources from the IntelligentPlatform project. Usage: /delete-module <ModuleName> [ModuleName2 ModuleName3 ...]
---

Delete obsolete modules and all their related code resources from the IntelligentPlatform project.

The arguments are: `$ARGUMENTS`

Parse `$ARGUMENTS` by splitting on spaces to get a list of module names. If there is more than one module name, process them **one at a time, sequentially** — complete all steps for the first module before starting the next. Apply the full procedure below to each module name in order.

---

## For each module name, repeat Steps 1–6 below before moving to the next module.

---

## Step 1 — Discover all references

Search for all Java files referencing the current module name across `src/main/java/`:

```
grep -rn "<ModuleName>" src/main/java/ | grep -v "^Binary" | awk -F: '{print $1}' | sort -u
```

Also search for closely related names (e.g. if module is `SettleOrder`, also search `SettleMaterialItem`, `SettleOrderAttachment`). Check the model files themselves to identify the full set of sub-entities.

## Step 2 — Categorize each file

### Files to DELETE entirely

Delete a file if its **entire purpose** is the module being removed:

- `*/model/<ModuleName>.java` — the entity class
- `*/model/<ModuleName>Item.java`, `<ModuleName>Attachment.java`, `<ModuleName>ActionNode.java` etc. — all sub-entity models
- `*/repository/<ModuleName>Repository.java`
- `*/dto/<ModuleName>Dto.java`
- Any `*UIModel`, `*SearchModel`, `*ServiceUIModel`, `*ServiceUIModelExtension`, `*ServiceModel`, `*ConfigureProxy`, `*Manager`, `*Helper`, `*DAO`, `*ListController`, `*EditorController` files that are **exclusively** for this module

### Files to EDIT partially (never delete these)

These are shared infrastructure files. Only remove the module's section:

| File pattern | What to remove |
|---|---|
| `common/model/IServiceModelConstants.java` | The constant block: `// Model Define for <Module>` + all `String <Module>...` lines |
| `*Service.java` (shared service) | The `@Autowired` repository field + the entire `// --- ModuleName ---` method section |
| `*Controller.java` (shared controller) | All endpoint methods for this module + the URL comment line in the class javadoc |
| `ServiceModuleProxy.java` | `@Autowired` field + import only if the module's manager is injected |
| `ServiceUIModuleProxy.java` | `@Autowired` field + import + any log/call statements referencing the module |
| `ServiceEntityManager.java` | Import + `@Autowired` field + any call blocks referencing the module |

## Step 3 — Critical safety rules (learned from past mistakes)

### DO NOT delete base classes
Before deleting any file, check if it is **extended by other classes**:
```
grep -rn "extends <ClassName>" src/main/java/
```
If other classes extend it, it is a **base class** — do NOT delete it. Instead, only remove the `ModelAndView` import or the specific method that references the obsolete module.

### DO NOT delete files where ModelAndView is only an unused import
If a file contains `import org.springframework.web.servlet.ModelAndView;` but `ModelAndView` does not appear in the class body, just remove the import line — do not delete the file.

### DO NOT delete files where ModelAndView is used in only one helper method
If `ModelAndView` appears in only one method and the rest of the file is valuable logic (e.g. `ServiceExcelReportProxy` with its Apache POI Excel engine), remove only that one method — do not delete the whole file.

### Distinguish class references from string data references
After removing code, run a final grep. Hits like these are **safe to keep** — they are string-valued field names or IDs, not class dependencies:
- `refSettleOrderId`, `refSettleItemUUID`, `settleAmount`
- Any `protected String ref<ModuleName>...` field in a UIModel
- Any `protected String ref<ModuleName>UUID` in a model entity

Only actual Java type references (`SettleOrder settleOrder`, `List<SettleOrder>`, `import ...SettleOrder`) must be removed.

### Check for @Autowired injection in shared files
When a module's Manager/Repository is `@Autowired` into a shared class like `ServiceModuleProxy` or `ServiceUIModuleProxy`, remove:
1. The `import` statement
2. The `@Autowired` field declaration (and the blank line after it)
3. Any method call statements using that field

## Step 4 — Execute deletions and edits

Delete the dedicated files first, then make surgical edits to shared files.

After each shared file edit, verify the change looks correct before moving on.

## Step 5 — Final verification

Run a grep to confirm zero remaining class-level references:
```
grep -rn "<ModuleName>" src/main/java/
```

Filter out known safe string-data false positives (ref fields). The result should be empty or only safe hits.

## Step 6 — Report for this module

Summarize:
- **X files deleted**: list them
- **Y files edited**: list them with what was removed from each

Then proceed to the next module name (if any).

---

## After all modules are processed

Print a combined summary listing every module processed, the files deleted, and the files edited across all modules.