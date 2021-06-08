import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.List;
import java.util.*;

public class SubjectUtils {

    public static Subject createSubjects(int id, String name){
        ArrayList<Student>students=new ArrayList<>();
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(name);
        subject.setStudents(students);
        return subject;
    }

    public static Subject settingStudents(Subject subject){
        ArrayList<Student> students = subject.getStudents();
        students.addAll(StudentUtils.addingStudent());
        subject.setStudents(students);
        return subject;
    }

    public static void loadFile(Subject subject) throws Exception{
        File subjectFile = new File("subject.txt");
        Scanner scnr = new Scanner(subjectFile);
        ArrayList<Student> students = new ArrayList<>();
        students = subject.getStudents();
        int lineNumber = 1;
        while(scnr.hasNextLine()){
            Student student = new Student();
            float grade;
            String line = scnr.nextLine();
            String[] splitString;
            splitString = line.split(":");
            student.setName(splitString[0]);
            grade = Float.parseFloat(splitString[1]);
            student.setGrade(grade);
            students.add(student);
            lineNumber++;
        }
        subject.setStudents(students);
    }

    public static void showSubjectData(Subject subject){
        ArrayList<Student> students = subject.getStudents();
        System.out.println(subject.getName());
        System.out.println("--------------------------------------------");
        for(Student student:students){
            System.out.println(student.getName() + ": " + student.getGrade());
        }
        System.out.println("--------------------------------------------");
        System.out.println("Statistic Report");
        System.out.println(subjectstats(subject));
    }

    public static String subjectstats(Subject subject){
        ArrayList<Student> students = subject.getStudents();
        float sum = 0;
        float average;
        float min;
        float max;
        float montRepeated;
        ArrayList<Student> mostStudents = new ArrayList<>();
        List<Float> vals = new ArrayList<>();
        for(Student student:students){
            vals.add(student.getGrade());
            sum += student.getGrade();
        }
        montRepeated = mostRepeated(vals);
        average = sum / students.size();
        min = Collections.min(vals);
        max = Collections.max(vals);

        for (Student stmost: students){
            if(stmost.getGrade() == montRepeated){
                mostStudents.add(stmost);
            }
        }
        StringBuilder srtBuild = new StringBuilder();
        srtBuild.append("\n");
        srtBuild.append("Most Repeated Value\n");
        for (Student mtSt: mostStudents){
            srtBuild.append(mtSt.getName()).append(": ").append(mtSt.getGrade()).append("\n");
        }
        return "Max: ," + max + ", Min:" + min + ", Average: " + average + srtBuild.toString();
    }

    public static float mostRepeated(List<Float> vals){
        LinkedHashMap<Float, Integer> mostMap = new LinkedHashMap<Float, Integer>();
        for(float val: vals){
            Float key = val;
            if(mostMap.containsKey(key)){
                int freq = mostMap.get(key);
                freq++;
                mostMap.put(key, freq);
            }
            else{
                mostMap.put(key, 1);
            }
        }
        int max_count = 0;
        float res = -1;
        for(Map.Entry<Float, Integer> val : mostMap.entrySet()){
            if (max_count < val.getValue()){
                res = val.getKey();
                max_count = val.getValue();
            }
        }
        return res;
    }

    public static void generateReport(Subject subject) throws FileNotFoundException, DocumentException {
        ArrayList<Student> students = subject.getStudents();
        Document doc = new Document();
        String fileName = subject.getName()+".pdf";
        FileOutputStream PDFFile = new FileOutputStream(fileName);
        PdfWriter.getInstance(doc,PDFFile);
        doc.open();
        Paragraph title = new Paragraph("Subject Data \n\n",
                FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK));
        doc.add(title);
        for (Student student:students){
            String str = student.getName() + ": " + student.getGrade();
            doc.add(new Paragraph(str));
        }
        doc.add(new Paragraph(subjectstats(subject)));
        doc.close();
    }


    public static void sendMail(String subjectName, String subjectAddres) throws Exception{
        subjectName = subjectName+".pdf";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocl", "smtp");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("frank.trailhead@gmail.com","Fiaues2011**");
            }
        });
        Message message = new MimeMessage(session);
        message.setSubject("Report");
        Address addressTo = new InternetAddress(subjectAddres);
        message.setRecipient(Message.RecipientType.TO,addressTo);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart attachment =  new MimeBodyPart();
        attachment.attachFile(new File(subjectName));
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<h1>Subject 1 Report</h1>","text/html");
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachment);
        message.setContent(multipart);
        Transport.send(message);
    }
}
