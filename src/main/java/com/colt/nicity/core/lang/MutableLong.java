/*
 * MutableLong.java.java
 *
 * Created on 12-29-2009 03:51:00 PM
 *
 * Copyright 2009 Jonathan Colt
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
package com.colt.nicity.core.lang;

public class MutableLong extends ASetObject implements Comparable {
    private long unit = 0;
    public MutableLong() {
    }
    public MutableLong(long _unit) {
        unit = _unit;
    }
    public Object hashObject() {
        return unit;
    }
    public long longValue() {
        return unit;
    }
    public long get() {
        return unit;
    }
    public void set(long _val) {
        unit = _val;
    }
    public void min(long _val) {
        if (_val < unit) {
            unit = _val;
        }
    }
    public void max(long _val) {
        if (_val > unit) {
            unit = _val;
        }
    }
    public void inc(long _amount) {
        unit += _amount;
    }
    public void inc() {
        unit++;
    }
    public void dec() {
        unit--;
    }
    public void multiply(long _amount) {
        unit *= _amount;
    }
    public void divide(long _amount) {
        unit /= _amount;
    }
    public void reset() {
        unit = 0;
    }
    @Override
    public String toString() {
        Object o = hashObject();
        if (o instanceof Integer) {
            return o.toString();
        }
        return o.toString() + "=" + unit;
    }
    public int compareTo(Object o) {
        long a = this.unit;
        long b = ((MutableLong) o).unit;
        return (a < b ? -1 : (a == b ? 0 : 1));
    }
}
