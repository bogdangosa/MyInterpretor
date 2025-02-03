package Containers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MyIDictionary<K, V>  {
    void put(K key, V value);
    V lookUp(K key);
    void update(K key, V value);
    boolean remove(K key);
    boolean isDefined(K key);
    void setContent(Map<K, V> content);
    Map<K, V> getContent();
    MyIDictionary<K, V>  deepCopy();
}
