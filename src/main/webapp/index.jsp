<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Currency Exchange</title>
</head>
<body>
<h1>Получить курс валюты в белорусских рублях</h1>
<form action="currency" method="post">
    <label for="currency">Наименование валюты (dollar, euro):</label>
    <input type="text" id="currency" name="currency">
    <input type="submit" value="Получить курс">
</form>

<%
    Object rateObj = request.getAttribute("rate");
    if (rateObj != null) {
        String rate = rateObj instanceof Double ? String.valueOf(rateObj) : (String) rateObj;
%>
<h2>Курс: <%= rate %> бел. руб.</h2>
<%
    }
%>
</body>
</html>