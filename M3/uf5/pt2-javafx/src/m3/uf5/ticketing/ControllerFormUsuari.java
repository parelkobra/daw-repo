package m3.uf5.ticketing;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import m3.uf5.ticketing.model.Empleat;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Supervisor;
import m3.uf5.ticketing.model.Tecnic;
import m3.uf5.ticketing.model.Usuari;

public class ControllerFormUsuari implements Initializable, ChangeListener<String> {
    @FXML
    private BorderPane vistaFormPerfil;
    @FXML
    private HBox boxEmpleats;
    @FXML
    private HBox boxTecnics;

    @FXML
    private Button btnDesar;

    @FXML
    private ComboBox<String> cmbTipus;

    @FXML
    private TextField txtUsuari;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtExtensio;

    @FXML
    private ComboBox<String> cmbUbicacio;
    @FXML
    private TextField txtLloc;
    @FXML
    private ComboBox<String> cmbEmpresa;

    @FXML
    private Label lblUsuari;
    @FXML
    private Label lblNom;
    @FXML
    private Label lblExtensio;

    @FXML
    private Label lblUbicacio;
    @FXML
    private Label lblLloc;
    @FXML
    private Label lblEmpresa;

    private boolean edicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
	this.edicio = false;

	ObservableList<String> listTipus = FXCollections.observableArrayList();
	listTipus.addAll(Arrays.asList(Usuari.TIPUS_USERS));
	this.cmbTipus.setItems(listTipus);

	ObservableList<String> ubicacions = FXCollections.observableArrayList();
	ubicacions.addAll(Arrays.asList(Empleat.UBICACIONS_EMPLEATS));
	this.cmbUbicacio.setItems(ubicacions);

	ObservableList<String> empreses = FXCollections.observableArrayList();
	empreses.addAll(Arrays.asList(Tecnic.EMPRESES));
	this.cmbEmpresa.setItems(empreses);

	// this.lblUsuari.setText("\u2386");
	// this.lblNom.setText("\u1F604");
	// this.lblExtensio.setText("\u2706");

	this.txtExtensio.setTextFormatter(new TextFormatter<>(Formatters.getExtensioFormatter(), 0));

	Usuari usuari = SistemaGestio.getInstance().getUsuari();
	if (usuari != null) {

	    this.txtUsuari.setText(usuari.getUsuari());
	    this.txtNom.setText(usuari.getNom());
	    this.txtExtensio.setText(usuari.getExtensio() + "");

	    if (usuari.esEmpleat()) {
		this.cmbTipus.setValue(Usuari.TIPUS_USERS[0]);
		this.cmbUbicacio.setValue(usuari.getUbicacio());
		this.txtLloc.setText(usuari.getLloc());

		this.enableAtributsEmpleat();
	    } else {
		this.cmbTipus.setValue(usuari.esTecnic() ? Usuari.TIPUS_USERS[1] : Usuari.TIPUS_USERS[2]);
		this.cmbEmpresa.setValue(usuari.getEmpresa());

		this.enableAtributsTecnic();
	    }
	}
    }

    public void enableEdicio() {
	this.edicio = true;

	// Set's cmbTipus and txtUsuari to be editable
	this.cmbTipus.setDisable(false);
	this.cmbTipus.setEditable(false);

	this.txtUsuari.setDisable(false);
	this.txtUsuari.setEditable(true);

	// Default user type is 'Empleat'
	this.cmbTipus.setValue(Usuari.TIPUS_USERS[0]);
	this.enableAtributsEmpleat();

	this.txtExtensio.clear();
	this.txtUsuari.clear();
	this.txtNom.clear();
	this.txtLloc.clear();

	this.cmbTipus.valueProperty().addListener(this);
    }

    private void enableAtributsEmpleat() {
	this.boxEmpleats.toFront();
	this.boxTecnics.toBack();

	this.cmbEmpresa.setVisible(false);
	this.cmbEmpresa.setValue(Tecnic.EMPRESES[0]);
    }

    private void enableAtributsTecnic() {
	this.boxTecnics.toFront();
	this.boxEmpleats.toBack();

	this.txtLloc.clear();
	this.cmbUbicacio.setValue(Empleat.UBICACIONS_EMPLEATS[0]);
	this.cmbEmpresa.setVisible(true);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	if (observable.equals(this.cmbTipus.valueProperty())) {

	    // Checks whether the user type is 'Empleat' or not
	    if (this.cmbTipus.getValue().equals(Usuari.TIPUS_USERS[0])) {
		this.enableAtributsEmpleat();
	    } else {
		this.enableAtributsTecnic();
	    }
	}
    }

    @FXML
    public void desarDadesClick(ActionEvent event) {
	try {
	    String tipus = this.cmbTipus.getValue();

	    String strUsuari = this.txtUsuari.getText();
	    String strNom = this.txtNom.getText();
	    int numExt = (int) this.txtExtensio.getTextFormatter().getValue();
	    String strUbicacio = this.cmbUbicacio.getValue();
	    String strLloc = this.txtLloc.getText();
	    String strEmpresa = this.cmbEmpresa.getValue();

	    Usuari usuari = SistemaGestio.getInstance().getUsuari();
	    if (usuari != null && !this.edicio) {
		usuari.updateUsuari(strNom, numExt, strUbicacio, strLloc, strEmpresa);
	    } else {
		Usuari nouUsuari = null;
		if (tipus.equals(Usuari.TIPUS_USERS[0])) {
		    nouUsuari = new Empleat(strUsuari, strNom, numExt, strUbicacio, strLloc);
		} else if (tipus.equals(Usuari.TIPUS_USERS[1])) {
		    nouUsuari = new Tecnic(strUsuari, strNom, numExt, strEmpresa);
		} else if (tipus.equals(Usuari.TIPUS_USERS[2])) {
		    nouUsuari = new Supervisor(strUsuari, strNom, numExt, strEmpresa);
		}
		SistemaGestio.getInstance().nouUsuari(nouUsuari);
	    }
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut desar les dades", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }
}
