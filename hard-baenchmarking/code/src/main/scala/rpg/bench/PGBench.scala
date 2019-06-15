package rpg.bench

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = Array("-Xms2G", "-Xmx2G"))
class PGBenchBasic {
  @Benchmark
  def splitByChar(bh: Blackhole): Unit = {
    "Ala ma kota".split(' ')
  }

  @Benchmark
  def splitByString(bh: Blackhole): Unit = {
    "Ala ma kota".split(" ")
  }

  @Benchmark
  def splitByRegEx(bh: Blackhole): Unit = {
    "Ala ma kota".split(" +")
  }

  @Benchmark
  def splitByReg2(bh: Blackhole): Unit = {
    "Ala".split(" +")
  }
}


object Strings {
  val longString =
    """Welcome!
      |This is the official repository for the Scala Programming Language standard library, compiler, and language spec.
      |How to contribute
      |To contribute in this repo, please open a pull request from your fork of this repository.
    """.stripMargin
}


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = Array("-Xms2G", "-Xmx2G"))
class PGBenchLong {
  import Strings._

  @Benchmark
  def splitByChar(): Unit = {
    longString.split(' ')
    "Ala ma kota".split(' ')
  }

  @Benchmark
  def splitByString(): Unit = {
    longString.split(" ")
    "Ala ma kota".split(" ")

  }

  @Benchmark
  def splitByRegEx(): Unit = {
    longString.split(" +")
    "Ala ma kota".split(" +")
  }
}


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = Array("-Xms2G", "-Xmx2G"))
class PGBenchBH {

  import Strings._

  @Benchmark
  def splitByChar(bh: Blackhole): Unit = {
    bh.consume(longString.split(' '))
    bh.consume("Ala ma kota".split(' '))
  }

  @Benchmark
  def splitByString(bh: Blackhole): Unit = {
    bh.consume(longString.split(" "))
    bh.consume("Ala ma kota".split(" "))
  }

  @Benchmark
  def splitByRegEx(bh: Blackhole): Unit = {
    bh.consume(longString.split(" +"))
    bh.consume("Ala ma kota".split(" +"))
  }
}