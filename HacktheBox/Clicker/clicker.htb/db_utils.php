<?php
session_start();

$db_server="localhost";
$db_username="clicker_db_user";
$db_password="clicker_db_password";
$db_name="clicker";
$mysqli = new mysqli($db_server, $db_username, $db_password, $db_name);
$pdo = new PDO("mysql:dbname=$db_name;host=$db_server", $db_username, $db_password);

function check_exists($player) {
	global $pdo;
	$params = ["player" => $player];
	$stmt = $pdo->prepare("SELECT count(*) FROM players WHERE username = :player");
	$stmt->execute($params);
	$result = $stmt->fetchColumn();
	if ($result > 0) {
		return true;
	}
	return false;
}

function create_new_player($player, $password) {
	global $pdo;
	$params = ["player"=>$player, "password"=>hash("sha256", $password)];
	$stmt = $pdo->prepare("INSERT INTO players(username, nickname, password, role, clicks, level) VALUES (:player,:player,:password,'User',0,0)");
	$stmt->execute($params);
}

function check_auth($player, $password) {
	global $pdo;
	$params = ["player" => $player];
	$stmt = $pdo->prepare("SELECT password FROM players WHERE username = :player");
	$stmt->execute($params);
	if ($stmt->rowCount() > 0) {
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		if(strcmp($row['password'], hash("sha256",$password)) == 0){
			return true;
		}
	}
	return false;
}

function load_profile($player) {
	global $pdo;
	$params = ["player"=>$player];
	$stmt = $pdo->prepare("SELECT nickname, role, clicks, level FROM players WHERE username = :player");
	$stmt->execute($params);
	if ($stmt->rowCount() > 0) {
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		return $row;
	}
	return array();
}

function save_profile($player, $args) {
	global $pdo;
  	$params = ["player"=>$player];
	$setStr = "";
  	foreach ($args as $key => $value) {
    		$setStr .= $key . "=" . $pdo->quote($value) . ",";
	}
  	$setStr = rtrim($setStr, ",");
  	$stmt = $pdo->prepare("UPDATE players SET $setStr WHERE username = :player");
  	$stmt -> execute($params);
}

// ONLY FOR THE ADMIN
function get_top_players($number) {
	global $pdo;
	$stmt = $pdo->query("SELECT nickname,clicks,level FROM players WHERE clicks >= " . $number);
	$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
	return $result;
}
function get_current_player($player) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT nickname, clicks, level FROM players WHERE username = :player");
    $stmt->bindParam(':player', $player, PDO::PARAM_STR);
    $stmt->execute();
    if ($stmt->rowCount() > 0) {
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        return $result;
    } else {
        return null; 
    }
}

?>
