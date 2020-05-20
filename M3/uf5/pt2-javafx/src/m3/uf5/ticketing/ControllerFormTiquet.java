package m3.uf5.ticketing;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import m3.uf5.ticketing.model.Empleat;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Tiquet;
import m3.uf5.ticketing.model.Usuari;

public class ControllerFormTiquet implements Initializable {
    @FXML
    private BorderPane vistaFormTiquet;

    @FXML
    private Button btnObrir;

    @FXML
    private TextField txtUsuari;

    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextArea areaDescripcio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {

	ObservableList<String> listCategories = FXCollections.observableArrayList();
	listCategories.addAll(Tiquet.CATEGORIES.keySet());
	this.cmbCategoria.setItems(listCategories);

	Usuari usuari = SistemaGestio.getInstance().getUsuari();
	this.txtUsuari.setText(usuari.getUsuari());
    }

    @FXML
    public void obrirTiquetClick(ActionEvent event) {
	try {
	    String categoria = this.cmbCategoria.getValue();

	    String descripcio = this.areaDescripcio.getText();

	    SistemaGestio.getInstance()
		    .nouTiquet(new Tiquet((Empleat) SistemaGestio.getInstance().getUsuari(), categoria, descripcio));

	    SistemaGestio.getInstance().getMenuController().consultaPendentsMenuClick(null);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut crear el tiquet", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }
}
