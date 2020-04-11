package web.hackerrank.java.introduction;

import java.util.Scanner;

public class StdinAndStdOut1 {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int a = scan.nextInt();
    int b = scan.nextInt(); // Complete this line
    int c = scan.nextInt();// Complete this line

    System.out.println(a);
    System.out.println(b); // Complete this line
    System.out.println(c); // Complete this line

    scan.close();
  }

}

