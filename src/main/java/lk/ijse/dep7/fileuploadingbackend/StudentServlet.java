package lk.ijse.dep7.fileuploadingbackend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@MultipartConfig
@WebServlet(name = "StudentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
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
