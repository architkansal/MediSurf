<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $med_list = array();
    $response  = array();
    if( isset($_POST['total_med']) )
    {   
        
        $m = $_POST['total_med'];
        $n = ($m);
        for ($i=0; $i <$n ; $i++) 
        { 
            $med = $_POST['med_name'.($i)];
            $query = "Select * from medicine where name = '$med' "; 
    
            $result = @mysql_query($query , $conn);
            $res=@mysql_fetch_array($result);
            
            if($result==NULL || $res==NULL)
            {
                $response[$med] = "Not Available in DB";
            }
            else
            {
                $gs = $res['generic_salt'];
                $query = "Select * from medicine where generic_salt = '$gs' ";
        
                $result = @mysql_query($query , $conn);
                $yourArray = array(); // make a new array to hold all your data
                $index = 0;
                while($row = mysql_fetch_assoc($result))
                { // loop to store the data in an associative array.
                     $yourArray[$index] = $row;
                     $index++;
                }
                $response[$med] = $yourArray;
            }
        }
        $response['success']=1;
        echo json_encode($response);       
    }
    else
    {
        $response['success']=0;
        $response['generic_salt']="no med_list given";
        echo json_encode($response);
    }

?>
