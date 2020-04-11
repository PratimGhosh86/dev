package web.hackerrank.java.introduction;

import java.util.Scanner;

public class SolveMeFirst {

  static int solveMeFirst(int a, int b) {
    // Hint: Type return a+b; below
    return a + b;
  }

  public static void main(String[] argh) {
    Scanner in = new Scanner(System.in);
    int a;
    a = in.nextInt();
    int b;
    b = in.nextInt();
    int sum;
    sum = solveMeFirst(a, b);
    System.out.println(sum);
    in.close();
  }

}

