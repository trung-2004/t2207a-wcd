package wcd.jpa.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mindrot.jbcrypt.BCrypt;
import wcd.jpa.entities.Classes;
import wcd.jpa.entities.Student;

import java.io.IOException;

@WebServlet("/add-password")

public class AddPasswordController extends HttpServlet {
    private SessionFactory sessionFactory;
    @Override
    public void init() throws ServletException {
        try{
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml").buildSessionFactory();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String entityId = req.getParameter("id");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, Integer.parseInt(entityId));
            session.getTransaction().commit();
            if (student != null){
                req.setAttribute("student", student);
                req.getRequestDispatcher("student/addpassword.jsp").forward(req,resp);
            } else {
                resp.setStatus(404);
            }
        } catch (Exception e){
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String entityId = req.getParameter("id");

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Student student = session.get(Student.class, Integer.parseInt(entityId));
            if (student != null){
                String pass = req.getParameter("password");
                String hassPass = BCrypt.hashpw(pass, BCrypt.gensalt());
                student.setPassword(hassPass);
                session.update(student);
            }
            session.getTransaction().commit();
            resp.sendRedirect("list-student");
        } catch (Exception e){
            resp.setStatus(500);
        }
    }
}
