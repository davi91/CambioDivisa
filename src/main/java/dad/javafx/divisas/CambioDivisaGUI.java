package dad.javafx.divisas;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisaGUI extends Application {

	/* Declaración JavaFX */
	private TextField txtFrom;
	private TextField txtTo;
	
	private ComboBox<String> selectFrom;
	private ComboBox<String> selectTo;
	
	private Button changeBt;
	
	/* Declaración divisas */
	private Divisa libra = new Divisa("Libra", 0.8873);
	private Divisa dolar = new Divisa("Dolar", 1.2007);
	private Divisa yen = new Divisa("Yen", 133.59);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		txtFrom = new TextField("0");
		
		txtTo = new TextField("0");
		txtTo.setEditable(false);
		
		changeBt = new Button("Cambiar");
		changeBt.setDefaultButton(true);
		changeBt.setOnAction( evt -> onChangeAction(evt));
		
		selectFrom = new ComboBox<>();
		selectFrom.getItems().addAll("Euro", "Dolar", "Yen", "Libra");
		selectFrom.getSelectionModel().selectFirst();
		
		selectTo = new ComboBox<>();
		selectTo.getItems().addAll("Euro", "Dolar", "Yen", "Libra");		
		selectTo.getSelectionModel().selectFirst();
		
		HBox fromBox = new HBox(5, txtFrom, selectFrom);
		fromBox.setAlignment(Pos.CENTER);
		fromBox.setFillHeight(false);
		
		HBox toBox = new HBox(5, txtTo, selectTo);
		toBox.setAlignment(Pos.CENTER);
		toBox.setFillHeight(false);
		
		VBox root = new VBox(5, fromBox, toBox, changeBt);
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private boolean isNumeric(String txt) {
		
		try {
			
			// Si falla, ya sabemos que es algo que no se parece a un número
			@SuppressWarnings("unused")
			double n = Double.parseDouble(txt);
			
		} catch( NumberFormatException e ) {
			return false;
		}
		
		return true;
	}
	
	/* Unas funciones auxiliares que nos ayudan a pasar las unidades */
	private double euro_getConversion(String from, double cantidad) {
	
		double result = 0;
		switch(from) {
		
			case "Yen":
				result = yen.toEuro(cantidad);
				break;
			case "Dolar":
				result = dolar.toEuro(cantidad);
				break;
			case "Libra":
				result = libra.toEuro(cantidad);
				break;
			default:
				result = cantidad;
				break;
		}
		
		return result;
	}
	
	private double dolares_getConversion(String from, double cantidad) {
		
		double result = 0;
		switch(from) {
		
			case "Yen":
				result = dolar.fromEuro(yen.toEuro(cantidad));
				break;
			case "Euro":
				result = dolar.fromEuro(cantidad);
				break;
			case "Libra":
				result = dolar.fromEuro(libra.toEuro(cantidad));
				break;
			default:
				result = cantidad;
				break;
		}
		
		return result;
	}
	
	private double libras_getConversion(String from, double cantidad) {
		
		double result = 0;
		switch(from) {
		
			case "Yen":
				result = libra.fromEuro(yen.toEuro(cantidad));
				break;
			case "Euro":
				result = libra.fromEuro(cantidad);
				break;
			case "Dolar":
				result = libra.fromEuro(dolar.toEuro(cantidad));
				break;
			default:
				result = cantidad;
				break;
		}
		
		return result;
	}
	
	private double yen_getConversion(String from, double cantidad) {
		
		double result = 0;
		switch(from) {
		
			case "Euro":
				result = yen.fromEuro(cantidad);
				break;
			case "Libra":
				result = yen.fromEuro(libra.toEuro(cantidad));
				break;
			case "Dolar":
				result = yen.fromEuro(dolar.toEuro(cantidad));
				break;
			default:
				result = cantidad;
				break;
		}
		
		return result;
	}
	
	/* Aqui comprobamos el evento del cambio */
	private void onChangeAction(ActionEvent evt) {
		
		String txt = txtFrom.getText();
		
		if( txt.isEmpty() || !isNumeric(txt) ) {
			
			// Algo no ha salido bien
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Campo introducido no válido");
			alert.setContentText("No se ha introducido un número correcto");
			alert.showAndWait();
		}
		
		else {
			
			double cantidad = Double.parseDouble(txt); // Ya hemos asegurado la conversión
			double resultado = 0;
			switch(selectTo.getValue()) {
			
			case "Euro":
				resultado = euro_getConversion(selectFrom.getValue(), cantidad);
				break;
			case "Dolar":
				resultado = dolares_getConversion(selectFrom.getValue(), cantidad);
				break;
			case "Yen":
				resultado = yen_getConversion(selectFrom.getValue(), cantidad);
				break;
			default :
				resultado = libras_getConversion(selectFrom.getValue(), cantidad);
				break;
			}
			
			// Lo redondeamos un poco con un float
			txtTo.setText(String.valueOf((float)resultado));
		}
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
