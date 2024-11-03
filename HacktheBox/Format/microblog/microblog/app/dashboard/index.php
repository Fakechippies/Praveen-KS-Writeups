<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();
if(is_null($_SESSION['username'])) {
    header("Location: /login");
    exit;
}

if (isset($_SESSION['username']) && isset($_POST['new-blog-name'])) {
    if(!preg_match('/^[a-z]+$/', $_POST['new-blog-name']) || strlen($_POST['new-blog-name']) > 50) {
        print_r("Invalid blog name");
        header("Location: /dashboard?message=Invalid blog name&status=fail");
        exit();
    }
    addSite($_POST['new-blog-name']);
}

function getFirstName() {
    if(isset($_SESSION['username'])) {
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
        $firstName = $redis->HGET($_SESSION['username'], "first-name");
        return "\"" . ucfirst(strval($firstName)) . "\"";
    }
}

function isPro() {
    if(isset($_SESSION['username'])) {
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
        $pro = $redis->HGET($_SESSION['username'], "pro");
        return strval($pro);
    }
    return "false";
}

function getSites() {
    if(isset($_SESSION['username'])) {
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
        $sites = $redis->LRANGE($_SESSION['username'] . ":sites", 0, -1);
        return $sites;
    }
    else {
        return "Sites not retrieved, not authenticated!";
    }
}

function generateRandomString($length) {
    $characters = 'abcdefghijklmnopqrstuvwxyz';
    $randomString = '';

    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, strlen($characters) - 1)];
    }

    return $randomString;
}

function addSite($site_name) {
    if(isset($_SESSION['username'])) {
        //check if site already exists
        $scan = glob('/var/www/microblog/*', GLOB_ONLYDIR);
        $taken_sites = array();
        foreach($scan as $site) {
            array_push($taken_sites, substr($site, strrpos($site, '/') + 1));
        }
        if(in_array($site_name, $taken_sites)) {
            header("Location: /dashboard?message=Sorry, that site has already been taken&status=fail");
            exit;
        }
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
	$redis->LPUSH($_SESSION['username'] . ":sites", $site_name);
        $tmp_dir = "/tmp/" . generateRandomString(7);
        system("mkdir -m 0700 " . $tmp_dir);
        system("cp -r /var/www/microblog-template/* " . $tmp_dir);
        system("chmod 500 " . $tmp_dir);
        system("chmod +w /var/www/microblog");
        system("cp -rp " . $tmp_dir . " /var/www/microblog/" . $site_name);
	system("chmod -w microblog");
	system ("chmod -R +w " . $tmp_dir);
	system("rm -r " . $tmp_dir);
        header("Location: /dashboard?message=Site added successfully!&status=success");
    }
    else {
        header("Location: /dashboard?message=Site not added, authentication failed&status=fail");
    }
}

?>
<!DOCTYPE html>
<head>
<link rel="icon" type="image/x-icon" href="../brain.ico">
<link rel="stylesheet" href="http://microblog.htb/static/css/styles.css">
<script src="http://microblog.htb/static/js/jquery.js"></script>
<script src="http://microblog.htb/static/js/fontawesome.js"></script>
<title>Dashboard - Microblog</title>
<script>
    $(window).on('load', function(){
        //populate from DB
        $(".user-first-name").text(<?php echo getFirstName(); ?>);
        const sites = <?php echo json_encode(getSites()); ?>;
        if(sites.length == 0) {
            $(".my-blogs-outer").text("No blogs found... get blogging!!");
            $(".empty-space-blogs").css("min-height", "20px");
        }
        else {
            for(var i = 0; i < sites.length; i++) {
                $(".my-blogs-outer").append(`<div class = "my-blogs-inner"><div class = "my-blogs-name my-blogs-item"><div class = "my-blogs-item-cell">${sites[i]}</div></div><a href = "http://${sites[i]}.microblog.htb" class = "my-blogs-link my-blogs-item"><div class = "my-blogs-item-cell">Visit Site</div></a><a href = "http://${sites[i]}.microblog.htb/edit/" class = "my-blogs-link my-blogs-item"><div class = "my-blogs-item-cell">Edit Site</div></a></div>`)
            }
        }
        const pro = <?php echo isPro(); ?>;
        if(!pro) {
            $(".pro").css("display", "none");
        }
        const queryString = window.location.search;
        if(queryString) {
            const urlParams = new URLSearchParams(queryString);
            if(urlParams.get('message') && urlParams.get('status')) {
                const status = urlParams.get('status')
                const message = urlParams.get('message')
                $(".floating-message").css("display", "block");
                $(".floating-message").children(".message-content").text(message);
                if(status === "fail") {
                    $(".floating-message").css("background-color", "#AF0606");
                }
                else {
                    $(".floating-message").css("background-color", "#4BB543");
                }
            }
        }
    });
</script>
</head>
<body class = "dashboard">
    <div class="floating-message">
        <span class="message-content" style = "margin-right: 10px"></span>
        <span class="closebtn" style = "font-weight: bold;" onclick="this.parentElement.style.display='none';">&times;</span>
    </div>
    <div class = "semi-circle blue-fill">
        <div class="navbar">
            <a href="/" class="float-left title">Microblog</a>
            <div class = "float-right select-buttons">
                <span class = "pro"><i class="fa fa-star gold"></i>&nbsp;&nbsp;<span class = "gold">Pro</span></span>
                <div class = "menu-button hello-text">Hello, <span class = "user-first-name"></span></div>
                <a href="/dashboard" class = "menu-button current-select">Dashboard</a>
                <a href="/logout" class = "menu-button">Logout</a>
            </div>
        </div>
        <div class = "header-content-item">
            <span class = "big-text heading">Dashboard</div>
        </div>
    </div>
    <div style = "min-height: 20px"></div>
    <div class = "dashboard-section-outer">
        <div class = "dashboard-section-inner">
            <span class = "big-text" style="width: 100%">New Blog</span>
            <div style = "min-height: 30px"></div>
            <div class="new-blog-outer">
                <form class = "new-blog-form" action = "<?=$_SERVER['PHP_SELF']?>" method="POST">
                    <div class = "new-blog-big" style = "display: flex;">
                        <input type="text" class = "new-blog-name new-blog-item pink-color" id="new-blog-name" name="new-blog-name" placeholder="myawesomeblog" pattern="[a-z]+" maxlength="50" title="Lowercase letters only, 50 character length limit" required>
                        <div class = "microblog-domain new-blog-item pink-color">.microblog.htb</div>
                    </div>
                    <input class = "new-blog-medium new-blog-item" type="submit" value="Create">
                </form>
            </div>
        </div>
    </div>
    <div style = "min-height: 30px"></div>
    <div class = "dashboard-section-outer white-fill">
        <div class = "dashboard-section-inner">
            <div style = "min-height: 20px"></div>
            <span class = "big-text" style="width: 100%">My Blogs</span>
            <div style = "min-height: 30px"></div>
            <div class = "my-blogs-outer">
            </div>
        </div>
    </div>
    <div class = "empty-space-blogs" style = "min-height: 10px; background-color: white;"></div>
    <footer style = "padding-top: 0px;">
        <div class = "end-message pink-fill" style="min-height: 75px !important;margin-bottom: 10px;">
            <div class = "end-message-text" style="font-size: 18px !important">Wanna go pro and upload images for only $5 a month? <a class = "pro-link" title="Sorry, pro licenses being developed. Please check back soon!"><b>Apply here!</b></a></div>
        </div>
        © 2022 Microblog<br/>
        <a href="https://www.vecteezy.com/free-vector/brain">Brain Vectors by Vecteezy</a>
    </footer>
</body>
</html>
