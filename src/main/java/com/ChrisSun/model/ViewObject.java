package com.ChrisSun.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 2016/7/25.
 */
public class ViewObject {
    private Map<String,Object> map = new HashMap<String,Object>();

    public void set(String key, Object value){
        map.put(key,value);
    }
    public Object get(String key){
        return map.get(key);
    }

}
