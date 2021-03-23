package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.net.URI;
import java.util.*;


@RestController
public class DemoController {
    //private CustomerService customerService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PlayerRepository playerRepository;

/*Home message*/
    @GetMapping("/")
    public String home(){
        return "T!";
    }

/*List all players in the player database*/
    @GetMapping(path="/player")
    @CrossOrigin()
    List<Player> getAll(){
		var l = new ArrayList<Player>();
		for(Player r : playerRepository.findAll())
		{
			l.add(r);
		}
		return l;
	}
    
/*List one player with the id {id}*/
    @GetMapping(path="/player/{id}")
    @CrossOrigin()
/**/public ResponseEntity<Player> getSingle(@PathVariable Integer id){
        boolean p = playerRepository.findById(id).isPresent();
        if (p == false) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(playerRepository.findById(id).get());
        }
    }


/*Change a player with the selected id with PUT --> all info needs to be sent again*/
    @PutMapping(path="/player/{id}", consumes="application/json", produces="application/json")
    @CrossOrigin()
    Player update(@PathVariable Integer id, @RequestBody Player updatedPlayer){ //get player data from the rest interface
        Player dbPlayer = playerRepository.findById(id).get(); //get player by id from the database

        dbPlayer.setBorn(updatedPlayer.getBorn());
        dbPlayer.setAge(updatedPlayer.getAge());
        dbPlayer.setName(updatedPlayer.getName());
        dbPlayer.setJersey(updatedPlayer.getJersey());

        playerRepository.save(dbPlayer);

        return dbPlayer;
    }

/*Create a new player and store in the database */
    @PostMapping(path="/player", consumes="application/json", produces="application/json")
    @CrossOrigin()
    ResponseEntity<Object> add(@RequestBody Player p){

        playerRepository.save(p);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(p.getId())
        .toUri();
        return ResponseEntity.created(location).build();
    }

/*Delete a player with the corresponding id. Return 404 if not present*/
    @DeleteMapping(path = "/player/{id}")
    @CrossOrigin()
    ResponseEntity<Object> delete(@PathVariable Integer id){
        if (playerRepository.findById(id).isPresent()) //get player by id from the database
        {
            playerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       

    }
/* */
    private Player applyPatchToPlayer(JsonPatch patch, Player targetPlayer) throws JsonPatchException, JsonProcessingException 
        {
            JsonNode patched = patch.apply(objectMapper.convertValue(targetPlayer, JsonNode.class));
            return objectMapper.treeToValue(patched, Player.class);
        }
        
    @PatchMapping(path = "/player/{id}", consumes = "application/json-patch+json")
    @CrossOrigin()
    public ResponseEntity<Player> updateCustomer(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            //Player dbPlayer = playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
            Player dbPlayer = playerRepository.findById(id).get();
            Player playerPatched = applyPatchToPlayer(patch, dbPlayer);
            playerRepository.save(playerPatched);
            return ResponseEntity.ok(playerPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } /** /catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        /**/
    }
}
