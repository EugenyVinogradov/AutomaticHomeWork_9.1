package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.dataHelper.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class VerificationPage {
    private SelenideElement verifyCode = $("[data-test-id=code] input");
    private SelenideElement loginButton = $("[data-test-id=action-verify]");
    private SelenideElement emptyCode = $x("//*[text()='Неверно указан код! Попробуйте ещё раз.']");
    private SelenideElement wrongCode = $x("//*[text()='Поле обязательно для заполнения']");

    public CardsPage cardsPage(DataHelper.VerificationCode code) {
        verifyCode.setValue(code.getCode());
        loginButton.click();
        return new CardsPage();
    }
}
