import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Banknote banknote1 = new Banknote(100);
        Banknote banknote2 = new Banknote(50);
        Banknote banknote3 = new Banknote(20);

        ATM atm = new ATM();

        atm.loadBanknotes(banknote1, 10);
        atm.loadBanknotes(banknote2, 20);
        atm.loadBanknotes(banknote3, 30);

        CashDispenser dispenser = new CashDispenserImpl();

        int amountToWithdraw = 350;
        Map<Banknote, Integer> withdrawalResult = atm.withdraw(amountToWithdraw, dispenser);

        if (!withdrawalResult.isEmpty()) {
            System.out.println("Успешно выдано: $" + amountToWithdraw);
            for (Banknote banknote : withdrawalResult.keySet()) {
                int count = withdrawalResult.get(banknote);
                System.out.println(banknote.denomination() + "$: " + count);
            }
        } else {
            System.out.println("Невозможно выдать $" + amountToWithdraw);
        }

        int atmBalance = atm.getBalance();
        System.out.println("Баланс банкомата: $" + atmBalance);
    }
}