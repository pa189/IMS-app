<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$question_no=$_POST["question_no"];
$query="select * from appsubmissions where question_no='$question_no';";
$result=mysqli_query($connect,$query);

$temp_array=array();

while($row=mysqli_fetch_assoc($result)) {
$temp_array[]=$row;
}

header('Content-Type: application/json');
echo json_encode(array("users"=>$temp_array));
mysqli_close($connect);
?>