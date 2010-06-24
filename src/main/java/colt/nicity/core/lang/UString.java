/*
 * UString.java.java
 *
 * Created on 12-29-2009 07:45:00 PM
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
import colt.nicity.core.collection.CSet;
import colt.nicity.core.comparator.AValueComparator;
import colt.nicity.core.comparator.UValueComparator;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class UString {
    //!! should use codePoint from unicode
    // Cleanup text
        /*
    _string = _string.replace('\n',' ');
    _string = _string.replace('\t',' ');
    _string = _string.replace(',',' ');
    _string = _string.replace('.',' ');
    _string = _string.replace('?',' ');
    _string = _string.replace('!',' ');
    _string = _string.replace('"',' ');
    _string = _string.replace(':',' ');
    _string = _string.replace(';',' ');
     */
    /**
     *
     * @param s
     * @return
     */
    public static String removePunctuation(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\n':
                    sb.append(" ");
                    break;
                case '(':
                    sb.append(" ");
                    break;
                case ')':
                    sb.append(" ");
                    break;
                case '[':
                    sb.append(" ");
                    break;
                case ']':
                    sb.append(" ");
                    break;
                case '{':
                    sb.append(" ");
                    break;
                case '}':
                    sb.append(" ");
                    break;
                case '|':
                    sb.append(" ");
                    break;
                case '\\':
                    sb.append(" ");
                    break;
                case '/':
                    sb.append(" ");
                    break;
                case '\r':
                    sb.append(" ");
                    break;
                case '\t':
                    sb.append(" ");
                    break;
                case '?':
                    sb.append(" ");
                    break;
                case '!':
                    sb.append(" ");
                    break;
                case ':':
                    sb.append(" ");
                    break;
                case ';':
                    sb.append(" ");
                    break;
                case '"':
                    sb.append(" ");
                    break;
                case '\'':
                    if (i - 1 > -1 && i + i < s.length()) {
                        if (Character.isWhitespace(s.charAt(i - 1)) || Character.isWhitespace(s.charAt(i + 1))) {
                            sb.append(" ");
                        } else {
                            sb.append("'");
                        }
                    }
                    break;
                case '.':
                    if (i + i < s.length()) {
                        if (Character.isDigit(s.charAt(i + 1))) {
                            sb.append(".");
                        } else {
                            sb.append(" ");
                        }
                    } else {
                        sb.append(" ");
                    }
                    break;
                case ',':
                    if (i - 1 >= 0 && i + i < s.length()) {
                        if (Character.isDigit(s.charAt(i - 1)) && Character.isDigit(s.charAt(i + 1))) {
                            sb.append(",");
                        } else {
                            sb.append(" ");
                        }
                    } else {
                        sb.append(" ");
                    }
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }
    /*public static void main(String[] _arg) {
    String source = " the quick brown fox \t jumped over the lazy dog";
    String selection = " Fox \n jumped    over  ";
    int[] sel = bestOverlap(source, selection);
    System.out.println(sel[0]+" "+sel[1]+" "+sel[2]);
    }*/
    // returns start, end and source length
    /**
     *
     * @param _source
     * @param _selection
     * @return
     */
    public static int[] bestOverlap(String _source, String _selection) {
        //!! naive brute force
        char[] a = _source.toLowerCase().toCharArray();
        char[] b = _selection.toLowerCase().toCharArray();
        int bestS = 0;
        int bestE = a.length;
        int bestMissCount = a.length;
        NextChar:
        for (int i = 0; i < a.length; i++) {
            if (Character.isWhitespace(a[i])) {
                continue;
            }
            int find = 0;
            while (Character.isWhitespace(b[find])) {
                find++;
            }
            if (a[i] == b[find]) {
                int lastValidIndex = 0;
                int missed = 0;
                Overlap:
                for (int j = i; find < b.length && j < a.length; j++) {
                    if (Character.isWhitespace(a[j])) {
                        continue;
                    }
                    while (Character.isWhitespace(b[find])) {
                        find++;
                        if (find >= b.length) {
                            break Overlap;
                        }
                    }
                    if (a[j] == b[find]) {
                        find++;
                        lastValidIndex = j + 1;
                    } else {
                        missed++;
                        if (missed > bestMissCount) {
                            continue NextChar;// early exit
                        }
                    }
                }

                if (find == b.length) {
                    if (missed < bestMissCount) {
                        bestS = i;
                        bestE = lastValidIndex;
                        bestMissCount = missed;
                    }
                }
            }
        }
        return new int[]{bestS, bestE, a.length};
    }
    /**
     *
     * @param _names
     * @return
     */
    public static Object[] shortestUniqueChunks(Object[] _names) {
        Arrays.sort(_names, UValueComparator.lowercase(AValueComparator.cAscending));
        int l = _names.length;
        ChunkString[] chunkStrings = new ChunkString[l];
        for (int i = 0; i < l; i++) {
            chunkStrings[i] = new ChunkString(_names[i].toString().toLowerCase().toCharArray());
        }
        for (int i = 0; i < l; i++) {
            ChunkString a = chunkStrings[i];
            for (int j = 0; j < l; j++) {
                if (i == j) {
                    continue;
                }
                ChunkString b = chunkStrings[j];
                if (a.unique(b)) {
                    continue;
                } else {
                    j = 0;
                }
            }
        }
        return chunkStrings;
    }

    static class ChunkString {
        char[] chars;
        int uniqueHead;
        ChunkString(char[] _chars) {
            chars = _chars;
            uniqueHead = 1;
        }
        public boolean unique(ChunkString _value) {
            char[] as = chars;
            char[] bs = _value.chars;
            for (int i = 0; i < uniqueHead; i++) {
                if (as[i] != bs[i]) {
                    return true;
                }
            }
            uniqueHead++;
            if (uniqueHead < as.length) {
                return false;
            }
            return true;
        }
        @Override
        public String toString() {
            return new String(chars, 0, uniqueHead) + "," + new String(chars, uniqueHead, chars.length - uniqueHead);
        }
    }
    /**
     *
     * @param _tokens
     * @return
     */
    public static Object[] duplicates(Object[] _tokens) {
        CSet set = new CSet();
        CSet duplicates = new CSet();
        for (int i = 0; i < _tokens.length; i++) {
            if (set.get(_tokens[i]) == null) {
                set.add(_tokens[i]);
            } else {
                duplicates.add(_tokens[i]);
            }
        }
        return duplicates.getAll(Object.class);
    }
    /**
     *
     * @param _string
     * @return
     */
    public static final String noPunctuation(String _string) {
        char[] in = _string.toCharArray();
        char[] out = new char[in.length];
        int l = 0;
        for (int i = 0; i < in.length; i++) {
            int t = Character.getType(in[i]);
            if (in[i] == '`') {
                continue;//??
            }
            if (in[i] == ',') {
                continue;//??
            }
            if (in[i] == '.') {
                continue;//??
            }
            if (t == Character.CONNECTOR_PUNCTUATION) {
                continue;
            }
            if (t == Character.DASH_PUNCTUATION) {
                in[i] = ' ';
            }
            if (t == Character.END_PUNCTUATION) {
                continue;
            }
            if (t == Character.FINAL_QUOTE_PUNCTUATION) {
                continue;
            }
            if (t == Character.INITIAL_QUOTE_PUNCTUATION) {
                continue;
            }
            if (t == Character.START_PUNCTUATION) {
                continue;
            }
            out[l] = in[i];
            l++;
        }
        return new String(UArray.trim(out, new char[l]));
    }
    /**
     *
     * @param _string
     * @return
     */
    public static final long checkSum(String _string) {
        if (_string == null) {
            return -1;
        }
        long sum = 0;
        char[] chars = _string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sum += chars[i];
        }
        return sum;
    }
    // given a b c d, ""
    // you get:
    // a b c d
    // ab c d
    // ab cd
    // a bc d
    // a bcd
    // a b cd
    // abc d
    // a bcd
    // abcd
    /**
     *
     * @param _strings
     * @param _delim
     * @return
     */
    public static String[][] combinations(String[] _strings, String _delim) {
        CArray solutions = new CArray(String[].class);
        int count = _strings.length;
        for (int l = 1; l < ((count) + 1); l++) {
            String string = toString(_strings, 0, l, _delim);
            String[] _post = UArray.copy(_strings, l, count - (l));
            String[][] post = combinations(_post, _delim);
            if (post.length > 0) {
                for (int b = 0; b < post.length; b++) {
                    String[] r = UArray.join(new String[]{string}, post[b]);
                    solutions.insertLast((Object) r);
                }
            } else {
                solutions.insertLast((Object) new String[]{string});
            }
        }

        return (String[][]) solutions.getAll();
    }
    // given a b c d, ""
    // you get:
    // a
    // b
    // c
    // d
    // ab
    // cd
    // bc
    // bcd
    // abc
    // abcd
    /**
     *
     * @param _strings
     * @param _delim
     * @return
     */
    public static String[] permutations(String[] _strings, String _delim) {
        CSet set = new CSet();
        CArray solutions = new CArray(String.class);
        int count = _strings.length;
        for (int l = 1; l < ((count) + 1); l++) {
            String string = toString(_strings, 0, l, _delim);
            String[] _post = UArray.copy(_strings, l, count - (l));
            String[] post = permutations(_post, _delim);
            if (post.length > 0) {
                for (int b = 0; b < post.length; b++) {
                    if (set.get(post[b]) == null) {
                        set.add(post[b]);
                        solutions.insertLast(post[b]);
                    }
                }
            }
            if (set.get(string) == null) {
                set.add(string);
                solutions.insertLast(string);
            }
        }
        return (String[]) solutions.getAll();
    }
    /*

    public static HashTable spaceControl = null;

    public static Exception loadSpaceControl(String _control) {
    // . t . n . t . where "." is whitespace delimiter, "t" is one or two chars or "T", and n is 0 or 1
    // "T" means any token not otherwise specified in the control file full of "t"
    String[] tokens = toStringArray(_control);
    if (spaceControl==null) spaceControl = new HashTable(tokens.length/3);
    try {
    for (int i=0;i<tokens.length-3;i+=3) {
    String key = tokens[i] +" " + tokens[i+2];
    Integer value = new Integer(tokens[i+1]);
    spaceControl.insertAt(value,key);
    }
    } catch(Exception x) {
    spaceControl = null;
    System.out.println(x);
    return x;
    }
    return null;
    }

    public static String detokenized(Object[] _tokens,String _delim) {
    if (spaceControl==null) return "";
    StringBuffer buffer = new StringBuffer();
    for (int i=0; i<_tokens.length-1; i++) {
    String t = _tokens[i].toString();
    buffer.append(t);
    if (spaceCheck(t,_tokens[i+1].toString())) buffer.append(_delim);
    }
    buffer.append(_tokens[_tokens.length-1].toString());
    return buffer.toString();
    }

    public static boolean spaceCheck(String _s1, String _s2) {
    if (spaceControl==null) return false;
    String key1 = _s1 + " " + _s2;
    String key2 = "T" + " " + _s2;
    String key3 = _s1 + " " + "T";
    Integer spaces = null;
    if (spaces==null) spaces = (Integer) spaceControl.getAt(key1);
    if (spaces==null) spaces = (Integer) spaceControl.getAt(key2);
    if (spaces==null) spaces = (Integer) spaceControl.getAt(key3);
    if (spaces==null) return true;
    if (spaces.intValue()>0) return true;
    return false;
    }
     */
    /**
     *
     * @param _source
     * @param _pattern
     * @param _replacement
     * @return
     */
    public static String replaceAll(String _source, String _pattern, String _replacement) {
        int i = 0;
        int j = -1;
        j = _source.indexOf(_pattern);
        if (j < 0) {
            return _source;
        }
        StringBuilder buffer = new StringBuilder();

        while (j >= 0) {
            buffer.append(_source.substring(i, j));
            if (_replacement != null) {
                buffer.append(_replacement);
            }
            j += _pattern.length();
            i = j;
            j = _source.indexOf(_pattern, j);
        }
        buffer.append(_source.substring(i));
        return buffer.toString();
    }
    /**
     *
     * @param _chars
     * @return
     */
    public static String[] toStringArray(char[] _chars) {
        String[] s = new String[_chars.length];
        for (int i = 0; i < _chars.length; i++) {
            s[i] = new String(_chars, i, 1);
        }
        return s;
    }
    /*
    public static void main(String[] _arg) {
    System.out.println(replaceAll("0123456789/#/0123456789","/#/",null));
    System.out.println(replaceAll("01234567890123456789","01",null));
    System.out.println(replaceAll("01234567890123456789","01","123"));
    System.out.println(replaceAll("01234567890123456789","567","x"));
    }*/
    /**
     *
     * @param string
     * @return
     */
    public static boolean isNumeric(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param _s
     * @param _e
     * @param _step
     * @return
     */
    public static String[] toStringArray(int _s, int _e, int _step) {
        String[] strings = new String[_e - _s / _step];
        for (int i = _s, s = 0; i < _e; i += _step, s++) {
            strings[s] = String.valueOf(i);
        }
        return strings;
    }
    /**
     *
     * @param _i
     * @param _significant
     * @param _fill
     * @return
     */
    public static String toString(int _i, int _significant, char _fill) {
        String s = Integer.toString(_i, 10);
        int pad = _significant - s.length();
        char[] fill = new char[pad];
        Arrays.fill(fill, _fill);
        return new String(fill) + s;
    }
    /**
     *
     * @param _s
     * @param _significant
     * @param _fill
     * @return
     */
    public static String toString(String _s, int _significant, char _fill) {
        String s = _s;
        int pad = _significant - s.length();
        char[] fill = new char[pad];
        Arrays.fill(fill, _fill);
        return new String(fill) + s;
    }
    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static boolean equalIgnoreWhitespace(String _a, String _b) {
        if (_a == null ? _b == null : _a.equals(_b)) {
            return true;
        }
        if (_a == null || _b == null) {
            return false;
        }

        char[] a = _a.toCharArray();
        char[] b = _b.toCharArray();

        int al = a.length;
        int bl = b.length;

        int ia = 0;
        int ib = 0;

        for (; ia < al && ib < bl; ia++, ib++) {
            if (Character.isWhitespace(a[ia])) {
                continue;
            }
            for (; ib < bl; ib++) {
                if (Character.isWhitespace(b[ib])) {
                    continue;
                } else {
                    break;
                }
            }
            if (a[ia] != b[ib]) {
                return false;
            }
        }

        for (ia++; ia < al; ia++) {
            if (!Character.isWhitespace(a[ia])) {
                return false;
            }
        }
        for (ib++; ib < bl; ib++) {
            if (!Character.isWhitespace(b[ib])) {
                return false;
            }
        }

        return true;
    }
    /**
     *
     * @param line
     * @return
     */
    public static String lowerCaseAtoZ(String line) {
        line = line.toLowerCase();
        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c > 96 && c < 123) {
                continue;
            }
            chars[i] = ' ';
        }
        line = new String(chars);
        return line;
    }
    /**
     *
     * @param string
     * @return
     */
    public static String[] toStringArray(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string);
        int tokenCount = tokenizer.countTokens();

        String[] tokens = new String[tokenCount];
        for (int i = 0; i < tokenCount; i++) {
            tokens[i] = tokenizer.nextToken();
        }
        return tokens;
    }
    /**
     *
     * @param strings
     * @param delim
     * @return
     */
    public static String toString(Object[] strings, String delim) {
        StringBuilder string = new StringBuilder();
        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                if (strings[i] == null) {
                    continue;
                }
                string.append(strings[i].toString());
                if (i < strings.length - 1) {
                    string.append(delim);
                }
            }
        }
        return string.toString();
    }
    /**
     *
     * @param strings
     * @param delim
     * @return
     */
    public static String toString(long[] strings, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            string.append(String.valueOf(strings[i]));
            if (i < strings.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    /**
     *
     * @param strings
     * @param delim
     * @return
     */
    public static String toString(byte[] strings, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            string.append(String.valueOf(strings[i]));
            if (i < strings.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    /**
     *
     * @param strings
     * @param delim
     * @return
     */
    public static String toString(double[] strings, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            string.append(String.valueOf(strings[i]));
            if (i < strings.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    /**
     *
     * @param strings
     * @param delim
     * @return
     */
    public static String toString(int[] strings, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            string.append(String.valueOf(strings[i]));
            if (i < strings.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    /**
     *
     * @param strings
     * @param _start
     * @param _len
     * @param delim
     * @return
     */
    public static String toString(Object[] strings, int _start, int _len, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = _start; i < (_start + _len); i++) {
            if (strings[i] == null) {
                continue;
            }
            string.append(strings[i].toString());
            if (i < (_start + _len) - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    /**
     *
     * @param string
     * @param delim
     * @return
     */
    public static String[] toStringArray(String string, String delim) {
        if (string == null || delim == null) {
            return new String[0];
        }
        StringTokenizer tokenizer = new StringTokenizer(string, delim);
        int tokenCount = tokenizer.countTokens();

        String[] tokens = new String[tokenCount];
        for (int i = 0; i < tokenCount; i++) {
            tokens[i] = tokenizer.nextToken();
        }
        return tokens;
    }
    /**
     *
     * @param _strings
     * @param _remove
     * @return
     */
    public static String[] remove(String[] _strings, String[] _remove) {
        if (_strings == null) {
            return new String[0];
        }
        if (_remove == null) {
            return _strings;
        }
        CSet set = new CSet();
        for (int i = 0; i < _remove.length; i++) {
            set.add(_strings[i]);
        }

        CArray array = new CArray(String.class);
        for (int i = 0; i < _strings.length; i++) {
            if (set.get(_strings[i]) == null) {
                array.insertLast(_strings[i]);
                set.add(_strings[i]);
            }
        }
        return (String[]) array.getAll();
    }
    /**
     *
     * @param _tokens
     * @return
     */
    public static Object[] toSet(Object[] _tokens) {
        CSet words = new CSet(_tokens.length * 2);
        for (int i = 0; i < _tokens.length; i++) {
            String w = _tokens[i].toString();
            words.add(w.intern());
        }
        return words.getAll(Object.class);
    }
    /**
     *
     * @param _tokens
     * @return
     */
    public static Object[] toLowerCaseSet(Object[] _tokens) {
        CSet words = new CSet(_tokens.length * 2);
        for (int i = 0; i < _tokens.length; i++) {
            String w = _tokens[i].toString().toLowerCase();
            words.add(w.intern());
        }
        return words.getAll(Object.class);
    }
    /**
     *
     * @param _strings
     * @return
     */
    public static String[] toSet(String[] _strings) {
        return removeDuplicates(_strings);
    }
    /**
     *
     * @param _strings
     * @return
     */
    public static String[] removeDuplicates(String[] _strings) {
        if (_strings == null) {
            return new String[0];
        }
        CSet set = new CSet();
        CArray array = new CArray(String.class);
        for (int i = 0; i < _strings.length; i++) {
            if (_strings[i] == null) {
                continue;
            }
            if (set.get(_strings[i]) == null) {
                array.insertLast(_strings[i]);
                set.add(_strings[i]);
            }
        }
        return (String[]) array.getAll();
    }
    /**
     *
     * @param _strings
     * @return
     */
    public static String[] lowerCaseSet(String[] _strings) {
        if (_strings == null) {
            return new String[0];
        }
        CSet set = new CSet();
        CArray array = new CArray(String.class);
        for (int i = 0; i < _strings.length; i++) {
            String s = _strings[i].toLowerCase();
            if (set.get(s) == null) {
                array.insertLast(s);
                set.add(s);
            }
        }
        return (String[]) array.getAll();
    }
    /**
     *
     * @param _readme
     */
    public static void printCommandLineReadMe(String[] _readme) {
        for (int r = 0; r < _readme.length; r++) {
            System.out.println(_readme[r]);
        }
    }
    /**
     *
     * @param s
     * @return
     */
    public static long longHash(String s) {
        byte[] a = s.getBytes();
        long hash = 0;
        long work = 0;
        for (int i = 0; i < a.length; i++) {
            if (i % 64 == 0) {
                work += hash;
                hash = 0;
            }
            hash += ((hash << 1) + a[i]);
        }
        return hash + work;
    }
    /**
     *
     * @param s
     * @return
     */
    public static int intHash(String s) {
        byte[] a = s.getBytes();
        int hash = 0;
        int work = 0;
        for (int i = 0; i < a.length; i++) {
            if (i % 32 == 0) {
                work += hash;
                hash = 0;
            }
            hash += ((hash << 1) + a[i]);
        }
        return hash + work;
    }
    /**
     *
     * @param s
     * @return
     */
    public static short shortHash(String s) {
        byte[] a = s.getBytes();
        int hash = 0;
        int work = 0;
        for (int i = 0; i < a.length; i++) {
            if (i % 16 == 0) {
                work += hash;
                hash = 0;
            }
            hash += ((hash << 1) + a[i]);
        }
        return (short) (hash + work);
    }
    /**
     *
     * @param s
     * @return
     */
    public static byte byteHash(String s) {
        byte[] a = s.getBytes();
        int hash = 0;
        int work = 0;
        for (int i = 0; i < a.length; i++) {
            if (i % 8 == 0) {
                work += hash;
                hash = 0;
            }
            hash += ((hash << 1) + a[i]);
        }
        return (byte) (hash + work);
    }
    /**
     *
     * @param _input
     * @param _startPattern
     * @param _endPattern
     * @param _count
     * @param _inclusize
     * @param _os
     * @param _oe
     * @return
     */
    public static String[] getChunks(String _input, String _startPattern, String _endPattern, int _count, boolean _inclusize, int _os, int _oe) {
        CArray chunks = new CArray(String.class);
        Pattern sp = Pattern.compile(_startPattern);
        Pattern ep = Pattern.compile(_endPattern);

        Matcher sm = sp.matcher(_input);
        Matcher em = ep.matcher(_input);
        while (sm.find()) {
            int start = 0;
            if (_inclusize) {
                start = sm.start() + _os;
            } else {
                start = sm.end() + _os;
            }

            if (em.find(start)) {
                int end = em.start();
                if (_inclusize) {
                    end = em.end() + _oe;
                } else {
                    end = em.start() - _oe;
                }
                String chunk = _input.substring(start, end);
                chunks.insertLast(chunk);
            }
            if (_count != -1) {
                _count--;
                if (_count <= 0) {
                    break;
                }
            }
        }
        return (String[]) chunks.getAll();
    }
    /**
     *
     * @param _input
     * @param _s
     * @param _e
     * @param _fill
     * @return
     */
    public static String removeScope(String _input, char _s, char _e, char _fill) {
        int d = 0;
        int count = 0;
        char[] chars = _input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (_s != _e) {
                if (c == _s) {
                    d++;
                    continue;
                }
                if (c == _e) {
                    d--;
                    continue;
                }
            } else if (c == _s) {
                if (d == 0) {
                    d = 1;
                } else {
                    d = 0;
                }
            }
            if (d == 0) {
                chars[count++] = c;
            }
        }
        _input = new String(chars, 0, count);
        return _input;
    }
    /**
     *
     * @param _input
     * @param _s
     * @param _e
     * @return
     */
    public static String keepScope(String _input, char _s, char _e) {

        int d = 0;
        int count = 0;
        int revert = 0;
        char[] in = _input.toCharArray();
        char[] out = new char[in.length];
        boolean transfer = false;

        for (int i = 0; i < in.length; i++) {
            char c = in[i];
            if (c == _s) {
                revert = count;
                if (count - 1 > -1 && !Character.isWhitespace(out[count - 1])) {
                    out[count++] = ' ';
                }
                for (i++; i < in.length; i++) {
                    c = in[i];
                    if (c == '>') {
                        count = revert;
                        break;
                    }
                    if (c == '<' && i + 1 < in.length && in[i + 1] == '/') {
                        if (count - 1 > -1 && Character.isWhitespace(out[count - 1])) {
                            count--;
                        }
                        break;
                    }
                    // compress white space
                    if (Character.isWhitespace(in[i])) {
                        if (count - 1 > -1 && Character.isWhitespace(out[count])) {
                            continue;
                        }
                    }

                    if (Character.isWhitespace(in[i])) {
                        out[count++] = ' ';
                    } else {
                        out[count++] = in[i];
                    }
                }
            }
        }
        _input = new String(out, 0, count);
        return _input;
    }
    /**
     *
     * @param _input
     * @param _count
     * @return
     */
    public static String[] wrap(String _input, int _count) {
        if (_input.length() < _count) {
            return new String[]{_input};
        }
        CArray wraps = new CArray(String.class);
        int s = 0;
        int l = 0;
        char[] chars = _input.toCharArray();
        for (int i = 0; i < chars.length; i++, l++) {
            if (l > _count) {
                char c = chars[i];
                if (Character.isWhitespace(c)) {
                    wraps.insertLast(new String(chars, s, l));
                    l = 0;
                    s = i;
                }
            }
        }
        if (l > 0) {
            wraps.insertLast(new String(chars, s, l));
        }
        return (String[]) wraps.getAll();
    }
    /**
     * stringToLong and longToString hash functions
     * StringToLong hashes any string into a long; the hash function is perfect and even reversible if
     * the string contains only 1..10 lower case a..z. Otherwise the hash function is excellent,
     * uses all bytes, distinquishes between almost identical strings, including case sensitivity.
     * NOTE: stringToLong longs written out as byte[8] will usually prove unequal within one or two
     * byte compares, and this is > 10x faster than converting the bytes into longs for comparison.
     * @param _s
     * @return
     */
    public static long stringToLong(String _s) {
        if (_s == null) {
            return 0;
        }
        // string may be any length; all bytes used in hash; case sensitive!
        // If case insensitivity is desired, lowercase the string before using stringToLong
        // partially reversible using longToString
        String s = _s.toLowerCase();
        byte[] bytes = s.getBytes();
        long result = 0;
        int j = 0;
        long hash = 0;
        for (int i = 0; i < bytes.length; i++) {
            hash += bytes[i];
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i];
            v -= 'a';
            if (v < 0 || v > 'z') {
                continue;
            }
            hash += v;
            result <<= 5;
            result |= v + 1; // +1 because zero reserved
            if (++j > 10) {
                break;
            }
        }
        if (j < 10) {
            result <<= 5 * (10 - j);
        } else if (j > 10) {
            result >>>= 5;
        }

        hash *= hash;
        hash %= 16383; // 2^14 for 14 remaining bits after 64 - ten 5-bit alpha chars
        hash <<= 50;
        result |= hash;
        return result;
    }
    /**
     *
     * @param _long
     * @return
     */
    public static String longToString(long _long) {
        if (_long == 0) {
            return null;
        }
        // partially reverses stringToLong; skips over the 14-bit hash;
        // produces the lower case of the first 10 alpha chars found in the original string,
        // so is perfect reversal for such strings. Returns string.length <= 10.
        byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            long v = _long >>> (45 - i * 5);
            v &= 0x1F;
            if (v > 0) {
                v += 'a' - 1; // -1 because zero reserved
                bytes[i] = (byte) v;
                continue;
            } else { //done; zero terminated before max 10 reached; trim
                byte[] trim = new byte[i];
                System.arraycopy(bytes, 0, trim, 0, i);
                bytes = trim;
                break;
            }
        }
        return new String(bytes);
    }
    // any run of alphanumeric becomes one token; each special char becomes one token; white space delimited
    /**
     *
     * @param _
     * @param _data
     * @return
     */
    public static String[] tokenizeSpecialChars(IOut _, String _data) {
        if (_data == null) {
            return new String[0];
        }
        CArray words = new CArray(String.class, _data.length() / 4);
        char[] chars = _data.toCharArray();
        char[] word = new char[64];//!! a single word never be more than 64 characters
        int wordLength = 0;
        int len = chars.length;
        int s = 0;
        int e = 0;
        for (int i = 0; i < len; i++) {
            char c = chars[e++];
            if (Character.isLetterOrDigit(c)) {//
                try {
                    word[wordLength++] = c;
                } catch (Exception x) {
                    wordLength = 0;
                }
            } else {
                if (wordLength > 0) {
                    String w = new String(word, 0, wordLength);
                    words.insertLast(w);
                    wordLength = 0;
                }
                if (!Character.isWhitespace(c)) {
                    String w = String.valueOf(c); // all special chars except white space become tokens
                    words.insertLast(w);
                }
            }
        }
        if (wordLength > 0) {
            String w = new String(word, 0, wordLength);
            words.insertLast(w);
        }
        return (String[]) words.getAll();
    }
    // any run of alphanumeric becomes one token; each special char becomes one token; white space delimited
    /**
     *
     * @param _
     * @param _data
     * @return
     */
    public static String[] tokenizeSpecialCharsPlusNewLine(IOut _, String _data) {
        if (_data == null) {
            return new String[0];
        }
        CArray words = new CArray(String.class, _data.length() / 4);
        char[] chars = _data.toCharArray();
        char[] word = new char[128];//!! a single word never be more than 128 characters
        int wordLength = 0;
        int len = chars.length;
        int s = 0;
        int e = 0;
        for (int i = 0; i < len; i++) {
            char c = chars[e++];
            if (Character.isLetterOrDigit(c)) {//
                try {
                    word[wordLength++] = c;
                } catch (Exception x) {
                    wordLength = 0;
                }
            } else {
                if (wordLength > 0) {
                    String w = new String(word, 0, wordLength);
                    words.insertLast(w);
                    wordLength = 0;
                }

                if (c == '\n' || c == '\t') {
                    String w = String.valueOf(c);
                    words.insertLast(w);
                } else if (Character.isWhitespace(c)) {
                } else {
                    String w = String.valueOf(c);
                    words.insertLast(w);
                }
            }
        }
        if (wordLength > 0) {
            String w = new String(word, 0, wordLength);
            words.insertLast(w);
        }
        return (String[]) words.getAll();
    }
    // any run of alphanumeric becomes one token; each special char becomes one token; white space delimited
    /**
     *
     * @param _
     * @param _data
     * @return
     */
    public static String[] tokenize(IOut _, String _data) {
        if (_data == null) {
            return new String[0];
        }
        CArray words = new CArray(String.class, _data.length() / 4);
        char[] chars = _data.toCharArray();
        char[] word = new char[64];//!! a single word never be more than 64 characters
        int wordLength = 0;
        int len = chars.length;
        int s = 0;
        int e = 0;
        for (int i = 0; i < len; i++) {
            char c = chars[e++];
            if (Character.isLetterOrDigit(c)) {//
                try {
                    word[wordLength++] = c;
                } catch (Exception x) {
                    wordLength = 0;
                }
            } else {
                if (wordLength > 0) {
                    String w = new String(word, 0, wordLength);
                    words.insertLast(w);
                    wordLength = 0;
                }
                String w = String.valueOf(c);
                words.insertLast(w);
            }
        }
        if (wordLength > 0) {
            String w = new String(word, 0, wordLength);
            words.insertLast(w);
        }
        return (String[]) words.getAll();
    }
    /**
     *
     * @param _
     * @param _data
     * @return
     */
    public static String[] charStrings(IOut _, String _data) {
        if (_data == null) {
            return new String[0];
        }
        char[] chars = _data.toCharArray();
        CArray words = new CArray(String.class, chars.length);
        for (int i = 0; i < chars.length; i++) {
            String w = String.valueOf(chars[i]);
            words.insertLast(w);
        }
        return (String[]) words.getAll();
    }
    /**
     *
     * @param _tokens
     * @return
     */
    public static String[] toStrings(Object[] _tokens) {
        if (_tokens == null) {
            return new String[0];
        }
        String[] strings = new String[_tokens.length];
        for (int i = 0; i < _tokens.length; i++) {
            if (_tokens[i] == null) {
                strings[i] = "";
            } else {
                strings[i] = _tokens[i].toString();
            }
        }
        return strings;
    }
    /*public static void main(String[] _arg) {
    String s = _arg[0];
    long code = stringToLong(s);
    String decode = longToString(code);
    System.out.println("coded long="+code);
    System.out.println("decoded long="+decode);
    System.out.println("len="+decode.length());

    // timing test; long versus byte[] comparison
    Stopwatch sw1 = new Stopwatch();
    Stopwatch sw2 = new Stopwatch();
    byte[] randbytes = new byte[10];
    for (int i=0; i<100000; i++) {
    for (int j=0; j<10; j++) {
    //--------------------------------------------------------
    randbytes[j] = (byte) (65 + URandom.rand(65)); // alpha bytes
    String rs = new String(randbytes);
    long hash = stringToLong(rs);
    byte[] bytes1 = UIO.longBytes(hash);
    hash = stringToLong(rs+" ");
    byte[] bytes2 = UIO.longBytes(hash);
    //--------------------------------------------------------
    sw1.start();
    for (int k=0; k<10; k++) {
    for (int b=0; b<8; b++) if (bytes1[b] != bytes2[b]) break;
    }
    sw1.stop();
    //--------------------------------------------------------
    sw2.start();
    for (int k=0; k<10; k++) {
    long long1 = UIO.bytesLong(bytes1);
    long long2 = UIO.bytesLong(bytes2);
    if (long1 != long2) continue;
    }
    sw2.stop();

    }
    }
    System.out.println("bytes="+sw1);
    System.out.println("longs="+sw2);
    }*/
    /**
     *
     * @param _string
     * @return
     */
    public static boolean isAllUpperCase(String _string) {
        int l = _string.length();
        for (int i = 0; i < l; i++) {
            int code = _string.codePointAt(i);
            if (Character.isLetter(code)) {
                if (!Character.isUpperCase(code)) {
                    return false;
                }
            }
        }
        return true;
    }
    // any run of alphanumeric becomes one token; each special char becomes one token; white space delimited
    /**
     *
     * @param _
     * @param _data
     * @return
     */
    public static String[] sentances(IOut _, String _data) {
        if (_data == null) {
            return new String[0];
        }
        _data = _data.replaceAll("  ", " ");
        _data = _data.replaceAll("  ", " ");
        _data = _data.trim();

        CArray sentances = new CArray(String.class);
        char[] chars = _data.toCharArray();
        int len = chars.length;
        int s = 0;
        for (int e = 0; e < len;) {
            char c = chars[e];
            e++;
            if (c == '!'
                    || c == ':'
                    || c == ';'
                    || c == ','
                    || c == '.'
                    || c == '?'
                    || c == '\n'
                    || c == '\r') {
                if (e - 2 > -1 && Character.isDigit(chars[e - 2]) && e < len) {
                    continue;
                }
                if (e < len && Character.isDigit(chars[e])) {
                    continue;
                }

                if (e - s > 1) {
                    if (e < len && chars[e] == '\'') {
                        e++;
                    }
                    if (e < len && chars[e] == '"') {
                        e++;
                    }

                    while (e < len && chars[e] == '.') {
                        e++;// handle ....
                    }

                    char[] charSentance = UArray.copy(chars, s, e - s);
                    String sentance = new String(charSentance);
                    sentance = sentance.replace(" and ", "\nand\n");
                    sentances.insertLast(UString.toStringArray(sentance, "\n"));
                }
                s = e;
            }
        }
        return (String[]) sentances.getAll();
    }
    /**
     *
     * @param _tokens
     * @param _seperator
     * @param _step
     * @param _orderd
     * @param _results
     */
    public static void pairs(Object[] _tokens, String _seperator, int _step, boolean _orderd, CSet _results) {
        for (int i = 0; i < _tokens.length - _step; i++) {
            String a = _tokens[i].toString().toLowerCase();
            String b = _tokens[i + _step].toString().toLowerCase();
            String[] array = new String[]{a, b};
            if (!_orderd) {
                Arrays.sort(array);
            }
            _results.add(a + _seperator + b);
        }
    }
}
