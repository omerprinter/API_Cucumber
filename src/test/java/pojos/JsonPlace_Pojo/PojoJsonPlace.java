package pojos.JsonPlace_Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PojoJsonPlace {

    /*
    {
"title":"Ahmet",
"body":"Merhaba",
"userId":10,
"id":70
}

     */

    private String title;
    private String body;
    private int userId;
    private int id;
}
