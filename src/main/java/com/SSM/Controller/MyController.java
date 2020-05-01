package com.SSM.Controller;

import com.SSM.POJO.Czk;
import com.SSM.POJO.PageParams;
import com.SSM.POJO.RoleParams;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.jws.WebParam;

/*
 *   index1:无注解获取前端参数
 *   index2：@RequestParam
 *   index3：@PathVariable
 *   index4：@RequestBody获取前端JSON格式数据
 *   index5：获取前端表单数据Serialize，使用POJO直接接收
 *   index6：String重定向，Model传递字符串
 *   index7：String重定向，RoleParamsInfo传递POJO
 *   index8：ModelAndView重定向，传递字符串
 *   index9：ModelAndView重定向，RoleParamsInfo传递POJO
 *   index10：@RequestAttribute：获取request域的数据
 *   index11：@SessionAttributes：往Session域存数据
 *   index12：@SessionAttribute：获取session域的值
 *   index13：获取请求头 @RequestHeader ，获取Cookie @CookieValue
 *
 * */
@Controller("myController")  //注明是一个控制器
@RequestMapping("/my")  //根映射
@SessionAttributes(names = "session_param", types = Czk.class)  //session域存数据
public class MyController {
    //http://localhost:8080/MySpringMVC_war_exploded/my/index.do
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");   // 由于配置了视图解析器，所以这个代表 /WEB-INF/JSP/index.jsp
        return mv;
    }

    /*
     * 显示获取 roleName 和id 与前端对应(名字对应，也能使用POJO)
     *       前端：roleName，id
     *       后端：roleName，id
     * */
    @RequestMapping(value = "index1", method = RequestMethod.GET)
    public void index1(String roleName, int id) {
        System.out.println(id);
        System.out.println(roleName);
    }

    /*
     * @RequestParam：前后端字段不一致。
     * */
    @RequestMapping(value = "index2", method = RequestMethod.GET)
    public void index2(@RequestParam("roleName") String name, @RequestParam("id") int id) {
        System.out.println(id);
        System.out.println(name);
    }

    /*
     * 前端使用URL传递参数
     *   @PathVariable
     *       前端：/index3/1.do
     *       后端：id=1
     * */
    @RequestMapping("/index3/{id}")
    public ModelAndView index3(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject(id);   // 由于配置了视图解析器，所以这个代表 /WEB-INF/JSP/index.jsp
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    /*
     *接收前端发送的JSON格式数据
     * */
    @RequestMapping("/index4")
    public ModelAndView index4(@RequestBody RoleParams roleParams) {
        System.out.println(roleParams);
        ModelAndView mv = new ModelAndView();
        mv.addObject(roleParams);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    /*
     * 获取前端表单数据Serialize，使用POJO直接接收
     * */
    @RequestMapping("/index5")
    public ModelAndView index5(PageParams pageParams) {
        System.out.println(pageParams);
        ModelAndView mv = new ModelAndView();
        mv.addObject(pageParams);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }
    /*
     * 重定向：
     *       String：
     *           index6：Model传递字符串
     *           index7：RoleParamsInfo传递POJO
     *       ModelAndView：
     *           index8：ModelAndView传递，传递字符串
     *           index9：ModelAndView传递，传递POJO
     * */

    /*
     * 重定向（String）:传递String字符串
     *       保存在model中，返回字符串redirect:./StringInfo.do
     *       或者使用ModelAndView也行--index8
     * */
    @RequestMapping("/index6")
    public String index6(Model model) {
        System.out.println("进来了");
        model.addAttribute("id", 1);
        model.addAttribute("name", "asd");
        model.addAttribute("sex", "female");
        return "redirect:./StringInfo.do";
    }

    @RequestMapping("/StringInfo")
    public void StringInfo(int id, String name, String sex) {
        System.out.println("重定向：");
        System.out.println(id);
        System.out.println(name);
        System.out.println(sex);
    }

    /*
     * 重定向(String)：
     *       传递POJO
     *       使用RedirectAttributes 传递POJO
     * */
    @RequestMapping("/index7")
    public String index7(RedirectAttributes ra) {
        System.out.println("进来了");
        RoleParams roleParams = new RoleParams();
        roleParams.setId(1);
        roleParams.setName("12");
        roleParams.setSex("ad");
        ra.addFlashAttribute("roleParams", roleParams);
        return "redirect:./RoleParamsInfo.do";
    }

    @RequestMapping("/RoleParamsInfo")
    public void StringInfo(RoleParams roleParams) {
        System.out.println("重定向：");
        System.out.println(roleParams);
    }

    /*
     * 重定向（MV）:传递字符串
     * */
    @RequestMapping("/index8")
    public ModelAndView index8(ModelAndView mv) {
        System.out.println("进来了");
        mv.addObject("id", 1);
        mv.addObject("name", "asd");
        mv.addObject("sex", "female");
        mv.setViewName("redirect:./StringInfo.do");
        return mv;
    }

    /*
     * 重定向（MV）:传递字符串
     * */
    @RequestMapping("/index9")
    public ModelAndView index9(ModelAndView mv, RedirectAttributes ra) {
        System.out.println("进来了");
        RoleParams roleParams = new RoleParams();
        roleParams.setId(1);
        roleParams.setName("12");
        roleParams.setSex("ad");
        ra.addFlashAttribute("roleParams", roleParams);
        mv.setViewName("redirect:./RoleParamsInfo.do");
        return mv;
    }

    /*
     * @RequestAttribute：获取request域的数据
     * */
    @RequestMapping("/index10")
    public void index10(@RequestAttribute("id") int id) {
        System.out.println(id);
    }

    /*
     * @SessionAttributes：往Session域存数据,必须return mv
     * */
    @RequestMapping("/index11")
    public ModelAndView index11() {
        String session_param = "ab";
        Czk czk = new Czk();
        czk.setName("aa");
        czk.setNote("bb");
        ModelAndView mv = new ModelAndView();
        mv.addObject("czk", czk); //存入Session域
        mv.addObject("session_param", session_param);//存入Session域
        mv.setViewName("redirect:./index12.do"); //去index12拿
        return mv;
    }

    /*
     *   @SessionAttribute：获取session域的值
     * */
    @RequestMapping("/index12")
    public void index12(@SessionAttribute("session_param") String session_param, @SessionAttribute("czk") Czk czk) {
        System.out.println(session_param);
        System.out.println(czk);
    }

    /*
     * 获取请求头 @RequestHeader
     * 获取Cookie @CookieValue
     * */
    @RequestMapping("/index13")
    public String index13(
            @RequestHeader(value = "User-Agent", required = false, defaultValue = "attribute") String userAgent,
            @CookieValue(value = "JSESSIONID", required = true, defaultValue = "MyJsessionId") String jsessionId) {
        System.out.println("userAgent" + userAgent);
        System.out.println("jsessionId" + jsessionId);
        return "index";
    }

    @RequestMapping("/index14")
    public void index14(Czk czk) {
        System.out.println(czk);

    }

    @RequestMapping("/test")
    @ResponseBody
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        String session_param = "asd11";
        mv.addObject("session_param", session_param);//存入Session域
        mv.setViewName("mv");
        return mv;

    }
    @RequestMapping("/test1")
    public String test1( ModelAndView mv) {
        mv.addObject("a","cccccc");
        return "forward:./test2.do";
    }

    @RequestMapping("/test2")
    public void test2(@RequestAttribute("a")String a) {
        System.out.println(a);
    }
}
