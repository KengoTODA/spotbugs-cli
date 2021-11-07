/* Copyright (C) 2021 Kengo TODA */
package jp.skypencil.spotbugs.cli

import kotlin.test.Test

class AppTest {
  @Test
  fun printHelp() {
    App.Companion.main(arrayOf("--help"))
  }

  @Test
  fun printVersion() {
    App.Companion.main(arrayOf("--version"))
  }
}
