package university.productionpraktik.weather_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.productionpraktik.weather_service.entity.WeatherSnapshot;

import java.util.List;
import java.util.UUID;

// возьмём готовый интерфейс Spring Data JPA для работы с БД
public interface WeatherSnapshotRepository
        extends JpaRepository<WeatherSnapshot, UUID> {
    // работаем с Entity "WeatherSnapshot",
    // у которой первичный ключ типа UUID

    List<WeatherSnapshot> findByCityOrderByCreatedAtDesc(String city); // реализация чтения истории по городу
}
