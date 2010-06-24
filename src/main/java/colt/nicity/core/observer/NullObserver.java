/*
 * NullObserver.java.java
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

/**
 *
 * @author Administrator
 */
public class NullObserver implements IObserver {
    /**
     *
     */
    public static NullObserver cNull = new NullObserver();
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
    }
    /**
     *
     * @param _observable
     */
    @Override
    public void released(IObservable _observable) {
    }
}
