package hu.elte.reservationtrackerrest.controllers;

import hu.elte.reservationtrackerrest.entities.User;
import hu.elte.reservationtrackerrest.repositories.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    
    
    @Autowired
    private UserRepository userRepository;
    @CrossOrigin
    @PostMapping("/registry")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setPassword(md5Hash(user.getPassword()));
        user.setEnabled(true);
        user.setRole(User.Role.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @CrossOrigin
     @PostMapping("/singin")
       public ResponseEntity<Map> login(@RequestBody User user) {
        HashMap<String,String> map = new HashMap<>();

        Optional<User> oUser = userRepository.findByUsername(user.getUsername());
        
       if (oUser.isPresent()) {
           if(oUser.get().getPassword().equals(md5Hash(user.getPassword()))){
               map.put("id",oUser.get().getId().toString());
            return ResponseEntity.ok(map);
           } 
       }
            return ResponseEntity.notFound().build();
       
    }
    
    public String md5Hash(String string){
        MessageDigest messageDigest = null;  
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        messageDigest.update(string.getBytes(),0,string.length());  
        String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);  
if (hashedPass.length() < 32) {
   hashedPass = "0" + hashedPass; 
}
        return hashedPass;
    }
            
    
 
}
