import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

class Test {
  void test(){
      List<URI> urlsList = new ArrayList<>();
      for (File resource : new File("resources").listFiles()) {
        urlsList.add(resource.toURI());
      }
      URI[] uris = urlsList.toArray(new URI[urlsList.size()]);
      // This will also compile...
      URL[] urls = urlsList.toArray(new URL[urlsList.size()]);



      CallableExecutor forkJoin = new LoggingForkJoinPool();
  }
}

interface CallableExecutor extends Executor {
  public void execute(Callable<?> operation);
}

class LoggingForkJoinPool
  extends ForkJoinPool implements CallableExecutor {
  @Override
  public void execute(Callable<?> operation) {
    execute(new Runnable() {
      @Override
      public void run() {
        try {
          operation.call();
        } catch (Exception e){
          e.printStackTrace();
        }
      }
    });
  }
}


