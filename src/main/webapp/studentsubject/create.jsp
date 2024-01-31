<%@ page import="wcd.jpa.entities.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="wcd.jpa.entities.Subject" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/29/2024
  Time: 4:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Subject to Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
<div class="container">
    <form action="create-student-subject" method="post">
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Student</label>
            <select name="student_id" class="form-select" id="exampleInputPassword1" aria-label="Default select example">
                <option selected>Open this select menu</option>
                <% for (Student s: (List<Student>) request.getAttribute("students")) { %>
                <option value="<%= s.getId() %>">
                    <%= s.getName() %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword2" class="form-label">Subject</label>
            <select name="subject_id" class="form-select" id="exampleInputPassword2" aria-label="Default select example">
                <option selected>Open this select menu</option>
                <% for (Subject s: (List<Subject>) request.getAttribute("subjects")) { %>
                <option value="<%= s.getId() %>">
                    <%= s.getName() %>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
