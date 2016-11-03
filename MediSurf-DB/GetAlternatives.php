<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $response = array();
    if( isset($_POST['med_name'] )
    {
        $med_name = $_POST['med_name'];
    
        $query = "Select * from medicine where name = '$med_name' "; 
    
        $result = @mysql_query($query , $conn);
        $res=@mysql_fetch_array($result);
        
        if($result==NULL || $res==NULL)
        {
            $response['success']=-1;
            $response['generic_salt']="no med_name available in DB";
            echo json_encode($response);
        }
        else
        {
            $gs = $res['generic_salt'];
            $des = $res['description'];
            
            $query = "Select * from medicine where generic_salt = '$gs' "; 
    
            $result = @mysql_query($query , $conn);
            $res=@mysql_fetch_array($result);

            $response['success']=1;
            $response['results']=$res;
            echo json_encode($response);
        }
    }
    else
    {
        $response['success']=0;
        $response['generic_salt']="no med_name given";
        echo json_encode($response);
    }

?>
