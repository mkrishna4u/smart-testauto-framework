/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.ui.core.data.builder;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Used to build randomized textual data to help support different type characters testing on each run. 
 * This can include alphabets, numbers, special characters, new line, spaces, leading characters etc.
 * 
 * @author Madhav Krishna
 *
 */
public class TestDataBuilder {
	private int length = 0;
	private int maxWordLength = 0;
	private boolean includeAlphabetsLower = false;
	private boolean includeAlphabetsUpper = false;
	private String alphabetsLower = "abcdefghijklmnopqrstuvwxyz";
	private String alphabetsUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private boolean includeNumbers = false;
	private String numbers = "1234567890";
	private boolean includeSpecialCharacters = false;
	private String specialCharacters = "`~!@#$%^&*()_-+={[]}\\|;:'\",<.>/?";
	private boolean includeNewLine = false;
	private boolean includeWhiteSpaces = false;
	private boolean includeLeadingWhiteSpace = false;
	private boolean includeTrailingWhiteSpace = false;

	public TestDataBuilder() {
		// Do nothing
	}

	public TestDataBuilder setLength(int length) {
		this.length = length;
		return this;
	}

	public TestDataBuilder setMaxWordLength(int maxWordLength) {
		this.maxWordLength = maxWordLength;
		return this;
	}

	public TestDataBuilder setIncludeAlphabets(boolean includeAlphabets) {
		this.includeAlphabetsLower = includeAlphabets;
		this.includeAlphabetsUpper = includeAlphabets;
		return this;
	}

	public TestDataBuilder setIncludeAlphabetsLower(boolean includeAlphabetsLower) {
		this.includeAlphabetsLower = includeAlphabetsLower;
		return this;
	}

	public TestDataBuilder setIncludeAlphabetsUpper(boolean includeAlphabetsUpper) {
		this.includeAlphabetsUpper = includeAlphabetsUpper;
		return this;
	}

	public TestDataBuilder setAlphabetsLower(String alphabetsLower) {
		this.alphabetsLower = alphabetsLower;
		return this;
	}

	public TestDataBuilder setAlphabetsUpper(String alphabetsUpper) {
		this.alphabetsUpper = alphabetsUpper;
		return this;
	}

	public TestDataBuilder setIncludeNumbers(boolean includeNumbers) {
		this.includeNumbers = includeNumbers;
		return this;
	}

	public TestDataBuilder setNumbers(String numbers) {
		this.numbers = numbers;
		this.includeNumbers = true;
		return this;
	}

	public TestDataBuilder setIncludeSpecialCharacters(boolean includeSpecialCharacters) {
		this.includeSpecialCharacters = includeSpecialCharacters;
		return this;
	}

	public TestDataBuilder setSpecialCharacters(String specialCharacters) {
		this.specialCharacters = specialCharacters;
		this.includeSpecialCharacters = true;
		return this;
	}

	public TestDataBuilder setIncludeNewLine(boolean includeNewLine) {
		this.includeNewLine = includeNewLine;
		return this;
	}

	public TestDataBuilder setIncludeWhiteSpaces(boolean includeWhiteSpaces) {
		this.includeWhiteSpaces = includeWhiteSpaces;
		return this;
	}

	public TestDataBuilder setIncludeLeadingAndTrailingWhiteSpace(boolean includeLeadingAndTrailingWhiteSpace) {
		this.includeLeadingWhiteSpace = includeLeadingAndTrailingWhiteSpace;
		this.includeTrailingWhiteSpace = includeLeadingAndTrailingWhiteSpace;
		return this;
	}

	public TestDataBuilder setIncludeLeadingWhiteSpace(boolean includeLeadingWhiteSpace) {
		this.includeLeadingWhiteSpace = includeLeadingWhiteSpace;
		return this;
	}

	public TestDataBuilder setIncludeTrailingWhiteSpace(boolean includeTrailingWhiteSpace) {
		this.includeTrailingWhiteSpace = includeTrailingWhiteSpace;
		return this;
	}

	public String build() {
		return build(0, null);
	}

