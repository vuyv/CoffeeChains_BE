package com.enclave.backend.api;

import com.enclave.backend.dto.DiscountDTO;
import com.enclave.backend.entity.Discount;
import com.enclave.backend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountAPI {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/new")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDTO dto) {
        return discountService.createDiscount(dto);
    }

    @PutMapping("/{code}")
    public Discount updateDiscount(@PathVariable("code") String code,@RequestBody Discount discount) {
        discount.setCode(code);
        return discountService.updateDiscount(discount);
    }

    @GetMapping("/all")
    public List<Discount> getDiscounts() {
        return discountService.getDiscounts();
    }

    @GetMapping("/{code}")
    public Discount getDiscountByCode(@PathVariable("code") String code) {
        return discountService.getDiscountByCode(code);
    }

    @GetMapping("/{code}/validate")
    public boolean validateDiscount(@PathVariable("code") String code) {
        Date currentDate = new Date();
        return discountService.isValidDiscount(code, currentDate);
    }

    @GetMapping("/status/{status}")
    public List<Discount> getDiscountsByStatus(@PathVariable(name="status") String status) {
        if (status.equals("expired")){
            return discountService.getExpiredDiscounts();
        }
        if (status.equals("happening")){
            return discountService.getHappeningDiscounts();
        }
        return discountService.getUpcomingDiscounts();
    }

    @DeleteMapping("/{code}")
    void deleteDiscount(@PathVariable("code") String code) {
        discountService.deleteDiscount(code);
    }
}
