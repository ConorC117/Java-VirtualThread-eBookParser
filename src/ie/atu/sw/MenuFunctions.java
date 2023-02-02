package ie.atu.sw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 
 * The {@code MenuFunctions} class provides the logic for processing user input
 * and executing the appropriate action.
 */
public class MenuFunctions {

	// Used for testing.
	//  private static final String DEFAULT_EBOOK_DIRECTORY = "./textFiles/1984Orwell.txt";
	//	private String ebookInput = DEFAULT_EBOOK_DIRECTORY;

	private static final String DEFAULT_DICTIONARY_DIRECTORY = "./dictionary.csv";
	private static final String DEFAULT_GOOGLE1000_DIRECTORY = "./google-1000.txt";
	private static final String DEFAULT_OUTPUT_FILE = "./output";
	private String ebookInput;

	// Initialise Scanner
	private static Scanner s;

	// Keep menu running while true
	private boolean keepRunning = true;

	// Initialise instance variable google1000 and set default value
	private String google1000Directory = DEFAULT_GOOGLE1000_DIRECTORY;

	// Create an empty set of stop words
	private Set<String> stopWords = new ConcurrentSkipListSet<>();

	// Create new instance of StopWordsParser, passing the google1000Directory and
	// the empty stopWords set
	StopWordsParser stopWordsParser = new StopWordsParser(google1000Directory, stopWords);

	// Initialising the index map for access across multiple classes. A
	// ConcurrentHashMap is used as it is thread safe
	private ConcurrentMap<String, WordDetail> index = new ConcurrentHashMap<>();

	// Create a new EBookParser, passing the ebookDirectory, index, and stopWords
	EBookParser ebookParser = new EBookParser(ebookInput, index, stopWordsParser.getStopWords());

	// Initialise instance variable dictionary and set default value
	private String dictionaryDirectory = DEFAULT_DICTIONARY_DIRECTORY;

	// Create new instance of DictionaryParser
	DictionaryParser dictionaryParser = new DictionaryParser(ebookParser.getIndex(), dictionaryDirectory,
			stopWordsParser.getStopWords());

	// Initialise instance variable output and set default value
	private String output = DEFAULT_OUTPUT_FILE;

	// Create new instance of IndexOutputter
	IndexOutputter indexOutputter = new IndexOutputter(output);

	/**
	 * 
	 * The {@code MenuFunctions} constructor processes user input via the Scanner
	 * class.
	 * 
	 */
	// Method to process user input via Scanner class. O(1), as it only initialises
	// the Scanner Object
	public MenuFunctions() {
		s = new Scanner(System.in);
	}

	/**
	 * The {@code start} method displays the menu options to the user and processes
	 * the user's input to execute the appropriate action.
	 * 
	 */
	// Method to start display of menu. O(n), assuming the user calls the execute
	// function, as the time complexity of each parser's parse method is O(n).
	public void start() {

		// While menu is set to display
		while (keepRunning) {

			// Show user options and request input
			MenuDisplay md = new MenuDisplay();
			md.displayOptions();

			// Blocking method: Parses input string as integer
			int choice = s.nextInt();
			s.nextLine(); // Consume the newline character

			// Where user input matches option, perform subsequent function.
			switch (choice) {
			case 1 -> specifyEBookDirectory();
			case 2 -> specifyDictionaryDirectory();
			case 3 -> specifyCommonWordsDirectory();
			case 4 -> specifiyOutputFile();
			case 5 -> execute();
			case 6 -> getTopFrequentWords();
			case 7 -> getSortedWords();
			case 8 -> getTotalUniqueWords();
			case 9 -> searchForWord();
			case 10 -> quit();

			}
		}
	}

	// Function to change the eBookDirectory file based on user input. O(1),
	// constant time to assign a new value to a variable based on single input
	private void specifyEBookDirectory() {

		System.out.println("[INFO] The current eBook URL or directory path is " + ebookInput);
		System.out.println("[INFO] Please specify the URL or eBook file directory path");

		System.out.println("Enter URL or eBook directory path>");

		// Assign user input to eBookDirectory
		String eBookDirectory = s.next();

		// Append characters after white spaces to string. This allows for spaces to be
		// included in the file path
		eBookDirectory += s.nextLine();

		// Set textDirectory instance variable value to that of textDirectory local
		// variable
		setEBookDirectory(eBookDirectory);
		System.out.println("The working file directory is now " + eBookDirectory);

	}

