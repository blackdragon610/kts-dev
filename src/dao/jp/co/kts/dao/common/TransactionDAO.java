package jp.co.kts.dao.common;

import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;

public class TransactionDAO extends BaseDAO {

	public void begin() throws DaoException {

		update("BEGIN", null);
	}

	public void rollback() throws DaoException {

		update("ROLLBACK", null);
	}

	public void commit() throws DaoException {

		update("COMMIT", null);
	}
}
