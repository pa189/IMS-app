<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$username=$_POST["username"];
$query="select sec_ques,sec_ans from appusers where username like '$username';";
$result=mysqli_query($connect,$query);
$response=array();
if($row=mysqli_fetch_assoc($result))
{$response["success"]=1;
$response["user"]=$row;}
else
{$response["success"]=0;
$response["user"]="Incorrect username";}
header('Content-Type: application/json');
echo json_encode($response);
mysqli_close($connect);
?>