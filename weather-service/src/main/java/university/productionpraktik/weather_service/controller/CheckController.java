package university.productionpraktik.weather_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // обработка HTTP-запросов
public class CheckController {
    @GetMapping("/") // ответ клиента(браузера) от запроса GET на /(корневой путь приложения: http://localhost:8080/)
    public String checkStatus() {
        return "Weather-Service is working!!!";
    }
}

// создал первый котроллер, который принимает HTTP-запрос GET
// для проверки работоспособности сервера и связи с программой