
val sharedSettings = Seq(
  organization := "com.lihaoyi",
  scalaVersion := "2.11.5",
  crossScalaVersions := Seq("2.10.4", "2.11.4"),
  version := "0.1.0",
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.2.4" % "test",
  testFrameworks += new TestFramework("utest.runner.JvmFramework"),
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
  publishTo := Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),
  pomExtra :=
    <url>https://github.com/lihaoyi/Ammonite</url>
      <licenses>
        <license>
          <name>MIT license</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/lihaoyi/Ammonite.git</url>
        <connection>scm:git://github.com/lihaoyi/Ammonite.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lihaoyi</id>
          <name>Li Haoyi</name>
          <url>https://github.com/lihaoyi</url>
        </developer>
      </developers>
)

lazy val sh = project.dependsOn(core).settings(sharedSettings:_*).settings(
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "jline" % "jline" % "2.12",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "com.lihaoyi" %% "scala-parser-lite" % "0.1.0"
  )
)

lazy val core = project.settings(sharedSettings:_*).settings(
  name := "ammonite",
  initialCommands in console := """
    import ammonite._
    var wd = cwd
  """
)
