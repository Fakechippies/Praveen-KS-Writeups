<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();

function checkAuth() {
    return(isset($_SESSION['username']));
}

function checkOwner() {
    if(checkAuth()) {
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
        $subdomain = array_shift((explode('.', $_SERVER['HTTP_HOST'])));
        $userSites = $redis->LRANGE($_SESSION['username'] . ":sites", 0, -1);
        if(in_array($subdomain, $userSites)) {
            return $_SESSION['username'];
        }
    }
    return "";
}

function getFirstName() {
    if(isset($_SESSION['username'])) {
        $redis = new Redis();
        $redis->connect('/var/run/redis/redis.sock');
        $firstName = $redis->HGET($_SESSION['username'], "first-name");
        return "\"" . ucfirst(strval($firstName)) . "\"";
    }
}

function fetchPage() {
    chdir(getcwd() . "/content");
    $order = file("order.txt", FILE_IGNORE_NEW_LINES);
    $html_content = "";
    foreach($order as $line) {
        $temp = $html_content;
        $html_content = $temp . "<div class = \"{$line}\">" . file_get_contents($line) . "</div>";
    }
    return $html_content;
}

?>
<!DOCTYPE html>
<head>
<link rel="icon" type="image/x-icon" href="/images/brain.ico">
<link rel="stylesheet" href="http://microblog.htb/static/css/styles.css">
<script src="http://microblog.htb/static/js/jquery.js"></script>
<title></title>
<script>
    $(window).on('load', function(){
        const html = <?php echo json_encode(fetchPage()); ?>.replace(/(\r\n|\n|\r)/gm, "");
        $(".push-for-h1").after(html);
        if(html.length === 0) {
            $(".your-blog").after("<div class = \"empty-blog\">Blog in progress... check back soon!</div>");
            $(".push-for-h1").css("display", "none");
        }
        const siteOwner = <?php echo json_encode(checkOwner()); ?>;
        if(siteOwner.length > 0) {
            $(".your-blog").css("display", "flex");
            $(".user-first-name").text(<?php echo getFirstName(); ?>);
        }
        const class_after_push = $(".push-for-h1").next().children().attr('class');
        if(class_after_push) {
            if(class_after_push.includes("blog-h1")) {
                $(".push-for-h1").css("display", "none");
            }
        }

        const blogName = String(window.location).split('.')[0].split('//')[1]
        document.title = blogName + " - Microblog"
    });
</script>
</head>
<body>
    <div class = "your-blog">
        <div><span class = "user-first-name"></span>, this is your blog! <a href = "/edit" style = "color: white;"><b>Edit it here.</b></a></div>
    </div>
    <div class = "push-for-h1" style = "min-height: 25px;"></div>
    <footer>
        © 2022 Microblog<br/>
        <a href="https://www.vecteezy.com/free-vector/brain">Brain Vectors by Vecteezy</a>
    </footer>
</body>
</html>
