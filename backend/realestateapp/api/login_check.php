<?php
	require("../utilities/Dbconnection.php");
	
	$id = $_POST["Username"];
	$pass = $_POST["Password"];
	
	$sc =new Dbconnection();
	
	$conn = $sc->con();
	
	
	
	$queryStudent = "SELECT * FROM users WHERE username='".$id."' and password='".$pass."'";
	$queryAdmin = "SELECT * FROM admin WHERE user_id='".$id."' and Password='".$pass."'";

	$resultStudent = mysqli_query($conn, $queryStudent);
	$resultAdmin = mysqli_query($conn, $queryAdmin);
	$response = array();
	
	 if(mysqli_num_rows($resultStudent) > 0){
		
		$code = "Login success";
		array_push($response, array("code"=>$code,"message"=>"User"));
		
		echo json_encode($response);
		
		
	}else if(mysqli_num_rows($resultAdmin) > 0){
		
		$code = "Login success";
		$message ="Admin";
		array_push($response, array("code"=>$code,"message"=>$message));
		echo json_encode($response);
	}
	else{
		
		$code = "Login failed";
		$message = "Please Register to use this service";
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
	}
	
	mysqli_close($conn);

?>