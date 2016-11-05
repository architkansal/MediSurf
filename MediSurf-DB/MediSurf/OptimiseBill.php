<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $med_list = array();
    $response  = array();
    $originals = array();
    if( isset($_POST['total_med']) )
    {   
       
        $m = $_POST['total_med'];
        $n = ($m);
        $response['num'] = $n;
        $k=0;
        for ($i=0; $i <$n ; $i++) 
        { 
            $med = $_POST['med_name'.($i)];
            $query = "Select * from medicine where name = '$med' "; 
            
            $result = @mysql_query($query , $conn);
            $res=@mysql_fetch_array($result);
            
            if($result==NULL || $res==NULL)
            {
                $response[$i] = "Not Available in DB";
            }
            else
            {
                $gs = $res['generic_salt'];
                $query = "Select * from medicine where generic_salt = '$gs' order by price asc";
                $prc = $res['price'];
                $result = @mysql_query($query , $conn);
                $yourArray = array(); // make a new array to hold all your data
                $index = 0;
                while($row = mysql_fetch_assoc($result))
                { // loop to store the data in an associative array.
                     $yourArray[$index] = $row;
                     $index++;
                }
                $response[$i] = $yourArray;
                $originals[$k]= $med;
                $k++;    
                $originals[$k]= $prc;
                $k++;
            }
        }
        $response['success']=1;
        $response['originals'] = $originals; 
        echo json_encode($response);       
    }
    else
    {
        $response['success']=0;
        $response['generic_salt']="no med_list given";
        echo json_encode($response);
    }

?>




