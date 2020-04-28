package web.java.datastructures.lru.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import web.java.datastructures.lru.LRUCache;

public class LRUCacheTest {

  public static final Integer CAPACITY = 5;

  @Test
  public void canHandleMoreThanCapacity() throws InterruptedException {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, 100).parallel().forEach(entry -> {
      System.out.println(String.format("Entry [key: %s]", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    Assert.assertEquals(CAPACITY.intValue(), lruCache.capacity().intValue());
  }

  @Test
  public void canRemoveExpectedEntry() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry [key: %s]", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    lruCache.fetch(String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    lruCache.fetch("1");
    System.out.println(lruCache);
    lruCache.store(String.valueOf(CAPACITY + 1), String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    Assert.assertEquals(CAPACITY.intValue(), lruCache.capacity().intValue());
  }

  @Test
  public void hasHits() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry [key: %s]", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(lruCache.fetch("1"));
      System.out.println(lruCache);
    });
    Assert.assertEquals(CAPACITY.intValue(), lruCache.cacheHits().intValue());
  }

  @Test
  public void hasMiss() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry [key: %s]", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    lruCache.fetch(String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    Assert.assertEquals(1, lruCache.cacheMiss().intValue());
  }

  // recommended to run in shell: ./mvnw test -Dtest=LRUCacheTest#parallelPutAndGet
  @Test
  public void parallelPutAndGet() throws InterruptedException {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    final Integer MAX_WORKERS = 10000;
    final ExecutorService pool = Executors.newFixedThreadPool(MAX_WORKERS * 2);
    final CompletableFuture<Void> futures =
        CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> {
          IntStream.rangeClosed(1, MAX_WORKERS).parallel().forEach(entry -> {
            System.out.println(String.format("Inserting entry [key: %s]", entry));
            lruCache.store(String.valueOf(entry), String.valueOf(entry));
          });
          return "insert complete";
        }, pool), CompletableFuture.supplyAsync(() -> {
          IntStream.rangeClosed(1, MAX_WORKERS).parallel().forEach(entry -> {
            System.out.println(String.format("Searching entry [key: %s]", entry));
            lruCache.fetch(String.valueOf(entry));
          });
          return "fetch complete";
        }, pool));
    while (!futures.isDone()) {
      // nothing to do.. chill!!
    }
    pool.shutdown();
    pool.awaitTermination(60L, TimeUnit.SECONDS);
    System.out.println(lruCache);
    Assert.assertEquals(MAX_WORKERS.intValue(),
        lruCache.cacheHits().intValue() + lruCache.cacheMiss().intValue());
  }

  @Rule
  public Stopwatch stopwatch = new Stopwatch() {
    @Override
    protected void succeeded(final long nanos, final Description description) {
      System.out.println(String.format("%s %s %s ms", description, "succeeded",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void failed(final long nanos, final Throwable e, final Description description) {
      System.out.println(String.format("%s %s %s ms", description, "failed",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void skipped(final long nanos, final AssumptionViolatedException e,
        final Description description) {
      System.out.println(String.format("%s %s %s ms", description, "skipped",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void finished(final long nanos, final Description description) {
      System.out.println(String.format("%s %s %s ms", description, "finished",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }
  };

}


