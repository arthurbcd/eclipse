<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-15">
<title>Affichage</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/style.css">
<jsp:useBean id="personne" scope="request" class="tds.metier.Personne"></jsp:useBean>
</head>
<body>
<div class='container-fluid'>
	<main>
	<div class="card" style="width: 18rem;">
	  <div class="card-header">
	    Personne
	  </div>
	  <ul class="list-group list-group-flush">
	    <li class="list-group-item">Nom: <jsp:getProperty property="nom" name="personne"/></li>
	    <li class="list-group-item">Prénom: <jsp:getProperty property="prenom" name="personne"/></li>
	  </ul>
	</div>
	</main>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>