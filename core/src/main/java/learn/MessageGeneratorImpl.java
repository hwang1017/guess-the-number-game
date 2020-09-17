package learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator{

    private final Game game;

    public MessageGeneratorImpl(Game game) {
        this.game = game;
    }

    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }

    @Override
    public String getMainMessage() {
        return "Number is between " + game.getSmallest()
                + " and " + game.getBiggest() + ". Can you guess it?";
    }

    @Override
    public String getResultMessage() {
        if (!game.isValidNumberRange()) return "Invalid number range!";
        else if (game.isGameWon()) return "You win!";
        else if (game.isGameLost()) return "You lose...";
        else if (game.getRemainingGuess() == game.getGuessCount()) return "What is your first guess?";
        else {
            String dir = "Lower";
            if (game.getGuess() < game.getNumber()) dir = "Higher";
            return dir + "!     You have " + game.getRemainingGuess() + " guesses left...";
        }
    }
}
