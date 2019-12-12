import java.util.concurrent.BlockingQueue;

/**
 * @author pratim
 */
public class ProducerThread extends Thread {

  protected BlockingQueue<Integer> blockingQueue;
  protected int numberOfElements;

  /**
   * @param blockingQueue
   * @param numberOfElements
   */
  public ProducerThread(final BlockingQueue<Integer> blockingQueue,
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
        // inserting elements in order
        this.blockingQueue.put(i);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
  }
}
