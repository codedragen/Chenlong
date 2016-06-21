package com.eetrust.securedoc.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by android on 2016/6/21.
 */
public class Map2Obj {

    public static <T> T map2Obj(Map <String,Object> map, Class<T> c)  {
        T t=null;
        try {
          t=  c.newInstance();
            Set<Map.Entry<String, Object>> set=map.entrySet();
            for (Map.Entry<String,Object> entry:set) {
              Field f= c.getField(entry.getKey()) ;
                f.setAccessible(true);
                f.set(t,entry.getValue());

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        return t;
    }
}
