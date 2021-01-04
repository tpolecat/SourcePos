# SourcePos

This is a Scala micro-library that provides:

- a `SourcePos` class that specifies a source file and line number, and
- an implicit macro that forges a `SourcePos` at the call site if none is available.

SourcePos is compiled for Scala **2.12**, **2.13**, and **3.0** (see release notes for exact versions).


```scala
libraryDependencies += "org.tpolecat" %% "sourcepos" % <version>
```

#### Example

If you want to know where a method call originated, demand an implicit `SourcePos`. It has fields for both `file` and `line`, and a `toString` that prints them as `<file>:<line>`, which most editors will hyperlink for you.

```scala
import org.tpolecat.sourcepos._

object Example {

  def method(n: Int)(implicit sp: SourcePos): String =
    s"You called me with $n from $sp"

  def main(args: Array[String]): Unit =
    println(method(42))

}
```

Running this program on my computer yields:

```
You called me with 42 from /Users/rnorris/Scala/SourcePos/src/test/scala/Example.scala:9
```


