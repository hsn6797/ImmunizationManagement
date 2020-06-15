package com.example.immunizationmanagement.Utills;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

enum TruncateAt {
	  END, START
}

 public class SString {

	 /**
	   * System.getProperty("line.separator"); This way you can make sure you have the right newline character regardless of
	   * the system the user is using.
	   */
	public static final String newline = System.getProperty("line.separator");

	  
	  /**
	   * Checks if the string is null, its length is 0 or a whitespace.
	   *
	   * @param str the string to be examined, can be null
	   * @return true if {@code str} is null, its length is 0 or a whitespace, false otherwise
	   * @since 1.0.0
	   */
	  public static boolean isBlank(String str) {
	    return (isEmpty(str) || str.matches("^\\s*$"));
	  }

	  /**
	   * Checks if the string is null or its length is 0.
	   *
	   * @param str the string to be examined, can be null
	   * @return true if {@code str} is null or its length is 0, false otherwise
	   * @since 1.0.0
	   */
	  public static boolean isEmpty(String str) {
	    return (str == null || str.isEmpty());
	  }
	  
	  /**
	   * A null check implementation of the String.length() method
	   *
	   * @param str the string to be examined, can be null
	   * @return -1 if {@code str} is null otherwise the length of the string that was passed in as an
	   * argument
	   * @since 1.2.0
	   */
	  public static int length(String str) {
	    return str != null ? str.length() : -1;
	  }
	  
	  
//	  public static String wordWrap(String text, int lineLength) {
//			text = text.trim();
//			if (text.length() < lineLength)
//				return text;
//			if (text.substring(0, lineLength).contains("\n"))
//				return text.substring(0, text.indexOf("\n")).trim() + "\n\n" + wordWrap(text.substring(text.indexOf("\n") + 1), lineLength);
//			int place = Math.max(Math.max(text.lastIndexOf(" ", lineLength), text.lastIndexOf("\t", lineLength)), text.lastIndexOf("-", lineLength));
//			return text.substring(0, place).trim() + "\n" + wordWrap(text.substring(place), lineLength);
//		}
	  
	  public static List<String> mapToWords(String string) {
			List<String> result = new ArrayList<String>();

			int start = 0;
			int i = 0;
			for (; i < string.length(); i++) {
				char ch = string.charAt(i);
				if (!Character.isLetterOrDigit(ch)
						&& !(ch == '\'' && i > 0 && Character.isLetter(string.charAt(i - 1)) && string.length() - 1 > i && string.charAt(i + 1) == 's' && (string
								.length() - 2 == i || !Character.isLetterOrDigit(string.charAt(i + 2))))) { // leaves ' in possesive pattern
					if (start != i) {
						result.add(string.substring(start, i));
					}
					start = i + 1;
				}
			}
			if (start != i) {
				result.add(string.substring(start, i));
			}
			return result;
		}

	  
	  /**
	   * Performs a wordwrap-like function on the given string limiting the line length to the given lineLimit. The
	   * lineLimit starts over based on the paragraph. It is defined by the System.getProperty("line.separator"). Words are
	   * defined by spaces.
	   *
	   * @param string
	   * @param lineLimit
	   * @return
	   */
	  public static String wordWrap(String string, int lineLimit) {
	    StringBuilder sb = new StringBuilder();
	    String[] paragraphs = string.split("[" + newline + "]");
	    for (int i = 0; i < paragraphs.length; i++) {
	      String paragraph = paragraphs[i];
	      if (!paragraph.isEmpty()) {
	        String[] words = paragraph.split(" ");
	        int lineLength = 0;
	        for (String word : words) {
	          if (word.length() + lineLength > lineLimit) {
	            lineLength = 0;
	            sb.append(newline);

	          }
//	          SString.show(lineLength+"");

	          lineLength += word.length();
	          sb.append(word).append(" ");
	        }
	        sb.append(newline);
	      }
	    }
	    return sb.toString();
	  }
	  
	  /**
	   * Simple method. Read it like this: Replace "items" with "replace" in "string"
	   *
	   * @param string the string to replace items in
	   * @param replace the text to replace the items with
	   * @param items the items to replace
	   * @return the replaced string
	   */
	  public static String replaceInString(String string, String replace, String... items) {
	    for (String item : items) {
	      string = string.replace(item, replace);
	    }
	    return string;
	  }
	  
	  /**
	   * Will insert the given inserts between each character of the given string. Here's an example:
	   * insertIntoString("1234", "a", "", "c", "+", "-", "32342") would return "a12c3+4". insertIntoString("1234", "",
	   * "hello", "", "world") would return "1hello23world4". Or a more useful example: insertIntoString("1231234567", "(",
	   * "", "", ") ", "", "", "-") would return "(123) 123-4567"
	   *
	   * @param string
	   * @param inserts
	   * @return
	   */
	  public static String insertIntoString(String string, String... inserts) {
	    char[] numbers = string.toCharArray();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < numbers.length; i++) {
	      if (i < inserts.length) {
	        sb.append(inserts[i]);
	      }
	      sb.append(numbers[i]);
	    }
	    return sb.toString();
	  }
	  
	  /**
	   * Simple method, returns the same string you give repeated the number of times you give.
	   *
	   * @param string
	   * @param times
	   * @return
	   */
	  public static String repeatString(String string, int times) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < times; i++) {
	      sb.append(string);
	    }
	    return sb.toString();
	  }
	  
	  /**
	     * Remove the last specified character
	     *
	     * @param stringText the string text
	     * @param endingChar the ending char
	     * @return the string
	     */
	    public static String removeLastChar(String stringText, String endingChar) {
	        if (!stringText.equals("") && stringText != null) {
	            if (stringText.endsWith(endingChar)) {
	                stringText = stringText.substring(0, stringText.length() - 1);
	            }
	        }
	        return stringText;
	    }
	    
	    /**
	     * Convert string to title case
	     *
	     * @param input the input
	     * @return the string
	     */
	    public static String toTitleCase(String input) {
	        StringBuilder titleCase = new StringBuilder();
	        boolean nextTitleCase = true;

	        for (char c : input.toCharArray()) {
	            if (Character.isSpaceChar(c)) {
	                nextTitleCase = true;
	            } else if (nextTitleCase) {
	                c = Character.toTitleCase(c);
	                nextTitleCase = false;
	            }

	            titleCase.append(c);
	        }

	        return titleCase.toString();
	    }
	    
	    /**
	     * Partially capitalizes the string from paramter start and offset.
	     *
	     * @param string String to be formatted
	     * @param start  Starting position of the substring to be capitalized
	     * @param offset Offset of characters to be capitalized
	     * @return
	     */
	    public static String capitalizeString(String string, int start, int offset) {
	        if (isBlank(string)) {
	            return null;
	        }
	        String formattedString = string.substring(start, offset).toUpperCase() + string.substring(offset, string.length());
	        return formattedString;
	    }
	    /**
	     * Capitalizes each word in the string.
	     * @param string
	     * @return
	     */
	    public static String capitalizeEveryWord(String string) {

	        if (string == null) {
	            return null;
	        }

	        char[] chars = string.toLowerCase().toCharArray();
	        boolean found = false;
	        for (int i = 0; i < chars.length; i++) {
	            if (!found && Character.isLetter(chars[i])) {
	                chars[i] = Character.toUpperCase(chars[i]);
	                found = true;
	            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You
	                // can add other
	                // chars here
	                found = false;
	            }
	        } // end for

	        return String.valueOf(chars);
	    }
	    
	    /**
	     * Count given character in the string.
	     * @param string
	     * @param c
	     * @return count
	     */
	    public static int count(String string, char c)
	    {
	        if (isBlank(string))
	        {
	            return 0;
	        }

	        int count = 0;
	        int len = string.length();
	        for (int i = 0; i < len; i++)
	        {
	            if (string.charAt(i) == c)
	            {
	                count++;
	            }
	        }

	        return count;
	    }

	    /**
	     * @param minLen minimum number of characters
	     * @param maxLen maximum number of characters
	     * @return String of alphabetical characters, with the first character uppercase (Proper case strings).
	     */
	    public static String getRandomString(int minLen, int maxLen)
	    {
	    	Random random = new Random();
	        StringBuilder s = new StringBuilder();
	        int length = minLen + random.nextInt(maxLen - minLen + 1);
	        for (int i=0; i < length; i++)
	        {
	            s.append(getRandomChar(random, i == 0));
	        }
	        return s.toString();
	    }

	    public static String getRandomChar(Random random, boolean upper)
	    {
	        int r = random.nextInt(26);
	        return upper ? "" + (char)((int)'A' + r) : "" + (char)((int)'a' + r);
	    }
	    
	    /**
	     * Convert a byte[] into a String with a particular encoding.
	     * Preferable used when the encoding is one of the guaranteed Java types
	     * and you don't want to have to catch the UnsupportedEncodingException
	     * required by Java
	     *
	     * @param bytes    bytes to encode into a string
	     * @param encoding encoding to use
	     */
	    public static String createString(byte[] bytes, String encoding)
	    {
	        try
	        {
	            return bytes == null ? null : new String(bytes, encoding);
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            throw new IllegalArgumentException(String.format("Encoding (%s) is not supported by your JVM", encoding), e);
	        }
	    }
	    
	    /**
	     * Contacts either a case sensitive or case insensitive search on the string passed in based on
	     * {@code ignoreCase}
	     *
	     * @param str the string to be examined
	     * @param searchStr the search string
	     * @param ignoreCase a flag to set the search algorithm to ignore the case or not
	     * @return true if the {@code searchStr} is found in {@code str}, false otherwise
	     * @since 1.2.0
	     */
	    public static boolean contains(String str, String searchStr, boolean ignoreCase) {
	      if (str == null || searchStr == null) {
	        return false;
	      }
	      return ignoreCase ? str.toLowerCase().contains(searchStr.toLowerCase())
	          : str.contains(searchStr);
	    }
	    
	    /**
	     * Reverse the string passed in
	     *
	     * @param str the string to be reversed
	     * @return a reversed version of {@code str}
	     * @since 1.1.0
	     */
	    public static String reverse(String str) {
	      return isBlank(str) ? str : new StringBuilder().append(str).reverse().toString();
	    }
	    
	    /**
	     * Returns a truncated string based on the parameters passed in
	     *
	     * @param str the string to be examined, can be null
	     * @param maxWidth the expected maximum width of the string
	     * @param trimPosition where should the string be truncated at, either beginning or end
	     * @return the {@code str} if the length of the string is smaller than {@code maxWidth}, otherwise
	     * a truncated version of {@code str}
	     * @since 1.0.0
	     */
	    public static String truncate(String str, int maxWidth, String trimPosition) {
	      return truncate(str, maxWidth, trimPosition, false);
	    }

	    /**
	     * Returns a truncated string based on the parameters passed in
	     *
	     * @param str the string to be examined, can be null
	     * @param maxWidth the expected maximum width of the string
	     * @param trimPosition where should the string be truncated at, either beginning or end
	     * @param includeEllipsis whether the ellipsis characters should be included or not
	     * @return the {@code str} if the length of the string is smaller than {@code maxWidth}, otherwise
	     * a truncated version of {@code str}
	     * @since 1.0.0
	     */
	    public static String truncate(String str, int maxWidth, String trimPosition,
	        boolean includeEllipsis) {
	      return truncate(str, maxWidth, trimPosition, includeEllipsis, "...");
	    }
	    
	    /**
	     * Returns a truncated string based on the parameters passed in
	     *
	     * @param str the string to be examined, can be null
	     * @param maxWidth the expected maximum width of the string
	     * @param trimPosition where should the string be truncated at, either beginning or end
	     * @param includeEllipsis whether the ellipsis characters should be included or not
	     * @param ellipseCharacter the character to be used to replace the truncated string
	     * @return the {@code str} if the length of the string is smaller than {@code maxWidth}, otherwise
	     * a truncated version of {@code str}
	     * @since 1.2.0
	     */
	    
	    
	    public static String truncate(String str, int maxWidth, String trimPosition,
	        boolean includeEllipsis, String ellipseCharacter) {
	      if (isBlank(str) || length(str) < maxWidth) {
	        return str;
	      }
	      if (trimPosition.equals(TruncateAt.START.toString())) {
	        return includeEllipsis ? str.substring(0, maxWidth) + ellipseCharacter
	            : str.substring(0, maxWidth);
	      } else if (trimPosition.equals(TruncateAt.END.toString())) {
	        return includeEllipsis ? ellipseCharacter + str.substring(maxWidth) : str.substring(maxWidth);
	      }
	      return str;
	    }
	   
	    
	 // Chomping
	    //--------------------------------------------------------------------------
	    
	    /**
	     * <p>Remove the last value of a supplied String, and everything after
	     * it from a String.</p>
	     *
	     * @param str String to chomp from
	     * @param sep String to chomp
	     * @return String without chomped ending
	     * @throws NullPointerException if str or sep is <code>null</code>
	     */
    	public static String postchomp(  String str,  String sep )
	    {
	        int idx = str.lastIndexOf( sep );
	        if ( idx != -1 )
	        {
	            return str.substring( 0, idx );
	        }
	        else
	        {
	            return str;
	        }
	    }
    	
    	/**
         * <p>Remove everything and return the last value of a supplied String, and
         * everything after it from a String.</p>
         *
         * @param str String to chomp from
         * @param sep String to chomp
         * @return String chomped
         * @throws NullPointerException if str or sep is <code>null</code>
         */
         public static String getPostchomp(  String str,  String sep )
        {
            int idx = str.lastIndexOf( sep );
            if ( idx == str.length() - sep.length() )
            {
                return sep;
            }
            else if ( idx != -1 )
            {
                return str.substring( idx );
            }
            else
            {
                return "";
            }
        }
         
         /**
          * <p>Remove the first value of a supplied String, and everything before it
          * from a String.</p>
          *
          * @param str String to chomp from
          * @param sep String to chomp
          * @return String without chomped beginning
          * @throws NullPointerException if str or sep is <code>null</code>
          */
          public static String prechomp(  String str,  String sep )
         {
             int idx = str.indexOf( sep );
             if ( idx != -1 )
             {
                 return str.substring( idx + sep.length() );
             }
             else
             {
                 return str;
             }
         }

         /**
          * <p>Remove and return everything before the first value of a
          * supplied String from another String.</p>
          *
          * @param str String to chomp from
          * @param sep String to chomp
          * @return String prechomped
          * @throws NullPointerException if str or sep is <code>null</code>
          */
          public static String getPrechomp(  String str,  String sep )
         {
             int idx = str.indexOf( sep );
             if ( idx != -1 )
             {
                 return str.substring( 0, idx + sep.length() );
             }
             else
             {
                 return "";
             }
         }

         // Chopping
         //--------------------------------------------------------------------------          
          
          public static String normalizeSpacing(String str){
        	// uneven spaces between words
        	   
        	  StringTokenizer st = new StringTokenizer(str, " ");
        	  StringBuffer sb = new StringBuffer();
        	   
        	  while(st.hasMoreElements())
        	  {
        	      sb.append(st.nextElement()).append(" ");
        	  }
        	   
        	  String nameWithProperSpacing = sb.toString();
        	           	   
        	  //trim leading and trailing white spaces
        	  nameWithProperSpacing = nameWithProperSpacing.trim();
        	  return nameWithProperSpacing;
          }
          
          
          /**
           * Parses the given String and replaces all occurrences of
           * '\n', '\r' and '\r\n' '\n\r' with the system line separator.
           *
           * @param s  a not null or empty String
           * @param ls the wanted line separator ("\n" on UNIX), if null or empty remove both \r and \n from s.
           * @return a String that not contains \n \r or \r\n or \n\r .
           * 
           */
          public static String removeLineSeparators(  String s,  String ls )
          {
              if ( isEmpty(s) )
              {
                  return null;
              }
              
              if ( ls.equals("\n") )
              {
            	  return normalizeSpacing(s.replaceAll("\n", " "));
              }
              else if ( ls.equals("\r"))
              {
            	  return normalizeSpacing(s.replaceAll("\r", " "));
              }
              else{
            	  
            	  return normalizeSpacing(s.replaceAll("\r", " ").replaceAll("\n", " "));   	
              }
              
              
          }     
          
          
          /**
           * <p>How many times is the substring in the larger String.</p>
           * <p/>
           * <p><code>null</code> returns <code>0</code>.</p>
           *
           * @param str the String to check
           * @param sub the substring to count
           * @return the number of occurances, 0 if the String is <code>null</code>
           * @throws NullPointerException if sub is <code>null</code>
           */
          public static int countMatches( String str, String sub )
          {
        	  if(isEmpty(str)){
        		  return 0;
        	  }
              
              if ( isEmpty(sub) )
              {
                  return 0;
              }
              int count = 0;
              int idx = 0;
              while ( ( idx = str.indexOf( sub, idx ) ) != -1 )
              {
                  count++;
                  idx += sub.length();
              }
              return count;
          }

         
          /**
           * @param source The source.
           * @param escapedChars set of characters to escape.
           * @param escapeChar prefix for escaping a character.
           * @return the String escaped
           */
          public static String escape( String source, final char[] escapedChars, char escapeChar )
          {
              if ( source == null )
              {
                  return null;
              }

              char[] eqc = new char[escapedChars.length];
              System.arraycopy( escapedChars, 0, eqc, 0, escapedChars.length );
              Arrays.sort( eqc );

              StringBuilder buffer = new StringBuilder( source.length() );

              for ( int i = 0; i < source.length(); i++ )
              {
                  final char c = source.charAt( i );
                  int result = Arrays.binarySearch( eqc, c );

                  if ( result > -1 )
                  {
                      buffer.append( escapeChar );
                  }
                  buffer.append( c );
              }

              return buffer.toString();
          }
          
          
         
	    // Count Upper and Lower case Characters   
	    public static int countsCharactersInUpperCase(String string) {
			int uppercase = 0;
			int charInt = 0;
			for (int i = 0; i < string.length(); i++) {
				charInt = (int) string.charAt(i);
				if (charInt > 64 && charInt < 91) {
					uppercase++;
				}
			}
			return uppercase;
		}

		public static int countsCharactersInLowerCase(String string) {
			int lowercase = 0;
			int charInt = 0;
			for (int i = 0; i < string.length(); i++) {
				charInt = (int) string.charAt(i);
				if (charInt > 96 && charInt < 123) {
					lowercase++;
				}
			}
			return lowercase;
		}
	    
	    // Check string methods
	    
	    public static boolean isInteger(String string) {
			try {
				Integer.parseInt(string);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}

		public static boolean isLong(String string) {
			try {
				Long.parseLong(string);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}

		public static boolean isNumber(String string) {
			try {
				Double.parseDouble(string);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		public static boolean isPalindrome(String input) {
		    String s = input.toLowerCase().replaceAll("[\\W_]", "");
		    return s.equals(reverse(s));
		            
		    
		}
		
		public static boolean containsNumber(String string) {
			for (int i = 0; i < string.length(); i++) {
				if ((int) string.charAt(i) < 58 && (int) string.charAt(i) > 47) {
					return true;
				}
			}
			return false;
		}

		public static int countNumbers(String string) {
			int total = 0;
			for (int i = 0; i < string.length(); i++) {
				if ((int) string.charAt(i) < 58 && (int) string.charAt(i) > 47) {
					total++;
				}
			}
			return total;
		}

		public static boolean containsLetter(String string) {
			for (int i = 0; i < string.length(); i++) {
				if (Character.isLetter(string.charAt(i))) {
					return true;
				}
			}
			return false;
		}

		public static int countLetters(String string) {
			int total = 0;
			for (int i = 0; i < string.length(); i++) {
				if (Character.isLetter(string.charAt(i))) {
					total++;
				}
			}
			return total;
		}
		
		public static String[] splitLines(String input) {
		    return input.split("\\r?\\n");
		}
		public static String sortCharactersInString(String input) {
			char[] arr = input.trim().toCharArray();
			Arrays.sort(arr);
		    return String.valueOf(arr);
		}
	    
	    
	    // TODO -- Not Tested yet
	  /**
	   * Convenience method. Allows you to give the splitBy method a list rather than an array or comma separated
	   * parameters.
	   *
	   * @param separator
	   * @param objects
	   * @return
	   */
	  public static <O extends Object> String splitBy(String separator, List<O> objects) {
	    return splitBy(separator, objects.toArray(new Object[objects.size()]));
	  }
	  
	  /**
	   * Separates the given strings with the given separator (", " for example). Does not add the separator to the end of
	   * the list
	   *
	   * @param separator
	   * @param objects
	   * @return
	   */
	  public static <O extends Object> String splitBy(String separator, O... objects) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < objects.length; i++) {
	      if (i > 0) {
	        sb.append(separator);
	      }
	      sb.append(objects[i]);
	    }
	    return sb.toString();
	  }

}

