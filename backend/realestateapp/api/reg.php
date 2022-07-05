<?php
	require ("../utilities/Dbconnection.php");
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	$username = $_POST['Username'];
	$password = $_POST['Password'];
	
	
	$results = $con->query("INSERT INTO users (username, password) VALUES ('$username', '$password')");
	
	$response = array();
	
	if($results){
		$code = "successful";
		$message = "";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
		
	}
	else{
		$code = "failed";
		$message = "failed to update values";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
	}
	$con->close();