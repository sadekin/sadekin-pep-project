package Service;

import Model.Account; 
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO; 

    public AccountService() {
        accountDAO = new AccountDAO(); 
    }

    public Account addAccount(Account account) {
        return accountDAO.addAccount(account); 
    }

    public Account loginToAccount(Account account) {
        return accountDAO.loginToAccount(account); 
    }

    public Account getAccountByID(int id) {
        return accountDAO.getAccountByID(id); 
    }
}
