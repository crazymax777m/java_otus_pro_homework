package otus.study.cashmachine.bank.resources;

import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/deposit")
public class DepositResource {
    private final CardService cardService;

    public DepositResource() {
        this.cardService = new CardServiceImpl(new AccountServiceImpl(), new CardsDao());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String depositMoney(
            @FormParam("cardNumber") String cardNumber,
            @FormParam("pinCode") String pinCode,
            @FormParam("amount") BigDecimal amount
    ) {
        try {
            BigDecimal newBalance = cardService.putMoney(cardNumber, pinCode, amount);
            return "Money deposited successfully. New balance: " + newBalance;
        } catch (IllegalArgumentException e) {
            return "Failed to deposit money: " + e.getMessage();
        }
    }
}
