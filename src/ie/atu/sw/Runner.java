package ie.atu.sw;

/**
 * @author Conor Cowman
 * @version 1.0
 * @since 19.0.1 The {@code Runner} class is the entry point of the program. It
 *        creates a new instance of the {@link MenuFunctions} class and calls
 *        its {@link MenuFunctions#start()} method to display the menu to users.
 * 
 * @throws Exception if there is an error when reading input or output files
 */
public class Runner {

	/**
	 * 
	 * The main method of the {@code Runner} class is the entry point of the
	 * program. It creates a new instance of the {@link MenuFunctions} class and
	 * calls its {@link MenuFunctions#start()} method to display the menu to users.
	 * 
	 * @param args command line arguments (not used in this program)
	 * @throws Exception if there is an error when reading input or output files
	 */
	public static void main(String[] args) throws Exception {

		// Start new instance of Menufunctions class
		MenuFunctions mf = new MenuFunctions();

		// Call the start method of MenuFunctions, which displays the menu to users
		mf.start();

	}
}