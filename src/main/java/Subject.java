import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor @NoArgsConstructor @ToString
public class Subject {
    @Getter @Setter
    int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private ArrayList<Student> students;

}
