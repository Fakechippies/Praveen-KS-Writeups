<?php
session_start();
include_once("db_utils.php");

if ($_SESSION["ROLE"] != "Admin") {
  header('Location: /index.php');
  die;
}
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

    <!-- Custom styles for this template -->
    <link href="/assets/cover.css" rel="stylesheet">
  </head>
  <body class="d-flex h-100 text-center text-light bg-dark">
    
<div class="cover-container d-flex w-100 h-50 p-1 mx-auto flex-column">
  <header class="mb-auto">
    <div>
      <h3 class="float-md-start mb-0">Administration Portal</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
        <a class="nav-link fw-bold py-1 px-0 active" aria-current="page" href="/index.php">Back to Home</a>
      </nav>
    </div>
    <h5 class="float-md-start mb-0" style="color:green;" name="msg"><?php echo $_GET['msg']; ?></h5>
    <h5 class="float-md-start mb-0" style="color:red;" name="err"><?php echo $_GET['err']; ?></h5>
  </header>

  <main class="px-3">
    <?php
      $threshold = 1000000;
      $top_players = get_top_players($threshold);
      if (count($top_players) > 0) {
        echo '<h3>Top players</h3>';
        echo '<table class="table table-dark">';
        echo '<thead>';
        echo '  <tr>';
        echo '    <th scope="col">Nickname</th>';
        echo '    <th scope="col">Clicks</th>';
        echo '    <th scope="col">Level</th>';
        echo '  </tr>';
        echo '</thread>';
        echo '<tbody>';
        foreach ($top_players as $player) {
          echo '  <tr>';
          echo '    <th scope="row">' . $player["nickname"] . '</th>';
          echo '    <td>' . $player["clicks"] . '</td>'; 
          echo '    <td>' . $player["level"] . '</td>';
          echo '  </tr>';
        }
        echo '</tbody>';
        echo '</table>';

        echo '<form name="export_form" action="export.php" method="post">';
        echo '<input type="hidden" name="threshold" value="' . $threshold . '">';
        echo '<button type="submit" class="btn btn-primary">Export</button> ';
        echo '<select style="text-align-last:center" class="form-select form-select-sm" aria-label=".form-select-sm example" name="extension">';
        echo '<option value="txt">txt</option>';
        echo '<option value="json">json</option>';
        echo '<option value="html">html</option>';
        echo '</select>';
        echo '</form>';
      }
      else {
        echo '<h3> No players to display </h3>';
      }
    ?>
  </main>
</div>
    
  </body>
</html>

