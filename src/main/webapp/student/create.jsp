<%@ page import="wcd.jpa.entities.Classes" %>
<%@ page import="java.util.List" %>
<%@ page import="wcd.jpa.entities.Subject" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/24/2024
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
<div class="container">
  <form action="create-students" method="post">
    <div class="mb-3">
      <label for="exampleInputEmail1" class="form-label">Name</label>
      <input type="text" name="name" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
      <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <div class="mb-3">
      <label for="exampleInputPassword2" class="form-label">Email</label>
      <input type="email" name="email" class="form-control" id="exampleInputPassword2">
    </div>
    <div class="mb-3">
      <label for="exampleInputPassword3" class="form-label">Address</label>
      <input type="text" name="address" class="form-control" id="exampleInputPassword3">
    </div>
    <div class="mb-3">
      <label for="exampleInputPassword4" class="form-label">Class</label>
      <select name="class_id" class="form-select" id="exampleInputPassword4" aria-label="Default select example">
        <% for (Classes s: (List<Classes>) request.getAttribute("classes")) { %>
        <option value="<%= s.getId() %>">
          <%= s.getName() %>
        </option>
        <% } %>
      </select>
    </div>
    <div class="mb-3">
      <label for="exampleInputEmail1" class="form-label">Subject</label>
      <% for (Subject s: (List<Subject>)request.getAttribute("subjects")){ %>
      <div class="form-check">
        <label class="form-check-label">
          <input name="subject_id[]" class="form-check-input" type="checkbox" value="<%= s.getId() %>">
          <%= s.getName() %>
        </label>
      </div>
      <% } %>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>
</body>
</html>
