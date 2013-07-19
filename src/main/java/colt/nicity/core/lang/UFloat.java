/*
 * UFloat.java.java
 *
 * Created on 12-29-2009 07:28:00 PM
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
public abstract class UFloat {// holder for static utility methods; do not instantiate
    //----------------------------------------------------------------------------
    /**
     *
     * @param _a
     * @param _b
     */
    static public void fill(float[] _a, float[] _b) {
        for (int i = 0; i < _a.length; i++) {
            _a[i] = _b[i];
        }
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param _hits
     * @param _ave
     * @param _smoothing
     * @return
     */
    static public float[] movingAverage(float[] _hits, int _ave, int _smoothing) {
        // _hits are any values >= zero; _ave defines the window for the moving average.
        //----------------------------------------------------------------------------
        for (int i = 0; i < _smoothing; i++) {
            _hits = movingAve(_hits, _ave);
        }
        return _hits;
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param _hits
     * @param _ave
     * @return
     */
    static public float[] movingAve(float[] _hits, int _ave) {
        // computes moving average for _ave hits
        //----------------------------------------------------------------------------
        float[] result = new float[_hits.length];
        for (int i = 0; i < _hits.length; i++) {
            float sum = 0;
            for (int j = -(_ave / 2); j < _ave / 2; j++) {
                int k = i + j;
                if (k < 0) {
                    continue;
                }
                if (k > _hits.length - 1) {
                    continue;
                }
                float h = _hits[k];
                if (h <= 1 && h >= 0) {
                    sum += h;
                }
            }
            result[i] = sum / _ave;
        }
        return result;
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static float range(float _v, float _min, float _max) {
        if (_v < _min) {
            return _min;
        } else if (_v > _max) {
            return _max;
        }
        return _v;
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static float clamp(float _v, float _min, float _max) {
        if (_v < _min) {
            return _min;
        } else if (_v > _max) {
            return _max;
        }
        return _v;
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param _array
     * @param _min
     * @param _max
     * @return
     */
    public static float[] bound(float[] _array, float _min, float _max) {// rename to scale
        int l = _array.length;
        MinMaxFloat minMax = new MinMaxFloat();
        for (int i = 0; i < l; i++) {
            minMax.value(_array[i]);
        }
        //----------------------------------------------------------------------------
        float min = minMax.min();
        float range = minMax.range();
        float _range = _max - _min;
        float[] array = new float[l];
        if (range == 0) {
            return UArray.copy(_array, array);
        }
        for (int i = 0; i < l; i++) {
            array[i] = _min + (_range * ((Math.abs(_array[i] - min)) / range));
        }
        return array;
    }
    //----------------------------------------------------------------------------
    /**
     *
     * @param source
     * @param alternate
     * @return
     */
    static public float checkFloat(float source, float alternate) {
        if (source == Float.POSITIVE_INFINITY ||
                source == Float.NEGATIVE_INFINITY ||
                Float.isNaN(source)) {
            return alternate;
        }
        return source;
    }
    //----------------------------------------------------------------------------
    //static public float[] st(float source, float alternate) {
    //----------------------------------------------------------------------------
}
