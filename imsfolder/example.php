<?php
$connect = mysqli_connect($myServer,$myUser,$myPass,$myDB);

$query="select * from appusers;";
$result=mysqli_query($connect,$query);
$numRows = mysqli_num_rows($result); 
echo "<h1>" . $numRows . " Row" . ($numRows == 1 ? "" : "s") . " Returned </h1>"; 


while($row = mysqli_fetch_array($result))
{
  echo "<li>" . $row["username"] . $row["pass"] . $row["name"] . "</li>";
}

mysqli_close($connect);
?>
