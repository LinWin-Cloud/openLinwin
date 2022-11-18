#! /bin/bash

echo '[Info] Start Build All The Module And Resource Code For Linux'
sleep 0.2s
# 编译主HTTP服务器模块
echo '[Info] Build: HTTP Server Module'
javac -d ./bin/server/ ./com.linwinhttp.package/src/*.java
# 编译代理服务器模块
echo '[Info] Build: Proxy Server Module'
javac -d ./bin/proxy/ ./com.linwinproxy.package/src/*.java
# 编译服务器启动和退出程序
echo '[Info] Build: Exit And Boot File.'
javac -d ./bin/Sevice/ ./Sevice/*.java
#编译快速网站部署模块
echo '[Info] Build: Fast WebSite Server Modle'
javac -d ./bin/FastServer/ ./com.linwinFastServer.package/src/*.java
sleep 0.2s
echo '[OK] Build All The Java File To Class ... ...'
sleep 0.1s
echo '[Info] Make And Build Jar File ... ...'

#将所有class文件编译jar包
echo '[Info] Build Server Module'
jar -cvfm ./server/LinWinHttp.jar ./server/MANIFEST.MF ./bin/server/*.class 
echo '[Info] Build Proxy Module'
jar -cvfm ./proxy/LinWin_Proxy.jar ./proxy/MANIFEST.MF ./bin/proxy/*.class
echo '[Info] Build Exit And Boot Module'
jar -cvfm ./Sevice/Exit_Server.jar ./Sevice/MANIFEST_EXIT.MF ./bin/Sevice/Exit_Server.class
jar -cvfm ./Sevice/ServerBoot.jar ./Sevice/MANIFEST.MF ./bin/Sevice/ServerBoot.class
echo '[Info] Build Fast WebSite Server Module'
jar -cvfm ./FastServer/jar/FastServer.jar ./FastServer/jar/MANIFEST.MF ./bin/FastServer/*.class
echo '\n[Info] Finish Building!'
