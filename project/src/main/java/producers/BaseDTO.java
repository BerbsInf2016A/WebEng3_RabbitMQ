package producers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract class BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private final Date creationDate;
    private final int plz;

    public String getLocationName() {
        return locationName;
    }

    private String locationName;

    public String getTypeIdentifier() {
        return typeIdentifier;
    }

    private final String typeIdentifier;

    public String getPlzString() {
        return String.valueOf(plz);
    }


    public BaseDTO(int plz, String typeIdentifier, String locationName){
        this.locationName = locationName;
        this.creationDate = new Date();
        this.plz = plz;
        this.typeIdentifier = typeIdentifier;
    }

}
