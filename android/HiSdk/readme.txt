##CI相关###
1.编译时去掉/HiSdk/src/com/baidu/im/sdkdemo的代码

2.project的root目录新建local.properties 内容如下(根据自己机器修改):
sdk.dir=D:\\app\\adt-bundle-windows-x86-20130522\\sdk
target=android-19

3.cd /d 到project的root目录
执行ant即可 产出jar(./build/hisdk.jar)


### 维护 ###

CI `http://ft.jenkins.baidu.com/job/bpit_hi_android_hisdk/`
ios 春涧 或者 休权
