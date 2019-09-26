package by.belhard.j16.maven_example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/exampledb?serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection connection;

    public DBManager() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error while registering JDBC driver");
        }

        connection = DriverManager.getConnection(DATABASE_URL, USER, PASS);
    }

    public void create(Person person) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "insert into employees value (null, ?, ?, ?, ?);"
        );

        statement.setString(1, person.getName());
        statement.setInt(2, person.getAge());
        statement.setDate(3, person.getDateOfEmployment());

        statement.setInt(4, person.getSpecialty().getId());

        statement.executeUpdate();
    }

    public List<Person> getAll() throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "select e.id as eid, e.name, e.age," +
                        " e.date_of_employment as date," +
                        " s.id as sid, s.specialty, s.salary " +
                        "from employees e join specialties s " +
                        "on (e.specialty_id = s.id);");

        List<Person> result = new ArrayList<>();
        while (resultSet.next()) {
            Person person = new Person();

            person.setId(resultSet.getInt("eid"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setDateOfEmployment(resultSet.getDate("date"));
            person.setSpecialty(
                    Specialty.builder().id(resultSet.getInt("sid"))
                            .specialty(resultSet.getString("specialty"))
                            .salary(resultSet.getInt("salary"))
                            .build()
            );

            result.add(person);
        }

        return result;
    }

    public void removeById(int id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                "delete from employees where id = ?;"
        );

        statement.setInt(1, id);
        statement.execute();
    }
}
