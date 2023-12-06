package otus.study.cashmachine.bank.resources;

import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/changePin")
public class ChangePinResource {
    private final CardService cardService;

    public ChangePinResource() {
        this.cardService = new CardServiceImpl(new AccountServiceImpl(), new CardsDao());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String changePin(
            @FormParam("cardNumber") String cardNumber,
            @FormParam("oldPin") String oldPin,
            @FormParam("newPin") String newPin
    ) {
        try {
            if (cardService.changePin(cardNumber, oldPin, newPin)) {
                return "Pin code changed successfully";
            } else {
                return "Failed to change pin code. Please check your old pin.";
            }
        } catch (IllegalArgumentException e) {
            return "Failed to change pin code: " + e.getMessage();
        }
    }
}
