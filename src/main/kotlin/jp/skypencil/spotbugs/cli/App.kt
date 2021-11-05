/* Copyright (C) 2021 Kengo TODA */
package jp.skypencil.spotbugs.cli

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.nio.file.Path
import java.util.concurrent.Callable
import kotlin.system.exitProcess

// TODO https://github.com/remkop/picocli/blob/master/picocli-examples/src/main/java/picocli/examples/VersionProviderDemo2.java
@Command(
    name = "spotbugs", version = ["experimental"],
    mixinStandardHelpOptions = true, // add --help and --version options
    description = ["An experimental CLI for SpotBugs"]
)
class App : Callable<Int> {
    @Option(
        names = ["--include"],
        description = ["file path of include filter file"]
    )
    lateinit var include: Path

    @Option(
        names = ["--exclude"],
        description = ["file path of exclude filter file"]
    )
    lateinit var exclude: Path

    @Option(
        names = ["--baseline"],
        description = ["file path of baseline file"]
    )
    lateinit var baseline: Path

    @Option(
        names = ["--plugin"],
        description = ["file path of plugin"]
    )
    lateinit var plugins: List<Path>

    @Option(
        names = ["--xml"],
        description = ["file path of XML file to generate"]
    )
    lateinit var xmlReport: Path

    @Option(
        names = ["--html"],
        description = ["file path of HTML file to generate"]
    )
    lateinit var htmlReport: Path

    @Option(
        names = ["--sarif"],
        description = ["file path of SARIF file to generate"]
    )
    lateinit var sarifReport: Path

    @Option(
        names = ["--emacs"],
        description = ["file path of EMACS file to generate"]
    )
    lateinit var emacsReport: Path

    @Option(
        names = ["--xdocs"],
        description = ["file path of xdoc XML file to generate"]
    )
    lateinit var xdocsReport: Path

    @Parameters(description = ["The target file to analyse"])
    lateinit var target: List<Path>

    override fun call(): Int {
        println(plugins.joinToString(","))
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            exitProcess(CommandLine(App()).execute(*args))
        }
    }
}
