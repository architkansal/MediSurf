<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $response = array();
    if( isset($_POST['med_name'] ))
    {
        $med_name = $_POST['med_name'];
    
        $query = "Select * from medicine where name = '$med_name' "; 
    
        $result = @mysql_query($query , $conn);
        $res=@mysql_fetch_array($result);
        
        if($result==NULL || $res==NULL)
        {
            $response['success']=-1;
            $response['brand_name']="no med_name available in DB";
            echo json_encode($response);
        }
        else
        {
            $response['success']=1;
            $response['brand_name'] = $res['brand_name'];
            $response['description'] = $res['description'];
            
            // $query = "Select * from medicine where generic_salt = '$gs' ";
    
            // $result = @mysql_query($query , $conn);
            // // $res=@mysql_fetch_array($result);
            //  // $res = $result->fetch_all(MYSQLI_NUM); 

            // $yourArray = array(); // make a new array to hold all your data

            // $index = 0;
            // while($row = mysql_fetch_assoc($result))
            // { // loop to store the data in an associative array.
            //      $yourArray[$index] = $row;
            //      $index++;
            // }
            // // print_r($yourArray);
            echo json_encode($response);    
        }
    }
    else
    {
        $response['success']=0;
        $response['brand_name']="no med_name available in DB";
        echo json_encode($response);
    }

?>

<html>
     <form action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="post">
        Med Name: <input type="text" name="med_name"><br>
        <input type="submit">
    </form>
</html>
