#! /bin/bash

echo '[Info] Start Build All The Module And Resource Code For Linux'
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
echo '[OK] Build All The Java File To Class ... ...'
echo '[Info] Make And Build Jar File ... ...'

#将所有class文件编译jar包
echo '[Info] Build Server Module'
cd ./bin/server/
jar -cvfm ../../server/LinWinHttp.jar ../../server/MANIFEST.MF *.class 
echo '[Info] Build Proxy Module'
cd ../proxy
jar -cvfm ../../proxy/LinWin_Proxy.jar ../../proxy/MANIFEST.MF *.class
echo '[Info] Build Exit And Boot Module'
cd ../Sevice/
jar -cvfm ../../Sevice/Exit_Server.jar ../../Sevice/MANIFEST_EXIT.MF Exit_Server*.class
jar -cvfm ../../Sevice/ServerBoot.jar ../../Sevice/MANIFEST.MF ServerBoot*.class
cd ../FastServer/
echo '[Info] Build Fast WebSite Server Module'
jar -cvfm ../../FastServer/jar/FastServer.jar ../../FastServer/jar/MANIFEST.MF *.class
echo '\n[Info] Finish Building!'
