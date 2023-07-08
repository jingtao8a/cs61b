package lab9;

import org.junit.Test;
import sun.awt.windows.WBufferStrategy;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (get(key) != null) {
            remove(key);
        }
        buckets[hash(key)].put(key, value);
        ++size;
        if (loadFactor() > MAX_LF) { // 扩容
            ArrayMap<K, V>[] oldBuckets = buckets;
            buckets = new ArrayMap[oldBuckets.length * 2];
            clear();
            for (ArrayMap<K, V> map : oldBuckets) {
                for (K oldkey : map) {
                    buckets[hash(oldkey)].put(oldkey, map.get(oldkey));
                    ++size;
                }
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        Iterator<K> iter = iterator();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V v =get(key);
        if (v == null) {
            return null;
        }
        buckets[hash(key)].remove(key);
        --size;
        return v;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V v = get(key);
        if (v == null || !v.equals(value)) {
            return null;
        }
        buckets[hash(key)].remove((key));
        --size;
        return v;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIter();
    }

    private class MyHashMapIter implements Iterator<K> {
        private Iterator<K> arrayMapIter = MyHashMap.this.buckets[0].iterator();
        private int index = 0;
        private int curSize = 0;
        @Override
        public boolean hasNext() {
            return  curSize < MyHashMap.this.size;
        }
        @Override
        public K next() {
            while (hasNext()) {
                if (arrayMapIter.hasNext()) {
                    ++curSize;
                    return arrayMapIter.next();
                }
                ++index;
                arrayMapIter = MyHashMap.this.buckets[index].iterator();
            }
            return null;
        }
    }
}
