import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            File file = new File("C:\\Users\\Max\\IdeaProjects\\java_otus_pro_homework\\hw8-serialization\\src\\main\\java\\sms.json");
            ChatSessionsData chatSessionsData = objectMapper.readValue(new FileInputStream(file), ChatSessionsData.class);

            for (ChatSession chatSession : chatSessionsData.getChat_sessions()) {
                System.out.println("Chat ID: " + chatSession.getChat_id());
                System.out.println("Chat Identifier: " + chatSession.getChat_identifier());

                for (Member member : chatSession.getMembers()) {
                    System.out.println("Member First Name: " + member.getFirst());
                    System.out.println("Member Phone Number: " + member.getPhone_number());
                }

                for (Message message : chatSession.getMessages()) {
                    System.out.println("Message Text: " + message.getText());
                    System.out.println("Message Date: " + message.getSend_date());
                }
            }

            // Создаем формат для парсинга даты
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

            // Создаем Map для группировки записей по полю chat_sessions.messages.belong_number
            Map<String, List<CustomEntry>> groupedEntries = new HashMap<>();

            // Перебираем chat_sessions
            for (ChatSession chatSession : chatSessionsData.getChat_sessions()) {
                for (Message message : chatSession.getMessages()) {
                    String chatIdentifier = chatSession.getChat_identifier();
                    String memberLast = chatSession.getMembers().get(0).getLast();
                    String belongNumber = message.getBelong_number();
                    String sendDateStr = message.getSend_date();
                    String text = message.getText();

                    // Парсим дату
                    Date sendDate = null;
                    try {
                        sendDate = dateFormat.parse(sendDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Создаем уникальный ключ для записи (чтобы не дублировались)
                    String uniqueKey = chatIdentifier + memberLast + belongNumber;

                    // Проверяем, есть ли уже запись с таким ключом
                    CustomEntry customEntry = new CustomEntry(uniqueKey, chatIdentifier, memberLast, belongNumber, sendDate, text);

                    // Добавляем запись в соответствующую группу
                    groupedEntries.computeIfAbsent(belongNumber, k -> new ArrayList<>()).add(customEntry);
                }
            }

            // Сортируем записи в каждой группе по дате (от старых к новым)
            for (List<CustomEntry> entries : groupedEntries.values()) {
                entries.sort(Comparator.comparing(CustomEntry::getSendDate));
            }

            // Выводим результат
            groupedEntries.forEach((belongNumber, entries) -> {
                System.out.println("Belong Number: " + belongNumber);
                for (CustomEntry entry : entries) {
                    System.out.println("Chat Identifier: " + entry.getChatIdentifier());
                    System.out.println("Member Last Name: " + entry.getMemberLast());
                    System.out.println("Send Date: " + entry.getSendDateStr());
                    System.out.println("Text: " + entry.getText());
                    System.out.println();
                }
            });

            String jsonFilePath = "output.json";
            objectMapper.writeValue(new File(jsonFilePath), groupedEntries);


            System.out.println("Данные успешно сериализованы в JSON в файл " + jsonFilePath);

            // Десериализуем данные из файла
            Map<String, List<CustomEntry>> groupedEntriesFromJSON = objectMapper.readValue(
                    new File(jsonFilePath),
                    new TypeReference<>() {
                    }
            );

            // Выводим данные в консоль
            groupedEntriesFromJSON.forEach((belongNumber, entries) -> {
                System.out.println("Belong Number: " + belongNumber);
                for (CustomEntry entry : entries) {
                    System.out.println("Chat Identifier: " + entry.getChatIdentifier());
                    System.out.println("Member Last Name: " + entry.getMemberLast());
                    System.out.println("Send Date: " + entry.getSendDateStr());
                    System.out.println("Text: " + entry.getText());
                    System.out.println();
                }
            });

            // Сериализация данных в XML
            ObjectMapper xmlMapper = XMLMapperProvider.getXMLMapper();
            xmlMapper.writeValue(new File("output.xml"), groupedEntries);
            System.out.println("Данные успешно сериализованы в файл output.xml");

            Map<String, List<CustomEntry>> groupedEntriesFromXML = xmlMapper.readValue(
                    new File("output.xml"),
                    new TypeReference<>() {
                    }
            );

            // Вывод данных в консоль
            groupedEntriesFromXML.forEach((belongNumber, entries) -> {
                System.out.println("Belong Number: " + belongNumber);
                for (CustomEntry entry : entries) {
                    System.out.println("Chat Identifier: " + entry.getChatIdentifier());
                    System.out.println("Member Last Name: " + entry.getMemberLast());
                    System.out.println("Send Date: " + entry.getSendDateStr());
                    System.out.println("Text: " + entry.getText());
                    System.out.println();
                }
            });

            // Создаем файл для записи CSV
            String csvFilePath = "output.csv";
            FileWriter writer = new FileWriter(csvFilePath);

            // Создаем объект для записи CSV
            CSVWriter csvWriter = new CSVWriter(writer);

            // Записываем данные
            groupedEntries.forEach((belongNumber, entries) -> {
                for (CustomEntry entry : entries) {
                    String[] data = {
                            belongNumber,
                            entry.getChatIdentifier(),
                            entry.getMemberLast(),
                            entry.getSendDateStr(),
                            entry.getText()
                    };
                    csvWriter.writeNext(data);
                }
            });

            csvWriter.close();
            System.out.println("Данные успешно сериализованы в файл " + csvFilePath);

            // Создаем объект для чтения CSV
            CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));

            // Читаем данные
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String belongNumber = nextRecord[0];
                String chatIdentifier = nextRecord[1];
                String memberLast = nextRecord[2];
                String sendDateStr = nextRecord[3];
                String text = nextRecord[4];

                // Вывод данных в консоль
                System.out.println("Belong Number: " + belongNumber);
                System.out.println("Chat Identifier: " + chatIdentifier);
                System.out.println("Member Last Name: " + memberLast);
                System.out.println("Send Date: " + sendDateStr);
                System.out.println("Text: " + text);
                System.out.println();
            }

            csvReader.close();

            // Создаем объект Yaml
            Yaml yaml = new Yaml();

            // Сериализуем данные
            String yamlData = yaml.dump(groupedEntries);

            // Записываем данные в файл YAML
            String yamlFilePath = "output.yml";
            try (FileWriter writer = new FileWriter(yamlFilePath)) {
                writer.write(yamlData);
            }

            System.out.println("Данные успешно сериализованы в файл " + yamlFilePath);

            // Десериализуем данные
            try (FileReader reader = new FileReader(yamlFilePath)) {
                Map<String, List<CustomEntry>> deserializedData = yaml.load(reader);

                // Выводим данные в консоль
                deserializedData.forEach((belongNumber, entries) -> {
                    System.out.println("Belong Number: " + belongNumber);
                    for (CustomEntry entry : entries) {
                        System.out.println("Chat Identifier: " + entry.getChatIdentifier());
                        System.out.println("Member Last Name: " + entry.getMemberLast());
                        System.out.println("Send Date: " + entry.getSendDateStr());
                        System.out.println("Text: " + entry.getText());
                        System.out.println();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
