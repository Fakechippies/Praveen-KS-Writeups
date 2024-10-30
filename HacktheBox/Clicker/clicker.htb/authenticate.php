<?php
session_start();
include_once("db_utils.php");

if (isset($_POST['username']) && isset($_POST['password']) && $_POST['username'] != "" && $_POST['password'] != "") {
	if(check_auth($_POST['username'], $_POST['password'])) {
		$_SESSION["PLAYER"] = $_POST["username"];
		$profile = load_profile($_POST["username"]);
		$_SESSION["NICKNAME"] = $profile["nickname"];
		$_SESSION["ROLE"] = $profile["role"];
		$_SESSION["CLICKS"] = $profile["clicks"];
		$_SESSION["LEVEL"] = $profile["level"];
		header('Location: /index.php');
	}
	else {
		header('Location: /login.php?err=Authentication Failed');
	}
}
?>
