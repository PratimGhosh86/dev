package web.hackerrank.java.introduction;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaLoops2 {

  public static void main(String[] argh) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    for (int i = 0; i < t; i++) {
      int a = in.nextInt();
      int b = in.nextInt();
      int n = in.nextInt();
      System.out.println(IntStream.range(0, n).boxed()
          .map(it -> String
              .valueOf(a + IntStream.rangeClosed(0, it).boxed().map(p -> (int) Math.pow(2, p) * b)
                  .collect(Collectors.summingInt(Integer::intValue))))
          .collect(Collectors.joining(" ")));
    }
    in.close();
  }

}

