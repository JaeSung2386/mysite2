package com.douzone.mysite.action.guestbook;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;

public class GuestbookActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("insert".equals(actionName)) {
			action = new InsertAction();
		} else {
			action = new InqueryAction();
		}
		return action;
	}

}
