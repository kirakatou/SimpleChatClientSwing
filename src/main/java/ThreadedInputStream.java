import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

class ThreadedInputStream extends Thread {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final Queue<String> responses;
    private final CountDownLatch signal;

    ThreadedInputStream(Socket socket, Queue<String> responses, CountDownLatch signal) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(this.socket.getInputStream());
        this.responses = responses;
        this.signal = signal;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = inputStream.readUTF();
                synchronized (responses) {
                    responses.offer(response);
                    responses.notify();
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                break;
            }
        }

        try {
            this.inputStream.close();
            signal.countDown();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}