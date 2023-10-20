import java.util.HashMap;
import java.util.Map;

public class CashDispenserImpl implements CashDispenser {
    @Override
    public Map<Banknote, Integer> dispense(int amount, Map<Banknote, Integer> availableBanknotes) {
        Map<Banknote, Integer> result = new HashMap<>();
        int remainingAmount = amount;

        Map<Banknote, Integer> availableBanknotesCopy = new HashMap<>(availableBanknotes);

        for (Banknote banknote : availableBanknotesCopy.keySet()) {
            int denomination = banknote.denomination();
            int availableNotes = availableBanknotesCopy.get(banknote);

            if (remainingAmount >= denomination && availableNotes > 0) {
                int notesToWithdraw = Math.min(remainingAmount / denomination, availableNotes);

                result.put(banknote, notesToWithdraw);
                availableBanknotesCopy.put(banknote, availableNotes - notesToWithdraw);
                remainingAmount -= notesToWithdraw * denomination;
            }
        }

        if (remainingAmount == 0) {
            availableBanknotes.putAll(availableBanknotesCopy);
            return result;
        } else {
            return new HashMap<>();
        }
    }
}
