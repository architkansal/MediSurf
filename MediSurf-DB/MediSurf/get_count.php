<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $response = array();
    
        $query = "Select sum(original) as org, sum(altered) as altrd from price "; 
    
        $result = @mysql_query($query , $conn);
        $res=@mysql_fetch_array($result);
        $response['success']=1;
        $response['count']=$res['org']-$res['altrd'];
        echo json_encode($response);
?>

