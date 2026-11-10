<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Produit</title>
</head>
<body>
    <h1>
        <c:choose>
            <c:when test="${empty produit.id}">Nouveau produit</c:when>
            <c:otherwise>Modifier le produit</c:otherwise>
        </c:choose>
    </h1>

    <form method="post" action="${ctx}/produits">

        <%-- Champ caché id : uniquement en modification --%>
        <c:if test="${not empty produit.id}">
            <input type="hidden" name="id" value="${produit.id}">
        </c:if>

        <p>
            <label>Nom :
                <input type="text" name="nom" value="<c:out value='${produit.nom}'/>">
            </label>
            <c:if test="${not empty erreurs.nom}">
                <br><span style="color:red"><c:out value="${erreurs.nom}"/></span>
            </c:if>
        </p>

        <p>
            <label>Description :
                <input type="text" name="description" value="<c:out value='${produit.description}'/>">
            </label>
            <c:if test="${not empty erreurs.description}">
                <br><span style="color:red"><c:out value="${erreurs.description}"/></span>
            </c:if>
        </p>

        <p>
            <label>Prix :
                <input type="text" name="prix"
                       value="<c:out value='${not empty param.prix ? param.prix : produit.prix}'/>">
            </label>
            <c:if test="${not empty erreurs.prix}">
                <br><span style="color:red"><c:out value="${erreurs.prix}"/></span>
            </c:if>
        </p>

        <p>
            <label>Stock :
                <input type="text" name="stock"
                       value="<c:out value='${not empty param.stock ? param.stock : produit.stock}'/>">
            </label>
            <c:if test="${not empty erreurs.stock}">
                <br><span style="color:red"><c:out value="${erreurs.stock}"/></span>
            </c:if>
        </p>

        <button type="submit">Enregistrer</button>
        <a href="${ctx}/produits">Annuler</a>
    </form>
</body>
</html>