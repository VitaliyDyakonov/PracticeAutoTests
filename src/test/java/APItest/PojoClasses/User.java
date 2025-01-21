package APItest.PojoClasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String userId;
    private String username;
    private ArrayList<Book> books;
}
