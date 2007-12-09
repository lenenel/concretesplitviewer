/*
 * ConcreteSplitViewer program for analazing splits.
 * Copyright (C) 2006-2007 Mytinski Leonid (Leonid.Mytinski@gmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */ 

/*
 * AthleteListener.java
 * 
 * Created on 05.09.2007, 20:16:35
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author Mytinski Leonid
 * 
 * Interface for listeners of changings in athlete
 */
public interface AthleteListener {
    public void splitsChanged();
}
