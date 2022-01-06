// -------------------------------------------------------
// Assignment 3
// Written by: Jonathan Abitbol 40190550
// -------------------------------------------------------

//Welcome to my Driver.
//This program is written on 11/14/2021 by Jonathan Abitbol 

/**
 * Jonathan Abitbol 40190550
 * COMP 249
 * Assignment #3
 * 11/14/2021
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import Exceptions.CSVDataMissing;
import Exceptions.CSVFileInvalidException;
/**
 * This driver reads all CSV files and converts them to LATEX files
 * @author Jonathan Abitbol
 * 
 */
public class Driver {
	/**
	 * LATEX constant that will be used later for creating the LATEX FILES
	 */
	final static String begin = "\\begin{table}[]";
	/**
	 * LATEX constant that will be used later for creating the LATEX FILES
	 */
	final static String beginTabular = "\\begin{tabular}{";
	/**
	 * LATEX constant that will be used later for creating the LATEX FILES
	 */
	final static String endTabular = "\\end{tabular}";
	/**
	 * LATEX constant that will be used later for creating the LATEX FILES
	 */
	final static String end = "\\end{table}";
	
	/**
	 * This driver reads all CSV files and converts them to LATEX files
	 * @author Jonathan Abitbol
	 * @param args parameter args
	 * 
	 */
	public static void main(String[] args) {
		File[] files = { new File("src/Files/EmployerInterview-Missing Data-CSV.csv"),
				new File("src/Files/EmployerInterviewCVS.csv"), new File("src/Files/salaryStudy-MissingAttr-CSV.csv"),
				new File("src/Files/salaryStudy.csv") };

		Scanner[] scanners = new Scanner[4];
		String userInput;
		Scanner keyIn = new Scanner(System.in);

		// Making sure we are able to read the files
		for (int i = 0; i < files.length; i++) {
			try {
				scanners[i] = new Scanner(new FileInputStream(files[i]));
			} catch (FileNotFoundException f) {
				System.out.println("Could not open input file " + files[i].getName() + " for reading");
				System.out.println("Please check if file exists! Program will terminate after closing all files.");
				for (int j = 0; j < scanners.length; j++) {
					scanners[j].close();

				}
				System.exit(0);
			}

		}
		File[] latexFiles = new File[4];
		String fileName;

		String pathName = "src/Files/";
		int errorCount = 0;

		// Making sure we can open all the files
		PrintWriter p[] = new PrintWriter[4];
		for (int i = 0; i < files.length; i++) {
			try {
				fileName = files[i].getName();
				latexFiles[i] = new File(pathName + fileName.substring(0, fileName.indexOf(".")) + ".tex");
				p[i] = new PrintWriter(new FileOutputStream(latexFiles[i]));

			} catch (FileNotFoundException f) {
				System.out.println("File " + latexFiles[i].getName()
						+ " cannot be opened/created...Deleting existing file and closing opened files now.");
				if (latexFiles[i].delete()) {
					System.out.println("File" + latexFiles[i].getName() + " was succesfully deleted.");
				} else {
					System.out.println("File" + latexFiles[i].getName()
							+ " was not able to be deleted.\nTerminating program IMMEDIATELY");
					System.exit(0);
				}

				errorCount++;
				if (errorCount == latexFiles.length) {
					System.out.println(
							"No files were able to be created/opened \n Closing all streams and terminating program...");
					for (int j = 0; j < scanners.length; j++) {
						scanners[j].close();
					}
					System.exit(0);
				}

			}
		}
		int count = 0;

		// We are calling the method and then making sure the user can read the LATEX
		// files created by using BufferedReader
		processFilesForValidation(files, latexFiles, scanners, p);
		BufferedReader bufferedReader = null;
		while (count < 2) {
			System.out.println("\n\nWhich file would you like to read? ");
			userInput = keyIn.nextLine() + ".tex";

			try {
				bufferedReader = new BufferedReader(new FileReader(pathName + userInput));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}
				count = 2;

			} catch (FileNotFoundException f) {
				System.out.println("File has not been found");
				count++;
				if (count >= 2) {
					System.out.println("Terminating Program Now!");
					System.exit(0);
				}
			} catch (IOException i) {
				System.out.println("Terminating Program due to unknown errors!");
				System.exit(0);
			}

		}
		keyIn.close();
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// This method is to check for validation
	/**
	 * This method is to verify validation for the files.
	 * @author Jonathan Abitbol
	 * 
	 * @param files  This files is an array of type File which will hold all the files
	 * @param latexFiles This is an array of type File which will hold all the converted latex files 
	 * @param scanners This is an array of type Scanner which will hold all the files that will be read 
	 * @param pw This is an array of type PrintWriter which will open all the created files 
	 */
	
