import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Node {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]); // Передаем порт как аргумент командной строки
        registerNode(port);
        handleRequests(port);
    }

    private static void registerNode(int port) {
        try {
            Socket registerSocket = new Socket("localhost", 8080);
            PrintWriter registerOutput = new PrintWriter(registerSocket.getOutputStream(), true);
            registerOutput.println("REGISTER");
            registerOutput.println(port);
            registerOutput.close();
            registerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequests(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request = input.readLine();
                String response = "Hello from Node on port " + port;
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                output.println(response);
                output.close();
                input.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
