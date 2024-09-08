*By Praveen Kumar Sharma*

<hr>

For me IP of the machine is : **10.10.167.47**

Lets try pinging it 

![[Pasted image 20240908182635.png]]

Alright lets do some port scanning 

<hr>

### Port Scanning : 

#### All Port Scan 

```bash
rustscan -a 10.10.167.47 --ulimit 5000
```

![[Pasted image 20240908182820.png]]
![[Pasted image 20240908182845.png]]

>[!Open Ports]
>PORT      STATE SERVICE REASON
21/tcp    open  ftp     syn-ack
22/tcp    open  ssh     syn-ack
80/tcp    open  http    syn-ack
62337/tcp open  unknown syn-ack

Lets do an aggressive scan on these 
#### Aggressive Scan : 

```bash
nmap -sC -sV -A -T5 -Pn -n -p 21,22,80,62337 10.10.167.47 -o aggressivScan.txt
```

![[Pasted image 20240908183154.png]]

>[!Aggressive Scan]
>PORT      STATE SERVICE VERSION
21/tcp    open  ftp     vsftpd 3.0.3
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
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
|      At session startup, client count was 2
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
22/tcp    open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   2048 e2:be:d3:3c:e8:76:81:ef:47:7e:d0:43:d4:28:14:28 (RSA)
|   256 a8:82:e9:61:e4:bb:61:af:9f:3a:19:3b:64:bc:de:87 (ECDSA)
|_  256 24:46:75:a7:63:39:b6:3c:e9:f1:fc:a4:13:51:63:20 (ED25519)
80/tcp    open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
62337/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Codiad 2.8.4
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

One thing here is this Codiad 2.8.4 here on port 62337 might have to keep and eye one this 

Alright moving before do some directory fuzzing  on both 80 and 62337 lets do some ftp enumeration first

<hr>

### FTP Enumeration : 

So the nmap showed that anonymous login is possible on this one 

![[Pasted image 20240908183609.png]]

So we can login but nothing here tho but lets just try a long listing just in case we have something here 

![[Pasted image 20240908183644.png]]

Lets see the triple dot here 

![[Pasted image 20240908184242.png]]

Alright lets get this file on our system now 

![[Pasted image 20240908184348.png]]

Lets read this now 

![[Pasted image 20240908184435.png]]

No indication of a password i assume it should be easier to brute force 

Moving on lets do directory fuzzing 

<hr>

### Directory Fuzzing :

#### Port 80 

```bash
feroxbuster --url http://10.10.167.47 -t 200 -w /usr/share/wordlists/dirb/common.txt
```

![[Pasted image 20240908184718.png]]

Nothing here tho lets try on port 62337
#### Port 62337

```bash
feroxbuster --url http://10.10.167.47:62337 -t 200 -w /usr/share/wordlists/dirb/common.txt
```

![[Pasted image 20240908184956.png]]

A lot of em tho might not need it u'll see later

Lets get to this web application now 

<hr>

### Web Application : 

On port 80 

![[Pasted image 20240908185158.png]]

Lets check port 62337 too 

![[Pasted image 20240908185242.png]]

A login page lets try some obvious one like `john:john` or `john:password`
Somehow `john:password` worked 

>[!Webpage Creds]
>Username : john
>Password : password

Logging in : 

![[Pasted image 20240908185515.png]]

Lets find some exploit now for this as we saw this is Codiad 2.8.4

<hr>

### Gaining Access : 

So i searched for Codiad 2.8.4 and found this : https://www.exploit-db.com/exploits/49705

![[Pasted image 20240908185638.png]]

Perfect lets try this 

![[Pasted image 20240908185814.png]]

Alright let put in those two command in two separate terminal windows 

![[Pasted image 20240908185911.png]]

![[Pasted image 20240908185921.png]]

Lets move forward in the script now 

![[Pasted image 20240908190013.png]]

And we get our shell 

![[Pasted image 20240908190050.png]]

Lets upgrade this 

![[Pasted image 20240908190216.png]]

<hr>

### Lateral PrivEsc

So this the user on the machine 

![[Pasted image 20240908190307.png]]

Lets go in his home directory to see what we can read 

![[Pasted image 20240908190404.png]]

Lets read .bash_history cuz we can 

![[Pasted image 20240908190503.png]]

So mysql is not present on this machine so im gonna assume this is drac password 

>[!SSH Creds]
>Username : drac
>Password : Th3dRaCULa1sR3aL

Lets SSH in now 

![[Pasted image 20240908190653.png]]

There we go here is ur user.txt 

![[Pasted image 20240908190741.png]]

<hr>

### Vertical PrivEsc

So lets check the sudo permission on this 

![[Pasted image 20240908190843.png]]

So this should be fairly easy we just need to edit the vsftpd.service let check the permission on it 

![[Pasted image 20240908191012.png]]

Lets edit to this add a revshell in there to get as root 

![[Pasted image 20240908191158.png]]

Alright start save this and start a listener 

![[Pasted image 20240908191309.png]]

Now run that command we can with sudo 

![[Pasted image 20240908191357.png]]

This is normal just put in the command it suggests 

![[Pasted image 20240908191441.png]]

Now lets run the command again 

![[Pasted image 20240908191530.png]]

Nothing should happen here and u should get your revshell here 

![[Pasted image 20240908191616.png]]

Here is your root.txt 

![[Pasted image 20240908191655.png]]

Thanks for reading :)

