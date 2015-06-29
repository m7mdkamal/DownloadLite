package mak.downloadlite;

import javax.xml.bind.Marshaller.Listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FilesController {
	@FXML
	private TableView<File> tableDownloads;
	@FXML
	private TableColumn<File, String> colName;
	@FXML
	private TableColumn<File, String> colURL;
	@FXML
	private TableColumn<File, String> colStatus;
	@FXML
	private TableColumn<File, String> colSize;
	@FXML
	private TableColumn<File, String> colProgress;
	@FXML
	private TableColumn<File, String> colDirectory;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnPause;
	@FXML
	private Button btnResume;
	@FXML
	private Button btnStop;
	@FXML
	private Label infoName;
	@FXML
	private Label infoSize;
	@FXML
	private Label infoDownloaded;
	@FXML
	private Label infoState;
	@FXML
	private Label infoURL;
	@FXML
	private Label infoDir;
	@FXML
	private ProgressBar infoProgress;
	private MainApp mainApp;
	private ChangeListener<Object> changelistener;
	public FilesController() {
		// System.out.println(" #@2323 ");
	}

	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		colName.setCellValueFactory(cellData -> cellData.getValue()
				.nameProperty());
		colURL.setCellValueFactory(cellData -> cellData.getValue()
				.urlProperty());
		colStatus.setCellValueFactory(cellData -> cellData.getValue()
				.statusProperty());
		colSize.setCellValueFactory(cellData -> cellData.getValue()
				.sizeMegaProperty());
		colDirectory.setCellValueFactory(cellData -> cellData.getValue()
				.dirProperty());
		colProgress.setCellValueFactory(cellData -> cellData.getValue()
				.getProgressProperty());

		tableDownloads
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(observable, oldValue, newValue) -> showFileDetails(
								oldValue, newValue));
	}

	public void setMainApp(MainApp App) {
		this.mainApp = App;

		tableDownloads.setItems(App.getFilesData());
	}

	@FXML
	private void handleNew() {
		this.mainApp.showNewDialog();
	}

	private void showFileDetails(File oldfile, File selectedfile) {
		System.out.println(selectedfile.getName());
		infoName.setText(selectedfile.getName());
		infoDir.setText(selectedfile.getDir());
		infoDownloaded.setText(selectedfile.downloadMegaProperty().get());
		infoState.setText(selectedfile.getStatus());
		infoSize.setText(selectedfile.sizeMegaProperty().get());
		infoURL.setText(selectedfile.getUrl());
		
		if(oldfile!=null)
			oldfile.getProgressProperty().removeListener(changelistener);
		selectedfile.getProgressProperty().addListener(changelistener = new ChangeListener<Object>() {
			@Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				infoProgress.setProgress(Double.parseDouble(newValue.toString())/100);
			}
            
		});
		 infoProgress.setProgress((double) selectedfile.getDownloaded()
		 / (double) selectedfile.getSize());

		if (selectedfile != null) {

			btnPause.setDisable(false);
			btnResume.setDisable(false);

			if (selectedfile.getStatus() == "Paused")
				btnPause.setDisable(true);
			if (selectedfile.getStatus() == "Downloading")
				btnResume.setDisable(true);
			if (selectedfile.getStatus() == "Finished") {
				btnPause.setDisable(true);
				btnResume.setDisable(true);
			}

		} else {

			btnPause.setDisable(true);
			btnResume.setDisable(true);
		}
	}


	@FXML
	private void handleResume() {
		int selectedIndex = tableDownloads.getSelectionModel()
				.getSelectedIndex();
		tableDownloads.getItems().get(selectedIndex).resumeDownload();
		btnResume.setDisable(true);
		btnPause.setDisable(false);
	}

	@FXML
	private void handlePause() {
		int selectedIndex = tableDownloads.getSelectionModel()
				.getSelectedIndex();

		tableDownloads.getItems().get(selectedIndex).pauseDownload();

		btnPause.setDisable(true);
		btnResume.setDisable(false);
	}

	@FXML
	private void handleStop() {
		System.err.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		int selectedIndex = tableDownloads.getSelectionModel()
				.getSelectedIndex();
		tableDownloads.getItems().get(selectedIndex).stopDownload();
		btnResume.setDisable(true);
		btnPause.setDisable(true);
	}
}
