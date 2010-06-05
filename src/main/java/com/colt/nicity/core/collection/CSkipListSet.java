/*
 * CSkipListSet.java.java
 *
 * Created on 02-03-2010 10:47:19 PM
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

import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.lang.URandom;
import java.util.Arrays;

/**
 *
 * @author Administrator
 * @param <V>
 */
public class CSkipListSet<V> {
    
    private AValueComparator valueComparator;
    private SkipListValue head;
     SkipListValue tail;
    final private CSet<SkipListValue<V>> set = new CSet<SkipListValue<V>>();
    /**
     *
     * @param _valueComparator
     */
    public CSkipListSet(AValueComparator _valueComparator) {
        valueComparator = _valueComparator;
        head = new SkipListValue(this);
    }

    /**
     *
     * @return
     */
    synchronized public long getCount() {
        return set.getCount();
    }
    /**
     * Adds the value to this collection
     * @param _value
     */
    synchronized public void add(Object _value) {
        if (_value == null) {
            throw new RuntimeException("null not supported");
        }

        if (set.contains(_value)) {
            return;
        }
        SkipListValue insert = new SkipListValue(_value);
        set.add(insert);
    
        SkipListValue start = head;
        SkipListValue[] colum = new SkipListValue[start.colum.length];
        Arrays.fill(colum, start);
        int level = colum.length - 1;// start at top of colum
        while (level > -1) {
            if (compare(colum[level], insert) < 0) {// insert is greater than value at current level
                SkipListValue next = colum[level].colum[level];
                if (next != null && compare(next, insert) < 0) {
                    colum[level] = next;
                } else {
                    level = stepDownALevel(colum, level);
                }
            } else {// insert is less than value at current level
                level--;
            }
        }

        int numberOfLevels = insert.colum.length;
        int missingLevels = numberOfLevels - colum.length;
        if (missingLevels > 0) {
            start.colum = (SkipListValue[]) UArray.join(start.colum, new SkipListValue[missingLevels], SkipListValue.class);
        }
        for (level = numberOfLevels - 1; level >= colum.length; level--) {
            insert(start, insert, level);
        }
        for (; level > -1; level--) {
            insert(colum[level], insert, level);
        }
    }
    final private void insert(SkipListValue _a, SkipListValue _b, int _level) {
        SkipListValue oldNext = _a.colum[_level];
        _a.colum[_level] = _b;
        _b.colum[_level] = oldNext;
        // maintain a tail to support getBefore
        if (tail == null) tail = _b;
        else if (tail == _a) {
            tail = _b;
        }
    }
    final private int stepDownALevel(SkipListValue[] _colum, int _level) {
        SkipListValue currentColum = _colum[_level];
        _level--;
        if (_level > -1) {
            _colum[_level] = currentColum;
        }
        return _level;
    }
    final private int compare(SkipListValue _a, SkipListValue _b) {
        if (_a.value == this) {
            return -1; // deal with head special case
        }
        int compare = valueComparator.compare(_a.value, _b.value);
        //System.out.println(_a.value+" "+_b.value+" "+compare);
        if (compare == 0) {
            throw new RuntimeException("Equal not supported");
        }
        return compare;
    }
    /**
     * Returns the value at the given key if present
     * @param _key
     * @return
     */
    synchronized public V get(Object _key) {
        if (_key == null) {
            throw new RuntimeException("null not supported");
        }
        SkipListValue get = set.get(_key);
        if (get != null) {
            return (V)get.value;
        } else {
            return null;
        }
    }
    /**
     * Returns the value after a known key. If key isn't in the set then the method
     * will return null. Circular hashing. Answers the question what is the next
     * available resouse when using this collection as a circular hashtable.
     * @param _key
     * @return
     */
    synchronized public V getAfter(Object _key) {
        if (_key == null) {
            throw new RuntimeException("null not supported");
        }
        SkipListValue get = set.get(_key);
        if (get == null) {
            return null;
        }
        SkipListValue next = get.colum[0];
        if (next == null) {
            SkipListValue first = head.colum[0];
            if (first == null) {
                return null;// should be impossible
            }
            return (V)first.value;
        } else {
            return (V)next.value;
        }

    }
    
