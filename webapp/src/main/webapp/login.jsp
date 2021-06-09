<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="styles.css">
<title>Production Plan</title>
</head>
<body>
    <div>
        <h1>Введіть електронну пошту і пароль</h1>
        <form action="/production-plan/login" method="post">
            <label for="email">Email:</label>
            <input name="email" size="30" />
            <br><br>
            <label for="password">Password:</label>
            <input type="password" name="password" size="30" />
            <br>${message}
            <br><br>           
            <button type="submit">Увійти</button>
        </form>
    </div>
</body>
</html>
