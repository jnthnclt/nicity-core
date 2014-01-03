/*
 * UCSet.java.java
 *
 * Created on 03-12-2010 10:57:00 PM
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
package colt.nicity.core.collection;

import colt.nicity.core.comparator.AValueComparator;
import colt.nicity.core.comparator.UValueComparator;
import colt.nicity.core.lang.UArray;

/**
 *
 * @author Administrator
 */
public class UCSet {

    /**
     *
     * @param _sets
     * @param _r
     * @return
     */
    public static CSet intersection(Object[] _sets, CSet _r) {
        if (_r == null) {
            _r = new CSet();
        }
        Object[] sets = UArray.removeNulls(_sets);
        if (sets.length < 2) {
            return _r;
        }
        java.util.Arrays.sort(sets, UValueComparator.haveCount(
            AValueComparator.cAscending));
        Object[] all = ((CSet) sets[0]).getAll(Object.class);
        Next:
        for (int i = 0; i < all.length; i++) {
            for (int s = 1; s < sets.length; s++) {
                if (((CSet) sets[s]).get(all[i]) == null) {
                    continue Next;
                }
            }
            _r.add(all[i]);
        }
        return _r;
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _r
     * @return
     */
    public static CSet intersection(CSet _a, CSet _b, CSet _r) {
        if (_r == null) {
            _r = new CSet();
        }
        if (_a.getCount() > _b.getCount()) {
            CSet s = _b;
            _b = _a;
            _a = s;
        }
        Object[] all = _a.getAll(Object.class);
        for (int i = 0; i < all.length; i++) {
            if (_b.get(all[i]) != null) {
                _r.add(all[i]);
            }
        }
        return _r;
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _r
     * @return
     */
    public static CSet union(CSet _a, CSet _b, CSet _r) {
        if (_r == null) {
            _r = new CSet();
        }
        Object[] all = _a.getAll(Object.class);
        for (int i = 0; i < all.length; i++) {
            _r.add(all[i]);
        }
        all = _b.getAll(Object.class);
        for (int i = 0; i < all.length; i++) {
            _r.add(all[i]);
        }
        return _r;
    }
}
