package web.hackerrank.java.introduction;

import java.util.Scanner;

public class EndOfFile {

  public static void main(String[] args) {
    try (final Scanner in = new Scanner(System.in)) {
      int count = 0;
      while (in.hasNextLine()) {
        final String nextLine = in.nextLine();
        if (!nextLine.isEmpty())
          System.out.println(String.format("%s %s", ++count, nextLine));
        else
          break;
      }
    }
  }

}

