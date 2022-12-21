package ru.deliveryClub;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SecondTest extends WebDriverSettings {

    @Test
    public static void testMoskovDelivery() {

        //Вводим адрес для доставки
        inputText("Москва, Октябрьская улица, 105к1", By.xpath("//input[@data-testid='address-input']"));

        //Открываем все магазины
        click(By.xpath("//button[@data-testid='ui-button']"));

        //Выбираем КуулКлевер
        click(By.xpath("//a[@href='/retail/moscow/kuulklever?placeSlug=myasnov_ot_kuulklever_dfvwz']"));

        //Проверяем верность title страницы
        String title = "КуулКлевер. Продукты МясновЪ — доставка продуктов на дом и в офис от 30 минут в Москве";
        wait.until(ExpectedConditions.titleIs(title));

        //Ищем в поисковой строке позицию и добавляем ее в корзину
        inputText("Бананы", By.xpath("//input[@data-testid='search-input']"));
        click(By.xpath("//div[@title='Бананы']//..//button[@data-testid='amount-select-increment']"));

        //Очищаем посковую строку
        click(By.xpath("//button[@data-testid='input-clear-button']"));

        //Ищем в поисковой строке позицию и добавляем ее в корзину
        inputText("Зайчонок", By.xpath("//input[@data-testid='search-input']"));
        click(By.xpath("//div[@title='Фигурка шоколадная Зайчонок МясновЪ Пекарня']//..//button[@data-testid='amount-select-increment']"));

        //Открываем корзину
        click(By.xpath("//button[@data-testid='desktop-retail-header-cart-button']"));

        //Проверяем описание позиций
        checkText("Бананы", By.xpath("//div.CartPopupContent_orderItems[0]//h3[@data-testid='desktop-checkout-order-item-name']"));
        checkText("Фигурка шоколадная Зайчонок МясновЪ Пекарня", By.xpath("//div.CartPopupContent_orderItems[1]//h3[@data-testid='desktop-checkout-order-item-name']"));

        //Кликаем 'Далее'
        click(By.xpath("//div[text()='Верно, далее']"));

        //Ждем подтверждения загрузки страницы с новым title
        title = "Авторизация";
        wait.until(ExpectedConditions.titleIs(title));

        //Кликаем 'Далее'
        click(By.id("passp:phone:controls:next"));

        //Проверяем описание ошибки
        checkText("Недопустимый формат номера", By.id("field:input-phone:hint"));

    }
}
