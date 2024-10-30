<?php
session_start();
if (! $_SESSION["ROLE"]) {
  header('Location: /index.php');
  die;
}
?>

<html>
<head>
<link href="/assets/cover.css" rel="stylesheet">
<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
<style>
  body {
  margin:0px;
}
.center {
  margin: 40px;
}

.round{
  font-size: 12px;
  color: #4D5259;
  line-height: 1.5;
  font-weight: bold;
  padding: .5em 2em;
  background: #FFFFFF;
  border: 2px solid #4D5259;
  box-shadow: 4px 4px 0 0 #4D5259;
  border-radius: 100px;
  outline:0;
  transition: ease all .1s;
} 
.round:active {
  transform: translateY(4px) translateX(4px);
  box-shadow: 0px 0px 0 0 #4D5259;
}

.square {
  font-size: 10px;
  color: #4D5259;
  line-height: 1.5;
  font-weight: bold;
  padding: .5em 2em;
  background: #FFFFFF;
  border: 2px solid #4D5259;
  box-shadow: 4px 4px 0 0 #4D5259;
  outline:0;
}

.square:active {
  transform: translateY(4px) translateX(4px);
  box-shadow: 0px 0px 0 0 #4D5259;
}
.square:hover {
  background: #d3d3d3;
}
</style>
</head>
<title>Clicker - The Game</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<body class="h-100 text-light bg-dark">
    <script>
      money = <?php echo $_SESSION["CLICKS"]; ?>;
      update_level = <?php echo $_SESSION["LEVEL"]; ?>;
      money = parseInt(money);
      update_level = parseInt(update_level);
      upgrade_cost = 15 * (5 ** update_level);
      money_increment = upgrade_cost / 15;

      function addcomma(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
      }

      function saveAndClose() {
        window.location.replace("/save_game.php?clicks="+money+"&level="+update_level);
      }

      function clicked() {
        money += money_increment;
        document.getElementById("total").innerHTML = "Clicks: " + addcomma(money);
      }

      function upgrade() {
        if (money >= upgrade_cost) {
          money_increment += upgrade_cost / 15;
          money -= upgrade_cost;
          update_level += 1;
          upgrade_cost = upgrade_cost * 5;
          document.getElementById("upgrade").innerHTML = addcomma(update_level) + " - LevelUP Cost: " + addcomma(upgrade_cost);
        }
  

        document.getElementById("click").innerHTML = "Level: " + addcomma(update_level);
        document.getElementById("total").innerHTML = "Clicks: " + addcomma(money);
      }

    </script>
    <br><br>
    <center class='text-center'>
      <h1 id='total'>Clicks: 0</h1>
      <h5 id="click" style="font-family:trebuchet MS;">Level: 0</h5>
      <img class="round" src="assets/cursor.png" width="150" onclick='clicked()'></img>
      <br><br>
      <button id='upgrade' class="round" onclick='upgrade()' style="font-family:courier;">0 - LevelUP cost: 15</button><br><br>
      <button class="square" onclick="saveAndClose()">Save and close</button>
    </center>

    <script>
      document.addEventListener('DOMContentLoaded', function() {
        document.getElementById("upgrade").innerHTML = addcomma(update_level) + " - LevelUP Cost: " + addcomma(upgrade_cost);
        document.getElementById("click").innerHTML = "Level: " + addcomma(update_level);
        document.getElementById("total").innerHTML = "Clicks: " + addcomma(money);
      }, false);
    </script>
  </link>
</body>
</meta>

</html>
