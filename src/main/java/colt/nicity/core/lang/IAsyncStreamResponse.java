/*
 * IStreamCallback.java
 *
 * Created on Apr 7, 2010, 8:43:31 PM
 *
 * Copyright Apr 7, 2010 Jonathan Colt 
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


package colt.nicity.core.lang;



/**
*
 * @param <V> type of value being streamed
 * @param <E> type of error message
 * @author Jonathan Colt
*/
public interface IAsyncStreamResponse<V,E> {
    /**
     *
     * @param _
     * @param value value being streamed
     * @param last used to denot end of stream
     * @return false to stop stream
     */
    public boolean response(IOut _,V value,boolean last);
    /**
     *
     * @param _
     * @param _e
     */
    public void error(IOut _, E _e);
}
