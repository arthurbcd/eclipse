<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.List" %>
        <%@ page import="fr.ubdx.net.web.FormData" %>
            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Résultat td5</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        max-width: 900px;
                        margin: 2rem auto;
                        padding: 0 1rem;
                        line-height: 1.5;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 1rem;
                    }

                    th,
                    td {
                        border: 1px solid #ccc;
                        padding: 0.75rem;
                        text-align: left;
                        vertical-align: top;
                    }

                    th {
                        background: #f5f5f5;
                    }

                    .actions {
                        margin-top: 1.5rem;
                    }
                </style>
            </head>

            <body>
                <h1>Données reçues</h1>
                <p>Dernière saisie :</p>
                <p>Nom : ${formData.name}</p>
                <p>Email : ${formData.email}</p>
                <p>Message : ${formData.message}</p>

                <h2>Historique des saisies</h2>
                <% List<FormData> formDataList = (List<FormData>) request.getAttribute("formDataList");
                        if (formDataList != null && !formDataList.isEmpty()) {
                        %>
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nom</th>
                                    <th>Email</th>
                                    <th>Message</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (int index=0; index < formDataList.size(); index++) { FormData
                                    item=formDataList.get(index); %>
                                    <tr>
                                        <td>
                                            <%= index + 1 %>
                                        </td>
                                        <td>
                                            <%= item.getName() %>
                                        </td>
                                        <td>
                                            <%= item.getEmail() %>
                                        </td>
                                        <td>
                                            <%= item.getMessage() %>
                                        </td>
                                    </tr>
                                    <% } %>
                            </tbody>
                        </table>
                        <% } else { %>
                            <p>Aucune saisie enregistrée.</p>
                            <% } %>

                                <div class="actions">
                                    <p><a href="${pageContext.request.contextPath}/form.jsp">Retour au formulaire</a>
                                    </p>
                                </div>
            </body>

            </html>
