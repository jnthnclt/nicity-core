/*
 * UFile.java.java
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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Administrator
 */
public class UFile {

    // Splits a PATH (with ; as separators) and fixes slashes for platform
    /**
     *
     * @param _classPath
     * @return
     */
    public static String[] toPaths(String _classPath) {
        if (_classPath == null) {
            return new String[0];
        }
        String[] paths = UString.toStringArray(_classPath, ";");
        for (int i = 0; i < paths.length; i++) {
            paths[i] = fixSlashes(paths[i]);
        }
        return paths;
    }

    /**
     *
     * @param _path
     * @return
     */
    public static String fixSlashes(String _path) {
        if (_path == null) {
            return "";
        }
        final StringBuffer result = new StringBuffer(_path);
        for (int i = 0; i < result.length(); i++) {
            fixSlash(result, i);
        }
        return result.toString();
    }

    private static boolean fixSlash(StringBuffer buffer, int pos) {
        if (buffer.charAt(pos) == '/' || buffer.charAt(pos) == '\\') {
            buffer.setCharAt(pos, File.separatorChar);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public static File[] fsRoots() {
        return File.listRoots();
    }

    /**
     *
     * @param _
     * @param _name
     * @return
     */
    public static File find(IOut _, String _name) {
        _name = _name.toLowerCase();
        File found = null;
        File[] roots = fsRoots();
        for (int r = 0; r < roots.length; r++) {
            if (_.canceled()) {
                break;
            }
            _.out(roots[r].toString());
            found = _find(_, roots[r], _name.toLowerCase());
            if (found != null) {
                break;
            }
        }
        return found;
    }

    private static File _find(IOut _, File _directory, String _name) {
        File[] array = _directory.listFiles();
        _.out(_directory.toString());
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (_.canceled()) {
                    break;
                }
                _.out(i, array.length);
                File _file = array[i];
                if (_file.isDirectory()) {
                    _find(_, _file, _name);
                } else if (_file.getName().toLowerCase().equals(_name)) {
                    return _file;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param _folder
     * @param _name
     * @return
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public static File nextAvailableFile(File _folder, String _name) throws IOException {
        File file = new File(_folder, _name);
        for (int i = 1; file.exists(); file = new File(_folder, i + _name), i++);
        file.createNewFile();
        return file;
    }

    /**
     *
     * @param _folder
     * @param _name
     * @return
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public static File nextAvailableFolder(File _folder, String _name) throws IOException {
        File folder = new File(_folder, _name);
        for (int i = 1; folder.isDirectory(); folder = new File(_folder, i + _name), i++);
        folder.mkdir();
        return folder;

    }

    /**
     *
     * @param _from
     * @param _to
     * @return
     * @throws Exception
     */
    public static boolean copyTo(File _from, File _to) throws Exception {
        boolean fromIsDir = _from.isDirectory();
        boolean toIsDir = _to.isDirectory();

        if (fromIsDir != toIsDir) {
            throw new Exception(_from + " isn't the same type as " + _to);
        }
        if (_from.isDirectory()) {
            File[] array = _from.listFiles();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    File copyTo = new File(_to, array[i].getName());
                    if (array[i].isDirectory()) {
                        copyTo.mkdir();
                    }
                    copyTo(array[i], copyTo);//!!recursion

                }
            }
        } else {
            if (_to.exists()) {
                return true;// replace or skip
            }
            File parent = _to.getParentFile();
            if (parent != null) {
                parent.mkdirs();
                //_to.createNewFile();
            }
            OutputStream to;
            try (InputStream from = new FileInputStream(_from)) {
                to = new FileOutputStream(_to);
                BufferedInputStream f = new BufferedInputStream(from, 16384);
                BufferedOutputStream t = new BufferedOutputStream(to, 16384);
                int i = -1;
                while ((i = f.read()) != -1) {
                    t.write(i);
                }
                t.flush();
            }
            to.close();
        }
        return true;
    }

    /**
     *
     * @param _from
     * @param _to
     * @return
     * @throws Exception
     */
    public static boolean replaceTo(File _from, File _to) throws Exception {
        boolean fromIsDir = _from.isDirectory();
        boolean toIsDir = _to.isDirectory();

        if (fromIsDir != toIsDir) {
            throw new Exception(_from + " isn't the same type as " + _to);
        }
        if (_from.isDirectory()) {
            File[] array = _from.listFiles();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    File copyTo = new File(_to, array[i].getName());
                    if (array[i].isDirectory()) {
                        copyTo.mkdir();
                    }
                    copyTo(array[i], copyTo);//!!recursion

                }
            }
        } else {
            File parent = _to.getParentFile();
            if (parent != null) {
                parent.mkdirs();
                //_to.createNewFile();
            }
            OutputStream to;
            try (InputStream from = new FileInputStream(_from)) {
                to = new FileOutputStream(_to);
                BufferedInputStream f = new BufferedInputStream(from, 16384);
                BufferedOutputStream t = new BufferedOutputStream(to, 16384);
                int i = -1;
                while ((i = f.read()) != -1) {
                    t.write(i);
                }
                t.flush();
            }
            to.close();
        }
        return true;
    }

