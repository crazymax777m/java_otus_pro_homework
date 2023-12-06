package otus.study.cashmachine.bank.resources;

import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/withdraw")
public class WithdrawResource {
    private final CardService cardService;

    public WithdrawResource() {
        this.cardService = new CardServiceImpl(new AccountServiceImpl(), new CardsDao());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String withdrawMoney(
            @FormParam("cardNumber") String cardNumber,
            @FormParam("pinCode") String pinCode,
            @FormParam("amount") BigDecimal amount
    ) {
        try {
            BigDecimal remainingBalance = cardService.getMoney(cardNumber, pinCode, amount);
            return "Money withdrawn successfully. Remaining balance: " + remainingBalance;
        } catch (IllegalArgumentException e) {
            return "Failed to withdraw money: " + e.getMessage();
        }
    }
}
