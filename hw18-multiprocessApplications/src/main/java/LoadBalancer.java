import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class LoadBalancer {
    private static final List<NodeInfo> nodes = new ArrayList<>();

    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            properties.load(new FileReader("hw18-multiprocessApplications/src/main/java/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int registrationPort = Integer.parseInt(properties.getProperty("registration.port"));
        int userRequestsPort = Integer.parseInt(properties.getProperty("user.requests.port"));
        int fixedThreadsCount = Integer.parseInt(properties.getProperty("fixed.threads.count"));

        // Запуск сервера LoadBalancer на порту registrationPort для регистрации Node
        new Thread(() -> {
            try (ServerSocket registrationSocket = new ServerSocket(registrationPort)) {
                while (true) {
                    Socket nodeSocket = registrationSocket.accept();
                    try (BufferedReader registerInput = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()))) {
                        String registerCommand = registerInput.readLine();
                        if ("REGISTER".equals(registerCommand)) {
                            int nodePort = Integer.parseInt(registerInput.readLine());
                            NodeInfo nodeInfo = new NodeInfo(nodeSocket.getInetAddress().getHostAddress(), nodePort);
                            nodes.add(nodeInfo);
                            System.out.println("Node registered: " + nodeInfo);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        ExecutorService executorService = Executors.newFixedThreadPool(fixedThreadsCount);

        // Запуск сервера LoadBalancer на порту userRequestsPort для обработки запросов от пользователей
        try (ServerSocket serverSocket = new ServerSocket(userRequestsPort)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> {
                    try (
                            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
                    ) {
                        String request = input.readLine();
                        String response = processRequest(request);
                        output.println(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String processRequest(String request) {
        StringBuilder response = new StringBuilder();

        int nodesSize = nodes.size();

        if (nodesSize == 0) {
            return "No available nodes";
        }

        // Случайным образом выбираем индекс ноды
        int randomNodeIndex = ThreadLocalRandom.current().nextInt(nodesSize);
        NodeInfo selectedNode = nodes.get(randomNodeIndex);

        try (
                Socket nodeSocket = new Socket(selectedNode.getHost(), selectedNode.getPort());
                PrintWriter nodeOutput = new PrintWriter(nodeSocket.getOutputStream(), true);
                BufferedReader nodeInput = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()))
        ) {
            nodeOutput.println(request);
            String nodeResponse = nodeInput.readLine();
            response.append(nodeResponse).append("\n");
        } catch (IOException e) {
            System.err.println("Node " + selectedNode + " did not respond.");
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
