# Integration Test Guide

## Overview

Integration tests boot the full Spring application context against a local MySQL
instance and exercise real HTTP endpoints via REST-Assured. They verify the
complete request-response cycle — security, session handling, controller logic,
and database access — with no mocking.

All tests extend `BaseIntegrationTest`, which handles login and session
management automatically before each test method.

---

## Prerequisites

### 1. Java 17

The project compiles under Java 17 (SapMachine JDK). Verify with:

```bash
/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home/bin/java -version
```

Set `JAVA_HOME` when running Maven so the right JDK is used:

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home
```

### 2. Local MySQL running

Start MySQL and ensure the database schemas exist with all tables created.
The test profile connects with these defaults (override with environment
variables if your setup differs):

| Variable      | Default     | Description             |
|---------------|-------------|-------------------------|
| `DB_HOST`     | `localhost` | MySQL host              |
| `DB_PORT`     | `3306`      | MySQL port              |
| `DB_NAME`     | `platform`  | Default schema          |
| `DB_USERNAME` | `ip_user`   | MySQL user              |
| `DB_PASSWORD` | `ip_password` | MySQL password        |

### 3. Test data in the database

Every test class documents its own data prerequisites in the class-level
Javadoc. The minimum for the PurchaseContract tests is:

- Client `C001` exists in the `platform` schema
- User `admin` / password `admin123` exists and belongs to client `C001`
- At least one `PurchaseContract` row for client `C001`

If these are not present, all tests are **skipped** (not failed) with a
message explaining what is missing.

---

## Running the Tests

### Run all integration tests

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  mvn test -Dspring.profiles.active=test
```

### Run a single test class

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  mvn test -Dspring.profiles.active=test \
  -Dtest=PurchaseContractSearchTableServiceTest
```

### Run a single test method

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  mvn test -Dspring.profiles.active=test \
  -Dtest="PurchaseContractSearchTableServiceTest#TC_LO_PURCON_SEARCH_001_emptySearch_returnsDataTableWithAllContracts"
```

### Custom database credentials

```bash
JAVA_HOME=... DB_USERNAME=myuser DB_PASSWORD=mypass \
  mvn test -Dspring.profiles.active=test
```

---

## Filling in UUID Placeholders

Several tests require UUIDs of real rows in the database. Placeholders look
like `REPLACE_WITH_KNOWN_CONTRACT_UUID`. To find the right value:

```sql
-- Find a PurchaseContract UUID for client C001
SELECT uuid FROM logistics.PurchaseContract WHERE client = 'C001' LIMIT 1;

-- Find a PurchaseContractMaterialItem UUID
SELECT uuid, rootNodeUUID
FROM logistics.PurchaseContractMaterialItem
WHERE rootNodeUUID IN (
  SELECT uuid FROM logistics.PurchaseContract WHERE client = 'C001'
)
LIMIT 1;
```

Replace the placeholder string in the test class constant and re-run.

---

## Test Status Reference

| Test Class | IDs | Status when DB is populated |
|---|---|---|
| `PurchaseContractSearchTableServiceTest` | `TC-LO-PURCON-SEARCH-001` … `009` | 001–005, 008–009 pass; 006 needs supplier UUID; 007 needs `no_list_user` |
| `PurchaseContractLoadModuleEditServiceTest` | `TC-LO-PURCON-EDIT-001` … `007` | 002, 005 pass; rest need UUIDs or `no_edit_user` |
| `PurchaseContractMaterialItemLoadModuleEditServiceTest` | `TC-LO-PURCON-ITEM-001` … `006` | 002 passes; rest need UUIDs or `no_edit_user` |

For the naming convention used for test IDs see
[TEST_ID_CONVENTION.md](TEST_ID_CONVENTION.md).

---

## Configuration Files

| File | Purpose |
|---|---|
| `src/test/resources/application-test.yml` | Spring profile for tests: points to local MySQL, disables Flyway and Flowable |
| `src/test/java/.../config/TestSecurityConfig.java` | Overrides production security: allows all requests, enables HTTP sessions |
| `src/test/java/.../logistics/dto/BaseIntegrationTest.java` | Base class: boots context, performs login, provides `sessionSpec` |
