package web.hackerrank.java.datastructures;

import java.util.Arrays;
import java.util.Scanner;

public class Comparator {

  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();

    Player[] player = new Player[n];
    Checker checker = new Checker();
    for (int i = 0; i < n; i++) {
      player[i] = new Player(scan.next(), scan.nextInt());
    }
    scan.close();

    Arrays.sort(player, checker);

    for (int i = 0; i < player.length; i++) {
      System.out.printf("%s %s\n", player[i].name, player[i].score);
    }
  }


  public static class Checker implements java.util.Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
      int c = Integer.compare(o2.score, o1.score);
      if (c == 0) // i.e. score is same
        c = o1.name.compareTo(o2.name);
      return c;
    }

  }


  public static class Player {
    String name;
    int score;

    Player(String name, int score) {
      this.name = name;
      this.score = score;
    }
  }

}

