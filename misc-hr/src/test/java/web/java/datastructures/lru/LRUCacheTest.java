package web.java.datastructures.lru;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class LRUCacheTest {

  public static final Integer CAPACITY = 5;

  @Test
  public void canHandleMoreThanCapacity() throws InterruptedException {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, 10000).parallel().forEach(entry -> {
      System.out.println(String.format("Entry { key: %s }", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    Assert.assertSame(CAPACITY, lruCache.capacity());
  }

  @Test
  public void canRemoveExpectedEntry() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry { key: %s }", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    lruCache.fetch(String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    lruCache.fetch("1");
    System.out.println(lruCache);
    lruCache.store(String.valueOf(CAPACITY + 1), String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    Assert.assertSame(CAPACITY, lruCache.capacity());
  }

  @Test
  public void hasHits() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry { key: %s }", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    System.out.println(lruCache);
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(lruCache.fetch("1"));
      System.out.println(lruCache);
    });
    Assert.assertSame(CAPACITY, lruCache.cacheHits());
  }

  @Test
  public void hasMiss() {
    final LRUCache lruCache = LRUCache.builder().capacity(CAPACITY).build();
    IntStream.rangeClosed(1, CAPACITY).forEach(entry -> {
      System.out.println(String.format("Entry { key: %s }", entry));
      lruCache.store(String.valueOf(entry), String.valueOf(entry));
    });
    lruCache.fetch(String.valueOf(CAPACITY + 1));
    System.out.println(lruCache);
    Assert.assertSame(1, lruCache.cacheMiss());
  }

  @Rule
  public Stopwatch stopwatch = new Stopwatch() {
    @Override
    protected void succeeded(long nanos, Description description) {
      System.out.println(String.format("%s %s %s ms", description, "succeeded",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
      System.out.println(String.format("%s %s %s ms", description, "failed",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
      System.out.println(String.format("%s %s %s ms", description, "skipped",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void finished(long nanos, Description description) {
      System.out.println(String.format("%s %s %s ms", description, "finished",
          TimeUnit.NANOSECONDS.toMillis(nanos)));
    }
  };

}