	// Function which changes eBookDirectory when called
	private void setEBookDirectory(String eBookDirectory) {
		this.ebookInput = eBookDirectory;
	}

	// Function to change the dictionaryDirectory file based on user input. O(1),
	// constant time to assign a new value to a variable based on single input
	private void specifyDictionaryDirectory() {
		System.out.println("[INFO] The current dictionary directory path is " + dictionaryDirectory);
		System.out.println("[INFO] Please specify the dictionary file directory path");

		System.out.println("Enter dictionary directory path>");

		// Assign user input to dictionaryDirectory
		String dictionaryDirectory = s.next();

		// Append characters after white spaces to string. This allows for spaces to be
		// included in the file path
		dictionaryDirectory += s.nextLine();

		// Set dictionaryDirectory instance variable value to that of
		// dictionaryDirectory local
		// variable
		setDictionaryDirectory(dictionaryDirectory);
		System.out.println("The working file directory is now " + dictionaryDirectory);
	}

	// Function which changes dictionaryDirectory when called
	private void setDictionaryDirectory(String dictionaryDirectory) {
		this.dictionaryDirectory = dictionaryDirectory;
	}

	// Function to change the google1000Directory file based on user input. O(1),
	// constant time to assign a new value to a variable based on single input
	private void specifyCommonWordsDirectory() {
		System.out.println("[INFO] The current common words directory path is " + google1000Directory);
		System.out.println("[INFO] Please specify the common words file directory path");

		System.out.println("Enter common words directory path>");

		// Assign user input to dictionaryDirectory
		String google1000Directory = s.next();

		// Append characters after white spaces to string. This allows for spaces to be
		// included in the file path
		google1000Directory += s.nextLine();

		// Set dictionaryDirectory instance variable value to that of
		// dictionaryDirectory local
		// variable
		setGoogle1000Directory(google1000Directory);
		System.out.println("The working file directory is now " + google1000Directory);
	}

	// Function which changes google1000Directory when called
	private void setGoogle1000Directory(String google1000Directory) {
		this.google1000Directory = google1000Directory;
	}

	// Function to change the output file based on user input. O(1),
	// constant time to assign a new value to a variable based on single input
	private void specifiyOutputFile() {

		System.out.println("[INFO] The current output file name is " + output);
		System.out.println("[INFO] Please specify the desired output path and file name");

		System.out.println("Enter output directory name>");

		// Make output equal to user input
		String output = s.next();

		// Append characters after white spaces to string. This allows for spaces to be
		// included in the file path
		output += s.nextLine();

		// Set output instance variable value to that of output local variable
		setOutput(output);
		System.out.println("The working output directory is now " + output + ".txt");

	}

	// Function which changes the output file if called
	private void setOutput(String output) {
		this.output = output;
	}

