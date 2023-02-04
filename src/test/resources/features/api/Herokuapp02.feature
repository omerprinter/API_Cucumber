#3- https://restful-booker.herokuapp.com/booking endpointine gerekli Query
#parametrelerini yazarak "firstname" degeri "Jim" ve "lastname" degeri
#"Jackson" olan rezervasyon oldugunu test edecek bir GET request gonderdigimizde,
#donen response'un status code'unun 200 oldugunu ve "Jim Jackson" ismine sahip
#en az bir booking oldugunu test edin.

Feature: Get_Pojo_Class

  Scenario: Get Sorgusu

    * Herokuapp Api gerekli URL icin "booking" path param hazirla
    * Herokuapp Api Get Request Expected Body hazirla
    * Response