package Commands;

import Errors.CollectionIsEmptyError;
import Utils.Context;

/**
 * Команда, для выполнения которых необходима не пустая коллекция
 */
public abstract class CommandWithNotEmptyCollection extends Command {
	public CommandWithNotEmptyCollection() {
		super();
	}
	
	@Override
	public void showDescriptionAndExecute(Context context) {
		this.validateCollectionSize(context);
		super.showDescriptionAndExecute(context);
	}
	
	public void validateCollectionSize(Context context) {
		boolean collectionIsEmpty = context.collectionManager.getIsCollectionEmpty();
		if (collectionIsEmpty)
			throw new CollectionIsEmptyError();
	}
}
