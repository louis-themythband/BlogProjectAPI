/**
 * 
 */
package com.sos.blog.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * This class is used to generate GUID (Globally Unique Id).
 * 
 * You must build an expression for the parser to use to build your GUID.  The 
 * expression has several parts.  There are value types, evaluated values, and 
 * static text.
 * 
 * Value Types are characters that represent types of data.  The following is a
 * list of character value types.
 * 
 *  VALUE TYPES:
 * 	1) # = A base 10 number (example: 0 - 9)
 *  2) $ = A base 16 number (example: 0 - F)
 *  3) @ = An upper case alphabet character (example: A - Z)
 *  4) & = A lower case alphabet character (example: a - z)
 *  5) ? = A mixed case alphabet character (example: a - z + A - Z)
 *  6) % = An alpha-numeric value (example: 0 - 9 + a - z + A - Z)
 *  7) ! = A symbol value (example: @ $ % * >)
 *  8) * = Any value (example: 0 - 9 + a - z + A - Z + symbols)
 *  9) any other character, number, or symbol will become static text. 
 *  
 *  If you need to use one of these symbols in a GUID as static text, then wrap
 *  the symbol inside of open and close braces.  (example: {@}@@####)
 *  
 *  {@}@@####
 *  ---------
 *  @SD7651
 *  @SD7652
 *  @SD7653
 *  
 *  An expression like this FBI-####$-@@&%-?$$## will generate a GUID like these
 *  results, ran four times:
 *  
 *  FBI-####$-@@&%-?$$##
 *  --------------------
 *  FBI-00805-SSd8-y2E25
 *  FBI-00805-SSd8-y2E26
 *  FBI-00805-SSd8-y2E27
 *  FBI-00805-SSd8-y2E28
 *  
 *  When VALUE TYPES will be incremented, when the next value id generated.  
 *  When the expression reaches 
 *  an expression value that is not a value type, then increment continue to the 
 *  next VALUE TYPE place holder.

 *  EVALUATED VALUES:
 *  The { and } braces are used to denote an evaluated value.  There are seventeen 
 *  characters used for evaluation.
 *  
 *  1)  Y = The current four digit year. (example: 2014)
 *  2)  y = The current two digit year. (example: 14)
 *  3)  M = The current two digit month. (example: 02 = February)
 *  4)  C = The short three character month . (example: 02 = Feb)
 *  5)  d = The current two digit day of the month. (example: 30)
 *  6)  H = The current two digit hour. (example: 01)
 *  7)  m = The current two digit minute. (example: 55)
 *  8)  s = The current two digit second. (example: 30)
 *  9)  S = The current three digit millisecond. (example: 754)
 *  10) X = A 32 alpha-numeric value that is a hash of a random generated value.
 *          (This is always an alpha-numeric 32 places in length all upper case)
 *  11) x = A 64 alpha-numeric value that is a hash of a random generated value.
 *          (This is always an alpha-numeric 64 places in length all upper case)
 *  12) R[#] = A randomly generated alpha-numeric mixed case with # of places.  
 *             (where # is any whole number)
 *  13) r[#] = A randomly generated alpha-numeric & symbols mixed case with # of 
 *             places.  (where # is any whole number)
 *  14) N[#] = A randomly generated numeric value with # places.
 *  15) n[#] = A randomly generated hex value with # places.
 *  16) A[#] = A randomly generated upper case alpha value with # places
 *  17) a[#] = A randomly generated lower case alpha value with # places
 *  18) Z[#] = A randomly generated mixed case alpha value with # places
 *             
 *  Any other character, symbol, or number will become static text.
 *  
 *  The following expression will generate values like the following:
 *  
 *  {R[32]}
 *  --------------------------------
 *  07QDqCdb5MwXBRaNu3USxyJLHFE0852t
 *  yJRk0F04i6BWO07ANwy3R1m7LwjP2fWD
 *  oEjGVYH3gF46BLlJpdAHygX5LFUOw676
 *  
 *  {X}
 *  --------------------------------
 *  6125EFA7FA503C9C76B52A11904AB63D
 *  68DA962DFDE4A2437C57BCD3631A34C8
 *  1D38F6824ABE830BE43216E618287668
 *  
 *  {M-d-Y_H:m:s:S}
 *  -----------------------
 *  05-22-2014_09:34:08:995
 *  05-22-2014_09:34:08:996
 *  05-22-2014_09:34:08:997
 *  
 *  {YMd_HmsS}
 *  ------------------
 *  20140522_014237184
 *  20140522_014237185
 *  20140522_014237186
 *  
 *  when evaluated expressions are used, they are evaluated newly every time.
 *  
 *  The following expression evaluates these results, ran seven times:
 *  
 *  {M-d-Y_H:m:s:S}-{R[32]} {X}-@&?%$#
 *  ------------------------------------------------------------
 *  05-22-2014_09:48:44:654-xSrAEFL34B3m8c1fn68IV562O5SBT47r BF0BD8419EEBA272ED37507DCF486743-ElwmA0
 *  05-22-2014_09:48:44:669-7CS2s7Bgd1QFN74MmMQnT7VLVP6A04r3 9076636DB4DAF5AF08AE4CB379AAC8EC-ElwmA1
 *  05-22-2014_09:48:44:670-3vgEZfEhD02DbE0UBaUj30DLfIfn32k7 E0DF9F2BEEA4FB00F12D81A3DAD4FA72-ElwmA2
 *  05-22-2014_09:48:44:670-ai1VHX5iC6vD4H5CB40tAFHnZfuH57Fk 81309DD9D10A79593EE72A47A47D0FD6-ElwmA3
 *  05-22-2014_09:48:44:671-3sFFsDmMU8DR57q2xKuKwvN8swAE76gs 73009A5B5882635BDDE65450EDA2399C-ElwmA4
 *  05-22-2014_09:48:44:671-qssnY3OXJ05JD62721E0P4jBfArE2sgH 49FA79370B440CC5E6EE87499081E6C8-ElwmA5
 *  05-22-2014_09:48:44:672-ctV5Hd06WCZbe4DrVTgtD1gAdmQ99Caf F4A2DBF585771E2DE64ADD5DDE6639D7-ElwmA6
 *  
 *  When using the {X} EVALUATED VALUE, you can set an expression value used to
 *  generate the hash value.  The has expression can only use VALUE TYPES in the 
 *  expression.  If a hash expression is not set, a default expression is used 
 *  instead.
 *  
 *  EXAMPLE CODE
 *  -------------
 *  GUIDExpressionParser guide = new GUIDExpressionParser();
 *  guide.setExpression("A{Y}-{X}-$$$$$");
 *  guide.setHashExpression("@###$#####");
 *  String value = guide.generateNext();
 *  
 *  This generates the random GUID A2014-8088ADCB667439C848039B521FA747D7-D19A0
 *  
 *  or 
 *  
 *  GUIDExpressionParser guide = new GUIDExpressionParser("A{Y}-{X}-$$$$$");
 *  String value = guide.generateNext();
 *  
 *  This generates a random GUID.  Same as previous.
 *  
 *  or
 *  
 *  String lastGeneratedValue = "A2014-9B998702769D8405FEF740E0304158F1-91371"
 *  GUIDExpressionParser guide = new GUIDExpressionParser("A{Y}-{X}-$$$$$");
 *  String value = guide.generateNext(lastGeneratedValue);
 *  
 *  This will generate an incremented GUID 
 *  A2014-9B998702769D8405FEF740E0304158F1-91372
 *  Based on the lastGeneratedValue.
 *  
 *  or
 *  
 *  String lastGeneratedValue = "A2014-9B998702769D8405FEF740E0304158F1-91371"
 *  String expression = "A{Y}-{X}-$$$$$$$$";
 *  GUIDExpressionParser guide = new GUIDExpressionParser(expression, lastGeneratedValue);
 *  String value = guide.generateNext();
 *  
 *  This will generate an incremented GUID from, based on the lastGeneratedValue.
 *  Same as previous.
 *  
 *  
 * 
 * @author lweyrich
 * @version 1.0
 */
public class GUIDExpressionParser
{
	
	public static final char [] VALUES = 
	{
		'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H',
		'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
		's','t','u','v','w','x','y','z','!','@','#','$','%','?','&','*','-','_',
		'=','+',':',';','<','>',',','.','/','^','\\','|'
	};
	
	public static final char NUMBER_BASE_10 = '#';
	public static final char NUMBER_BASE_16 = '$';
	public static final char ALPHA_UPPER = '@';
	public static final char ALPHA_LOWER = '&';
	public static final char ALPHA_MIXED_CASE = '?';
	public static final char ALPHA_NUMERIC = '%';
	public static final char SYMBOLS = '!';
	public static final char ANY = '*';
	public static final char SECRET = '~';
	public static final char SECRET_WITHOUT_SYMBOLS = '`';
	
	public static final char BRACE_START = '{';
	public static final char BRACE_END = '}';
	public static final char BRACKET_START = '[';
	public static final char BRACKET_END = ']';
//	public static final char QUOTE = '\'';
//	public static final char PAREN_START = '(';
//	public static final char PAREN_END = ')';
//	public static final char PLUS = '+';
//	public static final char MINUS = '-';
//	public static final char MULTIPLY = '*';
//	public static final char DIVIDE = '/';
//	public static final char MOD = '%';
	
	public static final char FOUR_NUMBER_YEAR = 'Y';
	public static final char TWO_NUMBER_YEAR = 'y';
	public static final char THREE_CHAR_MONTH = 'C';
	public static final char TWO_NUMER_MONTH = 'M';
	public static final char TWO_NUMER_DAY = 'd';
	public static final char TWO_NUMER_HOUR = 'H';
	public static final char TWO_NUMER_MINUTE = 'm';
	public static final char TWO_NUMER_SECOND = 's';
	public static final char THREE_NUMER_MILLISECOND = 'S';
	public static final char HEX = 'X';
	public static final char SHA256 = 'x';
	public static final char RANDOM = 'r';
	public static final char RANDOM_WITHOUT_SYMBOLS = 'R';
	public static final char RANDOM_NUMBER = 'N';
	public static final char RANDOM_HEX = 'n';
	public static final char RANDOM_UPPER_CASE_ALPHA = 'A';
	public static final char RANDOM_LOWER_CASE_ALPHA = 'a';
	public static final char RANDOM_MIXED_CASE_ALPHA = 'Z';
	
	public static final int RANDOM_COUNT = 16;
	
	
	//public static final String DEFAULT_HEX_CONFIG = "$%***********%&";
	
	
	private String expression;
	private String lastValue;
	private Map <String, Range> rangeMap;
	private String hashExpression;
	private int randomCount = RANDOM_COUNT;


	/**
	 * 
	 */
	public GUIDExpressionParser(String expression)
	{
		this();
		setExpression(expression);
	}


	public GUIDExpressionParser()
	{
		rangeMap = new HashMap <String, Range> ();
		rangeMap.put(Character.toString(NUMBER_BASE_10), new Range(NUMBER_BASE_10, 0, 9));
		rangeMap.put(Character.toString(NUMBER_BASE_16), new Range(NUMBER_BASE_16, 0, 15));
		rangeMap.put(Character.toString(ALPHA_UPPER), new Range(ALPHA_UPPER, 10, 35));
		rangeMap.put(Character.toString(ALPHA_LOWER), new Range(ALPHA_LOWER, 36, 61));
		rangeMap.put(Character.toString(ALPHA_MIXED_CASE), new Range(ALPHA_MIXED_CASE, 10, 61));
		rangeMap.put(Character.toString(ALPHA_NUMERIC), new Range(ALPHA_NUMERIC, 0, 61));
		rangeMap.put(Character.toString(SYMBOLS), new Range(SYMBOLS, 62, 83));
		rangeMap.put(Character.toString(ANY), new Range(ANY, 0, 83));
		rangeMap.put(Character.toString(SECRET), new Range(SECRET, 62, 70));
		rangeMap.put(Character.toString(SECRET_WITHOUT_SYMBOLS), new Range(SECRET_WITHOUT_SYMBOLS, 63, 69));
	}
	
	public GUIDExpressionParser(String expression, String lastValue)
	{
		this(expression);
		this.lastValue = lastValue;
	}

	public GUIDExpressionParser(String expression, String lastValue, String hashExpression)
	{
		this(expression);
		this.lastValue = lastValue;
		this.hashExpression = hashExpression;
	}

	public String generateRandomGUID()
	{
		StringBuilder generatedValue = new StringBuilder();
		
		for(int index = 0; index < expression.length(); index++)
		{
			char c = expression.charAt(index);
			
			switch(c)
			{
				case NUMBER_BASE_10 :
				{
					Range range = rangeMap.get(Character.toString(NUMBER_BASE_10));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case NUMBER_BASE_16 :
				{
					Range range = rangeMap.get(Character.toString(NUMBER_BASE_16));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case ALPHA_UPPER :
				{
					Range range = rangeMap.get(Character.toString(ALPHA_UPPER));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case ALPHA_LOWER :
				{
					Range range = rangeMap.get(Character.toString(ALPHA_LOWER));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case ALPHA_MIXED_CASE :
				{
					Range range = rangeMap.get(Character.toString(ALPHA_MIXED_CASE));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case ALPHA_NUMERIC :
				{
					Range range = rangeMap.get(Character.toString(ALPHA_NUMERIC));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case SYMBOLS :
				{
					Range range = rangeMap.get(Character.toString(SYMBOLS));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case ANY :
				{
					Range range = rangeMap.get(Character.toString(ANY));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case SECRET :
				{
					Range range = rangeMap.get(Character.toString(SECRET));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case SECRET_WITHOUT_SYMBOLS :
				{
					Range range = rangeMap.get(Character.toString(SECRET_WITHOUT_SYMBOLS));
					int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
					char charValue = VALUES[randomNumber];
					generatedValue.append(charValue);
					break;
				}
				case BRACE_START :
				{
					int endBraceLength =  expression.length() -1;
					
					for(int subIndex= index; subIndex < expression.length(); subIndex++)
					{
						char newc = expression.charAt(subIndex);
						if(newc == BRACE_END)
						{
							endBraceLength = subIndex;
							break;
						}
					}
					
					generatedValue.append(evaluate(expression.substring(index+1, endBraceLength)));
					
					index = endBraceLength;
					
					break;
				}
				default :
				{
					generatedValue.append(c);
				}
			}
		}
		
		return generatedValue.toString();
	}
	
	protected int generateRandomNumber(int start, int end)
	{
		Random random = new Random();
		int diff = (end) - start;
		int num = random.nextInt(diff);
		return num+start;
	}
	
	public String generateNextGUID(String lastValue)
	{
		boolean increment = true;
		StringBuilder builder = new StringBuilder();
		int expressionIndex = expression.length() -1;
		char charExpression = NUMBER_BASE_10;
		
		for(int index = lastValue.length() - 1; index >= 0; index--) 
		{
			char charValue = lastValue.charAt(index);
			
			if(expressionIndex >= 0) charExpression = expression.charAt(expressionIndex);
			ReturnValue returnValue = null;
			
			if(charExpression == BRACE_END)
			{
				for(int braceIndex = expressionIndex; braceIndex >= 0; braceIndex--)
				{
					char braceChar = expression.charAt(braceIndex);
					if(braceChar == BRACE_START)
					{
						String subexpression = expression.substring(braceIndex+1, expressionIndex);
						String evaluated = evaluate(subexpression);
						returnValue = new ReturnValue(evaluated, increment);
						expressionIndex = braceIndex -1;
						index -= (evaluated.length()-1);
						break;
					}
				}
				
			}
			else
			{
				returnValue = findChar(charValue, index, charExpression, increment);
				expressionIndex--;
			}
			
			
			builder.insert(0, returnValue.getCurrentValue());
			increment = returnValue.isIncrementNext();
			
		}
		
		return builder.toString();
	}

		
	public String generateNextGUID()
	{
		if(lastValue != null)
		{
			setLastValue(generateNextGUID(getLastValue()));
			return getLastValue();
		}
		else
		{
			setLastValue(generateRandomGUID());
			return getLastValue();
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	protected String evaluate(String value)
	{
		StringBuilder builder = new StringBuilder();
		
		for(int index = 0; index < value.length(); index++)
		{
			char c = value.charAt(index);
			switch(c)
			{
				case FOUR_NUMBER_YEAR :
				{
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR);
					builder.append(year);
					break;
				}
				case TWO_NUMBER_YEAR :
				{
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR);
					String yearString = String.valueOf(year);
					
					builder.append(yearString.substring(2,4));
					break;
				}
				case TWO_NUMER_MONTH :
				{
					Calendar cal = Calendar.getInstance();
					int month = cal.get(Calendar.MONTH);
					
					if(month < 10 && month >= 0)
					{
						builder.append("0");
					}
					
					builder.append(month+1);
					break;
				}
				case THREE_CHAR_MONTH :
				{
					Calendar cal = Calendar.getInstance();
					String monthName = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
					builder.append(monthName);
					break;
				}
				case TWO_NUMER_DAY :
				{
					Calendar cal = Calendar.getInstance();
					int day = cal.get(Calendar.DAY_OF_MONTH);

					if(day < 10 && day >= 0)
					{
						builder.append("0");
					}
					
					builder.append(day);
					break;
				}
				case TWO_NUMER_HOUR :
				{
					Calendar cal = Calendar.getInstance();
					int hour = cal.get(Calendar.HOUR);
					
					if(hour < 10 && hour >= 0)
					{
						builder.append("0");
					}
					
					builder.append(hour);
					break;
				}
				case TWO_NUMER_MINUTE :
				{
					Calendar cal = Calendar.getInstance();
					int minute = cal.get(Calendar.MINUTE);
					
					if(minute < 10 && minute >= 0)
					{
						builder.append("0");
					}
					
					builder.append(minute);
					break;
				}
				case TWO_NUMER_SECOND :
				{
					Calendar cal = Calendar.getInstance();
					int second = cal.get(Calendar.SECOND);
					
					if(second < 10 && second >= 0)
					{
						builder.append("0");
					}
					
					builder.append(second);
					break;
				}
				case THREE_NUMER_MILLISECOND :
				{
					Calendar cal = Calendar.getInstance();
					int milli = cal.get(Calendar.MILLISECOND);
					
					if(milli < 10 && milli >= 0)
					{
						builder.append("00");
					}
					else if(milli < 100 && milli >= 0)
					{
						builder.append("0");
					}
					
					builder.append(milli);
					break;
				}
				case HEX :
				{
					GUIDExpressionParser guide = 
							new GUIDExpressionParser(getHashExpression());
					
					String hexValue = guide.generateNextGUID();
					try
					{
						builder.append(generateMD5(hexValue));
					}
					catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}
					catch (NoSuchAlgorithmException e)
					{
						e.printStackTrace();
					}
					
					break;
				}
				case SHA256 :
				{
					GUIDExpressionParser guide = 
							new GUIDExpressionParser(getHashExpression());
					
					String hexValue = guide.generateNextGUID();
					try
					{
						builder.append(generateSHA256(hexValue));
					}
					catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}
					catch (NoSuchAlgorithmException e)
					{
						e.printStackTrace();
					}
					
					break;
				}
				case RANDOM :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}

								
								String randomString = generateRandomExpression(count, true);
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString);
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							String randomExpression = generateRandomExpression(randomCount, true);
							GUIDExpressionParser guide = new GUIDExpressionParser(randomExpression);
							builder.append(guide.generateNextGUID());
						}
						
					}
					
					break;
				}
				case RANDOM_WITHOUT_SYMBOLS :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								String randomString = generateRandomExpression(count, false);
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString);
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							String randomExpression = generateRandomExpression(randomCount, false);
							GUIDExpressionParser guide = new GUIDExpressionParser(randomExpression);
							builder.append(guide.generateNextGUID());
						}
					}
					
					break;
				}
				case RANDOM_NUMBER :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								StringBuilder randomString = new StringBuilder();
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								for(int i = 0; i < count; i++)
								{
									randomString.append("#");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								for(int i = 0; i < randomCount; i++)
								{
									randomString.append("#");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					break;
				}
				case RANDOM_HEX :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								StringBuilder randomString = new StringBuilder();
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								for(int i = 0; i < count; i++)
								{
									randomString.append("$");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								for(int i = 0; i < randomCount; i++)
								{
									randomString.append("$");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					break;
				}
				case RANDOM_UPPER_CASE_ALPHA :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								StringBuilder randomString = new StringBuilder();
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								for(int i = 0; i < count; i++)
								{
									randomString.append("@");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								for(int i = 0; i < randomCount; i++)
								{
									randomString.append("@");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					break;
				}
				case RANDOM_LOWER_CASE_ALPHA :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								for(int i = 0; i < count; i++)
								{
									randomString.append("&");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								for(int i = 0; i < randomCount; i++)
								{
									randomString.append("&");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					break;
				}
				case RANDOM_MIXED_CASE_ALPHA :
				{
					if(index < value.length())
					{
						char nextChar = value.charAt((value.length() > index+1)?index+1:index);
						if(nextChar == BRACKET_START)
						{
							int startValue = index+2;
							int endValue = startValue;
							
							for(int tempIndex = startValue; tempIndex < value.length(); tempIndex++)
							{
								if(value.charAt(tempIndex) == BRACKET_END)
								{
									endValue = tempIndex;
									index = tempIndex;
									break;
								}
							}
							
							String subValue = value.substring(startValue, endValue);
							
							try
							{
								StringBuilder randomString = new StringBuilder();
								int count = randomCount;
								
								try
								{
									count = Integer.parseInt(subValue);
								}
								catch(Exception e)
								{
									
								}
								
								for(int i = 0; i < count; i++)
								{
									randomString.append("?");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
						else
						{
							try
							{
								StringBuilder randomString = new StringBuilder();
								
								for(int i = 0; i < randomCount; i++)
								{
									randomString.append("?");
								}
								
								GUIDExpressionParser guide = new GUIDExpressionParser(randomString.toString());
								builder.append(guide.generateNextGUID());
							}
							catch(Exception e)
							{
								
							}
						}
					}
					
					break;
				}
				default :
				{
					builder.append(c);
				}
				
			}
		}
		
		return builder.toString();
	}
	

	public String getExpression()
	{
		return expression;
	}


	public void setExpression(String expression)
	{
		this.expression = expression;
	}


	public String getLastValue()
	{
		return lastValue;
	}


	public void setLastValue(String lastValue)
	{
		this.lastValue = lastValue;
	}
	
	public Map<String, Range> getRangeMap()
	{
		return rangeMap;
	}
	
	public String getHashExpression()
	{
		int number = generateRandomNumber(4, 16);
		return (hashExpression != null)?hashExpression:generateRandomExpression(number, true);
	}

	public void setHashExpression(String hexConfig)
	{
		this.hashExpression = hexConfig;
	}

	public int getRandomCount()
	{
		return randomCount;
	}


	public void setRandomCount(int randomCount)
	{
		this.randomCount = randomCount;
	}


	protected ReturnValue findChar(char currentChar, int currentIndex, char expressionChar, boolean increment)
	{
		if(rangeMap.containsKey(Character.toString(expressionChar)) && increment)
		{
			Range range = rangeMap.get(Character.toString(expressionChar));
			int charIndex = range.getIndexOfValue(currentChar);
			
			if(charIndex < range.getEnd())
			{
				if(increment)
				{
					String value = Character.toString(VALUES[charIndex+1]);
					return new ReturnValue(value, false);
				}
				else
				{
					String value = Character.toString(VALUES[charIndex]);
					return new ReturnValue(value, false);
				}
			}
			else
			{
				String value = Character.toString(VALUES[range.getStart()]);
				return new ReturnValue(value, true);
			}
		}
		else
		{
			return new ReturnValue(Character.toString(currentChar), increment);
		}
	}
	
	public String generateRandomGUID(int size, boolean withSymbols)
	{
		String randomExpression = generateRandomExpression(size, (withSymbols)?SECRET:SECRET_WITHOUT_SYMBOLS);
		GUIDExpressionParser guide = new GUIDExpressionParser(randomExpression);
		return guide.generateNextGUID();
	}
	
	public String generateRandomExpression(int size, boolean withSymbols)
	{
		return generateRandomExpression(size, (withSymbols)?SECRET:SECRET_WITHOUT_SYMBOLS);
	}

	protected String generateRandomExpression(int size, char type)
	{
		StringBuilder randomBuilder = new StringBuilder();
		
		try
		{
			
			Range range = rangeMap.get(Character.toString(type));
			
			for(int count = 0; count < size; count++)
			{
				int randomNumber = generateRandomNumber(range.getStart(), range.getEnd());
				char randomChar = VALUES[randomNumber];
				randomBuilder.append(randomChar);
			}
		}
		catch(Exception e)
		{
			
		}
		
		return randomBuilder.toString();
	}
	
	
	public String generateMD5(String value) 
	throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		byte[] bytesOfMessage = value.getBytes("UTF-32");

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte [] thedigest = md.digest(bytesOfMessage);
		
		StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<thedigest.length;i++) {
    		String hex=Integer.toHexString(0xff & thedigest[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
		
		return hexString.toString().toUpperCase();
	}
	
	public String generateSHA256(String value) 
	throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		byte[] bytesOfMessage = value.getBytes("UTF-8");

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte [] thedigest = md.digest(bytesOfMessage);
		
		StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<thedigest.length;i++) {
    		String hex=Integer.toHexString(0xff & thedigest[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
		
		return hexString.toString().toUpperCase();
	}


	private class Range
	{
		private char character;
		
		private int start;
		private int end;
		
		public Range(char character, int start, int end)
		{
			setCharacter(character);
			setStart(start);
			setEnd(end);
		}

		@SuppressWarnings("unused")
		public char getCharacter()
		{
			return character;
		}

		private void setCharacter(char character)
		{
			this.character = character;
		}

		public int getStart()
		{
			return start;
		}

		private void setStart(int start)
		{
			this.start = start;
		}

		public int getEnd()
		{
			return end;
		}

		private void setEnd(int end)
		{
			this.end = end;
		}
		
		public String toString()
		{
			return Character.toString(character);
		}

		@Override
		public int hashCode()
		{
			return toString().hashCode();
		}
		
		public boolean equals(Object src)
		{
			if(src instanceof String)
			{
				return(src.equals(toString()));
			}
			else if(src instanceof Range)
			{
				return src.toString().equals(toString());
			}
			else
			{
				return false;
			}
		}
		
		public int getIndexOfValue(char c)
		{
			for(int index = start; index <= end; index++)
			{
				if(c == VALUES[index])
				{
					return index;
				}
			}
			
			return 0;
		}
	}
	
	private class ReturnValue
	{
		private String currentValue;
		private boolean incrementNext;
	
		
		public ReturnValue(String value, boolean increment)
		{
			this.setCurrentValue(value);
			this.setIncrementNext(increment);
		}

		
		public String getCurrentValue()
		{
			return currentValue;
		}
		public void setCurrentValue(String currentValue)
		{
			this.currentValue = currentValue;
		}
		public boolean isIncrementNext()
		{
			return incrementNext;
		}
		public void setIncrementNext(boolean incrementNext)
		{
			this.incrementNext = incrementNext;
		}
		
	}

//	public static void main(String [] args)
//	{	
//		GUIDExpressionParser guide = new GUIDExpressionParser("FBI-{C-M-d-Y-y_H:m:s:S-A[1]Z[1]N[1]R[1]n[1]Z[1]r[1]a[1]-X-x} ~`!*@&?%$#") ;
//		System.out.println(guide.generateNextGUID());
//		
//		for(int index = 0; index < 1256; index++)
//		{
//			System.out.println(guide.generateNextGUID());
//		}
//		System.out.println("\nRandom Expression                  Evaluated Value                    MD5 Hash");
//		System.out.println("---------------------------------|----------------------------------|---------------------------------");
//		
//		for(int index = 0; index < 10; index++)
//		{
//			GUIDExpressionParser guide2 = new GUIDExpressionParser();
//			String  guid = guide2.generateRandomExpression(32, (index%2 == 0));
//			guide2.setExpression(guid);
//			try
//			{
//				System.out.println(guid+" = "+guide2.generateNextGUID()+" = "+guide2.generateMD5(guid));
//			}
//			catch (UnsupportedEncodingException e)
//			{
//				e.printStackTrace();
//			}
//			catch (NoSuchAlgorithmException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		
//	}


}


