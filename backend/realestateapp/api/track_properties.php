<?php
	
	
	require("../utilities/Dbconnection.php");
	
	
	
	$dbcon = new Dbconnection();
	$con = $dbcon->con();
	
	
	$username = $_POST['username'];
	
	
	$query = $con->query("SELECT reference_number, status  from acquisition where  username='$username'");
	
	$ref;
	$status;
	while($row = mysqli_fetch_array($query)) {
		$ref = $row['reference_number'];
		$status = $row['status'];
	}
	$propQuery = $con->query("Select address, house_type, sell_rent from properties where reference_number='$ref'");
	
	$response = array();
	while ($prop = mysqli_fetch_array($propQuery)){
		
		              array_push($response, array("reference"=>$ref,"status"=>$status, "address"=>$prop['address'], "house_type"=>$prop['house_type'],
			              "sell_rent"=>$prop['sell_rent']));
	}
	
	
	echo json_encode($response);
	$con->close();