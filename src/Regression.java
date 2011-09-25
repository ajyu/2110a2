/*
 * A2: 20-Sept-2011
 * Ansha Yu | ay226 | 2392034
 */

public class Regression implements RegressionInterface {
	private double slope; // the slope of this Regression
	private double intercept; //the y intercept of this Regression
	private double rmsd; // the root mean squared deviation of this Regression
	private boolean regressed; // whether the data of this Regression has been regressed
	
	
	public static void main(String[] args){
		Regression reg= new Regression();
		PointDouble pd1= new PointDouble(Double.parseDouble(args[0]),Double.parseDouble(args[1]));
		PointDouble pd2= new PointDouble(Double.parseDouble(args[2]),Double.parseDouble(args[3]));
		PointDouble pd3= new PointDouble(Double.parseDouble(args[4]),Double.parseDouble(args[5]));
		PointDouble[] pd= {pd1,pd2,pd3};
		reg.regress(pd);
		try {
			System.out.println(reg.getSlope());
			System.out.println(reg.getIntercept());
			System.out.println(reg.getRMSD());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	@Override
	public void regress(PointDouble[] points) {
		if (points.length==0) return;
		double xybar=0.0;
		double xbar=0.0;
		double ybar=0.0;
		double xsquaredbar=0.0;
		double rmsdnum=0.0;
		double[] xlist= new double[points.length];
		double[] ylist= new double[points.length];
		
		for (int i=0; i<points.length; i++) {
			double x= points[i].getX();
			double y= points[i].getY();
			xlist[i]= x;
			ylist[i]= y;
			xybar= xybar + x*y;
			xbar= xbar + x;
			ybar= ybar + y;
			xsquaredbar = xsquaredbar + Math.pow(x,2);
		}
		
		xybar= xybar/points.length;
		xbar= xbar/points.length;
		ybar= ybar/points.length;
		xsquaredbar= xsquaredbar/points.length;
		
		slope= (xybar-xbar*ybar)/(xsquaredbar-Math.pow(xbar,2));
		intercept= (ybar-slope*xbar);
		
		for (int i=0; i<points.length; i++) {
			double x= xlist[i];
			double y= ylist[i];
			rmsdnum= rmsdnum + Math.pow(y - (slope*x+intercept),2);
		}
		
		rmsd= Math.pow((rmsdnum)/points.length,.5);
		regressed= true;
	}

	@Override
	public double getSlope() throws InvalidDataException {
		if (regressed==false) throw new InvalidDataException("Regress data first");
		else return slope;
	}

	@Override
	public double getIntercept() throws InvalidDataException {
		if (regressed==false) throw new InvalidDataException("Regress data first");
		else return intercept;
	}

	@Override
	public double getRMSD() throws InvalidDataException {
		if (regressed==false) throw new InvalidDataException("Regress data first");
		else return rmsd;
	}

}
