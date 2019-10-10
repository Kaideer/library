# library
实用的library框架

如果需要发生网络请求的话 需要在首启动的Activity中配置一次您的BaseURL

在首启动的Activity内在onCreate方法里面通过调用 MyApi.BaseUrl = ""  来配置你的BaseUrl

两个步骤使用library

1：
  将maven { url 'https://jitpack.io' } 导入到项目的build.gradle中
  如下所示：

      allprojects {
		      repositories {
			      ...
			      maven { url 'https://jitpack.io' }
		      }
	      }
        
2：
  将 implementation 'com.github.Kaideer:library:1.0.4' 导入到您app的Module中
  如下所示：
  
      dependencies {
	              implementation 'com.github.Kaideer:library:1.0.4'
	      }
        
        
这样您就可以使用Activity或者Fragment通过继承BaseActivity或者BaseFragment使用library里的东西啦！        


