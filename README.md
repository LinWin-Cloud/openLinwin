# 目前最新版本
V1.3 Community Build 2022.10.29

# LinWin Http Server源代码

LinWin Http Server，项目最早构建于2021年的1月份，最初使用Python开发，
<br />
然而在中途中开发失败。过了1年半后，LinWin Http Server重新进入了开发的
<br />
范畴，并改用Java作为默认的开发语言。其独特的设计、简单的配置、贴合中
<br />
国人的使用体验，成就不非凡。

使用Java开发，而且性能进过了很大程度的优化，对静态和动态的数据都有不
<br />
错的处理能力。

(安装后的操作)
#### 启动命令 linwinhttp -server_start 或者 linwinboot
#### 重启命令 linwinreboot
#### 关闭服务命令 linwinhttp -server_stop

# 新版本 LinWin Http优势
1. 更新异步：代替原本资源占用大的线程极限一换一操作。大大降低了服务器响应时间。增加了服务器处理性能
2. 配置文件跨域：在服务器目录下新建 strict_origin_when_cross_origin.txt 在文件的第一行写入要跨域的网站，服务器便会在提供服务的时候允许跨域。
3. 废弃Python脚本关闭服务，使用Java多线程关闭服务：使用了专门的Java文件来关闭HTTP和代理服务，基本上不会出现原版本的关闭错误问题。
4. 更新更多便捷命令：linwinboot 启动命令 ; linwinreboot 重启服务命令
5. 修复 v1.2 Commutily Build 2022.10.23 重大服务器错误BUG

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
