# IntelligentPlatform — Backend

Spring Boot 3.2 backend migrated from 5 legacy Spring 4 + Hibernate 5 projects.

## Prerequisites

| Tool | Version |
|------|---------|
| Java (JDK) | 17 |
| Maven | 3.8+ |
| MySQL | 8.0+ |

> **macOS note:** This project uses `sapmachine-jdk-17`. Set `JAVA_HOME` before every Maven command if your default JDK is not 17:
> ```bash
> export JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home
> ```

---

## Database Setup

The app connects to **five MySQL schemas**: `platform`, `finance`, `logistics`, `sales`, `production`.

The default connection (in `application.yml`) targets `platform` as the JDBC URL database, but entity classes declare their own `catalog` (e.g. `@Table(catalog = "logistics")`), so all schemas must exist and be accessible by the DB user.

### Required schemas and user

```sql
-- Run once as MySQL root
CREATE DATABASE IF NOT EXISTS platform;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS logistics;
CREATE DATABASE IF NOT EXISTS sales;
CREATE DATABASE IF NOT EXISTS production;

CREATE USER IF NOT EXISTS 'ip_user'@'localhost' IDENTIFIED BY 'ip_password';
GRANT ALL PRIVILEGES ON platform.*   TO 'ip_user'@'localhost';
GRANT ALL PRIVILEGES ON finance.*    TO 'ip_user'@'localhost';
GRANT ALL PRIVILEGES ON logistics.*  TO 'ip_user'@'localhost';
GRANT ALL PRIVILEGES ON sales.*      TO 'ip_user'@'localhost';
GRANT ALL PRIVILEGES ON production.* TO 'ip_user'@'localhost';
FLUSH PRIVILEGES;
```

Schema tables are **not** managed by Hibernate (`ddl-auto: none`) and must be created/imported separately from the legacy dump.

---

## Configuration

All sensitive values are controlled by environment variables. The defaults in `application.yml` are for **local development only** — never use them in production.

| Env var | Default | Purpose |
|---------|---------|---------|
| `DB_HOST` | `localhost` | MySQL host |
| `DB_PORT` | `3306` | MySQL port |
| `DB_NAME` | `platform` | JDBC default database |
| `DB_USERNAME` | `ip_user` | DB user |
| `DB_PASSWORD` | `ip_password` | DB password |
| `JWT_SECRET` | `localDevSecretKey1234567890AbcDef` | JWT signing key (min 32 chars) |
| `JWT_EXPIRATION_MS` | `86400000` | Token TTL in ms (default 24 h) |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000` | Allowed CORS origins |
| `SERVER_PORT` | `8080` | HTTP port |
| `FLOWABLE_DB_UPDATE` | `true` | Auto-create Flowable schema (set `false` in prod) |

---

## Running Locally

### Quick restart (one command)

```bash
lsof -ti tcp:8080 | xargs kill -9 2>/dev/null; JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home mvn spring-boot:run
```

This kills any running instance on port 8080, then compiles and starts the app in one shot. No `mvn install` needed — `spring-boot:run` compiles automatically.

---

### Step 1 — Set JAVA_HOME

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home
```

### Step 2 — Stop any running instance first

If the app is already running, port 8080 will be in use and the next launch will fail with `Port 8080 was already in use`. Kill it before starting:

```bash
lsof -ti tcp:8080 | xargs kill -9
```

### Step 3 — Start the app

> **Important:** Always prefix Maven commands with `JAVA_HOME=...` or the `export` from Step 1 must be in the **same shell session**. Running `mvn spring-boot:run` without Java 17 active causes a `class file version 61.0` error because the plugin itself was compiled for Java 17.

> **Do I need `mvn install` before running?** No. `mvn spring-boot:run` compiles the source automatically — it runs the `compile` phase internally. Only run `mvn package` if you need the `.jar` file (e.g. to use `java -jar`).

