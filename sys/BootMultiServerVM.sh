#! /bin/bash

/usr/LinWinHttp/com.linwinMultiServer.package/build.sh
/usr/LinWinHttp/JRE/openJDK8/bin/java -jar /usr/LinWinHttp/com.linwinMultiServer.package/release/com.linwinMultiServer.package.jar $1 $2 $3
