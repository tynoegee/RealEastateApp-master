<?php
	
	require('../utilities/DbConnector.php');
	
	$dbconn = new DbConnector();
	$con = $dbconn->con();
	
	$log = $con->query("SELECT * FROM login_log");
	$values = array();
	while ($row = mysqli_fetch_array($log)) {
		
		array_push($values, array(
				"username" => $row['user_id'],
				"login_time" => $row['login_time'],
				"logout_time" => $row['logout_time']
			)
		);
	}
	echo json_encode($values);
	$con->close();