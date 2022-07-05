<?php
	require("../utilities/Dbconnection.php");
	
	$dbconn = new Dbconnection();
	
	$con = $dbconn->con();
	
	$username =$_POST['username'];
	
	
	$query = $con->query("Select reference_number from acquisition where username='$username'");
	
	$ref;
	while($row = mysqli_fetch_array($query)) {
		$ref = $row['reference_number'];
	}
	
	$propQuery = $con->query("Select * from properties where reference_number='$ref'");
	
	$response = array();
	while ($row = mysqli_fetch_array($propQuery)){
		array_push($response, array(
			"reference_number"=>$row['reference_number'], "price"=>$row['price'],
			"bedrooms"=>$row['bedrooms'], "bathrooms"=>$row['bathrooms'],
			"area"=>$row['area'], "description"=>$row['property_description'],
			"address"=>$row['address'], "city"=>$row['city'], "owners_number"=>$row['owners_number'],
			"location_type"=>$row['location_type'], "surburb"=>$row['surburb'],
			"sell_rent"=>$row['sell_rent'], "houseType"=>$row['house_type']));
	}
	
	echo json_encode($response);
	
	$con->close();