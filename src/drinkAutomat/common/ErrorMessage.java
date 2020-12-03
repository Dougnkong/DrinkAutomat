package drinkAutomat.common;

public enum ErrorMessage {

    AUTOMATE_EMPTY("The automate is empty"),
    NO_CHANGE_COIN("Not enough coin to change"),
    NO_MONEY_GAVE("No money is giving for the drink"),
    EMPTY_CHOICE("The drink you choose is empty"),
    NO_DRINK_CHOOSE("Please choose you drink first");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }


}
