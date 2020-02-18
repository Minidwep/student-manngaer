# 师生基本信息管理系统-后端

## 安装配置说明
1. 如果系统中已安装MySQL 5.x 版本且正常运行，则跳过此步骤，否则下载并安装MySQL 5.7。

2. 使用如下命令创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS StudentManager DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
```

3. 如系统中已安装Git，运行如下命令下载源代码：
```
git clone https://git.dev.tencent.com/h_li/student-manager-backend.git
```
否则，在浏览器中打开[项目主页](https://dev.tencent.com/u/h_li/p/student-manager-backend/git)，点击下载按钮下载源代码。

4. 如果已配置过Maven的阿里源，则跳过些步骤。检查用户目录下是否存在".m2/settings.xml"文件，如果没有则新建该文件。编辑该文件，加入如下代码：
```xml
    <mirrors>  
      <mirror>  
        <id>alimaven</id>  
        <name>aliyun maven</name>  
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
        <mirrorOf>central</mirrorOf>  
      </mirror>  
    </mirrors>
```

5. 编辑“src/main/resources/application.properties”，找到下面的代码，并使用你自己的信息替换
```
    spring.datasource.password=你的MySQL root账户密码
      
    spring.mail.username=你的163邮箱地址
    spring.mail.password=你的163邮箱密码
```

6. 在命令行窗口进入源代码目录，运行如下命令启动后端项目。
```bash
gradlew.bat bootRun
```
第一次运行会比较慢，等待几分钟，如果日志信息没有错误并且最后一行见到“> :bootRun”提示符表示项目已成功运行。

7. 将项目导入Eclipse  
启动Eclipse后，执行菜单“File | Import”，在向导窗口中选择“Gradle | Existing Gradle Project”，点击两次“Next”，选择项目目录，点击“Finish”。