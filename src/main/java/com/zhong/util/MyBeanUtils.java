package com.zhong.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PersistenceDelegate;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cc on 2021/3/26
 */
public class MyBeanUtils {

    /**
     * 获取所有属性值为空的属性名数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyName(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyName = new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String properName = pd.getName();
            if (beanWrapper.getPropertyValue(properName) == null){
                nullPropertyName.add(properName);
            }
        }
        return nullPropertyName.toArray(new String[nullPropertyName.size()]);
    }

}
