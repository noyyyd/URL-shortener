<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>URL Shortener</title>
    <link rel="stylesheet" type="text/css" href="css/mainPageStyle.css">
</head>

<body>
<div>
    <h1 class="mainLink">URL Shortener</h1>
</div>
<div class="mainForm">

    <form action="/mainPage" method="GET">
        <input id="input" type="text" name="originalLink" size="40"/>
        <input type="hidden" name="command" value="reduce"/>
        <button type="submit" name="submit">Click</button>
    </form>
    <br>
</div>
<br><br><br>
<div class="shortedLink">
    <c:if test="${hidden == true}">
        <a href="http://localhost:8080/l/${shortedLink}">http://localhost:8080/l/${shortedLink}</a>
    </c:if>
</div>
</body>
</html>