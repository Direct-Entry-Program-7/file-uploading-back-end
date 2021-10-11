package lk.ijse.dep7.fileuploadingbackend.service;

import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentServiceTest {

    private static StudentService studentService;
    private static Connection connection;

    @BeforeAll
    static void beforeAll() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep7_sms","root","mysql");
        connection.setAutoCommit(false);
        studentService = new StudentService(connection);
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    void saveStudent() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/ranjith-suranga/Desktop/chandima.jpeg"));
        String id = studentService.saveStudent(new StudentDTO("Chandima Herath", "Jaffna","077-1234567", bytes));
        assertTrue(id.matches("SID-\\d{3,}"));
        id = studentService.saveStudent(new StudentDTO("Gayal Seneviratne", "Galle","078-789456123", bytes));
        assertTrue(id.matches("SID-\\d{3,}"));
        id = studentService.saveStudent(new StudentDTO("Pethum Jeewantha", "Matara","055-1234567", bytes));
        assertTrue(id.matches("SID-\\d{3,}"));
        id = studentService.saveStudent(new StudentDTO("Dhanushka Chandimal", "Colombo","033-1234567", bytes));
        assertTrue(id.matches("SID-\\d{3,}"));
    }

    @Test
    void findStudents(){
        List<StudentDTO> ch = studentService.findStudents("Ch");
        System.out.println(ch.size());
        ch.forEach(System.out::println);
    }

}