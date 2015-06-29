package mak.downloadlite;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewController {
	@FXML
	private Button btnStart;
	@FXML
	private Button btnDir;
	@FXML
	private TextField txtURL;
	@FXML
	private Label lblDir;
	private Stage dialogStage;

	MainApp mainApp;

	@FXML
	private void handleStart() {
		System.out.println("Start Downlaod Command");
		mainApp.startDownload(txtURL.getText(),lblDir.getText());
		dialogStage.close();
	}

	@FXML
	private void handleDir() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");Stage dialogStage = new Stage();
		java.io.File defaultDirectory = new java.io.File("/");
		chooser.setInitialDirectory(defaultDirectory);
		java.io.File selectedDirectory = chooser.showDialog(dialogStage);
		if(selectedDirectory == null){
			lblDir.setText("No Directory selected");
        }else{
        	lblDir.setText(selectedDirectory.getAbsolutePath());
        }
	}

	public void setMainApp(MainApp App) {
		this.mainApp = App;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
}
