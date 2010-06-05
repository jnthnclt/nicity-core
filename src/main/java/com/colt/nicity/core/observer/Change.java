/*
 * Change.java.java
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
package com.colt.nicity.core.observer;

/**
 *
 * @author Administrator
 */
final public class Change {
    private Object mode,value,instance;
    /**
     *
     * @param _mode
     * @param _value
     * @param _instance
     */
    public Change(Object _mode,Object _value,Object _instance) {
        mode = _mode;
        value = _value;
        instance = _instance;
    }
    /**
     *
     * @return
     */
    final public Object mode() { return mode; }
    /**
     *
     * @return
     */
    final public Object value() { return value; }
    /**
     *
     * @return
     */
    final public Object instance() { return instance; }
}