	public static void processFilesForValidation(File[] files, File[] latexFiles, Scanner[] scanners,
			PrintWriter[] pw) {
		String firstLineTitle;
		String[] secondLineAttributes;
		StringTokenizer attributes;
		String attributeLine = "";
		StringTokenizer data;
		String[] indData;
		String dataLine = "";
		PrintWriter log = null;
		try {
			log = new PrintWriter(new FileOutputStream("src/Files/InformationOfMissing.log"));

		} catch (FileNotFoundException fnf) {
			System.out.println(fnf.getMessage());
		}
		int lineNumber = 1;
		for (int i = 0; i < files.length; i++) {
			try {
				firstLineTitle = scanners[i].nextLine();
				attributeLine = scanners[i].nextLine();
				attributeLine = attributeLine.replaceAll(",,", ",***,");
				attributes = new StringTokenizer(attributeLine, ",");
				lineNumber++;
				secondLineAttributes = new String[attributes.countTokens()];
				int columnNum = 0;
				for (int j = 0; j < secondLineAttributes.length; j++) {
					secondLineAttributes[j] = attributes.nextToken();
				}
				for (int j = 0; j < secondLineAttributes.length; j++) {
					if (secondLineAttributes[j].equals("***")) {
						throw new CSVFileInvalidException(files[i]);
					}

				}

				// Create latex
				pw[i].println(begin);
				pw[i].print(beginTabular);
				for (int j = 0; j < secondLineAttributes.length; j++) {
					pw[i].print("|1");
				}
				pw[i].println("|}\n\\toprule");
				pw[i].println(attributeLine.replaceAll(",", " & ") + " \\\\ \\midrule");
				while (scanners[i].hasNextLine()) {
					try {
						dataLine = scanners[i].nextLine();
						lineNumber++;
						dataLine = dataLine.replaceAll(",,", ",***,");
						data = new StringTokenizer(dataLine, ",");

						indData = new String[data.countTokens()];
						for (int j = 0; j < indData.length; j++) {
							indData[j] = data.nextToken();
						}

						for (int j = 0; j < indData.length; j++) {
							if (indData[j].equals("***")) {
								columnNum = j;
								throw new CSVDataMissing();
							}
						}

						// Create row
						if (scanners[i].hasNextLine()) {
							pw[i].println(dataLine.replaceAll(",", " & ") + " \\\\ \\midrule");
						} else {
							pw[i].println(dataLine.replaceAll(",", " & ") + " \\\\ \\bottomrule");
						}
						// If the file has a missing data then it will catch this
					} catch (CSVDataMissing c) {
						System.out.println(
								"In file " + files[i].getName() + " line " + lineNumber + " not converted to latex \n");
						log.println("File " + files[i].getName() + " line " + lineNumber + ".");
						log.println(dataLine);
						log.println("Missing:" + secondLineAttributes[columnNum] + "\n");
					}

				}
				// End latex
				pw[i].println(endTabular);
				pw[i].println("\\caption{" + firstLineTitle.substring(0, firstLineTitle.indexOf(",")) + "}");
				pw[i].println("\\label{" + files[i].getName().substring(0, files[i].getName().indexOf(".")) + "}");
				pw[i].print(end);

				// If the file has missing attributes it will catch this
			} catch (CSVFileInvalidException e) {
				System.out.println(attributeLine + "\nFile is not converted to LATEX");
				pw[i].close();
				if (latexFiles[i].delete()) {
					System.out.println("File " + latexFiles[i].getName() + " has succesfully been deleted");
				}

				log.println(e.getMessage(files[i]));
				log.println(attributeLine);
				log.println("File is not converted to LATEX");
			} finally {
				pw[i].close();
				scanners[i].close();
				attributeLine = "";
			}
		}
		log.close();

	}

}
