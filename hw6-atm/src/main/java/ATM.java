import java.util.HashMap;
import java.util.Map;

public class ATM {
    private final Map<Banknote, Integer> banknotes;

    public ATM() {
        banknotes = new HashMap<>();
    }

    public void loadBanknotes(Banknote banknote, int count) {
        banknotes.put(banknote, banknotes.getOrDefault(banknote, 0) + count);
    }

    public Map<Banknote, Integer> withdraw(int amount, CashDispenser dispenser) {
        return dispenser.dispense(amount, banknotes);
    }

    public int getBalance() {
        int balance = 0;

        for (Banknote banknote : banknotes.keySet()) {
            balance += banknote.denomination() * banknotes.get(banknote);
        }

        return balance;
    }
}
