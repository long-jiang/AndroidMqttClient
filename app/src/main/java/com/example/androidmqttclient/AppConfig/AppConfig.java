package com.example.androidmqttclient.AppConfig;

/**
 * 所有关于该程序的配置参数
 */
 public class AppConfig {
  //连接M Q T T服务器参数
    public static final String userName = "test2";//登录M Q T T服务器的账号密码 目前不支持匿名登录 匿名情况下会直接拒绝登录
    public static final String password = "123456";
    public static final String closeTopic = "android_server";
    public static final String URL = "tcp://121.5.238.148:1883";//M Q T T服务器地址
    public static final String clientId = "android_control_system";//该客服端ID

    /*  必须订阅server的所有消息
     1、更新
     2、获取用户自己的信息
     3、获取所有与该用户相关的设备
     4、获取所有的布局文件
     */

    /*
      后期尝试出现异常的情况都上传到服务器端


     通过导入界面的方式生成界面
     后期将界面的数据上传到服务器端保存下来  实现用户与界面绑定

     流程：
          添加一个设备 请求服务器生成一个KEY 添加界面布局文件  根据布局文件生成界面 生成一个设备  --->后期将数据打包上传到服务器
          {设备ID,布局文件}
     */


}
