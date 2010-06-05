/*
 * CSetOfLongs.java.java
 *
 * Created on 03-12-2010 11:24:38 PM
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
package com.colt.nicity.core.collection;

import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.lang.UInteger;
import com.colt.nicity.core.lang.ULong;
import com.colt.nicity.core.observer.AObservable;

/**
 *
 * @author Administrator
 */
public class CSetOfLongs extends AObservable implements ISetOfLongs {

    //!! cNull = Long.MAX_VALUE would require extra work because zero is the initial memory state!
    static final long cNull = 0;//!! caution! reserved! don't use for a key!
    static final long cSkip = Long.MIN_VALUE;	//!! caution! reserved! don't use for a key!

    /**
     * 
     * @param src
     * @return
     */
    public static final long[] trim(long[] src) {
        int count = 0;
        int len = src.length;
        long[] array = new long[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == cNull) {
                continue;
            }
            if (src[i] == cSkip) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = UArray.trim(array, count);
        }
        return array;
    }

    /**
     * 
     * @param src
     * @return
     */
    public static final Long[] trimLongs(long[] src) {
        int count = 0;
        int len = src.length;
        Long[] array = new Long[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == cNull) {
                continue;
            }
            if (src[i] == cSkip) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = UArray.trim(array, count);
        }
        return array;
    }
    private int maxKeys;
    private int numKeys;
    private int primeLength;
    private long[] keys;
    private static int cMinKeys = 5; // based on aubjex parsing statistics, this is a good min

    /**
     *
     */
    public CSetOfLongs() {
        setMinimumAllocation();
    }

    /**
     *
     * @param _keys
     */
    public CSetOfLongs(long[] _keys) {
        this(_keys.length);
        for (int i = 0; i < _keys.length; i++) {
            add(_keys[i]);
        }
    }

    /**
     * 
     * @param maxKeys
     */
    public CSetOfLongs(int maxKeys) {
        if (maxKeys <= cMinKeys) {
            setMinimumAllocation();
            return;
        }
        this.maxKeys = maxKeys;
        primeLength = UInteger.nextPrime(maxKeys + maxKeys / 3);
        numKeys = 0;
        keys = new long[primeLength];
    }

    synchronized private void grow() {
        if (numKeys < maxKeys) {
            return; // prevents possible waiting threads to re-grow needlessly
        }
        if (maxKeys < cMinKeys) {
            setMinimumAllocation();
            return;
        }
        CSetOfLongs grown = new CSetOfLongs(maxKeys + maxKeys / 2);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == 0) {
                continue;
            }
            grown.add(keys[i]);
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
        CSetOfLongs grown = new CSetOfLongs(maxKeys);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == 0) {
                continue;
            }
            grown.add(keys[i]);
        }
        keys = grown.keys;
        maxKeys = grown.maxKeys;
        numKeys = grown.numKeys;
        primeLength = grown.primeLength;
        grown = null;
    }

    synchronized private void setMinimumAllocation() {
        numKeys = 0;
        primeLength = cMinKeys;	// for such a small table, save space and don't worry about key collisions
        maxKeys = cMinKeys;
        keys = new long[primeLength];
        return;
    }

    /**
     *
     */
    public void free() {
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
    public long getCount() {
        return numKeys;
    }

    /**
     * 
     * @return
     */
    public long getMaxCount() {
        return maxKeys;
    }

    /**
     *
     * @return
     */
    public long rawSizeInBytes() {
        return keys.length * 8;
    }

    /**
     *
     * @return
     */
    synchronized public long[] getAll() {
        return trim(keys);
    }

    /**
     *
     * @param _keys
     * @return
     */
    synchronized public long[] replaceAll(long[] _keys) {
        long[] removedValues = trim(keys);
        setMinimumAllocation();
        for (int i = 0; i < _keys.length; i++) {
            add(_keys[i]);
        }
        if (isBeingObserved()) {
            change("Write", null);
        }
        return removedValues;
    }

    /**
     *
     * @return
     */
    synchronized public long[] removeAll() {
        long[] removedValues = trim(keys);
        setMinimumAllocation();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return removedValues;
    }

    final private int hash(long key) {
        long hash = key;
        hash *= hash << 1;
        int result = (int) (hash % primeLength);
        if (result < 0) {
            result = -result;
        }
        return result;
    }

    /**
     * 
     * @param _keys
     */
    synchronized public void add(long[] _keys) {
        for (long k : _keys) {
            add(k);
        }
    }

    /**
     *
     * @param key
     * @return
     */
    synchronized public boolean add(long key) {
        if (key == 0) {
            return false;
        }

        if (maxKeys == 0) {
            setMinimumAllocation();
        }
        if (numKeys >= maxKeys) {
            grow();
        }
        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for available slot
            i = (++i) % k, j++ // wraps around table
            ) {

            if (keys[i] == key) {
                return false;
            }
            if (keys[i] == cNull) {
                keys[i] = key;
                numKeys++;
                if (isBeingObserved()) {
                    change("Write", null);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _set
     * @throws Exception
     */
    public void add(CSetOfLongs _set) throws Exception {
        for (long l : _set.getAll()) {
            add(l);
        }
    }

    /**
     *
     * @param key
     * @return
     */
    synchronized public boolean contains(long key) {
        if (maxKeys == 0) {
            return false;
        }
        if (key == 0) {
            return false;
        }

        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for key
            i = (++i) % k, j++) {						// wraps around table

            if (keys[i] == cNull) {
                return false;
            }
            if (keys[i] == key) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param key
     * @return
     */
    synchronized public boolean remove(long key) {
        if (maxKeys == 0) {
            return false;
        }
        if (key == 0) {
            return false;
        }

        for (int i = hash(key), j = 0, k = primeLength; // stack vars for efficiency
            j < k; // max search for key
            i = (++i) % k, j++) {					// wraps around table

            if (keys[i] == cNull) {
                return false;
            }
            if (keys[i] == key) {
                keys[i] = cNull;
                numKeys--;

                int next = (i + 1) % k;
                if (keys[next] == cNull) {
                    for (int z = i; z >= 0; z--) {
                        if (keys[z] != cSkip) {
                            break;
                        }
                        keys[z] = cNull;
                    }
                    keys[i] = cNull;
                }
                else {
                    keys[i] = cSkip;
                }
                if (isBeingObserved()) {
                    change("Write", null);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _
     * @param _callback
     */
    public void backcall(IOut _, ICallback _callback) {
        try {
            long count = getCount();
            for (int i = 0; i < primeLength; i++) {
                if (count <= 0) {
                    break;
                }
                long key = keys[i];
                if (key == cSkip) {
                    continue;
                }
                if (key == cNull) {
                    continue;
                }
                byte[] ioKey = ULong.longBytes(key);
                Object back = _callback.callback(ioKey);
                if (back != ioKey) {
                    break;
                }
                if (_.canceled()) {
                    break;
                }
                count--;
            }
            return;
        }
        catch (Exception x) {
            return;
        }
    }
}

