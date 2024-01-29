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

@WebServlet("/create-class")
public class ClassCreateController extends HttpServlet {
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
        req.getRequestDispatcher("class/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Classes aClass = new Classes();
        aClass.setName(req.getParameter("name"));
        aClass.setSem(req.getParameter("sem"));
        aClass.setRoom(req.getParameter("room"));
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(aClass);
            session.getTransaction().commit();
        }
        resp.sendRedirect("list-class");
    }
}
