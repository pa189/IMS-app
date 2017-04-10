<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$question_no=$_POST["question_no"];
$username=$_POST["username"];
$submission=$_POST["submission"];
$image=$_POST["imgsub"];
if(strcmp($image,"null")!=0)
{$ext=".png";
$imgsub=$username.$question_no.$ext;
$actualpath = "uploads/".$imgsub;
unlink($actualpath);
file_put_contents($actualpath,base64_decode($image));
}
else
{$imgsub="null";}
$query="update appsubmissions set submission='$submission',imgsub='$imgsub' where username like '$username' and question_no='$question_no';";
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

mysqli_close($connect);
?>