package com.enclave.backend.service.impl;

import com.enclave.backend.converter.BranchConverter;
import com.enclave.backend.dto.BranchDTO;
import com.enclave.backend.entity.Branch;
import com.enclave.backend.repository.BranchRepository;
import com.enclave.backend.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchConverter branchConverter;

    @Override
    public Branch createBranch(BranchDTO branchDTO) {
        Branch newBranch = branchConverter.toEntity(branchDTO);
        return branchRepository.save(newBranch);
    }

    @Override
    public Branch updateBranch(Branch branch) {
        Branch oldBranch = branchRepository.findById(branch.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid branch Id:" + branch.getId()));
        oldBranch.setName(branch.getName());
        oldBranch.setAddress(branch.getAddress());
        oldBranch.setStatus(branch.getStatus());
        return branchRepository.save(oldBranch);
    }

    @Override
    public  List<Branch> getBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(short id) {
        return branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Imvalid branch id: " + id));
    }
}
