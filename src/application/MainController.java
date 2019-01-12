package application;

import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXButton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.scene.control.TableColumn.CellEditEvent;

public class MainController {
	@FXML
	private VBox modelling_vbox;
	@FXML
	private AnchorPane mainanchorpane, modellinganchorpane, ap_srs;
	@FXML
	private TabPane maintabPane;
	@FXML
	private TableView<SRS> tv = new TableView<SRS>();
	
	@FXML
	private TextField addFirstName, addLastName;
	@FXML
	private JFXButton add_row, import_req, import_spread,saveSpreadsheet, launch_srs;
	@FXML
	private TextArea txtArea;
	@FXML
	private TextField txtfield16 ;
	@FXML
	private Label draglabel;
	
	@FXML
	private ListView<HBox> lview;
	@FXML
	private Tab testcaseGen;
	
	@FXML
	private TableView<SRS> tblview = new TableView<SRS>();

	String stpath = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\STWorkbench";
	String curPath;
	
	ObservableList<SRS> data;
	ObservableList<SRS> paramvals;
	ArrayList<String> Model_List;
	
	
	
	@FXML public void initialize() throws FileNotFoundException {

		 data =
		        FXCollections.observableArrayList(
		        );
		 
		 paramvals =
			        FXCollections.observableArrayList(
			        );
		 
		 
		 BufferedReader br = new BufferedReader(new FileReader(stpath+"//"+"currentpath.txt" ));
			try {
				curPath = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		tblview.setVisible(false);
		tv.setEditable(true);
		
		SpreadSelect(null);
		
		import_req.setVisible(false);
		txtfield16.setVisible(false);
		launch_srs.setVisible(false);
		
		 
        TableColumn firstNameCol = new TableColumn("Parameters");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<SRS, String>("Parameters"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<SRS, String>>() {
                @Override
                public void handle(CellEditEvent<SRS, String> t) {
                    ((SRS) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setParameters(t.getNewValue());
                }
            }
        );
        
        TableColumn lastNameCol = new TableColumn("Values");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<SRS, String>("Values"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<SRS, String>>() {
                @Override
                public void handle(CellEditEvent<SRS, String> t) {
                    ((SRS) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setValues(t.getNewValue());
                }
            }
        );
 
        
 
        tv.setItems(data);
        tv.getColumns().addAll(firstNameCol, lastNameCol);
		
       //---------------------------------tblview-------------------------
        
        TableColumn paramCol = new TableColumn("Parameters");
        paramCol.setMinWidth(100);
        paramCol.setCellValueFactory(
                new PropertyValueFactory<SRS, String>("Parameters"));
        paramCol.setCellFactory(TextFieldTableCell.forTableColumn());
        paramCol.setOnEditCommit(
            new EventHandler<CellEditEvent<SRS, String>>() {
                @Override
                public void handle(CellEditEvent<SRS, String> t) {
                    ((SRS) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setParameters(t.getNewValue());
                }
            }
        );
 
        TableColumn valuesCol = new TableColumn("Values");
        valuesCol.setMinWidth(100);
        valuesCol.setCellValueFactory(
                new PropertyValueFactory<SRS, String>("Values"));
        valuesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valuesCol.setOnEditCommit(
            new EventHandler<CellEditEvent<SRS, String>>() {
                @Override
                public void handle(CellEditEvent<SRS, String> t) {
                    ((SRS) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setValues(t.getNewValue());
                }
            }
        );
 
 
        tblview.setItems(paramvals);
        tblview.getColumns().addAll(paramCol, valuesCol);
        
		
		
		mainanchorpane.setMaxHeight(bounds.getHeight());
		mainanchorpane.setMinHeight(bounds.getHeight());
		
		maintabPane.setMaxHeight(bounds.getHeight());
		maintabPane.setMinHeight(bounds.getHeight());
		
		modellinganchorpane.setMaxHeight(bounds.getHeight());
		modellinganchorpane.setMinHeight(bounds.getHeight());
		
		
		modelling_vbox.setMaxHeight(bounds.getHeight());
		modelling_vbox.setMinHeight(bounds.getHeight());
	}
	
	public void AddRows(ActionEvent e)
	{
		
		
		data.add(new SRS(
	            addFirstName.getText(),
	            addLastName.getText()
	        ));
	        addFirstName.clear();
	        addLastName.clear();
	}
	public void Button3Action(ActionEvent event)
	{
		//DirectoryChooser dc = new DirectoryChooser();
		//File selectedDirectory = dc.showDialog(null);
		
		
		   FileChooser fileChooser = new FileChooser();
		     File selectedFile = fileChooser.showOpenDialog(null);

		if(selectedFile == null){
		     //No Directory selected
		}else{
		
			        try {
			            FileReader reader = new FileReader(selectedFile);
			            int character;
			            String content ="";
			            
			            while ((character = reader.read()) != -1) 
		            	{
			            	content=content+((char) character);
			                //System.out.println((char) character);
		                }
			            
			            PrintWriter writer = new PrintWriter("C:\\Users\\bharg\\Documents\\STWorkbench\\sampleInput.txt", "UTF-8");
			            writer.println(content);
			            writer.close();
			            
			            String cont = content;
			            cont.replace(" ", "\' '");
			            System.out.println(cont);
			            String pythonScriptPath = "C:\\Users\\bharg\\Documents\\STWorkbench\\SRS.py";
			    		String[] cmd = new String[3];
			    		cmd[0] = "python"; // check version of installed python: python -V
			    		cmd[1] = pythonScriptPath;
			    		cmd[2] = content;
			    		 
			    		// create runtime to execute external command
			    		Runtime rt = Runtime.getRuntime();
			    		Process pr = rt.exec(cmd);
			    		
			    		String finalString = "";
			    		// retrieve output from python script
			    		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			    		String line = "";
			    		while((line = bfr.readLine()) != null) {
			    		// display each output line form python script
			    			finalString = finalString + line;
			    		//System.out.println(line);
			    		}
			    		
		    		System.out.println(finalString);
			    		//PythonInterpreter interpreter = new PythonInterpreter();
			    		String content1 = "";
			    		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\bharg\\Documents\\STWorkbench\\testfile.txt"))) {
			    			
			    			String sCurrentLine;

			    			while ((sCurrentLine = br.readLine()) != null) {
			    				content1 = content1 + sCurrentLine;
			    			}

			    		} catch (IOException e) {
			    			e.printStackTrace();
			    		}
			            
			            String[] params = new String[100];
			            String[] vals = new String[100];
			            
			            String[] temp = finalString.split(" ");
			            for(String hey : temp){
			                String temp1[] = hey.split("=");
			                //System.out.println(temp1.length);
			                if(temp1.length == 2)
			                {
			                	paramvals.add(new SRS(
			    			            temp1[0],
			    			            temp1[1]
			    			        ));
			                }
			            }
			            
			            
				        
			            draglabel.setText(content);
			            reader.close();
			 
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    
		  
		     
		     //txtview3.getItems().add(selectedDirectory.getAbsolutePath());
		     
		}
	
		
		
	}
	
	public void OpenACTS(ActionEvent e) throws IOException
	{
		File file = new File("E:\\Bhargav\\Projects\\STWorkbench\\Preethi Mam Files\\SampleInput\\FireEye\\acts_beta_v1_r9.3\\release9.3\\acts_gui_beta_v1_r9.3.jar");
		Desktop.getDesktop().open(file);
	}
	
	public void handleDragOver(DragEvent e)
	{
		if(e.getDragboard().hasFiles())
		{
		e.acceptTransferModes(TransferMode.ANY);
		}
	}
	
	public void handleDropped(DragEvent e) throws IOException
	{
		
		List<File> files = e.getDragboard().getFiles();
		String str = files.get(0).getName().toString();
		
		
		
		if(str.endsWith("xlsx"))
		{
			String[] sheetrows ;
			//File f = files.get(0);
			draglabel.setText(files.get(0).getName().toString());
			//InputStream f = new FileInputStream("LocalHackday.xlsx");
			InputStream ExcelFileToRead = new FileInputStream(files.get(0).getAbsolutePath());
			XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			
			XSSFWorkbook test = new XSSFWorkbook(); 
			
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row; 
			XSSFCell cell;

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext())
			{
				row=(XSSFRow) rows.next();
				
				sheetrows = new String[3];
				int i =0;
				
				Iterator cells = row.cellIterator();
				while (cells.hasNext())
				{
					
					cell=(XSSFCell) cells.next();
			
					if (cell.getCellType() == CellType.STRING)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getStringCellValue()+" ");
					}
					else if(cell.getCellType() == CellType.NUMERIC)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getNumericCellValue()+" ");
					}
					else
					{
						//U Can Handel Boolean, Formula, Errors
					}
					i=i+1;
				}
				
				data.add(new SRS(
			            sheetrows[0],
			            sheetrows[1]
			        ));
				
				System.out.println(sheetrows[0] + "blahh"+sheetrows[1]);
			}
			
		}
		
		else
		{
			draglabel.setText("Please enter a valid Excel File");
		}
	}
	
