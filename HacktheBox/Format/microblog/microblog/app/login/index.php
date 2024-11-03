<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();
if(isset($_SESSION['username'])) {
    header("Location: /dashboard");
    exit;
}

if (isset($_POST['username']) && isset($_POST['password'])) {
    $redis = new Redis();
    $redis->connect('/var/run/redis/redis.sock');
    $username = $redis->HGET(trim($_POST['username']), "username");
    if(strlen(strval($username)) > 0) {
        if(strval($redis->HGET(trim($_POST['username']), "username")) == trim($_POST['username']) && strval($redis->HGET(trim($_POST['username']), "password")) == trim($_POST['password'])) {
            $_SESSION['username'] = trim($_POST['username']);
            header("Location: /dashboard?message=Login successful!&status=success");
        }
        else {
            header("Location: /login?message=Login failed&status=fail");
        }
    }
    else {
        header("Location: /login?message=Login failed&status=fail");
    }
}
?>
<!DOCTYPE html>
<head>
<link rel="icon" type="image/x-icon" href="../brain.ico">
<link rel="stylesheet" href="http://microblog.htb/static/css/styles.css">
<script src="http://microblog.htb/static/js/jquery.js"></script>
<title>Login - Microblog</title>
<script>
    $(window).on('load', function(){
        const queryString = window.location.search;
        if(queryString) {
            const urlParams = new URLSearchParams(queryString);
            if(urlParams.get('message') && urlParams.get('status')) {
                const status = urlParams.get('status')
                const message = urlParams.get('message')
                $(".floating-message").css("display", "block");
                $(".floating-message").children(".message-content").text(message);
                if(status === "fail") {
                    $(".floating-message").css("background-color", "#AF0606");
                }
                else {
                    $(".floating-message").css("background-color", "#4BB543");
                }
            }
        }
    });
</script>
</head>
<body>
    <div class="floating-message">
        <span class="message-content" style = "margin-right: 10px"></span>
        <span class="closebtn" style = "font-weight: bold;" onclick="this.parentElement.style.display='none';">&times;</span>
    </div>
    <div class = "semi-circle blue-fill">
        <div class="navbar">
            <a href="/" class="float-left title">Microblog</a>
            <div class = "float-right select-buttons">
                <a href="/login" class = "menu-button current-select">Login</a>
                <a href="/register" class = "menu-button">Register</a>
            </div>
        </div>
        <div class = "header-content-item">
            <span class = "big-text heading">Login</div>
        </div>
    </div>
    <div style = "min-height: 40px"></div>
    <div class = "signin-form-outer">
        <form class = "signin-form" action="<?=$_SERVER['PHP_SELF']?>" method="POST">
            <input type="text" id="username" name="username" placeholder="Username" required><br/>
            <input type="password" id="password" name="password" placeholder="Password" required><br/>
            <input type="submit" value="Login">
        </form>
    </div>
    <div style = "min-height: 40px"></div>
    <footer>
        © 2022 Microblog<br/>
        <a href="https://www.vecteezy.com/free-vector/brain">Brain Vectors by Vecteezy</a>
    </footer>
</body>
</html>
