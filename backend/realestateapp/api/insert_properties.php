<?php
	require('../utilities/Dbconnection.php');
	
	
	
	$dbconn = new Dbconnection();
	$con = $dbconn->con();
	
	
	if(isset($_POST['image'])) {
	$price = $_POST['price'];
	$bathrooms = $_POST['bathrooms'];
	$bedrooms = $_POST['bedrooms'];
	$area = $_POST['area'];
	$description = $_POST['description'];
	$address = $_POST['address'];
	$city = $_POST['city'];
	$owners_number = $_POST['owners_number'];
	$location_type = $_POST['location_type'];
	$surburb = $_POST['surburb'];
	$sell_rent = $_POST['sell_rent'];
	$houseType = $_POST['houseType'];
	
	
	

		
		$query = $con->query("INSERT INTO properties (price, bedrooms, bathrooms, area, property_description, address, city,
		owners_number, location_type, surburb, sell_rent, house_type) VALUES ('" . $price . "', '" . $bedrooms . "', '" . $bathrooms . "',
		'" . $area . "', '" . $description . "','" . $address . "', '" . $city . "','" . $owners_number . "','" . $location_type . "',
         '" . $surburb . "', '" . $sell_rent . "', '" . $houseType . "')");
		
		//$message = upload($image, $address, $owners_number);
		
		$response = array();
		if ($query->num_rows > 0) {
			
			$code = "success";
			$message = "success";
				array_push($response, array("code" => $code, "message" => $message));
				echo json_encode($response);
		} else {
			$code = "failed";     			
			
			array_push($response, array("code" => $code, "message" => "failed"));
			echo json_encode($response);
		
	}
	
	}
	$con->close();
	
	