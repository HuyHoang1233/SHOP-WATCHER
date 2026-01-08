<%-- 
    Document   : forget-vertify
    Created on : Jan 5, 2026, 11:17:58 PM
    Author     : Huy Hoàng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Vertify</h1>
        ${requestScope.error}
        <form method="POST" action="vertify"> 
            <input type="text" name="email" value="${sessionScope.EMAIL}" />   
            <input type="text" name="otp" value="" />
            <input type="submit" value="XÁC THỰC" />
        </form>
    </body>
</html>
