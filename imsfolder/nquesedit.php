<?php

$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$s_no=$_POST["s_no"];
$question=$_POST["question"];
$tag=$_POST["tag"];
$date=$_POST["date"];
$image=$_POST["image"];
if(strcmp($image,"null")!=0)
{$ext=".png";
$qu="ques";
$imgsub=$qu.$s_no.$ext;
$actualpath = "uploads/".$imgsub;
unlink($actualpath);
file_put_contents($actualpath,base64_decode($image));
}
else
{$imgsub="null";}
$query="update appquestions set question='$question',image='$imgsub',tag='$tag',date='$date' where s_no='$s_no';";
if(mysqli_query($connect,$query))
{
$response="Question submitted!";
header('Content-Type: application/json');
echo json_encode($response);
}
else {

$response="Error!";
header('Content-Type: application/json');
echo json_encode($response);
}
