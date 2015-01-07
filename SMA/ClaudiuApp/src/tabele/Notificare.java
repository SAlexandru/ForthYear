package tabele;

import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Notificare {
	private int id;
	private Timestamp data;
	private String titlu;
	private String mesaj;
	private NotificationType type = NotificationType.INFO;
	
	public Notificare(String toParse) {
		StringTokenizer tokenizer = new StringTokenizer(toParse);
		
		data = Timestamp.valueOf(tokenizer.nextToken());
		titlu = tokenizer.nextToken();
		mesaj = tokenizer.nextToken();
		
		try {
			type = NotificationType.valueOf(tokenizer.nextToken());
		}
		catch (NoSuchElementException e) {
			
		}
	}
	
	public Notificare(int id, Timestamp data, String titlu, String mesaj) {
		this.id = id;
		this.data = data;
		this.titlu = titlu;
		this.mesaj = mesaj;
	}
	
	public Notificare(int id, Timestamp data, String titlu, String mesaj, NotificationType type) {
		this.id = id;
		this.data = data;
		this.titlu = titlu;
		this.mesaj = mesaj;
		this.type  = type;
	}
	
	public int getId() {
		return id;
	}
	public Timestamp getData() {
		return data;
	}
	public String getTitlu() {
		return titlu;
	}
	public String getMesaj() {
		return mesaj;
	}
	
	public NotificationType getNotificationType() {
		return type;
		
	}
	
}
