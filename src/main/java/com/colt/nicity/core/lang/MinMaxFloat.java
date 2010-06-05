/*
 * MinMaxFloat.java.java
 *
 * Created on 12-27-2009 03:12:00 PM
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

/**
 *
 * @author Administrator
 */
public class MinMaxFloat {
    /**
     *
     */
    public float min = Float.MAX_VALUE;
    /**
     *
     */
    public float max = -Float.MAX_VALUE;
    /**
     *
     */
    public int minIndex = -1;
    /**
     *
     */
    public int maxIndex = -1;
    private float sum = 0;
    private int count = 0;
    /**
     *
     */
    public MinMaxFloat() {
    }
    /**
     *
     * @param _min
     * @param _max
     */
    public MinMaxFloat(float _min, float _max) {
        min = _min;
        max = _max;
        count = 2;
    }

    /**
     *
     * @param _value
     * @return
     */
    public double std(float _value) {
        double mean = Math.pow(mean(), 2);
        double value = Math.pow((double) _value, 2);
        double variation = Math.max(mean, value) - Math.min(mean, value);
        return Math.sqrt(variation);
    }
    /**
     *
     * @param _p
     * @return
     */
    public boolean inclusivelyContained(float _p) {
        if (_p < min) {
            return false;
        }
        if (_p > max) {
            return false;
        }
        return true;
    }
    /**
     *
     * @return
     */
    public float min() {
        return min;
    }
    /**
     *
     * @return
     */
    public float max() {
        return max;
    }
    /**
     *
     * @param _float
     * @return
     */
    public float value(float _float) {
        sum += _float;
        if (_float > max) {
            max = _float;
            maxIndex = count;
        }
        if (_float < min) {
            min = _float;
            minIndex = count;
        }
        count++;
        return _float;
    }
    /**
     *
     */
    public void reset() {
        min = Float.MAX_VALUE;
        max = -Float.MAX_VALUE;

        minIndex = -1;
        maxIndex = -1;

        sum = 0;
        count = 0;
    }
    /**
     *
     * @return
     */
    public long samples() {
        return count;
    }
    /**
     *
     * @return
     */
    public float mean() {
        return sum / (float) count;
    }
    /**
     *
     * @return
     */
    public float range() {
        return max - min;
    }
    /**
     *
     * @return
     */
    public float middle() {
        return min + ((max - min) / 2);
    }
    /**
     *
     * @param _v
     * @param _inclusive
     * @return
     */
    public boolean isBetween(float _v, boolean _inclusive) {
        if (_inclusive) {
            return _v <= max && _v >= min;
        } else {
            return _v < max && _v > min;
        }
    }
    /**
     *
     * @param _float
     * @return
     */
    public float negativeOneToOne(float _float) {
        return (zeroToOne(_float) - 0.5f) * 2f;
    }
    /**
     *
     * @param _float
     * @return
     */
    public float zeroToOne(float _float) {
        return zeroToOne(min, max, _float);
    }
    /**
     *
     * @param _min
     * @param _max
     * @param _float
     * @return
     */
    final public static float zeroToOne(float _min, float _max, float _float) {
        return (_float - _min) / (_max - _min);
    }
    /**
     *
     * @param _float
     * @return
     */
    public float unzeroToOne(float _float) {
        return unzeroToOne(min, max, _float);
    }
    /**
     *
     * @param _min
     * @param _max
     * @param _float
     * @return
     */
    final public static float unzeroToOne(float _min, float _max, float _float) {
        return ((_max - _min) * _float) + _min;
    }
    @Override
    public String toString() {
        return "Min:" + min + " Max:" + max;
    }
}
