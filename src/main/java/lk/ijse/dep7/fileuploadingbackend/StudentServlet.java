package lk.ijse.dep7.fileuploadingbackend;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "StudentServlet", value = "/students", loadOnStartup = 0)
public class StudentServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/cp")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        try {
//            InitialContext ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cp");
//            System.out.println(ds.getConnection());
//        } catch (NamingException | SQLException e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

        System.out.println(name);
        System.out.println(address);
        System.out.println(contact);
        System.out.println(picture.getSubmittedFileName());

        picture.write("/home/ranjith-suranga/Desktop/uploaded/" + picture.getSubmittedFileName());
    }

}
