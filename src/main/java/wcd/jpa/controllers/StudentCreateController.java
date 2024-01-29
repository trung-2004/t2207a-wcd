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

@WebServlet("/create-students")
public class StudentCreateController extends HttpServlet {
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Classes> list = session.createQuery("FROM Classes", Classes.class)
                    .getResultList();
            session.getTransaction().commit();
            req.setAttribute("classes",list);
        }
        req.getRequestDispatcher("student/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        student.setName(req.getParameter("name"));
        student.setEmail(req.getParameter("email"));
        student.setAddress(req.getParameter("address"));
        String classId = req.getParameter("class_id");
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Classes aClass = session.get(Classes.class, Integer.parseInt(classId));
            if (aClass == null) {
                return;
            }
            student.setClasses(aClass);
            session.save(student);
            session.getTransaction().commit();
        }

        resp.sendRedirect("list-student");
    }
}