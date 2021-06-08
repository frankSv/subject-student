import java.util.ArrayList;
import java.util.Scanner;

public class StudentUtils {
    public static Student createStudent(){
        Student student = new Student();
        String name;
        float grade;
        String gradeErrorMessage = "The grade must be a NUMBER between 0 to 10";
        Scanner sc =  new Scanner(System.in);
        System.out.println("Type Student's name");
        name = sc.nextLine();
        System.out.println("Type Grade");
        while (!sc.hasNextFloat()) {
            System.out.println(gradeErrorMessage);
            sc.next();
        }
        grade = sc.nextFloat();
        while(!validator(grade)){
            System.out.println(gradeErrorMessage);
            grade = sc.nextFloat();
        }
        student.setName(name);
        student.setGrade(grade);
        return student;
    }

    public static boolean validator(float grade){
        return grade >= 0.0 && grade <= 10.0;
    }

    public static ArrayList<Student> addingStudent(){
        ArrayList<Student> students = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int insertOption = 1;
        while (insertOption == 1){
            students.add(createStudent());
            System.out.println("Need to add another Student?");
            System.out.println("1 yes, 0 no");
            insertOption = sc.nextInt();
        }
        return students;
    }
}
