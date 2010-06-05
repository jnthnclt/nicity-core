/*
 * UTrace.java.java
 *
 * Created on 01-02-2010 09:28:00 AM
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
package com.colt.nicity.core.lang;

import com.colt.nicity.core.collection.CArray;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class UTrace extends OutputStream {

    private CArray<String> callers = new CArray<String>(String.class);

    /**
     *
     */
    public static void traceCaller() {
        try {
            throw new Exception();
        } catch (Exception x) {
            UTrace trace = new UTrace(x);
            System.out.println(trace.getCaller(4));
            System.out.println(trace.getCaller(3));
        }
    }

    /**
     *
     * @param _depth
     */
    public static void traceCaller(int _depth) {
        try {
            throw new Exception();
        } catch (Exception x) {
            UTrace trace = new UTrace(x);
            System.out.println("<trace>");
            for (int i = _depth; i > 0; i--) {
                String s = trace.getCaller(i + 2);
                if (s != null) {
                    System.out.println(s);
                }
            }
            System.out.println("</trace>");
        }
    }

    /**
     *
     * @param x
     */
    public UTrace(Throwable x) {
        x.printStackTrace(new PrintStream(this));
    }

    /**
     *
     * @return
     */
    public static String[] traceCalls() {
        try {
            throw new Exception();
        } catch (Exception x) {
            UTrace trace = new UTrace(x);
            return trace.getCalls();
        }
    }

    /**
     *
     * @return
     */
    public String getCaller() {
        return callers.getAt(1);
    }

    /**
     *
     * @param depth
     * @return
     */
    public String getCaller(int depth) {
        return callers.getAt(depth);
    }

    /**
     *
     * @return
     */
    public String[] getCalls() {
        return callers.getAll();
    }

    /**
     *
     */
    public void printCaller() {
        System.out.println(callers.getAt(1));
        System.out.println(callers.getAt(2));
        System.out.println(callers.getAt(3));
        System.out.println(callers.getAt(4));
        System.out.println(callers.getAt(5));
    }

    // OutputStream
    @Override
    public void write(int b) throws IOException {
        //System.out.println("int b("+b+")");
    }

    @Override
    public void write(byte b[]) throws IOException {
        //System.out.println("byte b[]("+b+")");
        write(b, 0, b.length);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        String line = new String(b, off, len).trim();
        if (line.length() > 1) {
            callers.insertLast(line);
        }
    }

    @Override
    public void flush() throws IOException {
        System.out.println("flush()");
    }

    @Override
    public void close() throws IOException {
        System.out.println("close()");
    }
}
