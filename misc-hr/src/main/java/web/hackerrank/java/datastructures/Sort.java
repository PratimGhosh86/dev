package web.hackerrank.java.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sort {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int testCases = Integer.parseInt(in.nextLine());

    List<Student> studentList = new ArrayList<Student>();
    while (testCases > 0) {
      int id = in.nextInt();
      String fname = in.next();
      double cgpa = in.nextDouble();

      Student st = new Student(id, fname, cgpa);
      studentList.add(st);

      testCases--;
    }

    studentList.sort((a, b) -> {
      int c = 0;
      c = Double.compare(b.getCgpa(), a.getCgpa());
      if (c == 0) // i.e. cgpa is equal
        c = a.getFname().compareTo(b.getFname());
      if (c == 0) // i.e. name is equal
        c = Integer.compare(b.getId(), a.getId());
      return c;
    });

    for (Student st : studentList) {
      System.out.println(st.getFname());
    }

    in.close();
  }

  public static class Student {
    private int id;
    private String fname;
    private double cgpa;

    public Student(int id, String fname, double cgpa) {
      super();
      this.id = id;
      this.fname = fname;
      this.cgpa = cgpa;
    }

    public int getId() {
      return id;
    }

    public String getFname() {
      return fname;
    }

    public double getCgpa() {
      return cgpa;
    }
  }

}


