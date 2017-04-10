<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$pass=$_POST["pass"];
$username=$_POST["username"];
$query="select * from appusers where pass like '$pass' and username like '$username';";
$result=mysqli_query($connect,$query);
$response = array();
$rows=mysqli_num_rows($result);
if($rows>0)
{$response["success"]=1;
$row=mysqli_fetch_assoc($result);
$response["entry"]=$row;
}
else
{$response["success"]=0;
$response["entry"]="Username or password incorrect.";
}
header('Content-Type: application/json');
echo json_encode($response);
mysqli_close($connect);
?>