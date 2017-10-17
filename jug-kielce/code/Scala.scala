package myScala

import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledThreadPoolExecutor

import scala.math.Ordering

trait LoggingExecutor  {
  self: Executor =>
  private var _logs = List.empty[String]
  def execute(name: String, operation: Runnable): Unit = {
    synchronized(_logs +:= name)
    self.execute(operation)
  }

  override def execute(command: Runnable): Unit = execute(command.toString, command)

  def logs(): List[String] = _logs
}

trait CallableExecutor extends Executor {
  def execute(task: Callable[_]): Unit = execute(() => task.call(): Unit)
}

object PG extends App {
  val forkJoin = new ForkJoinPool() with LoggingExecutor
  val threadPool = new ScheduledThreadPoolExecutor(4) with LoggingExecutor


  val test = new Executor with LoggingExecutor

  test.execute("ala", () => println("Ala"))
  val uris = new File("resources").listFiles.map(_.toURI)


  val bold = (a: String) => s"**$a**"
  val h1 = (a: String) => s"#$a"
  val boldH1 = h1 andThen bold

  /*List("A").map(toUpperCase)
  List("A").map(toUpperCase)*/
}

object TClasses {
  trait Printer[A]{ def pretty(a: A): String }
  implicit object StringPrinter extends Printer[String] {
    override def pretty(a: String): String = a
  }
  implicit object IntPrinter extends Printer[Int] {
    override def pretty(a: Int): String = a.toString
  }

  // Java style, boring...
  def prettyLong[A](a: A, printer: Printer[A]) = printer.pretty(a)

  // Now you talking...
  def prettyNice[A: Printer](a: A) = implicitly[Printer[A]].pretty(a)

  prettyNice("Ala")
  prettyNice(1)

  implicit def arrayPretty[A: Printer] = new Printer[Array[A]] {
    override def pretty(a: Array[A]): String = a.map(e => prettyNice(e)).mkString("{", ", ", "}")
  }

  prettyNice(Array("a", "n"))
  //prettyNice(Array(false)) // This should not compile...

  case class User(name: String, age: Int)
  implicit object UserPrinter extends Printer[User]{
    override def pretty(a: User): String = s"${a.name}, ${a.age} years old"
  }

  prettyNice(Array(User("Krzys", 18)))


}

