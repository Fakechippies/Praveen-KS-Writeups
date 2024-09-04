import hashlib
import string
import itertools

counter = 1
lowercase = list(string.ascii_lowercase)

combo = itertools.product(lowercase, repeat=4)
combinations = [''.join(combos) for combos in combo]

for i in combinations :
    m = hashlib.md5(i.encode('utf-8')).hexdigest()
    if '001' in m:
        print(f'{i} : {m}')
