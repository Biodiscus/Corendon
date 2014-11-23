/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.itopia.corendon.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import nl.itopia.corendon.data.Employee;
import nl.itopia.corendon.utils.Log;

/**
 *
 * @author wieskueter.com
 */
public class UserModel {
    
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();
    private int result;
    

}