    /**
     * Searches thru the set and returns the value that this key would have been
     * inserted after. Answers the question what resource is the record on when using
     * this collection as a circular hashtable.
     * 
     * @param _key
     * @return
     */
    synchronized public V getBefore(Object _key) {
        if (_key == null) {
            throw new RuntimeException("null not supported");
        }
        SkipListValue get = set.get(_key);
        if (get != null) {
            return (V)get.value;
        }
        SkipListValue insert = new SkipListValue(_key);
        SkipListValue start = head;
        SkipListValue colum = start;
        int level = start.colum.length - 1;// start at top of colum
        while (level > -1) {
            if (compare(colum, insert) < 0) {// insert is greater than value at current level
                SkipListValue next = colum.colum[level];
                if (next != null && compare(next, insert) < 0) {
                    colum = next;
                } else {
                    level--;
                }
            } else {// insert is less than value at current level
                level--;
            }
        }
        if (colum == start) {
            if (tail == null) return null;
            return (V)tail.value;
        }
        else {
            return (V)colum.value;
        }
    }


    /**
     * Removes the key from the set if present.
     * @param _key
     */
    synchronized public void remove(Object _key) {
        if (_key == null) {
            throw new RuntimeException("null not supported");
        }
        SkipListValue remove = null;
        remove = set.get(_key);
        if (remove == null) return;
        set.remove(remove);

        SkipListValue start = head;
        SkipListValue[] colum = new SkipListValue[remove.colum.length];
        Arrays.fill(colum, start);
        int level = colum.length - 1;// start at top of colum
        while (level > -1) {
            if (colum[level].colum[level] != remove) {// insert is greater than value at current level
                SkipListValue next = colum[level].colum[level];
                if (next != null && next != remove) {
                    colum[level] = next;
                } else {
                    level = stepDownALevel(colum, level);
                }
            } else {// insert is less than value at current level
                level--;
            }
        }
        if (remove == tail) tail = colum[0];
        for(level = colum.length - 1;level >-1;level--) {
            colum[level].colum[level] = colum[level].colum[level].colum[level];
        }

    }
    /**
     *
     * @param _
     * @param _get
     */
    public void getAllInOrder(IOut _, ICallback _get) {
        SkipListValue at = head.colum[0];
        while (at != null) {
            if (_.canceled()) {
                break;
            }
            _get.callback(at.value);
            at = at.colum[0];
        }
    }
    
    /**
     *
     * @return
     */
    public SkipListValue<V>[] getAll() {
        return set.getAll(SkipListValue.class);
    }

    /**
     *
     * @param <V>
     */
    public class SkipListValue<V> extends ASetObject {
        Object value;
        SkipListValue[] colum;
        SkipListValue(V _value) {
            value = _value;
            if (value == CSkipListSet.this) {
                colum = new SkipListValue[1];
            } else {
                int newH = 1;
                // could pick a rand number bewteen 1 and 32 instead but odds are different
                // todo come up with a way to pick a random number where the odd are equevalent to the while loop
                // 50/50 1
                // 25/75 11
                // 25/75 111
                while (URandom.rand(1d) > 0.5d) {
                    newH++;
                }
                colum = new SkipListValue[newH];
            }
        }
        /**
         *
         * @return
         */
        public V getValue() {
            return (V)value;
        }
        /**
         *
         * @return
         */
        @Override
        public Object hashObject() {
            return value;
        }
    }


    // debugging aids
    /**
     *
     */
    public void toSysOut() {
        SkipListValue at = head;
        while (at != null) {
            toSysOut(at);
            at = at.colum[0];
        }
    }
    private void toSysOut(SkipListValue _v) {
        if (_v == head) System.out.print("HHHH - ");
        else System.out.printf("%04d - ",(Integer)_v.value);
        for(int i=0;i<_v.colum.length;i++) {
            if (_v.colum[i] == null) System.out.print("NULL ");
            else System.out.printf("%04d ",(Integer)_v.colum[i].value);
        }
        System.out.println();
    }
}
