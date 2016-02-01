// 1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structure
// Method 1: HashSet
// Time Complexity: O(n), Space Complexity: O(n)
public boolean isUnique(String word) {
	if (word == null || word.length() == 0) {
		return true;
	}
	Set<Character> hset = new HashSet<>();
	for (int i = 0; i < word.length(); i++) {
		if (hset.contains(word.charAt(i))) {
			return false;
		} else  {
			hset.add(word.charAt(i));
		}
	}
	return true;
}
// Time Complexity: O(n), Space Complexity: Constant Space
// Method 2: Not using data structure
public boolean isUnique(String str) {
	if (str == null || str.length() == 0) {
		return true;
	}
	if (str.length() > 128) {
		return false;
	}
	boolean[] char_set = new boolean[128];
	for (int i = 0; i < str.length(); i++) {
		int index = (int) (str.charAt(i) - 'a');
		if (char_set[index]) {
			return false;
		} else {
			char_set[index] = true;
		}
	}
	return true;
}

// 1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the other;
// Method: sort the String
// Time Complexity: O(nlog(n)), Space Complexity O(1)
public boolean permutation(String str1, String str2) {
	if (str1 == null && str2 == null || str1.length() == 0 && str2.length() == 0) {
		return true;
	} else if (str1 == null) {
		return false;
	} else if (str2 == null) {
		return false;
	}
	String newStr1 = sort(str1);
	String newStr2 = sort(str2);
	return newStr1.equals(newStr2);
}
public String sort(String str) {
	char[] temp = str.toCharArray();
	Arrays.sort(temp);
	return new String(temp);
}
// Method: Check if the two strins have identical charcter counts
// Time Complexity: O(n), Space Complexity Constant Space
public boolean permutation(String s, String t) {
	if (s.length() != t.length()) {
		return false;
	}
	int[] letters = new int[128];
	char[] s_array = s.toCharArray();
	for (char c : s_array) {
		letters[c]++;
	}
	for (int i = 0; i < t.length(); i++) {
		int c = (int) t.charAt(i);
		letters[c]--;
		if (letters[c] < 0) {
			return false;
		}
	}
	return true;
}

// 1.3 URLify: Write a method to replace all spaces in a string with '%20'. you may assume that the string has sufficient space at
// the end to hold the additional characters, and that you are given the "true" length of the string. (Note: if implementing in Java
// please use a character array so that you can perform this operation in place.)
// EXAMPLE:
// Input:	"Mr John Smith ", 13
// Output:	"Mr%20John%20Smith"
public void replaceSpace(char[] str, int length) {
	if (str == null || str.length == 0) {
		return;
	}
	int countSpace = 0;
	for (int i = 0; i < str.length; i++) {
		if (str[i] == ' ') {
			countSpace++;
		}
	}
	int len = str.length - 1;
	int length = str.length - 1 + 2 * countSpace;
	while (len >= 0) {
		if (str[len] == ' ') {
			str[length - 3] = '%';
			str[length - 2] = '2';
			str[length - 1] = '0';
			length -= 3;
		} else {
			str[length - 1] = str[len];
			length--;
		}
		len--;
	}
}

// 1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is a word 
// phrase that is the same forwards and backwards. A permutation is a rearrangement of letters. The palindrome does not need to be limited
// to just dictionary words.
// Input: Tact Coa
// Output True (permutations: "taco cat", "atco cta", etc)
// Time Complexity: O(n), Space Complexity: Constant Space
public boolean isPermutationOfPalindrome(String phrase) {
	int countOdd = 0;
	int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a')];
	for (char c : phrase.toCharArray()) {
		int x = getCharNumber(c);
		if (x != -1) {
			table[x]++;
			if (table[x] % 2 == 1) {
				countOdd++;
			} else {
				countOdd--;
			}
		}
	}
	return countOdd <= 1;
}
public int getCharNumber(char c) {
	return Character.getNumericValue(c) - Character.getNumericValue('a');
}

// 1.5 There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. Given
// two strings, write a function to check if they are one edit(or zero edits) away.
// Leetcode 72: Edit Distance
// Dynamic Programming
// Time Complexity: O(m * n), Space Complexity: O(m * n)
public int minDistance(String word1, String word2) {
	int m = word1.length();
	int n = word2.length();
	int[][] dp = new int[m + 1][n + 1];
	dp[0][0] = 0;
	for (int i = 1; i <= m; i++) {
		dp[i][0] = 1;
	}
	for (int i = 1; i <= n; i++) {
		dp[0][i] = 1;
	}
	for (int i = 1; i <= m; i++) {
		for (int j = 1; j <= n; j++) {
			if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
				dp[i][j] = dp[i - 1][j - 1];
			} else {
				dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
			}
		}
	}
	return dp[m][n];
}

// CC189 solutions:
// Time Complexity: O(n), Space Complexity: Constant Space
public boolean oneEditAway(String first, String second) {
	if (first.length() == second.length()) {
		return oneEditReplace(first, second);
	} else if (first.length() + 1 == second.length()) {
		return oneEditInsert(first, second);
	} else if (first.length() - 1 == second.length()) {
		return oneEditInsert(second, first);
	}
	return false;
}
public boolean oneEditReplace(String s1, String s2) {
	boolean foundDifference = false;
	for (int i = 0; i < s1.length(); i++) {
		if (s1.charAt(i) != s2.charAt(i)) {
			if (foundDifference) {
				return false;
			}
			foundDifference = true;
		}
	}
	return true;
}
public boolean oneEditInsert(String s1, String s2) {
	int index1 = 0;
	int index2 = 0;
	while (index2 < s2.length() && index1 < s1.length()) {
		if (s1.charAt(index1) != s2.charAt(index2)) {
			if (index1 != index2) {
				return false;
			}
			index2++;
		} else {
			index1++;
			index2++;
		}
	}
	return true;
}

