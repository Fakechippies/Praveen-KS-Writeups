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

    <script>
  function validate() {
    var username = document.forms["registration_form"]["username"].value;
    var password = document.forms["registration_form"]["password"].value;
    if (username == "" || password == "") {
      alert("Username and Password can't be empty");
      return false;
    }
    else {
      return true; 
    }
}
</script>

    <!-- Custom styles for this template -->
    <link href="/assets/cover.css" rel="stylesheet">
  </head>
  <body class="d-flex h-100 text-center text-light bg-dark">
    
<div class="cover-container d-flex w-100 h-50 p-3 mx-auto flex-column">
  <header class="mb-auto">
    <div>
      <h3 class="float-md-start mb-0">Registration</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
        <a class="nav-link fw-bold py-1 px-0 active" aria-current="page" href="/index.php">Back to Home</a>
      </nav>
    </div>
    <h5 class="float-md-start mb-0" style="color:green;" name="msg"><?php echo $_GET['msg']; ?></h5>
    <h5 class="float-md-start mb-0" style="color:red;" name="err"><?php echo $_GET['err']; ?></h5>
  </header>

  <main class="px-3">
    <h1>Register</h1>
    <form name="registration_form" action="create_player.php" method="post" onsubmit="return validate()">
      <div class="form-group">
        <label for="inputUsername">Username</label>
        <input class="form-control" name='username' id="exampleInputUsername1" aria-describedby="usernameHelp" placeholder="Username">
      </div>
      <div class="form-group">
        <label for="inputPassword">Password</label>
        <input type="password" name='password' class="form-control" id="InputPassword" placeholder="Password">
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  </main>
</div>
    
  </body>
</html>

