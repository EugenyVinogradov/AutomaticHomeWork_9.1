package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransfersPage {
    private SelenideElement heading = $x("//h1[contains(text(),'Пополнение карты')]");
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement transferFrom = $("[data-test-id=from] input");
    private SelenideElement transferTo = $("[data-test-id=to]");
    private SelenideElement actionButton = $("[data-test-id=action-transfer].button");
    private SelenideElement chancelButton = $("[data-test-id=action-cancel].button");
    private SelenideElement errorNotEnoughMoney = $x("//*[text() = 'Ошибка! Недостаточно средств для проведения операции!']");
    private SelenideElement errorEnterSumAmount = $x("//*[text() = 'Введите сумму перевода больше нуля']");
    private SelenideElement errorNotValidValue = $x("//*[text() = 'Ошибка! Можно вводить только цифры!']");


    public void isPageExist() {
        heading.shouldBe(Condition.visible);
    }
    public void errorNotEnoughMoney() {
        errorNotEnoughMoney.shouldBe(Condition.visible);
    }
    public void errorEnterSumAmount() {
        errorEnterSumAmount.shouldBe(Condition.visible);
    }
    public void errorNotValidValue() {
        errorNotValidValue.shouldBe(Condition.visible);
    }

    public CardsPage transfer(String sum, String cardNumber) {
        amount.setValue(String.valueOf(sum));
        transferFrom.setValue(cardNumber);
        actionButton.click();
        return new CardsPage();
    }
}
