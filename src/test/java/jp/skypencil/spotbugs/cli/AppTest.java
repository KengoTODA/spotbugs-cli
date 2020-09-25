/* Copyright (C) 2020 Kengo TODA */
package jp.skypencil.spotbugs.cli;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {
  @Test
  void appHasAGreeting() {
    App classUnderTest = new App();
    assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
  }
}
