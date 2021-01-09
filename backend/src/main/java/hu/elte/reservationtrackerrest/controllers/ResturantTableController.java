package hu.elte.reservationtrackerrest.controllers;


import hu.elte.reservationtrackerrest.entities.Label;
import hu.elte.reservationtrackerrest.entities.ResturantTable;
import hu.elte.reservationtrackerrest.repositories.LabelRepository;
import hu.elte.reservationtrackerrest.repositories.ResturantTableRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@CrossOrigin
@RequestMapping("/tables")
public class ResturantTableController {
    
    @Autowired
    private ResturantTableRepository resturantTableRepository;
      
    @Autowired
    private LabelRepository labelRepository;
    
    @GetMapping("")
    public ResponseEntity<Iterable<ResturantTable>> getAll() {     
            return ResponseEntity.ok(resturantTableRepository.findAll());
            
    }
    
//    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ResturantTable> get(@PathVariable Integer id) {
        Optional<ResturantTable> oResturantTable = resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            return ResponseEntity.ok(oResturantTable.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @CrossOrigin
    @GetMapping("/free")
    public ResponseEntity<List<ResturantTable>> getFree() {
        List<ResturantTable> oResturantTables = resturantTableRepository.findByStatus(ResturantTable.Status.FREE);
        if(oResturantTables.size()>0){
        return ResponseEntity.ok(oResturantTables);           
        }
        return ResponseEntity.notFound().build();
        }
    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<ResturantTable> insert(@RequestBody ResturantTable resturanttable) {
        return ResponseEntity.ok(resturantTableRepository.save(resturanttable));
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<ResturantTable> update(@PathVariable Integer id, @RequestBody ResturantTable resturanttable) {
        Optional<ResturantTable> oResturantTable = resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            resturanttable.setId(id);
            return ResponseEntity.ok(resturantTableRepository.save(resturanttable));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity<ResturantTable> delete(@PathVariable Integer id) {
        Optional<ResturantTable> oResturantTable = resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            resturantTableRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
   
    
  
    @CrossOrigin
    @GetMapping("/{id}/labels")
    public ResponseEntity<Iterable<Label>> labels(@PathVariable Integer id) {
        Optional<ResturantTable> oResturantTable = resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            return ResponseEntity.ok(oResturantTable.get().getLabels());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin
    @PostMapping("/{id}/labels")
    public ResponseEntity<Label> addLabel(@PathVariable Integer id, @RequestBody Label label) {
        Optional<ResturantTable> oResturantTable= resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            ResturantTable resturantTable = oResturantTable.get();

            Label newLabel = labelRepository.save(label);
            resturantTable.getLabels().add(newLabel);
            resturantTableRepository.save(resturantTable);
            return ResponseEntity.ok(newLabel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin
    @PutMapping("/{id}/labels")
    public ResponseEntity<Iterable<Label>> modifyLabels(
            @PathVariable Integer id, @RequestBody List<Label> labels) {
        Optional<ResturantTable> oResturantTable = resturantTableRepository.findById(id);
        if (oResturantTable.isPresent()) {
            ResturantTable resturantTable = oResturantTable.get();
            
            for (Label label: labels) {
                if (label.getId() == null) {
                    labelRepository.save(label);
                }
            }
            
            resturantTable.setLabels(labels);
            resturantTableRepository.save(resturantTable);
            return ResponseEntity.ok(labels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
