/*
 * UXYWH_D.java.java
 *
 * Created on 03-12-2010 11:25:25 PM
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
package colt.nicity.core.memory.struct;

/**
 *
 * @author Administrator
 */
public class UXYWH_D {

    /**
     *
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     * @return
     */
    public static final XYWH_D rect(double _fx, double _fy, double _tx,
        double _ty) {
        return new XYWH_D(
            Math.min(_fx, _tx),
            Math.min(_fy, _ty),
            Math.abs(_fx - _tx),
            Math.abs(_fy - _ty));
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param _x
     * @param _y
     * @return
     */
    public static final boolean contains(double x, double y, double w, double h,
        double _x, double _y) {
        return (_x >= x) && ((_x - x) < w) && (_y >= y) && ((_y - y) < h);
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public static final boolean contains(double x, double y, double w, double h,
        double _x, double _y, double _w, double _h) {
        if (w <= 0 || w <= 0 || _w <= 0 || _h <= 0) {
            return false;
        }
        return (_x >= x && _y >= y && _x + _w <= x + w && _y + _h <= y + h);
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public static final boolean intersects(double x, double y, double w,
        double h, double _x, double _y, double _w, double _h) {
        return !((_x + _w <= x) || (_y + _h <= y) || (_x >= x + w) || (_y >= y + h));
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public static final XYWH_D intersection(double x, double y, double w,
        double h, double _x, double _y, double _w, double _h) {
        double x1 = Math.max(x, _x);
        double x2 = Math.min(x + w, _x + _w);
        double y1 = Math.max(y, _y);
        double y2 = Math.min(y + h, _y + _h);
        if (((x2 - x1) < 0) || ((y2 - y1) < 0)) {
            return null;
        }
        else {
            return new XYWH_D(x1, y1, x2 - x1, y2 - y1);
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public static final XYWH_D union(double x, double y, double w, double h,
        double _x, double _y, double _w, double _h) {
        double x1 = Math.min(x, _x);
        double x2 = Math.max(x + w, _x + _w);
        double y1 = Math.min(y, _y);
        double y2 = Math.max(y + h, _y + _h);
        return new XYWH_D(x1, y1, x2 - x1, y2 - y1);
    }
}
