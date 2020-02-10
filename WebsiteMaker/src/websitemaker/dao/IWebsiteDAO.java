/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websitemaker.dao;

import java.util.List;
import models.Website;

/**
 *
 * @author Rafae
 */
public interface IWebsiteDAO {
    public List<Website> list();
    public boolean save(Website website);
    public Website get(String websiteName);
    public boolean alreadyExists(String websiteName);
}
