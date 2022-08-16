package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.AuthPage;
import ru.netology.dataHelper.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;


public class Tests {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    String sumTransfer = "5000";
    String sumTransferIsNotIntegerDigitAmountMoreDigitBalance = "9999.99";
    String sumTransferIsNotIntegerDigitAmountEqualDigitBalance = "999.99";
    String sumTransferMoreBalance = "100000";
    String sumTransferIsEmpty = "";
    String sumTransferIsNull = "0";
    String sumTransferIsNegative = "-1000";
    String sumTransferIsNotValidValue = "aaa";

    @Test
    void shouldTransferFromFirstCardToSecondCardOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo();
        float balanceFirstCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float balanceSecondCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard + Float.parseFloat(sumTransfer);
        float expected2 = balanceSecondCard - Float.parseFloat(sumTransfer);
        float actual1 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float actual2 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromSecondCardToFirstCardOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        float balanceFirstCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float balanceSecondCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getFirstCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard - Float.parseFloat(sumTransfer);
        float expected2 = balanceSecondCard + Float.parseFloat(sumTransfer);
        float actual1 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float actual2 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstCardToSecondCardIfSumTransferIsNotIntegerDigitAmountMoreDigitBalance() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo();
        float balanceFirstCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float balanceSecondCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNotIntegerDigitAmountMoreDigitBalance, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard + Float.parseFloat(sumTransferIsNotIntegerDigitAmountMoreDigitBalance);
        float expected2 = balanceSecondCard - Float.parseFloat(sumTransferIsNotIntegerDigitAmountMoreDigitBalance);
        float actual1 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float actual2 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstCardToSecondCardIfSumTransferIsNotIntegerDigitAmountEqualDigitBalance() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo();
        float balanceFirstCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float balanceSecondCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNotIntegerDigitAmountEqualDigitBalance, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard + Float.parseFloat(sumTransferIsNotIntegerDigitAmountEqualDigitBalance);
        float expected2 = balanceSecondCard - Float.parseFloat(sumTransferIsNotIntegerDigitAmountEqualDigitBalance);
        float actual1 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float actual2 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void shouldNotTransferFromSecondCardToFirstCardIfNotEnoughMoney() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferMoreBalance, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorNotEnoughMoney();
    }

    @Test
    void shouldNotTransferFromSecondCardToFirstICardIfNotSumMoney() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsEmpty, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }

    @Test
    void shouldNotTransferFromSecondCardToFirstCardIfNegativeSumAmount() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNegative, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }

    @Test
    void shouldNotTransferFromSecondCardToFirstCardIfSumAmountIsNull() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNull, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }

    @Test
    void shouldNotTransferFromSecondCardToFirstCardIfSumTransferIsNotValidValue() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNotValidValue, DataHelper.getFirstCardsInfo().getCardNumber());
        System.out.println(sumTransferIsNotValidValue);
        transferPage.errorNotValidValue();
    }

    @Test
    void checkButtonUpdateBalance() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo();
        float balanceFirstCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float balanceSecondCard = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        cardsPage.updateCardsInfo();
        float actual1 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getFirstCardsInfo().getId()));
        float actual2 = Float.parseFloat(cardsPage.getCardBalance(DataHelper.getSecondCardsInfo().getId()));
        Assertions.assertEquals(balanceFirstCard, actual1);
        Assertions.assertEquals(balanceSecondCard, actual2);
    }
}
