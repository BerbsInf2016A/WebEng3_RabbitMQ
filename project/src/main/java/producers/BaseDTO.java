package producers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * A base data transfer object to send data from the producers to the clients.
 */
public abstract class BaseDTO {

    /**
     * The creation date of the dto.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private final Date creationDate;

    /**
     * The "Postleitzahl" or zip code of the dto.
     */
    private final String plz;
    /**
     * A identifier for the content of this dto.
     */
    private final String typeIdentifier;
    /**
     * Name of the location/city this dto is for.
     */
    private String locationName;

    /**
     * Constructor for the base dto.
     *
     * @param plz            The "Postleitzahl" or zip code of the dto.
     * @param typeIdentifier The typeIdentifier for this dto.
     * @param locationName   The name of the location this dto is for.
     */
    public BaseDTO(String plz, String typeIdentifier, String locationName) {
        this.locationName = locationName;
        this.creationDate = new Date();
        this.plz = plz;
        this.typeIdentifier = typeIdentifier;
    }

    /**
     * Getter for the location name.
     *
     * @return The location name.
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Getter for the type identifier.
     *
     * @return The type identifier for this dto.
     */
    public String getTypeIdentifier() {
        return typeIdentifier;
    }

    /**
     * Get the "Postleitzahl" or zip code as String.
     *
     * @return The zip code as String.
     */
    public String getPlzString() {
        return String.valueOf(plz);
    }

}
