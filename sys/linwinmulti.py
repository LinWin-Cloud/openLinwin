
import os
import sys

def main():

    cmd = sys.argv
    try:
        if cmd[1] == "-reload":
            os.system('cd /usr/LinWinHttp/sys/java/ && /usr/LinWinHttp/JRE/openJDK8/bin/java -jar Reload_Config.jar')
            return True
        if cmd[1] == "-version":
            o = open('../config/Version.txt')
            print(o.read())
            return True
    except:
        o = open('../config/help/linwinhttp_help.txt')
        r = o.read()
        print(str(r))
        return False

main()