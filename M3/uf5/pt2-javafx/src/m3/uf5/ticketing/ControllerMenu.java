package m3.uf5.ticketing;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Usuari;

public class ControllerMenu implements Initializable {
    @FXML
    private BorderPane paneArrel;
    @FXML
    private AnchorPane paneVista;
    @FXML
    private AnchorPane vistaInici;

    @FXML
    private TextField txtUsuari;
    @FXML
    private Button btnLogin;

    @FXML
    private Menu mnIncidencies;
    @FXML
    private Menu mnUsuaris;
    @FXML
    private Menu mnPerfil;
    @FXML
    private MenuItem mnItNovaIncidencia;
    @FXML
    private MenuItem mnItNouUsuari;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
	this.logOff();
    }

    private void logOff() {
	try {
	    SistemaGestio.getInstance().setUsuari(null); // Logoff

	    this.enableMenu(false, null);

	    this.txtUsuari.clear();
	    this.carregarVista(this.vistaInici);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut mostrar la finestra d'inici", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Sortir menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void sortirMenuClick(ActionEvent event) {
	System.exit(0);
    }

    /**
     * Called when btnLogin button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void loginClick(ActionEvent event) {
	try {
	    String strUser = this.txtUsuari.getText();

	    Usuari usuari = SistemaGestio.getInstance().cercarUsuari(strUser);
	    SistemaGestio.getInstance().setUsuari(usuari); // Login

	    this.enableMenu(true, usuari);

	    this.openUserForm(true);

	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut accedir", e.getMessage(), ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Nova menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void novaIncidenciaMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaFormTiquet.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut mostrar el formulari d'incidències", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Pendents menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void consultaPendentsMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaIncidenciesPendents.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'han pogut mostrar les incidències pendents", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when En procés menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void consultaEnProcesMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaIncidenciesTasques.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'han pogut mostrar les incidències en procés", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Historic menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void consultaHistoricMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaIncidenciesHistoric.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'han pogut mostrar històric d'incidències resoltes", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Export to XML menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void xmlExportMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaXML.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut accedir a la vista XML", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Nou Usuari menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void nouUsuariMenuClick(ActionEvent event) {
	this.openUserForm(false);
    }

    /**
     * Called when Llistat usuaris menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void llistatUsuarisMenuClick(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaLlistaUsuaris.fxml"));
	    BorderPane vista = (BorderPane) loader.load();

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut mostrar la llista d'usuaris", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Backup menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void backupMenuClick(ActionEvent event) {
	try {
	    BorderPane vista = (BorderPane) FXMLLoader.load(getClass().getResource("VistaBackup.fxml"));

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut accedir a la vista backup", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Editar Perfil menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void editarPerfilMenuClick(ActionEvent event) {
	this.openUserForm(true);
    }

    /**
     * Called when Desconnectar menuItem is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void logoffClick(ActionEvent event) {
	this.logOff();
    }

    /**
     * Called when About menuItem is fired.
     *
     * @param event the action event.
     * @throws IOException
     */
    @FXML
    public void aboutMenuClick(ActionEvent event) {

	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("About ...");
	alert.setHeaderText(null);
	alert.setContentText(
		"Copyright@" + Calendar.getInstance(new Locale("ES_CA")).get(Calendar.YEAR) + "\nÀlex Macia");
	alert.showAndWait();
    }

    public void carregarVista(Pane vista) {
	if (vista == null) return;

	if (checkSiVistaEstaCarregada(vista.getId())) return;

	this.paneVista.getChildren().clear();

	// paneArrel.setPrefHeight(paneArrel.getHeight() - 80.0);

	this.paneVista.getChildren().add(vista);

	AnchorPane.setTopAnchor(vista, 0.0);
	AnchorPane.setBottomAnchor(vista, 0.0);
	AnchorPane.setLeftAnchor(vista, 0.0);
	AnchorPane.setRightAnchor(vista, 0.0);
	// this.paneVista.setVisible(true);
    }

    private boolean checkSiVistaEstaCarregada(String id) {
	if (id == null || this.paneVista != null || this.paneVista.getChildren() != null) return false;

	Iterator<Node> fills = this.paneVista.getChildren().iterator();

	while (fills.hasNext()) {
	    Node aux = fills.next();
	    if (id.equals(aux.getId())) return true;
	}

	return false;
    }

    private void enableMenu(boolean enable, Usuari usuari) {
	this.mnIncidencies.setVisible(enable);
	this.mnUsuaris.setVisible(enable);
	this.mnPerfil.setVisible(enable);
	if (usuari != null) {
	    if (usuari.esEmpleat()) {
		this.mnItNovaIncidencia.setDisable(false);
	    }
	    if (usuari.esSupervisor()) {
		this.mnItNouUsuari.setDisable(false);
	    }
	}
    }

    private void openUserForm(boolean perfil) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaFormUser.fxml"));
	    BorderPane vista = (BorderPane) loader.load();

	    ControllerFormUsuari controllerFormUsuari = loader.getController();
	    if (!perfil) {
		controllerFormUsuari.enableEdicio();
	    }

	    this.carregarVista(vista);
	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut accedir", e.getMessage(), ExceptionUtils.getStackTrace(e));
	}
    }

    public static Button addIconToButton(Button button, Image image, int size) {
	ImageView logo = new ImageView(image);
	logo.setFitWidth(size);
	logo.setFitHeight(size);
	button.setGraphic(logo);
	button.setText(null);
	return button;
    }

    public static void mostrarError(String title, String sms, String trace) {
	mostrarAlert("Error", title, sms, trace, AlertType.ERROR, "error-panel");
    }

    public static void mostrarInfo(String title, String sms) {
	mostrarAlert("Informació", title, sms, null, AlertType.INFORMATION, "info-panel");
    }

    public static boolean mostrarConfirmacio(String title, String sms) {
	Optional<ButtonType> result = mostrarAlert("Confirmació", title, sms, null, AlertType.CONFIRMATION,
		"info-panel");

	return result.get() == ButtonType.OK;
    }

    private static Optional<ButtonType> mostrarAlert(String title, String header, String sms, String trace,
	    AlertType tipus, String paneId) {

	Alert alert = new Alert(tipus);
	alert.setTitle(title);
	alert.getDialogPane().setId(paneId);
	alert.setHeaderText(header);
	alert.setContentText(sms);
	alert.setResizable(true);

	if (trace == null) {
	    alert.getDialogPane().setPrefSize(400, 120);
	} else {
	    alert.getDialogPane().setPrefSize(520, 200);

	    TextArea textArea = new TextArea(trace);
	    textArea.setEditable(false);
	    textArea.setWrapText(true);

	    // textArea.setMaxWidth(Double.MAX_VALUE);
	    textArea.setMaxWidth(600);
	    textArea.setMaxHeight(Double.MAX_VALUE);
	    textArea.setMaxHeight(300);
	    textArea.setMinHeight(300);
	    GridPane.setVgrow(textArea, Priority.ALWAYS);
	    GridPane.setHgrow(textArea, Priority.ALWAYS);

	    GridPane expContent = new GridPane();
	    expContent.setMaxWidth(Double.MAX_VALUE);
	    expContent.setMaxWidth(600);
	    expContent.add(textArea, 0, 1);

	    // Set expandable Exception into the dialog pane.
	    alert.getDialogPane().setExpandableContent(expContent);
	    alert.getDialogPane().setExpanded(false);

	    // Change Listener => property has been recalculated

	    alert.getDialogPane().expandedProperty().addListener(new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    alert.getDialogPane().requestLayout();
			    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			    stage.sizeToScene();
			}
		    });
		}
	    });

	    // Lambda version

	    /*
	     * alert.getDialogPane().expandedProperty().addListener((observable, oldvalue, newvalue) -> {
	     *
	     * Platform.runLater(() -> { alert.getDialogPane().requestLayout(); Stage stage = (Stage)
	     * alert.getDialogPane().getScene().getWindow(); stage.sizeToScene(); }); });
	     */

	    // Invalidation Listener => property and has to be recalculated

	    /*
	     * alert.getDialogPane().expandedProperty().addListener((observable) -> {
	     *
	     * Platform.runLater(() -> { alert.getDialogPane().requestLayout(); Stage stage = (Stage)
	     * alert.getDialogPane().getScene().getWindow(); stage.sizeToScene(); }); });
	     */
	}

	return alert.showAndWait();
    }
}
