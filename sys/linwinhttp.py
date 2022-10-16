# 作者: LinWinCloud
# 这个是一个命令行的管理工具

import os
import sys

def main():

    cmd = sys.argv
    try:
        if cmd[1] == "-version":
            o = open('../config/Version.txt')
            print(o.read())
            return True
        if cmd[1] == "-server_start":
            print(' [*] Boot Server')
            os.system("cd ../Sevice && ../jre/bin/java -jar ServerBoot.jar")
            return True
        if cmd[1] == "-server_stop":
            print(" [*] Stop Http Server , Stop Proxy Server")
            a = open('../config/Server.cfg')
            b = open("../config/proxy.cfg")
            c = open('../config/Admin.cfg')
            while True:
                line = a.readline()
                if not line:
                    break
                if "Port: " in line:
                    Port = line[line.index("Port: ")+len("Port: "):line.index(';1')]
                    import requests as requests
                    while True:
                        line1 = c.readline()
                        if not line1:
                            break
                        if "User: " in line1:
                            User = line1[line1.index("User: ")+len("User: "):line1.index(";")]
                            Pwd = line1[line1.index(";Pwd: ")+len(";Pwd: "):len(line1)]
                            
                            try:
                                r = requests.get('http://127.0.0.1:'+Port+"/linwin_http_boot_web_1234567890_qwertyuiop="+str(User)+";1234567890>>"+str(Pwd))
                                
                                while True:
                                    line2 = b.readline()
                                    if not line2:
                                        break
                                    if "Port: " in line2:
                                        Port1 = line2[line2.index("Port: ")+len("Port: "):line2.index(';')]
                                        try:
                                            t = requests.get('http://127.0.0.1:'+Port1+"/linwin_http_boot_web_1234567890_qwertyuiop="+str(User)+";1234567890>>"+str(Pwd))
                                            print(' [*] Proxy Server Message: '+t.text)
                                        except:
                                            print(' [*] Proxy Server Not Start')
                                            return False

                                print(" [*] Http Server Message: "+r.text)
                            except:
                                print(' [ERR] Http Server Not Start')
                                return False
                            break        
                    break
                return True
        if cmd[1] == "-log_view":
            c = open('../config/log.cfg')
            c = c.readline()
            os.system('cat '+c+"/*.log")
            return True
        if cmd[1] == "-agreement_gui":
            os.system('cd ../default/agreement/ && ../../jre/bin/java -jar agreement_en.jar')
            return True
        if cmd[1] == "-agreement_cmd":
            os.system('cd ../default/agreement/ && cat agreement.txt')
            return True
    except:
        o = open('../config/help/linwinhttp_help.txt')
        r = o.read()
        print(str(r))
        return False

main()
