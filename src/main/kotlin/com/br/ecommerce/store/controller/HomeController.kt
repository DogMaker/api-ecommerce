package com.br.ecommerce.store.controller

import com.br.ecommerce.store.domain.interfaces.TableauService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView


@Controller
class HomeController(val tableauService: TableauService){

    @RequestMapping("home")
    fun homePage()= ModelAndView("appPage","bean", ParametersRequestTableau())
    }
/*

    @RequestMapping("retrive-data-tableau")
    fun retriveDataFromTableau()= ModelAndView("appPage","bean", ParametersRequestTableau())
    }

 */

class ParametersRequestTableau{
    val localeUnderAnalisys= null
    val localeAnalisys = listOf("pt_BR","en_US","fr_CA")
    val localeBenchmark= null
    val localeBench= listOf("pt_BR","en_US","fr_CA")
    val metric= listOf("GSR","NLUER")
    val metricAnalisys = null
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    val startdateA = null
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    val enddateA = null
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    val startdateB = null
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    val enddateB = null
    val result= null
}