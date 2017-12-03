package bot;

import java.util.TreeMap;



public class Sesion {
	
	long chat_id;
	long user_id;
	int fase;
	int hora;
	int min;
	TreeMap<key, Temporizador> tmapSesion = new TreeMap<key, Temporizador>();

	
	public Sesion() {
		chat_id=0;
		user_id=0;
		fase=0;
		min=0;
		hora=0;
	}
	
	public Sesion(long chat,long user, int f) {
		chat_id=chat;
		user_id=user;
		fase=f;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Sesion [chat_id=" + chat_id + ", user_id=" + user_id + ", fase=" + fase + ", hora=" + hora + ", min="
				+ min + "]";
	}
	
	public void añadir(Temporizador temp, long chat_id, Integer user_id) {
		tmapSesion.put(new key(user_id,chat_id), temp);
	}
	
	public void parar(long chat_id, Integer user_id) {
		Temporizador t=tmapSesion.get(new key(user_id,chat_id));
		t.interrupt();
	}

public class key implements Comparable<key>{
		long user;
		long chat;
		
		
		public key(long user, long chat) {
			super();
			this.user = user;
			this.chat = chat;
		}
		public long getUser() {
			return user;
		}
		public void setUser(long user) {
			this.user = user;
		}
		public long getChat() {
			return chat;
		}
		public void setChat(long chat) {
			this.chat = chat;
		}
		
		public int compareTo(key o) {
			 if (user==o.getUser() && chat==o.getChat()) {
	             return 0;
	         }
			 
			return -1;
		}
		
		
		
		
		
	}






}


