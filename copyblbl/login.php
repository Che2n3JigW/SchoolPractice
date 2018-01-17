<?php
$user_account = $_POST["user_account"];
$user_password = $_POST["user_password"];
$con=mysqli_connect("localhost","root","","blbl");
// 检测连接
if (mysqli_connect_errno())
{
    echo "连接失败: " . mysqli_connect_error();
}
mysqli_query($con,"set names utf8"); 
$result = mysqli_query($con,"SELECT * FROM blbl_user WHERE user_account='$user_account' and user_password ='$user_password'");

if (mysqli_num_rows($result) > 0) {
    // 输出数据
    echo "success";
} else {
    echo "0";
}
$con->close();
?>