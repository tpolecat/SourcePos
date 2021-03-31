// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.sourcepos

import scala.reflect.macros.blackbox

trait SourcePosPlatform {
  implicit def instance: SourcePos =
    macro SourcePosPlatform.sourcePos_impl
}

object SourcePosPlatform {
  def sourcePos_impl(c: blackbox.Context): c.Tree = {
    import c.universe._
    val file = c.enclosingPosition.source.path
    val line = c.enclosingPosition.line
    q"_root_.org.tpolecat.sourcepos.SourcePos($file, $line)"
  }
}
