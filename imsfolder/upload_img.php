<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$image =$_POST['imgsub'];
$username=$_POST['username'];
$question_no=$_POST['question_no'];
$ext=".png";
$imgsub=$username.$question_no.$ext;

$actualpath = "uploads/".$imgsub;
		
$sql = "INSERT INTO appsubmissions (username,question_no,imgsub) VALUES ('$username','$question_no','$imgsub');";
		
if(mysqli_query($connect,$sql)){
file_put_contents($actualpath,base64_decode($image));
echo "Successfully Uploaded";
}
else
{echo "Error uploading image";}
mysqli_close($connect);
?>