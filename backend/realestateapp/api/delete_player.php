<?php
	require '../utilities/Dbconnection.php';
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	$regnum = $_POST['Username'];
			
			
			$deleteStudentQuery=$con->query("SELECT * FROM users WHERE username='".$regnum."'");
			
		if ($deleteStudentQuery->num_rows > 0){
				$studentQuery = $con->query("DELETE FROM users WHERE username='".$regnum."'");
				$Query = $con->query("DELETE FROM user_details WHERE username='".$regnum."'");
				checkResult($studentQuery);
			}
			
	
	function checkResult($result){
		$response = array();
		if ($result){
			$code = "successful";
			$message = "Successful delete";
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
