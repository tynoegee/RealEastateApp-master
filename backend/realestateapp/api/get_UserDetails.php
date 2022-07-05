<?php
	require("../utilities/Dbconnection.php");
	
	$Dbconn = new Dbconnection();
	$con = $Dbconn->con();
	
	$username = $_POST['username'];
	
	$query = $con->query("SELECT * FROM user_details where username='$username'");
	
	$respond = array();
	
	
	while ($row = mysqli_fetch_array($query)){
		
		array_push($respond, array(
			"fname"=>$row['fname'], "lname"=>$row['lname'],
			"email"=>$row['email'], "services"=>$row['services'],
			"phone"=>$row['phone_number'], "whatsapp"=>$row['whatsapp_number'],
			"username"=>$row['username'], "city"=>$row['city'], "company"=>$row['company'],"address"=>$row['address']));
	}
		
	
	echo json_encode($respond);
	
	$con->close();