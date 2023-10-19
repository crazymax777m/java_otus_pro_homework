import java.util.Map;

public interface CashDispenser {
    Map<Banknote, Integer> dispense(int amount, Map<Banknote, Integer> availableBanknotes);
}
