import java.io.IOException;

public interface BirdDataInterface {

   /**
    * This is a string separator is in between two separate entries for the
    * input files. For consistency you must also insert this separator in
    * between different entries for the output file. The exact layout of your
    * output file will be described in the project write up.
    */
   public static final String SEPERATOR = ",";

   /**
    * This method reads a file with file name: inputFile associated with
    * birdName and parses all of the data in the file and stores it in 4
    * different LinkedLists. The program must keep track of these lists
    * internally as an external could request access to these data structures
    * via getter methods.
    * 
    * @param - birdName the name of the bird the program is currently reading.
    * @param - inputFile the name of the file to read the input data from. The
    *        file should be in current directory or specify a full path.
    * @throws - IOException if any File IO operations fail
    * @throws - InvalidDataException if any data set does not conform to the
    *         specified formats
    */
   public void readInput(String birdName, String inputFile) throws IOException,
            InvalidDataException;

   /**
    * Uses the data that was read in and stored in the LinkedLists to generate a
    * formatted output file as specified in your assignment write up.
    * 
    * @param - destFileName the name of the output file to use
    * @throws - IOException if any File IO operations fail
    * @throws - InvalidDataException if the data stored in the LinkedList is
    *         non-existent or corrupted
    */
   public void generateOutput(String destFileName) throws IOException,
            InvalidDataException;

   /**
    * transforms a calendar day to a week number
    * 
    * @param year
    * @param month
    * @param day
    * @return - a number ranging from 0 to 52 inclusive indicating the week
    *         number of the user's date
    */
   public int weekOfYear(int year, int month, int day);

   /**
    * 
    * @return - the LinkedList containing all of the latitudes read from the
    *         input file
    */
   public LinkedList getLatitudes();

   /**
    * 
    * @return - the LinkedList containing all of the years read from the input
    *         file
    */
   public LinkedList getYears();

   /**
    * 
    * @return - the LinkedList containing all of the weeks read from the input
    *         file
    */
   public LinkedList getWeeks();

   /**
    * 
    * @return - the LinkedList containing all of the counts read from the input
    *         file
    */
   public LinkedList getCounts();

   /**
    * 
    * @return - the name of the bird, null if no birdName has been instantiated
    */
   public String getBirdName();

}
