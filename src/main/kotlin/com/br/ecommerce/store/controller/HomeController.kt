package com.br.ecommerce.store.controller

import com.br.ecommerce.store.model.interfaces.TableauService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController(val tableauService: TableauService){

    @RequestMapping("home")
    fun helloWord():ModelAndView{
        tableauService.authentication()
        return ModelAndView("appPage")
    }
}