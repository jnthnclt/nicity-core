/*
 * MutableDouble.java.java
 *
 * Created on 12-29-2009 03:56:00 PM
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
package colt.nicity.core.lang;

/**
 *
 * @author Administrator
 */
public class MutableDouble extends ASetObject implements Comparable {
    private double unit = 0;
    /**
     *
     */
    public MutableDouble() {
    }
    /**
     *
     * @param _unit
     */
    public MutableDouble(double _unit) {
        unit = _unit;
    }
    /**
     *
     * @return
     */
    @Override
    public Object hashObject() {
        return unit;
    }
    /**
     *
     * @return
     */
    public double doubleValue() {
        return unit;
    }
    /**
     *
     * @return
     */
    public double get() {
        return unit;
    }
    /**
     *
     * @param _val
     */
    public void set(double _val) {
        unit = _val;
    }
    /**
     *
     * @param _val
     */
    public void min(double _val) {
        if (_val < unit) {
            unit = _val;
        }
    }
    /**
     *
     * @param _val
     */
    public void max(double _val) {
        if (_val > unit) {
            unit = _val;
        }
    }
    /**
     *
     * @param _amount
     */
    public void inc(double _amount) {
        unit += _amount;
    }
    /**
     *
     */
    public void inc() {
        unit++;
    }
    /**
     *
     */
    public void dec() {
        unit--;
    }
    /**
     *
     * @param _amount
     */
    public void multiply(double _amount) {
        unit *= _amount;
    }
    /**
     *
     * @param _amount
     */
    public void divide(double _amount) {
        unit /= _amount;
    }
    /**
     *
     */
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
    @Override
    public int compareTo(Object o) {
        double a = this.unit;
        double b = ((MutableDouble) o).unit;
        return (a < b ? -1 : (a == b ? 0 : 1));
    }
}
