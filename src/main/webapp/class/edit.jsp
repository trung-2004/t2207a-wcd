<%@ page import="wcd.jpa.entities.Classes" %>
<%@ page import="wcd.jpa.entities.Classes" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/26/2024
  Time: 4:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Class</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
<div class="container">
    <% Classes aClasses = (Classes) request.getAttribute("class"); %>
    <form action="edit-class?id=<%=  aClasses.getId() %>" method="post">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Name</label>
            <input type="text" value="<%= aClasses.getName() %>" name="name" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword2" class="form-label">SEM</label>
            <input type="text" value="<%= aClasses.getSem() %>" name="sem" class="form-control" id="exampleInputPassword2">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword3" class="form-label">Room</label>
            <input type="text" value="<%= aClasses.getRoom() %>" name="room" class="form-control" id="exampleInputPassword3">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
