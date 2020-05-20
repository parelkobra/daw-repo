package m3.uf5.ticketing;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import m3.uf5.ticketing.model.Assignacio;
import m3.uf5.ticketing.model.Empleat;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Supervisor;
import m3.uf5.ticketing.model.Tecnic;
import m3.uf5.ticketing.model.Tiquet;
import m3.uf5.ticketing.model.Usuari;

public class ControllerIncidenciesPendents extends ControllerIncidencies {

    @Override
    protected void customColAction() {
	Usuari usuari = SistemaGestio.getInstance().getUsuari();

	if (!usuari.esSupervisor()) {
	    this.colCustomAction.setVisible(false);
	} else {
	    this.colCustomAction.setMinWidth(40);
	    this.colCustomAction.setMaxWidth(50);
	    // define a simple boolean cell value for the action column so that
	    // the column will only be shown for non-empty rows.
	    this.colCustomAction.setCellValueFactory(
		    new Callback<TableColumn.CellDataFeatures<Tiquet, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tiquet, Boolean> button) {
			    return new SimpleBooleanProperty(true);
			}
		    });

	    this.colCustomAction.setCellFactory(new ColumnButton<Tiquet, Boolean>("Assignar",
		    new Image(getClass().getResourceAsStream("resources/assign.png"))) {
		@Override
		public void buttonAction(Tiquet tiquet) {

		    Dialog<Pair<Usuari, Integer>> dialog = new Dialog<>();
		    dialog.setTitle("Assignar tasca");
		    dialog.setHeaderText("Seleccionar tècnic i prioritat");

		    ButtonType acceptButtonType = new ButtonType("Acceptar", ButtonData.OK_DONE);
		    dialog.getDialogPane().getButtonTypes().addAll(acceptButtonType, ButtonType.CANCEL);

		    // Escollir Tècnic
		    Label lblTecnic = new Label("Tècnic");
		    lblTecnic.getStyleClass().add("etiquetes");

		    ObservableList<Usuari> tecnics = FXCollections.observableArrayList();
		    tecnics.addAll(SistemaGestio.getInstance().getUsuarisByTipus(Usuari.TIPUS_USERS[1], "")); // Tècnics
		    ComboBox<Usuari> cmbTecnic = new ComboBox<>(tecnics);

		    HBox hTecnic = new HBox(lblTecnic, cmbTecnic);
		    hTecnic.getStyleClass().add("input-group");

		    // Indicar prioritat
		    Label lblPrioritat = new Label("Prioritat");
		    lblPrioritat.getStyleClass().add("etiquetes");

		    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			    Assignacio.MIN_PRIORITAT, Assignacio.MAX_PRIORITAT, Assignacio.MIN_PRIORITAT + 1);
		    Spinner<Integer> spinPrioritat = new Spinner<>(valueFactory);

		    HBox hPrioritat = new HBox(lblPrioritat, spinPrioritat);
		    hPrioritat.getStyleClass().add("input-group");

		    VBox panell = new VBox(hTecnic, hPrioritat);
		    panell.setSpacing(20);

		    dialog.getDialogPane().setContent(panell);

		    dialog.getDialogPane().getStylesheets()
			    .add(getClass().getResource("application.css").toExternalForm());

		    Platform.runLater(() -> cmbTecnic.requestFocus());

		    dialog.setResultConverter(new Callback<ButtonType, Pair<Usuari, Integer>>() {

			@Override
			public Pair<Usuari, Integer> call(ButtonType buttonPressed) {
			    if (buttonPressed == acceptButtonType)
				return new Pair<>(cmbTecnic.getValue(), spinPrioritat.getValue());
			    return null;
			}

		    });

		    Optional<Pair<Usuari, Integer>> result = dialog.showAndWait();

		    result.ifPresent(new Consumer<Pair<Usuari, Integer>>() {
			@Override
			public void accept(Pair<Usuari, Integer> t) {
			    try {
				SistemaGestio.getInstance().novaTasca(
					(Supervisor) SistemaGestio.getInstance().getUsuari(), new Date(),
					(Tecnic) t.getKey(), t.getValue());

				refreshTiquets();
			    } catch (Exception e) {
				ControllerMenu.mostrarError("Error assignant el tècnic", e.getMessage(),
					ExceptionUtils.getStackTrace(e));
				e.printStackTrace();
			    }

			}
		    });

		}
	    });
	}
    }

    @Override
    protected void refreshTiquets(LocalDate desde, LocalDate fins, Usuari empleat, String categoria) {
	this.tiquets = SistemaGestio.getInstance().getIncidencies(desde, fins, (Empleat) empleat, categoria);
    }
}
