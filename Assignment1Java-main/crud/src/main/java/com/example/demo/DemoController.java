package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.*;


@RestController
public class DemoController {

    @Autowired
    private PlayerRepository playerRepository;

/*Home message*/
    @GetMapping("/")
    public String home(){
        return "THIS IS MY FIRST REST API EVER...AND IT WORKS....BAAAAMMMM!!";
    }

/*List all players in the player database*/
    @GetMapping(path="/player")
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
    Player getSingle(@PathVariable Integer id){
        return playerRepository.findById(id).get();
    }

/*Change a player with the selected id with PUT --> all info needs to be sent again*/
    @PutMapping(path="/player/{id}", consumes="application/json", produces="application/json")
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
    ResponseEntity<Object> delete(@PathVariable Integer id){
        if (playerRepository.findById(id).isPresent()) //get player by id from the database
        {
            playerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       

    }
/**/
}
