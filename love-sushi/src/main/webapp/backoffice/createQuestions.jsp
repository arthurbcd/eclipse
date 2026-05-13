<%@ page contentType="text/html;charset=UTF-8" %>

<html>

<body>

<h1>Création des questions</h1>

<form action="generateQuestions.jsp"
      method="post">

    <label>
        Nombre de questions :
    </label>

    <input type="number"
           name="nbQuestions"
           min="1"
           required>

    <br><br>

    <button type="submit">
        Continuer
    </button>

</form>

</body>
</html>