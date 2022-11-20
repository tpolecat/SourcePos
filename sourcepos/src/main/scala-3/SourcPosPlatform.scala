// Copyright (c) 2020-2021 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.sourcepos

import scala.quoted._

trait SourcePosPlatform {

  implicit inline def instance: SourcePos =
    ${SourcePosPlatform.sourcePos_impl}

}

object SourcePosPlatform {

  def sourcePos_impl(using ctx: Quotes): Expr[SourcePos] = {
    val rootPosition = ctx.reflect.Position.ofMacroExpansion
    val file = Expr(rootPosition.sourceFile.getJPath.toString)
    val line = Expr(rootPosition.startLine + 1)
    '{SourcePos($file, $line)}
  }

}
