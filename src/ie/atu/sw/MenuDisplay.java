package ie.atu.sw;

public class MenuDisplay {

	public void displayOptions() {

		// You should put the following code into a menu or Menu class
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*              Virtual Threaded Text Indexer               *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify URL or Text File Directory Path");
		System.out.println("(2) Specify Dictionary File Directory Path");
		System.out.println("(3) Specify Word Ommitter File Directory Path");
		System.out.println("(4) Specify Output File Directory Name & Path");
		System.out.println("(5) Execute");
		System.out.println("(6) Display Most Frequent/Infrequent Words");
		System.out.println("(7) Display Sorted Words");
		System.out.println("(8) Display Total Unique Words");
		System.out.println("(9) Search for Word");
		System.out.println("(10) Quit");

		// Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-10]>");
		System.out.println();

	}

}
