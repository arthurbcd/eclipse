<%@ page import="java.util.List" %>

<%@ page import="model.Question" %>
<%@ page import="model.Answer" %>

<%
List<Question> questions =
(List<Question>)
request.getAttribute("questions");

List<Answer> answers =
(List<Answer>)
request.getAttribute("answers");
%>

<html>

<body>

<h1>Réponses des utilisateurs</h1>

<%
for (Answer answer : answers) {
%>

    <hr>

    <h2>
        Utilisateur :
        <%= answer.getUtilisateurId() %>
    </h2>

<%
for (int i = 0;
     i < answer.getReponses().size();
     i++) {
%>

    <p>

        <b>
            <%= questions.get(i)
                         .getQuestion() %>
        </b>

        :

        <%= answer.getReponses().get(i) %>

    </p>

<%
}
}
%>

</body>
</html>