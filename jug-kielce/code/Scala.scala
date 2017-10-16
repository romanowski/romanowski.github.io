package myScala

import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledThreadPoolExecutor

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
  def execute(task: Callable[_]): Unit = execute(() => task.call())
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

  List("A").map(toUpperCase)
  List("A").map(toUpperCase)
}