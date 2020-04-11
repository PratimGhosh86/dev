package web.hackerrank.java.introduction;

import java.util.Scanner;

public class StdinAndStdOut2 {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int i = scan.nextInt();

    // Write your code here.
    double d = scan.nextDouble();
    String s = scan.nextLine();
    s += scan.nextLine();
    scan.close();

    System.out.println("String: " + s);
    System.out.println("Double: " + d);
    System.out.println("Int: " + i);

    scan.close();
  }

}

