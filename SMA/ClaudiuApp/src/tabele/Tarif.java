package tabele;

public class Tarif {
	private int id;
	private int tip_zumba_id;
	private int nr_sedinte;
	private int pret;
	
	public Tarif(int id, int tip_zumba_id, int nr_sedinte, int pret) {
		this.id = id;
		this.tip_zumba_id = tip_zumba_id;
		this.nr_sedinte = nr_sedinte;
		this.pret = pret;
	}
	
	public int getId() {
		return id;
	}
	public int getTip_zumba_id() {
		return tip_zumba_id;
	}
	public int getNr_sedinte() {
		return nr_sedinte;
	}
	public int getPret() {
		return pret;
	}

	@Override
	public String toString() {
		return "Tarif [id=" + id + ", tip_zumba_id=" + tip_zumba_id
				+ ", nr_sedinte=" + nr_sedinte + ", pret=" + pret + "]";
	}
	
	
}
