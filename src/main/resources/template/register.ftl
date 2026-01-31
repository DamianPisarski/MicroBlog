<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"Register"}</title>
</head>
<body style="font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; max-width: 720px; margin: 40px auto; padding: 0 16px">
<nav>
    <a href="/hello">Home</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</nav>
<hr/>

<h2>Register</h2>

<#if msg?has_content>
    <p style="color:#b00">${msg}</p>
</#if>

<form method="post" action="/register">
    <label>Username</label><br/>
    <input name="username"/><br/>

    <label>Display Name</label><br/>
    <input name="displayName"/><br/>

    <label>Email</label><br/>
    <input name="email"/><br/>

    <label>Password</label><br/>
    <input type="password" name="password"/><br/><br/>

    <button type="submit">Register</button>
</form>

<p>Masz konto? <a href="/login">Zaloguj się</a></p>
</body>
</html>
