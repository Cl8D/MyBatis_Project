<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- JSP에서 JSTL 확장 태그를 사용하기 위해 tablib 지시자를 통해 라이브러리 선언해주기. -->
<!-- prefix는 코드 내에서 사용하는 (태그 이름 앞에 붙일) 접두사, URI는 라이브러리 식별자, 여기서는 core 사용. core가 기본이다! -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>example</title>
</head>
<body>
    <h2 style="text-align: center; margin-top: 100px;"> ids : ${ids}</h2>
    <!-- String[] 배열은 forEach를 통해서 출력.-->
    <c:forEach var="id" items="${ids}">
        <p style="text-align: center;">${id}</p>
    </c:forEach>

</body>