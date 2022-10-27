<?php
if (isset($_REQUEST['usr']))
{
	$usr=$_REQUEST['usr'];
	$cnx =  mysqli_connect("localhost","root","JuaneFe-1414","dbProductos") or die("Ha sucedido un error inexperado en la conexion de la base de datos");
	$result = mysqli_query($cnx,"SELECT id FROM productos WHERE id = '$id'");
	if (mysqli_num_rows($result)>0)
	{
		mysqli_query($cnx,"UPDATE productos SET activo='no' WHERE id = '$id'");
	}
	else
	{
		echo "No existe el producto";
	}
	mysqli_close($cnx);
}
else
{
	echo "Debe llenar los campos";
}
?>