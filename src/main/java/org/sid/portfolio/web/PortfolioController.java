package org.sid.portfolio.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class PortfolioController {
	
	@GetMapping(path="/index")
	public String index() {
		return "index";
	} 
}
