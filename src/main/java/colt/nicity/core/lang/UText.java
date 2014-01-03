/*
 * UText.java.java
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
package colt.nicity.core.lang;

import colt.nicity.core.collection.CArray;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Administrator
 */
public class UText {
    /**
     *
     * @param _file
     * @return
     */
    public static String[] loadTextFile(File _file) {
        CArray<String> lines = new CArray<String>(String.class);
        RandomAccessFile filer = null;
        try {
            filer = new RandomAccessFile(_file, "r");
            for (String line = filer.readLine(); line != null; line = filer.readLine()) {
                lines.insertLast(line);
            }
        } catch (IOException e) {
            try {
                filer.close();
            } catch (Exception xx) {
            }
            return null;
        }
        try {
            filer.close();
        } catch (Exception xx) {
        }
        return lines.getAll();
    }
    /**
     *
     * @param _lines
     * @param _file
     */
    public static void saveTextFile(Object[] _lines, File _file) {
        RandomAccessFile filer = null;
        if (_lines == null) {
            _lines = new Object[0];
        }
        if (_file == null) {
            return;
        }
        try {
            UFile.ensureDirectory(_file);
            filer = new RandomAccessFile(_file, "rw");
            filer.setLength(filer.getFilePointer());
            for (int l = 0; l < _lines.length; l++) {
                if (_lines[l] == null) {
                    continue;
                }
                filer.write((_lines[l].toString() + "\n").getBytes());
            }
            filer.close();
        } catch (Exception x) {
            System.out.println(_lines);
            System.out.println(filer);
            x.printStackTrace();
        }
    }
    /**
     *
     * @param _buffer
     * @param _file
     */
    public static void saveTextFile(StringBuffer _buffer, File _file) {
        RandomAccessFile filer = null;
        if (_file == null) {
            return;
        }
        try {
            UFile.ensureDirectory(_file);
            filer = new RandomAccessFile(_file, "rw");
            filer.setLength(filer.getFilePointer());
            filer.write(_buffer.toString().getBytes());
            filer.close();
        } catch (Exception x) {
            System.out.println(filer);
            x.printStackTrace();
        }
    }
    /**
     *
     * @param _line
     * @param _file
     */
    public static void appendTextFile(String _line, File _file) {
        appendTextFile(new String[]{_line}, 0, 1, _file);
    }
    /**
     *
     * @param _lines
     * @param _s
     * @param _l
     * @param _file
     */
    public static void appendTextFile(String[] _lines, int _s, int _l, File _file) {
        try {
            UFile.ensureDirectory(_file);
            RandomAccessFile filer = new RandomAccessFile(_file, "rw");
            filer.seek(filer.length());
            filer.setLength(filer.getFilePointer());
            for (int l = _s; l < _l; l++) {
                filer.write((_lines[l] + "\n").getBytes());
            }
            filer.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    /**
     *
     * @param _buffer
     * @param _file
     */
    public static void appendTextFile(StringBuffer _buffer, File _file) {
        try {
            UFile.ensureDirectory(_file);
            RandomAccessFile filer = new RandomAccessFile(_file, "rw");
            filer.seek(filer.length());
            filer.setLength(filer.getFilePointer());
            filer.write(_buffer.toString().getBytes());
            filer.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    /**
     *
     * @param _file
     * @return
     */
    public static String toString(File _file) {
        StringBuilder fileString = new StringBuilder();
        RandomAccessFile filer = null;
        try {
            filer = new RandomAccessFile(_file, "r");
            for (String line = filer.readLine(); line != null; line = filer.readLine()) {
                fileString.append(line + "\n");
            }
            filer.close();
        } catch (IOException e) {
            try {
                filer.close();
            } catch (Exception xx) {
            }
            return e.toString();
        }
        return fileString.toString();
    }
    /**
     *
     * @param _file
     * @return
     */
    public static byte[] toBytes(File _file) {
        RandomAccessFile filer = null;
        byte[] bytes = null;
        try {
            filer = new RandomAccessFile(_file, "r");
            bytes = new byte[(int) filer.length()];
            filer.read(bytes);
            filer.close();
        } catch (IOException e) {
            try {
                filer.close();
            } catch (Exception xx) {
            }
            return null;
        }
        return bytes;
    }
    /**
     *
     * @param _val
     * @param _caseSensitive
     * @return
     */
    public static String[] tokenizeASCIIletters(String _val, boolean _caseSensitive) {
        // anything not a-z or A-Z is skipped and used as delimiter
        String[] results = new String[(_val.length() / 5) + 1];
        int resultLen = 0;
        char[] a = new char[_val.length()];
        _val.getChars(0, _val.length(), a, 0);
        boolean lookingForStart = true;
        int start = 0;
        int end = 0;
        for (int i = 0; i < a.length; i++) {
            char k = a[i];

            if (lookingForStart) {
//!!			if (! Character.isLetterOrDigit(k)) continue;
                if (!Character.isLetter(k)) {
                    continue;
                }
                start = i;
                lookingForStart = false;
                continue;
            }

            //if (! Character.isWhitespace(k)) continue;
            switch (Character.getType(k)) {
                case Character.SPACE_SEPARATOR:
                    break;
                case Character.LINE_SEPARATOR:
                    break;
                case Character.PARAGRAPH_SEPARATOR:
                    break;
                case Character.START_PUNCTUATION:
                    break;
                case Character.END_PUNCTUATION:
                    break;
                case Character.INITIAL_QUOTE_PUNCTUATION:
                    break;
                case Character.FINAL_QUOTE_PUNCTUATION:
                    break;
                case Character.OTHER_PUNCTUATION:
                    break;
                default:
                    continue;
            }


            end = i;
            lookingForStart = true;

            int len = end - start;
            char[] b = new char[len];
            for (int j = 0; j < len; j++) {
                k = a[start + j];
                if (!_caseSensitive) {
                    b[j] = Character.toLowerCase(k);
                } //!!faster		if (!_caseSensitive && k<97 && k>64) b[j] = (char) (k+32); // then lower case only
                else {
                    b[j] = k;
                }
            }
            String s = new String(b);
            if (resultLen >= results.length) {
                results = UArray.grow(results, new String[resultLen * 2]);
            }
            results[resultLen++] = s;
        }
        return UArray.trim(results, new String[resultLen]);
    }
}
