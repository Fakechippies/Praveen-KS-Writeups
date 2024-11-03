<?php
$username = session_name("username");
session_set_cookie_params(0, '/', '.microblog.htb');
session_start();
if(file_exists("bulletproof.php")) {
    require_once "bulletproof.php";
}

if(is_null($_SESSION['username'])) {
    header("Location: /");
    exit;
}

function checkUserOwnsBlog() {
    $redis = new Redis();
    $redis->connect('/var/run/redis/redis.sock');
    $subdomain = array_shift((explode('.', $_SERVER['HTTP_HOST'])));
    $userSites = $redis->LRANGE($_SESSION['username'] . ":sites", 0, -1);
    if(!in_array($subdomain, $userSites)) {
        header("Location: /");
        exit;
    }
}

function provisionProUser() {
    if(isPro() === "true") {
        $blogName = trim(urldecode(getBlogName()));
        system("chmod +w /var/www/microblog/" . $blogName);
        system("chmod +w /var/www/microblog/" . $blogName . "/edit");
        system("cp /var/www/pro-files/bulletproof.php /var/www/microblog/" . $blogName . "/edit/");
        system("mkdir /var/www/microblog/" . $blogName . "/uploads && chmod 700 /var/www/microblog/" . $blogName . "/uploads");
        system("chmod -w /var/www/microblog/" . $blogName . "/edit && chmod -w /var/www/microblog/" . $blogName);
    }
    return;
}

//always check user owns blog before proceeding with any actions
checkUserOwnsBlog();

//provision pro environment for new pro users
provisionProUser();

//delete section
if(isset($_POST['action']) && isset($_POST['id'])) {
    chdir(getcwd() . "/../content");
    $contents = file_get_contents("order.txt");
    $contents = str_replace($_POST['id'] . "\n", '', $contents);
    file_put_contents("order.txt", $contents);

    //delete image file if content is image
    $data = file_get_contents($_POST['id']);
    $img_check = substr($data, 0, 26);
    if($img_check === "<div class = \"blog-image\">") {
        $startsAt = strpos($data, "<img src = \"/uploads/") + strlen("<img src = \"/uploads/");
        $endsAt = strpos($data, "\" /></div>", $startsAt);
        $fileToDelete = substr($data, $startsAt, $endsAt - $startsAt);
        chdir(getcwd() . "/../uploads");
        $file_pointer = $fileToDelete;
        unlink($file_pointer);
        chdir(getcwd() . "/../content");
    }
    $file_pointer = $_POST['id'];
    unlink($file_pointer);
    return "Section deleted successfully";
}

//add header
if (isset($_POST['header']) && isset($_POST['id'])) {
    chdir(getcwd() . "/../content");
    $html = "<div class = \"blog-h1 blue-fill\"><b>{$_POST['header']}</b></div>";
    $post_file = fopen("{$_POST['id']}", "w");
    fwrite($post_file, $html);
    fclose($post_file);
    $order_file = fopen("order.txt", "a");
    fwrite($order_file, $_POST['id'] . "\n");  
    fclose($order_file);
    header("Location: /edit?message=Section added!&status=success");
}

//add text
if (isset($_POST['txt']) && isset($_POST['id'])) {
    chdir(getcwd() . "/../content");
    $txt_nl = nl2br($_POST['txt']);
    $html = "<div class = \"blog-text\">{$txt_nl}</div>";
    $post_file = fopen("{$_POST['id']}", "w");
    fwrite($post_file, $html);
    fclose($post_file);
    $order_file = fopen("order.txt", "a");
    fwrite($order_file, $_POST['id'] . "\n");  
    fclose($order_file);
    header("Location: /edit?message=Section added!&status=success");
}