	// Function to parse each file and build the index using virtual threads. O(n),
	// as it calls the parse methods of the parsers classes which each have O(n)
	// complexity
	private void execute() {

		// Parses the list of stopWords from the specified file and creates a stopWords
		// ConcurrentSkipListSet to exclude them from
		// the index
		try {
			System.out.println("Loading common words file: " + google1000Directory);
			stopWordsParser.parse(google1000Directory);
			System.out.println("Added " + stopWordsParser.getStopWords().size() + " stop words.");

		} catch (Exception e) {
			System.out.println("[ERROR] An error occurred while loading the common words file: " + e.getMessage());
			e.printStackTrace();
		}

		// Parse the e-book file using the specified file path and add each word to the
		// index
		try {
			System.out.println("Parsing e-book file: " + ebookInput);
			ebookParser.parse(ebookInput);
			System.out.println("Page count " + ebookParser.getTotalPageCount());
		} catch (Exception e) {
			System.out.println("[ERROR] An error occurred while parsing the e-book file: " + e.getMessage());
			e.printStackTrace();
		}

		// Parse the dictionary file using the specified file path and add each word and
		// definition to the index
		try {
			System.out.println("Parsing dictionary file: " + dictionaryDirectory);
			dictionaryParser.parse(dictionaryDirectory);

		} catch (Exception e) {
			System.out.println("[ERROR] An error occurred while parsing the dictionary file: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			// Write index to output text
			System.out.println("Writing index to output file: " + output);
			indexOutputter.writeIndex(dictionaryParser.getIndex(), output);
			System.out.println("Index written to output file successfully.");
		} catch (Exception e) {
			System.out
					.println("[ERROR] An error occurred while writing the index to the output file: " + e.getMessage());
			e.printStackTrace();
		}

	}

	// Function to find most or least frequent words, within a specified range. O(n
	// log n), as it first sorts the list of words by frequency using a Comparator,
	// which has a time complexity of O(n log n),
	private void getTopFrequentWords() {
		System.out.println("Enter the number of top frequent words you want to get: ");
		int n = s.nextInt();

		System.out.println("Enter '1' for most frequent words or '2' for least frequent words: ");
		int choice = s.nextInt();

		List<WordDetail> topFrequentWords;
		if (choice == 1) {
			// Get the most frequent words
			topFrequentWords = getTopFrequentWords(n, true);
		} else if (choice == 2) {
			// Get the least frequent words
			topFrequentWords = getTopFrequentWords(n, false);
		} else {
			// If the user enters a value other than 1 or 2, initialise the list to an empty
			// list
			topFrequentWords = new ArrayList<>();
		}

		// Print the top frequent/infrequent words
		for (WordDetail wordDetail : topFrequentWords) {
			System.out.println(wordDetail.getWord() + ": Frequency = " + wordDetail.getWordCount());
		}
	}

	private List<WordDetail> getTopFrequentWords(int n, boolean mostFrequent) {
		// Create a new list of WordDetail objects
		List<WordDetail> wordDetails = new ArrayList<>();
		// Add all the WordDetail objects from the index to the list
		wordDetails.addAll(index.values());
		// Sort the list of WordDetail objects by frequency
		wordDetails.sort(mostFrequent ? (wd1, wd2) -> Integer.compare(wd2.getWordCount(), wd1.getWordCount())
				: (wd1, wd2) -> Integer.compare(wd1.getWordCount(), wd2.getWordCount()));
		// Return the first n elements of the sorted list
		return wordDetails.subList(0, n);
	}

	// Function to sort words. O(n log n), as it sorts the list of words
	// alphabetically using a Comparator, which has a time complexity of O(n log n),
	// and then iterates through the list to display the words, which has a time
	// complexity of O(n).
	private List<String> getSortedWords() {

		System.out.println("Enter the number of words per line: ");
		int wordsPerLine = s.nextInt();

		System.out.println("Enter '1' for ascending order or '2' for descending order: ");
		int sortingOrder = s.nextInt();

		Comparator<String> comparator;
		if (sortingOrder == 1) {
			comparator = Comparator.naturalOrder();
		} else {
			comparator = Comparator.reverseOrder();
		}

		// Get a sorted set of the keys (words) in the index
		Set<String> sortedWords = new TreeSet<>(comparator);
		sortedWords.addAll(index.keySet());

		// Convert the sorted set to a list
		List<String> sortedWordList = new ArrayList<>(sortedWords);

		// Iterate through the list and print out the desired number of words per line
		for (int i = 0; i < sortedWordList.size(); i++) {
			if (i % wordsPerLine == 0) {
				System.out.println();
			}
			System.out.print(sortedWordList.get(i) + " ");
		}

		return sortedWordList;
	}

	// Function to get the total number of unique words, i.e. the index size. O(1),
	// constant time to retrieve the size of the index map.
	private int getTotalUniqueWords() {
		System.out.println("Total Unique Words " + index.size());
		return index.size();
	}

	// Function to search for user input word. Converts input to lower case. O(1),
	// constant time to retrieve a value from the index map using a key.
	private void searchForWord() {
		System.out.println("Enter the word you wish to search for: ");
		String word = s.nextLine().toLowerCase();
		WordDetail wordDetail = index.get(word);
		if (wordDetail != null) {
			System.out.println("Word: " + wordDetail.getWord());
			System.out.println("Definition: " + wordDetail.getDefinition());
			System.out.println("Page numbers: " + wordDetail.getPages());
		} else {
			System.out.println("Word not found in index.");
		}
	}

	// Function to quit application and disable display. O(1), constant time to
	// assign a new value to a variable.
	private void quit() {
		System.out.println("Quitting");
		keepRunning = false;
	}

}
