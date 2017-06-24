import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class StopCommand extends BotCommand {

    public static final String LOGTAG = "STOPCOMMAND";

    public StopCommand() {
        super("stop", "Отключение бота");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (UserList.users.contains(user.getId())) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Завершение работы");

            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
    }
}