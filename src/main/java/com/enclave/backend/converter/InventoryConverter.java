package com.enclave.backend.converter;

import com.enclave.backend.dto.InventoryDTO;
import com.enclave.backend.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter {
    public Inventory toEntity(InventoryDTO inventoryDTO){
        Inventory entity = new Inventory();
//        entity.setUnit(inventoryDTO.getUnitId());
        return entity;
    }
}