    /**
     *
     * @param _
     * @param _from
     * @param _to
     * @return
     */
    public static int equal(IOut _, File _from, File _to) {
        boolean fromIsDir = _from.isDirectory();
        boolean toIsDir = _to.isDirectory();
        if (fromIsDir || toIsDir) {
            return _from.equals(_to) ? 1 : 0;
        }
        if (!_from.exists()) {
            return 0;
        }
        if (!_to.exists()) {
            return 0;
        }
        _.out(" Testing " + _from.getName() + " against " + _to.getName());
        try {
            InputStream to;
            int run;
            int fr;
            int tr;
            try (InputStream from = new FileInputStream(_from)) {
                to = new FileInputStream(_to);
                BufferedInputStream f = new BufferedInputStream(from, 16384);
                BufferedInputStream t = new BufferedInputStream(to, 16384);
                long l = _from.length();
                run = 0;
                fr = -1;
                tr = -1;
                while (((fr = f.read()) != -1) && ((tr = t.read()) != -1)) {
                    if (fr != tr) {
                        return run;
                    }
                    run++;
                    if (run % 1024 == 0) {
                        _.out(run, (int) l);
                    }
                }
            }
            to.close();
            return (fr == tr) ? run : 0;
        } catch (Exception x) {
            _.out(x);
            x.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param _file
     * @return
     */
    public static Exception emptyDirectory(File _file) {
        try {
            if (!_file.isDirectory()) {
                return null;
            }
            File[] array = _file.listFiles();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    remove(array[i]);
                }
            }
            return null;
        } catch (Exception x) {
            return x;
        }
    }

    /**
     *
     * @param _file
     * @return
     */
    public static Exception ensureDirectory(File _file) {
        if (_file == null) {
            return null;
        }
        try {
            if (_file.exists()) {
                return null;
            }
            File parent = _file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            return null;
        } catch (Exception x) {
            return x;
        }
    }

