package tabele;

public class TipZumba {
	private int id;
	private String nume;
	private String descriere;
	public TipZumba(int id, String nume, String descriere) {
		this.id = id;
		this.nume = nume;
		this.descriere = descriere;
	}
	public int getId() {
		return id;
	}
	public String getNume() {
		return nume;
	}
	public String getDescriere() {
		return descriere;
	}
	
}
