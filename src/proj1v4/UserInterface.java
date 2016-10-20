//Mayday Mayday Test7
package src.proj1v4;
/**
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * This class implements the user interface for the Library project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 *
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Library library;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int ADD_BOOKS = 2;
	private static final int ISSUE_BOOKS = 3;
	private static final int RETURN_BOOKS = 4;
	private static final int RENEW_BOOKS = 5;
	private static final int REMOVE_BOOKS = 6;
	private static final int PLACE_HOLD = 7;
	private static final int REMOVE_HOLD = 8;
	private static final int PROCESS_HOLD = 9;
	private static final int GET_TRANSACTIONS = 10;
	private static final int SAVE = 11;
	private static final int RETRIEVE = 12;
	private static final int HELP = 13;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			library = Library.instance();
		}
	}

	/**
	 * Supports the singleton pattern
	 *
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 *
	 * @param prompt
	 *            - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 *
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 *
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 *
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt
	 *            the string for prompting
	 * @return the integer corresponding to the string
	 *
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt
	 *            the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 *
	 * @return a valid command
	 *
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 *
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 12 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(ADD_BOOKS + " to  add books");
		System.out.println(ISSUE_BOOKS + " to  issue books to a  member");
		System.out.println(RETURN_BOOKS + " to  return books ");
		System.out.println(RENEW_BOOKS + " to  renew books ");
		System.out.println(REMOVE_BOOKS + " to  remove books");
		System.out.println(PLACE_HOLD + " to  place a hold on a book");
		System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
		System.out.println(PROCESS_HOLD + " to  process holds");
		System.out.println(GET_TRANSACTIONS + " to  print transactions");
		System.out.println(SAVE + " to  save data");
		System.out.println(RETRIEVE + " to  retrieve");
		System.out.println(HELP + " for help");
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * member.
	 *
	 */
	public void addMember() {
		String name = getToken("Enter member name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		Member result;
		result = library.addMember(name, address, phone);
		if (result == null) {
			System.out.println("Could not add member");
		}
		System.out.println(result);
	}

	/**
	 * Method to be called for adding a book. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * book.
	 *
	 */
	public void addBooks() {
		Book result;
		do {
			String title = getToken("Enter  title");
			String bookID = getToken("Enter id");
			String author = getToken("Enter author");
			result = library.addBook(title, author, bookID);
			if (result != null) {
				System.out.println(result);
			} else {
				System.out.println("Book could not be added");
			}
			if (!yesOrNo("Add more books?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for issuing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for issuing
	 * books.
	 *
	 */
	public void issueBooks() {
		Book result;
		// do {
		String memberID = sequenceMemberList();
		if (memberID != "exit") {
			if (memberID != null) {

				do {
					String bookID = sequenceNotCheckedOutList();
					if (bookID == "exit") {
						break;
					}
					result = library.issueBook(memberID, bookID);
					if (result != null) {
						System.out.println(result.getTitle() + "   " + result.getDueDate());
					} else {
						System.out.println("Book could not be issued");
					}
					if (!yesOrNo("Issue more books?")) {
						break;
					}

				} while (true);
			} else {
				issueBooks();
			}
		}
	}

	/**
	 * Method to be called for renewing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for renewing
	 * books.
	 *
	 */
	public void renewBooks() {

		Book result;
		String memberID = sequenceMemberList();
		if (memberID != "exit") {
			if (memberID != null) {
				do {
					String bookID = sequenceMemberCheckedOutList(memberID);
					// System.out.println(bookID + memberID);
					result = library.renewBook(bookID, memberID);

					if (result != null) {
						System.out.println(result.getTitle() + " " + result.getDueDate());
					} else {
						System.out.println("Book is not renewable");
					}
					if (!yesOrNo("Renew more books?")) {
						break;
					}

				} while (true);
			} else {
				renewBooks();
			}
		}
	}

	/**
	 * Method to be called for returning books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for returning
	 * books.
	 *
	 */
	public void returnBooks() {
		int result;
		do {

			String bookID = sequenceCheckedOutList();

			result = library.returnBook(bookID);
			switch (result) {
			case Library.BOOK_NOT_FOUND:
				System.out.println("No such Book in Library");
				break;
			case Library.BOOK_NOT_ISSUED:
				System.out.println(" Book  was not checked out");
				break;
			case Library.BOOK_HAS_HOLD:
				System.out.println("Book has a hold");
				break;
			case Library.OPERATION_FAILED:
				System.out.println("Book could not be returned");
				break;
			case Library.OPERATION_COMPLETED:
				System.out.println(" Book has been returned");
				break;
			default:
				System.out.println("An error has occurred");
			}
			if (!yesOrNo("Return more books?")) {
				break;
			}

		} while (true);
	}

	/**
	 * Method to be called for removing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing
	 * books.
	 *
	 */
	public void removeBooks() {
		int result;
		do {
			// String bookID = getToken("Enter book id");
			String bookID = sequenceRemovableList();
			result = library.removeBook(bookID);
			switch (result) {
			case Library.BOOK_NOT_FOUND:
				System.out.println("No such Book in Library");
				break;
			case Library.BOOK_ISSUED:
				System.out.println(" Book is currently checked out");
				break;
			case Library.BOOK_HAS_HOLD:
				System.out.println("Book has a hold");
				break;
			case Library.OPERATION_FAILED:
				System.out.println("Book could not be removed");
				break;
			case Library.OPERATION_COMPLETED:
				System.out.println(" Book has been removed");
				break;
			default:
				System.out.println("An error has occurred");
			}
			if (!yesOrNo("Remove more books?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for placing a hold. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for placing a
	 * hold.
	 *
	 */
	public void placeHold() {

		// New code here:
		String memberID = sequenceMemberList();
		if (memberID != "exit") {
			if (memberID != null) {
				String bookID = sequenceCheckedOutList();
				if (bookID != null) {
					int duration = getNumber("Enter duration of hold");
					int result = library.placeHold(memberID, bookID, duration);
					switch (result) {
					case Library.BOOK_NOT_FOUND:
						System.out.println("No such Book in Library");
						break;
					case Library.BOOK_NOT_ISSUED:
						System.out.println(" Book is not checked out");
						break;
					case Library.NO_SUCH_MEMBER:
						System.out.println("Not a valid member ID");
						break;
					case Library.HOLD_PLACED:
						System.out.println("A hold has been placed");
						break;
					default:
						System.out.println("An error has occurred");
					}
				}

				else {
					System.out.println("Invalid book");
				}
			} else {
				placeHold();
			}
		}

	}

	/**
	 * Method to be called for removing a holds. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing a
	 * hold.
	 *
	 */
	public void removeHold() {

		String memberID = sequenceMemberList();
		// System.out.println(memberID);
		if (memberID != "exit") {
			if (memberID != null) {
				String bookID = sequenceMemberHoldList(memberID);
				if (bookID != null) {
					int result = library.removeHold(memberID, bookID);
					switch (result) {
					case Library.BOOK_NOT_FOUND:
						System.out.println("No such Book in Library");
						break;
					case Library.NO_SUCH_MEMBER:
						System.out.println("Not a valid member ID");
						break;
					case Library.OPERATION_COMPLETED:
						System.out.println("The hold has been removed");
						break;
					default:
						System.out.println("An error has occurred");
					}
				} else {
					System.out.println("No books on hold for this member");
				}
			} else {
				removeHold();
			}
		}

	}

	/**
	 * Method to be called for processing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for processing
	 * books.
	 *
	 */
	public void processHolds() {
		Member result;
		do {
			// String bookID = getToken("Enter book id");
			String bookID = sequenceHasHoldList();

			result = library.processHold(bookID);
			if (result != null) {
				System.out.println(result);
			} else {
				System.out.println("No valid holds left");
			}
			if (!yesOrNo("Process more books?")) {
				break;
			}

		} while (true);

	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for displaying
	 * transactions.
	 *
	 */
	public void getTransactions() {
		Iterator result;
		String memberID = sequenceMemberList();
		if (memberID != "exit") {
			if (memberID != null) {
				sequenceMemberTransList(memberID);

				System.out.println("\n  There are no more transactions \n");
				getTransactions();
			} else {
				getTransactions();
			}
		}

	}

	/**
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
	 *
	 */
	private void save() {
		if (library.save()) {
			System.out.println(" The library has been successfully saved in the file LibraryData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Library method for retrieval.
	 *
	 */
	private void retrieve() {
		try {
			Library tempLibrary = Library.retrieve();
			if (tempLibrary != null) {
				System.out.println(" The library has been successfully retrieved from the file LibraryData \n");
				library = tempLibrary;
			} else {
				System.out.println("File doesnt exist; creating new library");
				library = Library.instance();
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalties.
	 *
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_MEMBER:
				addMember();
				break;
			case ADD_BOOKS:
				addBooks();
				break;
			case ISSUE_BOOKS:
				issueBooks();
				break;
			case RETURN_BOOKS:
				returnBooks();
				break;
			case REMOVE_BOOKS:
				removeBooks();
				break;
			case RENEW_BOOKS:
				renewBooks();
				break;
			case PLACE_HOLD:
				placeHold();
				break;
			case REMOVE_HOLD:
				removeHold();
				break;
			case PROCESS_HOLD:
				processHolds();
				break;
			case GET_TRANSACTIONS:
				getTransactions();
				break;
			case SAVE:
				save();
				break;
			case RETRIEVE:
				retrieve();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * Method to print a list of all members, generates sequence numbers Accepts
	 * the sequence number entered by the user Returns a string with the
	 * memberID
	 * 
	 * @return
	 */
	public String sequenceMemberList() {
		int i = 1;

		Iterator members = Library.instance().serveIterator();
		for (; members.hasNext();) {

			System.out.println("   " + i++ + ".   " + members.next().toString());

		}
		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);

		if (checkedNumber != -1) {
			String memberID = library.getMemberId(checkedNumber);
			return memberID;

		} else {
			return "exit";
		}
	}

	/**
	 * Method to print a list of all books in the catalog that are checked out,
	 * generates sequence numbers Accepts the sequence number entered by the
	 * user Returns a string with the bookID
	 * 
	 * @return
	 */

	public String sequenceCheckedOutList() {
		int i = 1;

		Iterator books = Catalog.instance().checkedOutList();
		for (; books.hasNext();) {
			Book localBook = ((Book) books.next());

			System.out.println("   " + i++ + ".   " + localBook.toString());
		}
		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);

		if (checkedNumber != -1) {
			String bookID = Catalog.getBookId(checkedNumber, Catalog.instance().checkedOutList());

			return bookID;
		} else {
			return "exit";
		}
	}

	/**
	 * Method to print a list of all books in the catalog that are not checked
	 * out, generates sequence numbers Accepts the sequence number entered by
	 * the user Returns a string with the bookID
	 * 
	 * @return
	 */

	public String sequenceNotCheckedOutList() {
		int i = 1;

		Iterator books = Catalog.instance().notCheckedOutList();
		for (; books.hasNext();) {
			Book localBook = ((Book) books.next());

			System.out.println("   " + i++ + ".   " + localBook.toString());
		}

		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);

		if (checkedNumber != -1) {
			String bookID = Catalog.getBookId(checkedNumber, Catalog.instance().notCheckedOutList());

			return bookID;
		} else {
			return "exit";
		}

	}

	/**
	 * Method to print a list of all books checked out by a particular member,
	 * generates sequence numbers Accepts the sequence number entered by the
	 * user Returns a string with the bookID
	 * 
	 * @return
	 */

	public String sequenceMemberCheckedOutList(String memberID) {

		// Iterator books = Library.instance().getBooks(memberID);
		Iterator books = Library.instance().getMemberIssued(memberID);
		int i = 1;
		for (; books.hasNext();) {
			Book localBook = ((Book) books.next());

			System.out.println("   " + i++ + ".   " + localBook.toString());
		}

		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);
		Iterator books1 = Library.instance().getMemberIssued(memberID);
		String bookID = Catalog.getBookId(checkedNumber, books1);
		// String bookID = Catalog.getBookId(sequenceNumberCheck(sequenceNumber,
		// i), Catalog.instance().checkedOutList());

		return bookID;

	}

	/**
	 * Method to print a list of all books on hold by a particular member,
	 * generates sequence numbers Accepts the sequence number entered by the
	 * user Returns a string with the bookID
	 * 
	 * @return
	 */

	public String sequenceMemberHoldList(String memberID) {
		int i = 1;

		Iterator holds = Library.instance().getMemberHolds(memberID);
		if (holds.hasNext()) {
			for (; holds.hasNext();) {
				Book localHold = ((Book) holds.next());

				System.out.println("   " + i++ + ".   " + localHold.toString());
			}
			String sequenceNumber = getToken("Enter Sequence Number: ");
			int checkedNumber = sequenceNumberCheck(sequenceNumber, i);
			System.out.println(checkedNumber);
			Iterator holds1 = Library.instance().getMemberHolds(memberID);
			String bookID = Catalog.getBookId(checkedNumber, holds1);

			return bookID;
		} else {
			return null;
		}

	}

	/**
	 * Method to print a list of all books in the catalog that are not checked
	 * out or on hold, generates sequence numbers Accepts the sequence number
	 * entered by the user Returns a string with the bookID
	 * 
	 * @return
	 */
	public String sequenceRemovableList() {
		int i = 1;

		Iterator books = Catalog.instance().removableList();
		for (; books.hasNext();) {
			Book localBook = ((Book) books.next());

			System.out.println("   " + i++ + ".   " + localBook.toString());
		}

		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);
		String bookID = Catalog.getBookId(checkedNumber, Catalog.instance().removableList());

		return bookID;

	}

	/**
	 * Method to print a list of all books on hold in the catalog, generates
	 * sequence numbers Accepts the sequence number entered by the user Returns
	 * a string with the bookID
	 * 
	 * @return
	 */
	public String sequenceHasHoldList() {
		int i = 1;

		Iterator holdBooks = Catalog.instance().hasHoldList();
		if (holdBooks.hasNext()) {
			for (; holdBooks.hasNext();) {
				Book localBook = ((Book) holdBooks.next());

				System.out.println("   " + i++ + ".   " + localBook.toString());
			}
			String sequenceNumber = getToken("Enter Sequence Number: ");
			int checkedNumber = sequenceNumberCheck(sequenceNumber, i);
			String bookID = Catalog.getBookId(checkedNumber, Catalog.instance().hasHoldList());

			return bookID;
		} else {
			return null;
		}
	}

	/**
	 * Method to print a list of all books in the catalog, generates sequence
	 * numbers Accepts the sequence number entered by the user Returns a string
	 * with the bookID
	 * 
	 * @return
	 */
	public String sequenceAllBooksList() {
		int i = 1;

		Iterator books = Catalog.instance().serveIterator();
		for (; books.hasNext();) {

			System.out.println("   " + i++ + ".   " + books.next().toString());
		}

		String sequenceNumber = getToken("Enter Sequence Number: ");
		int checkedNumber = sequenceNumberCheck(sequenceNumber, i);

		String bookID = Catalog.getBookId(checkedNumber);

		return bookID;

	}

	public void sequenceMemberTransList(String memberID) {
		int i = 1;

		Iterator books = Library.instance().getTransactions(memberID);
		for (; books.hasNext();) {

			System.out.println("   " + i++ + ".   " + books.next().toString());
		}
		// String sequenceNumber = getToken("Enter Sequence Number: ");
		// int checkedNumber = sequenceNumberCheck(sequenceNumber, i);
		// String bookID = Catalog.getBookId(checkedNumber);

		// return bookID;
	}

	/**
	 * Method to verify if a sequence number is valid Catches an exception if a
	 * non-integer is entered.
	 * 
	 * @param sequenceNumber
	 * @return
	 */
	public int sequenceNumberCheck(String sequenceNumber, int listSize) {
		// System.out.println("List Size = " + listSize);
		int number = 0;
		try {
			number = Integer.parseInt(sequenceNumber);
		} catch (NumberFormatException e) {
			System.out.println("That was not a number, try again \n ");
			return 0;
		}

		if (number != -1) {

			if ((listSize - 1) >= number) {

				return number;
			} else {

				System.out.println("Sequence number out of range " + number + "\n");
				return 0;
			}
		} else {
			// System.out.println("Sequence " + number);
			return -1;
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}