<%@ page import="java.util.List" %>
<%@ page import="com.entities.Student" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/22/2024
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="/layout/header.jsp"/>
    <h1 class="mb-3 mt-3">List Student</h1>
    <table class="table mb-5">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Address</th>
        </tr>
        </thead>
        <tbody>
        <% for (Student s: (List<Student>) request.getAttribute("students")) { %>
            <tr>
                <th scope="row"><%= s.id %></th>
                <td><%= s.name %></td>
                <td><%= s.email %></td>
                <td><%= s.address %></td>
            </tr>
        <% } %>

        </tbody>
    </table>
</div>

</body>
</html>
