package ru.otus.cache;


import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private static final String ACTION_PUT = "put";
    private static final String ACTION_REMOVE = "remove";
    private static final String ACTION_GET = "get";

    private final WeakHashMap<K, V> cacheMap;
    //    private final HashMap<K, V> cacheMap;
    private final Set<HwListener<K, V>> listeners;

    public MyCache()
    {
        this.cacheMap = new WeakHashMap<>();
        this.listeners = new HashSet<>();
    }

    @Override
    public void put(K key, V value) {

        cacheMap.put(key, value);
        notifyListeners(key, value, ACTION_PUT);
    }

    @Override
    public void remove(K key) {

        V value = cacheMap.remove(key);
        notifyListeners(key, value, ACTION_REMOVE);
    }

    @Override
    public V get(K key) {

        V value = cacheMap.get(key);
        notifyListeners(key, value, ACTION_GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {

        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {

        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action){

        for (HwListener<K, V> listener : listeners )
        {
            listener.notify(key, value, action);
        }
    }
}