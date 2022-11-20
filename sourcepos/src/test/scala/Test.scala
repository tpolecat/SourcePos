// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package test

import org.tpolecat.sourcepos._

class Test extends munit.FunSuite {

  test("pos") {
    val pos = implicitly[SourcePos]
    assert(clue(pos.file).endsWith("sourcepos/src/test/scala/Test.scala"))
    assertEquals(pos.line, 12)
  }

}