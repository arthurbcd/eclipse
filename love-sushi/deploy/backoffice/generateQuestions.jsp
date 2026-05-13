<%@ page contentType="text/html;charset=UTF-8" %>

<%
int nbQuestions =
Integer.parseInt(
        request.getParameter(
                "nbQuestions"
        )
);
%>

<html>

<body>

<h1>Questions</h1>

<form action="../CreateQuestionsServlet"
      method="post">

<%
for (int i = 0; i < nbQuestions; i++) {
%>

    <h3>
        Question <%= i + 1 %>
    </h3>

    <input type="text"
           name="question"
           placeholder="Question"
           required>

    <br><br>

    <input type="text"
           name="choix1"
           placeholder="Choix 1"
           required>

    <br><br>

    <input type="text"
           name="choix2"
           placeholder="Choix 2"
           required>

    <br><br><hr><br>

<%
}
%>

    <button type="submit">
        Enregistrer
    </button>

</form>

</body>
</html>