package Heap.ProgramHW1;

public class Student implements Comparable<Student> {
    private String name;
    private double gradePoints = 0;
    private int units = 0;
    // part 2:
    private int index = -1;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, double gpa, int units) {
        this.name = name;
        this.units = units;
        this.gradePoints = gpa * units;
    }

    public String getName() {
        return name;
    }

    // part2: getter
    public int getIndex() {
        return this.index;
    }

    // part2: setter
    void setIndex(int index) {
        if (index < -1) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        this.index = index;
    }

    public double gpa() {
        if (units > 0)
            return gradePoints / units;
        return 0;
    }

    public void addGrade(double gradePointsPerUnit, int units) {
        this.units += units;
        this.gradePoints += gradePointsPerUnit * units;
    }


    public int compareTo(Student other)  //Do not change this method.  Ask me why if you like.
    {
        double difference = gpa() - other.gpa();
        if (difference == 0) return 0;
        if (difference > 0) return 14;        //Do not hardcode 14, or -12, into your code.
        return -12;
    }
}
