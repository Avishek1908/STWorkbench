package application;

import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
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
	private TableView<Person> tv = new TableView<Person>();
	
	@FXML
	private TextField addFirstName, addLastName, addEmail;
	@FXML
	private Button add_row;
	@FXML
	private TextArea txtArea;
	@FXML
	private Label draglabel;
	
	ObservableList<Person> data;
	
	
	
	@FXML public void initialize() {
		
		 data =
		        FXCollections.observableArrayList(
		            new Person("Jacob", "Smith", "jacob.smith@example.com"),
		            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
		            new Person("Ethan", "Williams", "ethan.williams@example.com"),
		            new Person("Emma", "Jones", "emma.jones@example.com"),
		            new Person("Michael", "Brown", "michael.brown@example.com")
		            
		        );
		/*for(int i=0;i<=100;i++)
		{
			data.add(new Person("","",""));
		}*/
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		
		tv.setEditable(true);
		 
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Person, String>>() {
                @Override
                public void handle(CellEditEvent<Person, String> t) {
                    ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                }
            }
        );
 
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Person, String>>() {
                @Override
                public void handle(CellEditEvent<Person, String> t) {
                    ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                }
            }
        );
 
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Person, String>>() {
                @Override
                public void handle(CellEditEvent<Person, String> t) {
                    ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                }
            }
        );
 
        tv.setItems(data);
        tv.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
		
		ap_srs.setVisible(false);
		
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
		data.add(new Person(
	            addFirstName.getText(),
	            addLastName.getText(),
	            addEmail.getText()
	        ));
	        addFirstName.clear();
	        addLastName.clear();
	        addEmail.clear();
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
			data.clear();
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
				
				data.add(new Person(
			            sheetrows[0],
			            sheetrows[1],
			            sheetrows[2]
			        ));
				
				System.out.println();
			}
			
		}
		
		else
		{
			draglabel.setText("Please enter a valid Excel File");
		}
	}
	
	public void exportSheetToXlsx(ActionEvent e) throws IOException
	{
		
		Workbook workbook = new HSSFWorkbook();
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
        
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDir = dc.showDialog(null);
        if(selectedDir==null)
        {
        
        }
        else
        {
        FileOutputStream fileOut = new FileOutputStream(selectedDir.getAbsolutePath()+"\\workbook.xls");
        workbook.write(fileOut);
        fileOut.close();
        }
	}
	public void SrsSelect(ActionEvent e)
	{
		ap_srs.setVisible(true);
		txtArea.setVisible(true);
	}
	
	public void SpreadSelect(ActionEvent e)
	{
		ap_srs.setVisible(false);
		txtArea.setVisible(false);
	}
	
	
	public static class Person {
		 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
 
        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
 
        public String getFirstName() {
            return firstName.get();
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
        public String getEmail() {
            return email.get();
        }
 
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
	
	
}
