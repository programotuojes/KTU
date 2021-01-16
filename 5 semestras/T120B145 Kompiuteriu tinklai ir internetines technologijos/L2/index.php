<?php
$server="localhost";
$user="stud";
$password="stud";
$dbname="stud";
$table="gustasklevinskas";

$saved_successfully = false;

$conn = new mysqli($server, $user, $password, $dbname);
if ($conn->connect_error) die("Can't connect " . $conn->connect_error);

if ($_POST != null){
    $name = $_POST['name'];
    $email = $_POST['email'];
    $recipient = $_POST['recipient'];
    $date = date("Y-m-d H:i:s");
    $ip = $_SERVER['REMOTE_ADDR'];
    $message = $_POST['message'];

   $sql = "INSERT INTO $table (vardas, epastas, kam, data, ip, zinute) 
          VALUES ('$name', '$email', '$recipient', '$date', '$ip', '$message')";

   if (!$result = $conn->query($sql)) die("Can't write: " . $conn->error);
   
   $saved_successfully = true;
}
?>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</head>

<body class="container">
    <h2>Message system</h2>
    <table class="table table-striped table-hover">
        <tr>
            <th>Nr</th>
            <th>Name</th>
            <th>Sender email</th>
            <th>Recipient</th>
            <th>Date (IP)</th>
            <th>Message</th>
        </tr>
        <?php
            $sql = "SELECT * FROM $table";
            if (!$result = $conn->query($sql)) die("Can't read: " . $conn->error);

            while($row = $result->fetch_assoc()) {
                echo "<tr>";
                echo "<td>" . $row['id'] . "</td>";
                echo "<td>" . $row['vardas'] . "</td>";
                echo "<td>" . $row['epastas'] . "</td>";
                echo "<td>" . $row['kam'] . "</td>";
                echo "<td>" . $row['data'] . " (" .  $row['ip'] . ")" . "</td>";
                echo "<td>" . $row['zinute'] . "</td>";
                echo "</tr>";
            }
        ?>
    </table>

    <hr class="solid">
    <h2>Send a new message</h2>

    <form method="post">
        <div class="row">
            <div class="form-group col-md-4">
                <label for="name_input">Name</label>
                <input name="name" type="text" class="form-control" id="name_input" placeholder="Name">
            </div>
            <div class="form-group col-md-4">
                <label for="email_input">Sender email</label>
                <input name="email" type="email" class="form-control" id="email_input" placeholder="Email">
            </div>
            <div class="form-group col-md-4">
                <label for="receiver_input">Recipient</label>
                <input name="recipient" type="text" class="form-control" id="recipient_input" placeholder="Recipient">
            </div>
        </div>
        
        <div class="form-group">
            <label for="message_input">Message</label>
            <textarea name="message" class="form-control" rows="3" id="message_input" placeholder="Message"></textarea>
        </div>
        
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <?php
    if ($saved_successfully) {
        echo "<div class=\"alert alert-success\" role=\"alert\">
            Successfully saved!
        </div>";
    }
    ?>
</body>
