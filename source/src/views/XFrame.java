package views;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import helloClasses2.CSVEntry;
import helloClasses2.CSVFile;
import helloClasses2.CSVGenerator;
import helloClasses2.CSVReader;
import helloClasses2.ReportGenerator;
/**
 * @author JonTheZero!!!
 */

public class XFrame extends JFrame {
	private Container c,west,center,east,north;
	private ListContainer listContainer,reportContainer;
	private JLabel report,listLabel,reportLabel;
	private String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
	private JButton scanFiles,generateButton,next,prev,showFullYear,createFiles,startButton;
	private CSVFile csvFile;
	private JList<String> jlist,reportList;
	private DefaultListModel model,reportModel;
	private MonthContainer monthContainer;
	private boolean CSV_EXISTS=false;
	private boolean[] checked;
	public XFrame() {
		init();
		build();
		setSize(new Dimension(1000,800));
		setVisible(true);
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("John's CSV Factory!");
	}
	public CSVFile getCSVFile() {
		return this.csvFile;
	}
	private void build() {

		monthContainer=new MonthContainer();
		west=new Container();
		west.setLayout(new GridLayout(5,1));
		scanFiles=new JButton("Scan for csv files");//check if files exists, if not, generate them.
		startButton=new JButton("START");
		center=new Container();
		center.setLayout(new GridLayout(1,2));
		west.add(startButton);
		c=new Container();
		c.setLayout(new BorderLayout());
		model=new DefaultListModel<String>();
		reportModel=new DefaultListModel<String>();
		jlist=new JList<String>(model);
		jlist.setFont(new Font("Baskerville",5,14));
		listLabel=new JLabel();
		listLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listLabel.setFont(new Font("Baskerville",5,20));
		reportList=new JList<String>(reportModel);
		reportList.setFont(new Font("Baskerville",5,14));
		reportLabel=new JLabel();
		reportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reportLabel.setFont(new Font("Baskerville",5,20));
		
		
		listContainer=new ListContainer();
		listContainer.add(listLabel,BorderLayout.NORTH);
		listContainer.add(new JScrollPane(jlist),BorderLayout.CENTER);
		
		reportContainer=new ListContainer();
		reportContainer.add(reportLabel,BorderLayout.NORTH);
		reportContainer.add(new JScrollPane(reportList),BorderLayout.CENTER);
		
		center.add(listContainer);
		center.add(reportContainer);

		showFullYear=new JButton("Show Full Year");
		showFullYear.setEnabled(false); 
		//showFullYear.setToolTipText("CLICK ON SCAN FIRST !!");
		generateButton=new JButton("Generate a CSV File !!!");
		//generateButton.setEnabled(false);
		west.add(generateButton);
		west.add(showFullYear);	
		createFiles=new JButton("Create monthly files");
		createFiles.setEnabled(false);
		west.add(createFiles);
		c.add(west,BorderLayout.WEST);
		c.add(center,BorderLayout.CENTER);
		c.add(monthContainer,BorderLayout.NORTH);
		this.getContentPane().add(c);
		addListeners();
		pack();
		
		
	}

