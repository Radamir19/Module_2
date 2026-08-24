package org.example.cli;

import java.util.Scanner;

public class OrderCliApplication {
    public static String input() {
        System.out.print("Please, write file for parsing: ");
        Scanner sc = new Scanner(System.in);
        String pathFileToParse = sc.next();
        return pathFileToParse;
    }

    public static String output() {
        System.out.print("Please, write file for result: ");
        Scanner sc = new Scanner(System.in);
        String pathFileForResult = sc.next();
        return pathFileForResult;
    }
}
