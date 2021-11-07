/* Copyright (C) 2021 Kengo TODA */
package jp.skypencil.spotbugs.cli

import edu.umd.cs.findbugs.BugCollectionBugReporter
import edu.umd.cs.findbugs.BugRanker
import edu.umd.cs.findbugs.DetectorFactoryCollection
import edu.umd.cs.findbugs.ExitCodes
import edu.umd.cs.findbugs.FindBugs2
import edu.umd.cs.findbugs.Plugin
import edu.umd.cs.findbugs.Priorities
import edu.umd.cs.findbugs.Project
import edu.umd.cs.findbugs.config.UserPreferences
import java.nio.file.Path
import java.util.concurrent.Callable
import kotlin.system.exitProcess
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

// TODO
// https://github.com/remkop/picocli/blob/master/picocli-examples/src/main/java/picocli/examples/VersionProviderDemo2.java
@Command(
    name = "spotbugs",
    version = ["experimental"],
    mixinStandardHelpOptions = true, // add --help and --version options
    description = ["An experimental CLI for SpotBugs"])
class App : Callable<Int> {
  @Option(names = ["--include"], description = ["file path of include filter file"])
  private var include: List<Path> = listOf()

  @Option(names = ["--exclude"], description = ["file path of exclude filter file"])
  private var exclude: List<Path> = listOf()

  @Option(names = ["--baseline"], description = ["file path of baseline file"])
  private var baseline: List<Path> = listOf()

  @Option(names = ["--plugin"], description = ["file path of plugin"])
  private var plugins: List<Path> = listOf()

  @Option(names = ["--xml"], description = ["file path of XML file to generate"])
  private var xmlReport: Path? = null

  @Option(names = ["--html"], description = ["file path of HTML file to generate"])
  private var htmlReport: Path? = null

  @Option(names = ["--sarif"], description = ["file path of SARIF file to generate"])
  private var sarifReport: Path? = null

  @Option(names = ["--emacs"], description = ["file path of EMACS file to generate"])
  private var emacsReport: Path? = null

  @Option(names = ["--xdocs"], description = ["file path of xdoc XML file to generate"])
  private var xdocsReport: Path? = null

  @Option(names = ["--aux"], description = ["Class and libraries to use as aux classpath"])
  // TODO support patterns like "*.jar"
  private var auxClasspathes: List<Path> = listOf()

  @Parameters(description = ["The target file to analyse"])
  private var target: List<Path> = listOf()

  override fun call(): Int {
    return FindBugs2().use { engine ->
      engine.setDetectorFactoryCollection(DetectorFactoryCollection.instance())
      createProject().use {
        val bugReporter = createBugReporter(it)

        engine.project = it
        engine.bugReporter = bugReporter
        engine.userPreferences = createUserPreferences()

        engine.execute()

        if (bugReporter.queuedErrors.isNotEmpty()) {
          bugReporter.reportQueuedErrors()
          throw AssertionError("Analysis failed with exception. Check stderr for detail.")
        }
        ExitCodes.from(engine.errorCount, engine.missingClassCount, engine.bugCount)
      }
    }
  }

  private fun createBugReporter(it: Project): BugCollectionBugReporter {
    val bugReporter = BugCollectionBugReporter(it)
    // TODO support BugReportDispatcher
    bugReporter.setPriorityThreshold(Priorities.LOW_PRIORITY)
    bugReporter.setRankThreshold(BugRanker.VISIBLE_RANK_MAX)
    return bugReporter
  }

  private fun createUserPreferences(): UserPreferences {
    val preferences: UserPreferences = UserPreferences.createDefaultUserPreferences()
    preferences.enableAllDetectors(true)
    return preferences
  }

  private fun createProject(): Project {
    val project = Project()
    plugins.forEach {
      val pluginId: String? = Plugin.addCustomPlugin(it.toUri())?.pluginId
      project.setPluginStatusTrinary(pluginId, true)
    }
    auxClasspathes.forEach { project.addAuxClasspathEntry(it.toAbsolutePath().toString()) }
    target.forEach { project.addFile(it.toAbsolutePath().toString()) }

    include.forEach {
      project.configuration.includeFilterFiles[it.toAbsolutePath().toString()] = true
    }
    exclude.forEach {
      project.configuration.excludeFilterFiles[it.toAbsolutePath().toString()] = true
    }
    baseline.forEach {
      project.configuration.excludeBugsFiles[it.toAbsolutePath().toString()] = true
    }
    return project
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      exitProcess(CommandLine(App()).execute(*args))
    }
  }
}
