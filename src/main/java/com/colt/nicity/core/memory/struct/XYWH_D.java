/*
 * XYWH_D.java.java
 *
 * Created on 03-12-2010 11:33:27 PM
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

public class XYWH_D {

    public double x;
    public double y;
    public double w;
    public double h;

    public XYWH_D() {
    }

    public XYWH_D(double _x, double _y, double _w, double _h) {
        x = _x;
        y = _y;
        h = _h;
        w = _w;
    }

    public XYWH_D(float _x, float _y, float _w, float _h) {
        x = (double) _x;
        y = (double) _y;
        h = (double) _h;
        w = (double) _w;
    }

    public static XYWH_D newInstance(double x, double y, double w, double h) {
        XYWH_D r = new XYWH_D();
        r.setX(x);
        r.setY(y);
        r.setW(w);
        r.setH(h);
        return r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setX(double _x) {
        x = _x;
    }

    public void setY(double _y) {
        y = _y;
    }

    public void setW(double _w) {
        w = _w;
    }

    public void setH(double _h) {
        h = _h;
    }

    public boolean contains(XY_I _p) {
        if (_p == null) {
            return false;
        }
        return contains(_p.x, _p.y);
    }

    public boolean contains(double _x, double _y) {
        return (_x >= x) && ((_x - x) < w) && (_y >= y) && ((_y - y) < h);
    }

    public static boolean contains(double _x, double _y, double x, double y,
        double w, double h) {
        return (_x >= x) && ((_x - x) < w) && (_y >= y) && ((_y - y) < h);
    }

    public XYWH_D growFromCenter(double _amount) {
        return new XYWH_D(x - _amount, y - _amount, w + _amount * 2,
            h + _amount * 2);
    }

    public XYWH_D union(XYWH_D _r) {
        double x1 = Math.min(x, _r.x);
        double x2 = Math.max(x + w, _r.x + _r.w);
        double y1 = Math.min(y, _r.y);
        double y2 = Math.max(y + h, _r.y + _r.h);
        return new XYWH_D(
            x1, y1, x2 - x1, y2 - y1);
    }

    public void union(double _x, double _y, double _w, double _h) {
        if (x == Integer.MIN_VALUE) {//!! hack boandary condition for painting
            x = _x;
            y = _y;
            w = _w;
            h = _h;
            return;
        }

        double x1 = Math.min(x, _x);
        double x2 = Math.max(x + w, _x + _w);
        double y1 = Math.min(y, _y);
        double y2 = Math.max(y + h, _y + _h);

        x = x1;
        y = y1;
        w = x2 - x1;
        h = y2 - y1;
    }

    public boolean intersects(XYWH_D b) {
        double aw = this.w;
        double ah = this.h;
        double bw = b.w;
        double bh = b.h;
        if (bw <= 0 || bh <= 0 || aw <= 0 || ah <= 0) {
            return false;
        }
        double ax = this.x;
        double ay = this.y;
        double bx = b.x;
        double by = b.y;
        bw += bx;
        bh += by;
        aw += ax;
        ah += ay;
        return ((bw < bx || bw > ax)
            && (bh < by || bh > ay)
            && (aw < ax || aw > bx)
            && (ah < ay || ah > by));
    }

    @Override
    public int hashCode() {
        return (int) (x + y + w + h);
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof XYWH_D)) {
            return false;
        }
        XYWH_D r = (XYWH_D) instance;
        if (r.x != x || r.y != y || r.w != w || r.h != h) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + x + "," + y + "," + w + "," + h + "]";
    }
}
