package helloClasses2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Methods to generate daily and monthly reports
public class ReportGenerator {
	private String[]months= {"January","February","March","April","May","June","July","August","September","October","November","December"};
	private String[] departments={"Cardiology","Radiology","Pediatrics","Geriatrics","Pulmonology"};
	private ArrayList<CSVEntry>entries;
	private ArrayList<CSVEntry>[] arrayOfEntries;
	//private String[] reports;
	public ReportGenerator() {}
	public ReportGenerator(ArrayList<CSVEntry> entries) {
		arrayOfEntries=new ArrayList[12];
		for(int i=0;i<arrayOfEntries.length;i++) {
			arrayOfEntries[i]=new ArrayList<CSVEntry>();
		}
		this.entries=entries;
		//sortEntries();
	}
	public void monthly(String[][] csvFile) {
		return ;
	}
	public void weekly(String[][] csvFile) {
		return;
	}
	public void monthly(ArrayList<CSVEntry> list) {
		return;
	}
	public void setEntries(ArrayList<CSVEntry> entries) {
		this.entries=entries;
	}
	public void sortEntries() {
		for(int i=0;i<entries.size()-1;i++) {
			for(int j=i+1;j<entries.size();j++) {
				if(entries.get(i).getDate().compareTo(entries.get(j).getDate())>0) {
					CSVEntry entry=entries.get(i);
					entries.set(i, entries.get(j));
					entries.set(j,entry);
				}
			}
		}
		fillArrayOfEntries();	
}
	public void sortEntries(ArrayList<CSVEntry> entries) {
			for(int i=0;i<entries.size()-1;i++) {
				for(int j=i+1;j<entries.size();j++) {
					if(entries.get(i).getDate().compareTo(entries.get(j).getDate())>0) {
						CSVEntry entry=entries.get(i);
						entries.set(i, entries.get(j));
						entries.set(j,entry);
					}
				}
			}
			fillArrayOfEntries();	
	}
	
	
	
	
	
	public void createMonthlyFiles() {
			for(int i=0;i<arrayOfEntries.length;i++) {
				CSVFile.writeFile(System.getProperty("user.dir")+"\\csvfile\\"+months[i]+".csv", arrayOfEntries[i]);
			}
		
	}
	
	
	
	
	
	
	
