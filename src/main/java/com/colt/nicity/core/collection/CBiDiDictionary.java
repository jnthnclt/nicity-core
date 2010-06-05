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


package com.colt.nicity.core.collection;

import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.lang.OrderedKeys;



/**
*
* @author Jonathan Colt
*/
public class CBiDiDictionary {
    private CSet<BiDi> bidis = new CSet<BiDi>();
    public CBiDiDictionary() {
        
    }
    public void removeAll() {
        bidis.removeAll();
    }
    public void add(final Object a,final Object b) {
        bidis.add(new BiDi(b) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<Object>(a,true);
            }
        });
        bidis.add(new BiDi(a) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<Object>(b,false);
            }
        });
    }
    public void remove(final Object a,final Object b) {
        bidis.remove(new BiDi(b) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<Object>(a,true);
            }
        });
        bidis.remove(new BiDi(a) {
            @Override
            public Object hashObject() {
                return new OrderedKeys<Object>(b,false);
            }
        });
    }
    public Object forwardLookup(Object a) {
        BiDi bidi = bidis.get(new OrderedKeys<Object>(a,true));
        if (bidi == null) return null;
        return bidi.get();
    }
    public Object reverseLookup(Object b) {
        BiDi bidi = bidis.get(new OrderedKeys<Object>(b,false));
        if (bidi == null) return null;
        return bidi.get();
    }

    abstract class BiDi extends ASetObject<Object> {
        Object _v;
        BiDi(Object v) {
            _v = v;
        }
        public Object get() {
            return _v;
        }
    }
}
