package wcd.jpa.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import wcd.jpa.entities.Classes;
import wcd.jpa.entities.Student;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/edit-student")
public class StudentEditController extends HttpServlet {
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
            List<Classes> list = session.createQuery("FROM Classes", Classes.class)
                    .getResultList();
            session.getTransaction().commit();
            if (student != null){
                req.setAttribute("student", student);
                req.setAttribute("classes",list);
                req.getRequestDispatcher("student/edit.jsp").forward(req,resp);
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
                student.setName(req.getParameter("name"));
                student.setEmail(req.getParameter("email"));
                student.setAddress(req.getParameter("address"));
                String classId = req.getParameter("class_id");
                Classes aClass = session.get(Classes.class, Integer.parseInt(classId));
                if (aClass == null) {
                    return;
                }
                student.setClasses(aClass);
                session.update(student);
            }
            session.getTransaction().commit();
            resp.sendRedirect("list-student");
        } catch (Exception e){
            resp.setStatus(500);
        }
    }


}
