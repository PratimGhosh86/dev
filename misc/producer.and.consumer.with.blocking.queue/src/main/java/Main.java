
/*
 * Write a multi threaded Java program to implement Producer and Consumer. Span
 * two thread for producer and consumer from the main method. Using wait() and
 * notify() methods for co-ordinating between threads are error-prone, So in
 * this exercise we will use the blocking queue which is used for message
 * passing between multiple threads. In this problem the producer inserts the
 * number from 0 - N into the blocking queue and the consumer listens on this
 * queue for any information. Once the data is inserted the consumer takes the
 * message or number and prints on the console
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author pratim
 */
public class Main {

  /**
   * @param args
   */
  public static void main(final String[] args) {
    try (final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in))) {
      // read number of elements from console
      final int numOfElems = Integer.parseInt(bufferedReader.readLine());
      // create a blocking queue of minimum size to be shared among the threads
      final BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(1);
      // create the producer thread
      final ProducerThread pt = new ProducerThread(blockingQueue, numOfElems);
      // create the consumer thread
      final ConsumerThread ct = new ConsumerThread(blockingQueue, numOfElems);
      // start producing
      pt.start();
      // start consuming
      ct.start();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
