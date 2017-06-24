import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class DeleteCommand extends BotCommand {
    private static final String LOGTAG = "DELETECOMMAND";

    public DeleteCommand(){super("delete", "Удалить торрент\nФормат: delete id_торрента");}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (UserList.users.contains(user.getId())) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Команда удалить");

            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
    }
}
