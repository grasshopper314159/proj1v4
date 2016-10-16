package proj1v4;

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
import java.util.*;
import java.io.*;
/**
 * Represents a single hold on a book by a member
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Hold implements Serializable {
  private Book book;
  private Member member;
  private Calendar date;
  /**
   * The member and book are stored. The date is computed by adding the
   * duration days to the current date.
   * @param member who places the hold
   * @param book the book on which hold is placed
   * @param duration for how long the hold is valid
   */
  public Hold(Member member, Book book, int duration) {
    this.book = book;
    this.member = member;
    date = new GregorianCalendar();
    date.setTimeInMillis(System.currentTimeMillis());
    date.add(Calendar.DATE, duration);
  }
  /**
   * Getter for Member
   * @return Member who has the hold
   */
  public Member getMember() {
    return member;
  }
  /**
   * Getter for Book
   * @return Book being held
   */
  public Book getBook() {
    return book;
  }
  /**
   * Getter for date
   * @return date until which the hold is valid
   */
  public Calendar getDate() {
    return date;
  }
  /**
   * Checks whether the hold has become invalid because the last date has passed
   * @return true iff the hold is valid
   */
  public boolean isValid(){
    return (System.currentTimeMillis() < date.getTimeInMillis());
  }
}