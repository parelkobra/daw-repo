package m3.uf5.ticketing;

import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import m3.uf5.ticketing.model.Empleat;
import m3.uf5.ticketing.model.SistemaGestio;
import m3.uf5.ticketing.model.Tecnic;
import m3.uf5.ticketing.model.Tiquet;
import m3.uf5.ticketing.model.Usuari;

public class ControllerIncidenciesTasques extends ControllerIncidencies {

    @Override
    protected void customColAction() {
	Usuari usuari = SistemaGestio.getInstance().getUsuari();

	if (!usuari.esTecnic()) {
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

	    this.colCustomAction.setCellFactory(new ColumnButton<Tiquet, Boolean>("Intervenció",
		    new Image(getClass().getResourceAsStream("resources/intervencio.png"))) {
		@Override
		public void buttonAction(Tiquet tiquet) {

		    Dialog<NovaIntervencioResult> dialog = new Dialog<>();
		    dialog.setTitle("Intervenció realitzada");
		    dialog.setHeaderText("Omplir la informació");

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

		    // Indicar data
		    Label lblDate = new Label("Data");
		    lblDate.getStyleClass().add("etiquetes");

		    DatePicker date = new DatePicker(LocalDate.now());

		    HBox hDate = new HBox(lblDate, date);
		    hDate.getStyleClass().add("input-group");

		    // Indicar prioritat
		    Label lblHores = new Label("Hores");
		    lblHores.getStyleClass().add("etiquetes");

		    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
			    999);
		    Spinner<Integer> spinHores = new Spinner<>(valueFactory);

		    HBox hPrioritat = new HBox(lblHores, spinHores);
		    hPrioritat.getStyleClass().add("input-group");

		    // Indicar descripcio
		    Label lblDescripcio = new Label("Descripcio");
		    lblDescripcio.getStyleClass().add("etiquetes");

		    TextArea areaDescripcio = new TextArea();

		    HBox hDescripcio = new HBox(lblDescripcio, areaDescripcio);
		    hDescripcio.getStyleClass().add("input-group");

		    VBox panell = new VBox(hTecnic, hDate, hPrioritat, hDescripcio);
		    panell.setSpacing(20);

		    dialog.getDialogPane().setContent(panell);

		    dialog.getDialogPane().getStylesheets()
			    .add(getClass().getResource("application.css").toExternalForm());

		    Platform.runLater(() -> cmbTecnic.requestFocus());

		    dialog.setResultConverter(new Callback<ButtonType, NovaIntervencioResult>() {
			@Override
			public NovaIntervencioResult call(ButtonType buttonPressed) {
			    if (buttonPressed == acceptButtonType)
				return new NovaIntervencioResult((Tecnic) cmbTecnic.getValue(), date.getValue(),
					spinHores.getValue(), areaDescripcio.getText());
			    return null;
			}
		    });

		    Optional<NovaIntervencioResult> result = dialog.showAndWait();

		    result.ifPresent(new Consumer<NovaIntervencioResult>() {
			@Override
			public void accept(NovaIntervencioResult t) {
			    try {
				SistemaGestio.getInstance().novaIntervencio(tiquet.empleatObertura(),
					tiquet.dataObertura(), t.getTecnic(), t.getData(), t.getHores(),
					t.getDescripcio());
				refreshTiquets();
			    } catch (Exception e) {
				ControllerMenu.mostrarError("Error creant nova intervenció", e.getMessage(),
					ExceptionUtils.getStackTrace(e));
			    }
			}
		    });
		}
	    });
	}
    }

    @Override
    protected void refreshTiquets(LocalDate desde, LocalDate fins, Usuari empleat, String categoria) {
	this.tiquets = SistemaGestio.getInstance().getTasquest(desde, fins, (Empleat) empleat, categoria);
    }
}
