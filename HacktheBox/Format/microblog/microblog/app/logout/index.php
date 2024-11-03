<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();
session_destroy();
header("Location: /");
?>