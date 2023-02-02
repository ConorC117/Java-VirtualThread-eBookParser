package ie.atu.sw;

import java.io.PrintWriter;

import java.util.concurrent.ConcurrentMap;

/**
 * The {@code IndexOutputter} class is responsible for writing the index to a
 * file.
 */
public class IndexOutputter {

	/**
	 * Creates a new {@code IndexOutputter} instance with the specified output file
	 * path.
	 * 
	 * @param output the output file path
	 */
	public IndexOutputter(String output) {

	}

	/**
	 * Writes the index to a file.
	 * 
	 * @param index the index to write to the file
	 * @param file  the file path to write the index to
	 * 
	 * @throws Exception if an error occurs while writing the index to the file
	 */
	// O(n), where n is the number of words in the index. This is because the
	// function loops through the index map and performs a constant number of
	// operations (writing the word, definition, and pages to the file) on each
	// element in the map.
	public void writeIndex(ConcurrentMap<String, WordDetail> index, String file) throws Exception {

		// create a new PrintWriter with the file path specified in the argument
		PrintWriter writer = new PrintWriter(file);

		// iterate through the index map and write each word, definition, and pages to
		// the file
		for (String key : index.keySet()) {
			WordDetail detail = index.get(key);
			writer.println(detail.getWord() + "," + detail.getDefinition() + ",Pages:" + detail.getPages());
		}

		// close the writer to save the file
		writer.close();
	}

}
