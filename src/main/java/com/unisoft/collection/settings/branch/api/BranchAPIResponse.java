package com.unisoft.collection.settings.branch.api;

import lombok.Data;

import java.util.List;

@Data
public class BranchAPIResponse {

    private List<BranchDetails> branchDetails;
}
