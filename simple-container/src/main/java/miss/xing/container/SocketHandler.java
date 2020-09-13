package miss.xing.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class SocketHandler extends Thread {

    private Socket socket;

    public SocketHandler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = in.readLine();
            }

            out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println(); // \r\n

            out.println("<html><body>Current Time: ");
            out.println(LocalDateTime.now());
            out.println("</body></html>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
