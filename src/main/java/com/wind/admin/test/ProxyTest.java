package com.wind.admin.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 抽象角色
 */
interface Subject {
    public void doSomething();
}

/**
 * 真实角色
 */
class RealSubject implements Subject {
    public void doSomething() {
        System.out.println("call doSomething()");
    }
}

/**
 * 代理角色: 为其他对象提供一种代理以控制对这个对象的访问。
 */
class SubjectProxy implements Subject {
    Subject subimpl = new RealSubject();

    public void doSomething() {
        System.out.println("before"); // 调用目标对象之前可以做相关操作
        subimpl.doSomething();
        System.out.println("after");// 调用目标对象之后可以做相关操作
    }
}

/**
 * 动态代理:  绑定委托对象，并返回代理类.
 */
class ProxyHandler implements InvocationHandler {
    private Object tar;

    // 绑定委托对象，并返回代理类
    public Object bind(Object tar) {
        this.tar = tar;
        // 绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable// 不依赖具体接口实现
    {
        Object result = null;// 被代理的类型为Object基类
        // 这里就可以进行所谓的AOP编程了
        // 在调用具体函数方法前，执行功能处理
        result = method.invoke(tar, args);
        // 在调用具体函数方法后，执行功能处理
        return result;
    }
}

/**
 * ProxyTest
 *
 * @author qianchun 2018/11/15
 **/
public class ProxyTest {

//    public static void main(String[] args) throws Exception {
////        Subject sub = new SubjectProxy();
////        sub.doSomething();
//
//
//
//        ProxyHandler proxy = new ProxyHandler();
//        //绑定该类实现的所有接口
//        Subject sub = (Subject) proxy.bind(new RealSubject());
//        sub.doSomething();
//    }
}
