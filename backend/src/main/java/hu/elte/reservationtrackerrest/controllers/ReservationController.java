package hu.elte.reservationtrackerrest.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import hu.elte.reservationtrackerrest.entities.Reservation;
import hu.elte.reservationtrackerrest.entities.ResturantTable;
import hu.elte.reservationtrackerrest.entities.ResturantTable.Status;
import hu.elte.reservationtrackerrest.entities.User;
import hu.elte.reservationtrackerrest.repositories.LabelRepository;
import hu.elte.reservationtrackerrest.repositories.ResturantTableRepository;
import hu.elte.reservationtrackerrest.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.elte.reservationtrackerrest.repositories.ReservationRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private ResturantTableRepository tableRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LabelRepository labelRepository;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Reservation>> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
     
            return ResponseEntity.ok(reservationRepository.findAll());
        
    
    
    }

    
//    
    @GetMapping("/{id}")
    public ResponseEntity<List<Reservation>> get(@PathVariable Integer id) {
        List<Reservation> oOrder = reservationRepository.findByUser_id(id);
        if (oOrder.size()>0) {
            return ResponseEntity.ok(oOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("")

    public ResponseEntity<Map<String,String>> insert(@RequestBody HashMap<String,String> resp_map ) {
         HashMap<String,String> map = new HashMap<>();
        try{
        String str1=resp_map.get("resturanttable_name"
                + "");
        String str2=resp_map.get("user_id");
        ResturantTable table = tableRepository.findByTablename(str1);
        Integer resturant_id = table.getId();
        Integer user_id = Integer.parseInt(str2);
        Optional<ResturantTable> oResturantTable = tableRepository.findById(resturant_id);
        if (!oResturantTable.isPresent()) { return ResponseEntity.notFound().build();}
        ResturantTable rt= oResturantTable.get();
        rt.setStatus(Status.RESERVED);
        tableRepository.save(rt);
        Optional<User> oUser = userRepository.findById(user_id);
        if (!oUser.isPresent()) { return ResponseEntity.notFound().build();}
        Reservation reservation = new Reservation();
        reservation.setResturanttable(oResturantTable.get());
        reservation.setUser(oUser.get());  
        reservationRepository.save(reservation);
       
         map.put("message", "OK");
         return ResponseEntity.ok(map);
        }catch(Exception e){
        map.put("message", "ERROR");
        return ResponseEntity.ok(map);

        }
       
    }
    
    @DeleteMapping("/{id}")
  
    public ResponseEntity<Reservation> delete(@PathVariable Integer id) {
        Optional<Reservation> oReservation = reservationRepository.findById(id);
        if (oReservation.isPresent()) {
            
            ResturantTable table =oReservation.get().getResturanttable();
            table.setStatus(Status.FREE);
            tableRepository.save(table);
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
   
    
   
}
