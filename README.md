# Experimental spotbugs-cli
An experimental CLI for SpotBugs based on picocli. It is expected to solve two problems:

1. Make command line arguments and options following standard.
2. Build native binaries with [Picocli on GraalVM](https://picocli.info/picocli-on-graalvm.html).

## WIP: Command line option and argument

```
--help
--version
--include spotbugs-include.xml
--exclude spotbugs-exclude.xml
--baseline spotbugs-baseline.xml
--plugin path/to/sbcontrib.jar
--sarif build/reports/spotbugs/main.sarif
--text -
build/libs/target-to-analyse.jar
```

## Copyright

&copy; 2020-2021 Kengo TODA
