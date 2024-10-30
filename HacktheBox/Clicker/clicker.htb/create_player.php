<?php
session_start();
include_once("db_utils.php");

if (isset($_POST['username']) && isset($_POST['password']) && $_POST['username'] != "" && $_POST['password'] != "") {
	if (! ctype_alnum($_POST["username"])) {
		header('Location: /register.php?err=Special characters are not allowed');
	}
	elseif(check_exists($_POST['username'])) {
		header('Location: /register.php?err=User already exists');
	}
	else {
		create_new_player($_POST['username'], $_POST['password']);
		header('Location: /index.php?msg=Successfully registered');
	}
}

?>
