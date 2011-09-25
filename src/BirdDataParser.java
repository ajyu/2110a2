/*
 * A2: 20-Sept-2011
 * Ansha Yu | ay226 | 2392034
 */

import java.io.*;
import java.util.Calendar;


public class BirdDataParser implements BirdDataInterface {

	public static void main(String[] args){
		BirdDataParser bdp= new BirdDataParser();
		try{
		if (args[0].equals("readInput")) bdp.readInput(args[1], args[2]);
		if (args[0].equals("generateOutput")) {
			bdp.readInput(args[1], args[2]);
			bdp.generateOutput(args[3]);
		}
		if (args[0].equals("weekOfYear")) System.out.println(bdp.weekOfYear(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	DoubleList latitudes= new DoubleList(); //DoubleList used to store latitudes
	DoubleList years= new DoubleList(); //DoubleList used to store years
	DoubleList weeks= new DoubleList(); //DoubleList used to store weeks
	DoubleList counts= new DoubleList(); //DoubleList used to store counts
	String bn; //The birdname of the input of this BirdDataParser
	
	@Override
	public void readInput(String birdName, String inputFile)
			throws IOException, InvalidDataException {
		bn= birdName;
		BufferedReader br1;
		BufferedReader br2;
		try {
			File file= new File(inputFile);
			if (!file.canRead() || !file.exists()) throw new IOException("File does not exist or cannot be read");
			br1= new BufferedReader(new FileReader(file));
			br2= new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			throw new IOException("File does not exist or cannot be read");
		}
		
		int numLines= 0;
		String s;
		while ((s= br1.readLine()) != null) {
			numLines= numLines+1;
		}
		double[] latData= new double[numLines];
		double[] yrsData= new double[numLines];
		double[] wksData= new double[numLines];
		double[] cntData= new double[numLines];
		int lineCounter= 0;
		
		while ((s= br2.readLine()) != null) {
			String[] sep= s.split(SEPERATOR);
			double lineData[]= new double[4];
			if (sep.length != 5) throw new InvalidDataException("Data cannot be read correctly");
			try {
				lineData[0]= Double.parseDouble(sep[0]);
				lineData[1]= Double.parseDouble(sep[1]);
				lineData[2]= (double)weekOfYear(Integer.parseInt(sep[1]),Integer.parseInt(sep[2]),Integer.parseInt(sep[3]));
				lineData[3]= Double.parseDouble(sep[4]);
				} catch (NumberFormatException n) {
					throw new InvalidDataException("Data cannot be read correctly");
				}
			latData[lineCounter]=lineData[0];
			yrsData[lineCounter]=lineData[1];
			wksData[lineCounter]=lineData[2];
			cntData[lineCounter]=lineData[3];
			lineCounter= lineCounter+1;
		}
		latitudes.addAll(latData);
		years.addAll(yrsData);
		weeks.addAll(wksData);
		counts.addAll(cntData);
		
	}
	
	@Override
	public void generateOutput(String destFileName) throws IOException,
			InvalidDataException {
		if (latitudes.size()!= years.size() || years.size()!=weeks.size() || weeks.size()!=counts.size()){
			throw new InvalidDataException("Invalid input data file");
		}
		DoubleList lat= latitudes;
		DoubleList yrs= years;
		DoubleList wks= weeks;
		DoubleList cts= counts;
		double[][][] data= new double[MigrationStatistics.YEARS_IN_DATASET][MigrationStatistics.WEEKS_IN_YEAR][2];
		for (int i=0; i<latitudes.size(); i=i+1) {
			try {
				double l= lat.remove(0);
				int y= (int)yrs.remove(0)-MigrationStatistics.DATASET_BASE_YEAR;
				int w= (int)wks.remove(0);
				int c= (int)cts.remove(0);
				data[y][w][0]= data[y][w][0]+ l*c;
				data[y][w][1]= data[y][w][1]+ c;
			} catch (Exception e) {
				throw new InvalidDataException("Data cannot be read correctly");
			}
		}
		try {
			FileWriter file= new FileWriter(destFileName);
			BufferedWriter bw= new BufferedWriter(file);
			for (int i=0; i<=MigrationStatistics.YEARS_IN_DATASET-1; i=i+1) {
				String s= "";
				for (int j=0; j<MigrationStatistics.WEEKS_IN_YEAR; j=j+1) {
					s= s + (s.equals("") ? "": SEPERATOR) + ((data[i][j][1])!=0 ? data[i][j][0] / data[i][j][1] : "NaN");
				}
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			throw new IOException("Error writing file: check filename");
		}
	}

	@Override
	public int weekOfYear(int year, int month, int day) {
		Calendar cal;
		try {
			cal= Calendar.getInstance();
			cal.set(year, month-1, day);
		} catch (Exception e) {
			return 0;
		}
		int week= cal.get(Calendar.WEEK_OF_YEAR)-1;
		if (week==0 && cal.get(Calendar.MONTH)==11) week= 52;
		return week;
	}

	@Override
	public LinkedList getLatitudes() {
		return (LinkedList)latitudes;
	}

	@Override
	public LinkedList getYears() {
		return (LinkedList)years;
	}

	@Override
	public LinkedList getWeeks() {
		return (LinkedList)weeks;
	}

	@Override
	public LinkedList getCounts() {
		return (LinkedList)counts;
	}

	@Override
	public String getBirdName() {
		return bn;
	}

}
