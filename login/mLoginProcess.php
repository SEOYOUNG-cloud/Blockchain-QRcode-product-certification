<?php
        session_start();
        $conn=mysqli_connect('localhost','manager','root','member');

        $id=$_POST['id'];
        $pwd=$_POST['pwd'];

        $sql="SELECT * FROM manufacturer where id='$id'";

?>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
        <title>PARM</title>
</head>
<body>
        <div>
                <?php
                        $result = $conn->query($sql);
                                if(mysqli_num_rows($result)==1) { //아이디가 있다면 비밀번호 검사
                                $row=mysqli_fetch_assoc($result);
                                if($row['pwd']==$pwd){ //비밀번호가 맞다면 세션 생성
                                        $_SESSION['id']=$id;
                ?>
                                        <script>
                                                location.href = "http://www.parm-block.ml/index.html";
                                        </script>
                <?php
                                } else {
                ?>
                                        <script>
                                                alert("아이디 혹은 비밀번호가 잘못되었습니다.");
                                                history.back();
                                        </script>
                <?php
                                }
                        } else {
                ?>
                                        <script>
                                                alert("아이디 혹은 비밀번호가 잘못되었습니다.");
                                                history.back();
                                        </script>
                <?php
                        }
                ?>
        </div>
</body>
</html>