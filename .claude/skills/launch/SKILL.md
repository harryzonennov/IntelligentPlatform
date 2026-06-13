---
name: launch
description: Build and launch the IntelligentPlatform Spring Boot project. If already running on port 8080, just report the URL.
---

Build and launch the IntelligentPlatform Spring Boot project.

## Step 1 — Check if already running

Run:
```bash
lsof -ti tcp:8080
```

If a process is found on port 8080, the app is already running. Print:

```
Already running: http://localhost:8080
```

And stop here — do not rebuild or relaunch.

## Step 2 — Build

Run the Maven build with Java 17, skipping tests:

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home mvn package -DskipTests -Dmaven.test.skip=true -q
```

If the build fails (non-zero exit code), report the error and stop.

## Step 3 — Launch

Ensure the logs directory exists, then launch the jar in the background:

```bash
mkdir -p logs
JAVA_HOME=/Library/Java/JavaVirtualMachines/sapmachine-jdk-17.0.2.jdk/Contents/Home java -jar target/IntelligentPlatform-1.0.0-SNAPSHOT.jar > logs/intelligentplatform.log 2>&1 &
echo $!
```

Note the PID printed.

## Step 4 — Wait for startup

Poll `logs/intelligentplatform.log` until one of these lines appears:

- `Started IntelligentPlatformApplication` → success
- `Application run failed` → failure

Check every 5 seconds, up to 120 seconds total.

Use:
```bash
grep -E "Started IntelligentPlatformApplication|Application run failed" logs/intelligentplatform.log | tail -1
```

## Step 5 — Report result

If startup succeeded, print:

```
Running: http://localhost:8080
```

If startup failed, print the first ERROR line from the log:
```bash
grep "ERROR\|Exception" logs/intelligentplatform.log | head -5
```
