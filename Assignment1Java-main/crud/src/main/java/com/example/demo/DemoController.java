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

import java.net.URI;
import java.util.*;


@RestController
public class DemoController {

    @Autowired
    private PlayerRepository playerRepository;

/**/
    @GetMapping("/")
    public String home(){
        return "Hello hello hello";
    }
/**/

    @GetMapping(path="/player")
    List<Player> getAll(){
		var l = new ArrayList<Player>();
		for(Player r : playerRepository.findAll())
		{
			l.add(r);
		}
		return l;
	}
    
/**/
    @GetMapping(path="/player/{id}")
    Player getSingle(@PathVariable Integer id){
        return playerRepository.findById(id).get();
    }

    @PutMapping(path="/player/{id}", consumes="application/json", produces="application/json")
    Player update(@PathVariable Integer id, @RequestBody Player updatedPlayer){
        Player dbPlayer = playerRepository.findById(id).get();

        dbPlayer.setBorn(updatedPlayer.getBorn());
        dbPlayer.setAge(updatedPlayer.getAge());
        dbPlayer.setName(updatedPlayer.getName());
        dbPlayer.setJersey(updatedPlayer.getJersey());

        playerRepository.save(dbPlayer);

        return dbPlayer;
    }

    @PostMapping(path="/player", consumes="application/json", produces="application/json")
    ResponseEntity<Object> add(@RequestBody Player p){
    //void add(@RequestBody Player p){
        playerRepository.save(p);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(p.getId())
        .toUri();
        return ResponseEntity.created(location).build();
    }
    
/** /
    @GetMapping("/players")
    List<Player> pList = new ArrayList<Player>();
    
    Player p = new Player();
    p.SetAge(12);
    p.setName("Stefan");
    p.SetJersey(2);
    pList.add(p);

    Player p2 = new Player();
    p.SetAge(13);
    p.setName("Oliver");
    p.SetJersey(21);
    pList.add(p2);

    return pList;
/**/
}
