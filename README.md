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

`Jenkinsfile.cd` implements a basic CD pipeline that expects Docker and Harbor access on the Jenkins agent:

1. Logs in to Harbor using stored credentials.
2. Pulls an image tag from `harbor.local/calculator/calculator-cli`.
3. Re-tags and pushes the image to a designated test project (default `calculator-test`).
4. Launches the container and verifies the calculator CLI returns the expected result.

Provide Jenkins parameters:

- `IMAGE_TAG` (default `latest`) – the Harbor tag to deploy.
- `TEST_PROJECT` – Harbor project used for the promoted tag.

Create a Jenkins credential (`username/password`) with ID `harbor-robot` referencing a Harbor robot account.

Refer to `docs/harbor-setup.md` for a Harbor installation walkthrough.

## Usage

```powershell
java -jar build\libs\calculator-cli.jar add 4 5
```

Supported operations: `add`, `subtract`, `multiply`, `divide`.

Show usage:

```powershell
java -jar build\libs\calculator-cli.jar --help
```
