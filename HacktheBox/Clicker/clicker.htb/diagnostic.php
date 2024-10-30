<?php
if (isset($_GET["token"])) {
    if (strcmp(md5($_GET["token"]), "ac0e5a6a3a50b5639e69ae6d8cd49f40") != 0) {
        header("HTTP/1.1 401 Unauthorized");
        exit;
	}
}
else {
    header("HTTP/1.1 401 Unauthorized");
    die;
}

function array_to_xml( $data, &$xml_data ) {
    foreach( $data as $key => $value ) {
        if( is_array($value) ) {
            if( is_numeric($key) ){
                $key = 'item'.$key;
            }
        $subnode = $xml_data->addChild($key);
        array_to_xml($value, $subnode);
        } else {
            $xml_data->addChild("$key",htmlspecialchars("$value"));
        }
	}
}

$db_server="localhost";
$db_username="clicker_db_user";
$db_password="clicker_db_password";
$db_name="clicker";

$connection_test = "OK";

try {
	$pdo = new PDO("mysql:dbname=$db_name;host=$db_server", $db_username, $db_password, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
} catch(PDOException $ex){
    $connection_test = "KO";
}
$data=[];
$data["timestamp"] = time();
$data["date"] = date("Y/m/d h:i:sa");
$data["php-version"] = phpversion();
$data["test-connection-db"] = $connection_test;
$data["memory-usage"] = memory_get_usage();
$env = getenv();
$data["environment"] = $env;

$xml_data = new SimpleXMLElement('<?xml version="1.0"?><data></data>');
array_to_xml($data,$xml_data);
$result = $xml_data->asXML();
print $result;
?>
