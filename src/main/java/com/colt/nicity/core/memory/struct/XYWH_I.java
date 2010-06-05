/*
 * XYWH_I.java.java
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

public class XYWH_I {

    public int x;
    public int y;
    public int w;
    public int h;

    public XYWH_I() {
    }

    public XYWH_I(int _x, int _y, int _w, int _h) {
        x = _x;
        y = _y;
        h = _h;
        w = _w;
    }

    public XYWH_I(float _x, float _y, float _w, float _h) {
        x = (int) _x;
        y = (int) _y;
        h = (int) _h;
        w = (int) _w;
    }

    public static XYWH_I newInstance(int x, int y, int w, int h) {
        XYWH_I r = new XYWH_I();
        r.setX(x);
        r.setY(y);
        r.setW(w);
        r.setH(h);
        return r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public void setW(int _w) {
        w = _w;
    }

    public void setH(int _h) {
        h = _h;
    }

    public boolean contains(XY_I _p) {
        if (_p == null) {
            return false;
        }
        return contains(_p.x, _p.y);
    }

    public boolean contains(int _x, int _y) {
        return (_x >= x) && ((_x - x) < w) && (_y >= y) && ((_y - y) < h);
    }

    public static boolean contains(int _x, int _y, int x, int y, int w, int h) {
        return (_x >= x) && ((_x - x) < w) && (_y >= y) && ((_y - y) < h);
    }

    public XYWH_I growFromCenter(int _amount) {
        return new XYWH_I(x - _amount, y - _amount, w + _amount * 2,
            h + _amount * 2);
    }

    public XYWH_I union(XYWH_I _r) {
        int x1 = Math.min(x, _r.x);
        int x2 = Math.max(x + w, _r.x + _r.w);
        int y1 = Math.min(y, _r.y);
        int y2 = Math.max(y + h, _r.y + _r.h);
        return new XYWH_I(
            x1, y1, x2 - x1, y2 - y1);
    }

    public void union(int _x, int _y, int _w, int _h) {
        if (x == Integer.MIN_VALUE) {//!! hack boandary condition for painting
            x = _x;
            y = _y;
            w = _w;
            h = _h;
            return;
        }

        int x1 = Math.min(x, _x);
        int x2 = Math.max(x + w, _x + _w);
        int y1 = Math.min(y, _y);
        int y2 = Math.max(y + h, _y + _h);

        x = x1;
        y = y1;
        w = x2 - x1;
        h = y2 - y1;
    }

    public boolean intersects(XYWH_I b) {
        int aw = this.w;
        int ah = this.h;
        int bw = b.w;
        int bh = b.h;
        if (bw <= 0 || bh <= 0 || aw <= 0 || ah <= 0) {
            return false;
        }
        int ax = this.x;
        int ay = this.y;
        int bx = b.x;
        int by = b.y;
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
        return x + y + w + h;
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof XYWH_I)) {
            return false;
        }
        XYWH_I r = (XYWH_I) instance;
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
