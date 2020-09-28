package com.br.ecommerce.store.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController{
    @RequestMapping("home")
    fun helloWord():ModelAndView{
        return ModelAndView("appPage")
    }
}