使用Eclipse maven插件添加本地资源仓库的方法：
在Run->Run Configuration 界面中， 新建一个Maven Build的运行项目，具体运行配置如下：
Name：任意填写
Base Directory：填入工程路径，如：D:/workspace/CMS
Goals：install:install-file
Profiles: 为空
Paremeter: 以添加lib/ojdbc6.jar为列，添加下面几项
groupId=oracle.jdbc
artifactId=ojdbc6
version=6.0
packaging=jar
file=lib/ojdbc6.jar

