package lk.ijse.dep7.fileuploadingbackend.service;

import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        String id = studentService.saveStudent(new StudentDTO("Chandima Herath", "Jaffna","077-1234567", new String(bytes)));
        assertTrue(id.matches("SID-\\d{3,}"));
    }
}