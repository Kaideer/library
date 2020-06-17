# library
实用的library框架

--里面的微信登录纯属自己瞎捣鼓的，忽略--

这是一个简单实用的网络框架，里面封装了MVP使用Retrofit+RxJava+OkHttp请求网络，使用者只需要在Activity或者Fragment里面继承
BaseActivity或者BaseFragment通过requestBy...等一句话就可以在responseSuccess方法里面接收请求到的网络数据，通过 instanceof 关键字
来判断返回的数据是哪一个Bean类，因为当数据请求到之后会自动将数据解析成Bean类，以Object类型传到responseSuccess方法中的

其中包括各种工具类：Sp、TimeUtil（时间转换工具类）、网络判断工具类、动态权限申请工具类等...

其中封装EventBus，如果需要使用EventBus，则只需要在Activity或者Fragment中实现isRegisterEventBus方法返回true即可

如果需要发生网络请求的话 需要在首启动的Activity中配置一次您的BaseURL

只需配置一次就够了哦，但是一定要在请求数据之前配置

在首启动的Activity内在onCreate方法里面通过调用 MyApi.BaseUrl = ""  来配置你的BaseUrl，
也可以在Base类内的方法：initLayout或者initView方法都可以，

此外，需要您在AndroidManifest中设置网络权限和网络状态权限：

	<uses-permission android:name="android.permission.INTERNET"/>
    	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	
还需要您在AndroidManifest中的Application中设置一下  networkSecurityConfig 属性
具体配置请访问：

	https://www.cnblogs.com/renhui/p/9921790.html  
	https://blog.csdn.net/hewenlee/article/details/84097493  
	https://www.jianshu.com/p/b88cc9994174?tdsourcetag=s_pcqq_aiomsg  


接下来配置两个步骤使用library

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
  将 implementation 'com.github.Kaideer:library:1.0.8' 导入到您app的Module中的build.gradle即可
  如下所示：
  
      dependencies {
	              implementation 'com.github.Kaideer:library:1.0.8'
	      }
        
        
这样您就可以使用Activity或者Fragment通过继承BaseActivity或者BaseFragment使用library里的东西啦！        

本框架将持续更新，如有问题可联系：Kaideer@163.com
