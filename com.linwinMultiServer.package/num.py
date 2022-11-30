import os

num = 0;

for i in os.listdir('./src/'):
    if i.index('.java'):
        a = open('./src/'+str(i))
        while True:
            line = a.readline()
            if not line:
                break
            num += 1


print(num)
    
