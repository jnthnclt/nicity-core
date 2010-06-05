/*
 * UVector.java.java
 *
 * Created on 01-02-2010 10:11:00 AM
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
package com.colt.nicity.core.lang;

/**
 *
 * @author Administrator
 */
public class UVector {

    /**
     *
     */
    public static final double cPI = Math.PI;
    /**
     *
     */
    public static final double c2PI = cPI * 2;
    /**
     *
     */
    public static final double cHalfPI = cPI / 2;

    /**
     *
     * @param _radAngle
     * @return
     */
    public static double normalize2PI(double _radAngle) {
        if (_radAngle < 0) {
            return c2PI - (_radAngle - (Math.floor(_radAngle / c2PI) * c2PI));
        }
        return _radAngle - (Math.floor(_radAngle / c2PI) * c2PI);
    }

    /**
     *
     * @param _radAngle
     * @return
     */
    public static double normalizePI(double _radAngle) {
        //if (_radAngle < 0) return cPI -(_radAngle-(Math.floor(_radAngle/cPI)*cPI));
        return _radAngle - (Math.floor(_radAngle / cPI) * cPI);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    final static public double dotProd(double[] a, double[] b) {
        return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    final static public double[] vectorMinus(double[] a, double[] b) {
        double r[] = new double[3];
        r[0] = a[0] - b[0];
        r[1] = a[1] - b[1];
        r[2] = a[2] - b[2];
        return r;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    final static public double[] vectorPlus(double[] a, double[] b) {
        double r[] = new double[3];
        r[0] = a[0] + b[0];
        r[1] = a[1] + b[1];
        r[2] = a[2] + b[2];
        return r;
    }

    /**
     *
     * @param a
     * @param t
     * @return
     */
    final static public double[] scalarMult(double[] a, double t) {
        double r[] = new double[3];
        r[0] = a[0] * t;
        r[1] = a[1] * t;
        r[2] = a[2] * t;
        return r;
    }

    /**
     *
     * @param a
     * @param t
     * @param _store
     * @return
     */
    final static public double[] scalarMult(double[] a, double t, double[] _store) {
        _store[0] = a[0] * t;
        _store[1] = a[1] * t;
        _store[2] = a[2] * t;
        return _store;
    }

    /**
     *
     * @param a
     * @param t
     * @return
     */
    final static public double[] scalarDivide(double[] a, double t) {
        double r[] = new double[3];
        r[0] = a[0] / t;
        r[1] = a[1] / t;
        r[2] = a[2] / t;
        return r;
    }

    /**
     *
     * @param v
     * @param w
     * @param t
     * @return
     */
    final static public double[] surfacePoint(double v[], double w[], double t) {
        return vectorPlus(v, scalarMult(w, t));
    }

    /**
     *
     * @param s
     * @param center
     * @param r
     * @return
     */
    final static public double[] surfaceNormal(double s[], double center[], double r) {
        return scalarDivide(vectorMinus(s, center), r);
    }

    /**
     *
     * @param ray
     * @param normal
     * @return
     */
    final static public double[] reflectionVector(double[] ray, double[] normal) {
        return vectorPlus(scalarMult(normal, -2.0 * dotProd(ray, normal)), ray);
    }

    /**
     *
     * @param S
     * @param R
     * @param epsilon
     * @return
     */
    final static public double[] newV(double S[], double R[], double epsilon) {
        return vectorPlus(S, scalarMult(R, epsilon));
    }

    /**
     *
     * @param _from
     * @param _to
     * @return
     */
    final static public double[] vector(double[] _from, double[] _to) {
        return vectorMinus(_to, _from);
    }

    /**
     *
     * @param _vec
     * @return
     */
    final static public double getLength(double[] _vec) {
        return Math.sqrt((_vec[0] * _vec[0]) + (_vec[1] * _vec[1]) + (_vec[2] * _vec[2]));
    }

    /**
     *
     * @param _vx
     * @param _vy
     * @param _vz
     * @return
     */
    final static public double getLength(double _vx, double _vy, double _vz) {
        return Math.sqrt((_vx * _vx) + (_vy * _vy) + (_vz * _vz));
    }

    /**
     *
     * @param _vec
     * @param _length
     */
    final static public void setLength(double[] _vec, double _length) {
        double d = UDouble.check(_length / getLength(_vec), _length);
        _vec[0] *= d;
        _vec[1] *= d;
        _vec[2] *= d;
    }

    /**
     *
     * @param _vec
     * @return
     */
    final static public double[] getUnitVector(double[] _vec) {
        double d = UDouble.check(1.0d / getLength(_vec), 0);
        return new double[]{_vec[0] * d, _vec[1] * d, _vec[2] * d};
    }

    /**
     *
     * @param _vec
     * @return
     */
    final static public double[] getReversedVector(double[] _vec) {
        return new double[]{-_vec[0], -_vec[1], -_vec[2]};
    }

    /**
     *
     * @param _from
     * @param _to
     * @return
     */
    static public double distance(double _from[], double[] _to) {
        double distance = Math.sqrt(
                (_from[0] - _to[0]) * (_from[0] - _to[0])
                + (_from[1] - _to[1]) * (_from[1] - _to[1])
                + (_from[2] - _to[2]) * (_from[2] - _to[2]));
        return distance;
    }

    /**
     *
     * @param _v
     * @param _v1
     * @return
     */
    public static double[] getPlaneNormal(double[] _v, double[] _v1) {
        double x = _v[1] * _v1[2] - _v[2] * _v1[1];
        double y = _v1[0] * _v[2] - _v1[2] * _v[0];
        double z = _v[0] * _v1[1] - _v[1] * _v1[0];
        return new double[]{x, y, z};
    }

    /**
     *
     * @param _v
     * @param _v1
     * @return
     */
    public static double[] getBisector(double[] _v, double[] _v1) {
        double x = _v[0] + _v1[0];
        double y = _v[1] + _v1[1];
        double z = _v[2] + _v1[2];
        return new double[]{x, y, z};
    }
}
