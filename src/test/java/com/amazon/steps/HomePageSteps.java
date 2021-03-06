package com.amazon.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomePageSteps extends BaseSteps {

    @When("user chooses to search by (.*)")
    public void userChoosesToSearchByIphone(String searchTerm) {
        homePage.searchBy(searchTerm);
    }

    @Then("user verifies the results contain (.*)")
    public void userVerifiesTheResultsContainIphone(String searchTerm) {
        homePage.verifySearchResultsContain(searchTerm);
        currentScenario.setItemName(searchTerm);
    }

    @And("user adds the first product in results to cart")
    public void userAddsTheFirstProductInResultsToCart() throws InterruptedException {
        String actualPriceBeforeAddingToCart = homePage.getThePriceOfTheProductToBeAddedToCart();
        homePage.selectFirstProductInResults();
        currentScenario.setPrice(Double.parseDouble(actualPriceBeforeAddingToCart.trim()
                .replace(" ", "")
                .replace(",", "")));
        productDescriptionPage.addTheProductToCart();
        System.out.println("current item price :" +  currentScenario.getPrice());
    }


    @And("user navigates to my cart page")
    public void userNavigatesToMyCartPage() {
        homePage.clickOnMyCartButton();
    }

    @Then("user verifies the item added to cart")
    public void userVerifiesTheItemAddedToCart() {
        cartPage.assertItemNameInCart(currentScenario.getItemName());
    }

    @When("user increases the number of items in cart to (.*)")
    public void userIncreasesTheNumberOfItemsInCartToNumberOfItems(String numberOfItems) {
        cartPage.increaseCurrentProductUnitsTo(Integer.parseInt(numberOfItems));

    }

    @Then("the price should get updated accordingly to (.*)")
    public void thePriceShouldGetUpdatedAccordinglyToNumberOfItems(String numberOfItems) {
        Double cartPrice =  cartPage.verifyThePriceHasGotUpdateWithItemQuantityIncreasedTo(Integer.parseInt(numberOfItems),
                currentScenario.getPrice());
        currentScenario.setCartPrice(cartPrice);
    }
}
