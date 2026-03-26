# Schema Management Guide

## Overview

Flyway is **disabled** in this project (`spring.flyway.enabled: false` in all profiles).
The database schema is managed **manually** — Hibernate only validates against the existing schema.

## Why Flyway is Disabled

- The database schema was initially created by importing SQL dump files (V1 + V2).
- Flyway migration scripts have not been authored for the existing schema.
- Re-enabling Flyway requires versioned migration scripts that match the current schema exactly.

## Production Schema Pre-Creation Checklist

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

3. **Import the base schema dump** (provided separately):
   ```
   mysql -u root -p < schema/V1__base_schema.sql
   mysql -u root -p < schema/V2__seed_data.sql
   ```
   These files establish all tables with the correct `camelCase` column names
   matching the JPA entity field names.

4. **Flowable schema** — Flowable's ACT_* tables are managed by the Flowable engine:
   - In **development**: `FLOWABLE_DB_UPDATE=true` (auto-creates/updates on startup).
   - In **production**: `FLOWABLE_DB_UPDATE=false`. Run the official Flowable SQL scripts
     from the Flowable release package before deploying a new Flowable version.

## Adding Flyway in the Future

To re-enable Flyway:

1. Write a baseline migration `V1__baseline.sql` that creates the entire schema as-is.
2. Set `spring.flyway.baseline-on-migrate: true` and `spring.flyway.baseline-version: 1`
   on the first run against an existing database.
3. Change `spring.flyway.enabled: true` in `application.yml`.
4. Add `flyway-core` dependency to `pom.xml` if not already present.

All subsequent schema changes should be added as versioned Flyway migrations
(e.g. `V2__add_column_x.sql`) rather than modifying entities directly.

## Column and Table Naming Convention

- **Table names** = Java class name (exact camelCase, e.g. `MaterialStockKeepUnit`)
- **Column names** = Java field name (exact camelCase, e.g. `parentNodeUUID`)
- **Schema per module**: `platform`, `finance`, `logistics`, `sales`, `production`

No renaming strategy is applied — Hibernate uses the field/class names verbatim.
