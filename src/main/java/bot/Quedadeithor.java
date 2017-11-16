package bot;

import org.omg.Messaging.SyncScopeHelper;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.toIntExact;

public class Quedadeithor extends TelegramLongPollingBot {
	
	String hora;
	String lugar;
	long usuario;
	int fase=0;
	long chat;
	

    public void onUpdateReceived(Update update) {    	

        if (update.hasMessage() && update.getMessage().hasText()) {
        	
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            
            message_text = message_text.toLowerCase();
            
            if(update.getMessage().isCommand() && message_text.contains("/crearquedada")) {
               	
            	if(fase==0) {
            		mandarmensaje(chat_id,"Vale! dime la Hora a la que quieres quedar", 0);
            		usuario=update.getMessage().getFrom().getId();
            		chat=update.getMessage().getChatId();
            		fase=1;
            	}else{
            		mandarmensaje(chat_id,"Espera, ya se está creando otra, puedes cancelarla usando /cancelar", 0);

            	}
            }else if (update.getMessage().isCommand() && update.getMessage().getText().equals("/cancelar")) {
				if (fase != 0) {
					mandarmensaje(chat_id, "Se ha cancelado la creación de la quedada", 0);
					fase = 0;
				}      
   
//-----------------------------------------------------------------------------------------------------------------
            }else if(chat==chat_id && usuario==update.getMessage().getFrom().getId() && fase!=0){
				
				switch(fase) {
            	
            	case 1: //HORA
            		hora=update.getMessage().getText();
            		mandarmensaje(chat_id,"Perfecto, dime el Lugar", 0);
            		fase=2;
            		break;
            	case 2:
            		lugar=update.getMessage().getText();
            		fase=0;
            		
            		SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText("Se ha quedado a las "+hora+" en "+lugar+", ¿Te apuntas?");
                    
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();
                    
                    rowInline.add(new InlineKeyboardButton().setText("¡Yo voy!").setCallbackData("meapunto"));

                    
                    // Set the keyboard to the markup
                  
                    rowsInline.add(rowInline);
                    // Add it to the message
                    
                    markupInline.setKeyboard(rowsInline);
                    message.setReplyMarkup(markupInline);                   
                    
                    try {
                        execute(message); // Sending our message object to user
                        
                        anclar(chat_id, update.getMessage().getMessageId()+1);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            		
            		break;
            	}
							
			} 

 //------------------------------------------------------------------------------------
		} else if (update.hasCallbackQuery()) {
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();
			String answer = null;

			if (call_data.equals("meapunto")) {

				String m = update.getCallbackQuery().getMessage().getText();
				StringTokenizer st = new StringTokenizer(m, "\n");

				String alias = update.getCallbackQuery().getFrom().getUserName();
				String resultado = "";
				Boolean añadir = true;
				String t = "";
				while (st.hasMoreTokens()) {
					t = st.nextToken();
					if (t.contains(alias)) {
						añadir = false;

						AnswerCallbackQuery rajao = new AnswerCallbackQuery();
						rajao.setText("Baia baia, un traidicionador...");
						rajao.setShowAlert(true);
						rajao.setCallbackQueryId(update.getCallbackQuery().getId());

						try {
							super.answerCallbackQuery(rajao);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					} else {
						resultado = resultado + "\n" + t;
					}
				}

				answer = resultado;

				if (añadir)
					answer = update.getCallbackQuery().getMessage().getText() + "\n" + alias + "\n";

			}
			crearBotones(chat_id, message_id, answer);

		}
	}

	public void crearBotones(long chat_id, long message_id, String answer) {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
		List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();

		rowInline.add(new InlineKeyboardButton().setText("¡Yo voy!").setCallbackData("meapunto"));
		rowsInline.add(rowInline);

		EditMessageText new_message = new EditMessageText().setChatId(chat_id).setMessageId(toIntExact(message_id))
				.setText(answer);

		markupInline.setKeyboard(rowsInline);

		new_message.setReplyMarkup(markupInline);
		try {
			execute(new_message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void mandarmensaje(long chat_id, String respuesta, long replyid) {

		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(respuesta).setReplyToMessageId((int) replyid);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void anclar(long chat_id, long message_id) {
		PinChatMessage p = new PinChatMessage();
		p.setChatId(chat_id);
		p.setMessageId((int) message_id);
		try {
			super.pinChatMessage(p);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBotUsername() {
		// Return bot username
		// If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
		return "Quedadeithor";
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return "484087298:AAHl6PyhwKCLj_KbhRUIIDIemiV9TSiTTRQ";
	}
}
