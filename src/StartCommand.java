import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";

    public StartCommand() {
        super("start", "Запуск бота");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(!UserList.users.contains(user.getId())){
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Не леееезь бля дебил сука ебаный!");
            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
        else {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Добро пожаловать!");
            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
    }
}