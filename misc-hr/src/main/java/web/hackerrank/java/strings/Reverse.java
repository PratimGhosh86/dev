package web.hackerrank.java.strings;

import java.util.Scanner;

public class Reverse {

  static boolean isAnagram(String a, String b) {
    // Complete the function
    char[] aa = a.toUpperCase().toCharArray(), bb = b.toUpperCase().toCharArray();
    java.util.Arrays.sort(aa);
    java.util.Arrays.sort(bb);
    return new String(aa).equals(new String(bb));
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String a = scan.next();
    String b = scan.next();
    scan.close();
    boolean ret = isAnagram(a, b);
    System.out.println((ret) ? "Anagrams" : "Not Anagrams");
  }

}


