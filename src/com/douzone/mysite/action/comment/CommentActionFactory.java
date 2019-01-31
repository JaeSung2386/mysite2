package com.douzone.mysite.action.comment;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;
import com.douzone.mysite.action.comment.WriteAction;
import com.douzone.mysite.action.comment.DeleteFormAction;

public class CommentActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
	Action action = null;
		
		if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		} else if("reply".equals(actionName)) {
			action = new ReplyAction();
		} 
		return action;
	}

}
