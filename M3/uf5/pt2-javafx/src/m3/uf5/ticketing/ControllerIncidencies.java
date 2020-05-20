package m3.uf5.ticketing;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
import m3.uf5.ticketing.model.Empleat;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Tiquet;
import m3.uf5.ticketing.model.Usuari;

public abstract class ControllerIncidencies implements Initializable {
    @FXML
    private BorderPane vistaFormIncidencies;

    @FXML
    protected TableView<Tiquet> tiquetsTable;
    @FXML
    protected TableColumn<Tiquet, Number> colIndex;
    @FXML
    protected TableColumn<Tiquet, Date> colData;
    @FXML
    protected TableColumn<Tiquet, Empleat> colEmpleat;
    @FXML
    protected TableColumn<Tiquet, String> colCategoria;
    @FXML
    protected TableColumn<Tiquet, String> colDescripcio;
    @FXML
    protected TableColumn<Tiquet, Boolean> colCustomAction;	// Custom action
							   	// column
    @FXML
    protected TableColumn<Tiquet, Boolean> colTancar;

    @FXML
    protected ComboBox<Usuari> cmbEmpleat;
    @FXML
    protected ComboBox<Pair<String, String>> cmbCategoria;
    @FXML
    protected DatePicker dateDesde;
    @FXML
    protected DatePicker dateFins;

    protected List<Tiquet> tiquets;
    protected List<Usuari> empleats;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {

	this.empleats = SistemaGestio.getInstance().getUsuarisByTipus(Usuari.TIPUS_USERS[0], "");

	try {
	    this.empleats.add(0, new Empleat("", "tots", 0, "", ""));
	} catch (Exception e) {

	}
	this.cmbEmpleat.setItems(FXCollections.observableArrayList(empleats));
	this.cmbEmpleat.setConverter(new StringConverter<Usuari>() {	// Allow
								    	// null
								    	// /
								    	// empty
								    	// selection
	    @Override
	    public Usuari fromString(String usuari) {
		try {
		    Usuari value = SistemaGestio.getInstance().cercarUsuari(usuari);
		    return value;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	    }

	    @Override
	    public String toString(Usuari object) {
		if ("".equals(object.getUsuari())) return "Tots";
		return object.getNom();
	    }
	});

	Usuari usuari = SistemaGestio.getInstance().getUsuari();
	if (usuari.esEmpleat()) {
	    this.cmbEmpleat.getSelectionModel().select(usuari);
	    this.cmbEmpleat.setDisable(true);
	} else {
	    this.cmbEmpleat.getSelectionModel().select(0);
	    this.cmbEmpleat.valueProperty().addListener(new ChangeListener<Usuari>() {
		@Override
		public void changed(ObservableValue<? extends Usuari> observable, Usuari oldValue, Usuari newValue) {
		    refreshTiquets();
		}
	    });
	}

	ObservableList<Pair<String, String>> categories = FXCollections.observableArrayList();
	categories.add(new Pair<>("", ""));
	for (String categoria : Tiquet.CATEGORIES.keySet()) {
	    categories.add(new Pair<>(categoria, Tiquet.CATEGORIES.get(categoria)));
	}
	this.cmbCategoria.setItems(categories);
	this.cmbCategoria.getSelectionModel().select(0);
	this.cmbCategoria.setConverter(new StringConverter<Pair<String, String>>() {	// Allow
										    	// null
										    	// /
										    	// empty
										    	// selection
	    @Override
	    public Pair<String, String> fromString(String key) {
		if (!Tiquet.CATEGORIES.containsKey(key)) return new Pair<>("", "");

		return new Pair<>(key, Tiquet.CATEGORIES.get(key));
	    }

	    @Override
	    public String toString(Pair<String, String> categoria) {
		if (categoria == null || "".equals(categoria.getKey())) return "Totes";
		return categoria.getKey() + " - " + categoria.getValue();
	    }
	});
	this.cmbCategoria.valueProperty().addListener(new ChangeListener<Pair<String, String>>() {
	    @Override
	    public void changed(ObservableValue<? extends Pair<String, String>> observable,
		    Pair<String, String> oldValue, Pair<String, String> newValue) {
		refreshTiquets();
	    }
	});

	this.dateDesde.valueProperty().addListener(new ChangeListener<LocalDate>() {
	    @Override
	    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
		    LocalDate newValue) {
		refreshTiquets();
	    }
	});

	this.dateFins.valueProperty().addListener(new ChangeListener<LocalDate>() {
	    @Override
	    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
		    LocalDate newValue) {
		refreshTiquets();
	    }
	});

