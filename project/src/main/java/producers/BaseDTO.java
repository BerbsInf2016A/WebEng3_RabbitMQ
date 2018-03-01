package producers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract class BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private final Date creationDate;
    private final String plz;

    public String getTypeIdentifier() {
        return typeIdentifier;
    }

    private final String typeIdentifier;

    public String getPlz() {
        return plz;
    }


    public BaseDTO(String plz, String typeIdentifier){
        this.creationDate = new Date();
        this.plz = plz;
        this.typeIdentifier = typeIdentifier;
    }

}
