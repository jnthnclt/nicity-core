/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.core.lang;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author jonathan
 */
public class UClass {

    public static void main(String[] args) {
        System.out.println(new File(".").getAbsolutePath());
        for (Class c : getClasseNamesInPackage(Comparable.class, null, "./target/nicity-core-1.0-SNAPSHOT.jar")) {
            System.out.println(c);
        }
    }

    public static List<Class> getClasseNamesInPackage(Class isAssignableFrom, String packageName, String jarName) {

        if (packageName == null) {
            packageName = "";
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        packageName = packageName.replaceAll("\\.", "/");
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;
            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
                    try {
                        String className = jarEntry.getName().replaceAll("/", "\\.");
                        className = UFile.removeExtension(className);// removes traling .class
                        if (className.lastIndexOf("$") != -1) {
                            continue;
                        }
                        //System.out.println(className);
                        Class clazz = Class.forName(className,true,Thread.currentThread().getContextClassLoader());
                        if (isAssignableFrom != null && !isAssignableFrom.isAssignableFrom(clazz)) {
                            continue;
                        }
                        classes.add(clazz);
                    } catch (NoClassDefFoundError x) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
