import models.Booking;
import models.Room;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pageobjects.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class SmokeTest extends TestSetup {

    @Before
    public void logIntoApplication(){
        navigateToApplication();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.populateUsername("admin");
        loginPage.populatePassword("password");
        loginPage.clickLogin();
    }

    @Test
    public void authSmokeTest(){
        NavPage navPage = new NavPage(driver);

        assertThat(navPage.getDivNavBar().getText(), containsString("Shady Meadows B&B - Booking Management"));
    }

    @Test
    public void roomSmokeTest() throws InterruptedException {
        RoomListingPage roomListingPage = new RoomListingPage(driver);
        int initialRoomCount = roomListingPage.roomCount();

        roomListingPage.populateRoomNumber("102");
        roomListingPage.checkWifi();
        roomListingPage.checkSafe();
        roomListingPage.checkRadio();
        roomListingPage.clickCreateRoom();

        int currentRoomCount = roomListingPage.roomCount();

        assertThat(currentRoomCount, is(initialRoomCount + 1));
    }

    @Test
    public void bookingSmokeTest() throws InterruptedException {
        Booking booking = new Booking("Sam", "Jones", "100");

        RoomListingPage roomListingPage = new RoomListingPage(driver);
        roomListingPage.clickFirstRoom();

        RoomPage roomPage = new RoomPage(driver);
        int initialBookingCount = roomPage.getBookingCount();

        roomPage.populateFirstname(booking.getFirstname());
        roomPage.populateLastname(booking.getLastname());
        roomPage.populateTotalPrice(booking.getTotalPrice());
        roomPage.populateCheckin("2100-01-01");
        roomPage.populateCheckout("2100-01-02");
        roomPage.clickCreateBooking();

        int currentBookingCount = roomPage.getBookingCount();

        assertThat(currentBookingCount, is(initialBookingCount + 1));
    }

    @Test
    public void reportSmokeTest(){
        NavPage navPage = new NavPage(driver);
        navPage.clickReport();

        ReportPage reportPage = new ReportPage(driver);

        assertThat(reportPage.getReport(), instanceOf(WebElement.class));
    }

    @Test
    public void brandingSmokeTest(){
        NavPage navPage = new NavPage(driver);
        navPage.clickBranding();

        BrandingPage brandingPage = new BrandingPage(driver);
        String nameValue = brandingPage.getNameValue();

        assertThat(nameValue.length(), greaterThan(0));
    }

}
