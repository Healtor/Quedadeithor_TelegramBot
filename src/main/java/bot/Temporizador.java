package bot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Temporizador extends Thread {
	long tiempo;
	Quedadeithor q;
	Update update;
	long h,m;
	long user;
	long chat;
	Boolean antes=true;
	
	public Temporizador(int h, int m, Update u, Quedadeithor q, long uss, long ch) {
		
		update=u;
		this.q=q;
		this.h=h;
		this.m=m;
		user=uss;
		chat=ch;
			
		Date myDate = new Date();
//		String time = new SimpleDateFormat("HH:mm").format(myDate);
	//	System.out.println(time);
		
		@SuppressWarnings("deprecation")
		Date myDate2 = new Date(myDate.getYear(),myDate.getMonth(),myDate.getDate(),h,m);
//		String time2 = new SimpleDateFormat("HH:mm").format(myDate2);
	//	System.out.println(time2);
				
		if(myDate2.getTime()-myDate.getTime()>300000) {
			tiempo=myDate2.getTime()-myDate.getTime()-300000;
		}else {
			tiempo=myDate2.getTime()-myDate.getTime();
			antes=false;
		}
		
		
	}

	@Override
	public void run() {
		try {

			if (antes) {
				Thread.sleep(tiempo);
				String mensa = "Te recuerdo que has quedado en 5 minutos, espero que estés llegando...";
				mandarmensaje(user, mensa, 0);

			}

			Thread.sleep(300000);
			String mensa = "Ya son las " + h + ":" + m + "!!, habrás llegado ya, ¿verdad?";
			mandarmensaje(user, mensa, 0);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	private void mandarmensaje(long chat_id, String respuesta, long replyid) {

		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(respuesta).setReplyToMessageId((int) replyid);

		try {
			q.execute(message);
		} catch (TelegramApiException e) {

			e.printStackTrace();
		}
	}
}
