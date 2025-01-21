package APItest.PojoClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddBookList {

    private String userId;
    private List<Book> collectionOfIsbns;

}
