# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

# JustDDNS

> 阿里云域名ddns动态域名解析 By Java and Python.

> https://github.com/ahviplc/JustDDNS

```markdown
实现动态域名解析DDNS Java版本
https://help.aliyun.com/document_detail/172994.html

具体实现动态域名解析DDNS Java版本
https://note.youdao.com/ynoteshare/index.html?id=c2fe233164c3756b38693d78f31347ed

ienai/AliYunDdns - 码云 - 开源中国 具体实现动态域名解析DDNS Java版本
https://gitee.com/ienai/AliYunDdns/tree/master

Collection: / =【https://mvnrepository.com/repos/ugent-genesis】
http://genesis.ugent.be/maven2/
http://genesis.ugent.be/maven2/net/jimmc/jshortcut/

GitHub - jimmc/jshortcut: Java/JNI interface to access Windows shortcuts
https://github.com/jimmc/jshortcut

GitHub - XAS-712/Aliyun_DDNS: 利用Aliyun的修改解析记录API/SDK写的DDNS脚本 Py版本
https://github.com/XAS-712/Aliyun_DDNS

python脚本实现ipv6的ddns功能-阿里云开发者社区
https://developer.aliyun.com/article/695773

Python实现阿里云域名DDNS支持ipv4和ipv6 - Zeruns's Blog
https://blog.zeruns.tech/archives/507.html

Python实现阿里云域名DDNS支持ipv4和ipv6-阿里云开发者社区
https://developer.aliyun.com/article/755182

Python实现阿里云域名DDNS支持ipv4和ipv6: 阿里云域名ddns动态域名解析Python源码，支持ipv4和ipv6
https://gitee.com/zeruns/aliddns_Python

GitHub - zeruns/-Python-aliddns_ipv4-ipv6: 阿里云域名ddns动态域名解析Python源码
https://github.com/zeruns/-Python-aliddns_ipv4-ipv6

python requirements文件生成与使用 - 萤huo虫 - 博客园
https://www.cnblogs.com/TF511/articles/10758953.html

maven 使用assembly 进行打包_zhongzunfa的专栏-CSDN博客
https://blog.csdn.net/zhongzunfa/article/details/82465939
```

# api

`访问我`
> http://localhost:8866/api/app

```markdown
5分钟搭建最简单springboot-demo_Kevin.yang专栏-CSDN博客
https://blog.csdn.net/ouyang111222/article/details/87862194

Spring Boot Web 开发@Controller @RestController 使用教程 - fishpro - 博客园
https://www.cnblogs.com/fishpro/p/spring-boot-study-restcontroller.html

666 - maven3 springboot 几种打包构建方法 assembly - 简书
https://www.jianshu.com/p/4f67f9e3f0a7

Spring cloud的Maven插件（一）：repackage目标 - 壮壮熊 - 博客园
https://www.cnblogs.com/zhouqinxiong/p/repackage.html

666666 -【Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]】报错解决办法- SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]_-CSDN博客
产生这个错误的原因是在产生日志信息的时候有两个桥接器，发生冲突导致error
因为是有两个桥接器发生冲突，我们只需要消除一个就可以了
https://blog.csdn.net/weixin_48991096/article/details/117756811
```

# Note

`一些链接`

```markdown
此Java版本 借鉴于
ienai/AliYunDdns
https://gitee.com/ienai/AliYunDdns
https://gitee.com/ienai/AliYunDdns/blob/master/pom.xml

backup-maven-settings.xml 备份 来自于
https://hub.fastgit.org/ahviplc/lc-es-api/blob/master/lc-es-api/pom.xml
```

`安装python所需的所有依赖：`

生成 requirementts.txt:    
执行命令
> pip freeze > requirements.txt

安装命令：
> pip install -r requriements.txt

`Py版本`
> JustAliYunDdnsPy/app.py

进入JustAliYunDdnsPy文件夹

执行命令
> python app.py

`去除 maven-assembly-plugin 插件`

```xml
  <!-- 使用 assembly 进行打包-->
<plugin>
    <artifactId>maven-assembly-plugin</artifactId>
    <configuration>
        <appendAssemblyId>false</appendAssemblyId>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <archive>
            <manifest>
                <!-- 此处指定main方法入口的class -->
                <mainClass>com.lc.ddns.AppRun</mainClass>
            </manifest>
        </archive>
    </configuration>
    <executions>
        <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
                <!--<goal>single</goal>-->
               <!-- <goal>assembly</goal>-->
            </goals>
        </execution>
    </executions>
</plugin>
```

# 注意点

## 1.0 jshortcut的注意点

maven包 jshortcut 不在maven中央仓库中 需要配置其他仓库如下
http://genesis.ugent.be

```xml
<!-- jshortcut-->
<!-- Note: this artifact is located at UGent repository (http://genesis.ugent.be/maven2/)-->
<!-- https://mvnrepository.com/repos/ugent-genesis-->
<!-- https://mvnrepository.com/artifact/net.jimmc/jshortcut -->
<!-- 需要上面的 repositories 中id为UGent的repository配置 和 maven配置文件配合 如backup-maven-settings.xml备份模板这样的配置-->
<dependency>
    <groupId>net.jimmc</groupId>
    <artifactId>jshortcut</artifactId>
    <version>0.4-oberzalek</version>
</dependency>
```

或者从网址  
http://genesis.ugent.be/maven2/net/jimmc/jshortcut/  
下载下来jar包 放入maven仓库本地目录 
【.m2\repository\net\jimmc\jshortcut\0.4-oberzalek】

## 2.0 todo

todo
