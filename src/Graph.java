import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Graph extends JComponent {

   private final int y_BORDER_SIZE = 40;
   private final int x_BORDER_SIZE;
   private final int RADIUS = 3;
   private JFrame graphFrame;
   private double minX, maxX, minY, maxY, doubleWidth, doubleHeight, xScale,
            yScale;
   private int width, height;
   // Stores all of the points the user wants to plot, uses lazy evaluation
   private ArrayList<Point> points = new ArrayList<Point>(52);
   // Stores all of the lines the user wants to plot, also uses lazy evaluation
   private ArrayList<PointDouble> lines = new ArrayList<PointDouble>();

   /**
    * Constructor for instantiating a new graph class. The coordinates are used
    * for the graph to decide the range of x and y to display so the user should
    * always choose tightest bounds with a small boarder to make the graph
    * display the most amount of information.
    * 
    * @param graphName
    *           the name of the graph
    * @param minX
    *           the minimum coordinate for the x-axis
    * @param maxX
    *           the maximum coordinate for the x-axis
    * @param minY
    *           the minimum coordinate for the y-axis
    * @param maxY
    *           the maximum coordinate for the y-axis
    * @param xScale
    *           the amount to increment in the x-axis before drawing a grid line
    *           and displaying a x value on the axis
    * @param yScale
    *           the amount to increment in the y-axis before drawing a grid line
    *           and displaying the y value on the axis
    */
   public Graph(String graphName, double minX, double maxX, double minY,
                double maxY, double xScale, double yScale) {
      this.graphFrame = new JFrame(graphName);
      this.x_BORDER_SIZE = Math.max(Double.toString(minY).length(), Double
               .toString(maxY).length()) * 5;
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;
      this.xScale = xScale;
      this.yScale = yScale;
      this.doubleWidth = maxX - minX;
      this.doubleHeight = maxY - minY;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.width = (int)(screenSize.width * 0.75) - x_BORDER_SIZE;
      this.height = (int)(screenSize.height * 0.75) - y_BORDER_SIZE;
      this.graphFrame.setSize(this.width + x_BORDER_SIZE, this.height
               + y_BORDER_SIZE);
      this.graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.graphFrame.setResizable(false);
      this.graphFrame.add(this);
   }

   /**
    * Users can call this method to add new points to the plot for this graph.
    * Once a point is added, it cannot be removed from the graph. The graph
    * class uses lazy evaluation for all plotting functions so nothing will show
    * up until a showGraph() method is called.
    * 
    * @param newPoints
    *           the array of new points to add to the graph
    */
   public void addPoints(PointDouble[] newPoints) {
      for (PointDouble curPoint : newPoints) {
         if (testRange(curPoint.getX(), curPoint.getY())) {
            this.points.add(new Point(transformX(curPoint.getX()) - RADIUS,
                     transformY(curPoint.getY()) - RADIUS));
         } else {
            throw new IllegalArgumentException("Coordinates for point ("
                     + curPoint.getX() + "," + curPoint.getY()
                     + ") is out of range.");
         }
      }
   }

   /**
    * Adds a new linear line to the graph. The graph expects the line to be in
    * the form of y = mx + b where m corresponds to the slope and b corresponds
    * to the y-intercept of the line. The graph class uses lazy evaluation for
    * all plotting functions so nothing will show up until a showGraph() method
    * is called.
    * 
    * @param slope
    *           the slope of the line
    * @param yIntercept
    *           the y-intercept of the line
    */
   public void addLine(double slope, double yIntercept) {
      this.lines.add(new PointDouble(slope, yIntercept));
   }

   /**
    * this methods shows the graph with all of the current points and lines that
    * the user added.
    */
   public void showGraph() {
      this.repaint();
      this.graphFrame.setBackground(Color.WHITE);
      this.graphFrame.setVisible(true);
   }

   private int transformX(double x) {
      int newX = (int)Math.round(((x - this.minX) / this.doubleWidth)
               * this.width);
      return newX + x_BORDER_SIZE;
   }

   private int transformY(double y) {
      int newY = (int)Math.round(((y - this.minY) / this.doubleHeight)
               * this.height);

      return this.height - newY;
   }

   private boolean testRange(double x, double y) {
      if (x < this.minX || x > this.maxX) {
         return false;
      }
      if (y < this.minY || y > this.maxY) {
         return false;
      }
      return true;

   }

   @Override
   public void paintComponent(Graphics g) {
      // Draws the vertical grid lines plus the horizontal scale numbers
      for (int i = 0; i <= this.doubleWidth / this.xScale; ++i) {
         double value = minX + xScale * i;
         int curX = (int)(((value - this.minX) / this.doubleWidth) * this.width)
                  + x_BORDER_SIZE;
         int curY = this.height + y_BORDER_SIZE;
         g.drawLine(curX, curY - y_BORDER_SIZE, curX, 0);
         g.drawString(String.format("% d", (int)value), curX - 15, curY - 25);
      }
      // Draws the horizontal grid lines plus the vertical scale numbers
      g.drawLine(x_BORDER_SIZE, this.height, this.width + x_BORDER_SIZE,
               this.height);
      for (int i = (int)(this.doubleHeight / this.yScale); i > 0; --i) {
         double value = minY + yScale * i;
         int curY = (int)(this.height - ((value - this.minY) / this.doubleHeight)
                  * this.height);
         int curX = x_BORDER_SIZE;
         g.drawLine(curX, curY, this.width + x_BORDER_SIZE, curY);
         g.drawString(String.format("% .1f", value), 3, curY + 5);
      }
      // Plots the user points on the graph
      for (Point p : points) {
         g.fillOval((int)p.getX(), (int)p.getY(), 2 * RADIUS, 2 * RADIUS);
      }
      // Plots the user lines on the graph
      Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA,
               Color.ORANGE };
      int i = 0;
      for (PointDouble doublePoint : lines) {
         double slope = doublePoint.getX();
         double yInt = doublePoint.getY();
         double x1, y1, x2, y2, reference1, reference2;
         // Calculate the left end point of the line
         // Case 1: it lies on the right vertical axis
         if (slope != 0) {
            if (slope > 0) {
               reference1 = this.minX;
               reference2 = this.maxX;
            } else {
               reference1 = this.maxX;
               reference2 = this.minX;
            }
            // Calculate the first end point
            double tempY = slope * reference1 + yInt;
            if (testRange(reference1, tempY)) {
               x1 = reference1;
               y1 = tempY;
            } else {
               // If it is out of range of the side, check the bottom axis
               x1 = (this.minY - yInt) / slope;
               y1 = this.minY;
            }

            // Calculate the 2nd end point
            tempY = slope * reference2 + yInt;
            if (testRange(reference2, tempY)) {
               x2 = reference2;
               y2 = tempY;
            } else {
               // If it is out of range of the side, check the top axis
               x2 = (this.maxY - yInt) / slope;
               y2 = this.maxY;
            }
         } else {
            x1 = this.minX;
            y1 = yInt;
            x2 = this.maxX;
            y2 = yInt;
         }
         if (testRange(x1, y1) && testRange(x2, y2)) {
            g.setColor(colors[i % colors.length]);
            g.drawLine(transformX(x1), transformY(y1), transformX(x2),
                     transformY(y2));
            i++;
         }
      }
   }
}
