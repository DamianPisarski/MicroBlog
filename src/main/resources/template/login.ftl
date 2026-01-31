<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"Login"}</title>
</head>
<body style="font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; max-width: 720px; margin: 40px auto; padding: 0 16px">
<nav>
    <a href="/hello">Home</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</nav>
<hr/>

<h2>Login</h2>

<#if msg?has_content>
    <p style="color:#b00">${msg}</p>
</#if>

<form method="post" action="/login">
    <label>Username</label><br/>
    <input name="username"/><br/>

    <label>Password</label><br/>
    <input type="password" name="password"/><br/><br/>

    <button type="submit">Login</button>
</form>

<p>Nie masz konta? <a href="/register">Zarejestruj się</a></p>
</body>
</html>
