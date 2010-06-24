/*
 * UMath.java.java
 *
 * Created on 01-02-2010 10:10:00 AM
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
package colt.nicity.core.lang;

import colt.nicity.core.collection.CArray;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import java.awt.Polygon;

/**
 *
 * @author Administrator
 */
public class UMath {
    // Taimoto presence of absence

    /**
     *
     * @param v1
     * @param v2
     * @return
     */
    static public double tanamotoCorrelation(double[] v1, double[] v2) {
        double c1 = 0;
        double c2 = 0;
        double both = 0;
        for (int i = 0; i < v1.length; i++) {
            if (v1[i] != 0) {
                c1++;
            }
            if (v2[i] != 0) {
                c2++;
            }
            if (v1[i] != 0 && v2[i] != 0) {
                both++;
            }
        }
        return 1.0d - (both / (c1 - c2 - both));
    }

    // Pearson Correlation
    // v1.length must equal v2.length
    // 0 worst 1 best
    /**
     *
     * @param v1
     * @param v2
     * @return
     */
    static public double pearsonCorrelation(double[] v1, double[] v2) {
        double sum1 = sum(v1);
        double sum2 = sum(v2);
        double pSum = productSum(v1, v2);
        double numerator = pSum - (sum1 * sum2 / v1.length);

        double sum1Sq = powerSum(2, v1);
        double sum2Sq = powerSum(2, v2);
        double denominator = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / v1.length) * (sum2Sq - Math.pow(sum2, 2) / v1.length));
        if (denominator == 0) {
            return 0;
        }
        return numerator / denominator;
    }

    /**
     *
     * @param vs
     * @return
     */
    static public double sum(double... vs) {
        double v = 0;
        for (double add : vs) {
            v += add;
        }
        return v;
    }

    /**
     *
     * @param _power
     * @param vs
     * @return
     */
    static public double powerSum(double _power, double... vs) {
        double v = 0;
        for (double add : vs) {
            v += Math.pow(add, _power);
        }
        return v;
    }
    // v1.length must equal v2.length

    /**
     *
     * @param v1
     * @param v2
     * @return
     */
    static public double productSum(double[] v1, double[] v2) {
        double v = 0;
        for (int i = 0; i < v1.length; i++) {
            v += v1[i] * v2[i];
        }
        return v;
    }

    /**
     *
     * @param _bx
     * @param _by
     * @param _bw
     * @param _bh
     * @param _x
     * @param _y
     * @return
     */
    public static boolean pointContainedByRectOval(double _bx, double _by, double _bw, double _bh, double _x, double _y) {
        double xc = _bx + (_bw / 2);
        double yc = _by + (_bh / 2);

        double dx = (double) (_x - xc);
        double dy = (double) (_y - yc);

        return ((dx / _bw) * (dx / _bw) + (dy / _bh) * (dy / _bh)) <= 0.25;
    }

    /**
     *
     * @param _circumference
     * @return
     */
    public static double circumferenceToDiameter(double _circumference) {
        return _circumference / Math.PI;
    }

    /**
     *
     * @param _diameter
     * @return
     */
    public static double circumference(double _diameter) {
        return Math.PI * _diameter;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    final public static double computeAngle(double x, double y) {
        double angle = Math.atan(y / x);
        if (x == 0) {
            if (y > 0) {
                angle = Math.PI / 2;
            } else {
                angle = -Math.PI / 2;
            }
        }
        if (x < 0) {
            angle += Math.PI;
        }
        return angle;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    final public static double pathagorus(double x, double y) {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     *
     * @param ls
     * @param le
     * @param px
     * @param py
     * @param allowOnExtension
     * @return
     */
    public static XY_I getClosestPoint(XY_I ls, XY_I le, double px, double py, boolean allowOnExtension) {
        // If the two points represent the same point then
        // they are the closest point.
        if (ls.equals(le)) {
            return new XY_I(ls.x, ls.y);// clone
            // Compute the relative position of the closest point to the point 'p'
            // u = ((p - pt1) . (pt2 - pt1)) / ((pt2 - pt1) . (pt2 - pt1))
            // where '.' is the vector dot product
        }
        double u = ((px - ls.x) * (le.x - ls.x) + (py - ls.y) * (le.y - ls.y)) / (sqr(le.x - ls.x) + sqr(le.y - ls.y));

        // Remove this conditional statement if you allow the closest point to be
        // exterior to the direct line between pt1 and pt2.
        if (!allowOnExtension) {
            if (u >= 1.0) {
                return new XY_I(le.x, le.y);// clone
            } else if (u <= 0.0) {
                return new XY_I(ls.x, ls.y);// clone
            }
        }

        // Create the closest point
        return new XY_I(roundToInt(le.x * u + ls.x * (1.0 - u)), roundToInt(le.y * u + ls.y * (1.0 - u)));
    }

    private static double sqr(double x) {
        return x * x;
    }

    private static int roundToInt(double v) {
        return (int) (v + 0.5);
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static boolean equalSign(double _a, double _b) {
        if (_a == _b) {
            return true;
        }
        if (_a < 0 && _b < 0) {
            return true;
        }
        if (_a > 0 && _b > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param _r
     * @return
     */
    public static double circleArea(double _r) {
        return Math.PI * Math.pow(_r, 2);
    }

    /**
     *
     * @param _length
     * @return
     */
    public static long powerFit(long _length) {
        long blockPower = 0;
        for (long i = 1; i < 65; i++) { // 2^64 == long so why go anyfuther
            if (_length < Math.pow(2, i)) {
                return i;
            }
        }
        return 64;
    }

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static boolean inRange(int _v, int _min, int _max) {
        if (_v < _min) {
            return false;
        }
        if (_v > _max) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param _angle
     * @param _length
     * @return
     */
    public static double[] vector(double _angle, double _length) {
        return vector(_angle, _length, new double[2]);
    }

    /**
     *
     * @param _radians
     * @param _length
     * @param _xy
     * @return
     */
    public static double[] vector(double _radians, double _length, double[] _xy) {
        _radians -= Math.PI;
        _xy[0] = (Math.cos(_radians) * _length);
        _xy[1] = (Math.sin(_radians) * _length);
        return _xy;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static double angle(double x, double y) {// 0 to 2pi radians relative to center at .5,.5 using points between 0-1

        return angle(x, y, .5d, .5d);
    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double angle(double x1, double y1, double x2, double y2) {// 0-360 angle between any two points
        //!! validated to return correct angle using java convention where zero is at 3:00 and increases clockwise thru full circle
        // atan is never called with args that could result in NaN
        // atan is never called with args where results are known to be exactly n*pi/2 

        double dx = x1 - x2;
        double dy = y1 - y2;
        double a = 0;
        double pi = Math.PI;
        if (dx == 0 && dy == 0) { // known to be n*pi/2 

            a = 0;
        } else if (dx == 0) {// known to be n*pi/2 

            if (dy > 0) {
                a = pi / 2;
            } else {
                a = 3 * pi / 2;
            }
        } else if (dy == 0) {// known to be n*pi/2 

            if (dx > 0) {
                a = 0;
            } else {
                a = pi;
            }
        } else { // use atan 

            if (dx < 0) {
                a = Math.atan(dy / dx) + pi;
            } else if (dy < 0) {
                a = Math.atan(dy / dx) + 2 * pi;
            } else {
                a = Math.atan(dy / dx);
            }
        }
        return a;
    }

    /*

    public static void main(String[] args) { // test angle
    System.out.println("---");
    for (double y=0; y<1; y+=.25) {
    System.out.println(Math.toDegrees(angle(0,y)));
    }
    System.out.println("---");
    for (double x=0; x<1; x+=.25) {
    System.out.println(Math.toDegrees(angle(x,1)));
    }
    System.out.println("---");
    for (double y=1; y>0; y-=.25) {
    System.out.println(Math.toDegrees(angle(1,y)));
    }
    System.out.println("---");
    for (double x=1; x>0; x-=.25) {
    System.out.println(Math.toDegrees(angle(x,0)));
    }
    }
     */
    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double middle(double a, double b) {
        return middle(a, b, 0.5d);
    }

    /**
     *
     * @param a
     * @param b
     * @param _percentage
     * @return
     */
    public static double middle(double a, double b, double _percentage) {
        //double gap = Math.max(a,b)-Math.min(a,b);
        //double mid = Math.min(a,b)+(gap*_percentage);
        //return mid;
        //return a+((a-b)*_percentage);
        double gap = a - b;
        double mid = a - (gap * _percentage);
        return mid;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    final public static double fastMiddle(double a, double b) {
        return (a + b) / 2;
    }

    /**
     *
     * @param a
     * @param b
     * @param _percentage
     * @return
     */
    final public static double fastMiddle(double a, double b, double _percentage) {
        return (a + b) * _percentage;
    }

    /**
     *
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     * @return
     */
    public static double[] middle(double sx, double sy, double ex, double ey) {
        return middle(sx, sy, ex, ey, 0.5d);
    }

    /**
     *
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     * @param _percentage
     * @return
     */
    public static double[] middle(double sx, double sy, double ex, double ey, double _percentage) {
        double gapX = (ex - sx);
        double gapY = (ey - sy);
        double midX = sx + (gapX * _percentage);
        double midY = sy + (gapY * _percentage);
        return new double[]{midX, midY};
    }

    /**
     *
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     * @return
     */
    final public static double[] fastMiddle(double sx, double sy, double ex, double ey) {
        return new double[]{fastMiddle(sx, ex), fastMiddle(sy, ey)};
    }

    /**
     *
     * @param _w
     * @param _h
     * @return
     */
    public static double circumscribeRadius(double _w, double _h) {
        return Math.sqrt(Math.pow(_w / 2, 2) + Math.pow(_h / 2, 2));
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _radians
     * @return
     */
    public static double[] rectanglePerimeter(double _w, double _h, double _radians) {
        if (_w == 0 || _h == 0) {
            return new double[]{0, 0};
        }
        _radians = normalizeAngle(_radians);

        _w /= 2;
        _h /= 2;

        double a = Math.atan(_h / _w);
        double b = Math.atan(_w / _h);

        if (_radians > Math.PI + (a + (b * 2))) {
            _radians = a - (_radians - (Math.PI + (a + (b * 2))));
            double x = _w;
            double y = (Math.tan(_radians) * _w);
            return new double[]{-x, y};
        } else if (_radians > Math.PI + (a + b)) {
            _radians = (_radians - (Math.PI + (a + b)));
            double x = (Math.tan(_radians) * _h);
            double y = _h;
            return new double[]{-x, y};
        } else if (_radians > Math.PI + a) {
            _radians = b - (_radians - (Math.PI + a));
            double x = -(Math.tan(_radians) * _h);
            double y = _h;
            return new double[]{-x, y};
        } else if (_radians > Math.PI) {
            _radians = _radians - Math.PI;
            double x = -_w;
            double y = (Math.tan(_radians) * _w);
            return new double[]{-x, y};
        } else if (_radians > a + (b * 2)) {
            _radians = a - (_radians - (a + (b * 2)));
            double x = -_w;
            double y = -(Math.tan(_radians) * _w);
            return new double[]{-x, y};
        } else if (_radians > a + b) {
            _radians = (_radians - (a + b));
            double x = -(Math.tan(_radians) * _h);
            double y = -_h;
            return new double[]{-x, y};
        } else if (_radians > a) {
            _radians = b - (_radians - a);
            double x = (Math.tan(_radians) * _h);
            double y = -_h;
            return new double[]{-x, y};
        } else { // first quad

            double x = _w;
            double y = -(Math.tan(_radians) * _w);
            return new double[]{-x, y};
        }
    }

    /**
     *
     * @param _radians
     * @return
     */
    public static double normalizeAngle(double _radians) {
        if (Math.abs(_radians) > (Math.PI * 2)) {
            long count = (long) (_radians / (Math.PI * 2));
            _radians -= count * (Math.PI * 2);
        }
        if (_radians < 0) {
            _radians = (Math.PI * 2) + _radians;
        }
        return _radians;
    }

    /**
     *
     * @param _s
     * @param _e
     * @return
     */
    public static double distance(double _s, double _e) {
        return Math.abs(_s - _e);
    }

    /**
     *
     * @param _x1
     * @param _y1
     * @param _x2
     * @param _y2
     * @return
     */
    public static double distance(double _x1, double _y1, double _x2, double _y2) {
        double x = _x1 - _x2;
        double y = _y1 - _y2;
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     *
     * @param _x1
     * @param _y1
     * @param _x2
     * @param _y2
     * @return
     */
    public static int distance(int _x1, int _y1, int _x2, int _y2) {
        int x = _x1 - _x2;
        int y = _y1 - _y2;
        return (int) Math.sqrt((x * x) + (y * y));
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double nDimDistance(double[] _a, double[] _b) {
        int l = _a.length;
        double squaredTotal = 0;
        for (int i = 0; i < l; i++) {
            double delta = _a[i] - _b[i];
            squaredTotal += delta * delta;
        }
        return Math.sqrt(squaredTotal);
    }

    // some 3D math functions; use instead of the threeD.XYZ methods
    /**
     *
     * @param _x1
     * @param _y1
     * @param _z1
     * @param _x2
     * @param _y2
     * @param _z2
     * @return
     */
    final public static double distance(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2) {
        double x = _x1 - _x2;
        double y = _y1 - _y2;
        double z = _z1 - _z2;
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    // return a unit vector
    /**
     *
     * @param _x1
     * @param _y1
     * @param _z1
     * @return
     */
    public static double[] normalize(double _x1, double _y1, double _z1) {
        double[] result = new double[3];
        double d = distance(_x1, _y1, _z1, 0, 0, 0);
        result[0] = _x1 / d;
        result[1] = _y1 / d;
        result[2] = _z1 / d;
        return result;
    }

    /**
     *
     * @param _x1
     * @param _y1
     * @param _z1
     * @param _x2
     * @param _y2
     * @param _z2
     * @return
     */
    public static double dot(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2) {
        return (_x1 * _x2) + (_y1 * _y2) + (_z1 * _z2);
    }

    // cross product returns vector that is orthogonal to other two vectors
    /**
     *
     * @param _x1
     * @param _y1
     * @param _z1
     * @param _x2
     * @param _y2
     * @param _z2
     * @return
     */
    public static double[] cross(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2) {
        double[] result = new double[3];
        result[0] = (_y1 * _z2) - (_z1 * _y2);
        result[1] = (_z1 * _x2) - (_x1 * _z2);
        result[2] = (_x1 * _y2) - (_y1 * _x2);
        return result;
    }

    /*
    public static double dot(IXYZ _xyz1, IXYZ _xyz2) {
    return (_xyz1.x() * -_xyz2.x()) + (_xyz1.y() * -_xyz2.y()) + (_xyz1.z() * -_xyz2.z());
    }
    public static IXYZ cross(IXYZ _xyz1, IXYZ _xyz2) {
    XYZ result = new XYZ();
    ;
    result.x = (_xyz1.y() * _xyz2.z()) - (_xyz1.z() * _xyz2.y());
    result.y = (_xyz1.z() * _xyz2.x()) - (_xyz1.x() * _xyz2.z());
    result.z = (_xyz1.x() * _xyz2.y()) - (_xyz1.y() * _xyz2.x());
    return result;
    }
    

    public static void main(String[] args) {
    for(double x=0;x<1;x+=.1) {
    for(double y=0;y<1;y+=.1) {
    double a2d = angle(x,y,.5,.5);
    double a3d = angle(x,y,0,.5,.5,0);
    System.out.println((a2d-a3d)+" = "+a2d+" - "+a3d);
    }
    }
    }

    // angle = acos(dot(_xyz1,_xyz2) / (distance(_xyz1,center)*distance(_xyz1,center));
    public static double angle(IXYZ _xyz1, IXYZ _xyz2) {
    double _x1 = _xyz1.x();
    double _y1 = _xyz1.y();
    double _z1 = _xyz1.z();
    double _x2 = _xyz2.x();
    double _y2 = _xyz2.y();
    double _z2 = _xyz2.z();
    return angle(_x1, _y1, _z1, _x2, _y2, _z2);
    }*/
    //!! bug?? does not agree with 2d angle; see test main	
    // angle = acos(dot(_xyz1,_xyz2) / (distance(_xyz1,center)*distance(_xyz1,center)); 	
    /**
     *
     * @param _x1
     * @param _y1
     * @param _z1
     * @param _x2
     * @param _y2
     * @param _z2
     * @return
     */
    public static double angle(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2) {
        double dot = dot(_x1, _y1, _z1, _x2, _y2, _z2);
        double d1 = distance(_x1, _y1, _z1, 0, 0, 0);
        double d2 = distance(_x2, _y2, _z2, 0, 0, 0);
        if (dot == 0 || d1 == 0 || d2 == 0) {
            return 0; // NaN

        }
        return Math.acos(dot / (d1 * d2));
    }

    /**
     *
     * @param _samples
     * @param _window
     * @return
     */
    public static double correlation(double[] _samples, int _window) {
        // _samples are ANY doubles (not necessarily between 0 and 1)
        // returns 0 if perfect negative correlation
        // returns .5 if pure random noise
        // returns 1 if perfect positive

        if (_window < 2) {
            return 0; // no correlation possible

        }
        if (_window >= _samples.length) {
            return 0; // no correlation possible

        }
        int i = 0;
        double[] a = UArray.copy(_samples, i, _window);
        i += _window;
        double cc = 0;
        int count = 0;
        for (; i < _samples.length - _window; i += _window) {
            double[] b = UArray.copy(_samples, i, _window);
            cc += correlationCoefficient(a, b);
            count++;
        }
        if (count == 0) {
            return 0;
        }
        return cc / count;
    }

    /**
     *
     * @param _samples
     * @param _window
     * @return
     */
    public static double correlation2(double[] _samples, int _window) {
        // _samples are ANY doubles (not necessarily between 0 and 1)
        // returns 0 if perfect negative correlation
        // returns .5 if pure random noise
        // returns 1 if perfect positive

        if (_window < 2) {
            return .5; // no correlation possible

        }
        double flat = 0.001; // any slope between -flat and +flat is considered flat. 

        int max = _samples.length / _window;
        if (max < 1) {
            return 0;
        }
        double possible = 0;
        double actual = 0;
        for (int i = 0; i < ((max - 1) * _window); i += _window) {
            for (int j = i + _window; j < max * _window; j += _window) {
                for (int k = 0; k < _window; k++) {
                    if (i + k == 0) {
                        continue; // skips first val because it has no history

                    }
                    double slope1 = _samples[i + k] - _samples[i + k - 1];
                    double slope2 = _samples[j + k] - _samples[j + k - 1];
                    possible++;
                    if (slope1 > flat && slope2 > flat) {
                        actual++;
                    } else if (slope1 < -flat && slope2 < -flat) {
                        actual++;
                    } else if (slope1 < flat && slope1 > -flat && slope2 < flat && slope2 > -flat) {
                        actual++;
                    }
                }
            }
        }
        return actual / possible;
    }

    /**
     *
     * @param _samples
     * @param _windows
     * @param _correlations
     * @return
     */
    public static int[] bestCorrelation(double[] _samples, int[] _windows, double[] _correlations) {
        int bestWindow = 0;
        int worstWindow = 0;
        double bestCorrelation = 0;
        double worstCorrelation = 1.0;
        for (int i = 0; i < _windows.length; i++) {
            double correlation = correlation(_samples, _windows[i]);
            if (_correlations.length > i) {
                _correlations[i] = correlation;
            }
            if (correlation > bestCorrelation) {
                bestCorrelation = correlation;
                bestWindow = i;
            }
            if (correlation < worstCorrelation) {
                worstCorrelation = correlation;
                worstWindow = i;
            }
        }
        int[] result = new int[2];
        result[0] = bestWindow;
        result[1] = worstWindow;
        return result;
    }

    /*
    
    public static void main(String[] args) {
    int num = 1000;
    System.out.println("test correlation using samples = "+num);
    double[] samples = new double[num];
    double[] correlations = new double[num];
    int[] windows = new int[365];
    double max = 1.0d;
    if (args==null || args.length==0) {
    System.out.println("no pattern; pure noise");
    for (int i=0; i<samples.length; i++) {
    samples[i] = URandom.rand(max);
    }
    }
    else {
    System.out.println("strong pattern for many windows");
    for (int i=0; i<samples.length-2; i+=3) {
    double sample = URandom.rand(max);
    samples[i] = sample;
    samples[i+1] = sample/2;
    samples[i+2] = sample/3;
    }
    }
    for (int i=0; i<windows.length; i++) {
    windows[i] = i;
    }
    int[] result = bestCorrelation(samples,windows,correlations);
    System.out.println("best="+correlations[result[0]]+" at window="+result[0]);
    System.out.println("worst="+correlations[result[1]]+" at window="+result[1]);
    }
     */
    /**
     *
     * @param _set
     * @param _num
     * @param _max
     * @return
     */
    public static CArray permutations(Object[] _set, int _num, int _max) {
        // given a collection of _set things (like cards), returns all the unordered permutations
        // (like a hand of poker) taking _num things at a time. Returns one Object[_num] 
        // for every permutation possible, unless stopped by _max permutations. Use _max<0 for all.
        CArray result = new CArray();
        int[] _it = new int[_num]; // _num nested iteration loops 

        int _level = 0; // the level of a nested loop, 0.._num-1

        _permutations(result, _set, _num, _max, _level, _it);
        return result;
    }

    private static void _permutations(CArray _result, Object[] _set, int _num, int _max, int _level, int[] _it) {
        if (_max > 0 && _result.getCount() > _max) {
            return;
        }
        if (_level < _num) { // iteratively recurse; equivalent to _num nested loops 

            if (_level > 0) {
                _it[_level] = _it[_level - 1] + 1; // start iteration at one more than caller's current iteration

            }
            for (; _it[_level] < _set.length - (_num - (_level + 1)); _it[_level]++) {
                _permutations(_result, _set, _num, _max, _level + 1, _it); // next nested loop	

            }
        } else { // save nextP

            Object[] oneAnswer = new Object[_num];
            for (int s = 0; s < _num; s++) {
                oneAnswer[s] = _set[_it[s]];
            }
            _result.insertLast((Object) oneAnswer);
        }
    }


    /*	

    public static void main(String[] args) {// test permutations
    String[] set = new String[4];
    for (int i=0;i<set.length;i++) set[i] = " "+i;
    
    long start = System.currentTimeMillis();
    CArray permutations = permutations(set,3,-1);
    long end = System.currentTimeMillis();
    
    System.out.println("permutations="+permutations.getCount()+" in msecs="+(end-start));
    for (int i=0; i<10; i++) {
    Object[] p = (Object[]) permutations.getAt(i);
    if (p==null) break;
    String s = "";
    for (int j=0; j<p.length; j++) {
    s+=p[j].toString();
    }
    System.out.println(i+"\t"+s);
    }					
    }
     */
    /**
     *
     * @param _set
     * @param _num
     * @param _max
     * @return
     */
    public static CArray combinations(Object[] _set, int _num, int _max) {
        // like permutations but considers order too, so returns A,B AND B,A
        CArray result = new CArray();
        int[] _it = new int[_num]; // _num nested iteration loops 

        int _level = 0; // the level of a nested loop, 0.._num-1

        _combinations(result, _set, _num, _max, _level, _it);
        return result;
    }

    private static void _combinations(CArray _result, Object[] _set, int _num, int _max, int _level, int[] _it) {
        if (_max > 0 && _result.getCount() > _max) {
            return;
        }
        if (_level < _num) { // iteratively recurse; equivalent to _num nested loops 

            loop:
            for (_it[_level] = 0; _it[_level] < _set.length; _it[_level]++) {
                for (int i = 0; i < _num; i++) {// avoids duplication of same member; i.e. A,B,A

                    if (_level > i && _it[_level] == _it[_level - (i + 1)]) {
                        continue loop;
                    }
                }
                _combinations(_result, _set, _num, _max, _level + 1, _it); // next nested loop	

            }
        } else { // save nextP

            Object[] oneAnswer = new Object[_num];
            for (int s = 0; s < _num; s++) {
                oneAnswer[s] = _set[_it[s]];
            }
            _result.insertLast((Object) oneAnswer);
        }
    }

    /*

    public static void main(String[] args) {// test combinations
    String[] set = new String[3];
    for (int i=0;i<set.length;i++) set[i] = " "+i;
    
    long start = System.currentTimeMillis();
    CArray combinations = combinations(set,3,-1);
    long end = System.currentTimeMillis();
    
    System.out.println("combinations="+combinations.getCount()+" in msecs="+(end-start));
    for (int i=0; i<20; i++) {
    Object[] p = (Object[]) combinations.getAt(i);
    if (p==null) break;
    String s = "";
    for (int j=0; j<p.length; j++) {
    s+=p[j].toString();
    }
    System.out.println(i+"\t"+s);
    }					
    }
     */
    /**
     *
     * @param _type
     * @param _samples
     * @param _start
     * @param _length
     * @param _cycle
     * @param _amplitude
     * @param _offset
     */
    public static void wave(
            int _type, double[] _samples,
            int _start, int _length,
            int _cycle, double _amplitude, int _offset) {
        // _type==0 => sin +_amplitude to 0
        // _type==1 => sin +_amplitude to -_amplitude
        // _type==2 => cos +_amplitude to 0
        // _type==3 => cos +_amplitude to -_amplitude
        double[] wave = new double[_cycle];
        for (int i = 0; i < _cycle; i++) {
            double a = 360.0d;
            a *= i;
            a /= _cycle;
            if (_type < 0 || _type > 3) {
                return;
            } else if (_type == 0) {
                wave[i] = _amplitude * ((1 + Math.sin(Math.toRadians(a))) / 2);
            } else if (_type == 1) {
                wave[i] = _amplitude * Math.sin(Math.toRadians(a));
            } else if (_type == 2) {
                wave[i] = _amplitude * ((1 + Math.cos(Math.toRadians(a))) / 2);
            } else if (_type == 3) {
                wave[i] = _amplitude * Math.cos(Math.toRadians(a));
            }
        }
        if (_start + _length >= _samples.length) {
            _length = _samples.length - _start;
        }
        for (int i = _start; i < _start + _length; i++) {
            _samples[i] = wave[(i + _offset) % _cycle];
        }
    }
    /*

    public static void main(String[] args) { // test wave generator
    int len = 80;
    double[] d = new double[len];
    wave(0,d,0,len/4,20,1.0d,0);
    wave(1,d,len/4,len/2,20,1.0d,0);
    wave(2,d,len/2,3*len/4,20,1.0d,0);
    wave(3,d,3*len/4,len,20,1.0d,0);
    for (int i=0; i<len; i++) {
    if (i%(len/4)==0) System.out.println("---");
    System.out.println(i+"="+d[i]);
    }
    }
     */

    /**
     *
     * @param _d
     * @return
     */
    public static double signChanges(double[] _d) {
        if (_d.length < 3) {
            return 0;
        }
        double result = 0;
        double a = _d[0];
        double b = _d[1];
        for (int i = 2; i < _d.length; i++) {
            double c = _d[i];
            if ((a - b < 0 && b - c > 0) || (a - b > 0 && b - c < 0)) {
                result++;
            }
            a = b;
            b = c;
        }
        return result;
    }

    /**
     *
     * @param _d
     * @return
     */
    public static double[] deltas(double[] _d) {
        if (_d.length < 2) {
            return new double[]{0};
        }
        double[] deltas = new double[_d.length - 1];
        for (int i = 1; i < _d.length; i++) {
            deltas[i - 1] = _d[i] - _d[i - 1];
        }
        return deltas;
    }

    // input: equal number of raw x,y coordinates
    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public static double[] slopes(double[] _x, double[] _y) {
        if (_x.length < 2) {
            return new double[]{0};
        }
        double[] x = deltas(_x);
        double[] y = deltas(_y);
        double[] slopes = new double[x.length];
        for (int i = 0; i < slopes.length; i++) {
            double s = 0;
            if (x[i] != 0) {
                s = y[i] / x[i];
            }
            slopes[i] = s;
//System.out.println("slope="+s+"   "+y[i]+" / "+x[i]);			
        }
        return slopes;
    }

    // input: equal number of raw x,y coordinates
    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public static double[] distances(double[] _x, double[] _y) {
        if (_x.length < 2) {
            return new double[]{0};
        }
        double[] distances = new double[_x.length - 1];
        for (int i = 0; i < distances.length - 1; i++) {
            distances[i] = distance(_x[i], _y[i], _x[i + 1], _y[i + 1]);
        }
        return distances;
    }

    // input: two arbitrary sets of values, any size; output: 0-1 correlation of bell curves 
    // correlates standard deviations by comparing the _a and _b bell curves with the composite bell curve  
    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double stdCorrelation(double[] _a, double[] _b) {

        double sumA = 0;
        double sumB = 0;
        double aveA = 0;
        double aveB = 0;
        double aveAB = 0;
        double devA = 0;
        double devB = 0;

        int count = _a.length + _b.length;

        for (int i = 0; i < _a.length; i++) {
            sumA += _a[i];
        }
        aveA = sumA / _a.length;
        for (int i = 0; i < _a.length; i++) {
            devA += ((_a[i] - aveA) * (_a[i] - aveA));
        }
        for (int i = 0; i < _b.length; i++) {
            sumB += _b[i];
        }
        aveB = sumB / _b.length;
        for (int i = 0; i < _b.length; i++) {
            devB += ((_b[i] - aveB) * (_b[i] - aveB));
        }
        aveAB = (sumA + sumB) / count;
        // Standard Deviation = Square root(sum of squared deviations / (N-1)
        double std = Math.sqrt((devA + devB) / (count - 2));

        double[] deviationsA = stdDeviations(_a, std, aveAB);
        double[] deviationsB = stdDeviations(_b, std, aveAB);

        return correlateDeviations(deviationsA, deviationsB);
    }
    /**
     *
     */
    public static final int numStdDeviations = 10;

    /**
     *
     * @param _d
     * @param _std
     * @param _ave
     * @return
     */
    public static double[] stdDeviations(double[] _d, double _std, double _ave) {
        double[] d = new double[numStdDeviations]; // d[4] d[5] is bell curve += 1/2 of one standard deviation

        for (int i = 0; i < _d.length; i++) {
            double v = _d[i];
            if (v < _ave - 2 * _std) {
                d[0]++;
            } else if (v < _ave - 1.5 * _std) {
                d[1]++;
            } else if (v < _ave - 1 * _std) {
                d[2]++;
            } else if (v < _ave - .5 * _std) {
                d[3]++;
            } else if (v < _ave) {
                d[4]++;
            } else if (v < _ave + .5 * _std) {
                d[5]++;
            } else if (v < _ave + 1 * _std) {
                d[6]++;
            } else if (v < _ave + 1.5 * _std) {
                d[7]++;
            } else if (v < _ave + 2 * _std) {
                d[8]++;
            } else {
                d[9]++;
            }
        }
        for (int i = 0; i < d.length; i++) {
            d[i] /= _d.length;
        }
        return d;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double correlateDeviations(double[] _a, double[] _b) {
        double result = 0;
        for (int i = 0; i < numStdDeviations; i++) {
            double a = _a[i];
            double b = _b[i];
            double f = 0;
            if (a < b && b > 0) {
                f = a / b;
            } else if (a > 0) {
                f = b / a;
            }
            result += f;
        }
        result /= (numStdDeviations);
        return result;
    }

    /**
     *
     * @param _carrier
     * @param _signal
     * @return
     */
    public static double[] extractSignal(double[] _carrier, double[] _signal) {
        double[] results = new double[_signal.length];
        for (int i = 0; i < _signal.length; i++) {
            if (i >= _carrier.length) {
                results[i] = _signal[i];
            } else {
                results[i] = _carrier[i] - _signal[i];
            }
        }
        return results;
    }

    /**
     *
     * @param _carrier
     * @param _signal
     * @param _down
     * @param _off
     * @param _up
     * @return
     */
    public static double[] extractCrossings(
            double[] _carrier, double[] _signal,
            double _down, double _off, double _up) {
        // 0=> does not cross carrier; 1=> signal crosses going up; -1=> signal crosses going down
        double[] results = new double[_signal.length];
        for (int i = 0; i < _signal.length - 1; i++) {
            if (i >= _carrier.length) {
                results[i] = _off;
                continue;
            }
            double s1 = _signal[i];
            double s2 = _signal[i + 1];
            double c1 = _carrier[i];
            double c2 = _carrier[i + 1];
            if (s1 <= c1 && s2 >= c2) {
                results[i] = _up;
            } else if (s1 >= c1 && s2 <= c2) {
                results[i] = _down;
            } else {
                results[i] = _off;
            }
        }
        return results;
    }

    // Standard Deviation = Square root(sum of squared deviations / number of items)
    // ~68.2% of the data are between +- 0 and 1 standard deviations of the mean.
    // ~27.2% of the data are between +- 1 and 2 standard deviations of the mean.
    // ~ 4.2% of the data are between +- 2 and 3 standard deviations of the mean.
    // ~  .3% of the data are beyond  +- 3 standard deviations of the mean.
    // a standard statistical function
    /**
     *
     * @param _d
     * @return
     */
    public static double mean(double[] _d) {
        double mean = 0;
        for (int i = 0; i < _d.length; i++) {
            mean += _d[i];
        }
        mean /= _d.length;
        return mean;
    }

    // a standard statistical function
    /**
     *
     * @param _d
     * @return
     */
    public static double std(double[] _d) {
        return std(_d, mean(_d));
    }

    // a standard statistical function
    /**
     *
     * @param _d
     * @param _mean
     * @return
     */
    public static double std(double[] _d, double _mean) {
        if (_d.length < 2) {
            return 0;
        }
        double variance = variance(_d, _mean);
        if (variance <= 0) {
            return 0;
        }
        return Math.sqrt(variance);
    }

    // a standard statistical function
    /**
     *
     * @param _d
     * @return
     */
    public static double variance(double[] _d) {
        return variance(_d, mean(_d));
    }

    // a standard statistical function
    /**
     *
     * @param _d
     * @param _mean
     * @return
     */
    public static double variance(double[] _d, double _mean) {
        if (_d.length < 2) {
            return 0;
        }
        double dev = 0;
        double first = _d[0];
        boolean identical = true;
        for (int i = 0; i < _d.length; i++) {
            if (identical && _d[i] != first) {
                identical = false;
            }
            double d = _d[i] - _mean;
            dev += d * d;
        }
        if (identical) {
            return 0;	//!! an exact zero instead of ~10^-17

        }
        return (dev / (_d.length - 1));
    }

    // a standard statistical function; returns 0 to +1; 0 means zero correlation
    /**
     *
     * @param _d1
     * @param _d2
     * @return
     */
    public static double correlationCoefficient(double[] _d1, double[] _d2) {
        if (_d1.length != _d2.length || _d1.length < 3) {
            return 0;
        }
        double mean1 = mean(_d1);
        double mean2 = mean(_d2);
        double std1 = 0;
        double std2 = 0;

        std1 = std(_d1, mean1);
        if (std1 == 0) {
            return 0;	//!! return NaN would be more accurate

        }
        std2 = std(_d2, mean2);
        if (std2 == 0) {
            return 0;	//!! return NaN would be more accurate	

        }
        return correlationCoefficient(_d1, _d2, std1, std2, mean1, mean2);
    }

    // faster correlationCoefficient for callers who cache std and mean
    /**
     *
     * @param _d1
     * @param _d2
     * @param _std1
     * @param _std2
     * @param _mean1
     * @param _mean2
     * @return
     */
    public static double correlationCoefficient(
            double[] _d1, double[] _d2,
            double _std1, double _std2,
            double _mean1, double _mean2) {
        if (_d1.length != _d2.length || _d1.length < 3) {
            return 0;
        }
        if (_std1 == 0 || _std2 == 0) {
            return 0;
        }
        double meanProduct = 0;
        for (int i = 0; i < _d1.length; i++) {
            meanProduct += _d1[i] * _d2[i];
        }
        double result = (meanProduct / _d1.length) - (_mean1 * _mean2);
        result /= (_std1 * _std2); //!! already protected against zero divide 

        return result;
    }
    /*
    public static void main(String[] args) { // test correlationCoefficient
    double[] a = new double[99];
    double[] b = new double[99];
    double[] c = new double[99];
    double[] d = new double[99];
    for (int i=0; i<a.length; i++) {
    a[i] = URandom.rand(100d);
    b[i] = URandom.rand(1d);
    c[i] = (a[i] * a[i] + 10)/2; // correlationCoefficient(a,c) should remain very high
    if (i%2==0) d[i] = 1d; else d[i] = 1.0000001d; //!! 
    }
    System.out.println("low correlationCoefficient="+correlationCoefficient(b,d));
    System.out.println("max correlationCoefficient="+correlationCoefficient(a,a));
    System.out.println("max correlationCoefficient="+correlationCoefficient(b,b));
    System.out.println("high correlationCoefficient="+correlationCoefficient(a,c));
    System.out.println("rand correlationCoefficient="+correlationCoefficient(a,b));
    System.out.println("rand correlationCoefficient="+correlationCoefficient(b,c));
    }
     */

    /**
     *
     * @param _d
     * @param _numStds
     * @return
     */
    public static double[] extractInflection(double[] _d, double _numStds) {
        double mean = 0;
        for (int i = 0; i < _d.length; i++) {
            mean += _d[i];
        }
        mean /= _d.length;
        double std = std(_d, mean);
        double delta = std * _numStds; // _numStds is some small num, probably between .1 and 5

        double[] results = new double[_d.length];
        for (int i = 0; i < _d.length; i++) {
            double upper = _d[i] + (Math.abs((_d[i] - mean) / delta));
            double lower = _d[i] - (Math.abs((_d[i] - mean) / delta));
            results[i] = upper - lower;

            //results[i] = Math.abs((_d[i]-mean)/delta);
        }
        return results;
    }

    // similar to extractChiSquaredWave; since ChiSquared has been more thoroughly analyzed by statiticians, it is preferred
    /**
     *
     * @param _d
     * @param _numStds
     * @param _cumulative
     * @return
     */
    public static double[] extractDeviationWave(double[] _d, double _numStds, boolean _cumulative) {
        double[] results = new double[_d.length];
        double mean = 0;
        double mid = .5d;
        for (int i = 0; i < _d.length; i++) {
            mean += _d[i];
        }
        mean /= _d.length;
        double std = std(_d, mean);
        double delta = std * _numStds;

        if (std == 0 || delta == 0) {
            return results;
        }
        double deviation = (_d[0] - mean) / delta;
        boolean neg = deviation < 0;
        deviation *= deviation;
        if (neg) {
            results[0] = -deviation;
        } else {
            results[0] = deviation;
            // calc chi squared with sign of deviation
        }
        for (int i = 1; i < results.length; i++) {
            deviation = (_d[i] - mean) / delta;
            neg = deviation < 0;
            deviation *= deviation;
            if (neg) {
                results[i] = -deviation;
            } else {
                results[i] = deviation;
            }
        }
        // cumulative effect restated relative to mid
        if (_cumulative) { // cumulative effect restated relative to mid

            double cumulative = results[0];
            for (int i = 1; i < results.length; i++) {
                results[i - 1] = mid + cumulative;
                cumulative += results[i];
            }
            results[results.length - 1] = mid + cumulative;
        } else {
            results = UDouble.bound(results, 0, 1);
        }
        return results;
    }

    // ChiSquared is a a standard statistical function ("Z"); 
    // extractChiSquaredWave accumulates ChiSquareds and dampens the accumulation into a wave
    /**
     *
     * @param _d
     * @param _damp
     * @param _cumulative
     * @return
     */
    public static double[] extractChiSquaredWave(double[] _d, double _damp, boolean _cumulative) {
        if (_d.length < 1) {
            return new double[0];
            //!!todo: mathematical probability values and exact probability of _damp values
        }
        double mean = 0;
        double mid = .5d;
        for (int i = 0; i < _d.length; i++) {
            mean += _d[i];
        }
        mean /= _d.length;
        double std = std(_d, mean);
        double delta = std * _damp; // _damp==1 is regular chi squared; _damp=10 to 20 helps flatten less significant changes

        double[] results = new double[_d.length];

        boolean neg = (_d[0] - mean) < 0;
        double chiSquared = (_d[0] - mean);
        chiSquared *= chiSquared;
        chiSquared /= (mean * _damp);
        if (neg) {
            results[0] = -chiSquared;
        } else {
            results[0] = chiSquared;
            // calc chi squared with sign
        }
        for (int i = 1; i < results.length; i++) {
            neg = (_d[i] - mean) < 0;
            chiSquared = (_d[i] - mean);
            chiSquared *= chiSquared;
            chiSquared /= (mean * _damp);
            if (neg) {
                results[i] = -chiSquared;
            } else {
                results[i] = chiSquared;
            }
        }
        if (_cumulative) { // cumulative effect restated relative to mid

            double cumulative = results[0];
            for (int i = 1; i < results.length; i++) {
                results[i - 1] = mid + cumulative;
                cumulative += results[i];
            }
            results[results.length - 1] = mid + cumulative;
        } else {
            results = UDouble.bound(results, 0, 1);
        }
        return results;
    }

    /**
     *
     * @param _d
     * @param _numStds
     * @return
     */
    public static double[] extractStdWave(double[] _d, double _numStds) {
        double mean = 0;
        for (int i = 0; i < _d.length; i++) {
            mean += _d[i];
        }
        mean /= _d.length;
        double std = std(_d, mean);
        double delta = std * _numStds; // _numStds is some small num, probably between .1 and 3

        double[] results = new double[_d.length];
        for (int i = 0; i < _d.length; i++) {
            results[i] = Math.abs((_d[i] - mean) / delta);
        }
        return results;
    }

    // returns 0 to 1; 0=>extremely unlikely; 1=>most likely
    /**
     *
     * @param _count
     * @param _total
     * @param _std
     * @return
     */
    public static double significance(double _count, double _total, double _std) {
        double std = 0;
        if (_std < 0) {
            std = Math.abs(_std) - .5;
        } else {
            std = Math.abs(_std) + .5;
        }
        if (std < 1) {
            std = 1;
        }
        double stdFactor = .682; // approximate

        double expected = 0;
        for (int i = 0; i < (int) std; i++) {
            expected += (1.0d - expected) * stdFactor;
        }
        expected = 1d - expected;
        expected *= _total;
        double actual = _count;
        double result = 1d - Math.abs((actual - expected) / _total);
        return result;
    }

    /*		

    public static void main(String[] args) { // test significance
    for (double c=0; c<100; c+=5) {
    System.out.println("---percent="+(int)c);
    for (double s=1; s<8; s++) {
    System.out.println("s="+(float)significance(c, 100d, s));
    }
    }
    System.out.println("1,1,1="+(float)significance(1d, 1d, 1d));
    }
     */
    // return duration to reverse one trend (_slope1,_duration1) with another trend (_slope2)
    // returns zero if reversal is not possible (because both trends are in same direction)
    /**
     *
     * @param _slope1
     * @param _duration1
     * @param _slope2
     * @return
     */
    public static double getTimeToReverseTrend(double _slope1, double _duration1, double _slope2) {
        double duration2 = -((_slope1 * _duration1) / _slope2);
        if (duration2 < 0) {
            return 0;
        }
        return duration2;
    }

    // return slope to reverse one trend (_slope1,_duration1) with another duration (_duration2)
    /**
     *
     * @param _slope1
     * @param _duration1
     * @param _duration2
     * @return
     */
    public static double getSlopeToReverseTrend(double _slope1, double _duration1, double _duration2) {
        double slope2 = -((_slope1 * _duration1) / _duration2);
        return slope2;
    }

    /*

    public static void main(String[] args) { // test getTimeToReverseTrend & getSlopeToReverseTrend
    double s2 = .5;
    for (double s=-1; s<.1; s+=.1) {
    System.out.println("---slope="+s);
    for (double d=1; d<10; d++) {
    double time = getTimeToReverseTrend(s,d,s2);
    //System.out.println("time="+time+" for "+s+", "+d+", "+s2);
    if (time==0) continue;
    double slope = getSlopeToReverseTrend(s,d,time);
    double delta = s2-slope;
    if (delta< -.001 || delta> .001) System.out.println("*BAD* delta="+delta);
    System.out.println("good delta="+delta);							
    }
    }
    }
     */
    // returns null if circles do not intersect
    /**
     *
     * @param _x1
     * @param _y1
     * @param _r1
     * @param _x2
     * @param _y2
     * @param _r2
     * @param imaginary
     * @return
     */
    public static double[] intersectCircles(
            double _x1, // center 1 x
            double _y1, // center 1 y
            double _r1, // radius 1
            double _x2, // center 2 x
            double _y2, // center 2 y
            double _r2, // radius 2
            boolean imaginary //	if true, return "imaginary" points instead of null when intersection does not exist 
            ) {

        double dSquared = ((_x2 - _x1) * (_x2 - _x1)) + ((_y2 - _y1) * (_y2 - _y1));
        double e = (((_r1 + _r2) * (_r1 + _r2)) - dSquared) * (dSquared - ((_r2 - _r1) * (_r2 - _r1)));
        if (e < 0) {
            if (!imaginary) {
                return null;
            } else {
                e = -e;
            }
        }
        e = Math.sqrt(e);

        double r1Squared = _r1 * _r1;
        double r2Squared = _r2 * _r2;

        double x = ((_x2 + _x1) / 2) + (((_x2 - _x1) * (r1Squared - r2Squared)) / (2 * dSquared));
        double fx = ((_y2 - _y1) / (2 * dSquared)) * e;

        double y = ((_y2 + _y1) / 2) + (((_y2 - _y1) * (r1Squared - r2Squared)) / (2 * dSquared));
        double fy = ((_x2 - _x1) / (2 * dSquared)) * e;

        double[] result = new double[4];
        result[0] = x + fx;
        result[1] = y - fy;
        result[2] = x - fx;
        result[3] = y + fy;
        return result;
    }

    /*
    public static void main(String[] args) { // test intersectCircles
    double[] r = intersectCircles(0,0,200d,50,300,50d,true);
    
    System.out.println(r[0]+","+r[1]);							
    System.out.println(r[2]+","+r[3]);							
    }*/
    private static double[] deltas(
            double[] _wave,
            int _start,
            int _len,
            int _step) {
        if (_wave.length - _start < _len) {
            throw new RuntimeException("bad len=" + _len + " start=" + _start + " for wave len=" + _wave.length);
        }
        if (_step < 1) {
            _step = 1;
        }
        int resultLength = (_len / _step) - 1;
        double[] result = new double[resultLength]; // one less delta than data

        double first = 0;
        for (int j = _start; j < _start + _step; j++) {
            first += _wave[j];
        }
        first /= _step;
        for (int i = 0; i < result.length; i++) {
            double ave = 0;
            int windowStart = (int) (((i + 1) * _step) + _start);
            for (int j = windowStart; j < windowStart + _step; j++) {
                ave += _wave[j];
            }
            ave /= _step;
            if (i == 0) {
                result[i] = ave - first;
            } else {
                result[i] = ave - result[i - 1];
            }
        }


        return result;
    }

    /**
     *
     * @param _sample
     * @param _start
     * @param _len
     * @param _step
     * @param _reference
     * @return
     */
    public static double[] analyzeSlopes(
            double[] _sample,
            int _start,
            int _len,
            int _step,
            double[] _reference) {
        double[] ref = deltas(_reference, 0, _reference.length, _step);
        double meanRef = UMath.mean(ref);
        double std = UMath.std(ref, meanRef);

        double[] samp = deltas(_sample, _start, _len, _step);
        double meanSamp = UMath.mean(samp);

        double percentile = 0;
        if (std != 0) {
            double numStds = (meanSamp - meanRef) / std;
            percentile = numStds / 2; //!! make this be an accurate percentile level indicator

        }
        if (percentile < -1) {
            percentile = -1;
        }
        if (percentile > 1) {
            percentile = 1;
        }
        double[] result = new double[3];
        result[0] = meanRef;
        result[1] = meanSamp;
        result[2] = percentile;
        return result;
    }
    // adapted from equation #7 http://www.cs.princeton.edu/introcs/33modular/
    /**
     *
     */
    final public static int cParallel = -1;
    /**
     *
     */
    final public static int cFalse = 0;
    /**
     *
     */
    final public static int cTrue = 1;

    /**
     *
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param dx
     * @param dy
     * @param intersect
     * @return
     */
    final public static int linesIntersect(// does line a-b intersect line c-d?
            double ax, double ay, double bx, double by,
            double cx, double cy, double dx, double dy,
            double[] intersect // if not null, return intersect point
            ) {
        double acx = ax - cx;
        double acy = ay - cy;

        double bax = bx - ax;
        double bay = by - ay;

        double dcx = dx - cx;
        double dcy = dy - cy;

        double d = (bax * dcy) - (bay * dcx);
        if (d == 0) {
            return cParallel; // parallel	

        }
        double r = ((acy * dcx) - (acx * dcy)) / d;
        double s = ((acy * bax) - (acx * bay)) / d;

        if (r > 0 && r <= 1 && s >= 0 && s <= 1) { // r==0 => collinear

            if (intersect != null) { // calculate intersection point x,y

                intersect[0] = ax + r * bax;
                intersect[1] = ay + r * bay;
            }
            return cTrue;
        } else {
            return cFalse;
        }
    }
    // optimized

    /**
     *
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param dx
     * @param dy
     * @return
     */
    final public static double[] linesIntersect(// does line a-b intersect line c-d?
            double ax, double ay, double bx, double by,
            double cx, double cy, double dx, double dy) {

        double bax = bx - ax;
        double bay = by - ay;

        double dcx = dx - cx;
        double dcy = dy - cy;

        double d = (bax * dcy) - (bay * dcx);
        if (d == 0) {
            return null;
        }
        double acx = ax - cx;
        double acy = ay - cy;

        double r = ((acy * dcx) - (acx * dcy)) / d;
        if (r > 0 && r <= 1) {
            double s = ((acy * bax) - (acx * bay)) / d;
            if (s >= 0 && s <= 1) {
                return new double[]{ax + (r * bax), ay + (r * bay)};
            }
        }
        return null;
    }

    /**
     *
     * @param p
     * @param p0
     * @param p1
     * @return
     */
    static public boolean isPointRightOfLine(XY_I p, XY_I p0, XY_I p1) {// p=point; p0 <-> p1 = line
        XY_I a = new XY_I(p1.x - p0.x, p1.y - p0.y);
        XY_I b = new XY_I(p.x - p0.x, p.y - p0.y);
        double sa = a.x * b.y - b.x * a.y;
        if (sa < 0.0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param p0
     * @param p1
     * @return
     */
    static public double distance(XY_I p0, XY_I p1) {
        double xd = p0.x - p1.x;
        if (xd < 0) {
            xd *= -1;
        }
        double yd = p0.y - p1.y;
        if (yd < 0) {
            yd *= -1;
        }
        return java.lang.Math.sqrt(xd * xd + yd * yd);
    }

    /**
     *
     * @param _rects
     * @return
     */
    static public CArray getRectanglesPoints(CArray _rects) {// adk.view.XYWH_I
        Object[] ra = _rects.getAll();
        XY_I[] pa = new XY_I[ra.length * 4];
        for (int i = 0; i < ra.length; i++) {
            XYWH_I r = (XYWH_I) ra[i];
            int j = i * 4;
            pa[j++] = new XY_I(r.x, r.y);
            pa[j++] = new XY_I(r.x, r.y + r.h);
            pa[j++] = new XY_I(r.x + r.w, r.y + r.h);
            pa[j++] = new XY_I(r.x + r.w, r.y);
        }
        CArray a = new CArray();
        a.insertLast(pa);
        return a;
    }

    /**
     *
     * @param _rects
     * @return
     */
    static public CArray wrapRectangles(CArray _rects) {// adk.view.XYWH_I
        return wrap(getRectanglesPoints(_rects));
    }

    /**
     *
     * @param _p
     * @return
     */
    static public CArray wrap(CArray _p) { // _p is array of XY_I
        return wrap(_p.getAll());
    }

    /**
     *
     * @param _p
     * @return
     */
    static public CArray wrap(Object[] _p) { // _p is array of XY_I
        if (_p == null) {
            return null;
        }
        if (_p.length < 2) {
            return null;
        }
        Object[] pa = _p;
        XY_I[] _points = new XY_I[pa.length + 1];
        for (int i = 0; i < pa.length; i++) {
            _points[i] = (XY_I) pa[i];
        }

        // find the extreme point on the hull
        int next = 0;
        for (int i = 1; i < _points.length - 1; i++) {
            if (_points[i].x < _points[next].x || ((_points[i].x == _points[next].x) && (_points[i].y < _points[next].y))) {
                next = i;
            }
        }
        _points[_points.length - 1] = _points[next];

        CArray result = new CArray(XY_I.class); // traverse this ordered XY_I[] to draw convex hull (wrap)
        for (int i = 0; i < _points.length - 1; i++) {
            XY_I swap = _points[next];
            _points[next] = _points[i];
            _points[i] = swap;
            next = i + 1;

            result.insertLast(new XY_I(_points[i].x, _points[i].y)); // line from prev point to this point has the minimum polar angle

            for (int j = i + 2; j < _points.length; j++) {
                if (isPointRightOfLine(_points[j], _points[i], _points[next])) {
                    next = j; // j=point; i <-> next = line
                }
            }
            if (next == _points.length - 1) {
                return result;
            }
        }
        return null;
    }

    /**
     *
     * @param _r
     * @return
     */
    public static CArray polyTraverseRectangles(CArray _r) {
        Object[] ra = _r.getAll();
        CArray centers = new CArray(ra.length);
        for (int i = 0; i < ra.length; i++) {
            XYWH_I r = (XYWH_I) ra[i];
            XY_I p = new XY_I(r.x + r.w / 2, r.y + r.h / 2);
            centers.insertLast(p);
        }
        return polyTraverse(centers);
    }

    /**
     *
     * @param _p
     * @return
     */
    public static CArray polyTraverse(CArray _p) {// a pretty good and quick solution to the travelling salesman problem (TSP)
        if (_p == null || _p.getCount() < 2 || _p.getCount() > 5000) {
            return null; // >5000 takes hours!
        }
        CArray remainingPoints = (CArray) _p.clone();
        CArray drawPoints = null;
        drawPoints = wrap(remainingPoints);
        if (drawPoints == null) {
            return null;
        }
        remainingPoints.remove(drawPoints);

        while (true) {
            Object[] checkRemainingPoints = remainingPoints.getAll();
            Object[] checkDrawPoints = drawPoints.getAll();
            if (checkRemainingPoints.length == 0) {
                break;
            }

            int minDrawIndex = 0;
            int minRemainingIndex = 0;
            double minDistance = Double.MAX_VALUE;
            for (int i = 0; i < checkDrawPoints.length; i++) {
                int next = i + 1;
                if (next > checkDrawPoints.length - 1) {
                    next = 0;
                }
                double leg = distance((XY_I) checkDrawPoints[i], (XY_I) checkDrawPoints[next]);
                for (int j = 0; j < checkRemainingPoints.length; j++) {
                    double d1 = distance((XY_I) checkDrawPoints[i], (XY_I) checkRemainingPoints[j]);
                    if (d1 > leg) {
                        continue;
                    }
                    double d2 = distance((XY_I) checkDrawPoints[next], (XY_I) checkRemainingPoints[j]);
                    if (d2 > leg) {
                        continue;
                    }
                    double d = (d1 + d2) - leg;
                    if (d > 0 && d < minDistance) {
                        minDistance = d;
                        minDrawIndex = next;
                        minRemainingIndex = j;
                    }
                }
            }
            drawPoints.insertAt(checkRemainingPoints[minRemainingIndex], minDrawIndex);
            remainingPoints.removeAt(minRemainingIndex);
        }
        return drawPoints;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static Polygon removeAFromB(Polygon _a, Polygon _b) {
        if (_b == null) {
            return null;
        }
        if (_a == null) {
            return _b;
        }

        _a = reverse(_a);

        int[] xs = new int[0];
        xs = UArray.push(xs, _a.xpoints, _a.npoints);
        xs = UArray.push(xs, _b.xpoints, _b.npoints);

        int[] ys = new int[0];
        ys = UArray.push(ys, _a.ypoints, _a.npoints);
        ys = UArray.push(ys, _b.ypoints, _b.npoints);

        return new Polygon(xs, ys, xs.length);
    }

    /**
     *
     * @param _poly
     * @return
     */
    public static Polygon reverse(Polygon _poly) {
        Polygon poly = new Polygon();
        for (int i = _poly.npoints - 1; i > -1; i--) {
            poly.addPoint(_poly.xpoints[i], _poly.ypoints[i]);
        }
        return poly;
    }

    /**
     *
     * @param _points
     * @param _closed
     * @return
     */
    public static Polygon toPoly(CArray _points, boolean _closed) {
        if (_points == null) {
            return null;
        }
        if (_points.getCount() == 0) {
            return null;
        }
        Polygon poly = new Polygon();
        for (int i = 0; i < _points.getCount(); i++) {
            XY_I p = (XY_I) _points.getAt(i);
            poly.addPoint(p.x, p.y);
        }
        if (_closed) {
            XY_I p = (XY_I) _points.getAt(0);
            poly.addPoint(p.x, p.y);
        }
        return poly;
    }
}
