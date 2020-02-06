package com.trust.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Player {
    private Socket socket;

    public Player(String address, int port) throws IOException {
        // getting localhost ip
        InetAddress ip = InetAddress.getByName(address);

        // establish the connection with server port 5056
        socket = new Socket(ip, port);
    }

    public void doLogic() {
        try {
            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String serverText = dis.readUTF();
            System.out.println(serverText);

            DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

            // the following loop performs the exchange of
            // information between client and client handler
            int sent = 0;
            int received = 0;
            while (true) {
                String clientText = LocalDateTime.now().format(FOMATTER);
                dos.writeUTF(clientText);
                sent++;

                //what server sends
                serverText = dis.readUTF();
                System.out.println(serverText);
                received++;

                //pause for random seconds to imitate async talking
                try {
                    int seconds = 2000 + (int) (System.currentTimeMillis() % 5) * 1000;
                    System.out.println("[port=" + socket.getLocalPort() + "] paused for " + seconds / 1000 + "sec");
                    Thread.sleep(seconds);
                } catch (InterruptedException t) {
                }

                // If client sends more then 10 messages
                // and then break from the while loop
                if (sent >= 10 && (sent == received)) {
                    System.out.println("1.[Client] Closing this connection : " + socket);
                    break;
                }
            }

            // closing resources
            dis.close();
            dos.close();
            return;
        } catch (IOException t) {
            t.printStackTrace();

        }
    }
}