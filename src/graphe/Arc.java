package graphe;

public class Arc {
	
	private int flot;
	private int capacite;
	private Sommet sommetSource;
	private Sommet sommetDestination;
	
	
	public Arc(int flot, int capacite,Sommet sommetDestination) {
		super();
		this.flot = flot;
		this.capacite = capacite;
		this.sommetDestination = sommetDestination;
	}
	public int getFlot() {
		return flot;
	}
	public void setFlot(int flot) {
		this.flot = flot;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public Sommet getSommetDestination() {
		return sommetDestination;
	}
	public void setSommetDestination(Sommet sommetDestination) {
		this.sommetDestination = sommetDestination;
	}
	public Sommet getSommetSource() {
		return sommetSource;
	}
	public void setSommetSource(Sommet sommetSource) {
		this.sommetSource = sommetSource;
	}
	
	
	
}
