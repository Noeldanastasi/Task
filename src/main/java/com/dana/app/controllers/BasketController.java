package com.dana.app.controllers;

import com.dana.app.data.entities.BasketTable;
import com.dana.app.data.entities.UserTable;
import com.dana.app.data.repositories.Basketrepo;
import com.dana.app.data.repositories.Usertablerepo;
import com.dana.app.filter.Basket;
import com.dana.app.filter.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dana.app.data.entities.Products;
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
public class BasketController {


    private final Basketrepo basketrepo;


    private final Basket basket;

    public BasketController(Basketrepo basketrepo, Basket basket) {
        this.basketrepo = basketrepo;
        this.basket = basket;

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

        Page<BasketTable> basketTables = basket.getbasketdata(queryString, pageRequest);

        long totalRecords = basketTables.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        basketTables.forEach(basket-> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", basket.getbasketId());
            cellData.put("UserID", basket.getUserID());
            cellData.put("Product name", basket.getProductName());


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
        BasketTable basketTable = basketrepo.findById(Long.valueOf(id)).get();

        model.addAttribute("Basket instance", basketTable);

        return "/pages/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("User Instance")  BasketTable basketTable,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/page/edit.html";
        } else {
            if (basketrepo.save(basketTable) != null)
                atts.addFlashAttribute("message", "Basket updated successfully");
            else
                atts.addFlashAttribute("message", "Basket update failed.");

            return "redirect:/pages/";
        }
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("Basket Instance", new BasketTable());
        return "/pages/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("Basket Instance")  BasketTable basketTable,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/pages/create.html";
        } else {
            if (basketrepo.save(basketTable) != null)
                atts.addFlashAttribute("message", "Basket created successfully");
            else
                atts.addFlashAttribute("message", "Basket creation failed.");

            return "redirect:/pages/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        BasketTable basketTable = basketrepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Basket Not Found:" + id));

        basketrepo.delete(basketTable);

        atts.addFlashAttribute("message", "Basket deleted.");

        return "redirect:/pages/";
    }

}
