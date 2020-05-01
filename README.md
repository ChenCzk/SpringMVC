#Spring Mvc
###1.WEB容器初始化
    1.SpringIOC容器初始化（ContextLoadListener）
        a.只要配置ContextLoadListener（实现ServletContextListener接口），就可以在DispatcherServlet前完成初始化。
            在Web.xml中配置ContextLoaderListener
            <!--配置ContextLoaderListener-->
              <listener>
                <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
              </listener>
        b.如果没有配置ContextLoadListener，dispatcherServlet在初始化时会自动初始化IOC容器（不建议）。
    
    2.初始化映射
        通过注解完成映射
            @Controller:控制器、
            @RequestMapper:指定对应的URI
        Spring MVC会在初始化的时候解析这些信息，形成HandlerMapping。




###2.Spring MVC 入门：（输入网址，返回视图）

    使用注解配置：
        1.新建一个类继承AbstractAnnotationConfigDispatcherServletInitializer类。（类似Web.xml）
            1.getRootConfigClasses():加载Spring IOC 配置类。 return new Class<?>[]{xx.class};
            2.getServletConfigClasses():加载Spring MVC 配置类 。return new Class<?>[]{xx.class};
            3.getServletMappings():定义拦截内容 。return new String[]{"*.com"};
        2.定义Spring MVC配置类：
            1.开头注解：
                    @Configuration：定义该类为配置类。
                    @ComponentScan("com.*")：扫描规定的包，作用是加载映射。
                    @EnableWebMvc：启动Spring MVC框架的配置。
            2. @Bean加载视图解析器(配合控制类的方法调用)：2
                     @Bean("viewResolver")
                        public ViewResolver init(){。。。return viewResolver;}
        3.定义一个控制类
            1.开头注解：
                @Controller("myController")：自定义一个控制器 myController
                @RequestMapping("/my")：自定义URI（门槛），可以不写。
            2.方法注解：
                @RequestMapping("/index")：URI满足/my/index字段进入方法(这里的方法为返回视图)
                public ModelAndView index(){}
        4.流程：
            当服务器开启，初始化各种容器，包括解析控制类的RequestMapping形成HandleMapper（/my/index）。
            服务器接收到请求(http://localhost:8080/SpringMVC_war_exploded/my/index.com)，通过HandlerMapping找到对应的控制类，
            调用对应的方法index()，返回给DispatcherServlet，再由它返回视图给网站。

    使用XML配置：
        实现逻辑:
            1.首先配置Web.xml文件:定义一个Servlet(DispatcherServlet)以及servlet-mapping拦截路径
            2.定义Spring MVC的配置文件:DispatcherServlet.xml
            3.如果想添加Spring框架,可以在Web.xml文件中配置



    具体流程
        Web.xml配置文件：
            a.配置Spring MVC 的 DispatcherServlet 和 Spring MVC 的配置文件位置。
                <!--配置DispatcherServlet和Spring MVC (dispatcher-servlet.xml)配置文件位置-->
                <servlet>
                    <servlet-name>dispatcher</servlet-name>
                    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
                    <!--Spring MVC 文件位置，不写<init-param>的话，默认在WEB-INF目录下的dispatcher-servlet.xml-->
                    <init-param>
                        <param-name>contextConfigLocation</param-name>
                        <param-value>classpath:dispatcher-servlet.xml</param-value>
                    </init-param>
                    <!--容器初始化的时候就加载DispatcherServlet-->
                    <load-on-startup>2</load-on-startup>
                </servlet>
    
                    <!--配置dispatcher拦截内容,Spring MVC的入口-->
                    <servlet-mapping>
                        <servlet-name>dispatcher</servlet-name>
                        <!--这里是任何Url都会进入dispatcherServlet-->
                        <url-pattern>/</url-pattern>
    
    
                    </servlet-mapping>
            b.配置Spring IOC配置文件。
                <!--配置Spring Ioc配置文件-->
                <context-param>
                    <param-name>contextConfigLocation</param-name>
                    <param-value>classpath:applicationContext.xml</param-value>
                </context-param>
    
        配置Spring MVC 配置文件 dispatcher-servlet.xml
             <!--使用注解驱动-->
             <mvc:annotation-driven/>
             <!--定义扫描包-->
             <context:component-scan base-package="com.ssm.*"/>
             <!--定义视图解析器-->
             <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver"
                   p:prefix="/WEB-INF/JSP/" p:suffix=".jsp"/>
    
        配置applicationContext.xml
             可以配置dataSource,SqlSessionFactory，自动扫描








###3.注解介绍：
    1.@RequestMapping:主要三个参数value，method,params，可以作用在类或方法名上。
        @RequestMapping(value = "/czk",method = RequestMethod.GET,params={"name","id=3","!sex"})//有"name"，"id=3"的参数，且不含有"sex"参数
            value:请求路径
            method:请求方法
            params:请求参数
            
    2.@RequestParam:绑定规则,将客户端的请求参数值与服务端的参数值对应，用于获取请求参数的值。
        public ModeAndView index(@RequestParam(value = "id",required = false)Integer role_id){}:
            value:客户端的参数。
            required:true:不允许为空。
                客户端的参数Long类型"id"对应后台的Integer类型role_id值，期间会进行类型转换(Spring MVC内置的类型装换器)。
                如果客户端的参数名称(id,name)和服务端的参数名称(id,name)一致可以不用写注解。
                如果客户端的参数名称(id,name)和服务端的参数的字段(POJO:role.id,role.name)一致可以不用写注解。
                
    4.@PathVariable:使用URL传递参数,参数写在URl中。
        @RequestMapping("./index5/{id}")
        public ModelAndView index5(@PathVariable Long id){
            URL:/index5/{id}:{id}的值直接传给参数Long id。
            
    5.@RequestBody：绑定规则,将客户端的数据与服务端的参数值对应。
        该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上。
        public String index2(@RequestBody List<Long> list){}
            页面发送数据给这个控制方法，该方法的list与页面的数据匹配。
            Json格式的数据(String,数组)-->List<?>
            
    6.@ResponseBody    //将该方法的返回值直接返回给页面,作用在方法上面
        该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
        @ResponseBody
        @RequestMapping("/index6")
        public String index6(@RequestBody Role role){
            System.out.println(role);
            return "asd22";
        }
        return "asd22"; 直返回"asd22"字符串回页面,由result接收。
        
    7.重定向(redirect)
        1.传递参数：（重定向后的方法用参数形式接收即可）
            a.字符串常数：可以用Model,ModelAndView传递
                1.Model
                    @RequestMapping("/index7")
                    public String index7(Model mode){
                        mode.addAttribute("a","asd");
                        mode.addAttribute("b","czk");
                        return "redirect:./index7_1" ; //重定向的URl=/my/index7_1?a=asd&b=czk
                    }
                2.ModelAndView
                    @RequestMapping("/index8")
                    public ModelAndView index8(){
                        ModelAndView modelAndView = new ModelAndView();
                        modelAndView.addObject("a","asd2");
                        modelAndView.addObject("b","czk2");
                        modelAndView.setViewName("redirect:./index8_1");
                        return modelAndView;    //重定向的URl=my/index8_1?a=asd2&b=czk2
                    }
            b.传递POJO：RedirectAttributes类的addFlashAttribute方法,底层使用Session传递
                @RequestMapping("/index9")
                    public String index9(RedirectAttributes ra){
                        ra.addFlashAttribute("role",new Role(1,"asd","man","handsome",new Page(1,20)));
                        return "redirect:./index9_1";    //RedirectAttributes的addFlashAttribute方法：通过session实现传递对象
                    }
        2.接收参数：
            @RequestMapping("/index7_1")
                public ModelAndView index7_1(String a,String b){}
            @RequestMapping("/index8_1")
                public ModelAndView index8_1(String a,String b){}
            @RequestMapping("/index9_1")
                public ModelAndView index9_1(Role role){}
    
    8.在作用域中传递参数
        1.获取Request域的值
            @RequestAttribute
            public String index(@RequestAttribute(id),int id){}; //获取Request域中的值。
        2.获取session域的值
            @SessionAttribute
            public String index(@SessionAttribute(id),int id){}; //获取Session域中的值。
        3.添加值到session
            @SessionAttributes:只能作用在类上,names={"key"},type={xx.class}
            eg:
            @SessionAttributes(names = "c1",types = Page.class)   //指定参数Key为C的参数自动保存到Session里
            @Controller("myController")
            public class MyController {
                @RequestMapping("/index11")
                public ModelAndView index11(String c1,Page page1){
                    ModelAndView modelAndView = new ModelAndView();
                    modelAndView.setViewName("index");
                    modelAndView.addObject("c1",c);//一个key为c的数据，自动加入session数据。
                    modelAndView.addObject("p",page1);//一个类型为Page的数据，自动加入session数据。
                    return modelAndView;
                }
            }
    9.@CookieValue和@RequestHeader：用于获取cooke和HTTP请求头信息
        public ModelAndView index12(
                @CookieValue(value = "User-Agent",required = false,defaultValue = "attribute") String userAgent,
                @RequestHeader(value = "JSESSIONID",required = false,defaultValue = "MyJsessionId") String jsessionId
        ){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("index");
            modelAndView.addObject("userAgent",userAgent);
            modelAndView.addObject("MyJsessionId",jsessionId);
            return modelAndView;
        }
    
    10.拦截器
        步骤：自定义类继承HandleInterceptorAdapter,重写三个方法,MVC配置文件配置对应映射。
        1.继承，重写
            public class Myinterceptor extends HandlerInterceptorAdapter {
                preHandle:处理器前执行，返回值为false则不执行处理器。
                postHandle:执行玩处理器的方法后执行。
                afterCompletion：渲染视图后执行。
            }
        2.MVC配置映射
            <!--配置拦截器-->
            <mvc:interceptors>
                <mvc:interceptor>
                    <!--对应的处理器映射-->
                    <mvc:mapping path="/index"/>
                    <!--使用哪个拦截器-->
                    <bean class="com.ssm.interceptor.Myinterceptor"/>
                </mvc:interceptor>
            </mvc:interceptors>
    11.验证器
        1.一个表单。
        2.一个字段与表单name属性对应的POJO，在POJO上添加注解@NOTNULL等要验证的注解。
        3.一个实现org.springframework.validation.Validator接口的类，重写两个方法。
            1.第一个方法：supports
                /*
                * 判断当前验证器是否用于检验clazz类型的POJO
                * @paramclazz - POJO类型
                * @return true 启动检验，false则不再检验
                * */
                @Override
                public boolean supports(Class<?> aClass) {
    
                    return JSR303.class.equals(aClass);
                }
            2.第二个方法：validate
                /*
                * 检验的具体内容
                * @paramObject o：POJO请求对象
                * @param errors：错误信息
                *
                * */
                @Override
                public void validate(Object o, Errors errors) {
                    JSR303 jsr = (JSR303) o;
                    double di = jsr.getAmount() -(jsr.getPrice()*jsr.getQuantity());
                    if (Math.abs(di)>0.01){
                        errors.rejectValue("amount",null,"交易金额和购买数量与价格不匹配");
    
                    }
                }
    
        4.一个绑定和启动：
            @InitBinder:使数据绑定器加入验证器
                @InitBinder
                public void initBinder(DataBinder binder){
    
                    binder.setValidator(new JSR303Validator());
    
                }
            @Valid:启动验证器
                public ModelAndView validator(@Valid JSR303 js, Errors errors){}
    
    
    开发：
    1.模型与视图：控制器DispatcherServlet最终返回的结果可以是一个视图,或者数据,或者带有数据的视图。
        1.首先在MVC配置文件中设置视图解析器。
            <!--定义视图解析器,,prefix前缀suffix后缀-->
            <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver"
            p:prefix="/WEB-INF/JSP/" p:suffix=".jsp"/>
        2.返回视图和数据
            1.String方式+Model/ModelMap
                @RequestMapping("/index3")
                    public String index3(){
                        return "success"; //返回值给视图解析器后(/WEB-INF/JSP/success.jsp)给控制器DispatcherServlet在返回给用户。
                    }
                    @RequestMapping("/index13")
                    public String index13(Model model){
                        model.addAttribute("a","aa");
                        return "index";     //发送视图加数据
                    }
    
                    @RequestMapping("/index14")
                    public String index14(ModelMap modelMap){
                        modelMap.addAttribute("a","aaa");
                        return "index";     //发送视图加数据
                    }
            2.ModeAndView方式+Model/ModelMap/ModeAndView
                @RequestMapping("/index")
                public ModelAndView index(){
                    ModelAndView modelAndView = new ModelAndView();
                    modelAndView.setViewName("index");
                    return modelAndView;
                }
    2.逻辑视图与非逻辑视图：通过ModelAndView对象的addView方法，可以指定具体的视图方法。
        非逻辑视图：不用经过视图解析器，直接返回数据给用户。JSON
        逻辑视图：控制器的返回值经过视图解析器，定位视图资源，加上数据渲染成真正视图给用户。
            非逻辑视图：JSON(MappingJackson2JsonView)
                modelAndView.addView(new MappingJackson2JsonView());
            逻辑视图:需要配置一个视图解析器InternalResourceViewResolver
    3.视图解析器：
        控制器的两种返回值
            1.String:
                public String index13(Model model/ModelMap modelMap){..}
                重点:视图解析器会将返回值String+数据模型(Model/ModelMap)-->ModelAndView中,
                    视图解析器将字符串解析生成JSTl视图。
            2.ModelAndView
                重点：视图解析器通过前缀和后缀加上视图名称就能找到对应的JSP文件，然后把数据模型渲染到JSP文件中，最终展示给用户。
        1.获取请求参数：
            方法名(@RequestParam("id") int id){}
            @RequestParam("id"):request.getParameter("id");
            @SessionAttribute("id"):session.getParameter("id");
    
    
    
    
    自定义装换器(类型装换):
        一对装换器:Converter
            1.自定义类实现Converter接口
                public class .. implements Converter<S, T> {...@Overridepublic T convert(S s){...}};
            2.配置类
                <!--配置FormattingConversionServiceFactoryBean-->
                <bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
                    <property name="converters">
                        <list>
                            <bean class="com.ssm.converter.StringToUserConverter"/>
                        </list>
                    </property>
                </bean>
                <mvc:annotation-driven conversion-service="conversionService"/>
            3.使用
                @ResponseBody
                @RequestMapping("/index20")
                public ModelAndView index20(@RequestParam("text") User user){
                    ModelAndView mv = new ModelAndView();
                    mv.addObject("id",user.getId());
                    mv.addObject("name",user.getName());
                    mv.addObject("note",user.getNote());
                    mv.setView(new MappingJackson2JsonView());
                    return mv;
    
                }
    
    
    格式化器:Formatter
        日期格式化:
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
        数字格式化:
            @RequestParam("amount") @NumberFormat(pattern = "#,###.##")Double amount){}
        POJO:
            public ModelAndView index24(User user){...}
            POJO->
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                private Date date;
                @NumberFormat(pattern = "#,###.##")
                private Double amount;
    
    通知（为控制器方法服务滴）:
            创建一个类给注解@ControllerAdvice:(该包内的所有控制器都被服务,如果要服务特定的控制器,就直接在该控制里添加下面的注解)
                @ControllerAdvice(basePackages = {"com.ssm.controller"})
    
            参数管理:
                @InitBinder:可以指定POJO参数属性转换的数据绑定,为控制器的参数服务，格式处理Format和验证器等
                    @InitBinder
                    public void initBinder(WebDataBinder binder){
                        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),false));
                        //格式处理,好处是在控制器方法参数不用格式化处理
                    }
                @ModelAttribute:可以提前控制器添加一定的数据。
                    @ModelAttribute
                    public void p(Model model){
                        model.addAttribute("czk","czkkk");
                    }
            异常处理:@ExceptionHandler(Exception.class)
                控制器方法出现指定异常时调用该方法,return一个exception.jsp自定义异常页面。
                    @ExceptionHandler(Exception.class)
                    public String exception(){
                        return "exception";
                    }
