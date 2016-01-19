import android.Keys._

android.Plugin.androidBuild
platformTarget in Android := "android-23"

packagingOptions in Android := PackagingOptions(
  Seq.empty[String],
  Seq("reference.conf"),
  Seq.empty[String])

name := "macroid-for-habr"

scalaVersion := "2.11.7"
javacOptions ++= Seq("-target", "1.7", "-source", "1.7")

// a shortcut
run <<= run in Android

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "jcenter" at "http://jcenter.bintray.com"
)

// add linter
scalacOptions in (Compile, compile) ++=
  (dependencyClasspath in Compile).value.files.map("-P:wartremover:cp:" + _.toURI.toURL) ++
    Seq("-P:wartremover:traverser:macroid.warts.CheckUi")

libraryDependencies ++= Seq(
  aar("org.macroid" %% "macroid" % "2.0.0-M4"),
  aar("org.macroid" %% "macroid-viewable" % "2.0.0-M4"),
  "com.android.support" % "support-v4" % "23.1.1",
  compilerPlugin("org.brianmckenna" %% "wartremover" % "0.11")
)

proguardScala in Android := true

// Generic ProGuard rules
//proguardOptions in Android ++= Seq(
//  "-ignorewarnings",
//  "-keep class scala.Dynamic"
//)


