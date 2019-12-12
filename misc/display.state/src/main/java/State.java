/**
 * @author pratim
 */
public class State implements Comparable<Object> {

  private final String name;

  /**
   * @param name
   */
  public State(final String name) {
    super();
    this.name = name;
  }

  /**
   * @return the name
   */
  public final String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(final Object name) {
    return this.name.compareTo(name.toString());
  }

}
