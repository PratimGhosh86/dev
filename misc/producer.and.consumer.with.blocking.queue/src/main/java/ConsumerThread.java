import java.util.concurrent.BlockingQueue;

/**
 * @author pratim
 */
public class ConsumerThread extends Thread {

  protected BlockingQueue<Integer> blockingQueue;
  protected int numberOfElements;

  /**
   * @param blockingQueue
   * @param numberOfElements
   */
  public ConsumerThread(final BlockingQueue<Integer> blockingQueue,
      final int numberOfElements) {
    super();
    this.blockingQueue = blockingQueue;
    this.numberOfElements = numberOfElements;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    for (int i = 0; i < this.numberOfElements; i++)
      try {
        // printing out element from the blocking queue
        System.out.println(
            String.format("Consume value: %s", this.blockingQueue.take()));
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
  }
}
