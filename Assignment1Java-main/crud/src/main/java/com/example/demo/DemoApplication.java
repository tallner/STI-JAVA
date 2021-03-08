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
/**/
	// Get all players
	public void ListPlayer() {
		Iterable<Player> iterator = playerRepository.findAll();
        iterator.forEach(item -> System.out.println("Namn: "+item.GetName() + " || Ålder: " + item.GetAge() + " || Nr:" + item.GetJersey() + " || Stad: " + item.GetBorn()));
	}
/**/
/**/
	@Override
	public void run(String... args) throws Exception {
		while(true)
		{
			System.out.println(" *** MENY *** ");
			System.out.println(" 1. Add player ");
			System.out.println(" 2. Edit player ");
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
