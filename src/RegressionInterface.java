/**
 * Inteface for a class that performs Linear Regression and Root Mean Square
 * Deviation analysis on the data set. The getter methods getSlope(),
 * getIntercept(), and getRMSD() is dependent on regress() being called first.
 * If a getter is called before the value has been computed, it should throw a
 * new InvalidDataException
 */
public interface RegressionInterface {
   /**
    * Takes 2 double arrays and applies the linear regression formula to
    * calculate the slope and y-intercept of the line of best fit
    * 
    * @param points
    *           - the list of points perform regression on
    */
   public void regress(PointDouble[] points);

   /**
    * returns the slope of the line of best fit.
    * 
    * @return - the value of the slope
    * @throws InvalidDataException
    *            - if the value of slope has not been computed yet
    */
   public double getSlope() throws InvalidDataException;

   /**
    * returns the y-intercept of the line of best fit
    * 
    * @return - the value of the y-intercept
    * @throws InvalidDataException
    *            - if the value of the y-intercept has not been computed yet
    */
   public double getIntercept() throws InvalidDataException;

   /**
    * returns the Root Mean Square Deviation of the line of best fit relative to
    * the sample points x_data and y_data. You can find the formula for RMSD in
    * your assignment writeup.
    * 
    * @return - the RMSD value of the line of best fit
    * @throws InvalidDataException
    *            - if the line of best fit has not been computed yet
    */
   public double getRMSD() throws InvalidDataException;
}
