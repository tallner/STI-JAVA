package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//public class DemoApplication  implements CommandLineRunner { //Commandline inputs and menu
public class DemoApplication {
/** /
	@Autowired
	private PlayerRepository playerRepository;
/** /
	@GetMapping(path="/player", produces = "application/json")    
	List<Player> all(){
		var l = new ArrayList<Player>();
		for(Player r : playerRepository.findAll())
		{
			l.add(r);
		}
		return l;
	}
	
	@GetMapping("/player/{id}")
	Player one(@PathVariable Integer id) {
		return playerRepository.findById(id).get();
	}
	
	@PostMapping(path="/player", consumes="application/json",  produces = "application/json")
	ResponseEntity<Object> add(@RequestBody Player player){
		playerRepository.save(player);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(player.getId())
		.toUri();
		return ResponseEntity.created(location).build();    
	}
/**/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
/** /
	public void AddPlayer(int selector)
	{
		final int ADD_PLAYER = 1;
		final int MODIFY_PLAYER = 2;
		//final int DELETE_PLAYER = 4;
		
		String[] outputText = {
			" BAD VALUE ",
			" ***  ADD NEW PLAYER *** ",
			" *** EDIT PLAYER *** "
		};
		System.out.println(outputText[selector]);


		// Do not accept NULL string
		String name = "";
		while (name.isEmpty()) {
			System.out.println("Player name:");
			name = System.console().readLine();
		}

		String newName = "";
		if (selector==MODIFY_PLAYER){
			ListPlayer();
			System.out.println("Select name to change:");
			newName = System.console().readLine();
		}
		
		System.out.println("Player Age:");
		int age = Integer.parseInt(System.console().readLine()); 			
		
		System.out.println("Player Jerseynumber:");
		int jersey = Integer.parseInt(System.console().readLine()); 	
		
		System.out.println("Player City:");
		String city = System.console().readLine();

		Player p = new Player();
		String outputMsg = "PLAYER NOT FOUND";
		for (int j = 1; j <= (int)playerRepository.count(); j++) 
		{
			
			if (selector == MODIFY_PLAYER) p = playerRepository.findById(j).get();
				
			if ((selector==ADD_PLAYER) ||
				((selector==MODIFY_PLAYER) && name.equals(p.GetName())) ) 
			{
				if (selector == MODIFY_PLAYER) p.SetName(newName);
				else p.SetName(name);
				
				p.SetAge(age);
				p.SetJersey(jersey);
				p.SetBorn(city);	

				playerRepository.save(p);

				outputMsg = "EDIT PLAYER SUCCESFUL";

				break;
			}
		}
		System.out.println(outputMsg);
	}
/** /
	public void DeletePlayer() {
		ListPlayer();

		System.out.println("Select ID to delete:");
		int id = Integer.parseInt(System.console().readLine());

		playerRepository.deleteById(id);

	}
/** /
	public void EditPlayer(int selector)	
	{	
		AddPlayer(selector);
/** /		
		System.out.println(" *** Edit player *** ");
		ListPlayer();

		// Do not accept NULL string
		String oldname = "";
		while (oldname.isEmpty()) {
			System.out.println("Select player to edit:");
			oldname = System.console().readLine();
		}
		System.out.println("NewName:");
		String newname = System.console().readLine();

		System.out.println("NewAge:");
		int newage = Integer.parseInt(System.console().readLine());
	
		System.out.println("NewJNUM:");
		int newJnum = Integer.parseInt(System.console().readLine());

		System.out.println("NewCity:");
		String newcity = System.console().readLine();
		
		Player play = new Player();
		int k = (int)playerRepository.count();
		
		for (int j = 1; j <= k; j++) 
		{
			play = playerRepository.findById(j).get();
			
			String nuvarandeNamne = play.GetName();
		
			if ( oldname.equals(nuvarandeNamne) ) {
				play.SetName(newname);
				play.SetAge(newage);
				play.SetJersey(newJnum);
				play.SetBorn(newcity);
				playerRepository.save(play);
			}
		}

	}
/** /
	// Get all players
	public void ListPlayer() {
		Iterable<Player> iterator = playerRepository.findAll();
        iterator.forEach(item -> System.out.println("ID: " + item.getId() + " || " + "Namn: "+item.GetName() + " || Ålder: " + item.GetAge() + " || Nr:" + item.GetJersey() + " || Stad: " + item.GetBorn()));
	}
/**/
/* CONSOL INPUT * /
	@Override
	public void run(String... args) throws Exception {
		while(true)
		{
			System.out.println(" *** MENY *** ");
			System.out.println(" 1. Add player ");
			System.out.println(" 2. Edit player ");
			System.out.println(" 3. List player ");
			System.out.println(" 4. Delete player ");
			System.out.println(" 100. Exit ");
			System.out.println("Ange val");
			int sel = Integer.parseInt(System.console().readLine());
			if(sel == 100) break;
			if(sel == 1) AddPlayer(sel);
			if(sel == 2) EditPlayer(sel);
			if(sel == 3) ListPlayer();
			if(sel == 4) DeletePlayer();
		}

	}
/**/

}
