/*
 * SoftIndex.java.java
 *
 * Created on 03-12-2010 11:24:38 PM
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
package com.colt.nicity.core.memory;

import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.UArray;
import java.lang.ref.ReferenceQueue;

/**
 * Must call closeIndex to release worker thread
 */
public class SoftIndex<V, K, P> {

    final private CSet<SoftIndexRef<V, K, P>> set = new CSet<SoftIndexRef<V, K, P>>();
    private ReferenceQueue softIndexQueue;
    protected ICallback<SoftIndexRef<V, K, P>, SoftIndexRef<V, K, P>> released;
    public Object name = "SoftIndex";

    public SoftIndex() {
        this(null, (ICallback) null);
    }

    public SoftIndex(Object _name) {
        this(_name, (ICallback) null);
    }

    public SoftIndex(
        ICallback<SoftIndexRef<V, K, P>, SoftIndexRef<V, K, P>> _released) {
        this(null, _released);
    }

    public SoftIndex(Object _name,
        ICallback<SoftIndexRef<V, K, P>, SoftIndexRef<V, K, P>> _released) {
        if (_name != null) {
            name = _name;
        }
        released = _released;
        softIndexQueue = new ReferenceQueue();
        cleaner();
    }

    public void setReleaseCallback(
        ICallback<SoftIndexRef<V, K, P>, SoftIndexRef<V, K, P>> _released) {
        if (released == null) {
            released = _released;
        }
    }

    @Override
    public String toString() {
        return name + " (" + set.getCount() + ")";
    }

    public long getCount() {
        return set.getCount();
    }

    public V get(K _key) {
        synchronized (set) {
            SoftIndexRef<V, K, P> ref = set.get(_key);
            if (ref == null) {
                return null;
            }
            V got = ref.get();
            if (got == null) {
                set.remove(ref.getKey());
            }
            return got;
        }
    }

    public Object[] getAll() {
        synchronized (set) {
            SoftIndexRef<V, K, P>[] all = set.getAll(SoftIndexRef.class);
            int gi = 0;
            Object[] gots = new Object[all.length];
            for (SoftIndexRef<V, K, P> a : all) {
                V got = a.get();
                if (got == null) {
                    set.remove(a.getKey());
                }
                else {
                    gots[gi] = got;
                    gi++;
                }
            }
            return UArray.removeNulls(gots);
        }
    }
    public void getAll(IOut _,ICallback<SoftIndexRef<V, K, P>,SoftIndexRef<V, K, P>> callback) {
        set.backcall(_, callback);
    }

    public Object[] removeAll() {
        synchronized (set) {
            Object[] all = set.removeAll();
            int gi = 0;
            Object[] gots = new Object[all.length];
            for (Object a : all) {
                SoftIndexRef<V, K, P> ref = (SoftIndexRef<V, K, P>) a;
                V got = ref.get();
                if (got == null) {
                    set.remove(ref.getKey());
                }
                else {
                    gots[gi] = got;
                    gi++;
                }
            }
            return UArray.removeNulls(gots);
        }
    }

    public SoftIndexRef<V, K, P> getRef(K _key) {
        synchronized (set) {
            SoftIndexRef<V, K, P> ref = set.get(_key);
            if (ref == null) {
                return null;
            }
            V got = ref.get();
            if (got == null) {
                set.remove(ref.getKey());
            }
            return ref;
        }
    }

    public void remove(K _key) {
        synchronized (set) {
            SoftIndexRef<V, K, P> ref = set.get(_key);
            if (ref != null) {
                set.remove(ref.getKey());
                if (released != null) {
                    released.callback(ref);
                }
            }
        }
    }

    public Object set(V _value, K _key) {
        synchronized (set) {
            return set(_value, _key, null);
        }
    }

    public V set(V _value, K _key, P _payload) {
        synchronized (set) {
            if (cleanerThreadRunning) {
                SoftIndexRef<V, K, P> ref = set.get(_key);
                if (ref != null) {
                    V got = ref.get();
                    if (got != null) {
                        return got;
                    }
                }
                SoftIndexRef softRef = new SoftIndexRef(_value, _key, _payload,
                    softIndexQueue);
                set.add(softRef);
            }
            else {
                SoftIndexRef softRef = new SoftIndexRef(_value, _key, _payload,
                    null);
                if (released != null) {
                    released.callback(softRef);
                }
            }
            return _value;
        }
    }
    private boolean clearing = false;

    public void clear() {
        synchronized (set) {
            clearing = true;
        }
        while (set.getCount() > 0) {
            clearing(set.removeAll());
        }
        synchronized (set) {
            clearing = false;
        }
    }

    public void clearing(Object[] _clear) {
        if (released != null) {
            for (Object a : _clear) {
                released.callback((SoftIndexRef<V, K, P>) a);
            }
        }
    }

    public void closeIndex() {
        stopCleanerThread = true;
        while (isCleanerThreadRunning()) {
            Thread _cleaner = cleaner;
            if (_cleaner != null) {
                _cleaner.interrupt();
            }
        }
        clear();
    }

    public boolean isIndexValid() {
        synchronized (set) {
            return clearing ? false : cleanerThreadRunning;
        }
    }
    private Thread cleaner;
    private boolean cleanerThreadRunning = false;
    private boolean stopCleanerThread = false;

    private boolean isCleanerThreadRunning() {
        synchronized (set) {
            return cleanerThreadRunning;
        }
    }

    private void cleaner() {
        cleaner = new Thread() {

            @Override
            public void run() {
                do {
                    try {
                        SoftIndexRef ref = null;
                        try {
                            ref = (SoftIndexRef) softIndexQueue.remove();// Blocks
                        }
                        catch (InterruptedException x) {
                            continue;
                        }
                        synchronized (set) {
                            Object key = ref.getKey();
                            if (key != null) {
                                SoftIndexRef got = set.get(key);
                                if (got != null && got.get() != null) {
                                    continue;
                                }
                                set.remove(key);
                            }
                            if (released != null) {
                                released.callback(ref);
                            }
                        }
                    }
                    catch (Exception x) {
                        x.printStackTrace();
                    }
                }
                while (isCleanerThreadRunning() || stopCleanerThread);
                synchronized (set) {
                    cleanerThreadRunning = false;
                    cleaner = null;
                }
            }
        };
        synchronized (set) {
            cleanerThreadRunning = true;
            cleaner.start();
        }
    }
}
