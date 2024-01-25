num1=int(input('num1: '))
num2=int(input('num2: '))
f1=0
f2=0
if(num1 <0):
    f1=1
if(num2 <0):
    f2=1

absolutenum1 = abs(num1)
absolutenum2 = abs(num2)

finalresult = 0

while(absolutenum2 > 0):
    if(absolutenum2 % 2==1):
       finalresult = finalresult + absolutenum1;
    absolutenum1 = absolutenum1*2
    absolutenum2 = absolutenum2//2;
if(f1==1 and f2==1):   
    print(finalresult)

if(f1==0 and f2==0):
    print(finalresult)

if(f1 != f2):
    print('-',finalresult)
