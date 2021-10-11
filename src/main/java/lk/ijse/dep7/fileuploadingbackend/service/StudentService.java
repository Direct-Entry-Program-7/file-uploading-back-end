package lk.ijse.dep7.fileuploadingbackend.service;

import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            stm.setString(1, student.getName());
            stm.setString(2, student.getAddress());
            stm.setString(3, student.getContact());
            stm.setBlob(4, new SerialBlob(student.getPicture()));

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

    public List<StudentDTO> findStudents(String query) {

        List<StudentDTO> students = new ArrayList<>();

        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM student WHERE id LIKE ? OR name LIKE ? or address LIKE ? or contact LIKE ?");
            stm.setString(1, query);
            stm.setString(2, query);
            stm.setString(3, query);
            stm.setString(4, query);
            ResultSet rst = stm.executeQuery();

            while (rst.next()) {
                Blob picture = rst.getBlob("picture");
                byte[] bytes = picture.getBytes(1, (int) picture.length());

                students.add(new StudentDTO(
                        String.format("SID-%03d", rst.getInt(1)),
                        rst.getString("name"),
                        rst.getString("address"),
                        rst.getString("contact"), bytes));
            }

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
