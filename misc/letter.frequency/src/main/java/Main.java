
/*
 * program to calculate the character frequency in a sentence. The input consist
 * of a single sentence and the output display a graphical chart displaying the
 * frequency of each character by number of asterisk (*). Display the character
 * in the output in alphabetical order. Compute the frequency of all letters
 * except space Use TreeMap to store the characters and frequency since the tree
 * map maintains the entries sorted based on their natural ordering.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
      System.out.println("Enter the input string");
      final LetterSequence ls = new LetterSequence(bufferedReader.readLine());
      // pretty print character frequency
      ls.displayLetterFrequency(ls.computeFrequency());
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
