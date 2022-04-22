# MeliShow-API

## Instrunctions:

Realizar una API que

1. Devuelva informacion de show y asientos dependiendo la criteria solicitada

* [getShows](http://testmeliapi-env.eba-xaucipg7.us-east-1.elasticbeanstalk.com/api/shows?startDate=2022-04-20&endDate=2022-04-25&bottomPrice=100&topPrice=200)
  * QueryParams:
    * ```
      startDate=Date(Format:yyyy-MM-dd)
      endDate=Date(Format:yyyy-MM-dd
      bottomPrice=Float
      topPrice=Float
      ```
* [getSeats](http://testmeliapi-env.eba-xaucipg7.us-east-1.elasticbeanstalk.com/api/show/1/seats?startDate=2022-04-20&endDate=2022-04-25&bottomPrice=100&topPrice=200)
  * QueryParams:
    * ```
      startDate=Date(Format:yyyy-MM-dd)
      endDate=Date(Format:yyyy-MM-dd
      bottomPrice=Float
      topPrice=Float
      ```

2. Realizar reservas

* [postBook](http://testmeliapi-env.eba-xaucipg7.us-east-1.elasticbeanstalk.com/api/book)
  * Body:

    * ```
      {
          "name": String,
          "document": String,
          "date": Date(Format:yyyy-MM-dd),
      "show_id": 2,
          "seats": Array(SeatDTO)
      }
      ```
    * ```
      SeatDTO: {
      "row": "B",
      "numbers": ["2"]
      }
      ```

## Important Links

* [AWS Domain](http://testmeliapi-env.eba-xaucipg7.us-east-1.elasticbeanstalk.com)
* [Swagger JSON](http://testmeliapi-env.eba-xaucipg7.us-east-1.elasticbeanstalk.com/v2/api-docs)
* [Challenge MeliShows](https://github.com/riverojohnatan/melishow-api/blob/main/Challenge%20MeliShows.pdf)
* [DER](https://github.com/riverojohnatan/melishow-api/blob/main/der.jpg)
