package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.function.Consumer;

public class ParameterMap implements Iterable<Map.Entry<String, Object>> {
    private Map<String, Object> map = new HashMap<>();

    public Object get(String key){
        return map.get(key);
    }

    public String getString(String key){
        return containsKey(key) ?
                map.get(key).toString() :
                null;
    }

    public ParameterMap put(String key, Object value){
        map.put(key, value);
        return this;
    }

    public ParameterMap putAll(Map<String, Object> map){
        this.map.putAll(map);
        return this;
    }

    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    public Map<String, Object> getMap(){
        Map<String, Object> result = new HashMap<>();
        result.putAll(this.map);

        return result;
    }

    public String toJSONString(){
        return new JSONObject()
                .fluentPutAll(map)
                .toString();
    }

    public String toQueryString(){
        List<String> parameterList = new ArrayList<>();
        map.forEach((key,value) -> parameterList.add(key + "=" + (value == null ? "" : value.toString())));
        return  String.join("&", parameterList);
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return map.entrySet().iterator();
    }


    @Override
    public void forEach(Consumer<? super Map.Entry<String, Object>> action) {
        if (action == null)
            return;

        for (Map.Entry<String, Object> e : map.entrySet()) {
            action.accept(e);
        }
    }

    @Override
    public Spliterator<Map.Entry<String, Object>> spliterator() {
        return map.entrySet().spliterator();
    }
}
