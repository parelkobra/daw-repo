<?php

$username_login = $_POST['username_login'];
$passwd_login = $_POST['passwd_login'];

$host = "localhost";
$user = "root";
$pw = "";
$db = "employees";

$conexion = new mysqli($host, $user, $pw, $db);

#Comprobar la conexión
if ($conexion->connect_error) {
    printf("Conexión fallida: %s", $conexion->connect_error);
    exit();
}

$sql = "SELECT * FROM employees"
        . " WHERE username = $username_login "
        . "AND passwd = $passwd_login";

$res = $conexion->query($sql);

if($res) {
    if ($res->num_rows > 0) {
        echo "<table>";
            echo "<tr>";
                echo "<th>id</th>";
                echo "<th>name</th>";
                echo "<th>lastname</th>";
                echo "<th>email</th>";
                echo "<th>username</th>";
            echo "</tr>";
        while($row = $res->fetch_array()){
            echo "<tr>";
                echo "<td>" . $row['id'] . "</td>";
                echo "<td>" . $row['name'] . "</td>";
                echo "<td>" . $row['lastname'] . "</td>";
                echo "<td>" . $row['email'] . "</td>";
                echo "<td>" . $row['username'] . "</td>";
            echo "</tr>";
        }
        echo "</table>";
        
        $res->free();
    } else {
        echo "error no se han encontrado resultados en la query";
    }
} else {
    echo "error no se pudo hacer el select";
}


?>