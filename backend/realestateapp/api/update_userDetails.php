<?php
	require ("../utilities/Dbconnection.php");
	
	$Dbconn = new Dbconnection();
	
	$con = $Dbconn->con();
	
	$fname = $_POST['fname'];
	$lname = $_POST['lname'];
	$email = $_POST['email'];
	$services = $_POST['services'];
	$phone_number =$_POST['phone'];
	$whatsapp_number = $_POST['whatsapp'];
	$username = $_POST['username'];
	$company = $_POST['company'];
	$address = $_POST['address'];
	$city = $_POST['city'];
	
	$query = $con->query("INSERT INTO user_details (fname, lname, email, services, phone_number, whatsapp_number, username, company, address, city) VALUES ('$fname', '$lname', '$email', '$services', '$phone_number', '$whatsapp_number', '$username', '$company', '$address', '$city')");
	
	$response = array();
	
	if($query->num_rows > 0){
		
		$code = "success";
		$message ="success";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
	}
	else{
		
		$code = "failed";
		$message ="failed";
		
		array_push($response, array("code"=>$code, "message"=>$message));
		echo json_encode($response);
		
	}
	$con->close();
	