package com.enclave.backend.service.impl;

import com.enclave.backend.converter.DiscountConverter;
import com.enclave.backend.dto.DiscountDTO;
import com.enclave.backend.entity.Discount;
import com.enclave.backend.repository.DiscountRepository;
import com.enclave.backend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;


    @Autowired
    private DiscountConverter discountConverter;

    public boolean isExistDiscount(String discountCode){
        Optional<Discount> existDiscount = discountRepository.findById(discountCode);
        return existDiscount.isPresent();
    }


    @Override
    public Discount createDiscount(DiscountDTO discountDTO) {
        if (isExistDiscount(discountDTO.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Discount discount = discountConverter.toEntity(discountDTO);
        if (!discount.isUpcoming()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return discountRepository.save(discount);
    }

    @Override
    public Discount updateDiscount(Discount discount) {
        Discount oldDiscount = discountRepository.findById(discount.getCode()).orElseThrow(() -> new IllegalArgumentException("Invalide discount code: " + discount.getCode()));
        oldDiscount.setCode(discount.getCode());
        oldDiscount.setStartedAt(discount.getStartedAt());
        oldDiscount.setEndedAt(discount.getEndedAt());
        oldDiscount.setTitle(discount.getTitle());
        oldDiscount.setPercent(discount.getPercent());
//        oldDiscount.setStatus(discount.getStatus());
        return discountRepository.save(oldDiscount);
    }

    @Override
    public boolean isValidDiscount(String code, Date currentDate) {
        Discount discount = discountRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Invalid discount code: " + code));
        return discount.isHappening();
    }

    @Override
    public Discount getDiscountByCode(String code) {
        return discountRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Invalid discount code: " + code));
    }

    @Override
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscountWithDate(String code, Date currentDate) {
        System.out.println("DISCOUNT: " + code);
        Discount discount = discountRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Invalid discount code: " + code));
        return discount.getStartedAt().compareTo(currentDate) * currentDate.compareTo(discount.getEndedAt()) >= 0 ? discount : null;
    }

//    private boolean isHappeningDiscount(Discount discount, Date currentDate) {
//        Date startedAt = discount.getStartedAt();
//        Date endedAt = discount.getEndedAt();
//
//        if (startedAt.equals(currentDate) || endedAt.equals(currentDate)) {
//            return true;
//        }
//        return startedAt.before(currentDate) && endedAt.after(currentDate);
//    }

    @Override
    public List<Discount> getHappeningDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        List<Discount> happeningDiscounts = new ArrayList<>();
        discounts.forEach(discount -> {
            if (discount.getStatus().equals(Discount.Status.HAPPENING)) {
                happeningDiscounts.add(discount);
            }
        });
        return happeningDiscounts;
    }


    @Override
    public List<Discount> getUpcomingDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        List<Discount> upcomingDiscounts = new ArrayList<>();
        discounts.forEach(discount -> {
            if (discount.getStatus().equals(Discount.Status.UPCOMING)) {
                upcomingDiscounts.add(discount);
            }
        });
        return upcomingDiscounts;
    }

    @Override
    public List<Discount> getExpiredDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        List<Discount> expiredDiscounts = new ArrayList<>();
        discounts.forEach(discount -> {
            if (discount.getStatus().equals(Discount.Status.EXPIRED)) {
                expiredDiscounts.add(discount);
            }
        });
        return expiredDiscounts;
    }

    @Override
    public void deleteDiscount(String code) {
        try {
            discountRepository.deleteById(code);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
