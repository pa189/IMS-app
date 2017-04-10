<?php

$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$question_no=$_POST["question_no"];
$username=$_POST["username"];
$status=$_POST["status"];

$query="update appsubmissions set status='$status' where username like '$username' and question_no='$question_no';";
if(mysqli_query($connect,$query))
{
$response="Changed!";
header('Content-Type: application/json');
echo json_encode($response);
}
else {

$response="Error!";
header('Content-Type: application/json');
echo json_encode($response);
}

mysqli_close($connect);
?>