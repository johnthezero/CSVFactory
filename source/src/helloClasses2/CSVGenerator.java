package helloClasses2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author JonTheZero!!!
 */
public class CSVGenerator {
		//
		public String[] fields={"First Name","Last Name","Reason","Department","Date"};
		public String[] departments={"Cardiology","Radiology","Pediatrics","Geriatrics","Pulmonology"};
		public String[] lastnames= {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", 
				"Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", 
				"Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", 
				"Hill", "Scott", "Green", "Adams", "Baker", "Nelson", "Turner", "Mitchell"};
		public String[] firstnames= {"Emma", "Liam", "Olivia", "Noah", "Ava", "Isabella", "Sophia", "Jackson", "Mia", "Lucas", "Aiden",
				"Elijah", "Harper", "Benjamin", "Abigail", "Ethan", "Emily", "James", "Avery", "Sofia", "Daniel", "Madison", 
				"Henry", "Amelia","Alexander", "Ella", "Matthew", "Scarlett", "David", "Chloe", "Joseph", "Lily", "Samuel", 
				"Grace", "Carter", "Lillian", "Owen","Charlotte", "Wyatt", "Aubrey"};
		public String[] reasons= {"Appointment","Visit"};
		public ArrayList<CSVEntry> entries;
		public CSVGenerator() {
			entries=new ArrayList<CSVEntry>();
		}
		public CSVFile generateCSVFile(int numberOfEntries,int year) {
			CSVFile csvFile=new CSVFile();
			for(int i=0;i<numberOfEntries;i++) {
				csvFile.addEntry(generateEntry(year));
			}
			return csvFile;
		}
		public CSVEntry generateEntry() {
			String firstname=getRandomItem(firstnames);
			String lastname=getRandomItem(lastnames);
			String reason=getRandomItem(reasons);
			String department=getRandomItem(departments);
			LocalDate date=LocalDate.of(2023,12,31).minusDays(getRandomIntRange(0,365));
			return new CSVEntry(firstname,lastname,department,reason,date);
		}
		public CSVEntry generateEntry(int year) {
			String firstname=getRandomItem(firstnames);
			String lastname=getRandomItem(lastnames);
			String reason=getRandomItem(reasons);
			String department=getRandomItem(departments);
			int range=365;
			if(isLeapYear(year)) {
				range=366;
			}
			LocalDate date=LocalDate.of(year+1,1,1).minusDays(getRandomIntRange(0,range));
			return new CSVEntry(firstname,lastname,department,reason,date);
		}
		private int getRandomIntRange(int min,int max) {
			return new Random().nextInt(max-min)+1;
		}
		private String getRandomItem(String[] items) {
			int index=new Random().nextInt(items.length);
			return items[index];
		}
		private boolean isLeapYear(int year) {		
				    boolean isLeapYear;
				    isLeapYear = (year % 4 == 0);
				    isLeapYear = isLeapYear && (year % 100 != 0 || year % 400 == 0);	  
				return isLeapYear;
		}
	public ArrayList<CSVEntry> getEntries(){
		return this.entries;
	}
}

