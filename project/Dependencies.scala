import sbt._

object Dependencies {
  type Version = String
  type Modules = Seq[ModuleID]

  object Versions {
    val zio: Version        = "2.0.4"
    val zioLogging: Version = "2.1.5"
  }

  lazy val logging: Modules = Seq(
    "ch.qos.logback" % "logback-classic" % "1.4.5"
    // "dev.zio"       %% "zio-logging-jpl" % "2.1.5"
  ) ++ Seq(
    "dev.zio" %% "zio-logging",
    "dev.zio" %% "zio-logging-slf4j"
  ).map(_ % Versions.zioLogging)

  lazy val zio: Modules = Seq(
    "dev.zio" %% "zio",
    "dev.zio" %% "zio-streams",
    "dev.zio" %% "zio-macros"
  ).map(_ % Versions.zio) ++ Seq(
    "dev.zio" %% "zio-cli"  % "0.2.8",
    "dev.zio" %% "zio-http" % "0.0.3"
  ) ++ Seq(
    "dev.zio" %% "zio-test",
    "dev.zio" %% "zio-test-sbt",
    "dev.zio" %% "zio-test-junit",
    "dev.zio" %% "zio-test-magnolia"
  ).map(_ % Versions.zio % Test)

  lazy val libOs: Modules = Seq(
    "com.lihaoyi" %% "os-lib" % "0.8.1"
  )

  lazy val httpClient: Modules = Seq(
    // "org.apache.httpcomponents.client5" % "httpclient5"     % "5.2",
    "org.apache.httpcomponents" % "httpclient"      % "4.5.13" exclude ("commons-logging", "commons-logging"),
    "org.apache.httpcomponents" % "httpasyncclient" % "4.1.5" exclude ("commons-logging", "commons-logging"),
    "org.slf4j"                 % "jcl-over-slf4j"  % "2.0.4"
  )

  lazy val projectResolvers: Seq[MavenRepository] = Seq(
    "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
    "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "Sonatype staging" at "https://oss.sonatype.org/content/repositories/staging",
    "Java.net Maven2 Repository" at "https://download.java.net/maven/2/"
  )
}
