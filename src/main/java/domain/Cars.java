package domain;

import dto.CarDTO;
import util.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cars {
    private final List<Car> cars = new ArrayList<>();

    public Cars(List<String> carNames) {
        checkNameDuplication(carNames);
        addCar(carNames);
    }

    private void checkNameDuplication(List<String> carNames) {
        long uniqueNameCount = carNames.stream()
                .distinct()
                .count();
        if (uniqueNameCount != carNames.size()) {
            throw new IllegalArgumentException("중복되는 자동차 이름이 존재합니다.");
        }
    }

    private void addCar(List<String> carNames) {
        for (String carName : carNames) {
            cars.add(new Car(carName));
        }
    }

    public List<String> findWinners() {
        Integer maxDistance = findMaxDistance();
        return cars.stream()
                .filter(car -> car.getDistance() == maxDistance)
                .map(Car::getCarName)
                .collect(Collectors.toList());
    }

    private Integer findMaxDistance() {
        return cars.stream()
                .mapToInt(Car::getDistance)
                .max()
                .orElse(0);
    }

    public void progressRound(RandomNumberGenerator randomNumberGenerator, RoundResult roundResult) {
        for (Car car : cars) {
            Integer randomNumber = randomNumberGenerator.generateRandomNumber();
            boolean movingResult = roundResult.isGo(randomNumber);
            car.move(movingResult);
        }
    }

    public List<CarDTO> getCars() {
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            carDTOs.add(new CarDTO(car.getCarName(), car.getDistance()));
        }
        return carDTOs;
    }
}
