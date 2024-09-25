package com.sanr.dms.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ConvertAbstract {
    public static Object convertFromTo(Object objectIn, Object objectRes){
        Field[] fields = objectIn.getClass().getDeclaredFields();
        for(Field field:fields){
            String fieldName = field.getName();
            String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try{
                Method methodIn =  objectIn.getClass().getDeclaredMethod("get" + methodName);
                Method methodRes =  objectRes.getClass().getDeclaredMethod("set" + methodName, field.getType());
                methodRes.invoke(objectRes, methodIn.invoke(objectIn));
            }catch(Exception exc){
                System.out.println(exc.getMessage());
            }
        }
        return objectRes;
    }
}
