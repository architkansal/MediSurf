<?PHP 
    include_once("conn_medi_surf.php"); 
    mysql_select_db("medisurf")or die("cannot select DB");
    $response = array();
    if( isset($_POST['med_list']) )
    {   
        $med_list = array();
        // $med_list = $_POST['med_list'];
        array_push($med_list, "discorb");
        foreach ($med_list as $med_name) 
        {

            $query = "Select * from medicine where name = '$med_name' ";
            $result = @mysql_query($query , $conn);
            $res=@mysql_fetch_array($result);
            $gs = $res['generic_salt']; 
            $query = "Select * from medicine where generic_salt = '$gs' "; 
            $result = @mysql_query($query , $conn);
            $res=@mysql_fetch_array($result);
            $response[$med_name]=$res;
        }
        $response['success']=-1;
        echo json_encode($response);
        
    }
    else
    {
        $response['success']=0;
        $response['generic_salt']="no med_list given";
        echo json_encode($response);
    }

?>

<html>
     <form action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="post">
        Med Name: <input type="text" name="med_list"><br>
        <input type="submit">
    </form>
</html>

