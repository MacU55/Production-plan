<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="styles.css">
    <title>Production Plan</title>
</head>
<body>

<h1>Виробничий план</h1>
<p>Вітаю, ${name}</p>
<a href="/production-plan/logout" class ="btn">Вийти</a>
<hr>
<a href="/production-plan/orders" class ="btn">Замовлення</a> |
<a href="/production-plan/designs" class ="btn">Конструкторській відділ</a> |
<a href="/production-plan/users" class ="btn">Користувачі</a> |


<div>
    ${container}
</div>

</body>
</html> 