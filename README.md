# NewModule
我的基础框架代码

# 框架说明

## 框架概述：

框架利用第三方Arouter，采用类似组件化的伪组件化的开发思路，结合着mvp的设计模式，重要的一些网络访问框架、图片加载框架、list框架等都采用的是目前比较大众和主流的第三方框架，稳定性相对比较高。

## 框架展示

<img src="http://pa97zk4aq.bkt.clouddn.com/2018-08-04-094223.png" width="1000px" />

## 关于mvp

在mvp中View接口中定义一些常用的方法，以及一些需要实现的方法就可以了，通过回调内容，把控件赋值，数据展示等还是放回在activity中操作，presenter只需要将对应的实体或者数据给activity就好了，activity怎么展示，不用管，不关我的事情，做到各行各职。

### Presenter

MVP模式中，将P单独拿出封装为一个P层，除了减少MVC中C层的负荷，同时也是希望能将P层在功能类似的情况下进行复用。

presenter控制着数据的访问。presenter依赖于对应的View，


# 项目框架搭建用到的第三方

## 工具类：

### AndroidUtilCode

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-012651.png)

#### Git地址：

https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md

#### 简介：

这是一个很棒的工具类，基本上封装了大部分我们能用到的工具类。如果项目多的话能用一套工具箱开发的话，会很省力气。

它的功能包括：1、Activity相关（Activity的管理）2、App相关（App的当前状态和信息获取）3、栏相关（设置和获取状态栏和导航栏的相关信息和状态）4、缓存相关（图片String的缓存）5、清除相关（缓存、文件数据库都可以用）6、关闭相关（IO的关闭）7、转化相关（图片、流、字符、像素的转换）8、崩溃相关（Crash）9、设备相关（获取设备相关信息）10、编码相关（Base64和URL、Html的解码）11、加密相关（各种加密方法）12、文件相关（增删改查校验等）13、Fragment相关（Fragment的相关操作和信息获取）14、图片相关（图片的转化、裁边等）15、意图相关16、键盘相关（键盘的显示影藏等相关操作）17、日志相关（打印日志之类的）18、网络相关（判断当前网络状态）19、对象相关（判断对象状态和获取对象信息）20、权限相关（权限的请求和设置）21、手机相关（获取手机运行商信息和打电话等功能）22、进程相关（杀死服务进程）23、反射相关（获取反射类设置字段等）24、正则相关（简单的正则操作）25、屏幕相关（获取、设置屏幕相关信息）26、SD卡相关（SD卡路径和状态）27、服务相关（启动、停止、解绑服务）

28、Shell相关（是否在root下运行）29、尺寸相关（尺寸的转化、视图尺寸的获取）30、SnackBar相关（设置和显示）31、SpannableString相关（设置Spannable相关状态）32、SP相关（数据在sp中的存入和取出）33、字符串相关（字符串的转化）34、时间相关（时间的转化和判断）35、吐司相关（吐司的展示和背景）36、压缩相关（压缩为zip文件）

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-012941.png)

### Gson

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-012954.png)

#### Git地址：

<https://github.com/google/gson>

官方api文档：<http://www.javadoc.io/doc/com.google.code.gson/gson/2.8.2>

用户引导手册：

https://github.com/google/gson/blob/master/UserGuide.md

 

#### 简介：

我们比较常用的Json格式转化工具。配合的还有AS上的Gson插件，用它来解析json格式数据、创建对象效率不要太高~

## 图片加载

### Glide

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013001.png)

#### Git地址：

<https://github.com/bumptech/glide>

官方api使用文档：

<https://muyangmin.github.io/glide-docs-cn/>

#### 简介：

作为图片的加载工具，性能相对picasso来说是很棒的。可以在加载图片时实现1、图片的缓存2、图片的剪裁3、图片加载动画4、图片的压缩等。自定义缓存和自定义的图片转化是很棒的功能

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013023.png)

 

## 网络:

### retrofit

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013027.png)

#### Git地址：

<https://github.com/square/retrofit>

官方api使用文档：

https://square.github.io/retrofit/

#### 简介：

retrofit是一个很棒的相应式网络访问框架，它的底层实际网络访问采用的依然是okhttp。不过已经封装的很不错了，重点是他结合rxJava使用效果更棒。

