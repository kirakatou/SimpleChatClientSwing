import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import javax.swing.text.BadLocationException;

public class ChatClient {
    public static void main(String []args) throws IOException, BadLocationException {
//        final Socket socket = new Socket("localhost", 1286);
//        final CountDownLatch doneSignal = new CountDownLatch(2);
        final ChatSwing chat = new ChatSwing();
        chat.setVisible(true);
        chat.initialize();

        // start input and output thread
//        final ThreadedInputStream inputStream = new ThreadedInputStream(socket, doneSignal);
//        final ThreadedOutputStream outputStream = new ThreadedOutputStream(socket, doneSignal);

//        inputStream.start();
//        outputStream.start();

        //while (inputStream.isAlive() && outputStream.isAlive());
//        try {
//            doneSignal.await();
//        } catch (InterruptedException e) {
//        }
//
//        System.out.println("Disconnected from server");
//        socket.close();
    }
}
