# crash quickstart

中文 


## 目录

- [简介](#简介)
- [环境要求](#环境要求)
- [快速入门](#快速入门)
- [示例代码](#示例代码) 
- [技术支持](#技术支持)  


## 简介

认证服务提供了云侧服务和SDK，可以帮助开发者为应用快速构建安全可靠的用户认证系统，以便应用可以对用户进行身份认证。AppGallery Connect认证服务支持多种认证方式，并与其他Serverless服务紧密集成，开发者可以通过简单的规则定义来保护用户的数据安全。


## 环境要求

* 在使用codelab demo之前，开发者需要配置好Harmony开发环境
* 一台Harmony设备或者模拟器 

## 快速入门

在运行quickstart前，您需要
1、如果没有华为开发者联盟帐号，需要先[注册账号](https://developer.huawei.com/consumer/en/doc/start/registration-and-verification-0000001053628148)并通过实名认证。
2、使用申请的帐号登录[AppGallery Connect](https://developer.huawei.com/consumer/cn/doc/development/AppGallery-connect-Guides/agc-get-started)网站创建应用，软件包类型选择“APP(HarmonyOS)”。
3、启动认证方式：
      3.1 登录AppGallery Connect，点击“我的应用”，点击需要启动认证服务的应用所属的产品。
      3.2 点击“我的项目”页签，导航选择“构建 > 认证服务”。如果首次使用认证服务，请点击“立即开通”开通服务。
      3.3 点击需要启动的认证方式所在行的“启用”。
      3.4 如果有些认证方式需要填写相关信息，请参照开发指南完成信息得配置。。
4、在AppGallery Connect 网站上下载agconnect-services.json 文件，并拷贝agconnect-services.json文件到entry根目录下。

## 示例代码

codelab demo支持验证码获取、登录等功能

示例代码：src\main\java\com\huawei\agconnect\auth\slice\MainAbilitySlice.java

## 技术支持

如果您对使用AppGallery Connect示例代码有疑问，请尝试：
- 开发过程遇到问题上[Stack Overflow](https://stackoverflow.com/users/14194729/appgallery-connect)，在`appgallery`标签下提问，有华为研发专家在线一对一解决您的问题。
- 到[华为开发者论坛](https://developer.huawei.com/consumer/cn/forum/blockdisplay?fid=18) AppGallery Connect板块与其他开发者进行交流。

如果您在尝试示例代码中遇到问题，请向仓库提交[issue](https://github.com/AppGalleryConnect/agc-demos/issues)，也欢迎您提交[Pull Request](https://github.com/AppGalleryConnect/agc-demos/pulls)。


