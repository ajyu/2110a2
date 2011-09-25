public interface MigrationInterface {
   // Number of years in the data set
   public static final int YEARS_IN_DATASET = 20;
   // Number of weeks in a year
   public static final int WEEKS_IN_YEAR = 53;
   // The lowest year in the data set
   public static final int DATASET_BASE_YEAR = 1990;

   // This is an enumerable type of all the bird names, it can be conveniently
   // used for accessing files
   enum Birds {
      ROBIN("AmericanRobin"), GOOSE("CanadaGoose"), MARTIN("PurpleMartin"),
      HUMMINGBIRD("RubyThroatedHummingbird"), CRANE("SandhillCrane"), SCAUP("Scaup");

      private final String name;

      Birds(String name) {
         this.name = name;
      }

      String getName() {
         return this.name;
      }
   }

   /**
    * this method reads all the data files for the bird names listed in the enum
    * Birds and generates a corresponding processed file named:
    * [birdName]_out.txt
    */
   public void readWriteAllBirds();

   /**
    * this method reads the your PROCESSED bird data files (that are generated
    * from readWriteAllBirds()) and generates a corresponding array of
    * PointDoubles that will be passed into the Graph class. This method can
    * either generate points showing all the weeks in one given year or focus on
    * one single week for all given years. If the oneYear variable is true, your
    * points include ALL the weeks in the year number=index. If the oneYear
    * variable is false, your points should include only the week number=index
    * for all years in the data set.
    * 
    * @param birdName
    *           - the name of the bird to study
    * @param index
    *           - the week number or the year number to focus on
    * @param oneYear
    *           - true means generating points for 52 weeks in 1 year, false
    *           means generating points for 1 week over a number of years
    * @return - an array of points in the form of (x,y) used for the graphing
    *         function
    * @throws InvalidDataException
    *            - if the processed data files is not in the correct format
    * @throws IllegalArgumentException
    *            - if the index for week/year is not in bounds
    */
   public PointDouble[] generatePoints(String birdName, int index,
            boolean oneYear) throws InvalidDataException,
            IllegalArgumentException;

   /**
    * Instantiates a new Graph class and plots a graph with the given points and
    * regression line. This function should determine the best max/min y values
    * to pass into the graphing function such that all your points are displayed
    * in the graph window. This function should also show the graph once it is
    * done initializing it.
    * 
    * @param name
    *           - the name of the graph
    * @param points
    *           - the array of points to plot on the graph
    * @param slope
    *           - the slope of the regression line
    * @param yInt
    *           - the y-intercept of the regression line
    * @param oneYear
    *           - tells the graph whether it is plotting years or weeks so it
    *           can choose the x-axis bounds correctly
    */
   public void plotGraph(String name, PointDouble[] points, double slope,
            double yInt, boolean oneYear);
}
