package APItest.PojoClasses;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Account {
    private String userName;
    private String password;
    private String userId;
}
