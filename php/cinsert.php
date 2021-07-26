<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon2.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $id=$_POST['id'];
        $pwd=$_POST['pwd'];
        $name=$_POST['name'];
        $tel=$_POST['tel'];
        $email=$_POST['email'];
        $addr=$_POST['addr'];

      #  if(empty($id)){
      #      $errMSG = "id을 입력하세요.";
      #  }
      #  else if(empty($pwd)){
      #      $errMSG = "pwdd를 입력하세요.";
      #  }

        if(!isset($errMSG)) // 이름과 나라 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO customer(id, pwd, name, tel, email, addr) VALUES(:id, :pwd, :name, :tel, :email, :addr)');
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':pwd', $pwd);
                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':tel', $tel);
                $stmt->bindParam(':email', $email);
                $stmt->bindParam(':addr', $addr);


                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                ID: <input type = "text" name = "id" />
                Password: <input type = "text" name = "pwd" />
                Name: <input type = "text" name = "name" />
                Telenumber: <input type = "text" name = "tel" />
                Email: <input type = "text" name = "email" />
                Address: <input type = "text" name = "addr" />

                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>
