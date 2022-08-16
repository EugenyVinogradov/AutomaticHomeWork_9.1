package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.dataHelper.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


public class CardsPage {
    private SelenideElement heading = $x("//*[text()='Ваши карты']");
    private ElementsCollection cards = $$(".list__item div");

    private SelenideElement firstCardInfo = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement secondCardInfo = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private SelenideElement updateButton = $("[data-test-id=action-reload]");


    public void isPageExist() {
        heading.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }


    public TransfersPage depositActionFirstCard() {
        firstCardButton.click();
        return new TransfersPage();
    }

    public TransfersPage depositActionSecondCard() {
        secondCardButton.click();
        return new TransfersPage();
    }

    public SelenideElement getFirstCardInfo() {
        firstCardInfo.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return firstCardInfo;
    }

    public SelenideElement getSecondCardInfo() {
        secondCardInfo.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return secondCardInfo;
    }

    public String getCardBalance(String id) {
        String text = "";
        for (SelenideElement card : cards) {
            if (card.getAttribute("data-test-id").equals(id)) {
                String balance = card.getText().split(":")[1].split("р")[0].trim();
                text = balance;
            }
        }
        return text;
    }

    public CardsPage updateCardsInfo() {
        updateButton.click();
        return new CardsPage();
    }


}