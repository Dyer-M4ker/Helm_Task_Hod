# CLI Calculator

Simple Java command line calculator packaged as an executable JAR.

## Build

```powershell
mkdir -Force out\classes
javac -d out\classes src\main\java\com\example\calculator\*.java
jar --create --file out\calculator-cli.jar --main-class com.example.calculator.CalculatorCLI -C out\classes .
```

## Usage

```powershell
java -jar out\calculator-cli.jar add 4 5
```

Supported operations: `add`, `subtract`, `multiply`, `divide`.

Display help:

```powershell
java -jar out\calculator-cli.jar --help
```
