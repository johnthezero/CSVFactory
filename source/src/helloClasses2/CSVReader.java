package helloClasses2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader{
	private String path;
	private BufferedReader reader=null;
	private int currentIndex;
	private String[][] csv;
	private ArrayList<CSVEntry> entryList;
	private boolean isCompleted;
	
	public CSVReader() {
		entryList=new ArrayList<CSVEntry>();
	}
	public CSVReader(String path) {
		this();
		this.csv=new String[0][];
		this.currentIndex=0;
		this.path=path;
		String line="";
		String[] currentLine=null; 
		try {
			reader=new BufferedReader(new FileReader(path));
			System.out.println("here");
			while((line=reader.readLine())!=null) {
				currentLine=line.split(",");
				int i=0;
				int day=Integer.valueOf(currentLine[4].substring(0,2));
				int month=Integer.valueOf(currentLine[4].substring(3,5));
				int year=Integer.valueOf(currentLine[4].substring(6,10));
				CSVEntry entry=new CSVEntry(currentLine[i++],currentLine[i++],currentLine[i++],currentLine[i++],LocalDate.of(year,month,day));
				this.entryList.add(entry);
			}
			/*while((line=reader.readLine())!=null) {
				currentLine=line.split(",");
				addLine();
				this.csv[currentIndex++]=currentLine;
			}*/
			isCompleted=true;
		}catch(NullPointerException e) {
			e.printStackTrace();
		}catch(IOException e){
			
		}finally {
			try {
				if(reader!=null) {
					reader.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//change the return type to CSVFile
	public ArrayList<CSVEntry> getCSVFileFromPath(String path) {
		BufferedReader br=null;
		//ArrayList<CSVEntry>list=new ArrayList<CSVEntry>();
		try {
			FileReader fr=new FileReader(new File(path));
			CSVEntry entry;
			br=new BufferedReader(fr);
			while(br.ready()) {
				String str=br.readLine();
				int i=0;
				String[] currentLine=str.split(",");
				int day=Integer.valueOf(currentLine[4].substring(0,2));
				int month=Integer.valueOf(currentLine[4].substring(3,5));
				int year=Integer.valueOf(currentLine[4].substring(6,10));
				entry=new CSVEntry(currentLine[i++],currentLine[i++],currentLine[i++],currentLine[i++],LocalDate.of(year,month,day));
				entryList.add(entry);
			}
			if(br!=null) {
				br.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entryList;
	}
	public String[] getColumnByName(String name) {
		String[] column=null;
		for(int i=0;i<csv.length;i++) {
			if(csv[0][i].equals(name)) {
				return getColumnByIndex(i);
			}
		}
		return column;
	}
	public ArrayList<CSVEntry> getEntries(){
		return this.entryList;
	}
	public String[] getRowByIndex(int rowIndex) {
		return csv[rowIndex];
	}
	public String[] getColumnByIndex(int colIndex) {
		String[] col=new String[csv.length];
		for(int i=0;i<col.length;i++) {
			col[i]=csv[i][colIndex];
		}
		return col;
	}
	public String[][] getCSV() {
		return this.csv;
	}
	public String getFilePath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path=path;
	}
	public void displayCSV() {
		for(String[] row : csv) {
			for(String elem : row) {
				System.out.println(elem);
			}
		}
	}

	private void addLine() {
		this.csv=Arrays.copyOf(getCSV(), csv.length+1);
	}
}
