package by.belhard.j16.maven_example;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Specialty {
    private int id;
    private String specialty;
    private int salary;
}
