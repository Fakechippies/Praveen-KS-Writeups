import requests
file1 = requests.get("http://localhost/messageA")
file2 = requests.get("http://localhost/messageB")
params = {'user': file1.content, 'pass': file2.content}
r = requests.get("http://temple.fortress:7331/t3mple_0f_y0ur_51n5.php/",params=params)
print (r.text)
