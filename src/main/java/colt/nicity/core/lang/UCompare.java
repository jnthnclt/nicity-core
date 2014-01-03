/*
 * UCompare.java.java
 *
 * Created on 01-02-2010 09:46:00 AM
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

/**
 *
 * @author Administrator
 */
public class UCompare {

    /**
     *
     * @param _as
     * @param _bs
     * @return
     */
    final public static double compare(float[] _as, float[] _bs) {
        double r = 0;
        for (int i = 0; i < _as.length; i++) {
            r = compare(_as[i], _bs[i]);
            if (r != 0) {
                return r;
            }
        }
        return r;
    }

    // ascending
    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    final public static double compare(int _a, int _b) {
        return (_a < _b ? -1d : (_a == _b ? 0d : 1d));
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    final public static double compare(long _a, long _b) {
        return (_a < _b ? -1d : (_a == _b ? 0d : 1d));
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    final public static double compare(double _a, double _b) {
        return (_a < _b ? -1d : (_a == _b ? 0d : 1d));
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    final public static double compare(float _a, float _b) {
        return (_a < _b ? -1d : (_a == _b ? 0d : 1d));
    }
}
