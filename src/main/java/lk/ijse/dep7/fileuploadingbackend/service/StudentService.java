package lk.ijse.dep7.fileuploadingbackend.service;

import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;

import java.sql.*;

public class StudentService {

    private final Connection connection;

    public StudentService(Connection connection) {
        this.connection = connection;
    }

    public String saveStudent(StudentDTO student) {
        try {
            PreparedStatement stm = connection.
                    prepareStatement("INSERT INTO student (name, address, contact, picture) VALUES (?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            stm.setObject(1, student.getName());
            stm.setObject(2, student.getAddress());
            stm.setObject(3, student.getContact());
            stm.setObject(4, student.getPicture());

            if (stm.executeUpdate() == 1) {
                ResultSet keys = stm.getGeneratedKeys();

                keys.next();
                return String.format("SID-%03d", keys.getInt(1));
            } else {
                throw new RuntimeException("Failed to execute the statement");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
