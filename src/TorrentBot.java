import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;

public class TorrentBot extends TelegramLongPollingCommandBot{
    public TorrentBot() {
        register(new StartCommand());
        register(new StopCommand());
        register(new DeleteCommand());
        register(new ListCommand());
        register(new AddCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
    }

    @Override
    public String getBotUsername(){
        return "airaketa_torrent_bot";
    }

    @Override
    public String getBotToken(){
        return "";
    }
}
