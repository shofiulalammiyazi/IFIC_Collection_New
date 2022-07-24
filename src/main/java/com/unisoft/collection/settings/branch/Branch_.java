package com.unisoft.collection.settings.branch;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value = Branch.class)
public class Branch_ {
	public static volatile SingularAttribute<Branch, Integer> branchId;
	public static volatile SingularAttribute<Branch,String> branchCode;
	public static volatile SingularAttribute<Branch,String> branchName;	
}
