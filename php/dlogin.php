<?php

   $con = mysqli_connect("localhost", "manager", "root", "member");
   mysqli_query($con, 'SET NAMES utf8');

   $id = $_POST["id"];
   $pwd = $_POST["pwd"];

   $statement = mysqli_prepare($con, "SELECT * FROM distribution WHERE id = ? AND pwd = ?");
   mysqli_stmt_bind_param($statement, "ss", $id, $pwd);
   mysqli_stmt_execute($statement);

   mysqli_stmt_store_result($statement);
   mysqli_stmt_bind_result($statement, $B_name, $Admin_name, $addr);

   $response = array();
   $response["success"] = false;

   while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
       # $response["id"] = $id;
       # $response["pwd"] = $pwd;
        $response["B_name"] = $B_name;
        $response["Admin_name"] = $Admin_name;
       # $response["tel"] = $tel;
       # $response["email"] = $email;
        $response["addr"] = $addr;
   }

   echo json_encode($response);


?>
