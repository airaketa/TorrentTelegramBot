import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;
import com.google.inject.Module;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AddCommand extends BotCommand{
    private static final String LOGTAG = "ADDCOMMAND";
    
    public AddCommand(){super("add", "Добавить новый торрент\nФормат: add ссылка_на_торрент");}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (UserList.users.contains(user.getId())) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Торрент успешно добавлен!");

            try {
                URL url = new URL(arguments[0]);

                Config config = new Config(){
                    @Override
                    public int getNumOfHashingThreads(){
                        return 8;
                    }
                };

                Module dhtModule = new DHTModule(new DHTConfig(){
                    @Override
                    public boolean shouldUseRouterBootstrap(){
                        return true;
                    }
                });

                absSender.sendMessage(answer);

                File targetDirectory = new File("C:\\Users\\Алексей\\IdeaProjects\\TelegramBot\\buf");

                Storage storage = new FileSystemStorage(targetDirectory);

                BtClient client = Bt.client()
                        .config(config)
                        .storage(storage)
                        .torrent(url)
                        .autoLoadModules()
                        .module(dhtModule)
                        .build();

                client.startAsync(state -> {
                    if (state.getPiecesRemaining() == 0){
                        client.stop();
                    }
                }, 1000).join();

            } catch (TelegramApiException e1) {
                BotLogger.error(LOGTAG, e1);
            }
            catch (ArrayIndexOutOfBoundsException e2){
                SendMessage errormsg = new SendMessage();
                errormsg.setChatId(chat.getId().toString());
                errormsg.setText("Ошибка: не указан url на торрент.");
                try {
                    absSender.sendMessage(errormsg);
                } catch (TelegramApiException e3) {
                    BotLogger.error(LOGTAG, e3);
                }
            }
            catch (MalformedURLException e4){
                SendMessage errormsg = new SendMessage();
                errormsg.setChatId(chat.getId().toString());
                errormsg.setText("Ошибка: Неверный url");
                try {
                    absSender.sendMessage(errormsg);
                } catch (TelegramApiException e3) {
                    BotLogger.error(LOGTAG, e3);
                }
            }
        }
    }
}
