/*
 * KeyedCSet.java.java
 *
 * Created on 03-12-2010 11:00:00 PM
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
 * @param <K>
 * @param <SV> 
 * @author Administrator
 */
public class KeyedCSet<K, SV> extends ASetObject<K> implements
    Comparable<KeyedCSet<K, SV>> {

    private K key;
    /**
     *
     */
    public CSet<SV> set = new CSet<SV>();

    /**
     *
     * @param _key
     */
    public KeyedCSet(K _key) {
        key = _key;
    }

    /**
     *
     * @return
     */
    public Object[] getAll() {
        return set.getAll(Object.class);
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return (int) set.getCount();
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
    public CSet<SV> value() {
        return set;
    }
    
    @Override
    public int compareTo(KeyedCSet<K, SV> other) {
        double thisVal = this.set.getCount();
        double otherVal = other.set.getCount();
        return (otherVal < thisVal ? -1 : (otherVal == thisVal ? 0 : 1));
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, SV> int count(CSet<KeyedCSet<K, SV>> _set, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return 0;
            }
            return (int) set.set.getCount();
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _value
     * @param _key
     */
    public static <K, SV> void add(CSet<KeyedCSet<K, SV>> _set, SV[] _value,
        K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                set = new KeyedCSet<K, SV>(_key);
                _set.add(set);
            }
            set.set.add(_value);
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _value
     * @param _key
     */
    public static <K, SV> void add(CSet<KeyedCSet<K, SV>> _set, SV _value,
        K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                set = new KeyedCSet<K, SV>(_key);
                _set.add(set);
            }
            set.set.add(_value);
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _value
     * @param _key
     */
    public static <K, SV> void remove(CSet<KeyedCSet<K, SV>> _set, SV _value,
        K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return;
            }
            set.set.remove(_value);
            if (set.set.getCount() == 0) {
                _set.remove(_key);
            }
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, SV> Object[] removeAll(CSet<KeyedCSet<K, SV>> _set, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return new Object[0];
            }
            Object[] removed = set.set.removeAll();
            if (set.set.getCount() == 0) {
                _set.remove(_key);
            }
            return removed;
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _value
     * @param _key
     * @return
     */
    public static <K, SV> boolean contains(CSet<KeyedCSet<K, SV>> _set,
        SV _value, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return false;
            }
            return set.set.get(_value) != null;
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _value
     * @param _key
     * @return
     */
    public static <K, SV> Object get(CSet<KeyedCSet<K, SV>> _set,
        SV _value, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return null;
            }
            return set.set.get(_value);
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, SV> Object[] getAll(CSet<KeyedCSet<K, SV>> _set, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return new Object[0];
            }
            return set.set.getAll(Object.class);
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @param _key
     * @return
     */
    public static <K, SV> CSet<SV> set(CSet<KeyedCSet<K, SV>> _set, K _key) {
        synchronized (_set) {
            KeyedCSet<K, SV> set = _set.get(_key);
            if (set == null) {
                return null;
            }
            return set.set;
        }
    }

    /**
     *
     * @param <K>
     * @param <SV>
     * @param _set
     * @return
     */
    public static <K, SV> CSet[] sets(CSet<KeyedCSet<K, SV>> _set) {
        synchronized (_set) {
            Object[] all = _set.getAll(Object.class);
            CSet[] sets = new CSet[all.length];
            for (int a = 0; a < all.length; a++) {
                sets[a] = ((KeyedCSet<K, SV>) all[a]).set;
            }
            return sets;
        }
    }
}
	