	public void fillArrayOfEntries(){
		for(CSVEntry entry : entries) {
			int index=entry.getDate().getMonthValue()-1;
			arrayOfEntries[index].add(entry);
		}
	}
	public void fillArray() {
		for(CSVEntry entry : entries) {
			arrayOfEntries[(entry.getDate().getMonthValue())-1].add(entry);
		}
	}
	public ArrayList<CSVEntry> getEntries(){
		return this.entries;
	}
	public void printEntries() {
		int index=0;
		for(CSVEntry entry : entries) {
			System.out.println(index+++" : "+entry.toString());
		}
	}
	public void printArrays() {
		for(ArrayList<CSVEntry> list : arrayOfEntries) {
			for(CSVEntry entry : list) {
				System.out.println(entry.toString());
			}
			System.out.println("Appuye sur une touche");
			Scanner sc=new Scanner(System.in);
			String str=sc.nextLine();
		}
	}
	public ArrayList<CSVEntry> getMonthly(int month) {
		ArrayList<CSVEntry> sortedList=new ArrayList<CSVEntry>(); 
		
		for(CSVEntry entry : entries) {
			int index=entry.getDate().getMonthValue();
			arrayOfEntries[index].add(entry);
		}
		return sortedList;
	}
	public ArrayList<CSVEntry>getMonth(int month){
		return arrayOfEntries[month-1];
	}
	public int getMonthlyAppointments(ArrayList<CSVEntry> month) {
		int numberOfVisitors=0;
		for(int i=0;i<month.size();i++) {
			CSVEntry entry=month.get(i);
			if(entry.getReason().equals("Visit")) {
				numberOfVisitors++;
			};
		}
		System.out.println("Monthly appointments : "+numberOfVisitors);
		return numberOfVisitors;
	}
	public ArrayList<CSVEntry> getByReason(String reason,ArrayList<CSVEntry>list) {
		ArrayList<CSVEntry>sortedList=new ArrayList<CSVEntry>();
		for(CSVEntry entry : list) { 
			if(entry.getReason().equals(reason)) {
				sortedList.add(entry);
			}
		}
		return sortedList;
	}
	public ArrayList<CSVEntry> getByDepartment(String department,ArrayList<CSVEntry>list) {
		ArrayList<CSVEntry>sortedList=new ArrayList<CSVEntry>();
		if(department!="All") {
			for(CSVEntry entry : list) {  
				if(entry.getDepartment().equals(department)) {
					sortedList.add(entry);
				}
			}
		}else {
			
		}
		
		return sortedList;
	}
	public ArrayList<CSVEntry> getVisitorsByDepartment(ArrayList<CSVEntry> month,String department) {
		ArrayList<CSVEntry> sortedList=new ArrayList<CSVEntry>();
		for(CSVEntry entry : month ) {
			if(entry.getDepartment().equals(department)&&entry.getReason().equals("Visit")) {
				sortedList.add(entry);
			}
		}
		return sortedList;
	}
	public ArrayList<CSVEntry> getAppointmentsByDepartment(ArrayList<CSVEntry> month,String department) {
		ArrayList<CSVEntry> sortedList=new ArrayList<CSVEntry>();
		for(CSVEntry entry : month ) {
			if(entry.getDepartment().equals(department)&&entry.getReason().equals("Appointment")) {
				sortedList.add(entry);
			}
		}
		return sortedList;
	}
	public int getNumberOfAppointments(ArrayList<CSVEntry> month) {
		int numberOfAppointments=0;
		for(CSVEntry entry : month) {
			if(entry.getReason().equals("Appointment")) {
				numberOfAppointments++;
			}
		}
		return numberOfAppointments;
	}
	public int getNumberOfVisitors(ArrayList<CSVEntry> month) {
		int numberOfVisitors=0;
		for(CSVEntry entry : month) {
			if(entry.getReason().equals("Visit")) {
				numberOfVisitors++;
			}
		}
		return numberOfVisitors;
	}
	//Ne gère pas encore les cas extremes (zero, etc...)
	//Data hardcodée....ca craint , à changer asap si modif des départements
	/*
	 *"Cardiology","Radiology","Pediatrics","Geriatrics","Pulmonology"
	 * 
	 * 0 : monthly people in total
	 * 1 : monthly appointments
	 * 2 : monthly visitors
	 * 3 : monthly visitors of Cardiology
	 * 4 : monthly visitors of Radiology
	 * 5 : monthly visitors of Pediatrics
	 * 6 : monthly visitors of Geriatrics
	 * 7 : monthly visitors of Pulmonology
	 * 8 : monthly appointments of Cardiology
	 * 9 : monthly appointments of Radiology
	 * 10 : monthly appointments of Pediatrics
	 * 11 : monthly appointments of Geriatrics
	 * 12 : monthly appointments of Pulmonology

	 * */
	public ArrayList<CSVEntry>[] getArrayOfEntries(){
		return this.arrayOfEntries;
	}
	public String[] report(ArrayList<CSVEntry>month){
		//String[] reportTitles=""
		String[] reports=new String[13];
		
		String numberOfPeople=Integer.toString(month.size())+" people in total";
		reports[0]=numberOfPeople;
		String numberOfAppointments=Integer.toString(getNumberOfAppointments(month))+" appointments in total";
		reports[1]=numberOfAppointments;
		String numberOfVisitors=Integer.toString(getNumberOfVisitors(month))+" visitors in total";
		reports[2]=numberOfVisitors;
		String[] visitDepts=new String[departments.length];
		String[] appointDepts=new String[departments.length];
		for(int i=0;i<departments.length;i++) {
			visitDepts[i]=Integer.toString(getVisitorsByDepartment(month,departments[i]).size())+" visitors in "+departments[i];
			appointDepts[i]=Integer.toString(getAppointmentsByDepartment(month,departments[i]).size())+"  appointments in "+departments[i];
		}
		//dégueulasse !!!
		for(int i=3;i<8;i++) {
			reports[i]=visitDepts[i-3];
			if(reports[i]==null) {
				reports[i]="0 visitors";
			}
		}
		for(int i=8;i<reports.length;i++) {
			reports[i]=appointDepts[i-8];
			if(reports[i]==null) {
				reports[i]="0 appointments";
			}
		}
		return reports;
	}
}