// 1.6 String Compression: Implement a method to perform basic string compression using the counts of replated characters. For example, the string
// aabcccccaaa would become a2b1c5a3. if the "compressed" string would not become smaller than the original string. your method should return the 
// oritinal string. you can assume the string has only uppercase and lowercase letters (a - z).
// Method: StringBuilder
public String compression(String str) {
	if (str == null || str.length() == 0) {
		return null;
	}
	StringBuilder sb = new StringBuilder();
	int index = 0;
	while (index < str.length()) {
		int count = 1;
		while (index + 1 < str.length() && str.charAt(index) == str.charAt(index + 1)) {
			count++;
			index++;
		}
		sb.append(str.charAt(index));
		sb.append(count);
		index++;
	}
	return sb.toString();
}

// 1.7 Rotate Matrix: Given an image represented by an N * N matrix, where each pixel in the image is 4 bytes, write a method to rotate the image
// by 90 degress, Can you do this in place?
// LeetCode: 48
public void rotateMatrix(int[][] matrix) {
	if (matrix == null || matrix.length == 0) {
		return;
	}
	int length = matrix.length;
	for (int i = 0; i < length / 2; i++) {
		for (int j = 0; j < (length + 1) / 2 ; j++) {
			int temp = matrix[i][j];
			matrix[i][j] = matrix[length - j - 1][i];
			matrix[length - j - 1][i] = matrix[length - i - 1][length - j - j];
			matrix[length - i - 1][length - j - 1] = matrix[j][length - i - 1];
			matrix[j][length - i - 1] = temp;
		}
	}
}
// CC189 Solution
public void rotate (int[][] matrix, int n) {
	for (int layer = 0; layer < n / 2; ++layer) {
		int first  = layer;
		int last = n - 1 - layer;
		for (int i = first; i < last; ++i) {
			int offset = i - first;
			int top = matrix[first][i];
			// left -> top
			matrix[first][i] = matrix[last - offset][first];
			// bottom -> left
			matrix[last - offset][first] = matrix[last][last - offset];
			// right -> bottom
			matrix[last][last - offset] = matrix[i][last];
			// top -> right;
			matrix[i][last] = top;
		}
	}
}

// 1.8 Zero Matrix: Write an algorithm such that if an element in an M*N matrix is 0, its entire row and column are set to 0
// Time Complexity is O(m * n), Space Complexity is O(m * n)
public void setZeros(int[][] matrix) {
	if (matrix == null || matrix.length == 0) {
		return;
	}
	boolean[] row = new boolean[matrix.length];
	boolean[] col = new boolean[matrix[0].length];
	for (int i = 0; i < matrix.length; i++) {
		for (int j = 0; j < matrix[0].length; j++) {
			if (matrix[i][j] == 0) {
				row[i] = true;
				col[j] = true;
			}
		}
	}
	for (int i = 0; i < row.length; i++) {
		if (row[i]) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	for (int i = 0; i < col.length; i++) {
		if (col[i]) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[j][i] = 0;
			}
		}
	}
}
// CC189 Solution: Time Complexity O(m*n), Space Complexity is O(1)
// 这个做法是以第一行和第一列为标记单位。先扫一遍第一行和第一列，如果有0存在于第一行和第一列的，则要把flag设置成true以便最后把第一行第一列设置为0
// 其余的做法就是以第一行第一列为标记单位，如果那个坐标发现为0，则设置第一行和第一列相应的行与列的数值为0.最后扫的时候很容易就发现哪个是0了
public void setZeros(int[][] matrix) {
	boolean rowHasZero = false;
	boolean colHasZero = true;

	// Check first row has zero
	for (int j = 0; j < matrix[0].length; j++) {
		if (matrix[0][j] == 0) {
			rowHasZero = true;
			break;
		}
	}

	// Check first column has a zero
	for (int i = 0; i < matrix.length; i++) {
		if (matrix[i][0] == 0) {
			colHasZero = true;
			break;
		}
	}

	// Check for zeros in the rest of array
	for (int i = 1; i < matrix.length; i++) {
		for (int j = 1; j < matrix[0].length; j++) {
			if (matrix[i][j] == 0) {
				matrix[i][0] = 0;
				matrix[0][j] = 0;
			}
		}
	}

	// nullify rows based on values in first column
	for (int i = 1; i < matrix.length; i++) {
		if (matrix[i][0] == 0) {
			nullifyRow(matrix, i);
		}
	}
	// nullify columns based on values in first row
	for (int j = 1; j < matrix.length; j++) {
		if (matrix[0][j] == 0) {
			nullifyColumn(matrix, i);
		}
	}
	if (rowHasZero) {
		nullifyRow(matrix, 0);
	}
	if (colHasZero) {
		nullifyColumn(matrix, 0);
	}
}

// 1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring of another. Given two Strings, s1 and s2,
// write code to check if s2 is a rotation of s1 using only one call to isSubString
public boolean isRotation(String s1, String s2) {
	int length = s1.length();
	if (length == s2.length() && length > 0) {
		String s1s1 = s1 + s1;
		return isSubstring(s1s1, s2);
	}
	return false;
}