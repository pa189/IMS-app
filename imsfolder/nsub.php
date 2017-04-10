<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);


$username=$_POST["username"];
$query="select * from appsubmissions where username='$username' and status='c';";
$qu="select * from appsubmissions where username='$username';";
$result=mysqli_query($connect,$query);
$re=mysqli_query($connect,$qu);
$response = array();
$rows=mysqli_num_rows($result);
$ro=mysqli_num_rows($re);
$response["total"]=$ro;

$response["correct"]=$rows;

header('Content-Type: application/json');
echo json_encode($response);
mysqli_close($connect);
?>