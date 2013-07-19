/*
 * CBiDiDictionary.java
 *
 * Created on Apr 18, 2010, 10:24:08 PM
 *
 * Copyright Apr 18, 2010 Jonathan Colt 
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

import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.OrderedKeys;



/**
*
* @author Jonathan Colt
*/
public class CBiDiDictionary<A,B> {
    private CSet<BiDi> bidis = new CSet<>();
    /**
     *
     */
    public CBiDiDictionary() {
        
    }
    /**
     *
     */
    public void removeAll() {
        bidis.removeAll();
    }
    /**
     *
     * @param a
     * @param b
     */
    public void add(final A a,final B b) {
        bidis.add(new BiDi(b) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<>(a,true);
            }
        });
        bidis.add(new BiDi(a) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<>(b,false);
            }
        });
    }
    /**
     *
     * @param a
     * @param b
     */
    public void remove(final A a,final B b) {
        bidis.remove(new BiDi(b) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<>(a,true);
            }
        });
        bidis.remove(new BiDi(a) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<>(b,false);
            }
        });
    }
    /**
     *
     * @param a
     * @return
     */
    public B forwardLookup(A a) {
        BiDi bidi = bidis.get(new OrderedKeys<>(a,true));
        if (bidi == null) return null;
        return (B)bidi.get();
    }
    /**
     *
     * @param b
     * @return
     */
    public A reverseLookup(B b) {
        BiDi bidi = bidis.get(new OrderedKeys<>(b,false));
        if (bidi == null) return null;
        return (A)bidi.get();
    }

    static abstract class BiDi extends ASetObject<Object> {
        Object _v;
        BiDi(Object v) {
            _v = v;
        }
        public Object get() {
            return _v;
        }
    }
}
