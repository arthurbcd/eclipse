<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="metier.*" %>
<jsp:useBean id="liste" class="java.util.ArrayList" scope="request"></jsp:useBean>
<title>Liste</title>
</head>
<body>
<% 
for(Object c : liste)
	   out.println("Pizza: "+((Commande)c).getPizza().getNom()+" Quantité:" +((Commande)c).getQuantite()+" <a href=Affiche_form?type=del&id="+((Commande)c).getId() +">-</a><br>");
%>
<a href="./Saisie_pizza.jsp">Saisie de pizza</a>
</body>
</html>