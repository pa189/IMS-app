<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);
$username=$_POST["username"];
$query="select * from appquestions, appsubmissions where appquestions.s_no=appsubmissions.question_no and appsubmissions.username like '$username';";
$result=mysqli_query($connect,$query);

$temp_array=array();

while($row=mysqli_fetch_assoc($result)) {
$temp_array[]=$row;
}

header('Content-Type: application/json');
echo json_encode(array("users"=>$temp_array));
mysqli_close($connect);
?>