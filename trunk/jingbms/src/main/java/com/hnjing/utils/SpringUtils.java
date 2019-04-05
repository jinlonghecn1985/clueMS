/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: AAA.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.utils
 * @Description: 
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年1月8日 下午4:42:20
 * @version: V1.0  
 */
package com.hnjing.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component  
public class SpringUtils implements ApplicationContextAware {  
 
    private static ApplicationContext applicationContext = null;  
 
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        if(SpringUtils.applicationContext == null){  
            SpringUtils.applicationContext  = applicationContext;  
        }  
    }  
 
 
    //获取applicationContext  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
 
    //通过name获取 Bean.  
    public static Object getBean(String name){  
        return getApplicationContext().getBean(name);  
    }  
 
    //通过class获取Bean.  
    public static <T> T getBean(Class<T> clazz){  
        return getApplicationContext().getBean(clazz);  
    }  
 
    //通过name,以及Clazz返回指定的Bean  
    public static <T> T getBean(String name,Class<T> clazz){  
        return getApplicationContext().getBean(name, clazz);  
    }  
 
}  