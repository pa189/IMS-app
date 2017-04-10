<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$username=$_POST["username"];
$pass=$_POST["pass"];
$query="update appusers set pass='$pass' where username like '$username';";
$result=mysqli_query($connect,$query);
$response=array();
if($result)
{
$response["success"]=1;
$response["message"]="Successfully updated password";
header('Content-Type: application/json');
echo json_encode($response);
}
else {
$response["success"]=0;
$response["message"]="Error updating password";
header('Content-Type: application/json');
echo json_encode($response);
}

mysqli_close($connect);
?>