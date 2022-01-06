// -------------------------------------------------------
// Assignment 3
// Written by: Jonathan Abitbol 40190550 
// For COMP 249 Section D-DB Fall 2021
// Due Date = Sunday November 14, 2021
// -------------------------------------------------------

//Welcome to my Driver.
//This program is written on 11/14/2021 by Jonathan Abitbol 

/**
 * Jonathan Abitbol 40190550
 * COMP 249
 * Assignment #3
 * 11/14/2021
 */

package Exceptions;

/**
 * This exception is thrown when a data or an attribute is missing in the csv table.
 * @author Jonathan Abitbol
 * 
 */


public class InvException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvException() {
        System.out.println("Error: input row cannot be parsed due to missing information");
    }
}
