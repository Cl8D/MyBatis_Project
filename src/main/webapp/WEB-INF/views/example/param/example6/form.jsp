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
    <c:forEach var="id" items="${ids}">
        <p style="text-align: center;">${id}</p>
    </c:forEach>
    <!-- jquery 사용하기-->
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script>
        // 서버로 보낼 데이터를 JSON 형태로 만들기
        $(function() {
            var userData = {
                // 내부 필드 이름을 잘 맞춰줘야 한다...!
                user: {
                    name: "jenny",
                    age: "22",
                    address: "Korea"
                }
            };
            /*
                user:
                    address: "Korea"
                    age: "22"
                    name: "jenny"
             */
            console.log(userData);
            <!-- ajax 사용하기 -->
            $.ajax({
                url: '/example/param/example6/saveData', // URL은 @RequestMapping의 url이랑 동일하게
                type: 'post', // 전송 방식
                data: JSON.stringify(userData), // 전송할 파라미터 (만든 데이터), string으로 변환
                contentType: 'application/json', // JSON형태로 해야 함수에서 @RequestBody의 Map으로 받을 수 있다.
                dataType: 'json', // 받아올 데이터 타입 (json, xml... 그런 형태)
                success: (data) => {
                    // result: true
                    console.log(data);
                } // 성공 시 수행할 핸들러,
            });
        });
    </script>
</body>