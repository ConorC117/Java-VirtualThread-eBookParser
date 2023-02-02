package ie.atu.sw;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * The {@code EBookParser} class extends {@code AbstractParser} and is
 * responsible for parsing the ebook file and adding the words and their page
 * locations to the index.
 */
public class EBookParser extends AbstractParser {

	// Get the index from the AbstractParser class
	private ConcurrentMap<String, WordDetail> index = getIndex();

	private Set<String> stopWords = getStopWords();

	private int totalPageCount;

	private String definition;

	/**
	 * Creates a new {@code EBookParser} instance with the specified ebook input,
	 * index, and stop words set.
	 * 
	 * @param ebookInput the ebook file path or URL to parse
	 * @param index      the index map to add the words and page locations to
	 * @param stopWords  the set of stop words
	 */
	// Constructor for access to ebookDirectory and stopWords
	public EBookParser(String ebookInput, ConcurrentMap<String, WordDetail> index, Set<String> stopWords) {
		super(index, stopWords);
	}

	/**
	 * Parses the ebook file and adds the words and definitions to the index.
	 * 
	 * @param file the ebook file path or URL to parse
	 * 
	 * @throws IOException if an I/O error occurs reading the ebook file
	 */
	// O(n), where n is the number of lines that are parsed
	@Override
	public void parse(String file) {
		if (file == null || file.trim().isEmpty()) {
			System.out.println("Error: You must specify an EBook directory path or URL before execution.");
			// Redisplay the menu here
			return;
		}

		int lineCount = 0;
		try {
			// Check if ebookDirectory is a URL
			try {
				URL url = new URL(file);
				try (Scanner scanner = new Scanner(url.openStream())) {
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						lineCount++;
						processLineFromBook(line, lineCount);
						if (lineCount % 40 == 0) {
							totalPageCount = getTotalPageCount() + 1;
						}
					}
				}
			} catch (MalformedURLException e) {
				// ebookDirectory is not a URL, assume it's a file path
				for (String line : Files.lines(Path.of(file)).collect(Collectors.toList())) {
					lineCount++;
					processLineFromBook(line, lineCount);
					if (lineCount % 40 == 0) {
						totalPageCount = getTotalPageCount() + 1;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the specified line from the book and updates the index accordingly.
	 *
	 * @param line      the line from the book to process
	 * @param lineCount the line count of the line in the book
	 */
	// Processes the specified line from the book and updates the index accordingly
	// by splitting the line into words, converting them to lower case, and adding
	// them to the index if they are not stop words. O(n), where n is the number of
	// words in a line
	private void processLineFromBook(String line, int lineCount) {
		Thread.startVirtualThread(() -> {

			// Calculate the page number based on the line count
			int page = lineCount / 40 + 1;

			// Split the line into words and process each word
			Arrays.stream(line.split("[^a-zA-Z]+")).forEach(word -> {

				// Prevents blank spaces from being processed as words
				if (word.isEmpty()) {
					return;
				}

				// Convert the word to lower case
				word = word.toLowerCase();

				// Check if the word is a stop word and skip it if it is
				if (stopWords.contains(word)) {
					return;
				}

				// Check if the word is already in the index
				WordDetail wordDetail = index.get(word);
				if (wordDetail == null) {
					// If the word is not in the index, add it to the index
					wordDetail = addToIndex(word, definition, page);
				}
				// Increment the word count and add the page number to the list of pages where
				// the word appears
				wordDetail.incrementWordCount();
				wordDetail.addPage(page);
			});
		});
	}

	/**
	 * Adds the specified word and page number to the index. If the page is not
	 * already in the index, updates the definition if available.
	 *
	 * @param word       the word to add to the index
	 * @param definition the definition of the word
	 * @param pageNumber the page number of the word
	 *
	 * @return the {@code WordDetail} object for the word
	 */
	// The addToIndex method has a time complexity of O(1) on average, because it
	// uses a ConcurrentMap which has an average time complexity of O(1) for the get
	// and put operations. However, in the worst case it could have a time
	// complexity of O(n), where n is the number of elements in the map, if the map
	// needs to be resized.
	@Override
	public WordDetail addToIndex(String word, String definition, Integer pageNumber) {

		// Check if the word is already in the index
		WordDetail wordDetail = index.get(word);

		if (wordDetail == null) {

			// If the word is not in the index, create a new WordDetail object and add it to
			// the index
			wordDetail = new WordDetail(word, definition, pageNumber);
			index.put(word, wordDetail);
		} else {

			// If the page number is not already assigned to the word in the index, add the
			// page number to the list of
			// pages
			if (!wordDetail.getPages().contains(pageNumber)) {
				wordDetail.getPages().add(pageNumber);

			}

		}
		return wordDetail;
	}

	/**
	 * 
	 * Returns the total page count of the ebook.
	 * 
	 * @return the total page count of the ebook
	 */
	// Getter for TotalPageCount
	public int getTotalPageCount() {
		return totalPageCount;
	}

}
