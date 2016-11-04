<?php
$servername = "mysql1.000webhost.com"; //replace it with your database server name
$username = "a9929614_udhaar";  //replace it with your database username
$password = "architanshul7";  //replace it with your database password
$dbname = "a9929614_udhaar";

// Create connection

 //$conn = @mysql_connect($servername, $username, $password, $dbname);

$conn = @mysql_connect($servername, $username, $password)or die(mysql_error());
$db = @mysql_select_db($dbname,$conn)or die(mysql_error()); 

// Check connection

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

?>


