
import os
import sys

def main():

    cmd = sys.argv
    try:
        if cmd[1] == "version":
            o = open('/usr/LinWinHttp/config/Version.txt')
            print(o.read())
            return True
        if cmd[1] == "start":
            os.system("cd /usr/LinWinHttp/com.linwinMultiServer.package/release && /usr/LinWinHttp/JRE/openJDK8/bin/java -jar BootMultiServer.jar")
            return True
        if cmd[1] == "help":
            os.system("cat /usr/LinWinHttp/config/help/openLinwin_Multi")
            return True
        if cmd[1] == "proxy_start":
            os.system("cd /usr/LinWinHttp/com.openProxy.Service/release && /usr/LinWinHttp/JRE/openJDK8/bin/java -jar boot.jar")
            return True
    except:
        o = open('/usr/LinWinHttp/config/help/openLinwin_Multi')
        r = o.read()
        print(str(r))
        return False

main()
