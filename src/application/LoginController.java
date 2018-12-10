package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private ImageView imgview;
	@FXML
	public Button closeButton;
	Stage primaryStage=new Stage();
	@FXML
	private Button btn1;
	@FXML
	private Button createbtn;
	@FXML
	private TextField txtfield3;
	@FXML
	private TextField txtfield1;
	@FXML
	private TextArea txtarea1;
	

/*	@FXML
	private ListView<String> listview;
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void NewPro(ActionEvent event) throws Exception {
		
		Parent root=FXMLLoader.load(getClass().getResource("ProjectInfo.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth()/2-330);
		primaryStage.setHeight(bounds.getHeight()/2-165);
		primaryStage.show();
		
	}
	public void Button1Action(ActionEvent event)
	{
		DirectoryChooser dc = new DirectoryChooser();
		File selectedDirectory = dc.showDialog(null);

		if(selectedDirectory == null){
		     //No Directory selected
		}else{
		     System.out.println(selectedDirectory.getAbsolutePath());
		     txtfield3.setText(selectedDirectory.getAbsolutePath());
		     
		     //txtview3.getItems().add(selectedDirectory.getAbsolutePath());
		     
		}
	
		
		
	}

	public void createbutton(ActionEvent event) {
	
        try {
        	String value= txtfield1.getText().toString();
        	String value1= txtfield3.getText().toString();
        	String strDirectory=value;
        	String strManyDirectories=value1;
        	String finalpath = value1+ "\\"+ value;
        	System.out.println(value);
        	System.out.println(value1);
        	System.out.println(finalpath);
        	
        	
        	boolean success=( new File(finalpath)).mkdir();
        	if(success)
 {
        		System.out.println("Directory:"+strDirectory+" created");
        		
        		String projName = "Project Name: "+ txtfield1.getText().toString();
				String projDesc = "Project Description: "+txtarea1.getText();
				String projLoc = "Project Location: "+txtfield3.getText().toString();
        		BufferedWriter writer = new BufferedWriter(new FileWriter(finalpath+"\\"+"index.txt"));
        	    writer.write(projName);
        	    writer.write("\r\n");
        	    writer.write(projDesc);
        	    writer.write("\r\n");
        	    writer.write(projLoc);
        	    writer.close();
        		
        	}
        	String stpath = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\STWorkbench";
        	boolean success2=( new File(stpath)).mkdir();
        	String defaultpath="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\STWorkbench"+"//"+value;
        	boolean success1=( new File(defaultpath)).mkdir();
        	if(success1) {
        		System.out.println("Directory:"+strDirectory+" created");
        		String projName = "Project Name: "+ txtfield1.getText().toString();
				String projDesc = "Project Description: "+txtarea1.getText();
				String projLoc = "Project Location: "+txtfield3.getText().toString();
    		BufferedWriter writer2 = new BufferedWriter(new FileWriter(defaultpath+"\\"+"index.txt"));
    		 writer2.write(projName);
     	    writer2.write("\r\n");
     	    writer2.write(projDesc);
     	    writer2.write("\r\n");
     	    writer2.write(projLoc);
     	    writer2.close();
        	
     	    
     	   try {
   			//BorderPane root = new BorderPane();
   			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
   			Scene scene = new Scene(root);
   			scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
   			primaryStage.setScene(scene);
   			Screen screen = Screen.getPrimary();
   			Rectangle2D bounds = screen.getVisualBounds();
   			primaryStage.setX(bounds.getMinX());
   			primaryStage.setY(bounds.getMinY());
   			primaryStage.setWidth(bounds.getWidth()-100);
   			primaryStage.setHeight(bounds.getHeight());
   			primaryStage.show();
   		} catch(Exception e) {
   			e.printStackTrace();
   		}
        	
        
        			
        	
        }
        }
        catch(Exception e) {
        	System.err.println("Error:"+e.getMessage());
        }
	    
	}
	
	
}