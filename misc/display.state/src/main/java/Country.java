import java.util.ArrayList;
import java.util.List;

/**
 * @author pratim
 */
public class Country {

  private final String name;
  private final List<State> stateList;

  /**
   * @param name
   */
  public Country(final String name) {
    super();
    this.name = name;
    this.stateList = new ArrayList<>();
  }

  /**
   * @return the name
   */
  public final String getName() {
    return this.name;
  }

  /**
   * @return the stateList
   */
  public final List<State> getStateList() {
    return this.stateList;
  }

  /**
   * @param statename
   */
  public final void addState(final String statename) {
    this.stateList.add(new State(statename));
  }

}
