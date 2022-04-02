package com.enclave.backend.api;

import com.enclave.backend.dto.BranchDTO;
import com.enclave.backend.entity.Branch;
import com.enclave.backend.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchAPI {

    @Autowired
    private BranchService branchService;

    @GetMapping("/all")
    public List<Branch> getBranches() {
        return branchService.getBranches();
    }

    @PostMapping("/new")
    public Branch createBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.createBranch(branchDTO);
    }

    @PutMapping("/{id}")
    public Branch editBranch(@PathVariable("id") short id, @RequestBody Branch branch) {
        branch.setId(id);
        return branchService.updateBranch(branch);
    }

    @GetMapping("/{id}")
    public Branch getBranchById(@PathVariable("id") short id) {
        return branchService.getBranchById(id);
    }
}
