package java8;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Stream;

class Test {

  static String mapS(String a) {return  a;}


  public static void main(String[] args){
    URI[] uris = Arrays.stream(new File(".").listFiles())
      .map(f -> f.toURI()).toArray(URI[]::new);

    URL[] urls = Arrays.stream(new File(".").listFiles())
      .map(f -> f.toURI()).toArray(URL[]::new);
  }

  void test() {
    Stream<URI> uris = Arrays.stream(new File(".").listFiles()).map(File::toURI);

    URL[] urls = uris.toArray(URL[]::new);

    // This will also compile
  /*  URL[] urls = Arrays.stream(new File(".").listFiles())
      .map(f -> f.toURI()).toArray(URL[]::new);
*/

    CallableExecutor forkJoin = new CallableFJP();
    CallableExecutor threadPool = new CallableSTPE();

    Function<String, String> bold = a -> "*" + a + "*";
    Function<String, String> h1 = a -> "#" + a;
    Function<String, String> boldH1 = bold.andThen(h1);




/*
    Stream.of("a").map(toUpperCase);
    Stream.of("a").map(toUpperCase);
*/

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