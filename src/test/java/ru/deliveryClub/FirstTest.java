package ru.deliveryClub;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;

public class FirstTest extends WebDriverSettings {

    @Test
    public void testFoodOrder() {

        //Открываем страницу
        driver.get("https://www.delivery-club.ru/");

        //Проверяем верность title страницы
        String title = "Быстрая доставка еды Delivery Club";
        assertEquals(title, driver.getTitle());

        //Кликаем 'Добавить новый адрес'
        click(By.className("DesktopAddressButton_address"));

        //Если геолокация подставила автоматическим адрес, то кликаем крестик
        try {
            driver.findElement(By.xpath("//span[text()='Нет']")).click();
        } catch (Exception ignored) {}
        try {
            driver.findElement(By.xpath("//div[@data-testid='address-button-add']")).click();
        } catch (Exception ignored) {}
        try {
            driver.findElement(By.cssSelector("svg.UiKitUiKitIcon_m.UiKitUiKitIcon_root.AppAddressInput_closeIcon")).click();
        } catch (Exception ignored) {}

        //Вводим адрес для доставки
        inputText("Брянск, Советская улица, 110, подъезд 2", By.xpath("//input[@data-testid='address-input']"));

        //Подтверждаем введенный адрес
        click(By.xpath("//button[@data-testid='desktop-location-modal-confirm-button']"));

        //Выбираем ресторан
        inputText("Крошка картошка", By.xpath("//input[@data-testid='search-input']"));
        click(By.xpath("//h2[text()='Крошка картошка']"));

        //Ждем подтверждения загрузки страницы с новым title
        title = "Крошка картошка заказать доставку еды. Меню ресторана «Крошка картошка» в Брянске";
        wait.until(ExpectedConditions.titleIs(title));

        //Выбираем пункт меню
        click(By.xpath("//li[text()='Гриль Чиз']"));

        //Выбираем позицию
        click(By.xpath("//div[text()='Гриль-чиз Перечный жюльен']"));

        //Проверяем описание выбранной позиции
        try{
            Assert.assertEquals("Картофель, сыр творожный, жюльен перечный с курицей и грибами, сыр моцарелла"
                    , driver.findElement(By.cssSelector("div.HTMLDescription_root.ModalMenuItemOptions_htmlDescription")).getText());
        } catch (Exception ignored) {

            //В случае отказа по времени доставки тест завершается
            driver.findElement(By.xpath("//button[@data-testid='uikit-confirm-modal-cancel']")).click();
            return;
        }

        //Дублируем заказ
        click(By.xpath("//div[@data-testid='ui-counter-plus-button']"));

        //Проверяем стоимость
        checkText("630 ₽", By.cssSelector("div.ModalMenuItemOptions_totalValue > span"));

        //Добавляем заказ в корзину
        click(By.xpath("//button[@data-testid='desktop-menu-item-options-confirm']"));

        //Выбираем пункт меню
        click(By.xpath("//li[text()='Супы']"));

        //Выбираем позицию
        click(By.xpath("//div[text()='Борщ']"));

        //Проверяем описание позиции
        checkText("Картофель, свекла, морковь, капуста, томатная паста, томаты в собственном соку, лук жареный, петрушка, соль, масло растительное, сахар, уксус, бульон куриный, куриное филе, говядина."
                + "\nНа 100 граммов: К 34, Б 2, Ж 1, У 5"
        ,By.cssSelector("div.HTMLDescription_root.ModalMenuItemOptions_htmlDescription"));

        //Добавляем ингридиент
        click(By.xpath("//span[text()='Майонез']"));

        //Дублируем заказ
        click(By.xpath("//div[@data-testid='ui-counter-plus-button']"));

        //Проверяем стоимость
        checkText("330 ₽", By.cssSelector("div.ModalMenuItemOptions_totalValue > span"));

        //Добавляем заказ в корзину
        click(By.xpath("//button[@data-testid='desktop-menu-item-options-confirm']"));

        //Открываем корзину
        click(By.cssSelector("span.DesktopHeader_cartButtonText"));

        //Проверяем описание позиций
        checkText("Гриль-чиз Перечный жюльен", By.xpath("//div[@class='MobileUIShadowableScrollBox_content NewCartContent_content']/div[2]/div[2]/div[1]"));
        checkText("Борщ", By.xpath("//div[@class='MobileUIShadowableScrollBox_content NewCartContent_content']/div[3]/div[2]/div[1]"));

    //Кликаем 'Далее'
        click(By.xpath("//button[@class='UiKitButton_root UiKitButton_size-l UiKitButton_variant-action UiKitButton_shape-default UiKitButton_width-full UiKitButton_theme-dc NewCartPriceButton_button']"));

        //Ждем подтверждения загрузки страницы с новым title
        title = "Авторизация";
        wait.until(ExpectedConditions.titleIs(title));

        //Кликаем 'Далее'
        click(By.id("passp:phone:controls:next"));

        //Проверяем описание ошибки
        checkText("Недопустимый формат номера", By.id("field:input-phone:hint"));

    }
}
