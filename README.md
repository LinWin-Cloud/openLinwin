# 目前最新版本
V2.2 Community Build 2022.12.4

# LinWin Http Server

(安装后的操作)
### 产品网站入口: https://linwin-cloud.github.io/linwin-http-server/
### 文档入口: https://gitee.com//LinWin-Cloud/openLinwin/wiki/

# 多服务器模块命令
多服务器模块已经代替了原本的代理服务器和主服务器模块，新的多服务器模块是设计更加优秀的而且效率更高速度更快

1. Enter: linwinmutli start                 Start the linwin Mutli Server
2. Enter: linwinmutli proxy_start           Start the linwin Multi Proxy Service
3. Enter: linwinmulti help                  Get the help
4. Enter: linwinmulti version               view the version information

## 更新与模块信息
1. <a href='./moudule.md'>模块信息</a>
2. <a href='./update.md'>版本更新信息</a>

## 如何配置多个网站在服务器上
在 /usr/LinWinHttp/config/Multi-Server/ 目录下,你可以随意命名一个新的配
<br />
置文件，但是拓展名必须是 '.json',文件内容如下:
	{
    		"Server-Port" : "8080", //Server-Port选项必须要,后面的这个8080是默认的服务端口，可以改称其他的.
    		"Index" : "/usr/www/html" //Index选项也必须要，用于配置服务目录.
		"HttpVersion" : "1.1" //Http协议版本，推荐1.1或者2.0
	}
## 如何配置多个代理网站在服务器上
代理服务器指的是在客户端与真实服务器之间一个代理的中继服务器，多用于
隐藏真实服务器IP，提高访问效率和真实服务器安全以及隐私浏览等作用，对
用户和服务器都很隐私安全

在 /usr/LinWinHttp/config/Multi-Proxy/ 目录下,你可以随意命名一个新的配
<br />
置文件，但是拓展名必须是 '.json',文件内容如下:
        {
                "Server-Port" : "8080", //Server-Port选项必须要,后面的这个8080是默认的服务
端口，可以改称其他的.
                "ProxyUrl" : "http://127.0.0.1:80/" //ProxyUrl选项用于设置代理网址
        }


# 新版本 LinWin Http 优势
1. 多服务器，多线程并行运算和服务，效率更高，效果更好，服务更加便捷
	2.0版本服务效率比较1.0版本提升了70%
	2.0版本服务效率比较1.3版本提升了45%
	2.0版本服务效率比较1.7版本提升了50%
2. 修复了启动错误BUG，比 1.x 稳定了很多.
3. 加入网站攻击和测试模块: 在命令行内使用 wsattacker 命令便可以策划一次命令行下的攻击和测试.
4. 优化了编译结构和脚本
5. 对各个服务器的兼容非常好: 源代码一份，各个平台的Linux都支持(除了android). 
6. 更新多代理服务器模块，比旧版本的更加稳定高效，速度非常快，可同时部署多个代理网站
7. 优化了目录结构，使得代码和工作目录更加简洁和完善

# 使用LinWin HttpServer必须的依赖项目
### 1. python3 
    LinWin HttpServer同样需要使用python3来管理和运行部分的服务
### 2. linux运行环境 & windows运行环境
    LinWin Http Server的源代码可以运行在任何的平台，但是其中部分的命令行
    安装脚本、启动命令行并不支持windows，而是单单为Linux设计；但是同样可以
    在windows上运行该应用。
### 3. jdk >= 1.8
    本产品默认不内置jdk或者jre，所以必须要在目标计算机安装jdk并且配置好环境才
    能够使用。
# LinWin Http Server的安全性
LinWin Http Server一直把服务器安全放在第一位，不仅仅采用了java这种安全的
开发语言，而且内置了很多的安全策略以及服务器的安全访问，实现了用户的简单配置
、严密防护。

### XSS防护选项
LinWin Http Server支持通过配置文件启动xss防护，服务器会自动判断来自前端
发送的数据，一旦发现嵌入了非法的脚本、标签、内容便会及时停止用户的下一步访问

### SQL注入防护选项
LinWin Http Server注意到了很多前端网站会被其他恶意的攻击者发送非法的sql语句
来被盗取服务器，LinWin一直非常关注这点，于是加入了sql防护，默认是打开的，一旦
发送过来的数据含有非法sqlsql语句会停止用户的下一步访问操作。

### 资源访问安全性
在不经意间，很多开发者可能会将一些重要的文件、文件夹、日志和配置文件等放在web服务目录
下，这可能会导致服务器不该被访问的资源被盗取，LinWin Http Server默认的策略内包含了
很多的资源禁止访问。

# LinWin Http Server的灵活性
### 资源访问灵活
    LinWin Http Server支持资源在服务器目录外的访问（每次单个资源），大大提升了开
    发的快捷性质。
### 安装灵活
    安装方便简洁，毫不拖泥带水，从安装到完成不到10秒，快速部署快速开发。

    >>> 安装教程
    git clone https://gitee.com/LinWin-Cloud/linwin-http-server #克隆源代码到本地
    cd linwin-http-server                                       #进入源代码目录
    python3 install.py                                          #启动安装脚本
    (按1是安装，LinWinHttp将会安装在: /usr/LinWinHttp)
    (按2是产看协议)
    (按'exit'是退出安装)


    》》》
    推荐 >> 后端:LinWin Http Server + 数据库:Mysql + 前端:ux-js
    《《《
### 部署灵活
    LinWin Http Server默认的服务目录在 /usr/www/html ，在开发的时候可以把文件直接都放在
    这个目录，非常简单高效

# 什么样类型的网站适合 LinWin Http Server
LinWin Http Server主要设计用于中小型网站平台，这这其中LinWin Http Server有
出色的表现。
1. 博客类网站：LinWin Http对于综合的网页资源请求非常迅速，非常适合此类网站
2. 产品官网类网站: 对于静态网页的托管LinWin Http得心应手
3. 管理类网站: LinWin Http凭借着友好、灵活的特点，不仅仅可以提供静态服务，动态服务也是
一大亮点
4. 测试网站: LinWin Http安装方便、部署快速非常适合此类
5. Web小程序网站: 基于java的稳定和高效率，web小程序网站再也合适不过

# 说明
LinWin Http Server 默认并不是开机自启动的，但是有专门的脚本启动LinWinHttp，位于
/usr/LinWinHttp/sys/start_linwinhttp（安装过后的位置），可根据自己的需要添加自启动

# 贡献
1. LinWin Http Server 是一个开源软件，旨在通过大家一起来修改完善这个产品的源代码，
所以免费。

# 开发者、团队
1. LinWin Cloud 
2. 萤火科技团队
3. 优信团队

# 源代码移植问题


    除了安装程序、部分的配置文件（修改即可），其他的源代码都可以运行在windows和Mac操作
    系统上面
