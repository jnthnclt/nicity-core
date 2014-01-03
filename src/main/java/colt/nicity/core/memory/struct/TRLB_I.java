/*
 * XYWH_I.java.java
 *
 * Created on 03-12-2010 11:33:27 PM
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
package colt.nicity.core.memory.struct;

/**
 * Top Right Left Bottom
 * @author Administrator
 */
public class TRLB_I {

    /**
     *
     */
    public int top;
    /**
     *
     */
    public int right;
    /**
     *
     */
    public int left;
    /**
     *
     */
    public int bottom;

    /**
     *
     */
    public TRLB_I() {
    }

    public TRLB_I(int top, int right, int left, int bottom) {
        this.top = top;
        this.right = right;
        this.left = left;
        this.bottom = bottom;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TRLB_I other = (TRLB_I) obj;
        if (this.top != other.top) {
            return false;
        }
        if (this.right != other.right) {
            return false;
        }
        if (this.left != other.left) {
            return false;
        }
        if (this.bottom != other.bottom) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.top;
        hash = 47 * hash + this.right;
        hash = 47 * hash + this.left;
        hash = 47 * hash + this.bottom;
        return hash;
    }

   


    @Override
    public String toString() {
        return super.toString() + " [" + top + "," + right + "," + left + "," + bottom + "]";
    }
}
