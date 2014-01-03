/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.core.lang;

/**
 *
 * @author jonathan
 */
public class UThread {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            
        }
    }
}
