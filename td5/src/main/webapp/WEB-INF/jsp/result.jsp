<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <jsp:useBean id="formData" scope="request" class="fr.ubdx.net.web.FormData"></jsp:useBean>
    <jsp:useBean id="formDataList" scope="request" type="java.util.List"></jsp:useBean>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Résultat td5</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-fluid mt-3">
            <main>
                <h3>Dernière saisie</h3>
                <div class="card mb-4" style="width: 25rem;">
                    <div class="card-header">Saisie Actuelle</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Nom:
                            <jsp:getProperty property="name" name="formData" />
                        </li>
                        <li class="list-group-item">Email:
                            <jsp:getProperty property="email" name="formData" />
                        </li>
                        <li class="list-group-item">Message:
                            <jsp:getProperty property="message" name="formData" />
                        </li>
                    </ul>
                </div>

                <h3>Historique des saisies (<%= formDataList.size() %>)</h3>
                <div class="d-flex flex-wrap gap-2 mb-4">
                    <% for (int i=0; i < formDataList.size(); i++) { fr.ubdx.net.web.FormData
                        item=(fr.ubdx.net.web.FormData) formDataList.get(i); %>
                        <div class="card" style="width: 18rem;">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><b>#<%= i + 1 %></b> - <%= item.getName() %>
                                </li>
                                <li class="list-group-item">
                                    <%= item.getEmail() %>
                                </li>
                            </ul>
                        </div>
                        <% } %>
                </div>

                <a href="${pageContext.request.contextPath}/form.jsp" class="btn btn-secondary">Retour au formulaire</a>
            </main>
        </div>
    </body>

    </html>
