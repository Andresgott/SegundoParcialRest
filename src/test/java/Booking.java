import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Booking {

    @Getter @Setter
    @JsonProperty("firstname")
    private String firstName;

    @Getter @Setter
    @JsonProperty("lastname")
    private String lastName;

    @Getter @Setter
    @JsonProperty("totalprice")
    private Integer totalPrice;

    @Getter @Setter
    @JsonProperty("depositpaid")
    private Boolean depositPaid;

    @Getter @Setter
    @JsonProperty("additionalneeds")
    private String additionalNeeds;

    @Getter @Setter
    @JsonProperty("bookingdates")
    private BookingDates bookingDates;



    public static class BookingDates {
        @Getter @Setter
        @JsonProperty("checkin")
        private String checkIn;

        @Getter @Setter
        @JsonProperty("checkout")
        private String checkOut;
    }
}
