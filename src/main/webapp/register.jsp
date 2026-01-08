<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>ShopWatcher | Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .register-box {
            width: 380px;
            background: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #2c3e50;
        }
        ul {
            list-style: none;
            padding: 0;
        }
        li {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .btn-register {
            width: 100%;
            padding: 10px;
            background: #27ae60;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-register:hover {
            background: #1e8449;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
        .success {
            color: green;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="register-box">
    <h2>Register Account</h2>

    <form action="register" method="post">
        <ul>
            <li>
                <label>Username</label>
                <input type="text" name="username"　value="${param.username}"  required>
            </li>
            <li>
                <label>Password</label>
                <input type="password" name="password" required>
            </li>
            <li>
                <label>Confirm Password</label>
                <input type="password" name="confirm" required>
            </li>
            <li>
                <label>Email</label>
                <input type="email" name="email"　value="${param.email}">
            </li>
            <li>
                <button type="submit" class="btn-register">Register</button>
            </li>
        </ul>
    </form>

    <div class="error">${requestScope.error}</div>
    <div class="success">${requestScope.success}</div>
</div>

</body>
</html>