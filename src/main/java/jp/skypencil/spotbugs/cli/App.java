/* Copyright (C) 2020 Kengo TODA */
package jp.skypencil.spotbugs.cli;

public class App {
  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
  }
}
