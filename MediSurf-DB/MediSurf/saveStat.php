<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $med_list = array();
    $response  = array();
    $originals = array();
    if( True )
    {   
       $org_price = $_POST['org_price'];
       $altered_price = $_POST['altered_price'];

        $query = "insert into price values ('$org_price', '$altered_price')"; 
        $result = @mysql_query($query , $conn);
        $k=0;
        $i=$_POST['num'];
        for ($j=0;$j<$i;$j++) 
        {
            $pres = $_POST['originals'.($j)];
            $val = $_POST['finals'.($j)];
            $query = "insert into alternatives values ('$pres', '$val')"; 
            $result = @mysql_query($query , $conn);    
        }
       $data['success']=1;
       echo json_encode($data);
    }
    else
    {
        $response['success']=0;
        $response['Result']="Data not found";
        echo json_encode($response);
    }

?>




