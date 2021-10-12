package lk.ijse.dep7.fileuploadingbackend.api;

import jakarta.annotation.Resource;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;
import lk.ijse.dep7.fileuploadingbackend.service.StudentService;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig
@WebServlet(name = "StudentServlet", value = "/students", loadOnStartup = 0)
public class StudentServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/cp")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");

        try (Connection connection = dataSource.getConnection()) {
            StudentService studentService = new StudentService(connection);

            List<StudentDTO> students = studentService.findStudents(query != null ? query : "");

            response.setContentType("application/json");
            response.getWriter().println(JsonbBuilder.create().toJson(students));

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to obtain a connection");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getContentType() == null || !request.getContentType().startsWith("multipart/form-data")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        String name = null;
        String address = null;
        String contact = null;
        Part picture = null;
        byte[] picBytes = null;

        try {
            name = request.getParameter("name");
            address = request.getParameter("address");
            contact = request.getParameter("contact");
            picture = request.getPart("picture");
        }catch(IOException ex){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        String errorMsg = null;

        if (name == null || address == null) {
            errorMsg = "Name or Address can't be empty";
        } else if (!name.trim().matches("[A-Za-z ]+")) {
            errorMsg = "Invalid name";
        } else if (address.trim().length() < 3) {
            errorMsg = "Invalid address";
        } else if (contact !=null && !contact.trim().matches("[0-9 ]{7,}")) {
            errorMsg = "Invalid contact number";
        } else {

            pic:
            if (picture != null) {
                if (picture.getContentType() == null || !picture.getContentType().startsWith("image")){
                    errorMsg = "Invalid picture";
                    break pic;
                }

                try {
                    InputStream is = picture.getInputStream();
                    picBytes = new byte[is.available()];

                    is.read(picBytes);
                } catch (Exception e) {
                    errorMsg = "Failed to read the picture";
                }
            }
        }

        if (errorMsg != null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
            return;
        }

        /* Validation is okay */

        try (Connection connection = dataSource.getConnection()) {
            StudentService studentService = new StudentService(connection);
            StudentDTO student = new StudentDTO(name, address, contact, picBytes);
            String id = studentService.saveStudent(student);

            response.setContentType("application/json");
            response.getWriter().write(JsonbBuilder.create().toJson(id));

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to obtain a connection");
        }
    }

}
