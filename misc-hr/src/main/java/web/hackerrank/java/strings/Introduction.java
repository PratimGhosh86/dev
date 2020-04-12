package web.hackerrank.java.strings;

import java.util.Scanner;

public class Introduction {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String A = sc.next();
    String B = sc.next();
    /* Enter your code here. Print output to STDOUT. */
    System.out.println(A.length() + B.length());
    System.out.println(A.compareTo(B) <= 0 ? "No" : "Yes");
    System.out.println(A.substring(0, 1).toUpperCase() + A.substring(1) + " "
        + B.substring(0, 1).toUpperCase() + B.substring(1));
    sc.close();
  }

}


