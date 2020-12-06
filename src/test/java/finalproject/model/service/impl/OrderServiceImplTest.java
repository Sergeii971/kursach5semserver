package finalproject.model.service.impl;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.dao.impl.OrderDaoImpl;
import com.verbovskiy.server.model.entity.*;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
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
@PrepareForTest({OrderDaoImpl.class})
public class OrderServiceImplTest {
    OrderDaoImpl orderDao;

    @ObjectFactory
    public IObjectFactory setObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() {
        PowerMockito.mockStatic(OrderDaoImpl.class);
        orderDao = Mockito.mock(OrderDaoImpl.class);
        Mockito.when(OrderDaoImpl.getInstance()).thenReturn(orderDao);
    }

    @Test
    public void findByCarIdPositiveTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        Optional<UserOrder> order = Optional.of(new UserOrder(12, LocalDate.now(), user, car, false));
        Optional<UserOrder> expected = Optional.of(new UserOrder(12, LocalDate.now(), user, car, false));
        Mockito.when(orderDao.findByCarId(Mockito.anyLong())).thenReturn(order);
        OrderService service = new OrderServiceImpl();
        Optional<UserOrder> actual = service.findByCarId(41);

        assertEquals(actual, expected);
    }

    @Test
    public void findByCarIdNegativeTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        Optional<UserOrder> order = Optional.of(new UserOrder(12, LocalDate.now(), user, car, false));
        Optional<UserOrder> expected = Optional.of(new UserOrder(12, LocalDate.now(), user, car, true));
        Mockito.when(orderDao.findByCarId(Mockito.anyLong())).thenReturn(order);
        OrderService service = new OrderServiceImpl();
        Optional<UserOrder> actual = service.findByCarId(41);

        assertNotEquals(actual, expected);
    }

    @Test
    public void findOrdersByParametersPositiveTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        Optional<List<UserOrder>> expected = Optional.of(orders);
        Mockito.when(orderDao.findBySearchParameters(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        Optional<List<UserOrder>> actual = service.findOrdersByParameters("dfg", "dg", "dg",
                "dfh", "dfhg");

        assertEquals(actual, expected);
    }

    @Test
    public void findOrdersByParametersNegativeTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        Optional<List<UserOrder>> expected = Optional.empty();
        Mockito.when(orderDao.findBySearchParameters(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        Optional<List<UserOrder>> actual = service.findOrdersByParameters("dfg", "dg", "dg",
                "dfh", "dfhg");

        assertNotEquals(actual, expected);
    }

    @Test
    public void findOrdersByUserEmailPositiveTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        Optional<List<UserOrder>> expected = Optional.of(orders);
        Mockito.when(orderDao.findByUserEmail(Mockito.anyString())).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        Optional<List<UserOrder>> actual = service.findOrdersByUserEmail("dfg");

        assertEquals(actual, expected);
    }

    @Test
    public void findOrdersByUserEmailNegativeTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        Optional<List<UserOrder>> expected = Optional.empty();
        Mockito.when(orderDao.findByUserEmail(Mockito.anyString())).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        Optional<List<UserOrder>> actual = service.findOrdersByUserEmail("dfg");

        assertNotEquals(actual, expected);
    }

    @Test
    public void findAllOrdersPositiveTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        List<UserOrder> expected = orders;
        Mockito.when(orderDao.findAll()).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        List<UserOrder> actual = service.findAllOrders();

        assertEquals(actual, expected);
    }

    @Test
    public void findAllOrdersNegativeTest() throws DaoException, ServiceException {
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
        String login = "epam.online.store@gmail.com";
        boolean isAdmin = true;
        boolean isBlocked = false;
        Account account = new Account(login, isAdmin, isBlocked);
        String name = "Sergei";
        String surname = "Verbovskiy";
        User user = new User(account, login, name, surname);
        UserOrder order = new UserOrder(12, LocalDate.now(), user, car, false);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        List<UserOrder> expected = new ArrayList<>();
        Mockito.when(orderDao.findAll()).thenReturn(orders);
        OrderService service = new OrderServiceImpl();
        List<UserOrder> actual = service.findAllOrders();

        assertNotEquals(actual, expected);
    }
}