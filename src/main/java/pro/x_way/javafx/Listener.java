package pro.x_way.javafx;

import pro.x_way.javafx.Model.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Listener {
    private static Listener ourInstance = new Listener();
    private BlockingQueue<Message> abq = new ArrayBlockingQueue<>(20);

    public static Listener getInstance() {
        return ourInstance;
    }

    private Listener() {
    }

    public BlockingQueue<Message> getAbq() {
        return abq;
    }
}
