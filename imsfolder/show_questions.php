<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);


$query="select * from appquestions left join appsubmissions on appquestions.s_no=appsubmissions.question_no where appquestions.answer is not null or appquestions.imgans is not null;";
$result=mysqli_query($connect,$query);

$temp_array=array();

while($row=mysqli_fetch_assoc($result)) {
$temp_array[]=$row;
}

header('Content-Type: application/json');
echo json_encode(array("users"=>$temp_array));
mysqli_close($connect);
?>