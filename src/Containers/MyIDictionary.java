package Containers;

public interface MyIDictionary<K, V>  {
    void put(K key, V value);
    V lookUp(K key);
    void update(K key, V value);
    boolean remove(K key);
    boolean isDefined(K key);
}
