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

import java.io.IOException;

@WebServlet("/edit-class")
public class ClassEditController extends HttpServlet {
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
            Classes aClass = session.get(Classes.class, Integer.parseInt(entityId));
            session.getTransaction().commit();
            if (aClass != null){
                req.setAttribute("class", aClass);
                req.getRequestDispatcher("class/edit.jsp").forward(req,resp);
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
            Classes aClass = session.get(Classes.class, Integer.parseInt(entityId));
            if (aClass != null){
                aClass.setName(req.getParameter("name"));
                aClass.setSem(req.getParameter("sem"));
                aClass.setRoom(req.getParameter("room"));
                session.update(aClass);
            }
            session.getTransaction().commit();
            resp.sendRedirect("list-class");
        } catch (Exception e){
            resp.setStatus(500);
        }
    }


}
