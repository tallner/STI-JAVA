package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

	@Autowired
	private PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void AddPlayer()
	{
		System.out.println(" ***  ADD NEW PLAYER *** ");
		System.out.println("Namn:");
		String name = System.console().readLine(); 			
		System.out.println("Age:");
		int age = Integer.parseInt(System.console().readLine()); 			
		System.out.println("Jerseynumber:");
		int jersey = Integer.parseInt(System.console().readLine()); 	
		System.out.println("Stad:");
		String city = System.console().readLine(); 	
		
		Player p = new Player();
		p.SetAge(age);
		p.SetName(name);
		p.SetJersey(jersey);
		p.SetBorn(city);	

		playerRepository.save(p);
	}
/**/
	public void EditPlayer()
	{
		Player p = new Player();
		p = playerRepository.findById(3).get();
		
		System.out.println(p.GetBorn());
		System.out.println(p.getId());
/** /
		System.out.println(" *** Edit player *** ");
		System.out.println("Name of player to edit:");
		String oldname = System.console().readLine();

		System.out.println(" *** Add new player *** ");
		System.out.println("NewName:");
		String newname = System.console().readLine();
		System.out.println("NewAge:");
		int newage = Integer.parseInt(System.console().readLine());
		System.out.println("NewJNUM:");
		int newjnum = Integer.parseInt(System.console().readLine());
		int editindex = playerName.indexOf(oldname);
		
		playerName.remove(editindex);
		playerAge.remove(editindex);
		playerNum.remove(editindex);

		playerName.add(editindex,newname);
		playerAge.add(editindex,newage);
		playerNum.add(editindex,newjnum);
/**/
	}
/**/
	// Get all players
	public void ListPlayer() {
		Iterable<Player> iterator = playerRepository.findAll();
        iterator.forEach(item -> System.out.println("Namn: "+item.GetName() + " || Ålder: " + item.GetAge() + " || Nr:" + item.GetJersey() + " || Stad: " + item.GetBorn()));
	}
/** /
public void ListPlayer()
	{
		
	}
/**/
	@Override
	public void run(String... args) throws Exception {
		while(true)
		{
			System.out.println(" *** MENY *** ");
			System.out.println(" 1. Add player ");
			System.out.println(" 2. Edit player ");//ÖKurs
			System.out.println(" 3. List player ");
			System.out.println(" 100. Exit ");
			System.out.println("Ange val");
			int sel = Integer.parseInt(System.console().readLine());
			if(sel == 100) break;
			if(sel == 1) AddPlayer();
			if(sel == 2) EditPlayer();
			if(sel == 3) ListPlayer();
		}

	}

}