    /**
     *
     * @param _file
     * @return
     */
    public static boolean directoryExistsFor(File _file) {
        if (_file == null) {
            return false;
        }
        try {
            File parent = _file.getParentFile();
            if (parent.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception x) {
            return false;
        }
    }

    /**
     *
     * @param _
     * @param _oldDir
     * @param _newDir
     * @return
     */
    public static Exception extract(IOut _, File _oldDir, File _newDir) {
        // flattens, renames, moves; why? was needed to parse slow truth files.
        // get all files from all _oldDir directories, rename "1001" ... "nnnn"
        // and relocate under _newDir, which should exist before extract  
        try {
            File[] dirs = UFile.allFiles(_, _oldDir);
            int id = 1000;
            for (int i = 0; i < dirs.length; i++) {
                id++;
                File newFile = new File(_newDir, "" + id);
                UFile.moveTo(dirs[i], newFile);
            }
            return null;
        } catch (Exception x) {
            return x;
        }
    }

    /**
     *
     * @param _from
     * @param _to
     * @return
     */
    public static Exception moveTo(File _from, File _to) {
        try {
            _from.renameTo(_to);
            return null;
        } catch (Exception x) {
            return x;
        }
    }

    /**
     *
     * @param _remove
     * @return
     */
    public static Exception remove(File _remove) {
        try {
            if (_remove.isDirectory()) {
                File[] array = _remove.listFiles();
                if (array != null) {
                    for (int i = 0; i < array.length; i++) {
                        remove(array[i]);
                    }
                }
            }
            _remove.delete();
            return null;
        } catch (Exception x) {
            return x;
        }
    }

    

    /**
     *
     * @param _
     * @param _file
     * @return
     */
    public static File[] allFiles(IOut _, File _file) {
        if (_file == null) {
            return null;
        }
        if (_file.isDirectory()) {
            CArray all = new CArray(File.class);
            _allFiles(_, _file, all);
            return (File[]) all.getAll();
        } else {
            return new File[]{_file};
        }
    }

    private static void _allFiles(IOut _, File _directory, CArray _all) {
        File[] array = _directory.listFiles();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (_.canceled()) {
                    break;
                }
                _.out(i, array.length);
                File _file = array[i];
                _.out(_file);
                if (_file.isDirectory()) {
                    //_.pushProgress();
                    _allFiles(_, _file, _all);
                    //_.popProgress();
                } else {
                    _all.insertLast(_file);
                }
            }
        }
    }

    /**
     *
     * @param _files
     * @return
     */
    public static String[] toStrings(File[] _files) {
        CArray strings = new CArray(String.class);
        for (int i = 0; i < _files.length; i++) {
            if (_files[i] == null) {
                continue;
            }
            strings.insertLast(_files[i].getAbsolutePath());
        }
        return (String[]) strings.getAll();
    }

    /**
     *
     * @return
     */
    public static File home() {
        return new File(System.getProperty("user.dir"));
    }

    /**
     *
     * @param _file
     * @return
     */
    public static String getPathRelativeToHome(File _file) {
        return getRelativePath(new File(System.getProperty("user.dir")), _file);
    }

    /**
     *
     * @param _root
     * @param _file
     * @return
     */
    public static String getRelativePath(File _root, File _file) {
        String home = _root.getAbsolutePath();
        String path = _file.getAbsolutePath();
        if (!path.startsWith(home)) {
            return null;
        }
        String relative = path.substring(home.length() + 1);//BUG Fix add +1 on 7-16-08

        return relative;
    }

    /**
     *
     * @param _name
     * @return
     */
    public static String getName(String _name) {
        int p = -1;
        if ((p = _name.lastIndexOf('.')) < 0) {
            return _name;
        }
        return (_name.substring(0, p)).toLowerCase();
    }

    /**
     *
     * @param _name
     * @return
     */
    public static String getExtension(String _name) {
        int p = -1;
        if ((p = _name.lastIndexOf('.')) < 0) {
            return "";
        }
        return (_name.substring(p + 1)).toLowerCase();
    }

    /**
     *
     * @param _name
     * @param _ext
     * @return
     */
    public static String replaceExtension(String _name, String _ext) {
        int p = -1;
        if ((p = _name.lastIndexOf('.')) < 0) {
            return _name + "." + _ext;
        }
        return _name.substring(0, p + 1) + _ext;
    }

    /**
     *
     * @param _name
     * @return
     */
    public static String removeExtension(String _name) {
        int p = -1;
        if ((p = _name.lastIndexOf('.')) < 0) {
            return _name;
        }
        return _name.substring(0, p);
    }

    /**
     *
     * @param _name
     * @return
     */
    public static String removePath(String _name) {
        int p = -1;
        if ((p = _name.lastIndexOf(File.separatorChar)) < 0) {
            return _name;
        }
        return (_name.substring(p + 1));
    }

    /**
     *
     * @param _name
     * @return
     */
    public static String getPath(String _name) {
        return getPath(_name, File.separatorChar);
    }

    /**
     *
     * @param _name
     * @param _char
     * @return
     */
    public static String getPath(String _name, char _char) {
        int p = -1;
        if ((p = _name.lastIndexOf(_char)) < 0) {
            return "";
        }
        return (_name.substring(0, p + 1));
    }
    
    /**
     *
     * @param _
     * @param _file
     * @param _callback
     */
    public static void allFiles(IOut _, File _file, ICallback<File, File> _callback) {
        _allFilesAndFolders(_, _file, true, false, _callback);
    }

    public static void allFolders(IOut status, File _directory, ICallback<File, File> _callback) {
        _allFilesAndFolders(status, _directory, false, true, _callback);
    }

    public static void allFilesAndFolders(IOut _, File _directory, ICallback<File, File> _callback) {
        _allFilesAndFolders(_, _directory, true, true, _callback);
    }

    private static void _allFilesAndFolders(IOut _, File f, boolean _files, boolean _folders, final ICallback<File, File> _callback) {
        if (f == null) {
            return;
        }
        if (!f.isDirectory()) {
            if (_files) {
                _callback.callback(f);
            }
        } else {
            File[] array = f.listFiles();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    if (_.canceled()) {
                        break;
                    }
                    _.out(i, array.length);
                    File _file = array[i];
                    _.out(_file);
                    if (_file.isDirectory()) {
                        File callback = _callback.callback(_file);
                        if (callback == _file && _folders) {
                            _allFilesAndFolders(_, _file, _files, _folders, _callback);
                        }
                    } else {
                        if (_files) {
                            _callback.callback(_file);
                        }
                    }
                }
            }
        }
    }
}
