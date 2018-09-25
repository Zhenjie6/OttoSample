package org.study.ottosample;

import com.squareup.otto.Bus;


/*
* 1. 添加OTTO框架到自己的项目中：build.gradle 添加OTTO的依赖
* 2. 创建一个单例，来给APP中所有的Activity或者Fragment提供一个Bus
* 3. register,unregister 事件总线
* 4. 在要发送事件的地方调用bus.post(Event)
* 5. 接收的地方定义订阅的函数@Subscribe ...
* */
public class BusProvider {
    private final static Bus bus = new Bus();
    public static Bus getInstance() {
        return bus;
    }

    private BusProvider()
    {}
}
