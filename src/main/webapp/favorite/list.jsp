<%@ page import="wcd.jpa.entities.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/31/2024
  Time: 3:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="/layout/header.jsp"/>
    <h1 class="mb-3 mt-3">List Favorite</h1>
    <table class="table mb-5">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Address</th>
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
            <td><a class="text-info" onclick="likeStudent(<%= s.getId() %>)" href="javascript:void(0);">Like</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <script>
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
</div>
</body>
</html>
