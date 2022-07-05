<?php
	require '../utilities/Dbconnection.php';
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	$regnum = $_POST['User'];
	$ref = $_POST['ref'];
	
	
			$updateSoccer = $con->query("UPDATE acquisition SET status = 'approved' WHERE username='$regnum' and reference_number='$ref'");
			
			checkResult($updateSoccer);
			
		

	
	function checkResult($result){
		$response = array();
		if ($result){
			$code = "successful";
			$message = "Successful update";
			array_push($response, array("code"=>$code,"message"=>$message));
			echo json_encode($response);
		}
		elseif (!$result){
			$code = "failed";
			$message = "Something went wrong with the update";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		}
	}
	
	$con->close();
