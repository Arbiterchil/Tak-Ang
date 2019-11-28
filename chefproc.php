<?php
$db_name = "id11028263_restaurantfinal";
$mysql_user="id11028263_anvic1998";
$mysql_pass="anvic123";
$server_name="localhost";
$conn = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

	if(mysqli_connect_errno()){
		echo "FAILED TO CONNECT" .mysqli_connect_error();
		die();
	}

    $status = 1 ;

	$stmt = $conn->prepare("SELECT * FROM tbl_receipt WHERE status = '1' OR status = '2';");
	
	$stmt->execute();

	$stmt->bind_result($order_id,$date_ordered,$table_id,$status);
	
	$menus = array();
	
	while($stmt->fetch()){
		$temp = array();
		$temp['order_id']=$order_id;
		$temp['date_ordered']=$date_ordered;
		$temp['table_id']=$table_id;
		$temp['status']=$status;

		array_push($menus,$temp);
	}
	echo json_encode($menus);
	
	
?>