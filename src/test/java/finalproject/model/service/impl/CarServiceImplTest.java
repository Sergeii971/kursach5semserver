package finalproject.model.service.impl;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.dao.impl.CarDaoImpl;
import com.verbovskiy.server.model.entity.*;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*",
        "com.sun.org.apache.xalan.*", "javax.management.*"})
@PrepareForTest({CarDaoImpl.class})
public class CarServiceImplTest {
    CarDaoImpl carDao;

    @ObjectFactory
    public IObjectFactory setObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() {
        PowerMockito.mockStatic(CarDaoImpl.class);
        carDao = Mockito.mock(CarDaoImpl.class);
        Mockito.when(CarDaoImpl.getInstance()).thenReturn(carDao);
    }

    @Test
    public void findByIdPositiveTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Optional<Car> car = Optional.of(new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType));
        Car expected = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);

        Mockito.when(carDao.findById(Mockito.anyLong())).thenReturn(car);
        CarService service = new CarServiceImpl();
        Car actual = service.findById(41);

        assertEquals(actual, expected);
    }

    @Test
    public void findByIdNegativeTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Optional<Car> car = Optional.of(new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType));
        Car expected = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, false, color, boxType, engineType);

        Mockito.when(carDao.findById(Mockito.anyLong())).thenReturn(car);
        CarService service = new CarServiceImpl();
        Car actual = service.findById(41);

        assertNotEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findByIdExceptionTest() throws DaoException, ServiceException {
        Mockito.when(carDao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        CarService service = new CarServiceImpl();
        service.findById(41);
    }

    @Test
    public void findAllPositiveTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> expected = new ArrayList<>();
        expected.add(car);

        Mockito.when(carDao.findAll()).thenReturn(expected);
        CarService service = new CarServiceImpl();
        List<Car> actual = service.findAllCars();

        assertEquals(actual, expected);
    }

    @Test
    public void findAllNegativeTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> expected = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Mockito.when(carDao.findAll()).thenReturn(cars);
        CarService service = new CarServiceImpl();
        List<Car> actual = service.findAllCars();

        assertNotEquals(actual, expected);
    }

    @Test
    public void findCarsByParametersPositiveTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Optional<List<Car>> expected = Optional.of(cars);
        Mockito.when(carDao.adminFindBySearchParameters(Mockito.anyString(), Mockito.anyDouble(),
                Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(cars);
        CarService service = new CarServiceImpl();
        Optional<List<Car>> actual = service.findCarsByParameters("dfg", "23", "24",
                "fbg","bd", "dfg", "dfg", true);

        assertEquals(actual, expected);
    }

    @Test
    public void findCarsByParametersNegativeTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Optional<List<Car>> expected = Optional.empty();
        Mockito.when(carDao.adminFindBySearchParameters(Mockito.anyString(), Mockito.anyDouble(),
                Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(cars);
        CarService service = new CarServiceImpl();
        Optional<List<Car>> actual = service.findCarsByParameters("dfg", "23", "24",
                "fbg","bd", "dfg", "dfg", true);

        assertNotEquals(actual, expected);
    }

    @Test
    public void findAvailableCarPositiveTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        List<Car> expected = new ArrayList<>();
        expected.add(car);
        Mockito.when(carDao.findAvailableCar()).thenReturn(cars);
        CarService service = new CarServiceImpl();
        List<Car> actual = service.findAvailableCar();

        assertEquals(actual, expected);
    }

    @Test
    public void findAvailableCarNegativeTest() throws DaoException, ServiceException {
        long carId = 41;
        CarBrand brand = CarBrand.AUDI;
        double price = 30000;
        String description = "";
        String imageName = "f371f23e-c82a-4de2-a03a-745c4e181034.png";
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.of(2020,11,9);
        String model = "qwrer325";
        int manufactureYear = 2010;
        CarColor color = CarColor.BLACK;
        CarEngine engineType = CarEngine.DIESEL;
        BoxType boxType = BoxType.MECHANICS;
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName,
                addedDate, isAvailable, color, boxType, engineType);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        List<Car> expected = new ArrayList<>();
        Mockito.when(carDao.findAvailableCar()).thenReturn(cars);
        CarService service = new CarServiceImpl();
        List<Car> actual = service.findAvailableCar();

        assertNotEquals(actual, expected);
    }
}