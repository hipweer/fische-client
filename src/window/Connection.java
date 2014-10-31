package window;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class Connection implements Runnable {

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    private static Connection connection;

    private Connection(){
    }

    public static Connection getInstance(){
        if (connection == null){
            connection = new Connection();
        }
        return connection;
    }

    public static void sendLocation(float x, float y){
        if (os != null)
        os.print("//location "+ x + " " + y);
    }

    public static Connection connect(){
        getInstance();
        try {
            clientSocket = new Socket(Game.host, Game.portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + Game.host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + Game.portNumber);
        }
        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new Connection()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
        return connection;
    }
    private static void close(){
        try {
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Bye") != -1)
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
