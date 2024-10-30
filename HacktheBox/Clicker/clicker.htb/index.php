<?php
session_start();
?>

<!doctype html>
<html lang="en" class="h-100">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Clicker - The Game</title>

<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
<style>
body {
  background-image: url('assets/background.png');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50% 50%;
}
</style>

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
    </style>

    
    <!-- Custom styles for this template -->
    <link href="/assets/cover.css" rel="stylesheet">
    <style>
      div.halfOpacity {
        background-color: rgba(0, 0, 0, 0.6);
      }
      h1 {
        text-shadow: text-shadow: 0px 0px 3px #1F0D08, 3px 3px 3px #1F0D08;
      }
      p {
        text-shadow: text-shadow: 0px 0px 3px #1F0D08, 3px 3px 3px #1F0D08;
      }
    </style>
  </head>
  <body class="d-flex h-100 text-center text-light bg-dark">
    
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
  <header class="mb-auto">
    <div>
      <h3 class="float-md-start mb-0">Home</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
      <?php
        if ($_SESSION["ROLE"] == "") {
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/info.php">Info</a>';
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/login.php">Login</a>';
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/register.php">Register</a>';
  	    }
  	    else {
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/profile.php">Profile</a>';
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/logout.php">Logout</a>';
        	echo '<a class="nav-link fw-bold py-1 px-0 active" href="/play.php">Play</a>';
          if ($_SESSION["ROLE"] == "Admin") {
            echo '<a class="nav-link fw-bold py-1 px-0 active" href="/admin.php">Administration</a>';
          } 	    	
  	    }
  	  ?>
      </nav>
    </div>
  <h5 class="float-md-start mb-0" style="color:green;" name="msg"><?php echo $_GET['msg']; ?></h5>
  <h5 class="float-md-start mb-0" style="color:red;" name="err"><?php echo $_GET['err']; ?></h5>
  </header>

  <main class="px-3">
    <?php 
      if ($_SESSION["ROLE"] == "") {
        echo '<h1 >The ultimate gaming experience</h1>';
        echo '<p class="lead">Join countless other players in playing the most amazing game ever made. How many clicks can you get?</p>';
      }
      else {
        echo '<h1 >Welcome, ' . $_SESSION["PLAYER"] . '</h1>';
        echo '<p class="lead">Will you become a top player?</p>';
      }
    ?>
  </main>
</div>
    
  </body>
</html>

