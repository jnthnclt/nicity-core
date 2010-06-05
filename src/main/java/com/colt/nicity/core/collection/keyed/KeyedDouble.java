/*
 * KeyedDouble.java.java
 *
 * Created on 03-12-2010 11:03:43 PM
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
public class KeyedDouble<K> extends ASetObject<K> implements
    Comparable<KeyedDouble<K>> {

    /**
     *
     */
    protected K key;
    /**
     *
     */
    protected double value;

    /**
     * 
     */
    public KeyedDouble() {
    }

    /**
     *
     * @param _key
     */
    public KeyedDouble(K _key) {
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
    public double value() {
        return value;
    }

    @Override
    public int compareTo(KeyedDouble<K> o) {
        return -((Comparable<K>) key()).compareTo(o.key());//!! hacky

    }

    /**
     * 
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void add(
        CSet<KeyedDouble<K>> _set, K _key, double _amount) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value += _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void subtract(
        CSet<KeyedDouble<K>> _set, K _key, double _amount) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value -= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void multiply(
        CSet<KeyedDouble<K>> _set, K _key, double _amount) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value *= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _amount
     */
    public static <K> void divide(
        CSet<KeyedDouble<K>> _set, K _key, double _amount) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value /= _amount;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void max(
        CSet<KeyedDouble<K>> _set, K _key, double _value) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value = Math.max(kl.value, _value);
    }

    /**
     * 
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void min(
        CSet<KeyedDouble<K>> _set, K _key, double _value) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value = Math.min(kl.value, _value);
    }

    /**
     * 
     * @param <K>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K> void set(
        CSet<KeyedDouble<K>> _set, K _key, double _value) {
        KeyedDouble<K> kl = _get(_set, _key);
        kl.value = _value;
    }

    /**
     *
     * @param <K>
     * @param _set
     * @param _key
     * @return
     */
    public static <K> KeyedDouble<K> _get(CSet<KeyedDouble<K>> _set, K _key) {
        synchronized (_set) {
            KeyedDouble<K> record = _set.get(_key);
            if (record == null) {
                record = new KeyedDouble<K>(_key);
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
    public static <K> void remove(CSet<KeyedDouble<K>> _set, K _key) {
        synchronized (_set) {
            KeyedDouble<K> record = _set.get(_key);
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
    public static <K> double get(CSet<KeyedDouble<K>> _set, K _key) {
        synchronized (_set) {
            KeyedDouble<K> record = _set.get(_key);
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
    public static <K> boolean contains(CSet<KeyedDouble<K>> _set, K _key) {
        return get(_set, _key) == 0 ? false : true;
    }

    /**
     * 
     * @param <K>
     * @param _set
     * @param _keyClass
     * @return
     */
    public static <K> K[] toKeys(CSet<KeyedDouble<K>> _set, Class _keyClass) {
        synchronized (_set) {
            KeyedDouble<K>[] all = _set.getAll(KeyedDouble.class);
            K[] array = (K[]) Array.newInstance(_keyClass, all.length);
            for (int i = 0; i < all.length; i++) {
                array[i] = all[i].key();
            }
            return array;
        }
    }

    /**
     * 
     * @param <K>
     * @param _set
     * @return
     */
    public static <K> double[] toValues(CSet<KeyedDouble<K>> _set) {
        synchronized (_set) {
            Object[] all = _set.getAll(Object.class);
            double[] array = new double[all.length];
            for (int i = 0; i < all.length; i++) {
                array[i] = ((KeyedDouble<K>)all[i]).value();
            }
            return array;
        }
    }
}
