import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author pratim
 */
public class LetterSequence {

  private final String sentence;

  /**
   * @param sentence
   */
  public LetterSequence(final String sentence) {
    super();
    this.sentence = sentence;
  }

  /*
   * Compute the frequency of each character in the sentence and store it in the
   * TreeMap. Return the TreeMap after the computation.
   */
  /**
   * @return {@link TreeMap}
   */
  public TreeMap<Character, Integer> computeFrequency() {
    return this.sentence.chars().boxed().collect(Collectors
        .toMap(k -> Character.valueOf((char) k.intValue()), v -> 1,
            Integer::sum, TreeMap::new));
  }

  /*
   * Iterate the tree map and get all the entries and print the information in a
   * graphical view as shown the sample output
   */
  /**
   * @param frequencyMap
   */
  public void
      displayLetterFrequency(final TreeMap<Character, Integer> frequencyMap) {
    // removing space as per guidelines
    frequencyMap.remove(' ');
    // printing output replacing count with stars
    frequencyMap.forEach((k, v) -> {
      System.out.println(String.format("%s : %s", k.toString(),
          IntStream.rangeClosed(1, v).mapToObj(c -> "*")
              .collect(Collectors.joining())));
    });
  }
}
