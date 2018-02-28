package producers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract class BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm z")
    private final Date creationDate;
    private final String plz;
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
