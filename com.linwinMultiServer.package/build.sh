#! /bin/bash

/usr/LinWinHttp/JRE/openJDK8/bin/javac -d /usr/LinWinHttp/bin/multiServer/ /usr/LinWinHttp/com.linwinMultiServer.package/src/*.java
cd /usr/LinWinHttp/bin/linwinServer/
/usr/LinWinHttp/JRE/openJDK8/bin/jar -cvfm /usr/LinWinHttp/com.linwinMultiServer.package/release/com.linwinMultiServer.package.jar /usr/LinWinHttp/com.linwinMultiServer.package/release/MANIFEST.MF *.class