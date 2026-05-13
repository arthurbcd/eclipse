<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>

<%
List<Question> questions =
(List<Question>)
request.getAttribute("questions");
%>

<html>

<body>

<h1>Questions</h1>

<form action="SubmitAnswersServlet"
      method="post">

    <label>
        Utilisateur :
    </label>

    <input type="text"
           name="utilisateur_id"
           required>

    <br><br>

<%
for (int i = 0; i < questions.size(); i++) {

    Question q = questions.get(i);
%>

    <h3>
        <%= q.getQuestion() %>
    </h3>

    <input type="radio"
           name="q<%= i %>"
           value="<%= q.getChoix1() %>"
           required>

    <%= q.getChoix1() %>

    <br>

    <input type="radio"
           name="q<%= i %>"
           value="<%= q.getChoix2() %>">

    <%= q.getChoix2() %>

    <br><br>

<%
}
%>

    <button type="submit">
        Envoyer
    </button>

</form>

</body>
</html>