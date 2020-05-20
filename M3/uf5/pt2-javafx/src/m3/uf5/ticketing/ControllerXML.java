package m3.uf5.ticketing;

import java.io.File;
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

public class ControllerXML implements Initializable {
    @FXML
    private BorderPane vistaFormPerfil;

    @FXML
    private Button btnExport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
    }

    @FXML
    public void exportClick(ActionEvent event) {
	Stage stage = SistemaGestio.getInstance().getStage();
	FileChooser fileChooser = new FileChooser();
	String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());

	fileChooser.setTitle("Exportar a XML");
	fileChooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));
	fileChooser.setInitialFileName("tickets-" + date + ".xml");

	File selectedFile = fileChooser.showSaveDialog(stage);
	if (selectedFile != null) {
	    try {
		SistemaGestio.getInstance().export(selectedFile);
	    } catch (Exception e) {
		ControllerMenu.mostrarError("No s'ha pogut fer la exportació", e.getMessage(),
			ExceptionUtils.getStackTrace(e));
		return;
	    }
	    ControllerMenu.mostrarInfo("Exportar a XML", "Exportació realitzada correctament");
	}
    }

}
