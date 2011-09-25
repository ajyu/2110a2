import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Testing {

	public static void main(String[] args) {
		threec();
		threee();
		threef();
	}
	
	public static void threec() {
		MigrationStatistics ms= new MigrationStatistics();
		Double slope= 0.0;
		int weeknumber=0;
		int wkspos=0;
		int wksneg=0;
		for (int i=0; i<MigrationStatistics.WEEKS_IN_YEAR; i++) {
			try {
				Regression reg= new Regression();
				reg.regress(ms.generatePoints("AmericanRobin", i, false));
				double d= reg.getSlope();
				if (d> slope) {
					slope= d;
					weeknumber= i;
				}
				if (d>0) wkspos= wkspos+1;
				if (d<0) wksneg= wksneg+1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("American Robin statistics:");
		System.out.println("Week w/ largest positive slope: "+weeknumber+" (Slope= "+slope+")");
		System.out.println("Number of weeks w/ positive slope: "+wkspos);
		System.out.println("Number of wks w/ neg slope: "+wksneg);
	}
	
	public static void threee() {
		BufferedReader br1 = null;
		File file= new File("AmericanRobin_out.txt");
		try {
		br1= new BufferedReader(new FileReader(file));
		} catch (Exception e) {System.out.println("new file error");}
		String s="";
		PointDouble[] pd= new PointDouble[MigrationStatistics.YEARS_IN_DATASET];
		Double dmax=0.0;
		Double dmin= Double.MAX_VALUE;
		try {
		for (int i=0; (s=br1.readLine())!=null; i++) {
			double d=0.0;
			String[] dataTemp= s.split(BirdDataInterface.SEPERATOR);
			int k=0;
			for (int j=0; j<dataTemp.length; j++) {
				if (!dataTemp[j].equals("NaN")) {
					d= d+Double.parseDouble(dataTemp[j]);
					k=k+1;
				}
			}
			d=d/k;
			if (d>dmax) dmax= d;
			if (d<dmin) dmin= d;
			pd[i]= new PointDouble(i+1990,d);
		}
		} catch (Exception e) {System.out.println("read error");}
		Regression reg= new Regression();
		reg.regress(pd);
		Graph graph= new Graph("AmericanRobin",1990,2010,35,45,2,2);
		graph.addPoints(pd);
		try{
		graph.addLine(reg.getSlope(), reg.getIntercept());
		} catch (Exception e) {System.out.println("Slope/intercept error");}
		graph.showGraph();
	}
	
	public static void threef() {
		BufferedReader br1 = null;
		File file= new File("AmericanRobin_out.txt");
		try {
		br1= new BufferedReader(new FileReader(file));
		} catch (Exception e) {System.out.println("new file error");}
		String s="";
		PointDouble[] pd= new PointDouble[MigrationStatistics.YEARS_IN_DATASET];
		try {
		for (int i=0; (s=br1.readLine())!=null; i++) {
			ArrayList<PointDouble> pdTemp= new ArrayList<PointDouble>();
			String[] dataTemp= s.split(BirdDataInterface.SEPERATOR);
			if (i!=2 && i!=17 && i!=19) {
			for (int j=0; pdTemp.size()<5; j++) {
				if (!dataTemp[i].equals("NaN")) {
					double d= Double.parseDouble(dataTemp[j]);
					if (pdTemp.isEmpty()) {
						pdTemp.add(new PointDouble((double)j,d));
					} else if (d>pdTemp.get(pdTemp.size()-1).getY()) {
						pdTemp.add(new PointDouble((double)j,d));
					} else pdTemp.clear();
				} else {
					if ( !pdTemp.isEmpty() && (double)j+1 == pdTemp.get(pdTemp.size()-1).getX()) {
						pdTemp.add(new PointDouble((double)j,pdTemp.get(pdTemp.size()-1).getY()));
					} else pdTemp.clear();
				}
			}
			pd[i]= new PointDouble((double)i+1990,pdTemp.get(0).getX());
			} else {
				
				pd[i]= new PointDouble((double)i+1990,0.0);
			}
		}
		} catch (Exception e) {System.out.println("read error");}
		PointDouble[] pd1= pd;
		pd1[17]= new PointDouble(2007.0,11.9);
		pd1[19]= new PointDouble(2009.0,12.0);
		Regression reg= new Regression();
		reg.regress(pd);
		Graph graph= new Graph("AmericanRobin First Migration Weeks",1990,2010,0,20,2,4);
		graph.addPoints(pd);
		try{
		graph.addLine(reg.getSlope(), reg.getIntercept());
		} catch (Exception e) {System.out.println("Slope/intercept error");}
		graph.showGraph();
	}

}
