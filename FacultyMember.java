/*
    KIARA YOST

    COP 3330C

    Programming Assignment #9: Files, Exceptions

    Due date: 04/15/2026

    NOTE: original ucfsalaries-2016.txt has been slightly modified

*/

public class FacultyMember implements Comparable<FacultyMember> {
    private String first;
    private String last;
    private String position;
    private int salary;

    public FacultyMember() {
        first = null;
        last = null;
        position = null;
        salary = 0;
    }

    public FacultyMember(String l, String f, String p, int s) {
        first = f;
        last = l;
        position = p;
        salary = s;
    }

    @Override
    public int compareTo(FacultyMember other) {
        if(last.compareTo(other.last) != 0) return last.compareTo(other.last);
        return first.compareTo(other.first);
    }

    @Override
    public String toString() {
        return last + ", " + first + "\t" + position + "\t$" + salary;
    }

    public boolean hasFirst(String f) {
        return first.compareTo(f) == 0;
    }

    public String getPosition() {
        return position;
    }

    public String getLast() {
        return last;
    }

    public int getSalary() {
        return salary;
    }
}