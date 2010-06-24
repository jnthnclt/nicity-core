/*
 * KVO.java.java
 *
 * Created on 03-12-2010 06:29:57 PM
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

package colt.nicity.core.value;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.process.IAsyncResponse;


/**
 *
 * @author Administrator
 */
public class KVO extends KV {
    /**
     *
     * @param _key
     */
    public KVO(Object _key) {
        super(_key,new CSet());
    }
    /**
     *
     * @param _
     * @param _kvs
     */
    public void add(IOut _,KV... _kvs) {
        ((CSet)value).add(_kvs);
    }
    /**
     *
     * @param _
     * @param _kvs
     */
    public void remove(IOut _,KV... _kvs) {
        ((CSet)value).remove(_kvs);
    }
    // _wait in millis.. 0 == wait forever
    /**
     *
     * @param _
     * @param _kv
     * @param _wait
     * @param _took
     */
    public void take(IOut _,KV _kv,long _wait,IAsyncResponse<KV> _took) {
        synchronized(value) {
            KV take = (KV)((CSet)value).remove(_kv);
            if (take == null) {

            }
            else {
                if (take instanceof WaitToTake) {
                    
                }
                else {
                    
                }
            }
        }
    }
    /**
     *
     * @param _
     * @param _kv
     * @param _copied
     */
    public void copy(IOut _,KV _kv,IAsyncResponse<KV> _copied) {
        
    }

    class Took extends KV {
        Took(Object _key,KV _took) {
            super(_key,_took);
        }
    }

    class WaitToTake extends KV {
        WaitToTake(Object _key,IAsyncResponse _took) {
            super(_key,_took);
        }
        
    }
}
