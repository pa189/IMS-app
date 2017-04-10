<?php

$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$username=$_POST["username"];
$pass=$_POST["pass"];
$name=$_POST["name"];
$address=$_POST["address"];
$phone_no=$_POST["phone_no"];
$email=$_POST["email"];
$sec_ans=$_POST["sec_ans"];

$query="update appusers set pass='$pass',name='$name',address='$address',phone_no='$phone_no',email='$email',sec_ans='$sec_ans' where username='$username';";
if(mysqli_query($connect,$query))
{
$response="Information updated!";
header('Content-Type: application/json');
echo json_encode($response);
}
else {

$response="Error!";
header('Content-Type: application/json');
echo json_encode($response);
}