所以正确的使用姿势是Retrofit+OkHttp+RxAndroid+Gson

 

### okhttp

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013031.png)

#### git地址：

<https://github.com/square/okhttp>

官方api使用文档：

https://github.com/square/okhttp/wiki/Calls

#### 简介：

相对于传统的httpClient，OkHttp在性能上有很大提升，并且提供了很多定制化的方法从而实现我们的需求。

 

## 框架:

### RxAndroid：

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013038.png)

#### Git地址：

https://github.com/ReactiveX/RxAndroid

#### 简介：

它是一个异步库，这个异步库可以让我们用非常简洁的代码来处理复杂数据流或者事件。Observable用户发送消息，而Observer用于消费消息，在实际开发中，我们更多的是选择Observer的一个子类Subscriber来消费消息。在消息发送的过程中，Observable可以发送任意数量任意类型的消息（甚至一个将一个触摸事件当作一个消息发出），当Observable所发送的消息被成功处理或者消息出错时，一个流程结束。Observable会用它的每一个Subscriber（观察者）的onNext方法进行消息处理，在消息成功处理后以onComplete()方法结束流程，如果消息在处理的过程中出现了任何异常，则以onError()方法结束流程。

 

 

### ARouter

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013047.png)

#### git地址：

<https://github.com/alibaba/ARouter>

 

#### 简介：

这是一个很棒的路由跳转框架，解决了当项目量级变大，我们不得不采用组件化开发的时候，用它来做组件化界面的跳转和数据的传递再好不过了。通过它可以暂且构架出一个类似组件化的项目。

 

## 界面相关:

### BaseRecyclerViewAdapterHelper

![img](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013051.png)

#### Git地址：

<https://github.com/CymChad/BaseRecyclerViewAdapterHelper>

官方api地址：

https://www.jianshu.com/p/b343fcff51b0

 

#### 简介:

它是一个针对recycleView封装的adapter的。相比原来的adapter能大量节约开发时间。

它支持：

1、item和item子控件的点击长按；2、item的夹杂动画，3、添加头部和尾部4、上拉加载、下拉刷新5、分组和多布局6、item的拖拽和滑动删除等

## ButterKnif

### Git地址：

https://github.com/JakeWharton/butterknife

### 简介：

这个库的知名程度就不说了，JakeWharton大神也是我一直的偶像~很多牛逼的库都出自他手。这个框架，主要的功能是简化View的获取方式，通过注解的方式直接获取View对象。

## EventBus

### Git地址

https://github.com/greenrobot/EventBus

### 简介：

它是Android优化中很好的事件总线，他简化Android的activity，fragment，thread，Service之间通信，代码体积小，质量更高，他用的是事件订阅的思想：

