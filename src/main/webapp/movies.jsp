<%--
  Created by IntelliJ IDEA.
  User: gmikhailov
  Date: 06.11.2021
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movies</title>
</head>
<body>
<section>
    <a href="movies?action=create">Add Movie</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Year</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <jsp:useBean id="movies" scope="request" type="java.util.List"/>
        <c:forEach var="movie" items="${movies}">
            <tr>
                <td>${movie.title}</td>
                <td>${movie.description}</td>
                <td>${movie.year}</td>
                <td><a href="movies?action=update&id=${movie.id}">Update</a> </td>
                <td><a href="movies?action=delete&id=${movie.id}">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
