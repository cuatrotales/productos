<?php
if (isset($_REQUEST['id']) && isset($_REQUEST['nombre']) && isset($_REQUEST['valor'])) {
	$id = $_REQUEST['id'];
	$nombre = $_REQUEST['nombre'];
	$valor = $_REQUEST['valor'];
	$cnx =  mysqli_connect("localhost", "root", "", "dbProductos") or die("Ha sucedido un error inexperado en la conexion de la base de datos");
	$result = mysqli_query($cnx, "SELECT id FROM productos WHERE id = '$id'");
	if (mysqli_num_rows($result) == 0) {
		mysqli_query($cnx, "INSERT INTO productos (id,nombre,valor) VALUES ('$id','$nombre','$valor')");
	} else {
		echo "¿Dos veces lo mismo? NUUUUU...";
	}
	mysqli_close($cnx);
} else {
	echo "Si no llena los campos estamos paila";
}
?>