package Service;

import Model.Account; 
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO; 

    /**
     * No-args contructor for AccountService instantianes a plain AccountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO(); 
    }

    /** 
     * Uses AccountDAO to add a valid new account to the database. 
     * @param account to add.
     * @return added account or null if registration unsuccessful.
    */
    public Account addAccount(Account account) {
        // registration is unsuccessful if username is blank, 
        // the password is not at least 4 characters long, 
        // and if an account with the same username already exists
        if (
            account.getUsername().isEmpty() || 
            account.getPassword().length() < 4 ||
            accountDAO.getAccountByUsername(account.getUsername()) != null
        ) 
            return null; 
        
        return accountDAO.addAccount(account); 
    }

    /** 
     * Uses AccountDAO to log into an account in the database. 
     * @param account log into.
     * @return logged in account or null of login unsuccessul (incorrect username/password).
    */
    public Account loginToAccount(Account account) {
        return accountDAO.loginToAccount(account); 
    }

    /** 
     * Uses AccountDAO to get an account by ID. 
     * @param id account ID.
     * @return found accound or null if not found.
    */
    public Account getAccountByID(int id) {
        return accountDAO.getAccountByID(id); 
    }
}
