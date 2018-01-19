import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

class ThreadedOutputStream extends Thread {
    private final Socket socket;
    private final DataOutputStream outputStream;
    private final Queue<String> requests;
    private final CountDownLatch signal;

    ThreadedOutputStream(Socket socket, Queue<String> requests,CountDownLatch signal) throws IOException {
        this.socket = socket;
        this.outputStream = new DataOutputStream(this.socket.getOutputStream());
        this.requests = requests;
        this.signal = signal;
    }

    @Override
    public void run() {
        while (true) {
            String output = null;
            
            synchronized (requests) {
                output = requests.poll();
                if (output == null) {
                    try {
                        requests.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            try {
                if(output != null){
                    outputStream.writeUTF(output);
                }
            } catch (IOException e) {
                break;
            }
        }

        try {
            outputStream.close();
            signal.countDown();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}