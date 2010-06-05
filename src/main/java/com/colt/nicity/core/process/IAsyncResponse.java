/*
 * IAsyncResponse.java.java
 *
 * Created on 01-13-2010 07:11:00 AM
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
package com.colt.nicity.core.process;

import com.colt.nicity.core.lang.IOut;

/**
 *
 * @author Administrator
 * @param <R>
 */
public interface IAsyncResponse<R> {
    /**
     *
     * @param _
     * @param _response
     */
    public void response(IOut _,R _response);
    /**
     *
     * @param _
     * @param _t
     */
    public void error(IOut _,Throwable _t);
}
