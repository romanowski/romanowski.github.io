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
  def execute(task: Callable[_]): Unit = execute(new Runnable {
    override def run(): Unit = task.call()
  })
}

object PG extends App {
  val forkJoin = new ForkJoinPool() with LoggingExecutor
  val threadPool = new ScheduledThreadPoolExecutor(4) with LoggingExecutor


  val test = new Executor with LoggingExecutor

  test.execute("ala", new Runnable {
    override def run(): Unit = println("Ala")
  })
  val uris = new File("resources").listFiles.map(_.toURI)

}