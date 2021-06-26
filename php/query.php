<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');


$id = isset($_POST['id']) ? $_POST['id'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if($id != ""){

     # POST -> execute SELECT
    
     $sql = "select * from product where id = '$id'";
     $stmt = $con->prepare($sql);
     $stmt->execute();


     if($stmt->rowCount() == 0){
     
 
          echo "False";
     }
     else{
          
          echo "True \n";
          echo "ID : ";
          echo $id;

          $data = array();

          while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

                extract($row);

                array_push($data, array('id'=>$row["id"]));

          }

          if(!$android){
                echo "<pre>";
                print_r($data);
                echo '</pre>';
          }
          else{ #change json format

                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("user1"=>$data),
           JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
           #     echo $json;

          }
     }
}

else{
      echo "Enter ID ";
}

?>


<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if(!$android){
?>


<html>
   <body>

         <form action="<?php $_PHP_SELF ?>" method="POST">
               id: <input type = "text" name="id" />
               <input type = "submit"/>
         </form>

   </body>
</html>
<?php
}

?>
