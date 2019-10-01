package by.belhard.j16.maven_example;

import lombok.*;

import java.sql.Date;

@Data
public class Person {

    private int id;
    private String name;
    private int age;
    private Date dateOfEmployment;
    private Specialty specialty;
}
