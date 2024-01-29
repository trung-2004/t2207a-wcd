<%@ page import="java.util.List" %>
<%@ page import="wcd.jpa.entities.Classes" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/22/2024
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Class</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="/layout/header.jsp"/>
    <h1 class="mb-3 mt-3">List Class</h1>
    <a href="/T2207A_WCD1_war_exploded/create-class" class="float-end btn btn-info">Add Class</a>
    <table class="table mb-5">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">SEM</th>
            <th scope="col">Room</th>
            <th scope="col">Count Students</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Classes s: (List<Classes>) request.getAttribute("classes")) { %>
        <tr>
            <th scope="row"><%= s.getId() %></th>
            <td><%= s.getName() %></td>
            <td><%= s.getSem() %></td>
            <td><%= s.getRoom() %></td>
            <td><%= s.getStudents().size() %></td>
            <td><a href="edit-class?id=<%= s.getId() %>">Edit</a></td>
            <td><a class="text-danger" onclick="deleteClass(<%= s.getId() %>)" href="javascript:void(0);">Delete</a></td>
        </tr>
        <% } %>

        </tbody>
    </table>
</div>
<script>
    function deleteClass(id) {
        if (confirm("Are you sure?")){
            var url = `list-class?id=`+id;
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

</script>
</body>
</html>
