*By Praveen Kumar Sharma*

<hr>

For me IP of the machine is : **10.10.58.212**

Lets try pinging it 

![[Pasted image 20240905210215.png]]

Alright lets do some port scanning next 

<hr>

### Port Scanning : 

#### All Port Scan : 

```bash
nmap -p- -n -Pn --min-rate=10000 -T5 10.10.58.212 -o allPortScan.txt
```

![[Pasted image 20240905210411.png]]

>[!Open Ports]
>PORT    STATE SERVICE
22/tcp  open  ssh
80/tcp  open  http
445/tcp open  microsoft-ds

Lets enumerate further on those ports 

#### Aggressive Scan 

```bash
nmap -sC -sV -A -T5 -n -Pn -p 22,80,445 10.10.58.212 -o aggressiveScan.txt
```

![[Pasted image 20240905210816.png]]

>[!Aggressive Scan]
>PORT    STATE SERVICE VERSION
22/tcp  open  ssh     OpenSSH 8.2p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   3072 a3:6a:9c:b1:12:60:b2:72:13:09:84:cc:38:73:44:4f (RSA)
|   256 b9:3f:84:00:f4:d1:fd:c8:e7:8d:98:03:38:74:a1:4d (ECDSA)
|_  256 d0:86:51:60:69:46:b2:e1:39:43:90:97:a6:af:96:93 (ED25519)
80/tcp  open  http    Apache httpd 2.4.41 ((Ubuntu))
|_http-title: Apache2 Ubuntu Default Page: It works
|_http-server-header: Apache/2.4.41 (Ubuntu)
445/tcp open  http    Apache httpd 2.4.41 ((Ubuntu))
|_http-server-header: Apache/2.4.41 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Alright now lets do some directory fuzzing on these two http servers 

<hr>

### Directory Fuzzing : 

Lets do the port 80 first 

```bash
ffuf -u http://10.10.58.212:80/FUZZ -w /usr/share/wordlists/dirb/common.txt -t 200
```

![[Pasted image 20240905211027.png]]

>[!Directories on Port 80]
>admin                   [Status: 301, Size: 312, Words: 20, Lines: 10, Duration: 149ms]
>index.html              [Status: 200, Size: 10918, Words: 3499, Lines: 376, Duration: 154ms]
>passwd                  [Status: 200, Size: 25, Words: 1, Lines: 2, Duration: 249ms]
>shadow                  [Status: 200, Size: 25, Words: 1, Lines: 2, Duration: 152ms]

Ok now on port 445 

```bash
ffuf -u http://10.10.58.212:445/FUZZ -w /usr/share/wordlists/dirb/common.txt -t 200
```

![[Pasted image 20240905211253.png]]

>[!Directories on Port 445]
>index.html              [Status: 200, Size: 10918, Words: 3499, Lines: 376, Duration: 148ms]
management              [Status: 301, Size: 322, Words: 20, Lines: 10, Duration: 149ms]

Lets get to this web application now 

<hr>

### Web Application : 

#### Port 80 :

Default page 

![[Pasted image 20240905211510.png]]

lets try the /passwd first 

![[Pasted image 20240905211552.png]]

Lets decode this 

![[Pasted image 20240905211632.png]]

Alright there lets try the /shadow page 

![[Pasted image 20240905212317.png]]

Same thing as before lets try that /admin page now 

![[Pasted image 20240905212351.png]]

Lets take a look at this 

![[Pasted image 20240905212424.png]]

Lets decode this i guess 

![[Pasted image 20240905212526.png]]

Okay! lets try our luck at port 445 now 

#### Port 445 : 

Default page 

![[Pasted image 20240905212658.png]]

Lets see this /management page 

![[Pasted image 20240905212755.png]]

Lets click on the login button here 

![[Pasted image 20240905212834.png]]

Ok tried `admin:admin` and `admin:password` nothing worked lets capture one of these request and see what it going on in the background

![[Pasted image 20240905213007.png]]

<hr>

### Gaining Access : 

Looks like an easy SQL injection lets try to test it 

![[Pasted image 20240905213110.png]]

Got it lets try this in the login page now 

![[Pasted image 20240905213149.png]]

And we can login 

![[Pasted image 20240905213220.png]]

Ok so there is a few vulnerablity i found if u create a new offense we have XSS there but i just upload a php rev shell here 
Drivers List -> Action -> Edit 

 We can upload our revshell here 

![[Pasted image 20240905213432.png]]

Grab the pentest monkey php revshell and change the IP address and Port 

![[Pasted image 20240905213826.png]]

Start a listener next 

![[Pasted image 20240905213856.png]]

Now upload the revshell on the photo section 

and open the page of the user again and u should have your revshell here 

![[Pasted image 20240905214037.png]]

Lets upgrade this by usual 

```bash
python3 -c 'import pty; pty.spawn("/bin/bash")'

Ctrl+z

stty raw -echo; fg

export TERM=xterm
```

<hr>

### Lateral PrivEsc : 

So i checked the all the SUID binary files and found one that might get us root later on 

```bash
find / -perm -u=s -type f 2>/dev/null
```

![[Pasted image 20240905214443.png]]

Lets run linpeas for now 

![[Pasted image 20240905214622.png]]

So found this cronjob that is running lets see this where this is 

![[Pasted image 20240905214857.png]]

We can write in this folder lets delete this backup.sh here and then put our revshell to get a shell as this user 

![[Pasted image 20240905215147.png]]

Now start a listener and wait for the cronjob to run and get us teh shell 

![[Pasted image 20240905215359.png]]

Lets upgrade this as well 

![[Pasted image 20240905215503.png]]

here is user.txt 

![[Pasted image 20240905215609.png]]

<hr>

### Vertical PrivEsc 

So for Vertical i mention doas which had the suid bit

To exploit that lets first see its conf at /etc/doas.conf

![[Pasted image 20240905215818.png]]

So to get the root.txt just type in this as openssl can be run with root privileges 

```bash
doas openssl enc -in /root/root.txt
```

![[Pasted image 20240905220048.png]]

Not showing the entire file get it yourself ;)

Thanks for Reading :)

