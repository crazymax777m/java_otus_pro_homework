package otus.study.cashmachine.bank.resources;

import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/balance")
public class BalanceResource {
    private final CardService cardService;

    public BalanceResource() {
        this.cardService = new CardServiceImpl(new AccountServiceImpl(), new CardsDao());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkBalance(
            @QueryParam("cardNumber") String cardNumber,
            @QueryParam("pinCode") String pinCode
    ) {
        try {
            BigDecimal balance = cardService.getBalance(cardNumber, pinCode);
            return "Current balance: " + balance;
        } catch (IllegalArgumentException e) {
            return "Failed to check balance: " + e.getMessage();
        }
    }
}
