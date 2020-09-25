/* Copyright (C) 2020 Kengo TODA */
package jp.skypencil.spotbugs.cli;

import picocli.CommandLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class App implements Callable<Integer> {
  @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
  private boolean helpRequested = false;

  @CommandLine.Option(names = { "-v", "--version" }, description = "display the version of SpotBugs")
  private boolean versionRequested = false;

  @CommandLine.Option(names = { "-X" }, required = false, description = "Java Virtual Machine option")
  private List<String> jvmOptions = new ArrayList<>();

  @CommandLine.Option(names = { "-D" }, description = "Java system property")
  private Map<String, String> systemProperties = new HashMap<>();

  @CommandLine.Option(names = {"-e", "--effort"}, defaultValue = "DEFAULT", description = "Analysis effort level")
  private Effort effort = Effort.DEFAULT;

  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) {
    int statusCode = new CommandLine(new App()).setOptionsCaseInsensitive(true).execute("--effort", "min", "-Xmx20m");
    System.exit(statusCode);
  }

  @Override
  public Integer call() {
    if (helpRequested) {
      System.out.println("help!");
    }
    if (versionRequested) {
      System.out.println("version!");
    }
    System.out.printf("effort level is %s", effort);
    if (jvmOptions != null) {
      jvmOptions.forEach(option -> System.out.printf("jvm option: %s", option));
    }
    if (systemProperties != null) {
      systemProperties.forEach(
          (key, value) -> System.out.printf("Java system property: %s => %s", key, value));
    }
    return 0;
  }
}
