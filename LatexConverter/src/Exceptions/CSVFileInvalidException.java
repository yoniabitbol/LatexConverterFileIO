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
import java.io.File;

/**
 * This exception is thrown when attribute is missing in the csv table.
 * @author Jonathan Abitbol
 * 
 */

public class CSVFileInvalidException extends InvException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CSVFileInvalidException(File f) {
        System.out.println(getMessage(f));
        

    }
    public String getMessage(File f) {
        return "File " + f.getName() + " is invalid: attribute is missing.";
    }
}
