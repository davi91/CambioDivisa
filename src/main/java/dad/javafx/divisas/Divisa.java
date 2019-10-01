package dad.javafx.divisas;

public class Divisa {
	
	// Usamos el Euro como base para el valor de la divisa
	
	public String divName;
	private double divValue;
	
	public Divisa(String divName, double divValue) {
		
		this.divName = divName;
		this.divValue = divValue;
	}
	
	
	public double toEuro(double cantidad) {
		return cantidad / divValue;
	}
	
	public double fromEuro(double cantidad) {
		return cantidad * divValue;
	}
}
