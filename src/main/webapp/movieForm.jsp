<%--
  Created by IntelliJ IDEA.
  User: gmikhailov
  Date: 06.11.2021
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Movie</title>
    <link rel="stylesheet" href="css/Style.css">
</head>
<body>
<section>
    <h2>${param.action == 'create' ? 'Add book' : 'Edit book'}</h2>
    <jsp:useBean id="movie" type="edu.mikhailov.Movie" scope="request"/>
    <form method="post" action="movies">
        <input type="hidden" name="id" value="${movie.id}">
        <dl>
            <dt>Title</dt>
            <dd><input type="text" value="${movie.title}" name="title" required></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd><input type="text" value="${movie.description}" size=100 name="description" required></dd>
        </dl>
        <dl>
            <dt>Year</dt>
            <dd><input type="number" value="${movie.year}" min="1" max="2021" name="year" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
