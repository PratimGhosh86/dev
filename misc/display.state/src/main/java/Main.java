
/*
 * program to get the country names and state names from the user seperated by a
 * pipe symbol. Finally display all the countries and their states sorted in
 * ascending order based on their names
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
      final int count = Integer.parseInt(bufferedReader.readLine());
      // storing in map for ease of access
      final Map<String, Country> countryMap = new HashMap<>();
      IntStream.rangeClosed(1, count).forEach(index -> {
        try {
          // split on | , need to escape because its a reserved character in
          // regex
          final String[] input = bufferedReader.readLine().split("\\|");
          // if country already exists then fetch the country and add new state
          if (countryMap.containsKey(input[0]))
            countryMap.get(input[0]).addState(input[1]);
          // else create a new country with a new state
          else {
            countryMap.put(input[0], new Country(input[0]) {
              {
                this.addState(input[1]);
              }
            });
          }
        } catch (final IOException e) {
          e.printStackTrace();
        }
      });
      // sorting map based on key which reflects country name
      countryMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
              (oldValue, newValue) -> oldValue, LinkedHashMap::new))
          .forEach((k, v) -> {
            System.out.println(v.getName());
            // sorting state directly as it implements comparator
            v.getStateList().stream().sorted().forEachOrdered(
                state -> System.out
                    .println(String.format("--%s", state.getName())));
          });
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}
