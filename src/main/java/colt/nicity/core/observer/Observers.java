/*
 * Observers.java.java
 *
 * Created on 12-27-2009 03:12:00 PM
 *
 * Copyright 2009 Jonathan Colt
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
package colt.nicity.core.observer;

import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.UArray;
import java.lang.ref.SoftReference;

/*
 * Acts as a hub to which an IObserver can bind and then will recieve Change events
 */
/**
 *
 * @author Administrator
 */
abstract public class Observers extends ASetObject implements IObserver {
    abstract void dettach();
    /**
     *
     */
    protected SoftReference[] observers;
    private IObservable observable;
    /**
     *
     * @param _observable
     */
    public Observers(IObservable _observable) {
        observable = _observable;
    }
    /**
     *
     * @return
     */
    @Override
    final public Object hashObject() {
        return observable;
    }
    /**
     *
     * @return
     */
    public int numberOfObservers() {
        SoftReference[] _locol = observers;
        if (_locol == null) {
            return 0;
        }
        return _locol.length;
    }
    // Observers are cached softly therefore you must keep a hard refrence to you observers elsewhere
    // need to sync
    /**
     *
     * @param _observer
     * @return
     */
    public IObserver bind(IObserver _observer) {
        if (_observer == null) {
            return _observer;
        }
        if (observers == null) {
            observers = new SoftReference[]{new SoftReference(_observer)};
        } else {
            SoftReference[] newObservers = new SoftReference[observers.length + 1];
            int j = 0;
            for (int i = 0; i < observers.length; i++) {
                IObserver observer = (IObserver) observers[i].get();
                if (observer == null) {
                    continue; // soft released
                }
                if (observer == _observer) {
                    continue; // skip cause it will be added at end of loop
                }
                newObservers[j] = observers[i];
                j++;
            }
            newObservers[j] = new SoftReference(_observer);// add to end of list
            j++;
            observers = (SoftReference[]) UArray.trim(newObservers, new SoftReference[j]);
        }
        _observer.bound(observable);
        return _observer;
    }
    // need to sync
    /**
     *
     * @param _observer
     */
    public void release(IObserver _observer) {
        SoftReference[] newObservers = new SoftReference[observers.length];
        int j = 0;
        for (int i = 0; i < observers.length; i++) {
            IObserver observer = (IObserver) observers[i].get();
            if (observer == null) {
                continue;
            }
            if (observer == _observer) {
                continue;
            }
            newObservers[j] = observers[i];
            j++;
        }
        observers = (SoftReference[]) UArray.trim(newObservers, new SoftReference[j]);
        _observer.bound(observable);
    }
    /**
     *
     * @param _change
     */
    public void broadcast(Change _change) {
        if (observers == null) {
            return;
        }
        int nulls = 0;
        for (int i = 0; i < observers.length; i++) {
            IObserver observer = (IObserver) observers[i].get();
            if (observer != null) {
                observer.change(_change);
            } else {
                nulls += 1;
            }
        }
        if (nulls > 0) {
            observers = (SoftReference[]) UArray.removeNulls(observers, SoftReference.class);
            if (observers.length == 0) {
                dettach();
            }
        }
    }
    // IObserver
    /**
     *
     * @param _observable
     */
    @Override
    public void bound(IObservable _observable) {
    }
    /**
     *
     * @param _change
     */
    @Override
    public void change(Change _change) {
        broadcast(_change);
    }
    /**
     *
     * @param _observable
     */
    @Override
    public void released(IObservable _observable) {
    }
}
