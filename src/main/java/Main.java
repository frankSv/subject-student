import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public  static void menu() throws Exception {
        Subject subject1 = SubjectUtils.createSubjects(1, "Subject1");
        Subject subject2 = SubjectUtils.createSubjects(2, "Subject2");
        Subject subject3 = SubjectUtils.createSubjects(3, "Subject3");
        int subjectOption;
        String errorMessage = "You must type a NUMBER between 1 and 4";
        Scanner myScan = new Scanner(System.in);
        do{
            System.out.println("-------Select the subject------");
            System.out.println("1- Subject1");
            System.out.println("2- Subject2");
            System.out.println("3- Subject3");
            System.out.println("4- Exit");
            System.out.println("--------------------------------");
            while (!myScan.hasNextInt()) {
                System.out.println(errorMessage);
                myScan.next();
            }
            subjectOption = myScan.nextInt();
            while (!menuValid(subjectOption)){
                System.out.println(errorMessage);
                subjectOption = myScan.nextInt();
            }
            switch (subjectOption){
                case 1:
                    subMenu(subject1);
                    break;
                case 2:
                    subMenu(subject2);
                    break;
                case 3:
                    subMenu(subject3);
                    break;
            }
        }while(subjectOption != 4);
    }
    ///////////////////Sub-Menu/////////////////
    public  static void subMenu(Subject subject) throws Exception {
        int gradesOption;
        String errorMessage = "You must type a NUMBER between 1 and 5";
        Scanner myScan =  new Scanner(System.in);
        do{
            System.out.println("----------Select Option----------");
            System.out.println("1 - Add student manually");
            System.out.println("2 - Load file");
            System.out.println("3 - Generate and send report");
            System.out.println("4 - Show Subject Data");
            System.out.println("5 - Back to main menu");
            System.out.println("----------------------------------");

            while (!myScan.hasNextInt()) {
                System.out.println(errorMessage);
                myScan.next();
            }
            gradesOption = myScan.nextInt();
            while (!subMenuValid(gradesOption)){
                System.out.println(errorMessage);
                gradesOption = myScan.nextInt();
            }

            switch (gradesOption){
                case 1:
                    SubjectUtils.settingStudents(subject);
                    System.out.println(subject);
                    break;
                case 2:
                    System.out.println("Loading from File");
                    SubjectUtils.loadFile(subject);
                    break;
                case 3:
                    System.out.println("Generating Report from subject");
                    SubjectUtils.generateReport(subject);
                    SubjectUtils.sendMail(subject.getName(),"subjectscience941@gmail.com");
                    break;
                case 4:
                    SubjectUtils.showSubjectData(subject);
                default:
                    break;
            }
        }while(gradesOption != 5);
    }

    public static boolean menuValid(int opt){
        return opt >= 1 && opt <= 4;
    }

    public static boolean subMenuValid(int opt){
        return opt >= 1 && opt <= 5;
    }

    public static void main(String[] args) throws Exception {
        menu();
    }
}
