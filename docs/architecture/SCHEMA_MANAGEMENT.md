# Schema Management Guide

## Overview

Flyway is **disabled** in this project (`spring.flyway.enabled: false` in all profiles).
Hibernate `ddl-auto` is set to `none` — schema changes must be applied via Flyway migrations.

## Why Flyway is Disabled

- Three baseline Flyway migrations exist (`V1`, `V2`, `V3`) but have not yet been applied to an existing database.
- Re-enabling Flyway requires either a fresh database or running with `spring.flyway.baseline-on-migrate: true` on first run against an existing schema.
- The next migration to author is `V4__...sql`.

## Setting Up a New Environment

Before the first deployment to a new environment:

1. **Create all schemas** in MySQL:
   ```sql
   CREATE SCHEMA IF NOT EXISTS platform;
   CREATE SCHEMA IF NOT EXISTS finance;
   CREATE SCHEMA IF NOT EXISTS logistics;
   CREATE SCHEMA IF NOT EXISTS sales;
   CREATE SCHEMA IF NOT EXISTS production;
   ```

2. **Create the application database user** with appropriate grants:
   ```sql
   CREATE USER 'ip_user'@'%' IDENTIFIED BY '<password>';
   GRANT SELECT, INSERT, UPDATE, DELETE ON platform.* TO 'ip_user'@'%';
   GRANT SELECT, INSERT, UPDATE, DELETE ON finance.* TO 'ip_user'@'%';
   GRANT SELECT, INSERT, UPDATE, DELETE ON logistics.* TO 'ip_user'@'%';
   GRANT SELECT, INSERT, UPDATE, DELETE ON sales.* TO 'ip_user'@'%';
   GRANT SELECT, INSERT, UPDATE, DELETE ON production.* TO 'ip_user'@'%';
   FLUSH PRIVILEGES;
   ```

3. **Enable Flyway and run migrations** (see "Enabling Flyway" section below).
   The existing migrations (`V1__init_schemas.sql`, `V2__create_tables.sql`, `V3__seed_data.sql`)
   will create all tables and seed data automatically.

4. **Flowable schema** — Flowable's ACT_* tables are managed by the Flowable engine:
   - In **development**: `FLOWABLE_DB_UPDATE=true` (auto-creates/updates on startup).
   - In **production**: `FLOWABLE_DB_UPDATE=false`. Run the official Flowable SQL scripts
     from the Flowable release package before deploying a new Flowable version.

## Enabling Flyway

Flyway migrations already exist under `src/main/resources/db/migration/`:
- `V1__init_schemas.sql` — creates the five schemas
- `V2__create_tables.sql` — creates all tables
- `V3__seed_data.sql` — seeds reference data

To enable Flyway:

1. Set `spring.flyway.enabled: true` in `application.yml`.
2. For a **fresh database**, Flyway will run V1 → V3 automatically on startup.
3. For an **existing database** with tables already present, add these properties for the first run only, then remove them:
   ```yaml
   spring.flyway.baseline-on-migrate: true
   spring.flyway.baseline-version: 3
   ```
4. All subsequent schema changes must be added as versioned migrations (e.g. `V4__add_column_x.sql`).

## Column and Table Naming Convention

- **Table names** = Java class name (exact camelCase, e.g. `MaterialStockKeepUnit`)
- **Column names** = Java field name (exact camelCase, e.g. `parentNodeUUID`)
- **Schema per module**: `platform`, `finance`, `logistics`, `sales`, `production`

No renaming strategy is applied — Hibernate uses the field/class names verbatim.