![image-20180817153313863](http://pa97zk4aq.bkt.clouddn.com/2018-08-17-073317.png)





它的用处很多，我们在请求用户数据之后，去更新用户信息，可能更新的页面有点多，那么数据刷新就很麻烦，不知道什么时候需要去刷新用户数据，有了发布和订阅事件之后就能轻松地在数据改变之后去发布更新消息给订阅者。

## 

## FlycoTabLayout

### Git地址

https://github.com/H07000223/FlycoTabLayout/blob/master/README_CN.md

### 简介

比较好用的多样式，定制性很强的TabLayout，能够满足目前我们大部分对滑动标题的需求。

![](http://pa97zk4aq.bkt.clouddn.com/2018-07-23-155354.png)





## Tray

### Git地址

https://github.com/grandcentrix/tray

### 简介

对于一般的sp来说跨进程时不安全的。所以这里我们采用Tray来取代sp实现简单数据的持久化存储。


## AndroidRichText

### Git地址

https://github.com/jesson1989/AndroidRichText

### 简介

用来做富文本显示，内部内容容器是一个spannable，支持图片的点击。



## SmartRefreshLayout

### Git地址

https://github.com/scwang90/SmartRefreshLayout

使用文档：

https://github.com/scwang90/SmartRefreshLayout/blob/master/art/md_property.md

### 简介：

这个第三方主要用于下拉加载和上拉刷新的配置，配合recycleView使用

PullToRefresh支持；

## ImagePicker

### Git地址

https://github.com/jeasonlzy/ImagePicker

### 简介：

 Android自定义相册，完全仿微信UI，实现了拍照、图片选择（单选/多选）、 裁剪 、旋转、等功能。

## EasyPermission

### Git地址：

https://github.com/googlesamples/easypermissions

### 描述：

在Android6.0之后，对一些比较重要的特殊权限（六大权限），在使用前都需要去请求权限。这里我们就用谷歌力荐的EasyPermission做对应的权限申请。这个框架封装好了请求所需要的权限请求和对应的请求界面，所以整体来说还是比较好用的。

## Android-PickerView

### Git地址

https://github.com/Bigkoo/Android-PickerView

### 描述：

是一款仿iOS的PickerView控件，带有3D圆弧效果，并封装了时间选择和选项选择这两种选择器。

![image-20180808143955621](http://pa97zk4aq.bkt.clouddn.com/2018-08-08-063958.png)



## Banner

### Git地址

https://github.com/youth5201314/banner

### 描述

现在的绝大数app都有banner界面，实现循环播放多个广告图片和手动滑动循环等功能。因为ViewPager并不支持循环翻页， 所以要实现循环还得需要自己去动手，我就把项目中的控件剔了出来，希望大家觉得有用。目前框架可以进行不同样式、不同动画设置， 以及完善的api方法能满足大部分的需求了。

![image-20180808144221928](http://pa97zk4aq.bkt.clouddn.com/2018-08-08-064223.png)












# 封装好的一些模块

## ActivityStackManager

### 描述：

用来控制Activity的栈，我们可以直接用来将Activity出栈，或者关闭某个Activity之前的所有栈。

### 使用场景：

在修改经过多个步骤修改完数据之后需要将前面所有的activity清除掉的时候，就比较爽了。



## BottomUpSelectDialog

### 描述

从底部弹起的选择窗

### 使用场景

![](http://pa97zk4aq.bkt.clouddn.com/2018-07-24-065901.png)





## PopupWindowAlert

### 描述：

一个现实在中间的pop提示弹窗，支持，文字，图片，html格式数据的展示。

### 使用场景

![](http://pa97zk4aq.bkt.clouddn.com/2018-07-26-013521.png)



## SwipeLayout

### 描述：

一个左滑的view，因为设置了滑动，所以点击事件失效了，这块可以去优化下。

### 使用场景：

![](http://pa97zk4aq.bkt.clouddn.com/2018-07-24-065957.png)

## ResUtils

### 描述：

在AndroidUtils中我们可以拿到大部分我们需要的封装好的android的方法，但是有一些小的东西AndroidUtils没有提供，我们就自己封装到这个工具类中

### 使用场景：

AndroidUtils不能触及的工具类的调用

## LoadingDialog

### 描述：

这是我们加载数据的时候的loading，里面放了我们loading时候的样式和展示方式

### 使用场景：

加载数据之类的需要等待的界面



## UserInfoManager

### 描述：

获取用户信息相关的对象，用于获取、保存本地的用户信息。（本来想做刷新的，但是碍于所在模块是在比较底层的core模块，为了不破坏模块的功能化区，所以数据刷新只能在使用的时候外部做了）

### 使用场景：

在进行相关的用户数据操作的时候使用

## LocalSpDataManager

### 描述：

用于存储本地sp的对象，存储对象用的是Tray。

### 使用场景：

以sp形式，存储在本地的信息。


## CommonShapeButton

### 描述：

在使用按钮的时候常常因为按钮的圆角，边框样式不得不去定义不同的shape，从而增加res中shape的数量，所以，引入这个控件，让按钮的样式定义更加方便。

git地址：https://github.com/michaelxs/Android-CommonShapeButton?utm_source=androidweekly.io&utm_medium=website

### 使用场景

![](http://pa97zk4aq.bkt.clouddn.com/2018-08-08-091255.png)


## AutoLineGroup

### 描述：

很多控件需要自动换行或者几等分一行，我们这个控件就解决了均分和换行的问题。不必在再去依靠recycleView写一些本来很简单的页面。

### 使用场景

![屏幕快照 2018-08-21 18.28.38](http://pa97zk4aq.bkt.clouddn.com/2018-08-21-102902.png)

![](http://pa97zk4aq.bkt.clouddn.com/2018-08-21-102926.png)











 

 

 

 

 

 

 

 
