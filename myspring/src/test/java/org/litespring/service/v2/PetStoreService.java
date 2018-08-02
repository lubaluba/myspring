package org.litespring.service.v2;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
public class PetStoreService {

	private AccountDao accountDao;
	private ItemDao itemDao;
	private String owner;
	private int version;
	private boolean iszlm;

	public boolean isIszlm() {
		return iszlm;
	}
	public void setIszlm(boolean iszlm) {
		this.iszlm = iszlm;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public ItemDao getItemDao() {
		return itemDao;
	}
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
}
