/*
 * IBA.java.java
 *
 * Created on 03-12-2010 11:22:46 PM
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

//(IBA) ImmutableByteArray
/**
 *
 * @author Administrator
 */
public class IBA implements Comparable {

    /**
     *
     */
    public static final IBA cNull = new IBA(new byte[0]);
    private int hashCode = 0;
    private byte[] bytes;

    /**
     *
     */
    public IBA() {
    }

    /**
     *
     * @param _bytes
     */
    public IBA(byte[] _bytes) {
        bytes = _bytes;
    }

    /**
     *
     * @return
     */
    public byte[] immutableBytes() {
        return bytes;
    }
    // this should return a copy to make IBA truly Immutable
    // I have deliberate choosen not to for performance reasons.

    /**
     *
     * @return
     */
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        if (bytes == null) {
            return "";
        }

        return new String(bytes);
    }

    /**
     *
     * @return
     */
    public int length() {
        return bytes.length;
    }

    /**
     *
     * @return
     */
    public long getCount() {
        return bytes.length;
    }

    @Override
    public int hashCode() {
        if ((bytes == null) || (bytes.length == 0)) {
            return 0;
        }

        if (hashCode != 0) {
            return hashCode;
        }

        int hash = 0;
        long randMult = 0x5DEECE66DL;
        long randAdd = 0xBL;
        long randMask = (1L << 48) - 1;
        long seed = bytes.length;

        for (int i = 0; i < bytes.length; i++) {
            long x = (seed * randMult + randAdd) & randMask;

            seed = x;
            hash += (bytes[i] + 128) * x;
        }

        hashCode = hash;

        return hash;
    }

    /**
     *
     * @return
     */
    public long longHashCode() {
        if ((bytes == null) || (bytes.length == 0)) {
            return 0;
        }

        long hash = 0;
        long randMult = 0x5DEECE66DL;
        long randAdd = 0xBL;
        long randMask = (1L << 48) - 1;
        long seed = bytes.length;

        for (int i = 0; i < bytes.length; i++) {
            long x = (seed * randMult + randAdd) & randMask;

            seed = x;
            hash += (bytes[i] + 128) * x;
        }

        return hash;
    }

    @Override
    public boolean equals(Object _object) {
        if (_object == this) {
            return true;
        }
        byte[] b = null;
        if (_object instanceof byte[]) {
            b = (byte[]) _object;
        }
        else if (_object instanceof IBA) {
            b = ((IBA) _object).bytes;
        }
        else if (_object instanceof ASetObject) {
            Object bObject = ((ASetObject) _object).hashObject();
            if (this == bObject) {
                return true;
            }
            return bObject.equals(this);
        }
        if (b == null) {
            return false;
        }
        byte[] a = bytes;
        return equals(a, b);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(byte[] a, byte[] b) {
        if (a == b) {
            return true;
        }

        if ((a == null) || (b == null)) {
            return false;
        }

        if (a.length != b.length) {
            return false;
        }

        int l = a.length;

        for (int i = 0; i < l; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int compareTo(Object o) {
        byte[] b;
        if (o instanceof byte[]) {
            b = (byte[]) o;
        }
        else if (o instanceof IBA) {
            b = ((IBA) o).bytes;
        }
        else {
            b = new byte[0];
        }
        if (b.length < bytes.length) {
            return -1;
        }
        else if (b.length > bytes.length) {
            return 1;
        }
        else {
            for (int i = 0; i < bytes.length; i++) {
                if (b[i] < bytes[i]) {
                    return -1;
                }
                else if (b[i] > bytes[i]) {
                    return 1;
                }
            }
            return 0;
        }
    }
}
