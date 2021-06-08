import lombok.*;

import java.util.Scanner;

@AllArgsConstructor @NoArgsConstructor @ToString
public class Student {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private float grade;
}
