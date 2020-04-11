package web.hackerrank.java.introduction;

import java.util.Scanner;

public class JavaLoops1 {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int N = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
    System.out.print(java.util.stream.IntStream.rangeClosed(1, 10).boxed()
        .map(elem -> String.format("%s x %s = %s", N, elem, N * elem))
        .collect(java.util.stream.Collectors.joining(System.lineSeparator())));
    scanner.close();
  }

}

