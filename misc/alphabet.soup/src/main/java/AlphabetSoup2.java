
/*
 * Develop a program that, given a “bowl of alphabet soup” along with one or
 * more “messages” (i.e., character strings), determines, for each message,
 * whether or not the bowl contains all the letters necessary to form the
 * message. Regarding the case (lower or upper) of letters, your program should
 * treat a as being indistinguishable from A, b as being indistinguishable from
 * B, etc. Hence, an occurrence of, say, F in a message is “covered by” an
 * occurrence of f in the soup. Note that the number of occurrences of each
 * letter matters: if, for example, there are three occurrences of e/E in the
 * soup but four (or more) occurrences of e/E in a message, that message cannot
 * be formed from the letters in the soup. As for non-letters appearing in a
 * message (e.g., spaces, digits, punctuation symbols, etc.), they should be
 * ignored in determining whether the message can be formed in the soup.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author pratim
 */
public class AlphabetSoup2 {

  /**
   * @param args
   */
  public static void main(final String[] args) {
    try (final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in))) {
      // read the alphabet soup string
      final String soup = bufferedReader.readLine();
      // generate character map for store count of available characters in
      // provided soup
      final ConcurrentSkipListMap<Character,
          Integer> alphaSoupMap = soup.toLowerCase().chars()
              .boxed()
              .collect(Collectors.toMap(
                  k -> Character.valueOf((char) k.intValue()), v -> 1,
                  Integer::sum, ConcurrentSkipListMap::new));
      // read and store the input strings in a list
      final int inputCount = Integer.parseInt(bufferedReader.readLine());
      final List<String> inputs = new ArrayList<>();
      IntStream.rangeClosed(1, inputCount).forEach(index -> {
        try {
          inputs.add(bufferedReader.readLine());
        } catch (final IOException e) {
          e.printStackTrace();
        }
      });
      inputs.forEach(each -> {
        final ConcurrentSkipListMap<Character, Integer> tempAlphaSoupMap =
            new ConcurrentSkipListMap<>(alphaSoupMap);
        // replace all non alphanumeric characters as per guidance and create a
        // character map of the provided input and merge with the map created
        // from the original character soup
        each.toLowerCase().replaceAll("[^a-z]", "").chars().boxed()
            .collect(Collectors
                .toMap(k -> Character.valueOf((char) k.intValue()), v -> 1,
                    Integer::sum, TreeMap::new))
            .forEach((k, v) -> {
              // verify if the target character count is more or less than the
              // available character count in the soup
              tempAlphaSoupMap.computeIfPresent(k, (a, b) -> b - v);
            });
        System.out.println(String.format("%s : %s", each,
            tempAlphaSoupMap.entrySet().stream()
                .filter(eachChr -> eachChr.getValue() < 0)
                .map(eachChr -> eachChr.getValue()).collect(Collectors.toList())
                .size() == 0 ? "YES"
                    : "NO"));
      });
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}
