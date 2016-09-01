# MavenRepoDemo


###原文出处：[http://xuhao.tech/2016/08/30/maven.html](http://xuhao.tech/2016/08/30/maven.html)


**一、什么是Maven,Gradle?**
----------

**Maven** 是一个项目管理和自动构建工具。Maven 包集中存放的地方，就是 Maven 仓库。这些仓库，可以是放在本地，也可以放在某个远程服务器上。 可以是私有仓库，也可以是公开的。下开发用的库列表：

```
mavenCentral();
jcenter()
maven {
     url 'file:///Users/my-user-name/Documents/Android/repo/'
}
maven {
    url 'http://localhost:8081/nexus/content/repositories/releases/'
}
```

<!-- more -->
Android Studio Gradle 主要支持两个 Maven 中央库：mavenCentral 和 jcenter。

- mavenCentral 是最早的 maven 中央仓库
- jcenter 是 Android Studio 0.8 版本起的默认 maven 中央仓库
- 第三个是我的本机的仓库
- 第四个是笔者部署在内网服务器的私有仓库


**Gradle** 是一个基于Apache Ant和Apache Maven概念的项目自动化建构工具。它使用一种基于Groovy的特定领域语言来声明项目设置，而不是传统的XML


**二、使用Nexus搭建maven私服**
--------
### 1.Nexus  下载安装：
官网下载地址：http://www.sonatype.org/nexus/go/，我的开发环境是Windows，我下载的是Nexus Repository Manager OSS 2.xx下面的 All platforms	nexus-2.13.0-01-bundle.zip压缩文件。↓
![这里写图片描述](http://img.blog.csdn.net/20160830184129972)

### 2.Nexus 启动：
下载完成之后，解压后进入\nexus-2.1.2-bundle\nexus-2.1.2\bin\jsw\，根据操作系统类型选择文件夹，我选的是windows-x86-32文件夹，进入后可看到如下所示bat文件。
![这里写图片描述](http://img.blog.csdn.net/20160830185642150)

双击console-nexus.bat运行。再浏览器中输入[http://127.0.0.1:8081/nexus/，](http://127.0.0.1:8081/nexus/)出现图（2）所示就代表nexus已经启动成功了。

![这里写图片描述](http://img.blog.csdn.net/20160830190207761)
图(2)

 8081是默认的端口号，要修改端口号，进入\conf\打开nexus.properties文件，修改application-port属性值就可以了。
默认的用户名和密码分别是：admin和admin123。点击右上角的log in 登录后如图所示：
点击左侧的 repositories 查看现有的仓库列表：
![这里写图片描述](http://img.blog.csdn.net/20160830190723138)

### 3.Nexus仓库：

 **这里的仓库分了四种类型**

 1. hosted(宿主仓库):用来部署自己,第三方或者公共仓库的构件
 2. proxy(代理仓库):代理远程仓库
 3. virtual(虚拟仓库):默认提供了一个 Central M1虚拟仓库 用来将maven 2适配为maven 1
 4. group(仓库组):统一管理多个仓库


<font color=blue size=5>Public Repositories:  仓库组

**3rd party:** 无法从公共仓库获得的第三方发布版本的构件仓库

**Apache Snapshots:** 用了代理ApacheMaven仓库快照版本的构件仓库

**Central:** 用来代理maven中央仓库中发布版本构件的仓库

**Central M1 shadow:** 用于提供中央仓库中M1格式的发布版本的构件镜像仓库

**Codehaus Snapshots:** 用来代理CodehausMaven 仓库的快照版本构件的仓库

**Releases:** 用来部署管理内部的发布版本构件的宿主类型仓库

**Snapshots:**用来部署管理内部的快照版本构件的宿主类型仓库

### 4.建立Nexus宿主仓库
 新建一个内部仓库，步骤为Repositories –> Add –> Hosted Repository，在页面的下半部分输入框中填入Repository ID和Repository Name即可，另外把Deployment Policy设置为**Allow Redeploy**，点击save就创建完成了。这里我点击添加宿主类型的仓库，在仓库列表的下方会出现新增仓库的配置，如下所示：
 ![这里写图片描述](http://img.blog.csdn.net/20160830195149877)

建立好新的仓库之后需要配置一下相关账号信息.在安全选项下选择用户选项,可以看到三个默认的账号,分别是管理员账号,部署账号和Nexus账号.正常访问仓库内容的时候是不需要这三个账户的,一般也就是把部署账号暴露出去,方便仓库项目维护人员部署项目使用.所以这里可以用默认的Deployment账户(记得重置下密码).也可以新建一个账号来使用,新建的时候可以通过add role management来控制该账号的权限。
点击新建的仓库的url可以直接如今仓库的路劲，因为现在还没有部署项目，所以是空的仓库。


<font color=black  size=5>至此，搭建私服的maven仓库就已经完成，下面继续介绍Android 端在AS 上面的应用。


**三、上传库到Maven仓库**
----
 上传库到maven仓库有两种方式，我们先来介绍第一种：
1.  首先我们创建一个新的AndroidStudio 项目，然后新建一个module，选择Android Library。

![这里写图片描述](http://img.blog.csdn.net/20160830202410813)

然后，我们随便写一个功能供别人使用。例如我写一个ToastUtils：

![这里写图片描述](http://img.blog.csdn.net/20160830203501192)

然后RebuildProject生成依赖的arr包。


2.在MavenRepoDemo项目的根目录的build.gradle中配置刚刚建立的仓库：

```
allprojects {
    repositories {
        jcenter()
        maven{ url 'http://localhost:8081/nexus/content/repositories/releases/'}

    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

1.  在同目录下配置gradle.properties文件，定义通用属性，方便如果有多个库需要部署时，不需要修改每一个库中的配置：

```
#Maven仓库的URL
MAVEN_REPO_RELEASE_URL=http://localhost:8081/nexus/content/repositories/releases/
MAVEN_REPO_SNAPSHOT_URL=http://localhost:8081/nexus/content/repositories/snapshots/

#对应maven的GroupId的值
GROUP = common
#登录nexus ossde的用户名

NEXUS_USERNAME=admin
#登录nexus oss的密码

NEXUS_PASSWORD=admin123

# groupid
GROUP_ID = common

# type
TYPE = aar

# description
DESCRIPTION = This is Toast lib

```

这里的仓库我用的是Nexus 原有的仓库（你可以换成刚刚新建的仓库地址）。

1.   修改module对应的build.gradle文件，添加以下配置：

```
apply plugin: 'com.android.library'

apply plugin: 'maven'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.3"

    defaultConfig {
        minSdkVersion 19
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }

    lintOptions {
        abortOnError false
    }

}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.4.0'
}

uploadArchives {
    configuration = configurations.archives
    repositories {
        mavenDeployer {
            snapshotRepository(url: MAVEN_REPO_SNAPSHOT_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }
            repository(url: MAVEN_REPO_RELEASE_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }
            pom.project {
                version '1.0.0'
                artifactId 'toastutils-lib'
                groupId GROUP_ID
                packaging TYPE
                description DESCRIPTION
            }
        }
    }
}

artifacts {
    archives file('toastutils.aar')
}



```

1.  在as右边栏，找到Gradle打开如下：
    ![这里写图片描述](http://img.blog.csdn.net/20160830203919352)

然后双击uploadArchives，编译脚本并上传arr文件到私有仓库，最后在控制台可以看到日志是否上传成功。

可以去仓库查看到刚刚上传的库文件：


![这里写图片描述](http://img.blog.csdn.net/20160830204825029)

第二种，就是直接通过Nexus直接上传，这种就不详细说了，有兴趣的自己去研究下吧！ 嘿嘿


**四、在项目中应用**
----
1.  在项目的根目录build.gradle配置如下：

![这里写图片描述](http://img.blog.csdn.net/20160830205442120)

1.  在app目录下的build.gradle配置如下：

![这里写图片描述](http://img.blog.csdn.net/20160830205622888)

 这样我们就完工了。在项目中调用我们库了，别人按照上面的配置就可以引用库使用了。

<font color=black  size=5>**附上Demo的GitHub项目源码**：[MavenRepoDemo](https://github.com/git-xuhao/MavenRepoDemo)

对于频繁更新的子项目是否适合采用这种方式。因为每次变动都需要上传，而主项目在引用该AAR的时候则需要每次都去检查是否更新， 这会使得编译时间大大增加，有了这个maven库，就不用那么麻烦了。


**想了解更多有关的资料：**
---

 [Nexus私服使Maven更加强大](http://blog.csdn.net/liujiahan629629/article/details/39272321)

 [拥抱的androidStudio 系列文章 博客比较全](http://kvh.io/tags/EmbraceAndroidStudio/)


# License

    Copyright 2016 Xuhao
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
