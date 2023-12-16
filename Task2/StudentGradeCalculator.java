// Student Grade Calculator

import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome in Student Grade Calculator\n".toUpperCase());
        try {

            // Tacking Input of Marks
            System.out.print("Enter Marks of Maths out of 100 : ");
            double maths = scanner.nextDouble();
            System.out.print("Enter Marks of Physics out of 100 : ");
            double physics = scanner.nextDouble();
            System.out.print("Enter Marks of Chemistry out of 100 : ");
            double chemistry = scanner.nextDouble();
            System.out.print("Enter Marks of Biology out of 100 : ");
            double biology = scanner.nextDouble();
            scanner.close();

            // Calculate Total Marks
            double totalMarks = maths + physics + chemistry + biology;

            // Calculate Average Percentage
            double averagePercentage = totalMarks / 4;

            // Check Marks not greator than 100
            if (maths <= 100 && physics <= 100 && chemistry <= 100 && biology <= 100) {

                // Assigning Grade
                String grade = "";
                if (averagePercentage >= 91) {
                    grade = "A1";
                } else if (averagePercentage >= 81 && averagePercentage < 91) {
                    grade = "A2";
                } else if (averagePercentage >= 71 && averagePercentage < 81) {
                    grade = "B1";
                } else if (averagePercentage >= 61 && averagePercentage < 71) {
                    grade = "B2";
                } else if (averagePercentage >= 51 && averagePercentage < 61) {
                    grade = "C1";
                } else if (averagePercentage >= 41 && averagePercentage < 51) {
                    grade = "C2";
                } else if (averagePercentage >= 33 && averagePercentage < 41) {
                    grade = "D";
                } else {
                    grade = "E";
                }

                // Display Result
                if (grade == "E" || maths < 33 || chemistry < 33 || biology < 33 || physics < 33) {
                    System.out.println("\nYOUR FINAL RESULT ::\n");
                    System.out.println("SORRY YOU NEED STUDY IMPROVMENT");
                    System.out.println("RESULT STATUS : FAIL\n");
                } else {
                    System.out.println("\nYOUR FINAL RESULT ::\n");
                    System.out.println("TOTAL MARKS OBTAIN OUT OF 400 : " + totalMarks);
                    System.out.println("AVERAGE PERCENTAGE OBTAIN : " + averagePercentage + "%");
                    System.out.println("CALCULATED GRADE : " + grade);
                    System.out.println("RESULT STATUS : PASS\n");
                }
            } else {
                // Mesaage for Invalid Input
                System.out.println("\nPLESE ENTER VALID INPUT\nMARKS SHOULD'T BE GREATER THAN 100\n");
            }

        } catch (Exception e) {
            // Mesaage for Invalid Input
            System.out.println("\nPLESE ENTER VALID INPUT\nRERUN PROGRAMME\n");
        }
    }

}
