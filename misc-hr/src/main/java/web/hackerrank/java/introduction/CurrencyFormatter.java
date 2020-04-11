package web.hackerrank.java.introduction;

import java.text.NumberFormat;
import java.util.Scanner;

public class CurrencyFormatter {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    double payment = scanner.nextDouble();
    scanner.close();

    // Write your code here.
    String us = NumberFormat.getCurrencyInstance(java.util.Locale.US).format(payment);
    String india = NumberFormat.getCurrencyInstance(new java.util.Locale("English", "IN"))
        .format(payment).replace("INR ", "Rs.");
    String china = NumberFormat.getCurrencyInstance(java.util.Locale.CHINA).format(payment);
    String france = NumberFormat.getCurrencyInstance(java.util.Locale.FRANCE).format(payment);

    System.out.println("US: " + us);
    System.out.println("India: " + india);
    System.out.println("China: " + china);
    System.out.println("France: " + france);
  }

}

