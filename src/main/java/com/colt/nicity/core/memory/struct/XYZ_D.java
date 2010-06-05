/*
 * XYZ_D.java.java
 *
 * Created on 03-12-2010 11:25:51 PM
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
package com.colt.nicity.core.memory.struct;

import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.lang.UVector;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class XYZ_D extends ASetObject implements Comparable, IXYZ {

    /**
     *
     */
    public double x;
    /**
     *
     */
    public double y;
    /**
     *
     */
    public double z;

    /**
     *
     */
    public XYZ_D() {
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public XYZ_D(double _x, double _y, double _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    /**
     *
     * @return
     */
    @Override
    public Object hashObject() {
        return this;
    }

    /**
     *
     * @return
     */
    public int getIntX() {
        return x < 0 ? (int) (x - .5) : (int) (x + .5);
    }

    /**
     *
     * @return
     */
    public int getIntY() {
        return y < 0 ? (int) (y - .5) : (int) (y + .5);
    }

    /**
     *
     * @return
     */
    public int getIntZ() {
        return z < 0 ? (int) (z - .5) : (int) (z + .5);
    }

    // IXYZ
    /**
     *
     * @return
     */
    @Override
    public double x() {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public double y() {
        return y;
    }

    /**
     *
     * @return
     */
    @Override
    public double z() {
        return z;
    }

    /**
     *
     * @param _x
     */
    @Override
    public void x(double _x) {
        x = _x;
    }

    /**
     *
     * @param _y
     */
    @Override
    public void y(double _y) {
        y = _y;
    }

    /**
     *
     * @param _z
     */
    @Override
    public void z(double _z) {
        z = _z;
    }

    @Override
    public int hashCode() {
        return (int) ((x + y + z) * Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object _instance) {
        if (_instance instanceof XYZ_D) {
            XYZ_D xyz = (XYZ_D) _instance;
            return (xyz.x == x && xyz.y == y && xyz.z == z);
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + getIntX() + "," + getIntY() + "," + getIntZ();
    }

    /**
     *
     * @return
     */
    public XYZ_D getClone() {
        return new XYZ_D(x, y, z);
    }

    // Comparable
    @Override
    public int compareTo(Object otherXYZ) {
        double thisVal = this.z;
        double otherVal = ((XYZ_D) otherXYZ).z;
        return (otherVal < thisVal ? -1 : (otherVal == thisVal ? 0 : 1));
    }

    //**
    /**
     *
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @return
     */
    public double getScale(double eyeX, double eyeY, double eyeZ) {
        // returns 0=> infinitely far away; 1=> touching your eye
        double sz = z; //?? z-eyeZ;
        if (sz == 0) {
            return 1;
        }
        if (sz < 0) {
            sz *= -1;
        }
        return 1.0 / sz;	//!! cheap approximation is close, but doesn't consider x,y
        //?? what's wrong with this? seems exactly right to me, but visually it is way off!
        //double sx = Math.abs(x-eyeX);
        //double sy = Math.abs(y-eyeY);
        //sz = Math.abs(z-eyeZ);
        //double delta = Math.sqrt(sx*sx + sy*sy);
        //sz = Math.sqrt(delta*delta + sz*sz);
        //return 1.0/sz;
    }

    // All transforms are static and optimized to manipulate arrays of XYZs
    /**
     *
     * @param _xyz
     * @param alpha
     * @param beta
     * @param theta
     */
    public static void rotate(XYZ_D _xyz, double alpha, double beta,
        double theta) { // radians; rotate around x,y,z axis
        rotate(new XYZ_D[]{_xyz}, alpha, beta, theta);
    }

    /**
     *
     * @param _xyzs
     * @param alpha
     * @param beta
     * @param theta
     */
    public static void rotate(XYZ_D[] _xyzs, double alpha, double beta,
        double theta) { // radians; rotate around x,y,z axis

        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);
        double cosBeta = Math.cos(beta);
        double sinBeta = Math.sin(beta);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        // rotate using optimized equilavent of traditional 3-D matrix operations

        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];

            double _x = xyz.x;
            double _y = xyz.y;
            double _z = xyz.z;

            double xx = (cosBeta * cosTheta);
            double xy = (sinAlpha * sinBeta * cosTheta) - (cosAlpha * sinTheta);
            double xz = (cosAlpha * sinBeta * cosTheta) + (sinAlpha * sinTheta);

            double yx = (cosBeta * sinTheta);
            double yy = (sinAlpha * sinBeta * sinTheta) + (cosAlpha * cosTheta);
            double yz = (cosAlpha * sinBeta * sinTheta) - (sinAlpha * cosTheta);

            double zx = (-sinBeta);
            double zy = (sinAlpha * cosBeta);
            double zz = (cosAlpha * cosBeta);

            xyz.x = (_x * xx) + (_y * xy) + (_z * xz);
            xyz.y = (_x * yx) + (_y * yy) + (_z * yz);
            xyz.z = (_x * zx) + (_y * zy) + (_z * zz);

        }
    }

    /**
     *
     * @param _xyz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void translate(XYZ_D _xyz, double _x, double _y, double _z) { // radians; rotate around x,y,z axis
        translate(new XYZ_D[]{_xyz}, _x, _y, _z);
    }

    /**
     *
     * @param _xyzs
     * @param _x
     * @param _y
     * @param _z
     */
    public static void translate(XYZ_D[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x += _x;
            xyz.y += _y;
            xyz.z += _z;
        }
    }

    /**
     *
     * @param _xyz
     * @param _rx
     * @param _ry
     * @param _rz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void move(XYZ_D _xyz, double _rx, double _ry, double _rz,
        double _x, double _y, double _z) {
        move(new XYZ_D[]{_xyz}, _rx, _ry, _rz, _x, _y, _z);
    }

    /**
     *
     * @param _xyzs
     * @param _rx
     * @param _ry
     * @param _rz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void move(XYZ_D[] _xyzs, double _rx, double _ry, double _rz,
        double _x, double _y, double _z) {// object's change in position is a function of object's rx,ry,rz
        XYZ_D delta = new XYZ_D(_x, _y, _z);
        XYZ_D.rotate(delta, _rx, _ry, _rz);
        XYZ_D.translate(_xyzs, delta.x, delta.y, delta.z);
    }

    /**
     *
     * @param _xyz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void scale(XYZ_D _xyz, double _x, double _y, double _z) {
        scale(new XYZ_D[]{_xyz}, _x, _y, _z);
    }

    /**
     *
     * @param _xyzs
     * @param _x
     * @param _y
     * @param _z
     */
    public static void scale(XYZ_D[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x *= _x;
            xyz.y *= _y;
            xyz.z *= _z;
        }
    }

    /**
     *
     * @param _xyz
     * @return
     */
    public static double getLength(XYZ_D _xyz) {
        return Math.sqrt(
            (_xyz.x * _xyz.x) + (_xyz.y * _xyz.y) + (_xyz.z * _xyz.z));
    }

    /**
     *
     * @param _xyz
     * @param _length
     */
    public static void setLength(XYZ_D _xyz, double _length) {
        double d = UDouble.check(
            _length / Math.sqrt(
            (_xyz.x * _xyz.x) + (_xyz.y * _xyz.y) + (_xyz.z * _xyz.z)),
            _length);
        _xyz.x *= d;
        _xyz.y *= d;
        _xyz.z *= d;
    }

    /**
     *
     * @param _xyzs
     * @return
     */
    public static XYZ_D[] getClone(XYZ_D[] _xyzs) {
        XYZ_D[] results = new XYZ_D[_xyzs.length];
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            results[i] = new XYZ_D(xyz.x, xyz.y, xyz.z);
        }
        return results;
    }

    /**
     *
     * @param _xyzs
     */
    public static void sort(XYZ_D[] _xyzs) {
        Arrays.sort(_xyzs);
    }

    /**
     *
     * @param _xyzs
     * @param x
     * @param y
     * @param z
     * @param distance
     */
    public static void perspective(XYZ_D[] _xyzs,
        double x, double y, double z,
        double distance) {
        if (distance == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x = (xyz.x * -distance / xyz.z);
            xyz.y = -(xyz.y * distance / xyz.z);
        }
    }

    /**
     *
     * @param _xyzs
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     */
    public static void undoPerspective(XYZ_D[] _xyzs,
        double eyeX, double eyeY, double eyeZ,
        double distance) {
        if (distance == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x = (xyz.x * xyz.z / -distance);
            xyz.y = -(xyz.y * xyz.z / distance);
        }
    }
    /**
     *
     */
    public static final XYZ_D cOrigin = new XYZ_D(0, 0, 0);
    /**
     *
     */
    public static final XYZ_D cX = new XYZ_D(1, 0, 0);
    /**
     *
     */
    public static final XYZ_D cY = new XYZ_D(0, 1, 0);
    /**
     *
     */
    public static final XYZ_D cZ = new XYZ_D(0, 0, 1);
    /**
     *
     */
    public static final XYZ_D cNegX = new XYZ_D(-1, 0, 0);
    /**
     *
     */
    public static final XYZ_D cNegY = new XYZ_D(0, -1, 0);
    /**
     *
     */
    public static final XYZ_D cNegZ = new XYZ_D(0, 0, -1);

    /**
     *
     * @param _r
     */
    public void setXR(double _r) {
        XYZ_D vector = get3D(cX, cNegY, this).setAbs();
        double hypotenuse = Math.sqrt(
            (vector.x * vector.x) + (vector.y * vector.y));
        z = UDouble.check((Math.sin(_r) / hypotenuse), 0);
        y = UDouble.check((Math.cos(_r) / hypotenuse), 0);
    }

    /**
     *
     * @param _r
     */
    public void setYR(double _r) {
        XYZ_D vector = get3D(cY, cNegZ, this).setAbs();
        double hypotenuse = Math.sqrt(
            (vector.x * vector.x) + (vector.y * vector.y));
        x = UDouble.check((Math.sin(_r) / hypotenuse), 0);
        z = UDouble.check((Math.cos(_r) / hypotenuse), 0);
    }

    /**
     *
     * @param _r
     */
    public void setZR(double _r) {
        XYZ_D vector = get3D(cZ, cNegY, this).setAbs();
        double hypotenuse = Math.sqrt(
            (vector.x * vector.x) + (vector.y * vector.y));
        x = UDouble.check((Math.sin(_r) / hypotenuse), 0);
        y = UDouble.check((Math.cos(_r) / hypotenuse), 0);
    }

    /**
     *
     * @return
     */
    public double getXR() {
        XYZ_D vector = get3D(cX, cNegY, this);
        if (vector.x > 0) {
            if (vector.y > 0) {
                return UVector.cHalfPI - UDouble.check(Math.atan(
                    vector.y / vector.x), 0);
            }
            else {
                return UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
        else {
            if (vector.y < 0) {
                return UVector.cPI + (UVector.cHalfPI - Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0)));
            }
            else {
                return UVector.cPI + UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
    }

    /**
     *
     * @return
     */
    public double getYR() {
        XYZ_D vector = get3D(cY, cNegZ, this);
        if (vector.x > 0) {
            if (vector.y > 0) {
                return UVector.cHalfPI - UDouble.check(Math.atan(
                    vector.y / vector.x), 0);
            }
            else {
                return UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
        else {
            if (vector.y < 0) {
                return UVector.cPI + (UVector.cHalfPI - Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0)));
            }
            else {
                return UVector.cPI + UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
    }

    /**
     *
     * @return
     */
    public double getZR() {
        XYZ_D vector = get3D(cZ, cNegY, this);
        if (vector.x > 0) {
            if (vector.y > 0) {
                return UVector.cHalfPI - UDouble.check(Math.atan(
                    vector.y / vector.x), 0);
            }
            else {
                return UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
        else {
            if (vector.y < 0) {
                return UVector.cPI + (UVector.cHalfPI - Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0)));
            }
            else {
                return UVector.cPI + UVector.cHalfPI + Math.abs(UDouble.check(Math.atan(
                    vector.y / vector.x), 0));
            }
        }
    }

    /**
     *
     * @return
     */
    public XYZ_D setAbs() {
        if (x < 0) {
            x = -x;
        }
        if (y < 0) {
            y = -y;
        }
        if (z < 0) {
            z = -z;
        }
        return this;
    }

    /**
     *
     * @param _v
     * @return
     */
    public double getDot(XYZ_D _v) {
        return x * _v.x + y * _v.y + z * _v.z;
    }

    /**
     *
     * @param _v
     * @return
     */
    public double getAngle(XYZ_D _v) {
        double d = UDouble.check((getDot(_v) / (getLength() * _v.getLength())),
            0);
        if (d < -1D) {
            d = -1D;
        }
        else if (d > 1.0D) {
            d = 1.0D;
        }
        return Math.acos(d);
    }

    /**
     *
     * @return
     */
    public double getLength() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     *
     * @param _length
     */
    public void setLength(double _length) {
        double d = UDouble.check(_length / Math.sqrt(x * x + y * y + z * z),
            _length);
        x *= d;
        y *= d;
        z *= d;
    }

    /**
     *
     * @return
     */
    public XYZ_D getReversedVector() {
        return new XYZ_D(-x, -y, -z);
    }

    // Methods
    /**
     *
     * @param _v
     */
    public void add(XYZ_D _v) {
        x += _v.x;
        y += _v.y;
        z += _v.z;
    }

    /**
     *
     * @param _axis
     * @param _angle
     */
    public void rotate(XYZ_D _axis, double _angle) {
        //!!todo
    }

    /**
     *
     * @param _point
     * @param d
     */
    public final void interpolate(XYZ_D _point, double d) {
        x = (1.0D - d) * x + d * _point.x;
        y = (1.0D - d) * y + d * _point.y;
        z = (1.0D - d) * z + d * _point.z;
    }

    //  Static Methods
    // gets cross product
    /**
     *
     * @param _v
     * @param _v1
     * @return
     */
    public static XYZ_D getPlaneNormal(XYZ_D _v, XYZ_D _v1) {
        double x = _v.y * _v1.z - _v.z * _v1.y;
        double y = _v1.x * _v.z - _v1.z * _v.x;
        double z = _v.x * _v1.y - _v.y * _v1.x;
        return new XYZ_D(x, y, z);
    }

    /**
     *
     * @param _v
     * @param _v1
     * @return
     */
    public static XYZ_D getBisector(XYZ_D _v, XYZ_D _v1) {
        double x = _v.x + _v1.x;
        double y = _v.y + _v1.y;
        double z = _v.z + _v1.z;
        return new XYZ_D(x, y, z);
    }

    /**
     *
     * @return
     */
    public XYZ_D getUnitVector() {
        double d = UDouble.check(1.0D / Math.sqrt(x * x + y * y + z * z), 0);
        return new XYZ_D(x * d, y * d, z * d);
    }

    /**
     *
     * @param _xyzs
     * @param eyeRX
     * @param eyeRY
     * @param eyeRZ
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     * @return
     */
    public static XYZ_D[] sceneToPOV(XYZ_D[] _xyzs,
        double eyeRX, double eyeRY, double eyeRZ,
        double eyeX, double eyeY, double eyeZ,
        double distance) {

        XYZ_D[] xyzs = getClone(_xyzs); // generate pov without modifying the _xyzs scene

        //!!!!! THIS WORKS! need to pass in additional desired rotation point arg
        translate(xyzs, -0, -0, -0);//!! eye's desired rotation point
        rotate(xyzs, eyeRX, eyeRY, eyeRZ);
        translate(xyzs, -eyeX, -eyeY, -eyeZ);
        //doPerspective(xyzs, eyeX, eyeY, eyeZ, distance);
        return xyzs;
    }

    /**
     *
     * @param _xyzs
     * @param eyeRX
     * @param eyeRY
     * @param eyeRZ
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     * @return
     */
    public static XYZ_D[] povToScene(XYZ_D[] _xyzs,
        double eyeRX, double eyeRY, double eyeRZ,
        double eyeX, double eyeY, double eyeZ,
        double distance) {

        XYZ_D[] xyzs = getClone(_xyzs); // generate scene without modifying the pov

        //!!!!! THIS WORKS! need to pass in additional desired rotation point arg
        //undoPerspective(xyzs, eyeX, eyeY, eyeZ, distance);
        translate(xyzs, eyeX, eyeY, eyeZ);
        rotate(xyzs, -eyeRX, -eyeRY, -eyeRZ);
        translate(xyzs, 0, 0, 0);//!! eye's desired rotation point
        return xyzs;
    }

    // Given:
    // _lineOfSight = [0,0,-1]
    // _orientation = [0,-1,0]
    // _vector = [1,1,1]
    // return = [1,1,1]
    /**
     *
     * @param _lineOfSight
     * @param _orientation
     * @param _vector
     * @return
     */
    public static XYZ_D get3D(XYZ_D _lineOfSight, XYZ_D _orientation,
        XYZ_D _vector) {
        double vectorLength = UDouble.check(_vector.getLength(), 0);

        //_lineOfSight = _lineOfSight.getUnitVector();
        //_orientation = _orientation.getUnitVector();
        //_vector = _vector.getUnitVector();

        XYZ_D sightNormal = getPlaneNormal(_lineOfSight, _vector);
        XYZ_D orientationNormal = getPlaneNormal(_lineOfSight, _orientation);

        double steer = orientationNormal.getAngle(_vector);
        double role = sightNormal.getAngle(orientationNormal);
        double rotation = _lineOfSight.getAngle(_vector);

        double hypotenuse = UDouble.check((1 / Math.sin(rotation)), 0);
        double x = UDouble.check((Math.sin(role) / hypotenuse), 0);
        double y = -UDouble.check((Math.cos(role) / hypotenuse), 0);
        double z = -UDouble.check(Math.cos(rotation), 0);
        if (steer < UVector.cHalfPI) {
            x = -x;
        }

        XYZ_D xyz = new XYZ_D(x, y, z);
        xyz.setLength(vectorLength);
        return xyz;
    }
}

