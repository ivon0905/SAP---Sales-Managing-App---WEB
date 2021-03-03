package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.models.SalesRepresentative;
import com.example.demo.models.SoldProduct;
import com.example.demo.models.UserRequest;
import com.example.demo.repositories.*;
import com.example.demo.service.ProductService;
import com.example.demo.service.RequestsService;
import com.example.demo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private RequestRepository requestRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SalesRepresentativeRepository salesRepresentativeRepo;

    @Autowired
    private SoldProductRepository salesRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ProductService service;

    @Autowired
    private RequestsService requestService;

    @Autowired
    private SalesService salesService;

    @GetMapping("/homeAdmin")
    public String adminPage(){
        return "admin";
    }

    //vryshta stranicata sys vsichki produkti
    @GetMapping("/products")
    public String getAllProducts(Model model){
        List<Product> listProducts = productRepo.findAll();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }

    //stranica za dobavqne na nov product
    @GetMapping("/new")
    public String addProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "addProduct";
    }

    //zapazva noviq produkt i vryshta stranicata s produkti
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product, Model model) {
        service.save(product);
        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }

    //otvarq nova stranica za redaktirane na produkta
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mv = new ModelAndView("edit_product");
        Product product = service.get(id);
        mv.addObject("product", product);
        service.delete(id);

        return mv;
    }

    //iztriva produkta
    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/products";
    }

    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.GET)
    public String redirectAfterDeletingProduct(@PathVariable(name = "id") int id){
        service.delete(id);
        return "redirect:/products";
    }

    //stranica sys vsichki tyrgovski predstaviteli
    @GetMapping("/managers")
    public String getSalesRepresentatives(Model model){
        List<SalesRepresentative> listSalesRepresentatives = salesRepresentativeRepo.findAll();
        model.addAttribute("listSalesRepresentatives", listSalesRepresentatives);
        return "salesRepresentatives";
    }

    //iztriva tyrgovski predstavitel
    @RequestMapping(value = "/deleteManager/{id}", method = RequestMethod.DELETE)
    public String deleteManager(@PathVariable(name = "id") int id){
        salesRepresentativeRepo.deleteById((long) id);
        return "redirect:/managers";
    }

    @RequestMapping(value = "/deleteManager/{id}", method = RequestMethod.GET)
    public String redirectAfterDeletingManager(@PathVariable(name = "id") int id){
        salesRepresentativeRepo.deleteById((long) id);
        return "redirect:/managers";
    }

    //otvarq stranicata s zaqvki na novi potrebiteli
    @GetMapping("/requests")
    public String requestsPage(Model model){
        List<UserRequest> listRequests = requestRepo.findAll();
        model.addAttribute("listRequests",listRequests);
        return "requests";
    }

    //iztriva zaqvka
    @RequestMapping(value = "/deleteRequest/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteRequest(@PathVariable(name = "id") int id){
        requestRepo.deleteById((long)id);
        return "redirect:/requests";
    }

    @RequestMapping(value = "/deleteRequest/{id}", method = RequestMethod.GET)
    public String redirectToRequests(@PathVariable(name = "id") int id){
        requestRepo.deleteById((long)id);
        return "redirect:/requests";
    }

    //odobrqva zaqvkata i zapazva dannite v tablicata s tyrgovski predstaviteli
    @GetMapping("/addManager/{id}")
    public String addNewSalesRepresentative(@PathVariable(name = "id") int id, Model model){
        SalesRepresentative manager = new SalesRepresentative();
        UserRequest request = requestService.get((long)id);
        manager.setBrand(request.getBrand());
        manager.setEmail(request.getMail());
        manager.setName(request.getName());
        manager.setLastName(request.getLastName());
        manager.setPhone(request.getPhone());
        manager.setPassword(request.getPassword());
        manager.setUsername(request.getUsername());
        manager.setRole("manager");
        salesRepresentativeRepo.save(manager);
        requestRepo.deleteById((long)id);
        ModelAndView mv = new ModelAndView("requests");
        List<UserRequest> listRequests = requestRepo.findAll();
        mv.addObject("listRequests", listRequests);
        return "redirect:/requests";
    }


    //stranica s prodazhbi
    @GetMapping("/sales")
    public String viewSalesPage(Model model){
        List<SoldProduct> listSoldProducts = salesRepo.findAll();
        model.addAttribute("listSoldProducts",listSoldProducts);
        return "sales";
    }

    @GetMapping("/analysis")
    public String analysisTotalProfitPage(Model model){
        Double[] profit = salesService.getSalesByMonths();
        Map<String, Double> surveyMap = new LinkedHashMap<>();
        surveyMap.put("Sept", profit[0]);
        surveyMap.put("Oct", profit[1]);
        surveyMap.put("Nov", profit[2]);
        surveyMap.put("Dec", profit[3]);
        surveyMap.put("Jan", profit[4]);
        surveyMap.put("Feb", profit[5]);
        model.addAttribute("surveyMap", surveyMap);

        Object[][] list = salesService.getProfitsByBrands();
        model.addAttribute("Chanel",list[0][1]);
        model.addAttribute("Dior",list[1][1]);
        model.addAttribute("RayBan",list[2][1]);
        model.addAttribute("Gucci",list[3][1]);

        String[] brands = salesRepo.getAllBrands();
        Double[] profit1 = salesService.getSalesByBrand(brands[0]);

        Map<String, Double> surveyMap1 = new LinkedHashMap<>();
        surveyMap1.put("Sept", profit1[0]);
        surveyMap1.put("Oct", profit1[1]);
        surveyMap1.put("Nov", profit1[2]);
        surveyMap1.put("Dec", profit1[3]);
        surveyMap1.put("Jan", profit1[4]);
        surveyMap1.put("Feb", profit1[5]);
        model.addAttribute("surveyMap1", surveyMap1);

        Double[] profit2 = salesService.getSalesByBrand(brands[1]);

        Map<String, Double> surveyMap2 = new LinkedHashMap<>();
        surveyMap2.put("Sept", profit2[0]);
        surveyMap2.put("Oct", profit2[1]);
        surveyMap2.put("Nov", profit2[2]);
        surveyMap2.put("Dec", profit2[3]);
        surveyMap2.put("Jan", profit2[4]);
        surveyMap2.put("Feb", profit2[5]);
        model.addAttribute("surveyMap2", surveyMap2);

        Double[] profit3 = salesService.getSalesByBrand(brands[2]);

        Map<String, Double> surveyMap3 = new LinkedHashMap<>();
        surveyMap3.put("Sept", profit3[0]);
        surveyMap3.put("Oct", profit3[1]);
        surveyMap3.put("Nov", profit3[2]);
        surveyMap3.put("Dec", profit3[3]);
        surveyMap3.put("Jan", profit3[4]);
        surveyMap3.put("Feb", profit3[5]);
        model.addAttribute("surveyMap3", surveyMap3);

        Double[] profit4 = salesService.getSalesByBrand(brands[3]);

        Map<String, Double> surveyMap4 = new LinkedHashMap<>();
        surveyMap4.put("Sept", profit4[0]);
        surveyMap4.put("Oct", profit4[1]);
        surveyMap4.put("Nov", profit4[2]);
        surveyMap4.put("Dec", profit4[3]);
        surveyMap4.put("Jan", profit4[4]);
        surveyMap4.put("Feb", profit4[5]);
        model.addAttribute("surveyMap4", surveyMap4);

        return "analysis";
    }
}
