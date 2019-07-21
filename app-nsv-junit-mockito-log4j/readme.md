Junit

https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/

1. Create a maven project with junit:junit, org.mockito:mockito-all and log4j:log4j maven dependencies
2. Create Test method with @Test annotation


1. Code Coverage Behavior
Settings -> Build, Execution, Deployment -> Coverage
2. Code Coverage Option
https://www.jetbrains.com/help/idea/configuring-code-coverage-measurement.html
3. Code Coverage Color
Settings -> Editor -> Color scheme -> General -> Line Coverage

Junit Annotations
@Before
@After
@Test
@BeforeClass
@AfterClass
@Ignore

Assert Class methods to do assertions

============================================================================================================
============================================================================================================

Mockito - 1:20
***********************************************************************************************
Example Class
package com.nsv.calculation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CalculateTest {

    @Mock
    private Calculate calculate;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(calculate.add(1,2)).thenReturn(3);
        Mockito.when(calculate.subtract(4,3)).thenReturn(1);
        Mockito.when(calculate.multiply(2,3)).thenReturn(6);
        Mockito.when(calculate.div(10,2)).thenReturn(5);
        Mockito.when(calculate.div(Mockito.anyInt(),Mockito.eq(0))).thenThrow(new ArithmeticException());
    }

    @Test
    public void addMockBehaviourTest(){
        Assert.assertEquals(3, calculate.add(1,2));
    }

    @Test
    public void divMockBehaviorTest(){
        Assert.assertEquals(5, calculate.div(10,2));
    }

    @Test(expected = ArithmeticException.class)
    public void divMockExceptionTest(){
        Assert.assertEquals(5, calculate.div(10,0));
    }

    @Test
    public void verifyNumberOfTimes(){
        calculate.add(1,2);
        calculate.add(1,2);
        Mockito.verify(calculate, Mockito.times(2)).add(1,2);
    }

}
***********************************************************************************************

We cant test private and protected methods usings Mockito frameword. We need to use frameworks like PowerMock for private and protected methods.

Mockito Spy: We can spy on an object either using @Spy annotation or Mockito.spy() method.
Using Mockito Spy we can change the method behaviour on an object.
Spy creates a mock object, which does not affect the real object

1. Using @Spy annotation
    @Spy
    private Employee employee = new Employee("Naga", "Raja");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(calculate.add(1,2)).thenReturn(3);

        Mockito.when(employee.getLastName()).thenReturn("Varne");
    }

    @Test
    public void spyTest(){
        Assert.assertEquals("Varne",employee.getLastName());
        Assert.assertEquals("Naga",employee.getFirstName());
    }

2. Using Mockito.spy()
    private Employee bond = new Employee("James","Bond");
    private Employee spyBond;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        spyBond = Mockito.spy(bond);
        Mockito.when(spyBond.getLastName()).thenReturn("Sherlock");
    }

        @Test
        public void mockitoSpyMethodTest(){
            Assert.assertEquals("Sherlock",spyBond.getLastName());
            Assert.assertEquals("James",spyBond.getFirstName());
            System.out.println(spyBond);
        }

Note 1:
@Mock
MyService myService
OR
MyService myService = Mockito.mock(MyService.class);
https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock

Both come from the Mockito library and are functionally equivalent.

Note 2:  How to enable Mockito annotations ?
Approach 1 : MockitoAnnotations.initMocks(this)
enable Mockito annotations during test executions, the MockitoAnnotations.initMocks(this) static method has to be called.
To avoid side effect between tests, it is advised to do it before each test execution :

@Before
public void initMocks() {
    MockitoAnnotations.initMocks(this);
}

Approach 2:
Another way to enable Mockito annotations is annotating the test class with @RunWith by specifying the MockitoJUnitRunner that does this task and also other useful things :

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public MyClassTest{...}

Note 3:
@MockBean
Spring Boot library wrapping Mockito library

This is indeed a Spring Boot class:

import org.springframework.boot.test.mock.mockito.MockBean;
...
@MockBean
MyService myservice;
The class is included in the spring-boot-test library.

It allows to add Mockito mocks in a Spring ApplicationContext.
If a bean, compatible with the declared class exists in the context, it replaces it by the mock.
If it is not the case, it adds the mock in the context as a bean.


