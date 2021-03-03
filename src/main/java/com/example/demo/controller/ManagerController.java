package com.example.demo.controller;

import com.example.demo.customUserDetails.CustomUserDetails;
import com.example.demo.models.Client;
import com.example.demo.models.SoldProduct;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.SoldProductRepository;
import com.example.demo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ManagerController {
    @Autowired
    private SoldProductRepository salesRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private SalesService salesService;

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    @GetMapping("/home")
    public String homePage(){
        return "manager";
    }

    //stranica s klienti
    @GetMapping("/clients")
    public String clientsPage(Model model){
        List<Client> listClients = clientRepo.findAll();
        model.addAttribute("listClients",listClients);
        return "clients";
    }

    //stranica za vyvezhdane na nov klient
    @GetMapping("/addClient")
    public String addNewClientPage(Model model){
        Client client = new Client();
        model.addAttribute("client",client);
        return "add_client";
    }

    //zapazva noviq klient i vrushta stranicata sys vsichki klienti
    @RequestMapping(value = "/saveClient", method = RequestMethod.POST)
    public String saveNewClient(@ModelAttribute("client") Client client, Model model){
        clientRepo.save(client);
        List<Client> listClients = clientRepo.findAll();
        model.addAttribute("listClients",listClients);
        return "clients";
    }

    //otvarq nova stranica za redaktirane na klient
    @RequestMapping("/editClient/{id}")
    public ModelAndView showEditClientPage(@PathVariable(name = "id") int id) {
        ModelAndView mv = new ModelAndView("edit_client");
        Client client = clientRepo.findById(id).get();
        mv.addObject("client", client);
        clientRepo.deleteById(id);

        return mv;
    }

    //iztriva client
    @RequestMapping(value = "/deleteClient/{id}", method = RequestMethod.DELETE)
    public String deleteClient(@PathVariable(name = "id") int id){
        clientRepo.deleteById(id);
        return "redirect:/clients";
    }

    @RequestMapping(value = "/deleteClient/{id}", method = RequestMethod.GET)
    public String redirectAfterDeletingClient(@PathVariable(name = "id") int id){
        clientRepo.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/mySales")
    public String getSalesByBrand(@AuthenticationPrincipal CustomUserDetails c, Model model){
        String brand = c.getBrand();
        List<SoldProduct> listSoldProducts = salesRepo.findAllByBrand(brand);
        model.addAttribute("listSoldProducts",listSoldProducts);
        return "/salesByBrand";
    }

    @GetMapping("/newSale")
    public String addSalePage(Model model){
        SoldProduct soldProduct = new SoldProduct();
        model.addAttribute("soldProduct",soldProduct);
        return "add_sale";
    }

    @RequestMapping(value = "/saveSale", method = RequestMethod.POST)
    public String saveNewSale(@AuthenticationPrincipal CustomUserDetails c,@ModelAttribute("soldProduct") SoldProduct soldProduct, Model model){
        String brand = c.getBrand();
        soldProduct.setBrand(brand);
        Double price = soldProduct.getPrice();
        int quantity = soldProduct.getQuantity();
        Double finalPrice = price * quantity;
        soldProduct.setFinalPrice(finalPrice);
        String date = getDate();
        soldProduct.setDate(date);
        salesRepo.save(soldProduct);
        List<SoldProduct> listSoldProducts = salesRepo.findAllByBrand(brand);
        model.addAttribute("listSoldProducts",listSoldProducts);
        return "/salesByBrand";
    }

    @RequestMapping(value = "/deleteSale/{id}", method = RequestMethod.DELETE)
    public String deleteSale(@PathVariable(name = "id") int id){
        salesRepo.deleteById(id);
        return "redirect:/mySales";
    }

    @RequestMapping(value = "/deleteSale/{id}", method = RequestMethod.GET)
    public String redirectAfterDeletingSale(@PathVariable(name = "id") int id){
        salesRepo.deleteById(id);
        return "redirect:/mySales";
    }

    @RequestMapping("/editSale/{id}")
    public ModelAndView editSale(@AuthenticationPrincipal CustomUserDetails c,@PathVariable(name = "id") int id){
        ModelAndView mv = new ModelAndView("edit_sale");
        SoldProduct soldProduct = salesRepo.findById(id).get();
        mv.addObject("soldProduct", soldProduct);
        salesRepo.deleteById(id);

        return mv;
    }

    @GetMapping("/chart")
    public String getChart(@AuthenticationPrincipal CustomUserDetails c, Model model){
        String brand = c.getBrand();
        Double[] profit = salesService.getSalesByBrand(brand);
        Map<String, Double> surveyMap = new LinkedHashMap<>();
        surveyMap.put("Sept", profit[0]);
        surveyMap.put("Oct", profit[1]);
        surveyMap.put("Nov", profit[2]);
        surveyMap.put("Dec", profit[3]);
        surveyMap.put("Jan", profit[4]);
        surveyMap.put("Feb", profit[5]);
        model.addAttribute("surveyMap", surveyMap);
        return "barChartByMonth";
    }
}
