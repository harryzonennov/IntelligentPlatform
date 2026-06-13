# Skill: check-log

Full autonomous debug loop: fix errors, rebuild, restart, trigger the endpoint, and repeat until clean.

## Overview

This skill runs a complete fix→build→restart→test loop for `/purchaseContract/searchTableService`.
Repeat the full loop until the endpoint returns a clean response with no new ERRORs in the log.

If an error cannot be resolved confidently at any step, STOP and ask the user before modifying code.

---

## Step 1 — Kill any running instance

```bash
lsof -ti tcp:8080 | xargs kill -9 2>/dev/null; sleep 2; echo "port 8080 clear"
```

---

## Step 2 — Read and deduplicate errors from the log

```bash
grep -E "^[0-9]{4}.*ERROR|^[0-9]{4}.*WARN.*(JpaServiceEntityDAO|queryBy)" \
  /Users/I043125/work2/IntelligentPlatform/logs/intelligentplatform.log \
  | tail -80
```

Deduplicate: if the same `Unknown column 'X'` or `UnknownEntityException: X` appears multiple times, treat it as **one** error. Extract only distinct error signatures.

If the log is empty or has no errors newer than the last fix cycle, skip to Step 4.

---

## Step 3 — Fix all distinct errors

Apply all fixes before rebuilding. Known patterns:

### A. `Unknown column 'X' in 'field list'`
Column missing from a table in a Hibernate UNION inheritance hierarchy.
- Check which table is missing the column from the SQL in the error message
- Find the column's type by checking the sibling table or the Java entity field
- Find the next migration version: `ls src/main/resources/db/migration/`
- Create and run: `src/main/resources/db/migration/Vn__add_missing_columns.sql`
- SQL-only fix: **no rebuild needed** for this fix alone, but if other fixes require rebuild, do one rebuild for all.

```bash
/usr/local/mysql-8.3.0-macos14-arm64/bin/mysql -u ip_user -pip_password platform \
  < src/main/resources/db/migration/Vn__add_missing_columns.sql
```

### B. `UnknownEntityException: Could not resolve root entity 'X'`
`@Entity` and/or `@Table` annotation missing from the Java class.
- Add `@Entity` and `@Table(name = "ClassName", catalog = "schema")` before the class declaration
- Add `import jakarta.persistence.Entity;` and `import jakarta.persistence.Table;`
- Schema mapping: common/platform→`platform`, finance→`finance`, logistics→`logistics`, sales→`sales`, production→`production`
- Requires rebuild.

### C. `NullPointerException` at known location
Add a null check before the offending call. Follow the existing null-check pattern in the same method.
- Requires rebuild.

### D. Any other error
Read the relevant source file and stack trace carefully. If the fix is clear, apply it.
If not confident, **STOP and ask the user**.

---

## Step 4 — Build

```bash
cd /Users/I043125/work2/IntelligentPlatform && \
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
mvn package -DskipTests -Dmaven.test.skip=true -q 2>&1 | tail -20
```

If build fails, read the compiler errors, fix them, and retry once. If still failing, STOP and report to user.

---

## Step 5 — Launch

```bash
mkdir -p /Users/I043125/work2/IntelligentPlatform/logs
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home \
java -jar /Users/I043125/work2/IntelligentPlatform/target/IntelligentPlatform-1.0.0-SNAPSHOT.jar \
  > /Users/I043125/work2/IntelligentPlatform/logs/intelligentplatform.log 2>&1 &
echo "PID: $!"
```

Wait for startup — poll every 5 seconds, up to 120 seconds:
```bash
grep -E "Started IntelligentPlatformApplication|Application run failed" \
  /Users/I043125/work2/IntelligentPlatform/logs/intelligentplatform.log | tail -1
```

If `Application run failed`, read the first 10 ERROR lines and STOP.

---

## Step 6 — Login and get JWT token

```bash
curl -s -X POST http://localhost:8080/common/loginService \
  -H "Content-Type: application/json" \
  -H "accept-language: zh-CN" \
  -d '{"userId":"i00106","password":"e10adc3949ba59abbe56e057f20f883e","client":"001","languageCode":"zh-CN"}' 
```

Extract the JWT token from the response (field `token` or from the content object). Save it as `$TOKEN`.

---

## Step 7 — Trigger the endpoint

The endpoint to test depends on the skill invocation context. Default is `searchTableService`.

**searchTableService** (default):
```bash
curl -s -X POST http://localhost:8080/purchaseContract/searchTableService \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"client":"001","languageCode":"zh-CN","pageIndex":0,"pageSize":20}'
```

**loadModuleEditService** (when testing edit load by UUID):
```bash
curl -s "http://localhost:8080/purchaseContract/loadModuleEditService?uuid=<UUID>" \
  -H "Authorization: Bearer $TOKEN"
```

---

## Step 8 — Check for new errors

Wait 3 seconds, then:
```bash
sleep 3 && grep -E "ERROR|WARN.*(JpaServiceEntityDAO|queryBy)" \
  /Users/I043125/work2/IntelligentPlatform/logs/intelligentplatform.log | tail -40
```

- If **no new ERRORs** and the endpoint returned data → **DONE**. Report success to user.
- If **new ERRORs found** → go back to **Step 3**, fix them, then repeat from **Step 4**.
- Maximum 10 iterations. If still failing after 10, STOP and report all remaining errors to user.

---

## Reference

- Log file: `/Users/I043125/work2/IntelligentPlatform/logs/intelligentplatform.log`
- MySQL: `/usr/local/mysql-8.3.0-macos14-arm64/bin/mysql -u ip_user -pip_password platform`
- Migration dir: `src/main/resources/db/migration/`
- JAVA_HOME: `/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home`
- Login credentials: userId=`i00106`, password=`e10adc3949ba59abbe56e057f20f883e`, client=`001`
