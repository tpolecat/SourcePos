// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import org.tpolecat.sourcepos._

object Example {

  def method(n: Int)(implicit sp: SourcePos): String =
    s"You called me with $n from $sp"

  def main(args: Array[String]): Unit =
    println(method(42))

}