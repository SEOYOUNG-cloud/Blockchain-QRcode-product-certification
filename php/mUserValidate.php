<?php

    $con = mysqli_connect('localhost', 'manager', 'root', 'member');



     $userID = $_POST["id"];



     $statement = mysqli_prepare($con, "SELECT id FROM manufacturer WHERE id = ?");



     mysqli_stmt_bind_param($statement, "s", $id);

     mysqli_stmt_execute($statement);

     mysqli_stmt_store_result($statement);//결과를 클라이언트에 저장함

     mysqli_stmt_bind_result($statement, $id);//결과를 $userID에 바인딩함



     $response = array();

     $response["success"] = true;



     while(mysqli_stmt_fetch($statement)){

       $response["success"] = false;//회원가입불가를 나타냄

       $response["id"] = $id;

     }



     echo json_encode($response);

?>