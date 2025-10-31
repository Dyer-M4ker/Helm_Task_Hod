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

## Usage

```powershell
java -jar build\libs\calculator-cli.jar add 4 5
```

Supported operations: `add`, `subtract`, `multiply`, `divide`.

Show usage:

```powershell
java -jar build\libs\calculator-cli.jar --help
```
