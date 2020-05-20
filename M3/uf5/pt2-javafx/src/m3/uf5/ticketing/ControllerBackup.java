package m3.uf5.ticketing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import m3.uf5.ticketing.model.SistemaGestio;

public class ControllerBackup implements Initializable {
    @FXML
    private BorderPane vistaFormPerfil;

    @FXML
    private Button btnBackup;
    @FXML
    private Button btnRestore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
    }

    @FXML
    public void backupClick(ActionEvent event) {
	Stage stage = SistemaGestio.getInstance().getStage();
	FileChooser fileChooser = new FileChooser();
	String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());

	fileChooser.setTitle("Realitzar backup");
	fileChooser.getExtensionFilters().add(new ExtensionFilter("BAK", "*.bak"));
	fileChooser.setInitialFileName("ticketusers-" + date + ".bak");

	File selectedFile = fileChooser.showSaveDialog(stage);
	if (selectedFile != null) {
	    try {
		SistemaGestio.getInstance().backup(selectedFile);
	    } catch (Exception e) {
		ControllerMenu.mostrarError("No s'ha pogut fer el backup", e.getMessage(),
			ExceptionUtils.getStackTrace(e));
		return;
	    }
	    ControllerMenu.mostrarInfo("Backup", "Backup realitzat correctament");
	}
    }

    @FXML
    public void restoreClick(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, IOException {
	Stage stage = SistemaGestio.getInstance().getStage();
	FileChooser fileChooser = new FileChooser();

	fileChooser.setTitle("Restaurar Backup");

	File selectedFile = fileChooser.showOpenDialog(stage);
	if (selectedFile != null) {
	    try {
		SistemaGestio.getInstance().restore(selectedFile);
	    } catch (Exception e) {
		ControllerMenu.mostrarError("No s'han pogut restaurar els usuaris", e.getMessage(),
			ExceptionUtils.getStackTrace(e));
		return;
	    }
	    ControllerMenu.mostrarInfo("Restaurar", "Backup restaurat correctament");
	}
    }

}
