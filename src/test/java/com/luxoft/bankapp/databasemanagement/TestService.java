package com.luxoft.bankapp.databasemanagement;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Created by AKoscinski on 2016-05-10.
 */

class TestService {

    public static boolean isEquals(Object o1, Object o2) throws IntrospectionException, InvocationTargetException, IllegalAccessException {


        if (o1 instanceof Collection && o2 instanceof Collection) {
            Collection collection1 = (Collection) o1;
            Collection collection2 = (Collection) o2;

            if (collection1.size() != collection2.size()) {
                return false;
            }
            boolean flag = false;
            for (int i = 0; i < collection1.size(); i++) {
                flag = isEqualsObject(collection1.toArray()[i], collection2.toArray()[i]);
            }
            return flag;
        }

        return isEqualsObject(o1, o2);

    }

    private static boolean isEqualsObject(Object o1, Object o2) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Class c1 = o1.getClass();
        Class c2 = o2.getClass();

        Field[] fields1 = c1.getDeclaredFields();
        Field[] fields2 = c2.getDeclaredFields();

        for (int i = 0; i < fields1.length; i++) {
            if (!fields1[i].isAnnotationPresent(NoDB.class)) {
                if (!(new PropertyDescriptor(fields1[i].getName(), c1).getReadMethod().invoke(o1)).equals(new PropertyDescriptor(fields2[i].getName(), c1).getReadMethod().invoke(o2))) {
                    return false;
                }
            }
        }

        return true;
    }
}