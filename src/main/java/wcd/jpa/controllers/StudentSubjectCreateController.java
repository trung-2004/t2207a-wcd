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
import wcd.jpa.entities.Subject;

import java.io.IOException;
import java.util.List;

@WebServlet("/create-student-subject")
public class StudentSubjectCreateController extends HttpServlet {
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
            List<Student> listStudent = session.createQuery("FROM Student ", Student.class)
                    .getResultList();
            List<Subject> listSubject = session.createQuery("FROM Subject ", Subject.class)
                    .getResultList();
            session.getTransaction().commit();
            req.setAttribute("students", listStudent);
            req.setAttribute("subjects", listSubject);
        }
        req.getRequestDispatcher("studentsubject/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String student_id = req.getParameter("student_id");
        String subject_id = req.getParameter("subject_id");
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, Integer.parseInt(student_id));
            Subject subject = session.get(Subject.class, Integer.parseInt(subject_id));

            if (student == null || subject == null) {
                return;
            }

            if (student.getSubjects().contains(subject)) {
                // Nếu đã thêm rồi, không làm gì cả
                return;
            }
            if (subject.getStudents().contains(student)) {
                // Nếu đã thêm rồi, không làm gì cả
                return;
            }

            student.getSubjects().add(subject);
            subject.getStudents().add(student);

            session.save(student);
            session.save(subject);

            session.getTransaction().commit();
        }

        resp.sendRedirect("list-student");
    }
}
