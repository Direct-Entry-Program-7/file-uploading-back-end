package lk.ijse.dep7.fileuploadingbackend.service;

import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep7_sms","root","mysql");
        connection.setAutoCommit(false);
        studentService = new StudentService(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
//        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    void saveStudent() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/ranjith-suranga/Desktop/chandima.jpeg"));
        String id = studentService.saveStudent(new StudentDTO("Chandima Herath", "Jaffna","077-1234567", bytes));
        assertTrue(id.matches("SID-\\d{3,}"));
    }

    @Test
    void readStudent() throws SQLException, IOException {
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM student WHERE id=6");
        rst.next();
        Blob picture = rst.getBlob("picture");
        byte[] bytes = picture.getBytes(1, (int) picture.length());
        System.out.println(bytes.length);
        Files.write(Paths.get("/home/ranjith-suranga/Desktop/uploaded/chandima.jpeg"), bytes);
    }
}