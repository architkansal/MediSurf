<?php
$servername = "localhost"; //replace it with your database server name
$username = "root";  //replace it with your database username
$password = "";  //replace it with your database password
$dbname = "medisurf";

// Create connection

 //$conn = @mysql_connect($servername, $username, $password, $dbname);

$conn = @mysql_connect($servername, $username, $password)or die(mysql_error());
$db = @mysql_select_db($dbname,$conn)or die(mysql_error()); 

// Check connection

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

?>


