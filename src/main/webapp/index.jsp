<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/20 0020
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
      <%--请求转发--%>
      <%
        request.setAttribute("id",1);
        request.getRequestDispatcher("./my/index10.do").forward(request,response);
      %>
  </body>
</html>
