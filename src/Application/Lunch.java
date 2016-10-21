package Application;

import java.io.IOException;
import java.io.InputStream;

import org.com.xsx.Data.SoftVersionData;
import org.com.xsx.DownService.DownService;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Lunch extends Application{
	
	AnchorPane root;
	
	@FXML
	Label updateinfo;
	
	@FXML
	ProgressBar updateprogress;
	
	DownService downservice;
	
	public static void main(String[] args) {

		if(args.length > 0){
			System.out.println(args[0]);
			SoftVersionData.GetInstance().setVersion(args[0]);
			launch(args);
		}
		System.out.println("error");	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("Lunch.fxml"));
        InputStream in = this.getClass().getResourceAsStream("Lunch.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        primaryStage.setTitle("纽康度报告管理软件升级程序");

		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/RES/logo.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        downservice = new DownService();
        updateinfo.textProperty().bind(downservice.messageProperty());
        updateprogress.progressProperty().bind(downservice.progressProperty());
        
        downservice.start();
	}
	
	
}
