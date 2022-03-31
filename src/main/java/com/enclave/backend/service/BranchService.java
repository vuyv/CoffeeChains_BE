package com.enclave.backend.service;

import com.enclave.backend.dto.BranchDTO;
import com.enclave.backend.entity.Branch;

import java.util.List;

public interface BranchService {

    Branch createBranch(BranchDTO dto);

    Branch updateBranch(Branch branch);

    List<Branch> getBranches();

    Branch getBranchById(short id);
}
