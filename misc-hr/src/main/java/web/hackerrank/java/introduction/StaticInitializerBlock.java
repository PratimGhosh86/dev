package web.hackerrank.java.introduction;

import java.util.Scanner;

public class StaticInitializerBlock {

  static boolean flag = false;
  static int B = 0, H = 0;

  static {
    try (final Scanner in = new Scanner(System.in)) {
      B = in.nextInt();
      H = in.nextInt();
      if (B > 0 && B <= 100 && H > 0 && H <= 100)
        flag = true;
      else
        System.out.println("java.lang.Exception: Breadth and height must be positive");
    }
  }

  public static void main(String[] args) {
    if (flag) {
      int area = B * H;
      System.out.print(area);
    }
  }// end of main

}// end of class


