package m3.uf5.ticketing;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import m3.uf5.ticketing.model.SistemaGestio;

public class ControllerIncidenciesHistoric extends AbstractControllerPDF {
    @FXML
    private Label lblInforme;
    @FXML
    private DatePicker dateDesde;
    @FXML
    private DatePicker dateFins;
    @FXML
    private Button btnFiltrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
	try {
	    super.initialize(url, rsrcs);

	    if (this.lblInforme != null) {
		this.informeHistoric(null, null);
	    }

	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut mostrar l'històric d'incidències", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    /**
     * Called when Filtrar button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void filtrarClick(ActionEvent event) {
	try {
	    LocalDate desde = this.dateDesde.getValue();
	    LocalDate fins = this.dateFins.getValue();

	    this.informeHistoric(desde, fins);

	} catch (Exception e) {
	    ControllerMenu.mostrarError("No s'ha pogut filtrar l'històric d'incidències", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

    private void informeHistoric(LocalDate desde, LocalDate fins) {
	String informe = SistemaGestio.getInstance().informeHistoric(desde, fins);

	this.lblInforme.setText(informe);
    }

    @Override
    @FXML
    public void generarPDFClick(ActionEvent event) {
	try {
	    // LocalDate desde = this.dateDesde.getValue();
	    // LocalDate fins = this.dateFins.getValue();

	    // String informeHtml =
	    // SistemaGestio.getInstance().informeOperacionsHtml(desde, fins);

	    // super.htmlToPDF("titol", informeHtml, "dir", true);

	} catch (Exception e) {
	    // e.printStackTrace();
	    ControllerMenu.mostrarError("Error generant el PDF amb l'històric d'incidències", e.getMessage(),
		    ExceptionUtils.getStackTrace(e));
	}
    }

}
