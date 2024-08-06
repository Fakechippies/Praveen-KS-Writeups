*By Praveen Kumar Sharma*

<hr>

For me The IP of the machine is : **192.168.110.55**

Lets try pinging it :

![[Pasted image 20240806212016.png]]

Its online!!

<hr>

### Port Scanning :

#### All Port Scan :

```
nmap -p- -n -Pn -T5 --min-rate=10000 192.168.110.55 -o allPortScan.txt
```

![[Pasted image 20240806212225.png]]

>[!Open Ports]
>PORT   STATE SERVICE
21/tcp open  ftp
22/tcp open  ssh
80/tcp open  http

Lets try a deeper scan on these ports :

```
nmap -sC -sV -A -T5 -p 21,22,80 192.168.110.55 -o deeperScan.txt
```

![[Pasted image 20240806212454.png]]
![[Pasted image 20240806212505.png]]

>[!Deeper Scan]
>PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.2
| ftp-syst: 
|   STAT: 
| FTP server status:
|      Connected to 192.168.110.64
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 600
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 3
|      vsFTPd 3.0.2 - secure, fast, stable
|_End of status
| ftp-anon: Anonymous FTP login allowed (FTP code 230)
|_-rwxrwxrwx    1 1000     0            8068 Aug 10  2014 lol.pcap [NSE: writeable]
22/tcp open  ssh     OpenSSH 6.6.1p1 Ubuntu 2ubuntu2 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   1024 d6:18:d9:ef:75:d3:1c:29:be:14:b5:2b:18:54:a9:c0 (DSA)
|   2048 ee:8c:64:87:44:39:53:8c:24:fe:9d:39:a9:ad:ea:db (RSA)
|   256 0e:66:e6:50:cf:56:3b:9c:67:8b:5f:56:ca:ae:6b:f4 (ECDSA)
|_  256 b2:8b:e2:46:5c:ef:fd:dc:72:f7:10:7e:04:5f:25:85 (ED25519)
80/tcp open  http    Apache httpd 2.4.7 ((Ubuntu))
|_http-server-header: Apache/2.4.7 (Ubuntu)
| http-robots.txt: 1 disallowed entry 
|_/secret
|_http-title: Site doesn't have a title (text/html).
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

We do have a ftp server on port 21 lets enumerate this first 

<hr>

### FTP Enumeration :

Looks like we do have this FTP Server and we can do anonymous login as pointed out by nmap

Lets try connecting

```
ftp 192.168.110.55
```

We can connect also we have this .pcap file in here too 

![[Pasted image 20240806214109.png]]

Lets get it 

![[Pasted image 20240806214128.png]]

Lets see what's it about in Wireshark

<hr>

### PCAP Analysis 

We are gonna use Wireshark here 

Open this file by Clicking the Open button in the File Section Top Left

![[Pasted image 20240806214326.png]]

First Stream here

![[Pasted image 20240806214357.png]]

We dont have this file there 

Lets see what do we have other than this in here 

If you change the eq to 2 and click on this one then follow tcp stream

![[Pasted image 20240806214601.png]]

>[!Directory]
>/sup3rs3cr3tdirlol

Lets do some directory fuzzing

<hr>

### Directory Fuzzing : 

```
gobuster dir -u http://192.168.110.55 -w /usr/share/wordlists/dirb/common.txt
```

![[Pasted image 20240806214835.png]]

>[!Directories]
>/index.html           (Status: 200) [Size: 36]
/robots.txt           (Status: 200) [Size: 31]
/secret               (Status: 301) [Size: 316] [--> http://192.168.110.55/secret/]
/sup3rs3cr3tdirlol

Lets see this Web Application 

<hr>

### Web Application :

![[Pasted image 20240806215017.png]]

Nothing in the source code also the /index.html is this page only 

Lets see this /robots.txt 

![[Pasted image 20240806215059.png]]

Lets see this /secret i guess 

![[Pasted image 20240806215119.png]]

Again nothing in the source code as well 

Lets see the last one as well : /sup3rs3cr3tdirlol

![[Pasted image 20240806215253.png]]

Lets download this fle roflmao then :

```
wget http://192.168.110.55/sup3rs3cr3tdirlol/roflmao
```

![[Pasted image 20240806215353.png]]

Its a executable :

![[Pasted image 20240806215437.png]]

Lets see if we can spot anything in strings of this file 

![[Pasted image 20240806215528.png]]

Mention of this 0x0856BF

Lets try running it as well

![[Pasted image 20240806215616.png]]

Lets see if we find something like this in the web application

![[Pasted image 20240806215713.png]]

Its a directory looks like lets see these files now 

![[Pasted image 20240806215734.png]]

Lets download this 

```
wget http://192.168.110.55/0x0856BF/good_luck/which_one_lol.txt
```

![[Pasted image 20240806215904.png]]

it looks its a set of usernames 

![[Pasted image 20240806215921.png]]

Im gonna remove this <-- so i can work with this 

![[Pasted image 20240806220006.png]]

Lets get the other file as well

![[Pasted image 20240806220058.png]]

```
wget http://192.168.110.55/0x0856BF/this_folder_contains_the_password/Pass.txt
```

![[Pasted image 20240806220117.png]]

this contains this 

![[Pasted image 20240806220144.png]]

<hr>

### Gaining Access :

Lets try brute forcing ssh creds using hydra with these two files

```
hydra -L which_one_lol.txt -P Pass.txt ssh://192.168.110.55
```

![[Pasted image 20240806220317.png]]

No luck :(

Maybe its too obvious maybe the password is Pass.txt 

```
hydra -L which_one_lol.txt -p Pass.txt ssh://192.168.110.55
```

![[Pasted image 20240806220436.png]]

>[!SSH Creds]
>Username : overflow
>Password : Pass.txt

We can login 

![[Pasted image 20240806220546.png]]

<hr>

### Vertical PrivEsc :

Im gonna change my shell to /bin/bash for auto-completion and other stuff u can do this too if u want 

![[Pasted image 20240806220800.png]]

Lets run privEsc.sh u can find this with this document 

do this when u have the script in the same directory

![[Pasted image 20240806221003.png]]

![[Pasted image 20240806221058.png]]

Lets run it 

```
chmod +x privEsc.sh && ./privEsc.sh
```

![[Pasted image 20240806221159.png]]

Im gonna cut short here the important thing is in SysInfo.txt

![[Pasted image 20240806221446.png]]

Lets find some exploit on this 
I found this one here : https://www.exploit-db.com/exploits/37292

![[Pasted image 20240806221524.png]]

Lets download this 

![[Pasted image 20240806221629.png]]

I have saved this like this 

lets get this in the machine 

![[Pasted image 20240806221750.png]]

Lets run this 

![[Pasted image 20240806221807.png]]

![[Pasted image 20240806221821.png]]

Here is the flag :

![[Pasted image 20240806221839.png]]

