<?php


$user_account = $_POST["user_account"];
$user_password = $_POST["user_password"];

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "blbl";

// 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 

$sql = "INSERT INTO blbl_user(user_account,user_password) VALUES ('$user_account', '$user_password');";
mysqli_query($conn,"set names utf8"); 

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();
?>