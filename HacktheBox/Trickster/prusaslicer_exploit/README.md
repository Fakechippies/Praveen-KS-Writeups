# PrusaSlicer Arbitrary Code Execution 
Priv Esc using a .3mf file with vulnerable prusaslicer version.

Make sure you can run prusaslicer using sudo.

#Linux

1.Download the files

2.Change IP and PORT in exploit.sh

3.Make sure to have exploit.sh on /tmp directory

4.Execute the following command > sudo ./prusaslicer -s evil.3mf 

5.Exploit.sh will execute as root
