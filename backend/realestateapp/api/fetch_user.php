<?php
	/*
	 * delete user query and use join operation to display user name and if student or coach
	 */
	
	require '../utilities/Dbconnection.php';
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	$userQuery = $con->query("SELECT username,email from users") ;
	$values = array();
	while($row =$userQuery->fetch_array()){
		
		array_push($values,
			array("username"=>$row['username'],
			"email"=>$row['email']));
		
	}
	             echo json_encode($values);
	
	
	