**Recommended — run the pre-built jar directly (faster, no plugin classloader issues):**

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  java -jar target/IntelligentPlatform-1.0.0-SNAPSHOT.jar
```

**Alternative — Maven spring-boot:run (compiles and runs in one step, no jar needed):**

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  mvn spring-boot:run
```

The app starts on **http://localhost:8080**.

To run with custom DB credentials:

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  DB_HOST=myhost DB_USERNAME=myuser DB_PASSWORD=mypass \
  java -jar target/IntelligentPlatform-1.0.0-SNAPSHOT.jar
```

To run as a background process and tail logs:

```bash
mkdir -p logs
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  java -jar target/IntelligentPlatform-1.0.0-SNAPSHOT.jar > logs/intelligentplatform.log 2>&1 &
tail -f logs/intelligentplatform.log
```

To run with **remote debug** (attach IDE on port 5005):

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
  java '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005' \
  -jar target/IntelligentPlatform-1.0.0-SNAPSHOT.jar
```

### Step 3 — Verify startup

```bash
curl http://localhost:8080/api/v1/auth/login \
  -X POST -H 'Content-Type: application/json' \
  -d '{"userId":"i00101","client":"001","password":"654321"}'
```

Expected: HTTP 200 with a `token` field in the JSON response.

---

## Authentication

All `/api/v1/**` endpoints (except `/api/v1/auth/**`) require a Bearer token.

**Login:**

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"userId":"<userId>","client":"<client>","password":"<plaintext>"}'
```

Response:
```json
{
  "token": "<JWT>",
  "uuid": "<userUUID>",
  "userId": "i00101",
  "client": "001"
}
```

**Use the token:**

```bash
curl http://localhost:8080/api/v1/logistics/purchaseContracts?client=001 \
  -H "Authorization: Bearer <JWT>"
```

---

## Available REST Endpoints (summary)

| Module | Base path |
|--------|-----------|
| Auth | `/api/v1/auth` |
| Logistics — Purchase | `/api/v1/logistics/purchaseContracts`, `purchaseRequests`, `purchaseReturnOrders`, `inquiries` |
| Logistics — Quality / Waste | `/api/v1/logistics/qualityInspectOrders`, `wasteProcessOrders` |
| Logistics — Inventory | `/api/v1/logistics/inventoryCheckOrders`, `inventoryTransferOrders`, `warehouseStores` |
| Logistics — Delivery | `/api/v1/logistics/inboundDeliveries`, `outboundDeliveries` |
| Finance | `/api/v1/finance/finAccounts` |
| Sales | `/api/v1/sales/salesContracts`, `salesReturnOrders` |
| Production | `/api/v1/production/productionOrders`, `productionPlans`, `billOfMaterialOrders`, `prodPickingOrders` |

---

## Build

```bash
# Compile only
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home mvn compile

# Package (skip tests)
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home mvn package -DskipTests -Dmaven.test.skip=true

# Run tests
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home mvn test
```

---

## Known Remaining Gaps

The following 4 endpoints return HTTP 500 due to DB schema columns that exist in the entity model but are absent from the legacy DB tables. These require `ALTER TABLE` migrations before they can serve data:

| Endpoint | Table | Missing columns |
|----------|-------|----------------|
| `GET /logistics/inventoryTransferOrders` | `logistics.inventoryTransferOrder` | `freightCharge`, `freightChargeType`, `grossOutboundFee`, and others from the `Delivery` superclass |
| `GET /logistics/warehouseStores` | `logistics.warehouseStore` | `documentCategoryType`, `priorityCode`, doc-chain columns from `DocumentContent` |
| `GET /finance/finAccounts` | `finance.FinAccount` | `documentType` |
| `GET /production/productionOrders` | `production.ProductionOrder`, `production.RepairProdOrder` | doc-chain columns (`nextDocType`, `prevDocType`, etc.) from `DocumentContent` |

All other endpoints return HTTP 200 with data.
