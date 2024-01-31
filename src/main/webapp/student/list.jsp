<%@ page import="java.util.List" %>
<%@ page import="wcd.jpa.entities.Student" %>
<%@ page import="wcd.jpa.entities.Subject" %><%--
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
    <a href="create-student" class="float-end btn btn-info">Add student</a>
    <table class="table mb-5">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Address</th>
            <th scope="col">Class</th>
            <th scope="col">Subjetc</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Student s: (List<Student>) request.getAttribute("students")) { %>
            <tr>
                <th scope="row"><%= s.id %></th>
                <td><%= s.name %></td>
                <td><%= s.email %></td>
                <td><%= s.address %></td>
                <td><%= s.getClasses().getName() %></td>
                <td>
                    <ul>
                        <% for (Subject j: s.getSubjects()){ %>
                        <li><%= j.getName() %></li>
                        <% }%>
                    </ul>
                </td>
                <td><a href="edit-student?id=<%= s.getId() %>">Edit</a></td>
                <td><a class="text-danger" onclick="deleteStudent(<%= s.getId() %>)" href="javascript:void(0);">Delete</a></td>
                <td><a class="text-info" onclick="likeStudent(<%= s.getId() %>)" href="javascript:void(0);">Like</a></td>
            </tr>
        <% } %>

        </tbody>
    </table>
</div>
<script>
    function deleteStudent(id) {
        if (confirm("Are you sure?")){
            var url = `list-student?id=`+id;
            fetch(url,{
                method: 'DELETE'
                // body: formData
            }).then(rs=>{
                if(confirm("Reload page?"))
                    window.location.reload();
            }).error(err=>{
                alert(err)
            })
        }
    }
    function likeStudent(id) {
        if (confirm("Are you sure?")){
            var url = `list-student?id=`+id;
            fetch(url,{
                method: 'POST'
                // body: formData
            }).then(rs=>{
                if(confirm("Reload page?"))
                    window.location.reload();
            }).error(err=>{
                alert(err)
            })
        }
    }
</script>
</body>
</html>
