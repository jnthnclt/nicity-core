/*
 * KeyedLong.java.java
 *
 * Created on 03-12-2010 11:06:24 PM
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
package com.colt.nicity.core.collection.keyed;

import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.ASetObject;
import java.lang.reflect.Array;

/**
 *
 * @author Administrator
 * @param <K>
 */
public class KeyedLong<K> extends ASetObject<K> implements
    Comparable<KeyedLong<K>> {

    /**
     *
     */
    protected K key;
    /**
     *
     */
    protected long value;

    /**
     * 
     */
    public KeyedLong() {
    }

    /**
     *
     * @param _key
     */
    public KeyedLong(K _key) {
        key = _key;
    }

    /**
     *
     * @return
     */
    @Override
    public K hashObject() {
        return key;
    }

    /**
     *
     * @return
     */
    public K key() {
        return key;
    }

    /**
     *
     * @return
     */
    public long value() {
        return value;
    }

    @Override
    public int compareTo(KeyedLong<K> o) {
        return -((Comparable<K>) key()).compareTo(o.key());//!! hacky

    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void add(CSet<KeyedLong<K>> _set, K _key, long _amount) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value += _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void subtract(CSet<KeyedLong<K>> _set, K _key,
        long _amount) {
        KeyedLong kl = _get(_set, _key);
        kl.value -= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void multiply(CSet<KeyedLong<K>> _set, K _key,
        long _amount) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value *= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void divide(CSet<KeyedLong<K>> _set, K _key, long _amount) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value /= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void max(CSet<KeyedLong<K>> _set, K _key, long _value) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value = Math.max(kl.value, _value);
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void min(CSet<KeyedLong<K>> _set, K _key, long _value) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value = Math.min(kl.value, _value);
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void set(CSet<KeyedLong<K>> _set, K _key, long _value) {
        KeyedLong<K> kl = _get(_set, _key);
        kl.value = _value;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @return
     */
    public static <K> KeyedLong<K> _get(CSet<KeyedLong<K>> _set, K _key) {
        synchronized (_set) {
            KeyedLong<K> record = _set.get(_key);
            if (record == null) {
                record = new KeyedLong<K>(_key);
                _set.add(record);
            }
            return record;
        }
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     */
    public static <K> void remove(CSet<KeyedLong<K>> _set, K _key) {
        synchronized (_set) {
            KeyedLong<K> record = _set.get(_key);
            if (record == null) {
                return;
            }
            _set.remove(_key);
        }
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @return
     */
    public static <K> long get(CSet<KeyedLong<K>> _set, K _key) {
        synchronized (_set) {
            KeyedLong<K> record = _set.get(_key);
            if (record == null) {
                return 0;
            }
            return record.value;
        }
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @return
     */
    public static <K> boolean contains(CSet<KeyedLong<K>> _set, K _key) {
        return get(_set, _key) == 0 ? false : true;
    }

    /**
     * 
     * @param <K>
     * @param _set
     * @param _keyClass
     * @return
     */
    public static <K> K[] toKeys(CSet<KeyedLong<K>> _set, Class<? extends K> _keyClass) {
        synchronized (_set) {
            KeyedLong<K>[] all = _set.getAll(KeyedLong.class);
            K[] array = (K[]) Array.newInstance(_keyClass, all.length);
            for (int i = 0; i < all.length; i++) {
                array[i] = all[i].key();
            }
            return array;
        }
    }

    /**
     *
     * @param _set
     * @return
     */
    public static long[] toValues(CSet _set) {
        synchronized (_set) {
            Object[] all = _set.getAll(Object.class);
            long[] array = new long[all.length];
            for (int i = 0; i < all.length; i++) {
                array[i] = ((KeyedLong) all[i]).value();
            }
            return array;
        }
    }
}
