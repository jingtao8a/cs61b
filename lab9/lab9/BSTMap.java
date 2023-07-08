package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        if (p.key.compareTo(key) < 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        V v = get(key);
        if (v != null) {
            remove(key, v);
        }
        root = putHelper(key, value, root);
        ++size;
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
        Iterator<K> iter = iterator();
        Set<K> set = new TreeSet<>();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private void swapNode(Node one, Node two) {
        K tmpK = one.key;
        V tmpV = one.value;
        one.key = two.key;
        one.value = two.value;
        two.key = tmpK;
        two.value = tmpV;
    }
    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            if (p.left == null && p.right == null) { // p is a leaf Node
                return null;
            }
            if (p.left != null && p.right != null) { // p has two children
                Node leftChild = p.left;
                while (leftChild.right != null) {
                    leftChild = leftChild.right;
                }
                swapNode(p, leftChild);
                p.left = removeHelper(key, p.left);
                return p;
            }
            if (p.left != null) {
                return p.left;
            }
            if (p.right != null) {
                return p.right;
            }
        }
        if (p.key.compareTo(key) < 0) {
            p.right = removeHelper(key, p.right);
        } else {
            p.left = removeHelper(key, p.left);
        }
        return p;
    }

    @Override
    public V remove(K key) {
        V v = get(key);
        if (v == null) {
            return null;
        }
        root = removeHelper(key, root);
        --size;
        return v;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V v = get(key);
        if (v == null || !v.equals(value)) {
            return null;
        }
        root = removeHelper(key, root);
        --size;
        return v;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements  Iterator<K> {
        private Deque<Node> stack = new LinkedList<>();
        private Node cur = BSTMap.this.root;

        @Override
        public boolean hasNext() {
            return cur != null || !stack.isEmpty();
        }

        @Override
        public K next() {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            K ret = cur.key;
            cur = cur.right;
            return ret;
        }
    }
}