	public String build(int numLeadingChars, Character leadingChar) {
		StringBuilder sb = new StringBuilder("");
		Random rLowerAlphabet = new Random();
		Random rUpperAlphabet = new Random();
		Random rNumberChars = new Random();
		Random rSpecialChars = new Random();

		if (includeLeadingWhiteSpace) {
			sb.append(" ");
		}

		int startIndex = includeLeadingWhiteSpace ? 1 : 0;
		int endIndex = includeTrailingWhiteSpace ? length - 2 : length - 1;

		for (int i = startIndex; i <= endIndex;) {
			if (numLeadingChars > 0) {
				sb.append(leadingChar);
				numLeadingChars--;
				i++;
				if (sb.length() == length) {
					break;
				}
				continue;
			}

			if (includeAlphabetsLower) {
				sb.append(getNextCharFromStr(alphabetsLower, rLowerAlphabet));
				i++;
				if (sb.length() == length) {
					break;
				}
			}

			if (includeAlphabetsUpper) {
				sb.append(getNextCharFromStr(alphabetsUpper, rUpperAlphabet));
				i++;
				if (sb.length() == length) {
					break;
				}
			}

			if (includeNumbers) {
				sb.append(getNextCharFromStr(numbers, rNumberChars));
				i++;
				if (sb.length() == length) {
					break;
				}
			}

			if (includeSpecialCharacters) {
				sb.append(getNextCharFromStr(specialCharacters, rSpecialChars));
				i++;
				if (sb.length() == length) {
					break;
				}
			}
		}

		// separate string into words
		List<Integer> wordCountList = prepareWordCountList(maxWordLength, sb.length());
		int offset = 0;
		for (int i = 0; i < wordCountList.size(); i++) {
			offset = offset + wordCountList.get(i);
			if (offset + i >= length) {
				break;
			}
			sb.insert(offset + i, " ");
			sb.deleteCharAt(sb.length() - 1);
		}

		if (includeWhiteSpaces && sb.length() > 1 && sb.indexOf(" ") < 0) {
			sb.insert(1, " ");
			sb.deleteCharAt(sb.length() - 1);
		}

		if (includeNewLine && sb.length() > 1) {
			sb.insert(sb.length() / 2, "\n");
			sb.deleteCharAt(sb.length() - 1);
		}

		if (includeTrailingWhiteSpace) {
			sb.append(" ");
		}

		return sb.toString();
	}

	private List<Integer> prepareWordCountList(int maxWordLength, int dataLength) {
		List<Integer> wordCountList = new LinkedList<>();
		if (maxWordLength > 0) {
			int totalLength = 0;
			Random rWordLength = new Random();
			do {
				int wordCount = generateNextNum(rWordLength, 1, maxWordLength + 1);
				totalLength += wordCount;
				if (totalLength <= dataLength) {
					wordCountList.add(wordCount);
				} else {
					int diff = totalLength - dataLength;
					if (diff > 0) {
						wordCountList.add(wordCount);
					}
				}
			} while (totalLength < dataLength);

			if (wordCountList.size() > 0) {
				wordCountList.remove(wordCountList.size() - 1);
			}
		}
		return wordCountList;
	}

	private String getNextCharFromStr(String str, Random r) {
		if (str == null || "".equals(str)) {
			return "";
		}
		return "" + str.charAt(generateNextNum(r, 0, str.length()));
	}

	private int generateNextNum(Random r, int min, int length) {
		return (r.nextInt(length - min) + min);
	}

//	public static void main(String[] args) {
//		try {
//			TestDataBuilder b = new TestDataBuilder();
//			b.setLength(50)
//			 .includeAlphabets(true)
//			 .includeNumbers(true)
//			 .includeSpecialCharacters(true);
//
//			String data = b.build(30, '0');
//
//			System.out.println(data);
//			System.out.println("Length: " + data.length());
//			
//			b = new TestDataBuilder();
//			b.setLength(50)
//			 .includeAlphabets(true)
//			 .includeNumbers(true)
//			 .includeSpecialCharacters(true);
//
//			data = b.build(30, '0');
//
//			System.out.println(data);
//			System.out.println("Length: " + data.length());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
