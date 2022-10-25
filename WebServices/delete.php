<?php
if (isset($_REQUEST['id']))
{
	$id=$_REQUEST['id'];
	$cnx =  mysqli_connect("localhost","root","","dbProductos") or die("Ha sucedido un error inexperado en la conexion de la base de datos");
	$result = mysqli_query($cnx,"select usr from usuarios where usr = '$id'");
	if (mysqli_num_rows($result)>0)
	{
		mysqli_query($cnx,"DELETE FROM productos WHERE id = '$id'");
	}
	else
	{
		echo "¿Seguro que es ese?";
	}
	mysqli_close($cnx);
}
else
{
	echo "La unica forma de eliminar es por el ID";
}
?>
