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
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Book objects
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;
	private List books = new LinkedList();
	private static Catalog catalog;

	/*
	 * Private constructor for singleton pattern
	 * 
	 */
	private Catalog() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static Catalog instance() {
		if (catalog == null) {
			return (catalog = new Catalog());
		} else {
			return catalog;
		}
	}

	/**
	 * Checks whether a book with a given book id exists.
	 * 
	 * @param bookId
	 *            the id of the book
	 * @return true iff the book exists
	 * 
	 */
	public Book search(String bookId) {
		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getId().equals(bookId)) {
				return book;
			}
		}
		return null;
	}

	/**
	 * Removes a book from the catalog
	 * 
	 * @param bookId
	 *            book id
	 * @return true iff book could be removed
	 */
	public boolean removeBook(String bookId) {
		Book book = search(bookId);
		if (book == null) {
			return false;
		} else {
			return books.remove(book);
		}
	}

	/**
	 * Inserts a book into the collection
	 * 
	 * @param book
	 *            the book to be inserted
	 * @return true iff the book could be inserted. Currently always true
	 */
	public boolean insertBook(Book book) {
		books.add(book);
		return true;
	}

	/**
	 * Returns an iterator to all books
	 * 
	 * @return iterator to the collection
	 */
	public Iterator getBooks() {
		return books.iterator();
	}

	/*
	 * Supports serialization
	 * 
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(catalog);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/*
	 * Supports serialization
	 * 
	 * @param input the stream to be read from
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (catalog != null) {
				return;
			} else {
				input.defaultReadObject();
				if (catalog == null) {
					catalog = (Catalog) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			System.out.println("in Catalog readObject \n" + ioe);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * String form of the collection
	 * 
	 */
	@Override
	public String toString() {
		return books.toString();
	}

	/**
	 * Returns an iterator on the member list
	 * 
	 */
	public Iterator serveIterator() {

		Iterator books = Catalog.instance().getIterator();
		return books;
	}

	/**
	 * Builds a list of books that are checked out. Returns a list iterator
	 * 
	 */
	public Iterator checkedOutList() {
		List checkedOut = new LinkedList();

		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getBorrower() != null) {
				checkedOut.add(book);
			}

		}
		// TODO Auto-generated method stub
		return checkedOut.iterator();
	}

	/**
	 * Builds a list of books that are not checked out. Returns a list iterator
	 * 
	 */
	public Iterator notCheckedOutList() {
		List notCheckedOut = new LinkedList();

		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getBorrower() == null) {
				notCheckedOut.add(book);
			}

		}
		// TODO Auto-generated method stub
		return notCheckedOut.iterator();
	}

	/**
	 * Builds a list of books that are both not checked out and not on hold.
	 * Returns a list iterator
	 * 
	 */
	public Iterator removableList() {
		List removable = new LinkedList();

		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getBorrower() == null && (!(book.hasHold()))) {
				removable.add(book);
			}

		}
		return removable.iterator();
	}

	/**
	 * Builds a list of books that are on hold. Returns a list iterator
	 * 
	 */
	public Iterator hasHoldList() {
		List hasHold = new LinkedList();

		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getBorrower() != null && book.hasHold()) {
				hasHold.add(book);
			}

		}

		return hasHold.iterator();
	}

	public Iterator hasBookList() {
		List hasBook = new LinkedList();

		for (Iterator iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getBorrower() != null) {
				hasBook.add(book);
			}

		}

		return hasBook.iterator();
	}

	/**
	 * Returns a list iterator from the list of books
	 * 
	 */
	private Iterator getIterator() {

		Iterator<Book> iterator = books.iterator();
		return iterator;
	}

	/**
	 * Method to get the book ID from a book object selected in a sequence list
	 * 
	 * @param sequenceNumber
	 * @return
	 */
	public static String getBookId(int sequenceNumber) {
		int i = 0;
		Iterator<Book> books = Catalog.instance().getIterator();
		Book b = null;
		// System.out.println("Sequence #: " + sequenceNumber);
		while (books.hasNext()) {
			i++;
			b = books.next();

			if (i == sequenceNumber)

				return b.getId();
		}
		return null;

	}

	/**
	 * Method to get the book ID from a book object selected in a sequence list,
	 * also takes an iterator as a parameter
	 * 
	 * 
	 * @param sequenceNumber
	 * @return
	 */
	public static String getBookId(int sequenceNumber, Iterator iterator) {
		int i = 0;
		// Iterator<Book> books = Catalog.instance().getIterator();
		Book b = null;
		// System.out.println("Sequence #: " + sequenceNumber);
		while (iterator.hasNext()) {
			i++;
			b = (Book) iterator.next();
			// System.out.println("i #: " + i +" m.getID: " + m.getId());

			if (i == sequenceNumber)

				return b.getId();
		}
		return null;

	}

}
