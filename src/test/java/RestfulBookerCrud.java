import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import io.restassured.RestAssured;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import static org.hamcrest.Matchers.not;

public class RestfulBookerCrud {

    @Test
    public void getBookingIds(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured
                .when().get("/booking");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", not(0));
        response.then().log().body();
    }

    @Test
    public void getBookingById(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = 1367;
        Response response = RestAssured
                .given().pathParam("id", bookingId)
                .when().get("/booking/{id}");
        response.then().assertThat().statusCode(200);

        response.then().log().body();
        response.then().assertThat().body("firstname", Matchers.equalTo("John"));
        response.then().assertThat().body("lastname", Matchers.equalTo("Smith"));
        response.then().assertThat().body("totalprice", Matchers.equalTo(111));
        response.then().assertThat().body("depositpaid", Matchers.equalTo(true));
        response.then().assertThat().body("bookingdates.checkin", Matchers.equalTo("2018-01-01"));
        response.then().assertThat().body("bookingdates.checkout", Matchers.equalTo("2019-01-01"));
        response.then().assertThat().body("additionalneeds", Matchers.equalTo("Breakfast"));


    }

    @Test
    public void postBookingTest() throws JsonProcessingException, ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        Booking booking = new Booking();
        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckIn("2024-01-01");
        bookingDates.setCheckOut("2024-01-10");
        booking.setFirstName("Andres");
        booking.setLastName("Gottlieb");
        booking.setTotalPrice(111);
        booking.setDepositPaid(true);
        booking.setBookingDates(bookingDates);
        booking.setAdditionalNeeds("Extra pillows please");

        ObjectMapper mapper = new ObjectMapper();

        String payload = mapper.writeValueAsString(booking);
        System.out.println("Payload: " + payload);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/booking");

        response.then().log().body();
        response.then().assertThat().statusCode(200);

    }
}

