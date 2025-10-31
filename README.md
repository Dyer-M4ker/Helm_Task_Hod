# CLI Calculator

Simple Java command line calculator packaged as an executable JAR.

## Build

Run the Gradle wrapper to compile, test, and package the CLI:

```powershell
.\gradlew clean build
```

The runnable JAR is produced at `build\libs\calculator-cli.jar`.

Run tests independently:

```powershell
.\gradlew test
```

## Docker Image

```powershell
docker build -t calculator-cli .
docker run --rm calculator-cli add 4 5
```

## Continuous Integration (CI)

`Jenkinsfile` defines the CI pipeline:

- Resolves dependencies with the Gradle wrapper.
- Runs unit tests and publishes JUnit/Jacoco reports.
- Archives the application JAR.

Configure the Jenkins toolchain with a JDK 17 installation labeled `Temurin-17` and install the JaCoCo plugin.

## Continuous Delivery (CD)

`Jenkinsfile.cd` promotes images hosted on Docker Hub:

1. Logs in with credentials stored in Jenkins (ID `dockerhub-credentials`).
2. Pulls a source image tag (default `latest`) from `docker.io/dyer-m4ker/helm-task-hod`.
3. Re-tags and pushes it with a build-specific test tag (e.g., `test-42`).
4. Runs the container to smoke-test the calculator output via `scripts/smoke-test.ps1`.

Pipeline parameters:

- `IMAGE_TAG` – Docker Hub tag to promote.
- `TEST_TAG` – Prefix used for the generated test tag (`<TEST_TAG>-<BUILD_NUMBER>`).

Set up Docker Hub access following `docs/dockerhub-setup.md`.

### Smoke Test Script

`scripts/smoke-test.ps1` launches the promoted image (`add 2 3`) and validates the expected response. Jenkins invokes it during the CD pipeline to keep the Groovy pipeline definition simple.

## Usage

```powershell
java -jar build\libs\calculator-cli.jar add 4 5
```

Supported operations: `add`, `subtract`, `multiply`, `divide`.

Show usage:

```powershell
java -jar build\libs\calculator-cli.jar --help
```
