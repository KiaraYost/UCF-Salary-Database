/*
    KIARA YOST

    COP 3330C

    Programming Assignment #9: Files, Exceptions

    Due date: 04/15/2026

    NOTE: original ucfsalaries-2016.txt has been slightly modified

*/

import java.io.*;
import java.util.*;

public class FacultyList {
    private ArrayList<FacultyMember> list; //stores members
    private HashMap<String, ArrayList<FacultyMember>> posList; //organizes members by position keys
    private HashMap<String, ArrayList<FacultyMember>> lastList; //organizes members by last name keys

    public FacultyList(String inputFile) throws FileNotFoundException {
        Scanner fin = new Scanner(new File(inputFile));
        StringTokenizer tok = new StringTokenizer(fin.nextLine());

        int numFaculty = Integer.parseInt(tok.nextToken());
        list = new ArrayList<>(numFaculty);
        posList = new HashMap<>(numFaculty/2);
        lastList = new HashMap<>();

        //reads data of each member and adds to list
        for(int i = 0; i < numFaculty; i++) {
            tok = new StringTokenizer(fin.nextLine());

            String last = tok.nextToken("\t");
            String first = tok.nextToken("\t");
            String position = tok.nextToken("\t");
            int salary = Integer.parseInt(tok.nextToken("\t"));

            FacultyMember member = new FacultyMember(last, first, position, salary);
            list.add(member);
        }

        Collections.sort(list);

        //posList and lastList are done after list is sorted so each of their keys' arrayLists are already sorted
        //parses through each member in list
        for(FacultyMember member: list) {
            String last = member.getLast();
            String position = member.getPosition();

            //add member to posList in position key
            if(posList.containsKey(position)) posList.get(position).add(member);
            else {
                ArrayList<FacultyMember> newList = new ArrayList<>();
                posList.put(position, newList);
                posList.get(position).add(member);
            }

            //add member to lastList in last name key
            if(lastList.containsKey(last)) lastList.get(last).add(member);
            else {
                ArrayList<FacultyMember> newList = new ArrayList<>();
                lastList.put(last, newList);
                lastList.get(last).add(member);
            }
        }
    }

    //prints data of all members to outputFile
    public void print(String outputFile) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            int sum = 0;
            int num = list.size();
            double avg;
            double sumDev = 0;
            
            //parses through each member and calculates sum of salaries
            for(FacultyMember member: list) {
                out.println(member.toString());
                sum += member.getSalary();
            }
            
            //calculates average salary
            avg = ((double)sum) / num;
            
            //calculates summation part of standard deviation
            for(FacultyMember member: list) {
                sumDev += Math.pow(member.getSalary() - avg, 2);
            }
            
            System.out.println("Average Salary: $" + avg);
            System.out.println("Salary Standard Deviation: " + Math.sqrt(sumDev/num));
            System.out.print("\n");
        }
    }

    //prints data of members of a given position to outputFile
    public void print(String outputFile, String position) throws IOException, PositionNotFoundException {
        
        //if position key is not in posList hashmap, throw error
        if(!posList.containsKey(position)) throw new PositionNotFoundException("Position not found.");

        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            //saves position's respective arrayList to subList
            ArrayList<FacultyMember> subList = posList.get(position);
            int sum = 0;
            int num = subList.size();
            double avg;
            double sumDev = 0;
            
            //parses through each member in subList and calculates sum of salaries
            for(FacultyMember member: subList) {
                out.println(member.toString());
                sum += member.getSalary();
            }
            
            //calculates average salary
            avg = ((double)sum) / num;
            
            //calculates summation part of standard deviation
            for(FacultyMember member: subList) {
                sumDev += Math.pow(member.getSalary() - avg, 2);
            }
            
            System.out.println("Average " + position + " Salary: $" + avg);
            System.out.println(position + " Salary Standard Deviation: " + Math.sqrt(sumDev/num));
            System.out.print("\n");
        }
    }

    //prints data of members of a given last name to standard output
    public void printByName(String last) throws NameNotFoundException {
        //if last name key is not found in lastList hashmap, throw error
        if(!lastList.containsKey(last)) throw new NameNotFoundException("Last Name Not Found.");

        //saves last names's respective arrayList to subList
        ArrayList<FacultyMember> subList = lastList.get(last);

        //prints data of each member in subList
        for(FacultyMember member: subList) System.out.println(member.toString());
        System.out.print("\n");
    }

    //prints data of members of a given last and first name to standard output
    public void printByName(String last, String first) throws NameNotFoundException {
        //if last name key is not found in lastList hashmap, throw error
        if(!lastList.containsKey(last)) throw new NameNotFoundException("Full Name Not Found.");

        int num = 0;

        //saves last names' respective arrayList to subList
        ArrayList<FacultyMember> subList = lastList.get(last);

        //parses through each member, printing data of members with first name
        for(FacultyMember member: subList) {
            if(member.hasFirst(first)) {
                System.out.println(member.toString());
                ++num;
            }
        }
        System.out.print("\n");

        //if no members with last name have first name, throw error
        if(num == 0) throw new NameNotFoundException("Full Name Not Found.");
    }

    class PositionNotFoundException extends Exception {
        public PositionNotFoundException(String s) {
            super(s);
        }
    }

    class NameNotFoundException extends Exception {
        public NameNotFoundException(String s) {
            super(s);
        }
    }
}