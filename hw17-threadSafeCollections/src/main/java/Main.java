public class Main {
    public static void main(String[] args) {
        MedianList medianList = new MedianList();
        medianList.add(1);
        medianList.add(2);
        medianList.add(3);
        medianList.add(4);
        medianList.add(5);
        System.out.println(medianList.size());
        medianList.remove(3);
        System.out.println(medianList.size());
        System.out.println(medianList.getMedian());
        medianList.printAllValues();

        MedianList medianList1 = new MedianList();
        System.out.println(medianList1.getMedian());

        MedianList medianList2 = new MedianList();

        for (int i = 0; i < 10_000_000; i++) {
            medianList2.add(i);
        }
        long startTime = System.currentTimeMillis();
        double median2 = medianList2.getMedian();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println(median2);
        System.out.println("Время выполнения операции: " + elapsedTime + " миллисекунд");
    }
}
