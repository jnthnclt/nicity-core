/*
 * KeyedValue.java.java
 *
 * Created on 03-12-2010 11:07:53 PM
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
package colt.nicity.core.collection.keyed;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.ASetObject;

/**
 * 
 * @author Administrator
 * @param <K>
 * @param <V>
 */
public class KeyedValue<K, V> extends ASetObject<K> implements
    Comparable<KeyedValue<K, V>> {

    /**
     *
     */
    protected K key;
    /**
     *
     */
    protected V value;

    /**
     *
     */
    public KeyedValue() {
    }

    /**
     *
     * @param _key
     */
    public KeyedValue(K _key) {
        key = _key;
    }

    /**
     *
     * @param _key
     * @param _value
     */
    public KeyedValue(K _key, V _value) {
        key = _key;
        value = _value;
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
    public V value() {
        return value;
    }

    @Override
    public String toString() {
        return "Key=" + key + " Value=" + value;
    }
    // Comparable

    @Override
    public int compareTo(KeyedValue<K, V> o) {
        return -((Comparable<K>) key()).compareTo(o.key());//!! hacky

    }

    // Helper Functions
    /**
     * 
     * @param <K>
     * @param <V>
     * @param _set
     * @param _key
     * @param _value
     */
    public static <K, V> void add(CSet<KeyedValue<K, V>> _set, K _key, V _value) {
        _set.add(new KeyedValue<K, V>(_key, _value));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, V> V get(CSet<KeyedValue<K, V>> _set, K _key) {
        KeyedValue<K, V> got = _set.get(_key);
        if (got == null)
            return null;
        return got.value();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, V> V remove(CSet<KeyedValue<K, V>> _set, K _key) {
        KeyedValue<K, V> got = _set.remove(_key);
        if (got == null)
            return null;
        return got.value();
    }
}
