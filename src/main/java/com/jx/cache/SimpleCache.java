package com.jx.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple in-memory cache implementation.
 * 
 * @param <K> The type of keys maintained by this cache
 * @param <V> The type of values maintained by this cache
 */
public class SimpleCache<K, V> {
    private final Map<K, CacheEntry<V>> cache;
    private final long defaultExpirationTimeMillis;

    /**
     * Creates a new cache with the specified default expiration time.
     * 
     * @param defaultExpirationTimeMillis The default expiration time in milliseconds
     */
    public SimpleCache(long defaultExpirationTimeMillis) {
        this.cache = new ConcurrentHashMap<>();
        this.defaultExpirationTimeMillis = defaultExpirationTimeMillis;
    }

    /**
     * Creates a new cache with a default expiration time of 1 hour.
     */
    public SimpleCache() {
        this(3600000); // 1 hour in milliseconds
    }

    /**
     * Puts a value in the cache with the default expiration time.
     * 
     * @param key The key
     * @param value The value
     */
    public void put(K key, V value) {
        put(key, value, defaultExpirationTimeMillis);
    }

    /**
     * Puts a value in the cache with a specific expiration time.
     * 
     * @param key The key
     * @param value The value
     * @param expirationTimeMillis The expiration time in milliseconds
     */
    public void put(K key, V value, long expirationTimeMillis) {
        long expirationTime = System.currentTimeMillis() + expirationTimeMillis;
        cache.put(key, new CacheEntry<>(value, expirationTime));
    }

    /**
     * Gets a value from the cache.
     * 
     * @param key The key
     * @return The value, or null if not found or expired
     */
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        
        return entry.getValue();
    }

    /**
     * Removes a value from the cache.
     * 
     * @param key The key
     */
    public void remove(K key) {
        cache.remove(key);
    }

    /**
     * Clears all entries from the cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Removes all expired entries from the cache.
     */
    public void cleanUp() {
        for (Map.Entry<K, CacheEntry<V>> entry : new HashMap<>(cache).entrySet()) {
            if (entry.getValue().isExpired()) {
                cache.remove(entry.getKey());
            }
        }
    }

    /**
     * Inner class to hold cache entries with expiration times.
     */
    private static class CacheEntry<V> {
        private final V value;
        private final long expirationTime;

        public CacheEntry(V value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        public V getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}