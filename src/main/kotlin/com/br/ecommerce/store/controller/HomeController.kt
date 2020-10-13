package com.br.ecommerce.store.controller

import com.br.ecommerce.store.domain.interfaces.TableauService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.servlet.ModelAndView


@Controller
class HomeController(val tableauService: TableauService){

    @RequestMapping("")
    fun homePage()= ModelAndView("home","bean", ParametersRequestTableau())

    @RequestMapping("retriveData", method= [POST])
    fun getDataFromTableau(params : ParametersRequestTableau): ModelAndView{
        params.result = tableauService.getData(params)

        return ModelAndView("home","bean", params)
    }
}

class ParametersRequestTableau{
    var localeUnderAnalisys: String = ""
    val localeAnalisys = listOf("pt_BR","en_US","fr_CA")
    var localeBenchmark: String = ""
    val localeBench= listOf("pt_BR","en_US","fr_CA")
    val metric= listOf("GSR","NLUER")
    var metricAnalisys : String = ""
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    var startdateA : String = ""
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    var enddateA : String = ""
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    var startdateB : String = ""
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    var enddateB : String = ""
    var result= ""
}