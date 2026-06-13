# Test Case ID Naming Convention

## Format

```
TC-{MODULE}-{DOCTYPE}-{API}-{NNN}
```

Every segment is separated by a hyphen. In Java method names, hyphens become underscores:

```
TC_{MODULE}_{DOCTYPE}_{API}_{NNN}_{descriptive_name}
```

---

## Segments

| Position | Segment    | Type     | Description                                             |
|----------|------------|----------|---------------------------------------------------------|
| 1        | `TC`       | Fixed    | Stands for **Test Case** — present in every test ID     |
| 2        | `{MODULE}` | Code     | The backend module the API belongs to (see table below) |
| 3        | `{DOCTYPE}`| Code     | The document or entity type being tested                |
| 4        | `{API}`    | Code     | Which endpoint within that document type                |
| 5        | `{NNN}`    | Sequence | 3-digit zero-padded integer: `001`, `002` … `999`       |

---

## Module Codes

| Code  | Module package          | Description             |
|-------|-------------------------|-------------------------|
| `PLT` | `platform`              | Platform / common       |
| `FIN` | `finance`               | Finance                 |
| `LO`  | `logistics`             | Logistics               |
| `SD`  | `salesDistribution`     | Sales & Distribution    |
| `PRD` | `production`            | Production              |

---

## Document Type Codes

Codes are abbreviated camelCase names of the root `ServiceEntityNode` class.

| Code      | Class                    | Module |
|-----------|--------------------------|--------|
| `PURCON`  | `PurchaseContract`       | `LO`   |
| `PURRET`  | `PurchaseReturnOrder`    | `LO`   |
| `INBDEL`  | `InboundDelivery`        | `LO`   |
| `OUTBDEL` | `OutboundDelivery`       | `LO`   |
| `INVCHK`  | `InventoryCheckOrder`    | `LO`   |
| `QIORD`   | `QualityInspectOrder`    | `LO`   |
| `SALESCON`| `SalesContract`          | `SD`   |
| `SALESRET`| `SalesReturnOrder`       | `SD`   |
| `PRODORD` | `ProductionOrder`        | `PRD`  |
| `PROCRT`  | `ProcessRouteOrder`      | `PRD`  |

> Add new codes to this table as new document types gain test coverage.

---

## API Codes

Each `{API}` code identifies which endpoint within a document type the test exercises.

| Code     | Endpoint pattern                          | HTTP   |
|----------|-------------------------------------------|--------|
| `SEARCH` | `/{seName}/searchTableService`            | POST   |
| `EDIT`   | `/{seName}/loadModuleEditService`         | GET    |
| `VIEW`   | `/{seName}/loadModuleViewService`         | GET    |
| `SAVE`   | `/{seName}/saveModuleService`             | POST   |
| `DELETE` | `/{seName}/deleteModule`                  | GET    |
| `ACTION` | `/{seName}/executeDocAction`              | POST   |
| `ITEM`   | `/{seItemName}/loadModuleEditService`     | GET    |
| `ITEMSAVE` | `/{seItemName}/saveModuleService`       | POST   |

> `ITEM` and `ITEMSAVE` are used when the controller path is the child/item entity
> (e.g. `purchaseContractMaterialItem`) rather than the root document.

---

## Sequence Numbers

- Always **3 digits**, zero-padded: `001`, `002` … `099`, `100`.
- Assigned in the order the test cases are defined within a test class.
- **Do not renumber** existing cases when adding new ones — append at the end.

---

## Examples

| Test ID                          | Meaning                                                         |
|----------------------------------|-----------------------------------------------------------------|
| `TC-LO-PURCON-SEARCH-001`        | Logistics / PurchaseContract / searchTableService / case 1      |
| `TC-LO-PURCON-EDIT-003`          | Logistics / PurchaseContract / loadModuleEditService / case 3   |
| `TC-LO-PURCON-ITEM-005`          | Logistics / PurchaseContractMaterialItem / loadModuleEditService / case 5 |
| `TC-SD-SALESCON-SEARCH-001`      | SalesDistribution / SalesContract / searchTableService / case 1 |
| `TC-PRD-PRODORD-ACTION-002`      | Production / ProductionOrder / executeDocAction / case 2        |

---

## Usage in Code

The test ID appears in three places, all kept in sync:

**1. Java method name** (underscores replace hyphens, followed by a descriptive name):
```java
@Test
void TC_LO_PURCON_SEARCH_001_emptySearch_returnsDataTableWithAllContracts() { ... }
```

**2. Section header comment** above the method:
```java
// -----------------------------------------------------------------------
// TC-LO-PURCON-SEARCH-001: Empty search returns all contracts for the session client
// -----------------------------------------------------------------------
```

**3. Skip log message** inside the method body (when a placeholder UUID is not yet filled):
```java
System.out.println("[TC-LO-PURCON-SEARCH-001] Skipped: set knownSupplierUUID to a real value.");
```

---

## File Location Convention

Test classes mirror the `src/main/java` package structure under `src/test/java`:

```
src/test/java/com/company/IntelligentPlatform/
└── {module}/
    └── dto/
        ├── BaseIntegrationTest.java
        ├── {DocumentType}SearchTableServiceTest.java      ← SEARCH cases
        ├── {DocumentType}LoadModuleEditServiceTest.java   ← EDIT cases
        └── {DocumentType}MaterialItemLoadModuleEditServiceTest.java  ← ITEM cases
```

Current test classes:

| File | IDs covered |
|------|-------------|
| `logistics/dto/PurchaseContractSearchTableServiceTest.java`              | `TC-LO-PURCON-SEARCH-001` … `009` |
| `logistics/dto/PurchaseContractLoadModuleEditServiceTest.java`           | `TC-LO-PURCON-EDIT-001` … `007`   |
| `logistics/dto/PurchaseContractMaterialItemLoadModuleEditServiceTest.java` | `TC-LO-PURCON-ITEM-001` … `006` |
