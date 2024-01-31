import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Node {
    public static void main(String[] args) {
        int port = getPortFromArgs(args);

        if (port == -1) {
            System.err.println("Invalid input. Please provide a valid integer port number.");
            return;
        }

        registerNode(port);
        handleRequests(port);
    }

    private static int getPortFromArgs(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return -1;
        }
    }

    private static void registerNode(int port) {
        int registrationPort = getRegistrationPortFromConfig();
        if (registrationPort == -1) {
            System.err.println("Error reading registration port from config.");
            return;
        }

        try (Socket registerSocket = new Socket("localhost", registrationPort);
             PrintWriter registerOutput = new PrintWriter(registerSocket.getOutputStream(), true)) {
            registerOutput.println("REGISTER");
            registerOutput.println(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequests(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    String request = input.readLine();
                    String response = "Hello from Node on port " + port;
                    output.println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRegistrationPortFromConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("config.properties"));
            return Integer.parseInt(properties.getProperty("registration.port"));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
