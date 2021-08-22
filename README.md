# bgspr5

## Beginning Spring 5

* Configuration Through Annotation
    * Declaring a Spring Bean with @Component
    * Wiring Components Together with @Autowired
    * Choosing Components with @Qualifier and Bean Names
    * Constructor Injection with Annotations
* Configuration through XML
    * Declaring a Bean with <bean/>
    * Wiring Components together with <property/>
    * Wiring Components together with <constructor-arg/>
* Configuration through Java
    * Declaring Components with @Bean
    * Using Programmatic Configuration for ApplicationContext
    * Writing Components together with @Autowired with Static Configuration (configuration 8)
    * Using @Qualifier to select specific Components for wiring (configuration 9)
    * Constructor injection with Static Configuration (configuration 10)
    * Testing every configuration with a DataProvider
* Intro to Lifecycle
    * Scope
    * Calling Constructors
    * Calling Methods after Construction and before Destruction
    * Lifecycle Listeners
* Lifecycle with JSR-250 Annotations
    * Annotated Beans with Scopes
    * Calling Methods after Construction and before Destruction
* Lifecycle with Java Configuration
* The Annotation-Based Web Application
    * First Standalone working servlet
        * Start the servlet container: `gradle :ch5anno:tomcatStartWar`
    * Adding a Spring Context for servlets
        * Testing manually: `curl "http://localhost:8080/ch05anno/vote?artist=Therapy+Zeppelin&song=Medium"`
