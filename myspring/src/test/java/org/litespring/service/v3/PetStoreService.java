package org.litespring.service.v3;
import org.litespring.dao.v3.AccountDao;
import org.litespring.dao.v3.ItemDao;
public class PetStoreService {
	
	private AccountDao accountDao;
	private ItemDao itemDao;
	private int version;
	private String name;
	
	
	public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}
	public PetStoreService(AccountDao accountDao, ItemDao itemDao, String name) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.name = name;
	}
	public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
		this(accountDao, itemDao, 0);
	}
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public int getVersion() {
		return version;
	}
	
	public String getName() {
		return name;
	}
	
	
}
