# Experimental spotbugs-cli

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8c4167f5920a4c19b884f79df6c88039)](https://app.codacy.com/gh/KengoTODA/spotbugs-cli?utm_source=github.com&utm_medium=referral&utm_content=KengoTODA/spotbugs-cli&utm_campaign=Badge_Grade_Settings)
[![Build](https://github.com/KengoTODA/spotbugs-cli/actions/workflows/build.yml/badge.svg)](https://github.com/KengoTODA/spotbugs-cli/actions/workflows/build.yml)
[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-908a85?logo=gitpod)](https://gitpod.io/#https://github.com/KengoTODA/spotbugs-cli)

An experimental CLI for SpotBugs based on picocli. It is expected to solve two problems:

1. Make command line arguments and options following the [Command Line Interface Guidelines](https://clig.dev/).
2. Build native binaries with [Picocli on GraalVM](https://picocli.info/picocli-on-graalvm.html).

## How to run the native image

```shell
export JAVA_HOME=path/to/graalvm-21.1.0
export JAVA8_HOME=path/to/jdk8
./gradlew nativeImage
build/executable/spotbugs \
  -Djava.home=$JAVA8_HOME \
  --aux $JAVA8_HOME/jre/lib/rt.jar \
  build/libs/spotbugs-cli-1.0.0-SNAPSHOT.jar
```

## WIP: Command line option and argument

```
--help
--version
--include spotbugs-include.xml
--exclude spotbugs-exclude.xml
--baseline spotbugs-baseline.xml
--xml build/reports/spotbugs/main.xml
--html build/reports/spotbugs/main.html
--sarif build/reports/spotbugs/main.sarif
--xdocs build/reports/spotbugs/main.xdocs
--emacs build/reports/spotbugs/main.emacs
--detector OverridingMethodsMustInvokeSuperDetector
--aux path/to/dependencies/*.jar
build/libs/target-to-analyse.jar
```

## Copyright

Copyright &copy; 2020-2021 Kengo TODA
