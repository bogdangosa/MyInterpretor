package Containers;

import Exceptions.DictionaryException;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MyDictionary<K, V>  implements MyIDictionary<K, V>{
    private Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<K, V>();
    }

    public MyDictionary( Map<K, V> map) {
        this.map = map;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        if(!this.isDefined(key))
            throw new DictionaryException("Key not found!");
        return map.get(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean remove(K key) {
        return map.remove(key) != null;
    }

    @Override
    public boolean isDefined(K key){
        return map.containsKey(key);
    }

    @Override
    public void setContent(Map<K, V> content) {
        map = content;
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public MyIDictionary<K, V>  deepCopy() {
        Map<K,V> copy_map = new HashMap<K, V>();
        for(K key : map.keySet()){
            copy_map.put(key, map.get(key));
        }
        return new MyDictionary<K, V>(copy_map);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public Map<K, V> getAll(){
        return map;
    }
}
