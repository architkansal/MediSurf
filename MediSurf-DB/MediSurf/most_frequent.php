<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $response = array();
    
        $query = "Select original , alternative , count(*) as cnt from alternatives group by (original) order by count(*) desc"; 
    
        $result = @mysql_query($query , $conn);
        // $res=@mysql_fetch_array($result);
       	$yourArray = array(); // make a new array to hold all your data

        $index = 0;
        while($row = mysql_fetch_assoc($result))
        { // loop to store the data in an associative array.
             $yourArray[$index] = $row;
             $index++;
        }
        // print_r($yourArray);

        $response['success']=2;
        $response['results']=$yourArray;
        echo json_encode($response);
?>

