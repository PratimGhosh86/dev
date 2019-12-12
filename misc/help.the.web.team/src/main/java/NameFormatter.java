
/*
 * Input consists of n+1 lines where n corresponds to the number of words in the
 * list. * The first line contains the number n of names * The following n lines
 * each contain a name, a name can have 1 to 3 components * A 1 component name
 * is a family name, ex: CLEOPATRA * A 2 component name is a first name followed
 * by a family name, ex: Albert EINSTEIN * A 3 component name is either a
 * particle name (Leonardo da VINCI) or a name with a middle initial (John F.
 * KENNEDY)
 *
 * Output format * For each name you should print its corresponding formatted
 * version: * The first name must start with an uppercase letter and be followed
 * by lowercase letters * The middle initial must be uppercase * The family name
 * must be uppercase * The particle must be lower (the da in Leonardo da VINCI)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author pratim
 *
 */
public class NameFormatter {

  /**
   * @param args
   * @throws NumberFormatException
   */
  public static void main(final String[] args) {
    // declare variables for store unformatted and formatted names
    final List<String> unfNames = new ArrayList<>(), fNames = new ArrayList<>();
    // intializing console reader
    try (final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in))) {
      // read number of names
      final int n = Integer.parseInt(bufferedReader.readLine());
      // read unformatted names from console
      IntStream.rangeClosed(1, n).forEach(index -> {
        try {
          // add console entry to unformatted names list
          unfNames.add(bufferedReader.readLine());
        } catch (final IOException e) {
          System.err.println("Invalid input");
        }
      });
      // formatting provided names
      fNames.addAll(unfNames.stream().map(name -> {
        // splitting name in words
        final String[] words = name.split(" ");
        String formatted = "";
        // switching based on word count
        switch (words.length) {
          case 1: // formatting as family name
            formatted = words[0].toUpperCase();
            break;
          case 2: // formatting as first name followed by a family name
            formatted = String.format("%s%s %s",
                words[0].substring(0, 1).toUpperCase(),
                words[0].substring(1).toLowerCase(), words[1].toUpperCase());
            break;
          case 3: // formatting as a particle name or first name followed by
                  // middle initial followed by last name
            formatted = String.format("%s%s %s %s",
                words[0].substring(0, 1).toUpperCase(),
                words[0].substring(1).toLowerCase(),
                words[1].endsWith(".") ? words[1].toUpperCase()
                    : words[1].toLowerCase(),
                words[2].toUpperCase());
            break;
          default: // nothing to format
            formatted = name;
            break;
        }
        return formatted;
      }).collect(Collectors.toList()));
      // iterating and printing formatted names
      fNames.forEach(System.out::println);
    } catch (final IOException | NumberFormatException e) {
      System.err.println("Invalid input");
    }
  }

}
