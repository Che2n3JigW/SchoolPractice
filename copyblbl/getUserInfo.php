<?php
$user_account = $_POST["user_account"];
$json = '';
$data = array();
class User 
{
public $user_account;
public $user_password;
public $user_nick;
public $user_head;
}

$con=mysqli_connect("localhost","root","","blbl");
// 检测连接
if (mysqli_connect_errno())
{
    echo "连接失败: " . mysqli_connect_error();
}
mysqli_query($con,"set names utf8"); 

$result = mysqli_query($con,"SELECT * FROM blbl_user WHERE user_account='$user_account'");

if($result){
	//echo "查询成功";
	while ($row = mysqli_fetch_array($result,MYSQL_ASSOC)){
		$user = new User();
		$user->user_account = $row["user_account"];
		$user->user_password = $row["user_password"];
		$user->user_nick = $row["user_nick"];
		$user->user_head = $row["user_head"];
		$data[]=$user;
	}
	$json = json_encode($data);//把数据转换为JSON数据.
	echo "{".'"user"'.":".$json."}";
}else{
echo "查询失败";
}
$con->close();
?>	