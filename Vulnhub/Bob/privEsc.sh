#!/bin/bash

main=Privy
sys=$main/SysInfo.txt
ugo=$main/UserGroupInfo.txt
svc=$main/RootServices.txt
suid=$main/SUID-GUID.txt
cronned=$main/CronJobs.txt
path_info=$main/PATH-Info.txt
netstuff=$main/NetworkInfo.txt
shdw=$main/Shadow.txt
pswd=$main/Passwd.txt
sql=$main/MySQL.txt



mkdir ./$main
echo "[+] Getting System Info"
echo "==========================================================="
echo "[+] System Info" >> $sys
echo "===========================================================" >> $sys
date >> $sys
echo "" >> $sys
echo -e "\033[0;32muname -a\033[0m" >> $sys
echo "--------" >> $sys
uname -a >> $sys
echo >> $sys
echo -e "\033[0;32mcat /etc/issue\033[0m" >> $sys
echo "--------------" >> $sys
cat /etc/issue >> $sys
echo >> $sys
echo -e "\033[0;32mcat /etc/*-release\033[0m" >> $sys
echo "------------------" >> $sys
cat /etc/*-release >> $sys
echo >> $sys
echo >> $sys
echo "[+] Getting User/Group Info"
echo "==========================================================="
echo "[+] User/Group Info" >> $ugo
echo "===========================================================" >> $ugo
date >> $ugo
echo "" >> $ugo
echo -e "\033[0;32mwhoami\033[0m" >> $ugo
echo "------" >> $ugo
whoami >> $ugo
echo >> $ugo
echo -e "\033[0;32mid\033[0m" >> $ugo
echo "------" >> $ugo
id >> $ugo
echo >> $ugo
echo -e "\033[0;32mcat /etc/passwd\033[0m" >> $ugo
echo "---------------" >> $ugo
echo -e "*** CHECK FOR \033[0;32mPasswd.txt\033[0m FILE IN ./Privy ***" >> $ugo
cat /etc/passwd >> $pswd
echo >> $ugo
echo -e "\033[0;32mcat /etc/group\033[0m" >> $ugo
echo "---------------" >> $ugo
cat /etc/group >> $ugo
echo >> $ugo
echo -e "\033[0;32mcat /etc/shadow\033[0m" >> $ugo
echo "---------------" >> $ugo
echo -e "*** CHECK FOR \033[0;32mShadow.txt\033[0m FILE IN ./Privy ***" >> $ugo
cat /etc/shadow >> $shdw
echo >> $ugo
echo -e "\033[0;32mlast\033[0m" >> $ugo
echo "------" >> $ugo
last >> $ugo
echo >> $ugo
echo -e "\033[0;32mls -al /home/*\033[0m" >> $ugo
echo "--------------" >> $ugo
ls -al /home/* >> $ugo
echo >> $ugo
echo -e "\033[0;32mls -al /root\033[0m" >> $ugo
echo "------------" >> $ugo
ls -al /root >> $ugo
echo >> $ugo
echo -e "\033[0;32msudo -l 2>&1\033[0m" >> $ugo
echo "------------" >> $ugo
sudo -l 2>&1 >> $ugo
echo >> $ugo
echo >> $ugo
echo "[+] Looking For Services Running as Root"
echo "==========================================================="
echo "[+] Services Running as Root" >> $svc
echo "===========================================================" >> $svc
date >> $svc
echo -e "\033[0;32mps aux | grep root\033[0m" >> $svc
echo "------------------" >> $svc
ps aux | grep root >> $svc
echo >> $svc
echo >> $svc
echo "[+] Looking For SUID/GUID Files and World Writable Files"
echo "==========================================================="
echo "[+] SUID/GUID and World Writable Files" >> $suid
echo "===========================================================" >> $suid
date >> $suid
echo >> $suid
echo -e "\033[0;32mSUID (find / -perm -u=s -type f 2>/dev/null\033[0m" >> $suid
echo "-------------------------------------------" >> $suid
find / -perm -u=s -type f 2>/dev/null >> $suid
echo >> $suid
echo -e "\033[0;32mGUID (find / -perm -g=s -type f 2>/dev/null\033[0m" >> $suid
echo "-------------------------------------------" >> $suid
find / -perm -g=s -type f 2>/dev/null >> $suid
echo >> $suid
echo -e "\033[0;32mWorld Writeable Files (find / -perm -2 -type f 2>/dev/null | grep -v /proc/ \033[0m" >> $suid
echo "--------------------------------------------------------------------------" >> $suid
find / -perm -2 -type f 2>/dev/null | grep -v /proc/ >> $suid
echo >> $suid
echo >> $suid
echo "[+] Looking For Cron Jobs"
echo "==========================================================="
echo "[+] Cron Jobs" >> $cronned
echo "===========================================================" >> $cronned
date >> $cronned
echo >> $cronned
echo -e "\033[0;32mcat /etc/crontab\033[0m" >> $cronned
echo "----------------" >> $cronned
cat /etc/crontab >> $cronned
echo >> $cronned
echo >> $cronned
echo "[+] Looking For PATH Info"
echo "==========================================================="
echo "[+] PATH Info" >> $path_info
echo "===========================================================" >> $path_info
date >> $path_info
echo >> $path_info
echo -e "\033[0;32mecho \$PATH\033[0m" >> $path_info
echo "-------------" >> $path_info
echo $PATH >> $path_info
echo >> $path_info
echo >> $path_info
echo "[+] Checking For Network Info"
echo "==========================================================="
echo "[+] Network Information" >> $netstuff
echo "===========================================================" >> $netstuff
date >> $netstuff
echo >> $netstuff
echo -e "\033[0;32mifconfig -a\033[0m" >> $netstuff
echo "-----------" >> $netstuff
ifconfig -a >> $netstuff
echo >> $netstuff
echo -e "\033[0;32mip addr\033[0m" >> $netstuff
echo "-------" >> $netstuff
ip addr >> $netstuff
echo >> $netstuff
echo -e "\033[0;32mhostname -i\033[0m" >> $netstuff
echo "-----------" >> $netstuff
hostname -i >> $netstuff
echo >> $netstuff
echo -e "\033[0;32mnetstat -antup\033[0m" >> $netstuff
echo "--------------" >> $netstuff
netstat -antup >> $netstuff
echo >> $netstuff
echo -e "\033[0;32mroute\033[0m" >> $netstuff
echo "-----" >> $netstuff
route >> $netstuff
echo >> $netstuff
echo >> $netstuff
echo "[+] Looking For MySQL Info"
echo "==========================================================="
echo "[+] MySQL Info" >> $sql
echo "===========================================================" >> $path_info
date >> $sql
echo >> $sql
echo -e "\033[0;32mMySQL Version (mysql --version 2>/dev/null)\033[0m" >> $sql
echo "--------------------------------------------" >> $sql
mysql --version 2>/dev/null >> $sql
echo >> $sql
echo >> $sql
echo -e "\033[0;32mEasy Access to MySQL? (mysql -u root)\033[0m" >> $sql
echo "------------------------------------" >> $sql
mysql -u root 

#checks to see if root/root will get us a connection
mysqlconnect=`mysqladmin -uroot -proot version 2>/dev/null`
if [ "$mysqlconnect" ]; then
  echo -e "\e[00;33m[+] We can connect to the local MYSQL service with default root/root credentials!\e[00m\n$mysqlconnect" >> $sql
  echo -e "\n" >> $sql
else 
  :
fi

#mysql version details
mysqlconnectnopass=`mysqladmin -uroot version 2>/dev/null`
if [ "$mysqlconnectnopass" ]; then
  echo -e "\e[00;33m[+] We can connect to the local MYSQL service as 'root' and without a password!\e[00m\n$mysqlconnectnopass" >> $sql
  echo -e "\n" >> $sql
else 
  :
fi



echo >> $sql
echo >> $sql

echo "."
echo "."
echo "."
echo "[+] DONE!"
echo
echo

