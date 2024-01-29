package com.demo.student;

import com.dao.StudentDAO;
import com.entities.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/create")
public class CreateControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("student/create.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student s = new Student(
                0,
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("address")
                );
        StudentDAO sd = new StudentDAO();
        if (sd.create(s)){
            resp.sendRedirect("student");
            return;
        }
        resp.sendRedirect("create");
    }
}