	public void SaveSpreadSheet(ActionEvent e) throws IOException
	{
		if(tv.isVisible())
		{
			exportSheetToXlsx();
		}
		else if(tblview.isVisible())
		{
			SaveSRSSpread();
		}
		else
		{
			
		}
	}
	
	public void SrsSelect(ActionEvent e)
	{
		ap_srs.setVisible(true);
		tblview.setVisible(true);
		tv.setVisible(false);
		import_req.setVisible(true);
		txtfield16.setVisible(true);
		import_spread.setVisible(false);
		saveSpreadsheet.setVisible(true);
		launch_srs.setVisible(true);
		addFirstName.setVisible(false);
		addLastName.setVisible(false);
		add_row.setVisible(false);
		
	}
	
	public void SpreadSelect(ActionEvent e)
	{
		ap_srs.setVisible(false);
		tblview.setVisible(false);
		tv.setVisible(true);
		import_req.setVisible(false);
		txtfield16.setVisible(false);
		import_spread.setVisible(true);
		saveSpreadsheet.setVisible(true);
		launch_srs.setVisible(false);
		addFirstName.setVisible(true);
		addLastName.setVisible(true);
		add_row.setVisible(true);
	}
	
	public void Launch_SRS_Process(ActionEvent e)

	{
		String content = txtfield16.getText().toString();
		
		try {
            
            PrintWriter writer = new PrintWriter("C:\\Users\\bharg\\Documents\\STWorkbench\\sampleInput.txt", "UTF-8");
            writer.println(content);
            writer.close();
            
            String cont = content;
            cont.replace(" ", "\' '");
            System.out.println(cont);
            String pythonScriptPath = "C:\\Users\\bharg\\Documents\\STWorkbench\\SRS.py";
    		String[] cmd = new String[3];
    		cmd[0] = "python"; // check version of installed python: python -V
    		cmd[1] = pythonScriptPath;
    		cmd[2] = content;
    		 
    		// create runtime to execute external command
    		Runtime rt = Runtime.getRuntime();
    		Process pr = rt.exec(cmd);
    		
    		String finalString = "";
    		// retrieve output from python script
    		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    		String line = "";
    		while((line = bfr.readLine()) != null) {
    		// display each output line form python script
    			finalString = finalString + line;
    		//System.out.println(line);
    		}
    		
		System.out.println(finalString);
    		//PythonInterpreter interpreter = new PythonInterpreter();
    		String content1 = "";
    		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\bharg\\Documents\\STWorkbench\\testfile.txt"))) {
    			
    			String sCurrentLine;

    			while ((sCurrentLine = br.readLine()) != null) {
    				content1 = content1 + sCurrentLine;
    			}

    		} catch (IOException err) {
    			err.printStackTrace();
    		}
            
            String[] params = new String[100];
            String[] vals = new String[100];
            
            String[] temp = finalString.split(" ");
            for(String hey : temp){
                String temp1[] = hey.split("=");
                //System.out.println(temp1.length);
                if(temp1.length == 2)
                {
                	paramvals.add(new SRS(
    			            temp1[0],
    			            temp1[1]
    			        ));
                }
            }
            
            
	        
            draglabel.setText(content);
 
        } catch (IOException err) {
            err.printStackTrace();
        }
    

 
 //txtview3.getItems().add(selectedDirectory.getAbsolutePath());
 

	}
	
	public void Import_Spreadsheet(ActionEvent e) throws IOException
	{
		
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		
		String str = file.getAbsolutePath().toString();
		if(str.endsWith("xlsx"))
		{
			String[] sheetrows ;
			//File f = files.get(0);
			draglabel.setText(file.getName().toString());
			//InputStream f = new FileInputStream("LocalHackday.xlsx");
			InputStream ExcelFileToRead = new FileInputStream(str);
			XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			
			XSSFWorkbook test = new XSSFWorkbook(); 
			
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row; 
			XSSFCell cell;

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext())
			{
				row=(XSSFRow) rows.next();
				
				sheetrows = new String[3];
				int i =0;
				
				Iterator cells = row.cellIterator();
				while (cells.hasNext())
				{
					
					cell=(XSSFCell) cells.next();
			
					if (cell.getCellType() == CellType.STRING)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getStringCellValue()+" ");
					}
					else if(cell.getCellType() == CellType.NUMERIC)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getNumericCellValue()+" ");
					}
					else
					{
						//U Can Handel Boolean, Formula, Errors
					}
					i=i+1;
				}
				
				data.add(new SRS(
			            sheetrows[0],
			            sheetrows[1]
			        ));
				
				
				System.out.println(sheetrows[0] + "blahh"+sheetrows[1]);
			}
			
		}
		
		else
		{
			draglabel.setText("Please enter a valid Excel File");
		}
	}
	
	public void Import_Model(String path) throws IOException

	{
		/*FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);*/
		
		//String str = file.getAbsolutePath().toString();
		String str = path;
		File xl = new File(path);
		Model_List =new ArrayList<String>();
		
		if(str.endsWith("xlsx"))
		{
			String[] sheetrows ;
			//File f = files.get(0);
			draglabel.setText(xl.getName().toString());
			//InputStream f = new FileInputStream("LocalHackday.xlsx");
			InputStream ExcelFileToRead = new FileInputStream(str);
			XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			
			XSSFWorkbook test = new XSSFWorkbook(); 
			
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row; 
			XSSFCell cell;

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext())
			{
				row=(XSSFRow) rows.next();
				
				sheetrows = new String[3];
				int i =0;
				
				Iterator cells = row.cellIterator();
				while (cells.hasNext())
				{
					
					cell=(XSSFCell) cells.next();
			
					if (cell.getCellType() == CellType.STRING)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getStringCellValue()+" ");
					}
					else if(cell.getCellType() == CellType.NUMERIC)
					{
						sheetrows[i] = cell.getStringCellValue();
						System.out.print(cell.getNumericCellValue()+" ");
					}
					else
					{
						//U Can Handel Boolean, Formula, Errors
					}
					
					i=i+1;
				}
				
				Model_List.add(sheetrows[0]);
				
				
				System.out.println(sheetrows[0] + "blahh"+sheetrows[1]);
			}
			System.out.println(Model_List);
		}
		
		else
		{
			draglabel.setText("Please enter a valid Excel File");
		}
	}

	public void setxml(String xlname) throws IOException
	{
		
		File xl = new File(xlname);
		String xln = xl.getName();
		String finalxl = xln.substring(0, xln.lastIndexOf('.'));
		String stpath = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\STWorkbench";
		 
	        //System.out.println(curPath);
	        //System.out.println(curPath.substring(curPath.lastIndexOf("\\")+1));
	        String Slinex=curPath.substring(curPath.lastIndexOf("\\")+1);
	        
	        BufferedWriter writer4 = new BufferedWriter(new FileWriter(curPath+"\\"+finalxl+".xml"));
       		
	        
	        writer4.write ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        writer4.write("\r\n");
	        
	        writer4.write ("<System name=\""+Slinex+"\">");
	        writer4.write("\r\n");
	        writer4.write(" <Parameters>");
	        writer4.write("\r\n");
	        String[] myStrings = { "Bhargav", "Avishek", "Likhith", "Itisha", "Akash", "Aneesh", "Preethi", "Krishnan", "Aneesh", "Panda"  };
	        for(int id = 0; id< Model_List.size(); id++) {
	        	String xXx="<Parameter id=\""+id+"\" name=\""+Model_List.get(id)+"\" type=\"2\"> ";
	        	writer4.write(xXx);
	        	writer4.write("\r\n");
	        	//writer4.write("\r\n");
	        	//writer4.write("\r\n");
	            writer4.write("<values>");
	            writer4.write("\r\n");
	           // writer4.write("\r\n");
	            writer4.write("  <value>true</value>");
	            writer4.write("\r\n");
	            writer4.write("  <value>false</value>");
	            writer4.write("\r\n");
	          //  writer4.write("\r\n");
	            writer4.write("</values>");
	            writer4.write("\r\n");
	         //   writer4.write("\r\n");
	            writer4.write("<basechoices>");
	            writer4.write("\r\n");
	           // writer4.write("\r\n");
	            writer4.write("  <basechoice>true</basechoice>");
	            writer4.write("\r\n");
	            writer4.write("  <basechoice>false</basechoice>");
	            writer4.write("\r\n");
	          //  writer4.write("\r\n");
	            writer4.write("</basechoices>");
	            writer4.write("\r\n");
	            //writer4.write("\r\n");
	            writer4.write("</Parameter>");
	            writer4.write("\r\n");
	            
	            
	            
	        }
	        
	        
	        
	     
	        
	        writer4.write("</Parameters>");
	        writer4.write("\r\n");

	        writer4.write("<OutputParameters />");
	        writer4.write("\r\n");
	        writer4.write("<Relations>");
	        String xHx="<Relation Strength=\""+Model_List.size()+"\" Default=\"true\">";
	        
        	writer4.write(xHx);
        	 writer4.write("\r\n");
	        for(int id = 0; id< Model_List.size(); id++)
	        {
	        	
	        	String xGx= "<Parameter name=\"" +Model_List.get(id)+"\">";
	        	writer4.write(xGx);
	        	writer4.write("\r\n");
	            writer4.write("  <value>true</value>");
	            writer4.write("\r\n");
	            writer4.write("  <value>false</value>");
	            writer4.write("\r\n");
	            writer4.write("</Parameter>");
	            writer4.write("\r\n");
	           }
	        writer4.write("</Relation>");
	        writer4.write("\r\n");
	        writer4.write("</Relations>");
	        writer4.write("\r\n");
	        writer4.write("<Constraints />");
	        writer4.write("\r\n");
	        writer4.write("</System>");
	        writer4.write("\r\n");
	        
	        
       		//writer4.write(ssline1);
        	writer4.close();
       	
		
	}
	
	public void SaveSRSSpread() throws IOException

	{
		TextInputDialog dialog = new TextInputDialog("");
        
        dialog.setTitle("Save SRS File");
        dialog.setHeaderText("Enter the FileName");
        dialog.setContentText("Name:");
 
        Optional<String> result = dialog.showAndWait();
 
        result.ifPresent(name -> {
        	Workbook workbook = new XSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            
            
            Row row = spreadsheet.createRow(0);
            
            

            for (int j = 0; j < tblview.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tblview.getColumns().get(j).getText());
            }

            for (int i = 0; i < tblview.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tblview.getColumns().size(); j++) {
                    if(tblview.getColumns().get(j).getCellData(i) != null) { 
                        row.createCell(j).setCellValue(tblview.getColumns().get(j).getCellData(i).toString()); 
                    }
                    else {
                        row.createCell(j).setCellValue("");
                    }   
                }
            }
            
            
            try {
                FileOutputStream fileOut = new FileOutputStream(curPath+"\\"+name+".xlsx");
                workbook.write(fileOut);
                fileOut.close();
                }
                catch(Exception e)
                {
                	
                }
            
            Alert alert = new Alert(AlertType.INFORMATION, "Saved the SRS File to Project Location", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        });
        
		
	}
	
	public void exportSheetToXlsx() throws IOException
	{
        TextInputDialog dialog = new TextInputDialog("");
        
        dialog.setTitle("Save Model File");
        dialog.setHeaderText("Enter the FileName");
        dialog.setContentText("Name:");
 
        Optional<String> result = dialog.showAndWait();
 
        result.ifPresent(name -> {
        	
        	Workbook workbook = new XSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            
            
            Row row = spreadsheet.createRow(0);
            
            

            for (int j = 0; j < tv.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tv.getColumns().get(j).getText());
            }

            for (int i = 0; i < tv.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tv.getColumns().size(); j++) {
                    if(tv.getColumns().get(j).getCellData(i) != null) { 
                        row.createCell(j).setCellValue(tv.getColumns().get(j).getCellData(i).toString()); 
                    }
                    else {
                        row.createCell(j).setCellValue("");
                    }   
                }
            }
            
            try {
            FileOutputStream fileOut = new FileOutputStream(curPath+"\\"+name+".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            }
            catch(Exception e)
            {
            	
            }
            
        	Alert alert = new Alert(AlertType.INFORMATION, "Saved the Model File to Project Location", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        });
        
        
	}
	
	public void TestCaseGen(Event e)
	{
		File folder = new File(curPath);
		File[] listOfFiles = folder.listFiles();
		
		int random = (int)(Math.random() * 50 + 1);
		
		ArrayList<HBox> fileNames = new ArrayList<>();
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  if(listOfFiles[i].getName().contains("xlsx")) {
				  Label label = new Label("");
				  label.setText(listOfFiles[i].getName());
				  Label rand = new Label("");
				  rand.setText(""+(int)(Math.random() * 50 + 1));
				  HBox temp = new HBox(label, rand);
			  fileNames.add(temp);
			  }
		    System.out.println("File " + listOfFiles[i].getName());
		  } else if (listOfFiles[i].isDirectory()) {
		    System.out.println("Directory " + listOfFiles[i].getName());
		  }
		}
		
		ObservableList<HBox> data = FXCollections.observableArrayList(fileNames);
		//lview.getItems().addAll("Bhargav", "Avishek","Pooja");
		lview.setItems(data);
	}

	public void OnListClick()
	{
		 lview.setOnMouseClicked(new EventHandler<MouseEvent>() {

		        @Override
		        public void handle(MouseEvent event) {
		            System.out.println("clicked on " + lview.getSelectionModel().getSelectedItem());
		            HBox click = lview.getSelectionModel().getSelectedItem();
		            String clic = click.getChildren().get(0).toString();
		            clic = clic.substring(clic.indexOf('\'')+1, clic.lastIndexOf('\''));
		            System.out.println(clic);
		            try {
		            	String xml = clic.substring(0,clic.lastIndexOf('.'));
						Import_Model(curPath+"\\"+clic);
						setxml(curPath+"\\"+clic);
						Alert alert = new Alert(AlertType.INFORMATION, "The TestCase File has been prepared for you. \n You can launch ACTS and browse to the "+xml+".xml", ButtonType.OK);
			            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			            alert.show();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    });
	}
	
	public static class SRS {
		 
        private final SimpleStringProperty parameters;
        private final SimpleStringProperty values;
 
        private SRS(String param, String val) {
            this.parameters = new SimpleStringProperty(param);
            this.values = new SimpleStringProperty(val);
        }
 
        public String getParameters() {
            return parameters.get();
        }
 
        public void setParameters(String fName) {
        	parameters.set(fName);
        }
 
        public String getValues() {
            return values.get();
        }
 
        public void setValues(String fName) {
        	values.set(fName);
        }
    }
	
	
}