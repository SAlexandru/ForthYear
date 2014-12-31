package tabele;

public class Utilizator {
	private int id;
	private String nume;
	private String prenume;
	private String mail;
	private String telefon;
	private String parola;
	private int e_instructor;
	
	public Utilizator(int id, String nume, String prenume, String mail, String telefon, String parola, int e_instructor){
		this.id = id;
		this.nume = nume;
		this.prenume = prenume;
		this.mail = mail;
		this.telefon = telefon;
		this.parola = parola;
		this.e_instructor = e_instructor;
	}

	public int getId() {
		return id;
	}

	public String getNume() {
		return nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public String getMail() {
		return mail;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getParola() {
		return parola;
	}

	public int getE_instructor() {
		return e_instructor;
	}

	@Override
	public String toString() {
		return "Utilizator [id=" + id + ", nume=" + nume + ", prenume="
				+ prenume + ", mail=" + mail + ", telefon=" + telefon
				+ ", parola=" + parola + ", e_instructor=" + e_instructor + "]";
	}
	
	
}
