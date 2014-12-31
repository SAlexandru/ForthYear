package tabele;

public class DetaliuCurs {
	private int id;
	private String telefon;
	private String site;
	private String strada;
	private int nr;
	private String localitate;
	private int instructor_id;
	private String link_poza;
	
	public DetaliuCurs(int id, String telefon, String site, String strada,
			int nr, String localitate, int instructor_id, String link_poza) {
		this.id = id;
		this.telefon = telefon;
		this.site = site;
		this.strada = strada;
		this.nr = nr;
		this.localitate = localitate;
		this.instructor_id = instructor_id;
		this.link_poza = link_poza;
	}

	public int getId() {
		return id;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getSite() {
		return site;
	}

	public String getStrada() {
		return strada;
	}

	public int getNr() {
		return nr;
	}

	public String getLocalitate() {
		return localitate;
	}

	public int getInstructor_id() {
		return instructor_id;
	}

	public String getLink_poza() {
		return link_poza;
	}
	
	
}
