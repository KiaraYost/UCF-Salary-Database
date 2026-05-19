/*
    KIARA YOST

    COP 3330C

    Programming Assignment #9: Files, Exceptions

    Due date: 04/15/2026

    NOTE: original ucfsalaries-2016.txt has been slightly modified

*/

import java.io.*;
import java.util.*;

public class FacultyData {
    public static Scanner stdin;
    public static void main(String[] args) {

        stdin = new Scanner(System.in);
        System.out.println("Enter input file name: ");
        String file = stdin.nextLine();

        try{
            //reads input file and stores all data in facultyList
            FacultyList faculty = new FacultyList(file);

            int option = getMenuChoice();

            // Process user choice
            while(option != 5) {
                switch (option) {
                    //Output All Data
                    case 1 ->                         {
                            System.out.println("Enter output file name: ");
                            String outFile = stdin.nextLine();
                            faculty.print(outFile);
                        }
                    //Output Data of Position
                    case 2 ->                         {
                            System.out.println("Enter position: ");
                            String position = stdin.nextLine();
                            System.out.println("Enter output file name: ");
                            String outFile = stdin.nextLine();

                            try {
                                faculty.print(outFile, position);
                            } catch (FacultyList.PositionNotFoundException e) {
                                System.out.println(e.getMessage() + "\n");
                            }
                            
                        }
                    //Output Data of Last Name
                    case 3 ->                         {
                            System.out.println("Enter Last Name: ");
                            String last = stdin.nextLine();

                            try {
                                faculty.printByName(last);  
                            } catch (FacultyList.NameNotFoundException e) {
                                System.out.println(e.getMessage() + "\n");
                            }
                        }
                    //Output Data of Full Name
                    case 4 ->                         {
                            System.out.println("Enter Last Name: ");
                            String last = stdin.nextLine();
                            System.out.println("Enter First Name: ");
                            String first = stdin.nextLine();

                            try {
                                faculty.printByName(last, first); 
                            } catch (FacultyList.NameNotFoundException e) {
                                System.out.println(e.getMessage() + "\n");
                            }
                        }
                    default -> {
                    }
                }

                //reads next user choice
                option = getMenuChoice();
            }

        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Prints the menu and gets the user's choice.
    public static int getMenuChoice() {
        int choice = 0;
        boolean done = false;

        while(!done) {
            System.out.println("1. Output All Data");
            System.out.println("2. Output Data of Position");
            System.out.println("3. Output Data of Last Name");
            System.out.println("4. Output Data of Full Name");
            System.out.println("5. Quit");

            try {
                choice = Integer.parseInt(stdin.nextLine()); 

                //checks for valid menu choice
                if(choice < 1 || choice > 5) System.out.println("Please enter a valid integer.\n");
                else done = true;
                 
            } catch (NumberFormatException e) { //checks for valid input data type
                System.out.println("Please enter an integer.\n");
            }
        }

        return choice; 
    }
}