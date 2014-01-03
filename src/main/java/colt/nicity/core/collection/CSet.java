/*
 * CSet.java.java
 *
 * Created on 03-12-2010 10:52:02 PM
 *
 * Copyright 2010 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package colt.nicity.core.collection;

import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.lang.UInteger;
import colt.nicity.core.observer.AObservable;

/**
 * 
 * @author Administrator
 * @param <V>
 */
public class CSet<V> extends AObservable implements IBackcall<V>, IHaveCount {

    private static final Object skip = new Object();
    /**
     *
     */
    public static int cMinKeys = 5;
    private int maxKeys;
    private int numKeys;
    private int primeLength;
    private Object[] keys;

    /**
     *
     */
    public CSet() {
        setMinimumAllocation();
    }

    /**
     *
     * @param _keys
     */
    public CSet(V[] _keys) {
        this(_keys.length);
        add(_keys);
    }

    /**
     *
     * @param maxKeys
     */
    public CSet(int maxKeys) {
        if (maxKeys <= cMinKeys) {
            setMinimumAllocation();
            return;
        }
        this.maxKeys = maxKeys;
        primeLength = UInteger.nextPrime(maxKeys + maxKeys / 3);
        numKeys = 0;
        keys = new Object[primeLength];
    }

    synchronized private void grow() {
        if (numKeys < maxKeys) {
            return; // prevents possible waiting threads to re-grow needlessly

        }
        if (maxKeys < cMinKeys) {
            setMinimumAllocation();
            return;
        }
        CSet<V> grown = new CSet<>(maxKeys + maxKeys / 2);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                continue;
            }
            if (keys[i] == skip) {
                continue;
            }
            grown.add((V) keys[i]);
        }
        keys = grown.keys;
        maxKeys = grown.maxKeys;
        numKeys = grown.numKeys;
        primeLength = grown.primeLength;
        grown = null;
    }

    /**
     *
     */
    synchronized public void rehash() {
        CSet<V> grown = new CSet<>(maxKeys);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                continue;
            }
            if (keys[i] == skip) {
                continue;
            }
            grown.add((V) keys[i]);
        }
        keys = grown.keys;
        maxKeys = grown.maxKeys;
        numKeys = grown.numKeys;
        primeLength = grown.primeLength;
        grown = null;
    }

    private void setMinimumAllocation() {
        numKeys = 0;
        primeLength = cMinKeys;	// for such a small table, save space and don't worry about key collisions
        maxKeys = cMinKeys;
        keys = new Object[primeLength];
        return;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return maxKeys;
    }

    /**
     *
     * @return
     */
    @Override
    public long getCount() {
        return numKeys;
    }

    /**
     *
     * @param _key
     * @return
     */
    public boolean contains(Object _key) {
        return get(_key) != null;
    }

    /**
     *
     * @param _set
     * @return
     */
    public CSet intersection(CSet _set) {
        if (_set == null) {
            return this;
        }
        CSet intersection = new CSet();
        Object[] all = getAll(Object.class);
        for (int i = 0; i < all.length; i++) {
            if (_set.get(all[i]) != null) {
                intersection.add(all[i]);
            }
        }
        return intersection;
    }

    /**
     *
     * @param _set
     */
    public void add(CSet _set) {
        if (_set == null) {
            return;
        }
        add(_set.getAll(Object.class));
    }

    /**
     *
     * @param _set
     */
    public void subtract(CSet _set) {
        if (_set == null) {
            return;
        }
        remove(_set.getAll(Object.class));
    }

    /**
     *
     * @param _class
     * @return
     */
    public V[] getAll(Class _class) {
        return (V[]) UArray.removeNulls(keys, skip, _class);
    }

    /**
     *
     * @return
     */
    synchronized public Object[] removeAll() {
        if (isBeingObserved()) {
            Object[] removedValues = UArray.removeNulls(keys, skip);
            setMinimumAllocation();
            return removedValues;
        }
        else {

            Object[] removedKeys = UArray.removeNulls(keys, skip);
            setMinimumAllocation();
            change("Write", null);
            return removedKeys;
        }
    }

    final private int hash(Object key) {
        int keyHash = key.hashCode();
        int keyShuffle = keyHash;
        keyShuffle += keyShuffle >> 8; // shuffle bits to avoid worst case clustering

        if (keyShuffle < 0) {
            keyShuffle = -keyShuffle;
        }
        return keyShuffle % primeLength;
    }

    /**
     * 
     * @param _key
     */
    public void xor(V _key) {
        if (get(_key) == null) {
            add(_key);
        }
        else {
            remove(_key);
        }
    }

    /**
     *
     * @param _key
     */
    public void xor(Object[] _key) {
        for (int i = 0; i < _key.length; i++) {
            xor((V) _key[i]);
        }
    }

    /**
     *
     * @param key
     * @return
     */
    synchronized public V get(Object key) {
        if (maxKeys == 0) {
            return null;
        }
        if (key == null) {
            return null;
        }
        if (key == skip) {
            return null;
        }
        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for key
            i = (++i) % k, j++) {					// wraps around table

            if (keys[i] == skip) {
                continue;
            }
            if (keys[i] == null) {
                return null;
            }
            if (keys[i].equals(key)) {
                return (V) keys[i];
            }
        }
        return null;
    }

    /**
     * 
     * @param keys
     * @return
     */
    public Object[] get(Object[] keys) {
        Object[] got = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            got[i] = get(keys[i]);
        }
        return got;
    }

    /**
     *
     * @param keys
     * @return
     */
    public Object[] remove(Object[] keys) {
        if (keys == null) {
            return null;
        }
        Object[] removed = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            removed[i] = remove(keys[i], false);
        }
        if (isBeingObserved()) {
            change("Write", null);
        }
        return removed;
    }

    /**
     * 
     * @param key
     * @return
     */
    public V remove(Object key) {
        return remove(key, true);
    }

    synchronized private V remove(Object key, boolean _notify) {
        if (maxKeys == 0) {
            return null;
        }
        if (key == null) {
            return null;
        }
        if (key == skip) {
            return null;
        }
        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for key
            i = (++i) % k, j++) {					// wraps around table

            if (keys[i] == skip) {
                continue;
            }
            if (keys[i] == null) {
                return null;
            }
            if (keys[i].equals(key)) {
                synchronized (keys[i]) {
                    if (!(keys[i].equals(key))) {
                        return null; // it could be removed while waiting on lock!

                    }
                    Object removedKey = keys[i];
                    int next = (i + 1) % k;
                    if (keys[next] == null) {
                        for (int z = i; z >= 0; z--) {
                            if (keys[z] != skip) {
                                break;
                            }
                            keys[z] = null;
                        }
                        keys[i] = null;
                    }
                    else {
                        keys[i] = skip;
                    }
                    numKeys--;
                    if (_notify && isBeingObserved()) {
                        change("Write", null);
                    }
                    return (V) removedKey;
                }
            }
        }
        return null;
    }

    // active entries; assert this is same as cardinality of an iterator on same collection
    /**
     *
     * @return
     */
    synchronized public V removeOne() {
        int i = 0;
        for (; i < keys.length; i++) {
            if (keys[i] == null || keys[i] == skip) {
                continue;
            }
            break;
        }
        if (i == keys.length) {
            return null;
        }
        return (V) remove(keys[i]);
    }

    /**
     *
     * @param key
     */
    public void add(V key) {
        if (key == null) {
            return;
        }
        replace(key);
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     *
     * @param _keys
     */
    public void add(Object[] _keys) {
        if (_keys == null) {
            return;
        }
        for (int i = 0; i < _keys.length; i++) {
            if (_keys[i] == null) {
                continue;
            }
            replace(_keys[i]);
        }
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     * 
     * @param key
     * @return
     */
    public V replace(Object key) {
        if (key == null) {
            return null;
        }
        V replaced = (V) _replace(key);
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replaced;
    }

    /**
     *
     * @param _keys
     * @return
     */
    public Object[] replace(Object[] _keys) {
        if (_keys == null || _keys.length == 0) {
            return null;
        }
        int len = _keys.length;
        Object[] replaced = new Object[len];
        for (int i = 0; i < len; i++) {
            replaced[i] = _replace(_keys[i]);
        }
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replaced;
    }

    synchronized final private V _replace(Object key) {
        if (maxKeys == 0) {
            setMinimumAllocation();
        }
        if (numKeys >= maxKeys) {
            grow();
        }
        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for available slot
            i = (++i) % k, j++) {					// wraps around table

            if (keys[i] == key) {//!! sync not needed
                Object oldKey = keys[i];
                keys[i] = key;
                return (V) oldKey;
            }
            if (keys[i] == null || keys[i] == skip) {
                synchronized (keys) {
                    if (keys[i] != null && keys[i] != skip) {
                        continue; // it could change while waiting on lock!

                    }
                    keys[i] = key;
                    numKeys++;
                    return null;
                }
            }
            if (keys[i].equals(key)) {
                synchronized (keys) { // sync is to be extra safe

                    Object oldKey = keys[i];
                    keys[i] = key;
                    return (V) oldKey;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "(" + getCount() + ") " + super.toString();
    }

    /**
     *
     * @param _
     * @param _callback
     */
    @Override
    public void backcall(IOut _, ICallback<V, V> _callback) {
        try {
            Object[] _keys = keys;
            long c = _keys.length;
            if (c <= 0) {
                return;
            }
            for (int i = 0; i < c; i++) {
                V v = (V) _keys[i];
                if (v == null) {
                    continue;
                }
                if (v == skip) {
                    continue;
                }
                V back = _callback.callback(v);
                if (back != v) {
                    break;
                }
                if (_.canceled()) {
                    break;
                }
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    /**
     *
     * @param _class
     * @return
     */
    public Object[] toArray(Class _class) {
        if (_class == Object.class) {
            return getAll(Object.class);
        }
        CArray array = new CArray(_class);
        Object[] all = getAll(Object.class);
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getClass() == _class) {
                array.insertLast(all[i]);
            }
        }
        return array.getAll();
    }
}

