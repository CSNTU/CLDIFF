package edu.fdu.se.astdiff.miningactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

import com.github.javaparser.Range;
import edu.fdu.se.astdiff.miningoperationbean.ClusteredActionBean;
import edu.fdu.se.gumtree.MyTreeUtil;

public class MatchVariableDeclarationExpression {
	/**
	 * level III VARIABLEDECLARATIONSTATEMENT
	 * 
	 * @param a
	 * @return
	 */
	public static ClusteredActionBean matchVariableDeclaration(MiningActionData fp, Action a, String nodeType) {
		TreeContext con = null;
		if (a instanceof Insert) {
			con = fp.getDstTree();
		} else {
			con = fp.getSrcTree();
		}
		String operationEntity = "VARIABLEDECLARATION";

		List<Action> subActions = new ArrayList<Action>();
		int status = MyTreeUtil.traverseNodeGetAllEditActions(a, subActions);
		fp.setActionTraversedMap(subActions);

		ITree nodeContainVariableDeclarationFragment = AstRelations.isChildContainVariableDeclarationFragment(a.getNode());
		if (nodeContainVariableDeclarationFragment != null && nodeContainVariableDeclarationFragment.getChildren().size()>1) {
			operationEntity += "-OBJECT-INITIALIZING";
			if (AstRelations.isClassCreation(subActions)) {
				operationEntity += "-NEW";
			}
		}

		Range nodeLinePosition = AstRelations.getnodeLinePosition(a,con);

		ClusteredActionBean mHighLevelOperationBean = new ClusteredActionBean(
				a,nodeType,subActions,nodeLinePosition,status,operationEntity,null,null);
		return mHighLevelOperationBean;
	}

	public static ClusteredActionBean matchVariableDeclarationByFather(MiningActionData fp, Action a, String nodeType,ITree fafafatherNode, String ffFatherNodeType) {
		String operationEntity = "FATHER-VARIABLEDECLARATION";

		List<Action> subActions = new ArrayList<Action>();

		ITree srcfafafather = null;
		ITree dstfafafather = null;
		TreeContext con = null;
		if (a instanceof Insert) {
			con = fp.getDstTree();
			dstfafafather = fafafatherNode;
			srcfafafather = fp.getMappedSrcOfDstNode(dstfafafather);
			if (srcfafafather == null) {
				System.err.println("err null mapping");
			}
		} else {
			con = fp.getSrcTree();
			srcfafafather = fafafatherNode;
			dstfafafather = fp.getMappedDstOfSrcNode(srcfafafather);
			if (dstfafafather == null) {
				System.err.println("err null mapping");
			}
		}

		Set<String> srcT = MyTreeUtil.traverseNodeGetAllEditActions(srcfafafather, subActions);
		Set<String> dstT = MyTreeUtil.traverseNodeGetAllEditActions(srcfafafather, subActions);
		int status = MyTreeUtil.isSrcOrDstAdded(srcT,dstT);

		fp.setActionTraversedMap(subActions);

		ITree nodeContainVariableDeclarationFragment = AstRelations.isChildContainVariableDeclarationFragment(fafafatherNode);
		if (nodeContainVariableDeclarationFragment != null && nodeContainVariableDeclarationFragment.getChildren().size()>1) {
			operationEntity += "-OBJECT-INITIALIZING";
			if (AstRelations.isClassCreation(subActions)) {
				operationEntity += "-NEW";
			}
		}

		Range nodeLinePosition = AstRelations.getnodeLinePosition(a,con);

		ClusteredActionBean mHighLevelOperationBean = new ClusteredActionBean(
				a,nodeType,subActions,nodeLinePosition,status,operationEntity,null,null);
		return mHighLevelOperationBean;
	}
}
