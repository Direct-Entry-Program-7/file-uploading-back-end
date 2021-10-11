package lk.ijse.dep7.fileuploadingbackend.api;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep7.fileuploadingbackend.dto.StudentDTO;
import lk.ijse.dep7.fileuploadingbackend.service.StudentService;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "StudentServlet", value = "/students", loadOnStartup = 0)
public class StudentServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/cp")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hello Servlet");
        System.out.println(request.getParameter("q"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        Part picture = request.getPart("picture");

        StudentService studentService = new StudentService();
        InputStream is = picture.getInputStream();
        byte[] bytes = new byte[is.available()];
        is.read(bytes);

        StudentDTO student = new StudentDTO(name,address, contact, new String(bytes));
        studentService.saveStudent(student);
    }

}
