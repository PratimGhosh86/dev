package web.hackerrank.java.strings;

import java.util.Scanner;

public class SubstringComparison {

  public static String getSmallestAndLargest(String s, int k) {
    String smallest = "";
    String largest = "";

    // Complete the function
    // 'smallest' must be the lexicographically smallest substring of length 'k'
    // 'largest' must be the lexicographically largest substring of length 'k'
    final java.util.regex.Matcher m =
        java.util.regex.Pattern.compile("([a-zA-Z]){" + k + "}").matcher(s);
    final java.util.Set<String> tmp = new java.util.TreeSet<>();
    for (int i = 0; m.find(i); i = m.start() + 1) {
      tmp.add(m.group());
    }
    smallest = (String) tmp.toArray()[0];
    largest = (String) tmp.toArray()[tmp.size() - 1];
    return smallest + "\n" + largest;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String s = scan.next();
    int k = scan.nextInt();
    scan.close();

    System.out.println(getSmallestAndLargest(s, k));
  }

}
