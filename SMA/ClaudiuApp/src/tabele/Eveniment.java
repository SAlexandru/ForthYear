package tabele;

import java.sql.Timestamp;

public class Eveniment {
	private int id;
	private int notificare_id;
	private Timestamp data;
	
	public Eveniment(int id, int notificare_id, Timestamp data) {
		this.id = id;
		this.notificare_id = notificare_id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public int getNotificare_id() {
		return notificare_id;
	}

	public Timestamp getData() {
		return data;
	}
	
	
}
