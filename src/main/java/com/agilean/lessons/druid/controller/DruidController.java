package com.agilean.lessons.druid.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.agilean.lessons.druid.hibernate.jpa.entity.Druid;
import com.agilean.lessons.druid.service.DruidService;


@Controller
public class DruidController {

	@Autowired
	
	private DruidService druidService;
	
	@RequestMapping(value= "/add",method=RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) {
		ModelAndView model=new ModelAndView();
		model.setViewName("druid_add");
		return model;
	}
	
	@RequestMapping(value= "/list",method=RequestMethod.GET)
	public ModelAndView save(HttpServletRequest request) {
		ModelAndView model=new ModelAndView();
		Druid d=new Druid();
		d.setName("Mike");
		d.setAddress("HongKong");
		druidService.save(d);
		
		Iterable<Druid> druids=druidService.browse();
		model.setViewName("druid_browse");
		request.setAttribute("druids", druids);
		return model;
	}
}
