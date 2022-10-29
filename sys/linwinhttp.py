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
            os.system("cd /usr/LinWinHttp/Sevice/ && java -jar Exit_Server.jar")
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
