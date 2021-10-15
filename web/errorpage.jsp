<%-- 
    Document   : errorpage
    Created on : Sep 21, 2021, 7:16:18 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page</title>
        <style>
            .Title{
                color: yellow;
                background: black;
                text-align: center;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h1 class="Title">*YELLOW MOON SHOP*</h1>
        <a href="MainController"><input type="submit" value="Back to home page" /></a>
        <br/>
        <br/>
        <h1 style="text-align: center">Something wrong!</h1>
        <h2 style="text-align: center">${requestScope.ERRORLOGIN}</h2>
    </body>
</html>
