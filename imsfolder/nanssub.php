<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$s_no=$_POST["s_no"];
$answer=$_POST["answer"];

$image=$_POST["imgans"];
if(strcmp($image,"null")!=0)
{$ext=".png";
$qu="ans";
$imgsub=$qu.$s_no.$ext;
$actualpath = "uploads/".$imgsub;
unlink($actualpath);
file_put_contents($actualpath,base64_decode($image));
}
else
{$imgsub="null";}
$query="update appquestions set answer='$answer',imgans='$imgsub' where s_no='$s_no';";
if(mysqli_query($connect,$query))
{
$response="Answer submitted!";
header('Content-Type: application/json');
echo json_encode($response);
}
else {

$response="Error!";
header('Content-Type: application/json');
echo json_encode($response);
}