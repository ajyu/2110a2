import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 * A2: 20-Sept-2011
 * Ansha Yu | ay226 | 2392034
 */

public class MigrationStatistics implements MigrationInterface {

	public static void main(String[] args){
		MigrationStatistics ms= new MigrationStatistics();
		if (Integer.parseInt(args[0])==1) ms.readWriteAllBirds();
		PointDouble[] pd;
		Regression reg= new Regression();
		try {
			pd= ms.generatePoints(args[1],Integer.parseInt(args[2]),Boolean.parseBoolean(args[3]));
			reg.regress(pd);
			Double pdSlope= reg.getSlope();
			Double pdIntercept= reg.getIntercept();
			System.out.println("RMSD: "+reg.getRMSD());
			System.out.println("Slope: "+reg.getSlope());
			System.out.println("Intercept: "+reg.getIntercept());
			ms.plotGraph(args[1], pd, pdSlope, pdIntercept, Boolean.parseBoolean(args[3]));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private double yMin= Double.MAX_VALUE; //the minimum latitude values of this set of MigrationStatistics
	private double yMax= 0.0; //the maximum latitude values of this set of MigrationStatistics
	
	@Override
	public void readWriteAllBirds() {
		BirdDataParser bdp= new BirdDataParser();
		for (Birds b: Birds.values()) {
			try {
				bdp.readInput(b.getName(),b.getName()+".txt");
				bdp.generateOutput(b.getName()+"_out.txt");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Override
	public PointDouble[] generatePoints(String birdName, int index,
			boolean oneYear) throws InvalidDataException,
			IllegalArgumentException {
		
		String bn= birdName;
		
		BufferedReader br1;
		File file= new File(bn+"_out.txt");
		if (!file.canRead() || !file.exists()) throw new InvalidDataException();
		try {
			br1= new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new InvalidDataException("File does not exist or cannot be read");
		}
		
		String s="";
		ArrayList<PointDouble> pd= new ArrayList<PointDouble>();
		
		if (oneYear) {
			if (index-DATASET_BASE_YEAR+1 > YEARS_IN_DATASET || index-DATASET_BASE_YEAR < 0) 
				throw new IllegalArgumentException("Index out of range of dataset");
			for (int i=0; i<=index-DATASET_BASE_YEAR; i++) {
				try {
					s= br1.readLine();
				} catch (IOException e) {
					throw new InvalidDataException("File cannot be read correctly");
				}
			}
			if (s==null) throw new IllegalArgumentException("Index out of range of dataset");
			String[] dataTemp= s.split(BirdDataInterface.SEPERATOR);
			if (dataTemp.length != WEEKS_IN_YEAR) throw new InvalidDataException("Index out of range of dataset");
			for (int i=0; i<dataTemp.length; i++) {
				if (!dataTemp[i].equals("NaN")) {
					double d= Double.parseDouble(dataTemp[i]);
					if (d > yMax) yMax= d;
					if (d < yMin) yMin= d;
					pd.add(new PointDouble((double)i,d));
				}
			}
		} else {
			if (index+1>WEEKS_IN_YEAR || index<0) throw new IllegalArgumentException("Index out of range of dataset");
			try {
				for (int i=0; (s=br1.readLine()) != null && i<YEARS_IN_DATASET; i++) {
					String[] dataTemp= s.split(BirdDataInterface.SEPERATOR);
					if (!dataTemp[index].equals("NaN")) {
						double d= Double.parseDouble(dataTemp[index]);
						if (d > yMax) yMax= d;
						if (d < yMin) yMin= d;
						pd.add(new PointDouble((double)i+DATASET_BASE_YEAR,d));
					}
				}
			} catch (IOException e) {
				throw new InvalidDataException("Data cannot be read correctly");
			}
		}
		return pd.toArray(new PointDouble[pd.size()]);
		
	}

	@Override
	public void plotGraph(String name, PointDouble[] points, double slope,
			double yInt, boolean oneYear) {
		Graph graph;
		if (oneYear) {
			graph= new Graph(name,0.0,(double)WEEKS_IN_YEAR,yMin,yMax,(double)WEEKS_IN_YEAR/10,(yMax-yMin)/10);
		} else {
			graph= new Graph(name,(double)(DATASET_BASE_YEAR),(double)(DATASET_BASE_YEAR+YEARS_IN_DATASET),yMin,yMax,(double)YEARS_IN_DATASET/10,(yMax-yMin)/10);
		}
		graph.addPoints(points);
		
		graph.addLine(slope, yInt);
		
		graph.showGraph();
	}

}
