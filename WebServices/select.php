<?php
	//$cnx =  mysqli_connect("localhost","root","","bdusuarios");
	if (isset($_REQUEST['id']))
	{
		$id=$_REQUEST['id'];
		//Conexion a la base de datos con mysqli
		//en vez de localhost se pone el servidor de la nube o el ip servidor
		//$cnx = new PDO("mysql:host=localhost;dbname=serviciosandroidphp","root","");
		$cnx =  mysqli_connect("localhost","root","","dbProductos");
		//Ejecutar una sentencia SELECT y recibir una respuesta
		$res=$cnx->query("SELECT * FROM productos WHERE id = '$id'");
		//si existe el usuario la variable res queda en 1 y sino en 0
		//En este arreglo se guardará la informacion para pasarla a JSON
		$json = array();
		foreach ($res as $row)
		{
			$json['datos'][]=$row;
		}
		//pasar los datos del array a JSON con informacion o vacío
		echo json_encode($json);
	}
	else
	{
		echo "El id es obligatorio.";
	}
 ?>