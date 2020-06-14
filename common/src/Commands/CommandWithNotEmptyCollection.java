package Commands;

import Errors.CollectionIsEmptyError;
import Session.SessionServerClient;
import Utils.Context;

/**
 * Команда, для выполнения которых необходима не пустая коллекция
 */
public abstract class CommandWithNotEmptyCollection extends Command {
	public CommandWithNotEmptyCollection() {
		super();
	}
	
	@Override
	public void showDescriptionAndExecute(Context context, SessionServerClient session) {
		this.validateCollectionSize(context);
		super.showDescriptionAndExecute(context, session);
	}
	
	public void validateCollectionSize(Context context) {
		boolean collectionIsEmpty = context.collectionManager.getIsCollectionEmpty();
		if (collectionIsEmpty)
			throw new CollectionIsEmptyError();
	}
}