	private Container getCenter() {
		return this.center;
		
		
	}
	private MonthContainer getMonthContainer() {
		return this.monthContainer;
	}
	public void createCleanFiles() {
		CSVReader reader=new CSVReader(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv");
		ReportGenerator rGen=new ReportGenerator(reader.getEntries());
		rGen.sortEntries();
	}
	public ArrayList<CSVEntry>readFile(String path){
		//csvFile=CSVFile.readFile(path);
		return new CSVFile(CSVFile.readFile(path)).getEntryList();
		
	}
	private DefaultListModel getListModel() {
		return this.model;
	}
	private DefaultListModel getReportModel() {
		return this.reportModel;
	}
	public XFrame getFrame() {
		return this;
	}
	private void addListeners() {
		this.showFullYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<CSVEntry>currentYear=CSVFile.readFile(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv");
				model.clear();
				model.addAll(currentYear);
				listLabel.setText("Visitors/patients of "+currentYear.get(0).getDate().getYear());
				ReportGenerator rGen=new ReportGenerator(currentYear);
				String[]currentReport=rGen.report(currentYear);
				reportLabel.setText("Report of "+currentYear.get(0).getDate().getYear());
				reportModel.clear();
				reportModel.addAll(Arrays.asList(currentReport));
			}
			
		});
		this.startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//if(file.exists)=>{ do nothing or generate new csv}
				String[] optionsStep1= {"Scan for existing file","Generate a new csv file","Cancel"};
				//int step1=JOptionPane.showConfirmDialog(getFrame(),"Choose an option","Step 1",optionsStep1 );
				int step1=JOptionPane.showOptionDialog(getFrame(),"Choose an option", "Step 1", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionsStep1,optionsStep1[2]);
				switch(step1) {
					case 0: System.out.println("Scanning files !!");
					{
						if(new File(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv").exists()) {
							JOptionPane.showMessageDialog(getFrame(),"The file already exists, and that's cool ! You can now use the button to create files for monthly reports");
							createFiles.setEnabled(true);
							showFullYear.setEnabled(true);
						}else {
							
							new File(System.getProperty("user.dir")+"\\csvfile\\").mkdir();
							JOptionPane.showMessageDialog(getFrame(),"New directory created, the CSV File will now be generated");
							generateCSV();
						}
					}
					
							break;
					case 1: System.out.println("Generating CSV");
							generateCSV();
							break;
					case 2: System.out.println("Cancel!");
							break;
					default : System.out.println("Operation cancelled !");
								break;
				}
				
				//if csv generated, propose to split it into monthly files

				
				
				
			}
		});
		for(int i=0;i<monthContainer.getButtons().length;i++) {
			String month=months[i];
			monthContainer.getButtons()[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ArrayList<CSVEntry>currentMonth=CSVFile.readFile(System.getProperty("user.dir")+"\\csvfile\\"+month+".csv");
					model.clear();
					model.addAll(currentMonth);
					listLabel.setText("Visitors/patients of "+month);
					ReportGenerator rGen=new ReportGenerator(currentMonth);
					String[]currentReport=rGen.report(currentMonth);
					reportLabel.setText("Report of "+month);
					reportModel.clear();
					reportModel.addAll(Arrays.asList(currentReport));
				}
				
			});
		}
		this.createFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CSVReader reader=new CSVReader(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv");
				ReportGenerator rGen=new ReportGenerator(reader.getEntries());
				rGen.sortEntries();
				rGen.createMonthlyFiles();
				System.out.println("Done creating files");
			}
			
		});
		
		this.generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateCSV();
			}
		});
	
		
	}
	public void getDirectory() {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(new File(System.getProperty("user.dir")+"\\csvfile\\"));
			
			//desktop.browseFileDirectory(new File(System.getProperty("user.dir")+"\\csvfile\\"));
			//Runtime.getRuntime().exec("explorer.exe /select," + System.getProperty("user.dir")+"\\csvfile\\csvfile.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	} 
	public void createMonthly() {
		CSVReader reader=new CSVReader(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv");
		ReportGenerator rGen=new ReportGenerator(reader.getEntries());
		rGen.sortEntries();
		rGen.createMonthlyFiles();
		System.out.println("Done creating files");
	}
	public void scanFile() {
		if(new File(System.getProperty("user.dir")+"\\csvfile\\csvfile.csv").exists()) {
			String[] options= {"Create monthly files","Cancel"};
			int step1=JOptionPane.showOptionDialog(getFrame(),"Choose an option", "Step 1", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,options[2]);
			if(step1==0) {
				createFiles.setEnabled(true);
			}else {
				createFiles.setEnabled(true);
			}
		}else {
			
		}
	}
	public int getNumberFromString(String year) {
		final int MAX=9999;
		int result=-1;
		String res="";
		if(!year.isBlank() && !year.isEmpty()) {
			year=year.trim();
			String[] correct= "0123456789".split("");
			for(int i=0;i<year.length();i++) {
				for(int j=0;j<correct.length;j++) {
					if(year.charAt(i)==correct[j].charAt(0)) {
						res=res.concat(correct[j]);
					}
				}
			}
			if(res.length()!=0) {
				result=Integer.valueOf(res);
				if(result>9999) {
					result=MAX;
				}
			}
		}
		return result;
	}
	public void generateCSV() {
		try {
			
			CSVGenerator csvGen=new CSVGenerator();
			int numberOfEntries=-1;
			int year=-1;
			try {
				while((numberOfEntries=getNumberFromString(JOptionPane.showInputDialog(getFrame(),"Enter the number of lines,must be an integer between 1 and 9999 \r\n ex:2500")))==-1) {
					
				}
				while((year=getYearFromString(JOptionPane.showInputDialog(getFrame(),"Enter the year, between 1971 and 2100 \r\n ex: 2023")))==-1) {
					
				}
				
				csvFile=csvGen.generateCSVFile(numberOfEntries,year);
				csvFile.createCSVFile();
				CSVFile.writeFile(csvFile.getFullPath(),csvFile.getEntryList());
				System.out.println("Done writing file !!!");
				CSV_EXISTS=true;
				createFiles.setEnabled(CSV_EXISTS);
				String[] options= {"YES (recommended)","NO"};
				int step1=JOptionPane.showOptionDialog(getFrame(),"Would you like to create a file for each month ? ( Yes: recommended )", "Generate files", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,options[1]);
				if(step1==0) {
					
					createFiles.setEnabled(true);
					createMonthly();
				}else {
					createFiles.setEnabled(true);
					
					JOptionPane.showMessageDialog(getFrame(),"You can generate the file later by clicking the 'Create monthly' button" );
				}
				showFullYear.setEnabled(true);
			}catch(NullPointerException e) {
				System.out.println("Cancel ok!!");
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public int getYearFromString(String year) {
		final int MAX=2100,MIN=1971;
		int result=-1;
		String res="";
		if(!year.isBlank() && !year.isEmpty()) {
			year=year.trim();
			String[] correct= "0123456789".split("");
			for(int i=0;i<year.length();i++) {
				for(int j=0;j<correct.length;j++) {
					if(year.charAt(i)==correct[j].charAt(0)) {
						res=res.concat(correct[j]);
					}
				}
			}
			if(res.length()!=0) {
				result=Integer.valueOf(res);
				if(result>MAX || result<MIN) {
					result=-1;
				}
			}
		}
		return result;
	}
	
}
