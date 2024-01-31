package wcd.jpa.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import wcd.jpa.entities.Classes;
import wcd.jpa.entities.Student;
import wcd.jpa.entities.Subject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            List<Subject> listSubject = session.createQuery("FROM Subject ", Subject.class)
                    .getResultList();
            session.getTransaction().commit();
            req.setAttribute("classes",list);
            req.setAttribute("subjects",listSubject);
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

            // find subjects
            List<Integer> s_ids = Arrays.stream(req.getParameterValues("subject_id[]"))
                    .map(Integer::parseInt).collect(Collectors.toList());
            Query<Subject> query = session.createQuery("FROM Subject WHERE id IN (:ids)", Subject.class);
            query.setParameter("ids", s_ids);
            List<Subject> subjects = query.getResultList();
            student.setSubjects(subjects);

            session.save(student);
            session.getTransaction().commit();
        }

        resp.sendRedirect("list-student");
    }
}