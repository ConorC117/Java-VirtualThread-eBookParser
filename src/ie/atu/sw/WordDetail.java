package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code WordDetail} class represents a word and its associated details
 * such as its definition, page numbers, and word count.
 */
public class WordDetail implements Comparable<WordDetail> {
	private String word;
	private String definition;
	private List<Integer> pages;
	private int wordCount;

	/**
	 * Creates a new {@code WordDetail} instance with the specified word,
	 * definition, and page number.
	 *
	 * @param word       the word
	 * @param definition the definition of the word
	 * @param pageNumber the page number where the word appears
	 */
	public WordDetail(String word, String definition, Integer pageNumber) {
		this.word = word;
		this.definition = definition;
		this.pages = new ArrayList<>();
		this.pages.add(pageNumber);
		this.wordCount = 0;
	}

	/**
	 * Returns the list of page numbers where the word appears.
	 *
	 * @return the list of page numbers
	 */
	// O(1), returning a value of a field that is stored in memory.
	public List<Integer> getPages() {
		return pages;
	}

	/**
	 * Returns the word.
	 *
	 * @return the word
	 */
	// O(1), returning a value of a field that is stored in memory.
	public String getWord() {
		return word;
	}

	/**
	 * Returns the definition of the word.
	 * 
	 * @return the definition of the word
	 */
	// O(1), returning a value of a field that is stored in memory.
	public String getDefinition() {
		return definition;
	}

	/**
	 * Sets the definition of the word.
	 * 
	 * @param definition the definition of the word to set
	 */
	// O(1), setting value of a field that is stored in memory.
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * Increments the word count.
	 */
	// O(1), incrementing value of a field that is stored in memory.
	public void incrementWordCount() {
		this.wordCount++;
	}

	/**
	 * 
	 * Returns the number of occurrences of the word in the book.
	 * 
	 * @return the number of occurrences of the word in the book
	 */
	// O(1), returning a value of a field that is stored in memory.
	public int getWordCount() {
		return wordCount;
	}

	/**
	 * 
	 * Adds the specified page number to the list of pages where the word appears.
	 * If the page number is already in the list, it is not added again.
	 * 
	 * @param pageNumber the page number to add
	 */
	// O(n) on average, as it needs to search through the entire list to find the
	// correct position to insert the page number. In the worst case, it could have
	// a time complexity of O(n^2) if the list needs to be resized and reallocated
	// in memory.
	public void addPage(int pageNumber) {
		if (!pages.contains(pageNumber)) {
			int index = 0;
			while (index < pages.size() && pageNumber > pages.get(index)) {
				index++;
			}
			pages.add(index, pageNumber);
		}
	}

	/**
	 * 
	 * Compares this {@code WordDetail} object to the specified object. The result
	 * is {@code true} if and only if the argument is not {@code null} and is a
	 * {@code WordDetail} object that represents the same word count as this object.
	 * 
	 * @param other the object to compare this {@code WordDetail} object against
	 * @return {@code true} if the given object represents a {@code WordDetail}
	 *         equivalent to this object, {@code false} otherwise
	 */
	// O(1), comparing two integer values stored in memory and returning the result.
	@Override
	public int compareTo(WordDetail other) {
		// Compare the wordCount of this WordDetail object and the other WordDetail
		// object
		return Integer.compare(this.wordCount, other.wordCount);
	}

}
