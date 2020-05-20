package m3.uf5.ticketing;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import m3.uf5.ticketing.model.SistemaGestio;

public abstract class AbstractControllerPDF implements Initializable {
    @FXML
    protected Button btnPDF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rsrcs) {
	ControllerMenu.addIconToButton(this.btnPDF, new Image(getClass().getResourceAsStream("resources/pdf-logo.png")),
		30);
    }

    @FXML
    public abstract void generarPDFClick(ActionEvent event);

    protected void htmlToPDF(String titol, String html, String path, boolean landscape) throws Exception {
	PdfManager manager;

	String informeHtml = html;

	manager = new PdfManager(path);

	manager.generarPDF(titol, informeHtml, landscape);

	File pdfGenerat = new File(path);

	HostServices hostServices = SistemaGestio.getInstance().getApp().getHostServices();
	hostServices.showDocument(pdfGenerat.getAbsolutePath());
    }

}
