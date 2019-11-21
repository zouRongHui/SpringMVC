package org.rone.study.springmvc.handlers;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import org.rone.study.springmvc.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import org.rone.study.springmvc.entity.User;

@SessionAttributes(value="user", types=User.class)
@RequestMapping("/rone")
@Controller
public class HelloWorld {

	private HelloWorldService service;

	public HelloWorld() {}

	@Autowired
	public HelloWorld(HelloWorldService service) {
		this.service = service;
	}

	/**
	 * 下载文件
	 * @param response	相应报文
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response) throws Exception {

		response.reset();
		response.setContentType("application/txt;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("下载" + ".txt", "UTF-8"));
		File file = new File("E:\\fap二次开发.txt");
		InputStream inputStream = new FileInputStream(file);
		OutputStream outputStream = response.getOutputStream();
		byte[] b = new byte[100];
		while (inputStream.read(b) != -1) {
			System.out.println(new String(b, "utf-8"));
			outputStream.write(b);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * ECharts页面
	 * @return	"echarts"
	 */
	@RequestMapping("/echarts")
	public String echartsView() {
		return "echarts";
	}

	/**
	 * 下载PDF文件
	 */
	@RequestMapping("/downloadPDF")
	@ResponseBody
	public void downloadPDF(HttpServletResponse response, String firstBase64Data, String secondBase64Data, String thirdBase64Data, String fourthBase64Data) throws IOException, DocumentException {
		service.downLoadPDF(response, firstBase64Data, secondBase64Data, thirdBase64Data, fourthBase64Data);
	}
	
	@RequestMapping("/error")
	public String error() {
		return "error";
	}
	
	//使用@RequestMapping注解来映射请求的URL
	@RequestMapping("/helloworld")
	public String hello(){
		System.out.println("hello world!");
		return "success";
	}
	
	//使用method属性来指定请求方式
	//也可以使用params, headers来更加精确的映射请求。params, headers支持简单的表达式
	@RequestMapping(value={"/testMethod","/testPostMethod"}, method={RequestMethod.POST,RequestMethod.GET})
	public String testMethod(){
		System.out.println("testMethod!");
		return "success";
	}
	
	/**
	 * 测试Ant风格
	 */
	@RequestMapping("/testAn?_*_test/**/ant")
	public String testAnt() {
		System.out.println("testAnt...");
		return "success";
	}
	
	//通过@PathVariable可以将URL中的占位符参数绑定到控制器处理方法的参数中
	@RequestMapping("/testPathVariable/{p}")
	public String testPathVariable(@PathVariable("p") String p) {
		System.out.println("testPathVariable + " + p);
		return "success";
	}
	
	//REST风格的请求
	@RequestMapping(value="/testREST/{p}", method=RequestMethod.GET)
	public String testRESTGet(@PathVariable("p") String p) {
		System.out.println("testRESTGet + " + p);
		return "success";
	}
	@RequestMapping(value="/testREST", method=RequestMethod.POST)
	public String testRESTPost() {
		System.out.println("testRESTPost");
		return "success";
	}
	@RequestMapping(value="/testREST/{p}", method=RequestMethod.DELETE)
	public String testRESTDelete(@PathVariable("p") String p) {
		System.out.println("testRESTDelete + " + p);
		return "redirect:success";
	}
	@RequestMapping(value="/testREST/{p}", method=RequestMethod.PUT)
	public String testRESTPut(@PathVariable("p") String p) {
		System.out.println("testRESTPut + " + p);
		return "redirect:success";
	}
	
	//value值为参数名，required为该参数是否必须(默认为true)，defaultValue为默认值
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam() String username, 
			@RequestParam(value="password",required=false,defaultValue="111") int pw) {
		System.out.println("testRequestParam username:" + username + " , password:" + pw);
		return "success";
	}
	
	//springMVC会按请求参数名和POJO属性名自动进行匹配，自动为其填充值。支持级联属性
	@RequestMapping("/testPOJO")
	public String testPOJO(User user) {
		System.out.println("testPOJO_User: " + user);
		return "success";
	}
	
	//使用原生的ServletAPI作为参数
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest req, HttpServletResponse resp, Writer writer) throws IOException {
		System.out.println("testServletAPI: " + req +", " + resp);
		writer.write("hello world!");
	}
	
	//目标方法的返回值可以是ModelAndView类型。
	//其中可以包含视图和模型数据
	//SpringMVC会把ModelAndView对象中的数据放入到request域对象中。
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewName = "success";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("time", new Date());
		return mv;
	}
	
	//目标方法可以添加Map(或Model、ModelMap)类型的参数
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		map.put("time", new Date());
		return "success";
	}
	
	//@SessionAttributes用于将数据置入session
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user = new User("rone", "123", "9@rone.com", "18015564912");
		map.put("user", user);
		map.put("others", "it is me!");
		return "success";
	}
	
	//使用@ModelAttribute后，会在每个handler方法执行前执行
	//运行流程：1.执行@ModelAttribute修饰的方法，将数据置入Map中
	//			2.SpringMVC从Map中取出对象，并将请求参数赋值给该对象相应的属性
	//			3.SpringMVC将上诉对象传入目标方法的参数
	//tips：
	//	1.多个@ModelAttribute顺序执行
	//	2.@ModelAttribute不可添加在已有@RequestMapping注解的方法上，不然已有的请求会报404
	//注意：在@ModelAttribute修饰的方法中，置入Map时键需要和目标方法的参数的类型的小写一致
	@ModelAttribute
	public void getUser(@RequestParam(value="username", required=false) String username, Map<String, Object> map) {
		if (username != null) {
			User user = new User("rone", "123456", "rone@rone.com", "18015564912");
			System.out.println("get data: " + user);
			map.put("user", user);
		}
	}
	
	@RequestMapping("/testModelAttributes")
	public String testModelAttributes(User user) {
		System.out.println("new data: " + user);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/testResponseBody")
	public User testResponseBody() {
		return new User("me", "123456", "rone@foxmail.com", "18015564912");
	}
}