//add image
if (isset($_FILES['image']) && isset($_POST['id'])) {
    if(isPro() === "false") {
        print_r("Pro subscription required to upload images");
        header("Location: /edit?message=Pro subscription required&status=fail");
        exit();
    }
    $image = new Bulletproof\Image($_FILES);
    $image->setLocation(getcwd() . "/../uploads");
    $image->setSize(100, 3000000);
    $image->setMime(array('png'));

    if($image["image"]) {
        $upload = $image->upload();

        if($upload) {
            $upload_path = "/uploads/" . $upload->getName() . ".png";
            $html = "<div class = \"blog-image\"><img src = \"{$upload_path}\" /></div>";
            chdir(getcwd() . "/../content");
            $post_file = fopen("{$_POST['id']}", "w");
            fwrite($post_file, $html);
            fclose($post_file);
            $order_file = fopen("order.txt", "a");
            fwrite($order_file, $_POST['id'] . "\n");  
            fclose($order_file);
            header("Location: /edit?message=Image uploaded successfully&status=success");
        }
        else {
            header("Location: /edit?message=Image upload failed&status=fail");
        }
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

function getBlogName() {
    return '"' . array_shift((explode('.', $_SERVER['HTTP_HOST']))) . '"';
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
    chdir(getcwd() . "/../content");
    $order = file("order.txt", FILE_IGNORE_NEW_LINES);
    $html_content = "";
    foreach($order as $line) {
        $temp = $html_content;
        $html_content = $temp . "<div class = \"{$line} blog-indiv-content\">" . file_get_contents($line) . "</div>";
    }
    return $html_content;
}

?>
<!DOCTYPE html>
<head>
<link rel="icon" type="image/x-icon" href="/images/brain.ico">
<link rel="stylesheet" href="http://microblog.htb/static/css/styles.css">
<script src="http://microblog.htb/static/js/jquery.js"></script>
<script src="http://microblog.htb/static/js/fontawesome.js"></script>
<title></title>
<script>
    $(window).on('load', function(){
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
        const pro = <?php echo isPro(); ?>;
        if(!pro) {
            $(".pro").css("display", "none");
            $("#img-dot").css("display", "none");
        }
        const html = <?php echo json_encode(fetchPage()); ?>.replace(/(\r\n|\n|\r)/gm, "");
        $(".push-for-h1").after(html);
        $(".user-first-name").text(<?php echo getFirstName(); ?>);
        $(".blog-name").text(<?php echo getBlogName(); ?>);
        const class_after_push = $(".push-for-h1").next().children().attr('class');
        if(class_after_push) {
            if(class_after_push.includes("blog-h1")) {
                $(".push-for-h1").css("display", "none");
            }
        }
        const placeholders = ["Today, I learned to...", "You won't believe what happened! I went...", "My name is...", "On today's adventure in the park, I...", "Well, it finally happened..."];
        $(".txt-form-input").attr("placeholder", placeholders.sort(() => 0.5 - Math.random())[0]);
        $(".form-id").attr("value", Math.random().toString(36).slice(2));

        //image upload
        const actualBtn = document.getElementById('actual-btn');
        const fileChosen = document.getElementById('file-chosen');
        actualBtn.addEventListener('change', function(){
            fileChosen.textContent = this.files[0].name
        })

        //add delete buttons
        $('.blog-indiv-content').each(
            function() {
                $(this).prepend("<i class=\"fa fa-trash delete-button\" onclick=delete_section(this)></i>");
            }
        )

        const blogName = String(window.location).split('.')[0].split('//')[1]
        document.title = blogName + " - edit - Microblog"
        $(".blog-name").attr("href", "http://"+blogName+".microblog.htb")
    });
</script>
<script>
    function showForm(name) {
        //reset selected options
        $(".dot").removeClass("dotSelected");
        $(".component-form").css("display", "none");
        $(".dot").css("background", "#CA776D");

        $(`#${name}-dot`).addClass("dotSelected");
        $(".dot").hover(
            function() {
                if(!$(this).hasClass("dotSelected")) {
                    $(this).css("background", "#e4aaa3")
                }
            },
            function() {
                if(!$(this).hasClass("dotSelected")) {
                    $(this).css("background", "#CA776D")
                }
            }
        )
        $(`#${name}-form`).css("display", "block");
        $(`#${name}-dot`).css("background", "#e4aaa3");
    }
</script>
<script>
    function delete_section(section) {
        const id = $(section).parent().attr('class').split(" ")[0]
        $.ajax({
            type: "POST",
            url: "/edit/index.php",
            data: {"action":"delete","id":id},
            success: function() {
                window.location.replace("/edit?message=Section deleted&status=success");
            }
        })
    }
</script>
</head>
<body>
    <div class="floating-message">
        <span class="message-content" style = "margin-right: 10px"></span>
        <span class="closebtn" style = "font-weight: bold;" onclick="this.parentElement.style.display='none';">&times;</span>
    </div>
    <div class = "blue-fill" style = "border-bottom: 2px solid; padding-bottom: 25px;">
        <div class="navbar" style = "overflow: inherit;">
            <a href="http://app.microblog.htb" class="float-left title">Microblog</a>
            <div class = "float-right select-buttons">
                <span class = "pro"><i class="fa fa-star gold"></i>&nbsp;&nbsp;<span class = "gold">Pro</span></span>
                <div class = "menu-button hello-text">Hello, <span class = "user-first-name"></span></div>
                <a href="http://app.microblog.htb/dashboard" class = "menu-button">Dashboard</a>
                <a href="http://app.microblog.htb/logout" class = "menu-button">Logout</a>
            </div>
        </div>
        <div class = "header-content-item">
            <span class = "big-text heading">Edit Blog</span>
            <a class = "blog-name" style = "font-size: 25px; text-align: center; top: -20px; position: relative; display: block; margin-left: auto; margin-right: auto; width: min-content;"></a>
        </div>
    </div>
    <div class = "push-for-h1" style = "min-height: 25px;"></div>
    <div class = "component-selector">
        <div class = "links">
            <a class = "dot" id = "h1-dot" onclick="showForm('h1')">h1</a>
            <a class = "dot" id = "txt-dot" onclick="showForm('txt')">txt</a>
            <a class = "dot" id = "img-dot" onclick="showForm('img')">img</a>
        </div>
    </div>
    <form action="<?=$_SERVER['PHP_SELF']?>" method="POST" class = "component-form" id = "h1-form">
        <input class = "form-id" name = "id" type="hidden"/>
        <input name = "header" type = "text" placeholder = "Header" required>
        <input type = "submit" value="Post">
    </form>
    <form action="<?=$_SERVER['PHP_SELF']?>" method="POST" class = "component-form" id = "txt-form">
        <input class = "form-id" name = "id" type="hidden"/>
        <textarea name = "txt" form="txt-form" class = "txt-form-input" required></textarea>
        <input type = "submit" value="Post">
    </form>
    <form action="<?=$_SERVER['PHP_SELF']?>" method="POST" class = "component-form" id = "img-form" enctype="multipart/form-data">
        <div class = "image-upload-outer">
            <input class = "form-id" name = "id" type="hidden"/>
            <input type="file" id="actual-btn" name="image" accept="image/png" hidden required/>
            <label class = "select-image-label pink-fill" for="actual-btn">Select Image</label>
            <span id="file-chosen">No image selected</span>
        </div>
        <p></p>
        <input type = "submit" value="Upload">
    </form>
    <footer>
        © 2022 Microblog<br/>
        <a href="https://www.vecteezy.com/free-vector/brain">Brain Vectors by Vecteezy</a>
    </footer>
</body>
</html>
