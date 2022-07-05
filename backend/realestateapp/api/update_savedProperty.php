<?php
	require("../utilities/Dbconnection.php");
	
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	
	$action = $_POST['action'];
	$username = $_POST['username'];
	$ref = $_POST['reference'];
	
	if($action == "delete"){
		$queryDelete = $con->query("DELETE FROM acquisition WHERE username='$username' and reference_number='$ref'");
		$message = "deleted";
		checkResults($queryDelete, $message);
	}
	elseif ($action == "insert"){
		$queryUpdate = $con->query("INSERT INTO acquisition (username, reference_number, status)
 		values ('$username', '$ref', 'not approved')");
		$message = "updated";
		checkResults($queryUpdate, $message);
		
	}
	
	function checkResults($result, $message){
		
		$response = array();
		if($result){
			$code = "Success";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		}
		else{
			$code = "Failed";
			array_push($response, array("code"=>$code, "message"=>"Failed"));
			
			echo json_encode($response);
		}
	}
	
	$con->close();