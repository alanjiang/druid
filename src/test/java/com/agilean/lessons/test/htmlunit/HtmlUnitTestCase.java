package com.agilean.lessons.test.htmlunit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.meterware.httpunit.PostMethodWebRequest;
public class HtmlUnitTestCase {
	@Ignore
	@Test
	public void homePage() throws Exception {
	    try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://www.dianliaome.com");
	        
	        System.out.println("----"+page.asText());
	        System.out.println("----end----");
	        System.out.println("----"+page.asXml());
	        /*Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

	        final String pageAsXml = page.asXml();
	        Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

	        final String pageAsText = page.asText();
	        Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));*/
	    }
	}
	
	
	@Test
	public void submittingForm() throws Exception {
		
		try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
			
			//参数设置
			// 1 启动JS
			webClient.getOptions().setJavaScriptEnabled(true);
			// 2 禁用Css，可避免自动二次请求CSS进行渲染
			webClient.getOptions().setCssEnabled(false);
			//3 启动客户端重定向
			webClient.getOptions().setRedirectEnabled(true);
			// 4 运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// 5 设置超时
			webClient.getOptions().setTimeout(50000);
			//6 设置忽略证书
			//webClient.getOptions().setUseInsecureSSL(true);
			//7 设置Ajax
			//webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			//8设置cookie
			webClient.getCookieManager().setCookiesEnabled(true);
			
			//获取页面
			HtmlPage page = webClient.getPage("http://www.dianliaome.com/security_admin_login");  
			// 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)   
	        HtmlForm form = page.getForms().get(0);
	        System.out.println("----form="+form);
	        DomNodeList<HtmlElement> eles= form.getElementsByTagName("input");
	        for(HtmlElement e:eles) {
	        	System.out.println(e);
	        }
	        HtmlTextInput username  = ( HtmlTextInput) page.getByXPath("//input[contains(@name, 'username')]").get(0);
	        HtmlPasswordInput password  = (HtmlPasswordInput) page.getByXPath("//input[contains(@name, 'password')]").get(0);
	        System.out.println(username+","+password);
	        //HtmlTextInput username = (HtmlTextInput) form.getInputByName("username");
	        //HtmlPasswordInput password = (HtmlPasswordInput) form.getInputByName("password");
	        username.setValueAttribute("hnsd");
	        password.setValueAttribute("hnsdhg");
	        //HtmlButton button =(HtmlButton)page.getHtmlElementById("loginBtn");   
	        HtmlButton button  = (HtmlButton) page.getByXPath("//button[contains(@type, 'submit')]").get(0);
	        System.out.println(">>button="+button);
	        HtmlPage retPage = (HtmlPage) button.click();
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(10000);
			//输出网页内容
			//System.out.println(retPage.asXml()); 
			//获取cookie
			Set<Cookie> cookies = webClient.getCookieManager().getCookies();; 
			Map<String, String> responseCookies = new HashMap<String, String>();
			for (Cookie c : cookies) {
				responseCookies.put(c.getName(), c.getValue());
				System.out.print(c.getName()+":"+c.getValue());
			}
			
			System.out.println(">>>>operate a page need role<<<<");
			page = webClient.getPage("http://www.dianliaome.com:80/security_shop/browse?type=sub");
			System.out.println(page.asXml()); 
			
			//关闭webclient
			webClient.close(); 
		}
		
		
		

	}
}
