<?php

$name = $_POST['name'];
$lastname = $_POST['lastname'];
$email = $_POST['email'];
$username = $_POST['username'];
$passwd = $_POST['passwd'];

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
    $res = $conexion->query("INSERT INTO employees (name, lastname, email, username, passwd, image) VALUES ('$name', '$lastname', '$email', '$username', '$passwd', 'noprofile.jpg')");
    
    if($res) {
        echo "OK";
    } else {
        echo "Error al insertar";
    }
?>
