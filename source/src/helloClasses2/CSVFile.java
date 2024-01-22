package helloClasses2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author JonTheZero!!!
 */

public class CSVFile {
	private String fullPath="";
	private ArrayList<CSVEntry> entryList;
	public CSVFile() {
		entryList=new ArrayList<CSVEntry>();
	}
	public CSVFile(ArrayList<CSVEntry>entries) {
		this();
		entryList=entries;
	}
	//WTF ???
	public CSVFile(String path) {
		this();
		CSVReader reader=new CSVReader(path);
		for(int i=0;i<reader.getCSV().length;i++) {
			//entryList.add(reader.getCSV()[i]);
		}
	}
	public CSVFile(String[][]csvArray) {
		this();
		
	}
	public void addEntry(CSVEntry entry) {
		entryList.add(entry);
	}

	public boolean createCSVFile() {
		boolean isCreated=false;
		System.out.println(new File("\\csvfile\\csvfile.csv").getAbsolutePath());
		if(new File("\\csvfile\\csvfile.csv").exists()) {
			System.out.println("File exists");
			isCreated=true;
		}else {
			File directory=new File("csvfile");
			directory.mkdir();
			File csvFile=new File("csvfile.csv");
			this.fullPath=directory.getName()+"\\csvfile.csv";
			/*try {
				csvFile.createNewFile();
				isCreated=true;
				System.out.println("Created !!");
			} catch (IOException e) {
				isCreated=false;
				e.printStackTrace();
			}*/
		}
		
		
		return isCreated;
	}
	public String getFullPath() {
		return fullPath;
	}
	public static void writeFile(String path,ArrayList<CSVEntry>listOfEntries) {
		try {
			FileWriter fw=new FileWriter(path);
			BufferedWriter bw=new BufferedWriter(fw);
			System.out.println("ENtries :"+listOfEntries.size());
			for(int i=0;i<listOfEntries.size();i++) {
				CSVEntry current=listOfEntries.get(i);
					for(int j=0;j<current.getLength();j++) {
						bw.write(current.get(j));
						if(j!=current.getLength()-1) {
							bw.write(",");
						}else {
							bw.newLine();
						}
					}
			}
			bw.close();
			System.out.println("@writeFile : Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public String toString() {
		String str="";
		for(CSVEntry c :this.entryList) {
			str=str.concat(c.toString());
		}
		return str;
	}
	public static ArrayList<CSVEntry>readFile(String path){
		String line="";
		String[] currentLine=null;
		ArrayList<CSVEntry>entries=new ArrayList<CSVEntry>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(path));
			while((line=reader.readLine())!=null) {
				currentLine=line.split(",");
				int i=0;
				int day=Integer.valueOf(currentLine[4].substring(0,2));
				int month=Integer.valueOf(currentLine[4].substring(3,5));
				int year=Integer.valueOf(currentLine[4].substring(6,10));
				CSVEntry entry=new CSVEntry(currentLine[i++],currentLine[i++],currentLine[i++],currentLine[i++],LocalDate.of(year,month,day));
				entries.add(entry);
			}
			if(reader!=null) {
				reader.close();
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}catch(IOException e){
			
		}
		return entries;
	}
	public ArrayList<CSVEntry> getEntryList(){
		return this.entryList;
	}
}
