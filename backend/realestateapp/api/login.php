<?php
	require("../utilities/Dbconnection.php");
	
	$dbconn = new Dbconnection();
	
	$con = $dbconn->con();
	
	$username = $_POST["Username"];
	$password = $_POST["Password"];
	$login_time = $_POST["Login"];
	
	$userQuery = $con->query("Select * from users where username ='$username' and password ='$password'");
	$queryAdmin = $con->query("SELECT * FROM admin WHERE user_id='$username' and Password='$password'");
	$response = array();
	
	
		if ($userQuery->num_rows > 0) {
			
			$queryLoginTimeCoach = $conn->query("INSERT INTO login_log (`user_id`, `login_time`)
				VALUES ('".$id."', '".$login_time."')");
			$code = "Login success";
			$message = "User";
			array_push($response, array("code" => $code, "message" => $message));
			
			echo json_encode($response);
		}elseif ($queryAdmin->num_rows > 0){
			$code = "Login success";
			$message = "Admin";
			array_push($response, array("code" => $code, "message" => $message));
			echo json_encode($response);
		} else {
			$code = "Login failed";
			$message = "Failed";
			array_push($response, array("code" => $code, "message" => $message));
			
			echo json_encode($response);
	
	}
	
	$con->close();
	?>