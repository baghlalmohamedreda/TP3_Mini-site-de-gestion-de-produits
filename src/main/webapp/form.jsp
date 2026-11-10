
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
</head>
<body>
    <h1>Inscription</h1>

    <form method="post" action="${pageContext.request.contextPath}/inscription">
        Nom : <input type="text" name="nom" required><br><br>
        Email : <input type="email" name="email" required><br><br>
        Age : <input type="number" name="age" required><br><br>

        <button type="submit">S'inscrire</button>
    </form>
</body>
</html>
