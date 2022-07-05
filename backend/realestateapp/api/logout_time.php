<?php
	require '../utilities/Dbconnection.php.php';
	
	$dbconn = new Dbconnection();
	$con = $dbconn->con();
	
	$logout_time = $_POST['Logout'];
	$regnum = $_POST['Username'];
	
	
	$results = $con->query("UPDATE login_log SET logout_time ='".$logout_time."' where user_id = '".$regnum."' ");
	$response = array();
	
	if($results){
		
		$code = "successful";
		$message = "successful";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
	}
	else{
		
		$code = "failed";
		$message = "Something went wrong";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
	}
	
	$con->close();