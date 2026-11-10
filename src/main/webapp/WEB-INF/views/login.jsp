
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
</head>
<body>
    <h1>Connexion</h1>

    <c:if test="${not empty erreur}">
        <p style="color:red"><c:out value="${erreur}"/></p>
    </c:if>

    <form method="post" action="${ctx}/login">
        <p>
            <label>Login :
                <input type="text" name="login" value="<c:out value='${login}'/>">
            </label>
        </p>
        <p>
            <label>Mot de passe :
                <input type="password" name="password">
            </label>
        </p>
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>