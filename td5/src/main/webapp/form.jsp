<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire td5</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 720px;
            margin: 2rem auto;
            padding: 0 1rem;
            line-height: 1.5;
        }

        form {
            display: grid;
            gap: 1rem;
            margin-top: 1rem;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 0.35rem;
        }

        input,
        textarea,
        button {
            font: inherit;
        }

        input,
        textarea {
            width: 100%;
            box-sizing: border-box;
            padding: 0.7rem;
        }

        .actions {
            display: flex;
            gap: 0.75rem;
        }

        .error {
            color: #b00020;
            min-height: 1.25rem;
        }
    </style>
</head>
<body>
    <h1>Formulaire td5</h1>
    <form id="formulaire" method="post" action="${pageContext.request.contextPath}/submitForm" novalidate>
        <div>
            <label for="name">Nom :</label>
            <input type="text" id="name" name="name" required minlength="2" maxlength="60" autocomplete="name">
            <div class="error" id="nameError"></div>
        </div>
        <div>
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" required autocomplete="email">
            <div class="error" id="emailError"></div>
        </div>
        <div>
            <label for="message">Message :</label>
            <textarea id="message" name="message" rows="4" cols="40" required minlength="5" maxlength="500"></textarea>
            <div class="error" id="messageError"></div>
        </div>
        <div class="actions">
            <button type="submit">Envoyer</button>
        </div>
    </form>

    <script>
        const form = document.getElementById('formulaire');
        const nameInput = document.getElementById('name');
        const emailInput = document.getElementById('email');
        const messageInput = document.getElementById('message');

        const nameError = document.getElementById('nameError');
        const emailError = document.getElementById('emailError');
        const messageError = document.getElementById('messageError');

        function validateName() {
            if (nameInput.value.trim().length < 2) {
                nameError.textContent = 'Le nom doit contenir au moins 2 caractères.';
                return false;
            }

            nameError.textContent = '';
            return true;
        }

        function validateEmail() {
            if (!emailInput.validity.valid) {
                emailError.textContent = 'Veuillez saisir un email valide.';
                return false;
            }

            emailError.textContent = '';
            return true;
        }

        function validateMessage() {
            if (messageInput.value.trim().length < 5) {
                messageError.textContent = 'Le message doit contenir au moins 5 caractères.';
                return false;
            }

            messageError.textContent = '';
            return true;
        }

        form.addEventListener('submit', function (event) {
            const isValid = validateName() && validateEmail() && validateMessage();

            if (!isValid) {
                event.preventDefault();
            }
        });

        nameInput.addEventListener('input', validateName);
        emailInput.addEventListener('input', validateEmail);
        messageInput.addEventListener('input', validateMessage);
    </script>
</body>
</html>