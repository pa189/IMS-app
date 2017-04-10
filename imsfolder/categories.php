<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);


$query="select distinct tag from appquestions;";
$result=mysqli_query($connect,$query);

$temp_array=array();

while($row=mysqli_fetch_assoc($result)) {
$temp_array[]=$row;
}

header('Content-Type: application/json');
echo json_encode($temp_array);
mysqli_close($connect);
?>