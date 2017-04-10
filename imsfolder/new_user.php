<?php

$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);


$response=array();
$username=$_POST["username"];
$pass=$_POST["pass"];
$submissions="0";
$correct_sub="0";
$name=$_POST["name"];
$address=$_POST["address"];
$phone_no=$_POST["phone_no"];
$email=$_POST["email"];
$sec_ans=$_POST["sec_ans"];
$sec_ques=$_POST["sec_ques"];
$result="insert into appusers values('$username','$pass','$submissions','$correct_sub','$name','$address','$phone_no','$email','$sec_ans','$sec_ques');";

if(mysqli_query($connect,$result))
{
$response["success"]=1;
$response["message"]="Success";
header('Content-Type: application/json');
echo json_encode($response);
}
else {
$response["success"]=0;
$response["message"]="Username already exists.";
header('Content-Type: application/json');
echo json_encode($response);
}

mysqli_close($connect);
?>