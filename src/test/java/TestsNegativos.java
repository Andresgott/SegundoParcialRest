import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestsNegativos
{
    @Test
    public void getBookingByInexistentId()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = 99999;
        Response response = RestAssured
                .given().pathParam("id", bookingId)
                .when().get("/booking/{id}");
        response.then().assertThat().statusCode(404); // 404 de not found

        response.then().log().body();

    }

    @Test
    public void getBookingByNotNumericId()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String bookingId = "Hola";
        Response response = RestAssured
                .given().pathParam("id", bookingId)
                .when().get("/booking/{id}");
        response.then().assertThat().statusCode(404); // 404 de not found

        response.then().log().body();

    }

    @Test
    public void postBookingTestIncompleteData() throws JsonProcessingException, ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();
        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckIn("2024-01-01");
        bookingDates.setCheckOut("2024-01-10");
        booking.setLastName("Gottlieb");
        booking.setTotalPrice(111);
        booking.setDepositPaid(true);
        booking.setBookingDates(bookingDates);
        booking.setAdditionalNeeds("Extra pillows please");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        String payload = mapper.writeValueAsString(booking);
        System.out.println("Payload: " + payload);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/booking");

        response.then().log().body();
        response.then().assertThat().statusCode(400); //400 de bad request
    }

    @Test
    public void postBookingTestNoData() throws JsonProcessingException, ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();

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
        response.then().assertThat().statusCode(400); //400 de bad request
    }
}
