# Experimental spotbugs-cli
An experimental CLI for SpotBugs based on picocli. It is expected to solve two problems:

1. Make command line arguments and options following the [Command Line Interface Guidelines](https://clig.dev/).
2. Build native binaries with [Picocli on GraalVM](https://picocli.info/picocli-on-graalvm.html).

## WIP: Command line option and argument

```
--help
--version
--include spotbugs-include.xml
--exclude spotbugs-exclude.xml
--baseline spotbugs-baseline.xml
--plugin path/to/sbcontrib.jar
--xml build/reports/spotbugs/main.sarif
--html build/reports/spotbugs/main.sarif
--sarif build/reports/spotbugs/main.sarif
--xdocs build/reports/spotbugs/main.sarif
--emacs build/reports/spotbugs/main.sarif
build/libs/target-to-analyse.jar
```

## Copyright

Copyright &copy; 2020-2021 Kengo TODA
