package mak.downloadlite;

import java.io.IOException;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private ObservableList<File> filesData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("main.fxml"));

			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			primaryStage.show();
			FilesController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.setOnCloseRequest(event -> { Platform.exit();
			          System.exit(0);
			       }
			    );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MainApp() {
		
		//filesData.add(new File("http://localhost/1.mkv", "/home/mohamed/Desktop/down"));
		//filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-codec.jar.zip", "/home/mohamed/Desktop/down"));
		//filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-lang.jar.zip", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-lang.jar.zip", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-lang.jar.zip", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-lang.jar.zip", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://www.java2s.com/Code/JarDownload/org-apache/org-apache-commons-lang.jar.zip", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
//		filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
		//filesData.add(new File("http://releases.ubuntu.com/14.10/ubuntu-14.10-desktop-amd64.iso", "/home/mohamed/Desktop/down"));
		
	}

	public ObservableList<File> getFilesData() {
		
		return filesData;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void showNewDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("new.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("New File");
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			// return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void startDownload(String URL,String Dir) {
		filesData.add(new File(URL, Dir));
	}
}
