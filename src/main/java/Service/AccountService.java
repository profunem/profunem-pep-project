package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account)
    {
        String username = account.getUsername();
        String password = account.getPassword();
        
        if(username.isEmpty() || password.length() < 4 || accountDAO.getAccountByUsername(username) != null)
        {
            return null;
        }

        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account)
    {
        String username = account.getUsername();
        String password = account.getPassword();

        return accountDAO.getAccountByUsernamePassword(username, password);
    }

    public List<Account> getAllAccounts()
    {
        return accountDAO.getAllAccounts();
    }
    
}
