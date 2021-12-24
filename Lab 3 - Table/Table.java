/*
 * Author:  Andrew Eden, aeden2019@fit.edu
 * Course:  CSE 1002, Section 01, Fall 2021
 * Project: Proj 3, table
 */

public final class Table {
    public static void main (final String[] args) {
        final int n = Integer.parseInt (args[0]);
        
        String h1 = "i";
        String h2 = "hex";
        String h3 = "i*i";
        String h4 = "i*i*i";
        String h5 = "log";
        String h6 = "pcnt";

        System.out.printf ("%7s %-6s %9s %9s %9s %8s %n", h1, h2, h3, h4, h5, h6);
        
        for (int i = 1; i <= n; i++) {
            double x = Math.log(i*i)/Math.log(2);
            int y = (i*100)/n;
            System.out.printf ("%,7d 0x%04X %,9d ", i, i, i*i);
            System.out.printf ("%,9d %9.3f %7d%s %n", i*i*i, x, y, "%");
        }
    }
}
