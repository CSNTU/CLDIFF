package edu.fdu.se.astdiff.miningactions;

import com.github.gumtreediff.actions.model.Action;

import edu.fdu.se.astdiff.miningoperationbean.ClusteredActionBean;

public class MatchSynchronized {
	
	public static ClusteredActionBean matchSynchronized(MiningActionData fp, Action a, String nodeType){
		String operationEntity = "SYNCHRONIZED";
		ClusteredActionBean mHighLevelOperationBean = AstRelations.matchByNode(fp,a,nodeType,operationEntity);
		return mHighLevelOperationBean;
	}


}
