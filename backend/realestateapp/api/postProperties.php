<?php
	
	require("../utilities/Dbconnection.php");
	
	$Dbconn = new Dbconnection();
	$con = $Dbconn->con();
	
	$query = $con->query("SELECT * FROM properties");
	
	$respond = array();
	
	
	while ($row = mysqli_fetch_array($query)){
		
		array_push($respond, array(
			"reference_number"=>$row['reference_number'], "price"=>$row['price'],
			"bedrooms"=>$row['bedrooms'], "bathrooms"=>$row['bathrooms'],
			"area"=>$row['area'], "description"=>$row['property_description'],
			"address"=>$row['address'], "city"=>$row['city'], "owners_number"=>$row['owners_number'],
			"location_type"=>$row['location_type'], "surburb"=>$row['surburb'],
			"sell_rent"=>$row['sell_rent'], "houseType"=>$row['house_type']));
	}
	
	
	
	
	
	echo json_encode($respond);
	
	$con->close();