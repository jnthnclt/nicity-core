/*
 * ChangeObserver.java.java
 *
 * Created on 12-28-2009 12:47:00 PM
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
package com.colt.nicity.core.observer;

import com.colt.nicity.core.collection.CSet;

/**
 *
 * @author Administrator
 */
abstract public class ChangeObserver implements IObserver {
    private CSet<ChangeHandler> handlers = new CSet<ChangeHandler>();
    /**
     *
     * @param _handler
     */
    synchronized public void add(ChangeHandler _handler) {
        handlers.add(_handler);
    }
    /**
     *
     * @param _handler
     */
    synchronized public void remove(ChangeHandler _handler) {
        handlers.remove(_handler);
    }
    /**
     *
     * @return
     */
    synchronized public ChangeHandler[] all() {
        return handlers.getAll(ChangeHandler.class);
    }
    
    /**
     *
     * @param _change
     */
    @Override
    synchronized public void change(Change _change) {
        ChangeHandler handler = handlers.get(_change.mode());
        if (handler != null) {
            handler.change(_change);
        }
    }

}
