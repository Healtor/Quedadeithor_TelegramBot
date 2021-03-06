package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Quedadeithor_Main {
	
  public static void main(String[] args) {
      // Initialize Api Context
      ApiContextInitializer.init();

      // Instantiate Telegram Bots API
      TelegramBotsApi botsApi = new TelegramBotsApi();

      // Register our bot
      try {
          botsApi.registerBot(new Quedadeithor());
      } catch (TelegramApiException e) {
          e.printStackTrace();
      }
  }
}
