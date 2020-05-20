package m3.uf5.ticketing;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Usuari;

public class ControllerLlistaUsuaris extends AbstractControllerPDF {
    @FXML
    private BorderPane vistaFormUsuaris;

    @FXML
    private ComboBox<String> cmbTipus;
    @FXML
    private TextField txtNom;

    @FXML
    private TableView<Usuari> usuarisTable;
    @FXML
    private TableColumn<Usuari, Number> colIndex;
    @FXML
    private TableColumn<Usuari, String> colUsuari;
    @FXML
    private TableColumn<Usuari, String> colNom;
    @FXML
    private TableColumn<Usuari, Integer> colExtensio;
    @FXML
    private TableColumn<Usuari, String> colUbicacio;
    @FXML
    private TableColumn<Usuari, String> colLloc;
    @FXML
    private TableColumn<Usuari, String> colEmpresa;
    @FXML
    private TableColumn<Usuari, Boolean> colEsborrar;

    private LinkedList<Usuari> usuaris;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {

	super.initialize(url, rsrcs);

	this.txtNom.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		refreshUsuaris();
	    }
	});

	ObservableList<String> listTipus = FXCollections.observableArrayList();
	listTipus.add("");
	listTipus.addAll(Arrays.asList(Usuari.TIPUS_USERS));
	this.cmbTipus.setItems(listTipus);
	this.cmbTipus.valueProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		refreshUsuaris();
	    }
	});

	this.refreshUsuaris();

	this.usuarisTable.setEditable(true);
	this.usuarisTable.getSelectionModel().setCellSelectionEnabled(true);
	this.usuarisTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	this.colIndex.setMinWidth(30);
	this.colIndex.setMaxWidth(50);
	this.colIndex.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Usuari, Number>, ObservableValue<Number>>() {
		    @Override
		    public ObservableValue<Number> call(TableColumn.CellDataFeatures<Usuari, Number> usuari) {
			return new SimpleLongProperty(usuaris.indexOf(usuari.getValue()) + 1);
		    }
		});

	this.colUsuari.setMinWidth(110);
	this.colUsuari.setMaxWidth(180);
	this.colUsuari.setCellValueFactory(new PropertyValueFactory<Usuari, String>("usuari"));
	this.colUsuari.setCellFactory(TextFieldTableCell.forTableColumn());
	this.colUsuari.setEditable(false);

	this.colNom.setMinWidth(200);
	this.colNom.setMaxWidth(300);
	this.colNom.setCellValueFactory(new PropertyValueFactory<Usuari, String>("nom"));
	this.colNom.setCellFactory(TextFieldTableCell.forTableColumn());

	this.colNom.setOnEditCommit(new EventHandler<CellEditEvent<Usuari, String>>() {			// Edició
	    @Override
	    public void handle(CellEditEvent<Usuari, String> t) {
		try {
		    // Usuari client =
		    // SistemaGestio.getInstance().getClientByIndex(t.getTablePosition().getRow());
		    Usuari usuari = t.getRowValue();
		    usuari.setNom(t.getNewValue());
		} catch (Exception e) {
		    ControllerMenu.mostrarError("No s'ha pogut modificar el nom de l'usuari", e.getMessage(),
			    ExceptionUtils.getStackTrace(e));
		}
	    }
	});

	this.colExtensio.setMinWidth(100);
	this.colExtensio.setMaxWidth(150);
	this.colExtensio.setCellValueFactory(new PropertyValueFactory<Usuari, Integer>("extensio"));
	this.colExtensio.setCellFactory(TextFieldTableCell.forTableColumn(Formatters.getExtensioFormatter()));

	this.colExtensio.setOnEditCommit(new EventHandler<CellEditEvent<Usuari, Integer>>() {			// Edició
	    @Override
	    public void handle(CellEditEvent<Usuari, Integer> t) {
		try {
		    // Usuari client =
		    // SistemaGestio.getInstance().getClientByIndex(t.getTablePosition().getRow());
		    Usuari usuari = t.getRowValue();
		    usuari.setExtensio(t.getNewValue());
		} catch (Exception e) {
		    ControllerMenu.mostrarError("No s'ha pogut modificar l'extensió de l'usuari", e.getMessage(),
			    ExceptionUtils.getStackTrace(e));
		}
	    }
	});

	this.colUbicacio.setMinWidth(110);
	this.colUbicacio.setMaxWidth(180);
	this.colUbicacio.setCellValueFactory(new PropertyValueFactory<Usuari, String>("ubicacio"));
	this.colUbicacio.setCellFactory(TextFieldTableCell.forTableColumn());

	this.colLloc.setMinWidth(110);
	this.colLloc.setMaxWidth(180);
	this.colLloc.setCellValueFactory(new PropertyValueFactory<Usuari, String>("lloc"));
	this.colLloc.setCellFactory(TextFieldTableCell.forTableColumn());

	this.colEmpresa.setMinWidth(110);
	this.colEmpresa.setMaxWidth(180);
	this.colEmpresa.setCellValueFactory(new PropertyValueFactory<Usuari, String>("empresa"));
	this.colEmpresa.setCellFactory(TextFieldTableCell.forTableColumn());

	this.colEsborrar.setMinWidth(40);
	this.colEsborrar.setMaxWidth(50);
	// define a simple boolean cell value for the action column so that the
	// column will only be shown for non-empty rows.
	this.colEsborrar.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Usuari, Boolean>, ObservableValue<Boolean>>() {
		    @Override
		    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Usuari, Boolean> cell) {
			return new SimpleBooleanProperty(false);
		    }
		});

	this.colEsborrar.setCellFactory(new ColumnButton<Usuari, Boolean>("Esborrar",
		new Image(getClass().getResourceAsStream("resources/recycle-bin.png"))) {
	    @Override
	    public void buttonAction(Usuari usuari) {
		try {
		    SistemaGestio.getInstance().esborrarUsuari(usuari.getUsuari());
		    refreshUsuaris();
		} catch (Exception e) {
		    ControllerMenu.mostrarError("No s'ha pogut esborrar l'usuari", e.getMessage(),
			    ExceptionUtils.getStackTrace(e));
		}
	    }
	});
    }

    private void refreshUsuaris() {
	String tipus = this.cmbTipus.getValue();
	String cerca = this.txtNom.getText();
	this.usuaris = SistemaGestio.getInstance().getUsuarisByTipus(tipus, cerca);
	this.usuarisTable.getItems().clear();
	this.usuarisTable.setItems(FXCollections.observableArrayList(this.usuaris));
    }

    @Override
    @FXML
    public void generarPDFClick(ActionEvent event) {
	try {
	    // String informeHtml =
	    // SistemaGestio.getInstance().llistatusuarisHtml();

	    // super.htmlToPDF(SistemaGestio.TITOL_INFORME_usuaris, informeHtml,
	    // SistemaGestio.BASE_DIR+SistemaGestio.FILE_INFORME_usuaris, true);

	} catch (Exception e) {
	    // e.printStackTrace();
	    ControllerMenu.mostrarError("Error generant el PDF de la llista d'usuaris", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }
}
