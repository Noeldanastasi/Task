package com.dana.app.controllers;

import com.dana.app.filter.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dana.app.data.entities.Products;
import com.dana.app.data.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pages")
public class ProdController {

    @Autowired
    private final ProductsRepository productsRepository;

    @Autowired
    private final Product product;

    public ProdController(ProductsRepository productsRepository, Product product) {
        this.productsRepository = productsRepository;
        this.product = product;
    }


    @GetMapping
    public String index() {
        return "/pages/index.html";
    }

    @RequestMapping(value = "/data_for_datatable", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
        String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
        if (params.containsKey(dataTableOrderColumnName))
            sortName = params.get(dataTableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
                length,
                sort);

        String queryString = (String) (params.get("search[value]"));

        Page<Products> products = product.getProductsdata(queryString, pageRequest);

        long totalRecords = products.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        products.forEach(product -> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", product.getID());
            cellData.put("Name", product.getName());
            cellData.put("Type", product.getType());
            cellData.put("UserID", product.getUserID());

            cells.add(cellData);
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Products productInst = productsRepository.findById(Long.valueOf(id)).get();

        model.addAttribute("Productinstance", productInst);

        return "/pages/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("ProductInstance")  Products productInst,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/pages/edit.html";
        } else {
            if (productsRepository.save(productInst) != null)
                atts.addFlashAttribute("message", "Product updated successfully");
            else
                atts.addFlashAttribute("message", "Product update failed.");

            return "redirect:/pages/";
        }
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("Product Instance", new Products());
        return "/pages/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product Instance")  Products productInst,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/pages/create.html";
        } else {
            if (productsRepository.save(productInst) != null)
                atts.addFlashAttribute("message", "Product created successfully");
            else
                atts.addFlashAttribute("message", "Product creation failed.");

            return "redirect:/pages/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        Products productInst = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found:" + id));

        productsRepository.delete(productInst);

        atts.addFlashAttribute("message", "Product deleted.");

        return "redirect:/pages/";
    }

}
