I got the server-passphrase here 

![[Pasted image 20241116191326.png]]

Now i converted this to salted passwd as the article says

![[Pasted image 20241116191406.png]]

Now ill go to the site 

![[Pasted image 20241116191514.png]]

Putting in `test` here i captured this in burp

![[Pasted image 20241116191600.png]]

Now i captured its response 

![[Pasted image 20241116191634.png]]

Got this nonce here now i run that command that the article ran in console

![[Pasted image 20241116191810.png]]

Now i copy this and then put this in the next POST request after forwarding this response request

And i did URL encode this 

![[Pasted image 20241116191928.png]]

Now forwarding this 

![[Pasted image 20241116191944.png]]


