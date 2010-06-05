/*
 * URandom.java.java
 *
 * Created on 01-03-2010 09:03:00 AM
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

public abstract class URandom {// holder for static utility methods; do not instantiate
//	vars for fastRand

    static public class Seed {
        public long randSeed;
        public Seed(long _seed) {
            randSeed = _seed;
        }
    }

    private final static long randMult = 0x5DEECE66DL;
    private final static long randAdd = 0xBL;
    private final static long randMask = (1L << 48) - 1;
    // public so you can set your own randSeed for repeatability
    public static Seed defaultSeed = new Seed((System.currentTimeMillis() ^ randMult) & randMask);

    public static double signedRand(double max) {// 0 <= return < max
        return signedRand(defaultSeed, max);
    }
    public static double signedRand(Seed _longSeed,double max) {// 0 <= return < max
        return max * ((double) signedRand(_longSeed,Integer.MAX_VALUE) / (double) Integer.MAX_VALUE);
    }
    

    public static double[] signedRand(double max, int _count) {// 0 <= return < max
        return signedRand(defaultSeed, max, _count);
    }
    public static double[] signedRand(Seed _longSeed,double max, int _count) {// 0 <= return < max
        double[] rands = new double[_count];
        for (int i = 0; i < rands.length; i++) {
            rands[i] = signedRand(_longSeed,max);
        }
        return rands;
    }

    public static double rand(double max) {// 0 <= return < max
        return rand(defaultSeed, max);
    }
    public static double rand(Seed _longSeed,double max) {// 0 <= return < max
        return max * ((double) rand(_longSeed,Integer.MAX_VALUE) / (double) Integer.MAX_VALUE);
    }

    public static double[] rand(double max, int _count) {// 0 <= return < max
        return rand(max, _count);
    }
    public static double[] rand(Seed _longSeed,double max, int _count) {// 0 <= return < max
        double[] rands = new double[_count];
        for (int i = 0; i < rands.length; i++) {
            rands[i] = rand(_longSeed,max);
        }
        return rands;
    }

    public static int signedRand(int max) {// 0 <= return < max
        return signedRand(defaultSeed, max);
    }
    public static int signedRand(Seed _longSeed,int max) {// 0 <= return < max
        int sign = 1;
        if (rand(_longSeed,max) > max / 2) {
            sign = -1;
        }
        return rand(_longSeed,max) * sign;
    }

    public static int nextInt() {
        return nextInt(defaultSeed);
    }
    public static int nextInt(Seed _longSeed) {
        long x = (_longSeed.randSeed * randMult + randAdd) & randMask;
        _longSeed.randSeed = x;
        int rand = (int) (x >>> (16));
        return rand;
    }

    public static int rand() {
        return rand(defaultSeed);
    }
    public static int rand(Seed _longSeed) {
        long x = (_longSeed.randSeed * randMult + randAdd) & randMask;
        _longSeed.randSeed = x;
        int rand = (int) (x >>> (16));
        return rand;
    }

    public static int rand(int max) {// 0 <= return < max
        return rand(defaultSeed, max);
    }
    public static int rand(Seed _longSeed,int max) {// 0 <= return < max
        long x = (_longSeed.randSeed * randMult + randAdd) & randMask;
        _longSeed.randSeed = x;
        int rand = (int) (x >>> (16));
        rand %= max;
        if (rand < 0) {
            return -rand;
        }
        return rand;
    }

    public static int[] randInts(int count, int max) {
        return randInts(defaultSeed, count, max);
    }
    public static int[] randInts(Seed _longSeed,int count, int max) {
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            long x = (_longSeed.randSeed * randMult + randAdd) & randMask;
            _longSeed.randSeed = x;
            int rand = (int) (x >>> (16));
            rand %= max;
            if (rand < 0) {
                rand = -rand;
            }
            ints[i] = rand;
        }
        return ints;
    }

    public static int[] randUniqueInts(int count, int max) {
        return randUniqueInts(defaultSeed, count, max);
    }
    public static int[] randUniqueInts(Seed _longSeed,int count, int max) {
        //!! works fastest when max > count; excellent if max > 2*count
        //!! works slowest when max <= count, especially if max is large
        int len = count;
        if (max < count) {
            len = max;
        }
        int[] ints = new int[len];
        int i = 0;
        nextRand:
        while (i < len) {
            long x = (_longSeed.randSeed * randMult + randAdd) & randMask;
            _longSeed.randSeed = x;
            int rand = (int) (x >>> (16));
            rand %= max;
            if (rand < 0) {
                rand = -rand;
            }
            for (int j = 0; j < i; j++) {
                if (rand == ints[j]) {
                    continue nextRand;
                }
            }
            ints[i++] = rand;
        }
        return ints;
    }

    public static int[] randIndexes(int count) {
        return randIndexes(defaultSeed, count);
    }
    public static int[] randIndexes(Seed _longSeed,int count) {
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            ints[i] = i;
        }
        for (int i = 0; i < count; i++) {
            int j = rand(_longSeed,count);
            if (i == j) {
                continue;
            }
            int k = ints[i];
            ints[i] = ints[j];
            ints[j] = k;
        }
        return ints;
    }

    public static void fill(byte[] _fill, int _offset, int _length) {
        fill(defaultSeed, _fill, _offset, _length);
    }
    public static void fill(Seed _longSeed,byte[] _fill, int _offset, int _length) {
        for (int i = _offset; i < _offset + _length; i++) {
            _fill[i] = (byte) rand(_longSeed,256);
        }
    }

    public static void fill(byte[] _fill, int _offset, int _length, int _min, int _max) {
        fill(defaultSeed, _fill, _offset, _length, _min, _max);
    }
    public static void fill(Seed _longSeed,byte[] _fill, int _offset, int _length, int _min, int _max) {
        for (int i = _offset; i < _offset + _length; i++) {
            _fill[i] = (byte) (_min + rand(_longSeed,_max - _min));
        }
    }

    public static void fill(double[] _fill, int _offset, int _length, double _resolution) {
        fill(defaultSeed, _fill, _offset, _length, _resolution);
    }
    public static void fill(Seed _longSeed,double[] _fill, int _offset, int _length, double _resolution) {
        for (int i = _offset; i < _offset + _length; i++) {
            _fill[i] = ((int) (_resolution * rand(_longSeed,1d))) / _resolution;
        }
    }

    public static byte[] randomLowerCaseAlphaBytes(int _length) {
        return randomLowerCaseAlphaBytes(defaultSeed, _length);
    }
    public static byte[] randomLowerCaseAlphaBytes(Seed _longSeed,int _length) {
        byte[] bytes = new byte[_length];
        URandom.fill(_longSeed,bytes, 0, _length, 97, 122);// 97 122 lowercase a to z ascii
        return bytes;
    }

    public static String randomLowerCaseAlphaString(int _length) {
        return randomLowerCaseAlphaString(defaultSeed, _length);
    }
    public static String randomLowerCaseAlphaString(Seed _longSeed,int _length) {
        return new String(randomLowerCaseAlphaBytes(_longSeed,_length));
    }

    public static byte[] randomNumberBytes(int _length) {
        return randomNumberBytes(defaultSeed, _length);
    }
    public static byte[] randomNumberBytes(Seed _longSeed,int _length) {
        byte[] name = new byte[_length];
        URandom.fill(_longSeed,name, 0, _length, 48, 57);// 48 57  0 to 9 ascii
        return name;
    }

    public static String randomNumberString(int _length) {
        return randomNumberString(defaultSeed, _length);
    }
    public static String randomNumberString(Seed _longSeed,int _length) {
        return new String(randomNumberBytes(_longSeed,_length));
    }
}
