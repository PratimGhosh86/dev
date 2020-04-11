package web.hackerrank.java.introduction;

import java.util.Scanner;

public class IfElse {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int N = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
    if (N % 2 != 0) // odd
      System.out.print("Weird");
    else { // even
      if (N >= 2 && N <= 5)
        System.out.print("Not Weird");
      else if (N >= 6 && N <= 20)
        System.out.print("Weird");
      else
        System.out.print("Not Weird");
    }
    scanner.close();
  }

}

