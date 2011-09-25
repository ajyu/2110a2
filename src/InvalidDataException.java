/**
 * This exception should be thrown whenever a line from text file does not match
 * the expected formatting that was specified in the assignment description. It
 * can also be thrown when the data in memory is corrupted and does not
 * correctly represent the data that was read in from the file.
 */
public class InvalidDataException extends Exception {

   private static final long serialVersionUID = 1L;

   public InvalidDataException() {
      super();
   }

   public InvalidDataException(String msg) {
      super(msg);
   }
}
