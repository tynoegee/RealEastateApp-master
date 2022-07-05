<?php
	require ("../utilities/Dbconnection.php");
	
	$dbconn = new Dbconnection();
	
	$con = $dbconn->con();
	
	$results = $con->query("Select * from acquisition where status = 'not approved'");
	
	$response =array();
	
	while ($row = $results->fetch_array()){
		
		array_push($response, array(
			"username"=>$row['username'],
			"ref"=>$row['reference_number'],
			"status"=>$row['status']
		));
		
	}
	
	echo json_encode($response);
	
	$con->close();