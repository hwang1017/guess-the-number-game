package learn;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Getter
@Component
public class GameImpl implements Game{

    @Getter(AccessLevel.NONE)
    private final NumberGenerator numberGenerator;

    private final int guessCount;

    private int number;
    private int smallest;
    private int biggest;
    private int remainingGuess;
    private boolean validNumberRange = true;

    @Setter
    private int guess;

    @Autowired
    public GameImpl(NumberGenerator numberGenerator, @GuessCount int guessCount) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
    }

    // -- init --
    @PostConstruct
    @Override
    public void reset() {
        smallest = numberGenerator.getMinNumber();
        biggest = numberGenerator.getMaxNumber();
        guess = numberGenerator.getMinNumber();;
        remainingGuess = guessCount;
        number = numberGenerator.next();
        log.debug("the number is {}", number);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("in Game preDestroy()");
    }


    @Override
    public void check() {
        checkValidNumberRange();
        if (validNumberRange) {
            if (guess > number) {
                biggest = guess - 1;
            }

            if (guess < number) {
                smallest = guess + 1;
            }
        }
        remainingGuess--;
    }

    @Override
    public boolean isGameWon() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuess <= 0;
    }

    // private method
    private void checkValidNumberRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }

}
