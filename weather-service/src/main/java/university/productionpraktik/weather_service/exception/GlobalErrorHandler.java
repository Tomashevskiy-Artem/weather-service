package university.productionpraktik.weather_service.exception;

import org.springframework.http.ResponseEntity; // добавим класс по управлению HTTP-ответом для обработки ошибки 400
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import university.productionpraktik.weather_service.dto.ErrorResponse;

@RestControllerAdvice // данный класс занимается обработкой ошибок
public class GlobalErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class) // при возникновении ошибки рода
                                                      // IllegalArgumentException (ошибка в переданном аргументе)
                                                      // нужно вызвать этот метод
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException bug) {
        ErrorResponse error = new ErrorResponse(bug.getMessage());

        return ResponseEntity
                .badRequest() // формирование ответа на ошибку 400
                .body(error); // добавление объекта типа ErrorResponse (City can't be empty!!!)
    }
}
