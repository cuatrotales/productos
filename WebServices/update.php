<?php
if (isset($_REQUEST['id']) && isset($_REQUEST['nombre']) && isset($_REQUEST['valor'])) {
	$id = $_REQUEST['id'];
	$nombre = $_REQUEST['nombre'];
	$valor = $_REQUEST['valor'];
	$cnx =  mysqli_connect("localhost", "root", "", "dbProductos") or die("Ha sucedido un error inexperado en la conexion de la base de datos");
	$result = mysqli_query($cnx, "SELECT id FROM productos WHERE id = '$id'");
	if (mysqli_num_rows($result) > 0) {
		mysqli_query($cnx, "UPDATE productos SET nombre='$nombre',valor='$valor' WHERE id = '$id'");
	} else {
		echo "El producto no existe...";
	}
	mysqli_close($cnx);
} else {
	echo "Debe llenar los campos";
}
?>