Note 4:
When use classic/plain Mockito and when use @MockBean
As you write a test that doesn't need any dependencies from the Spring Boot container, the classic/plain Mockito is the way to follow : it is fast and favors the isolation of the tested component.
If your test needs to rely on the Spring Boot container and you want also to add or mock one of the container beans : @MockBean from Spring Boot is the way.

Note 5:
Typical usage of Spring Boot @MockBean

Note 6: How to test Controller in spring boot
Approach 1: @RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
Class Declaration{

    @Autowire
    private MockMvc mockmvc;

    @MockBean
    private MyService service;
    OR
    private MyRepository repository;
}

Approach 2: @RunWith(SpringRunner.class)
@WebMvcTest(ControllerClassName.class)
ControllerClassTest Declaration{

    @Autowire
    private MockMvc mockmvc;

    @MockBean
    private MyService service;
    OR
    private MyRepository repository;
}

**********************************************************************************************

Unit Testing using MockMvc and Mockito
https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/

1. By annotating the UserService with the @Mock annotation, we can return mocked data when we call a method from this service. Using the @InjectMocks annotation, we can inject the mocked service inside our UserController. Before each test, we must initialize these mocks using the MockitoAnnotations#initMocks(this).
The MockMvc is initialized using the MockMvcBuilders.standaloneSetup(...).build() method. Optionally we can add filters, interceptors or etc. using the .addFilter() or .addInterceptor() methods.
public class UserControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(new CORSFilter())
                .build();
    }

    // ...

}

MockMvc is the main entry point for server-side Spring MVC test support. Perform a request and return a type that allows chaining further actions, such as asserting expectations, on the result.
@Mock creating a mock. This can also be achieved by using org.mockito.mock(..) method.
@InjectMocks injects mock or spy fields into tested objects automatically.
MockitoAnnotations.initMocks(this) initializes fields annotated with Mockito annotations.
MockMvcBuilders.standaloneSetup(..).build() builds a MockMvc instance by registering one or more @Controller instances and configuring Spring MVC infrastructure programmatically.


Spring Boot Test - PluralSight Way

public class ShipwreckControllerTest{
@InjectMocks
private ShipwreckController sc;

@Mock
private ShipwreckRepository sr;

@Before
public void init(){
    MockitoAnnotations.initMocks(this);
}

@Test
public void testShipwreck(){
    Shipwreck sw = new Shipwreck(1);
    when(sr.findOne(1)).thenReturn(sw);

    Shipwreck getsw = sc.get(1);
    assertEquals(sw,getsw);

}
}


*****************************************************************************
logging - log4j
1. Add Maven Dependency for log4j : log4j
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
2. Create log4j.properties / log4j.xml file under src/main/resources
    log4j.xml file should contain Appenders for Console , RollingFile etc
3. Define the appenders to be referenced in the <root> element
4. configure the message level in the <root> <level> element
    All -> debug -> info -> warn -> error -> fatal -> off
    
(In Spring Boot application, 
logging.level.root = info
logging.level.<packagename> = info)    

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration debug="true"
                     xmlns:log4j='http://jakarta.apache.org/log4j/'>

    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern"
                   value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
        </layout>
    </appender>

    <appender name="file" class="org.apache.log4j.RollingFileAppender">
        <param name="append" value="false" />
        <param name="maxFileSize" value="10MB" />
        <param name="maxBackupIndex" value="5" />
        <!-- For Tomcat -->
        <param name="file" value="${catalina.home}/logs/junitMockitoLog4jDemoApp.log" />
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern"
                   value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
        </layout>
    </appender>

    <root>
        <level value="info" />
        <appender-ref ref="console" />
        <appender-ref ref="file" />
    </root>



5. Define the Pattern layout for the messages in the appender
<layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern"
                   value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
        </layout>

        p -> logging level
        c -> class name
        L -> Line number
        m -> message
        n -> next line

6. Usuage
    private static final Logger LOGGER = Logger.getLogger(Calculator.class);

    public int add(int i, int i1) {
        LOGGER.info("input received. first param="+i+". second param="+i1);
        LOGGER.info("sum is"+(i+i1));
        return i+i1;
    }

    public void priceCheck(int price){
        if(price <100){
            try {
                throw new Exception("Price Check Failed"+price);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                LOGGER.error(e);
            }
        }else{
            LOGGER.info("Price Check Success"+price);
        }
    }