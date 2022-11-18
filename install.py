import os
import getpass
import time

def Install():
    print('\n[*] LinWin Http Server Dir: /usr/www/html\n')
    print('[!] LinWin Http Installer Will Overwrite writes to /usr/LinWinHttp.So you need save the important file in others')
    a = input('\n[?] LinWin Http Server Will Install In: /usr/LinWinHttp/ : [Y/N]')
    if a == "n" or a == "N":
        exit()
    else:
        def a():
            os.system('rm -fr /usr/LinWinHttp')
            os.system('mkdir /usr/LinWinHttp')
            os.system('mkdir /usr/www')
            os.system('mkdir /usr/www/html')
            os.system('cp -rf * /usr/LinWinHttp/')
            os.system('chmod +x /usr/LinWinHttp/Sevice/shell/*.sh')
            os.system('cp /usr/LinWinHttp/default/page/linwinhttp-index.html /usr/www/html') 
            os.system('cd /usr/LinWinHttp/nodeJS && unzip linux.zip')
            # os.system('cd /usr/LinWinHttp/Sevice/ && ../jre/bin/java -jar ServerBoot.jar')
            os.system('chmod 777 /usr/www/html')
            os.system('cp -f Sevice/systemd/linwinhttp.service /etc/systemd/system/')
            os.system('systemctl daemon-reload')
            os.system('systemctl linwinhttp start')
            os.system('cp /usr/LinWinHttp/sys/start_linwinhttp /etc/init.d')
            os.system('chmod +x /usr/LinWinHttp/* -R')
            os.system('cd /usr/LinWinHttp/JRE && unzip openJDK8.zip')
            os.system('/usr/LinWinHttp/sys/start_linwinhttp')

            os.system('cp /usr/LinWinHttp/sys/linwinhttp /bin/')
            os.system('cp /usr/LinWinHttp/sys/linwinboot /bin/')
            os.system('cp /usr/LinWinHttp/sys/linwinreboot /bin/')
            os.system('chmod +x /bin/linwinboot && chmod +x /bin/linwinreboot/')

            os.system('chmod +x /bin/linwinhttp')
            os.system('chmod +x /etc/rc.d/rcX.d/linwinhttp')
            os.system('systemctl enable linwinhttp.service')
            os.system('cp -f /usr/LinWinHttp/Sevice/desktop/linwinhttp.desktop /usr/share/applications')

            os.system('cp /usr/LinWinHttp/sys/linwindoc /bin/')
            os.system('chmod +x /bin/linwindoc')

            os.system('cp /usr/LinWinHttp/sys/status/linwinstatus /bin/')
            os.system('chmod +x /bin/linwinstatus')

            os.system('cp /usr/LinWinHttp/sys/fastserver/fastserver /bin/ && chmod +x /bin/fastserver')
            os.system('cp /usr/LinWinHttp/sys/linwinreload /bin/ && chmod +x /bin/linwinreload')

            os.system('cp /usr/LinWinHttp/sys/linwindoc /bin/ && chmod +x /bin/linwindoc')
            os.system('chmod 777 /usr/LinWinHttp/* -R')
            
            print('[!] Finish Install!Install Path: /usr/LinWinHttp/')
            print('[!] Input: \"linwinboot\" in the terminal,then start the HTTP Server.Default Port Is 8080')
        print('Install ... ...')
        if os.path.exists("/usr/LinWinHttp/") and os.path.isdir("/usr/LinWinHttp/"):
            a()
            return True
        else:
            os.mkdir('/usr/LinWinHttp/')
            a()
            return True


def main():

    user = getpass.getuser()
    print("User Now: "+user)
    if user != "root":
        print('[ERR]Must Be RUn As Root')
        exit()
    os.system("clear")                        
    print("""                                            
                                                         ___
                                                   _____  \ \  
     _     _    __        ___       ___           /   __\  \ \   
    | |   (_)_ _\ \      / (_)_ __  |__|         /_/\ \     \ \  
    | |   | | '_ \ \ /\ / /| | '_ \  |__|      __    \ \    / /  
    | |___| | | | \ V  V / | | | | |  |__|    /\ \    \ \  / /   
    |_____|_|_| |_|\_/\_/  |_|_| |_|   |__|  / /\ \____\ \/ /    
     _   _ _   _        ____                /_/  \______\ \/     
    | | | | |_| |_ _ __/ ___|  ___ _ ____   _____ _ __   \_\ 
    | |_| | __| __| '_ \___ \ / _ \ '__\ \ / / _ \ '__|   
    |  _  | |_| |_| |_) |__) |  __/ |   \ V /  __/ |      
    |_| |_|\__|\__| .__/____/ \___|_|    \_/ \___|_|      
                  |_|                                     
    [*] LinWinCloud Teams,UChat Teams,Yinghuo Teams.      
    [*] For Linux 2023
    """)

    print(" 1. Install LinWin Http Server (input '1')")
    print(" 2. Read Agreement Before Install LinWin Http Server (input '2')")
    print(" 3. View This Version")
    print(" 4. Exit installer (input 'exit')")
    print("")

    def console():
        options = input(" LinWin Http Server Installer_>  ")
    
        if options == "1":
            Install()
            return True
        if options == "2":
            os.system('cat ./default/agreement/agreement.txt')
            console()
            return True
        if options == "3":
            os.system("cat ./config/Version.txt")
            console()
            return True 
        if options == "exit":
            print('Bye!')
            exit()
        else:
            main()
            return True
    console()
main()

