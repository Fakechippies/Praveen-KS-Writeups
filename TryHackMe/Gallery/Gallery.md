*By Praveen Kumar Sharma*

<hr>

For me IP of the machine is : **10.10.29.22**
Lets try pinging it 

![[Pasted image 20240907204230.png]]

Lets do some port scanning now 

<hr>

### Port Scanning : 

#### All Port Scan : 

```bash
nmap -p- -n -Pn --min-rate=10000 -T5 10.10.29.22 -o allPortScan.txt
```

![[Pasted image 20240907204618.png]]

>[!Open Ports]
>PORT     STATE SERVICE
80/tcp   open  http
8080/tcp open  http-proxy

Lets do a aggressive scan on these 

#### Aggressive Scan :

```bash
nmap -sC -sV -A -T5 -Pn -n -p 80,8080 10.10.29.22 -o aggressiveScan.txt
```

![[Pasted image 20240907205250.png]]

>[!Aggressive Scan]
>PORT     STATE SERVICE VERSION
80/tcp   open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-title: Apache2 Ubuntu Default Page: It works
|_http-server-header: Apache/2.4.29 (Ubuntu)
8080/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
| http-open-proxy: Potentially OPEN proxy.
|_Methods supported:CONNECTION
|_http-title: Simple Image Gallery System
| http-cookie-flags:
|   /:
|     PHPSESSID:
|_      httponly flag not set

Lets do some directory fuzzing next 

<hr>

### Directory Fuzzing : 

```bash
feroxbuster --url http://10.10.29.22
```

![[Pasted image 20240907205911.png]]

>[!Directories]
>301      GET        9l       28w      312c http://10.10.29.22/gallery => http://10.10.29.22/gallery/
200      GET       15l       74w     6147c http://10.10.29.22/icons/ubuntu-logo.png
200      GET      375l      964w    10918c http://10.10.29.22/

Lets get to this web application now 

<hr>

### Web Application : 

Default page 

![[Pasted image 20240907210243.png]]

Lets try this /gallery page

![[Pasted image 20240907210317.png]]

Oh a login page lets try some default creds like `admin:admin`, they didnt work but i saw the request in burp and it looks like 

![[Pasted image 20240907210608.png]]

We have a SQL Injection here 

<hr>

### Gaining Access : 

To exploit this put in username as `admin' OR 1=1-- -`

![[Pasted image 20240907210820.png]]

And it worked lets login now with `admin' OR 1=1-- -`

![[Pasted image 20240907210917.png]]

Alright here to get a shell here go to admin button top right 

![[Pasted image 20240907211040.png]]

Here is our vector to get in 

Download the pentest-monkey php-reverse-shell and change the IP and port 

![[Pasted image 20240907211234.png]]

Start a listener 

![[Pasted image 20240907211259.png]]

Now upload the php revshell and hit update and u should have your revshell 

![[Pasted image 20240907211409.png]]

Lets upgrade it 

![[Pasted image 20240907211559.png]]

<hr>

### Lateral Movement : 

I found the MySQL password hardcoded here 

![[Pasted image 20240907211835.png]]

Lets login in MySQL

![[Pasted image 20240907211942.png]]

Lets see the databases

![[Pasted image 20240907212025.png]]

Lets see the tables of this one 

![[Pasted image 20240907212144.png]]

Lets see the data from this table 

![[Pasted image 20240907212245.png]]

One of the answers here 

Moving on I found this backup file of mike home directory in /var/backups/

![[Pasted image 20240907212409.png]]

Lets see the .bash_history to see if we can spot a password 

![[Pasted image 20240907212507.png]]

Got the password of mike 

>[!Creds]
>Username : mike
>Password : b3stpassw0rdbr0xx

Lets change our user to mike 

![[Pasted image 20240907212636.png]]

Here is your user.txt 

![[Pasted image 20240907212728.png]]

<hr>

### Vertical PrivEsc

Lets check the sudo permission here 

![[Pasted image 20240907212823.png]]

Lets see the code here for this script 

![[Pasted image 20240907212922.png]]

Its running nano for read lets check GTFObins for nano running as root 

![[Pasted image 20240907213027.png]]

Lets run it and execute these to get root, Also hit enter a couple of times to see your command as well

![[Pasted image 20240907213221.png]]

Here is your root.txt 

![[Pasted image 20240907213302.png]]

Thanks for reading :)




