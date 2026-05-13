<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="metier.*" %>
<jsp:useBean id="commande" class="metier.Commande" scope="request"></jsp:useBean>
<title>Commande</title>
</head>
<body>
Pizza: <%= commande.getPizza().getNom() %> <br>
Quantité <%= commande.getQuantite() %>

<a href="Affiche_form?type=list">Liste des commandes</a>
</body>
</html>