	this.tiquetsTable.setEditable(true);
	this.tiquetsTable.getSelectionModel().setCellSelectionEnabled(true);
	this.tiquetsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	this.refreshTiquets();

	this.colIndex.setMinWidth(40);
	this.colIndex.setMaxWidth(50);
	this.colIndex.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Tiquet, Number>, ObservableValue<Number>>() {
		    @Override
		    public ObservableValue<Number> call(TableColumn.CellDataFeatures<Tiquet, Number> tiquet) {
			return new SimpleLongProperty(tiquets.indexOf(tiquet.getValue()) + 1);
		    }
		});

	this.colData.setMinWidth(100);
	this.colData.setMaxWidth(130);
	this.colData.setCellValueFactory(new PropertyValueFactory<Tiquet, Date>("data"));
	this.colData.setCellFactory(new ColumnFormatter<Tiquet, Date>(new SimpleDateFormat("dd/MM/YYYY HH:mm")));

	this.colEmpleat.setMinWidth(120);
	this.colEmpleat.setMaxWidth(200);
	this.colEmpleat.setCellValueFactory(new PropertyValueFactory<Tiquet, Empleat>("empleat"));

	this.colCategoria.setMinWidth(80);
	this.colCategoria.setMaxWidth(100);
	this.colCategoria.setCellValueFactory(new PropertyValueFactory<Tiquet, String>("categoria"));

	this.colDescripcio.setMinWidth(220);
	this.colDescripcio.setMaxWidth(320);
	this.colDescripcio.setCellValueFactory(new PropertyValueFactory<Tiquet, String>("descripcio"));

	this.customColAction();

	this.colTancar.setMinWidth(40);
	this.colTancar.setMaxWidth(50);
	// define a simple boolean cell value for the action column so that the
	// column will only be shown for non-empty rows.
	this.colTancar.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Tiquet, Boolean>, ObservableValue<Boolean>>() {
		    @Override
		    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tiquet, Boolean> cell) {
			return new SimpleBooleanProperty(false);
		    }
		});

	this.colTancar.setCellFactory(new ColumnButton<Tiquet, Boolean>("Tancar",
		new Image(getClass().getResourceAsStream("resources/ok.png"))) {
	    @Override
	    public void buttonAction(Tiquet tiquet) {
		try {
		    boolean result = ControllerMenu.mostrarConfirmacio("Tancament",
			    "Segur que vols tancar aquesta incidència?");
		    if (result) {
			SistemaGestio.getInstance().tancarTiquet(tiquet.empleatObertura(), tiquet.dataObertura(),
				usuari, new Date());
			refreshTiquets();
		    }
		} catch (Exception e) {
		    ControllerMenu.mostrarError("No s'ha pogut tancar la incidència", e.getMessage(),
			    ExceptionUtils.getStackTrace(e));
		}
	    }
	});

    }

    protected abstract void customColAction();

    protected void refreshTiquets() {
	Usuari empleat = this.cmbEmpleat.getValue();
	Pair<String, String> pCategoria = this.cmbCategoria.getValue();
	String categoria = "";
	LocalDate desde = this.dateDesde.getValue();
	LocalDate fins = this.dateFins.getValue();
	if (empleat == null || "".equals(empleat.getUsuari())) {
	    empleat = null;
	}
	if (pCategoria != null && !"".equals(pCategoria.getKey())) {
	    categoria = pCategoria.getKey();
	}

	this.refreshTiquets(desde, fins, empleat, categoria);

	this.tiquetsTable.getItems().clear();
	this.tiquetsTable.setItems(FXCollections.observableArrayList(this.tiquets));
    }

    protected abstract void refreshTiquets(LocalDate desde, LocalDate fins, Usuari empleat, String categoria);
}
