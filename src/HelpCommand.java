import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class HelpCommand extends BotCommand {

    private static final String LOGTAG = "HELPCOMMAND";

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "Список всех команд");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        if (UserList.users.contains(user.getId())) {
            StringBuilder helpMessageBuilder = new StringBuilder("");

            helpMessageBuilder.append(commandRegistry.getRegisteredCommand("add").toString()).append("\n\n");
            helpMessageBuilder.append(commandRegistry.getRegisteredCommand("list").toString()).append("\n\n");
            helpMessageBuilder.append(commandRegistry.getRegisteredCommand("delete").toString()).append("\n\n");
            //helpMessageBuilder.append(commandRegistry.getRegisteredCommand("start").toString()).append("\n\n");
            //helpMessageBuilder.append(commandRegistry.getRegisteredCommand("stop").toString()).append("\n\n");
            //helpMessageBuilder.append(commandRegistry.getRegisteredCommand("help").toString()).append("\n\n");

            SendMessage helpMessage = new SendMessage();
            helpMessage.setChatId(chat.getId().toString());
            helpMessage.enableHtml(true);
            helpMessage.setText(helpMessageBuilder.toString());

            try {
                absSender.sendMessage(helpMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
    }
}