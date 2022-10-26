# 远程服务连接终端
# 适用于linux系统

from operator import indexOf
import os
from textwrap import indent
import requests
import time

def Remote_Input():
    #远程控制输入

    return True

def Remote_Print():
    #远程控制输出

    return True

def Connect():

    os.system("clear")

    print('''
 _     _    __        ___       
| |   (_)_ _\ \      / (_)_ __  
| |   | | '_ \ \ /\ / /| | '_ \ 
| |___| | | | \ V  V / | | | | |
|_____|_|_| |_|\_/\_/  |_|_| |_|
 _   _ _   _        ____                           
| | | | |_| |_ _ __/ ___|  ___ _ ____   _____ _ __ 
| |_| | __| __| '_ \___ \ / _ \ '__\ \ / / _ \ '__|
|  _  | |_| |_| |_) |__) |  __/ |   \ V /  __/ |   
|_| |_|\__|\__| .__/____/ \___|_|    \_/ \___|_|   
              |_|                            
    ''')
    print("[*] Connect To LinWin Http Server , Enter 'Ctrl + c' To Exit")
    #先判断连接地址
    print("(For example:  http://127.0.0.1:8888)")
    ConnectTo = input("Input A Adress To Connect: ")

    try:
        GetConnect = requests.get(ConnectTo+"?Do=Logon")
        GetConnectMes = GetConnect.text
        GetConnectMes = GetConnectMes.replace("\n","")
        if GetConnectMes == "successful":
            #链接成功
            print("[!] Connect To LinWin HttpServer Successful")
            #登录
            LogonToConsole(ConnectTo)
            return True
        else:
            print("[ERR] Do Not Connect To The LinWin HttpServer Console")
            Connect()
            return False
    except:
        #未能成功链接服务器
        print("[ERR] Do Not Connect To The LinWin HttpServer Console")
        time.sleep(1)
        Connect()
        return False

def RemoteRunCommand(RemoteIP,SystemCommand):
    try:
        RunResult = requests.get(RemoteIP+"?options="+SystemCommand+"&")
        print(RunResult.text)
        return True
    except:
        print('[*]Do Not Run Command On Target Computer')
        return False

def ShowRemoteStatus(USER,Remote):
    #获取与对方计算机的连接状态
    try:
        status = requests.get(Remote+"?welcome")
        print("")
        print("Connect Status: "+str(status.status_code))
        print('logon User: '+str(USER))
        print('Remote Computer: '+str(Remote))
        print("")
        return True
    except:
        print("[*] Internet Was Not Connected")
        return False

def SendMes(Mes,Remote,User,PWD):
    run = requests.get(Remote+"/?options="+Mes+"&?User="+User+"&&?Pwd="+PWD+"&&&")
    a = run.text
    return a

def RemoteConsole(User,Pwd,Remote):
    #远程控制台
    Command = input(str(User)+"@LinWinHttpServer_> ")
    if Command == "clear":
        os.system("clear")
        RemoteConsole(User,Pwd,Remote)
        return True
    if Command == "exit":
        exit()
    if Command == "info":
        print("""
 LinWin Connect Client: 1.0
 https://github.com/LinWin-Cloud
                """)
        RemoteConsole(User,Pwd,Remote)
        return True
    if Command == "show status":
        ShowRemoteStatus(User,Remote)
        RemoteConsole(User,Pwd,Remote)
        return True
    if Command == "set_port":
        port = input("[*] Set LinWin Http Server' Port: ")
        a = SendMes('set_port='+str(port),Remote,User,Pwd)
        print("[*] Message: "+str(a))
        RemoteConsole(User,Pwd,Remote)
        return True
    else:
        Run = SendMes(Command,Remote,User,Pwd)
        print(Run)
        RemoteConsole(User,Pwd,Remote)
        return True

    #return True

def LogonToConsole(ConnectTo):
    UsersName = input("Logon User: ")
    UsersPwd = input("Logon Password: ")
    LogonSend_Mes = requests.get(ConnectTo + "?Logon="+UsersName+"&Pwd="+UsersPwd+"&&")
    Mes = LogonSend_Mes.text
    Mes = Mes.replace("\n","")
    print ("[*]Get Message: "+Mes)
    if Mes == "Successful":
        #进入控制台
        os.system("clear")
        welcome = requests.get(ConnectTo+"?welcome")
        print(welcome.text)
        RemoteConsole(UsersName,UsersPwd,ConnectTo)
        return True
    else:
        LogonToConsole(ConnectTo)
        return False

Connect() #运行登录测试程序

