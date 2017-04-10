<?php

$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$username=$_POST["username"];
$paid=$_POST["paid"];

$query="update appusers set paid='$paid' where username like '$username';";
if(mysqli_query($connect,$query))
{
$response="Done!";
header('Content-Type: application/json');
echo json_encode($response);
}
else {

$response="Incorrect username!";
header('Content-Type: application/json');
echo json_encode($response);
}

mysqli_close($connect);
?>