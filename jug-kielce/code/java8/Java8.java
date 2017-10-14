package java8;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;

class Test {
  void test() {
    URI[] uris = Arrays.stream(new File("resources").listFiles())
      .map(f -> f.toURI()).toArray(URI[]::new);

    // This will also compile
    URL[] urls = Arrays.stream(new File("resources").listFiles())
      .map(f -> f.toURI()).toArray(URL[]::new);


    CallableExecutor forkJoin = new CallableFJP();
    CallableExecutor threadPool = new CallableSTPE();
  }
}

interface CallableExecutor extends Executor {
  default void execute(Callable<?> operation) {
    execute(operation::call);
  }
}

class CallableFJP extends ForkJoinPool implements CallableExecutor {}
class CallableSTPE extends ScheduledThreadPoolExecutor implements CallableExecutor {
  public CallableSTPE() {
    super(4);
  }
}

