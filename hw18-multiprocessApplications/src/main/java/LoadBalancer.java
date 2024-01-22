import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {
    private static final List<NodeInfo> nodes = new ArrayList<>();

    public static void main(String[] args) {
        // Запуск сервера LoadBalancer на порту 8080 для регистрации Node
        new Thread(() -> {
            try {
                ServerSocket registrationSocket = new ServerSocket(8080);
                while (true) {
                    Socket nodeSocket = registrationSocket.accept();
                    BufferedReader registerInput = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));
                    String registerCommand = registerInput.readLine();
                    if ("REGISTER".equals(registerCommand)) {
                        int nodePort = Integer.parseInt(registerInput.readLine());
                        NodeInfo nodeInfo = new NodeInfo(nodeSocket.getInetAddress().getHostAddress(), nodePort);
                        nodes.add(nodeInfo);
                        System.out.println("Node registered: " + nodeInfo);
                    }
                    registerInput.close();
                    nodeSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Запуск сервера LoadBalancer на порту 9091 для обработки запросов от пользователей
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(9091);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String request = input.readLine();
                    String response = processRequest(request);
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    output.println(response);
                    output.close();
                    input.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        startNode(9092);
        startNode(9093);
        startNode(9094);
    }

    private static void startNode(int port) {
        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("java", "Node", String.valueOf(port));
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static String processRequest(String request) {
        StringBuilder response = new StringBuilder();
        for (NodeInfo node : nodes) {
            try {
                Socket nodeSocket = new Socket(node.getHost(), node.getPort());
                PrintWriter nodeOutput = new PrintWriter(nodeSocket.getOutputStream(), true);
                nodeOutput.println(request);
                BufferedReader nodeInput = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));
                String nodeResponse = nodeInput.readLine();
                response.append(nodeResponse).append("\n");
                nodeInput.close();
                nodeOutput.close();
                nodeSocket.close();
            } catch (IOException e) {
                System.err.println("Node " + node + " did not respond.");
            }
        }
        return response.toString().trim();
    }

    private static class NodeInfo {
        private final String host;
        private final int port;

        public NodeInfo(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        @Override
        public String toString() {
            return "NodeInfo{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    '}';
        }
    }
}
