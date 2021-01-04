// Copyright (c) 2020 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package org.tpolecat.sourcepos

final case class SourcePos(file: String, line: Int) {
  override def toString =
    s"$file:$line"
}

object SourcePos extends SourcePosPlatform