<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();
if(isset($_SESSION['username'])) {
    header("Location: /dashboard");
    exit;
}

if (isset($_POST['first-name']) && isset($_POST['last-name']) && isset($_POST['username']) && isset($_POST['password'])) {
    if(!preg_match('/^[a-zA-Z\-]+$/', $_POST['first-name']) || strlen($_POST['first-name']) > 50) {
        print_r("Invalid first name");
        header("Location: /register?message=Invalid field&status=fail");
        exit();
    }
    if(!preg_match('/^[a-zA-Z\-]+$/', $_POST['last-name']) || strlen($_POST['last-name']) > 50) {
        print_r("Invalid last name");
        header("Location: /register?message=Invalid field&status=fail");
        exit();
    }
    if(!preg_match('/^[a-zA-Z0-9.\-_]+$/', $_POST['username']) || strlen($_POST['username']) > 50) {
        print_r("Invalid username");
        header("Location: /register?message=Invalid field&status=fail");
        exit();
    }
    $redis = new Redis();
    $redis->connect('/var/run/redis/redis.sock');
    $username = $redis->HGET(trim($_POST['username']), "username");
    if(strlen(strval($username)) > 0) {
        header("Location: /register?message=User already exists&status=fail");
    }
    else {
        $redis->HSET(trim($_POST['username']), "username", trim($_POST['username']));
        $redis->HSET(trim($_POST['username']), "password", trim($_POST['password']));
        $redis->HSET(trim($_POST['username']), "first-name", trim($_POST['first-name']));
        $redis->HSET(trim($_POST['username']), "last-name", trim($_POST['last-name']));
        $redis->HSET(trim($_POST['username']), "pro", "false"); //not ready yet, license keys coming soon
        $_SESSION['username'] = trim($_POST['username']);
        header("Location: /dashboard?message=Registration successful!&status=success");
    }
}
?>
<!DOCTYPE html>
<head>
<link rel="icon" type="image/x-icon" href="../brain.ico">
<link rel="stylesheet" href="http://microblog.htb/static/css/styles.css">
<script src="http://microblog.htb/static/js/jquery.js"></script>
<title>Register - Microblog</title>
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
                <a href="/login" class = "menu-button">Login</a>
                <a href="/register" class = "menu-button current-select">Register</a>
            </div>
        </div>
        <div class = "header-content-item">
            <span class = "big-text heading">Register</div>
        </div>
    </div>
    <div style = "min-height: 40px"></div>
    <div class = "signin-form-outer">
        <form class = "signin-form" action="<?=$_SERVER['PHP_SELF']?>" method="POST">
            <input type="text" class = "form-name-field" id="first-name" name="first-name" placeholder="First Name" pattern="[A-Za-z\-]+" maxlength="50" title="Letters and hyphens only, 50 character length limit" required><input type="text" class = "form-name-field" id="last-name" name="last-name" placeholder="Last Name" pattern="[A-Za-z\-]+" maxlength="50" title="Letters and hyphens only, 50 character length limit" required><br/>
            <input type="text" id="username" name="username" placeholder="Username" pattern="[A-Za-z\._\-0-9]+" maxlength="50" title="Letters, numbers, . _ - only, 50 character length limit" required><br/>
            <input type="password" id="password" name="password" placeholder="Password" required><br/>
            <input type="submit" value="Register">
        </form>
    </div>
    <div style = "min-height: 40px"></div>
    <footer>
        © 2022 Microblog<br/>
        <a href="https://www.vecteezy.com/free-vector/brain">Brain Vectors by Vecteezy</a>
    </footer>
</body>
</html>
