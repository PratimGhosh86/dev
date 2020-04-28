package web.java.datastructures.lru;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LRUCache {

  private Integer capacity;

  private AtomicInteger cacheHits, cacheMiss;

  private BlockingDeque<String> keys;
  private ConcurrentMap<String, Object> data;

  final Consumer<String> removeAndPush =
      ((Consumer<String>) cacheKey -> keys.removeFirstOccurrence(cacheKey))
          .andThen(cacheKey -> keys.push(cacheKey));

  protected LRUCache() {}

  protected LRUCache(final Integer cap) {
    capacity = cap;
    cacheHits = new AtomicInteger(0);
    cacheMiss = new AtomicInteger(0);
    keys = new LinkedBlockingDeque<>(cap);
    data = new ConcurrentHashMap<>(cap);
  }

  public synchronized Boolean store(final String key, final Object value) {
    Boolean status = Boolean.FALSE;
    if (!data.containsKey(key)) {
      // no entry in cache, lets verify size
      if (keys.size() == capacity) {
        // reached maximum, need to remove least used entry i.e. the last entry
        final String removedItem = keys.removeLast();
        // now remove the actual value
        data.remove(removedItem);
        System.out.println(String.format("Entry removed successfully: [key: %s]", removedItem));
      }
      // else nothing to do yet
    }
    // cache entry exists, need to replace
    else {
      keys.removeFirstOccurrence(key);
      data.remove(key);
      System.out.println(String.format("Entry removed successfully: [key: %s]", key));
    }
    // insert into cache
    keys.push(key);
    data.put(key, value);
    System.out
        .println(String.format("Entry cached successfully: [key: %s, value: %s]", key, value));
    status = Boolean.TRUE;
    return status;
  }

  public Optional<Object> fetch(final String key) {
    Optional<Object> result = Optional.empty();
    if (data.containsKey(key)) {
      // match found, need to push element to front of queue
      removeAndPush.andThen(cacheKey -> cacheHits.incrementAndGet()).accept(key);
      result = Optional.of(data.get(key));
      System.out.println(String.format("Entry promoted successfully: [key: %s]", key));
    } else {
      cacheMiss.incrementAndGet();
      System.out.println(String.format("No matching entry for: [key: %s]", key));
    }
    return result;
  }

  public void clear() {
    keys.clear();
    data.clear();
  }

  public static LRUCacheBuilder builder() {
    return new LRUCacheBuilder();
  }

  // Getters/ToString
  public Deque<String> keys() {
    return keys;
  }

  public ConcurrentMap<String, Object> data() {
    return data;
  }

  public Integer capacity() {
    return capacity;
  }

  public Integer cacheHits() {
    return cacheHits.get();
  }

  public Integer cacheMiss() {
    return cacheMiss.get();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("LRUCache [capacity=").append(capacity).append(", keys=").append(keys)
        .append(", data=").append(data).append(", cacheHits=").append(cacheHits)
        .append(", cacheMiss=").append(cacheMiss).append("]");
    return builder.toString();
  }

  public static class LRUCacheBuilder {

    private Integer capacity;

    public LRUCacheBuilder capacity(final Integer capacity) {
      this.capacity = capacity;
      return this;
    }

    public LRUCache build() {
      return new LRUCache(this.capacity);
    }
  }

}
