/*
 * XCoordinatesListener.java
 *
 * Created on 27 June 2007 г., 19:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author Mytinski Leonid
 */
public interface XCoordinatesListener {
    /**
     * 
     * @param  source  viewer in which x coordinates was changed
     */
    public void xCoordinatesChanged(SplitViewer source);
}
