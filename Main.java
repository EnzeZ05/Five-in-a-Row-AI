import java.util.*; 
import java.io.*;

//game blueprint
/*
                      gobang
                     /      \
                player       game - rules
               /  /          /        \
             p1  p2        pvp     valid_move

  functionality: based on interactions
  player: play(), place(), check()
  board: compete(), check_steps()
*/

//game logic: pvp
/*
                        Main()
                          |
                        input
                          |
                        Play() - class Gobang
                        /    \
                   player1   player2
                        \    /
                       Operate()  <----------
                           |                 | 
                        Check() --> false -->
                           |
                         true
                           |
                        Finish()
*/
    
/* Interaction:
   anime
   rule
   anime (skip)
   option
   play
   end
   option
*/

class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    TextHandler txt = new TextHandler();
      
    //print the introduction, managed by TextHandler
    txt.printIntro();
    String s = sc.next();
    while(!s.equals("y") && !s.equals("n")){
      System.out.println("Please re-enter your decision: y(yes) / n(no)");
      s = sc.next();
    }
    System.out.print("\033[H\033[2J");
    System.out.flush();

    if(s.equals("y")){
      txt.printRule();
      if(sc.next() != null){
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    }

    while(true){
      txt.printGame();
      s = sc.next();
      while(!s.equals("1")){
        System.out.println("Please re-enter 1 to start the game: ");
        s = sc.next();
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }

      //Game starts, maintained by the Gobang class
      Gobang gobang = new Gobang(1);
      Player res = gobang.Play(); 
      txt.printCongrats(res);
      txt.printContinue();
      
      s = sc.next();
      System.out.print("\033[H\033[2J");
      System.out.flush();
      while(!s.equals("y") && !s.equals("n")){
        System.out.println("Please re-enter a valid choice");
      }
      if(s.equals("n")){
        txt.printInfo();
        break;
      }
      else{
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    }
  }
}

/*
All the dot drawings are free sources provided by emojicombos.com
Emoji Combos. "Anime Dot Art." Emoji Combos, n.d. Web. Accessed April 02, 2024.
https://emojicombos.com/anime-dot-art
*/
class TextHandler {
  Scanner sc = new Scanner(System.in);
  public void printIntro() {
    String intro = 
        "⠀⠀⠀⠀⠀⠀⡠⢐⣵⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⠑⠄⠀⠀⠀⠀\n" +
        "⠀⠀⠀⠀⢀⠌⣴⣿⣿⣿⣿⣿⣿⣿⣿⣻⣿⣿⣿⣿⣿⢟⢿⡌⣧⠈⢆⠀⠀⠀\n" +
        "⠀⠀⠀⠠⠁⣼⣿⡟⢽⠻⢻⣿⠿⢿⢿⣟⣿⡿⠿⣿⠃⠀⢨⣿⣾⣦⠄⣄⠀⠀\n" +
        "⠀⠀⢠⠁⡜⣿⣿⣿⡿⠁⣿⣿⠀⣤⣀⠐⣄⠿⣆⣿⡏⠟⢹⣿⣿⢰⢉⣿⣅⠀\n" +
        "⠀⠀⢀⠎⢰⣿⣿⣿⠃⠐⢿⡏⣷⣾⣿⡇⢻⣿⠘⡿⠤⠤⣽⣿⣿⢸⣫⠛⣿⠀  Welcome to Gobang\n" +
        "⠀⠀⡎⠀⣼⣿⣿⣿⣆⣤⣼⣧⠹⣿⡝⢝⣬⣿⣦⡀⠁⣶⣷⣴⣿⣌⣿⠁⢻⡇\n" +
        "⠀⠸⠰⠀⢹⣿⣿⣿⠏⢸⢿⢿⠀⠀⠀⠀⢩⠿⣯⡟⠦⢸⠛⢹⣻⢿⣿⠀⢻⢿\n" +
        "⢀⠇⠀⢱⣾⣿⣿⣿⡆⠈⠁⠉⠀⠀⠀⠀⠀⠁⠘⠁⠀⠁⣾⠿⣽⣿⡟⡐⢸⠸\n" +
        "⢸⠀⠀⣾⣿⣿⣿⠏⡟⡗⡆⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⢀⣈⣥⡾⣿⣿⠀⠇⠘⡀\n" +
        "⠜⢆⣻⣿⣿⣿⣿⡄⢿⠐⣦⣀⠀⠀⠀⠀⠀⢠⠀⣠⣔⣻⣿⣿⣿⣿⣿⢰⠀⠀⠑\n" +
        "⣴⣿⣿⣿⣿⠏⡈⢈⠀⢍⣿⠇⠦⢊⣤⡔⢃⠾⢃⣼⣿⣿⣿⣿⣿⣿⣿⣼⠀⠀⠀\n" +
        "⣿⣿⣿⣻⡍⢂⢰⢸⣤⣾⣯⣶⣾⣿⡁⠄⠒⢭⣿⣿⡿⣿⣿⠭⠯⠿⣧⠀⠀⠀\n" +
        "⣿⣿⡇⠋⠀⢠⡏⠙⣿⣿⣿⣦⡄⠀⠘⣉⡵⣿⣿⣿⣧⠟⠁⢀⡨⠪⡀⠆⢀⠠\n" +
        "⡿⣃⡇⢀⣷⢾⣿⣾⣿⣿⣿⠟⣡⡄⢠⢋⡇⢹⣿⣿⡿⠙⢋⠉⠀⠀⠀⡉⠀⠀\n" +
        "⣿⣿⠀⣼⣯⣿⣿⣿⣿⡿⠁⣴⡿⣴⡟⣓⣳⣿⣵⡿⢁⠕⢁⠀⢀⠄⢀⣿⣉⠀";
    System.out.println(intro);
    Sleep(2000);
    System.out.print("\033[H\033[2J");
    System.out.flush();

    String rules = 
        "⠀⠀⠀⠀⠀⠀⡠⢐⣵⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⠑⠄⠀⠀⠀⠀\n" +
        "⠀⠀⠀⠀⢀⠌⣴⣿⣿⣿⣿⣿⣿⣿⣿⣻⣿⣿⣿⣿⣿⢟⢿⡌⣧⠈⢆⠀⠀⠀\n" +
        "⠀⠀⠀⠠⠁⣼⣿⡟⢽⠻⢻⣿⠿⢿⢿⣟⣿⡿⠿⣿⠃⠀⢨⣿⣾⣦⠄⣄⠀⠀\n" +
        "⠀⠀⢠⠁⡜⣿⣿⣿⡿⠁⣿⣿⠀⣤⣀⠐⣄⠿⣆⣿⡏⠟⢹⣿⣿⢰⢉⣿⣅⠀\n" +
        "⠀⠀⢀⠎⢰⣿⣿⣿⠃⠐⢿⡏⣷⣾⣿⡇⢻⣿⠘⡿⠤⠤⣽⣿⣿⢸⣫⠛⣿⠀ Do you want to know the rules?\n" +
        "⠀⠀⡎⠀⣼⣿⣿⣿⣆⣤⣼⣧⠹⣿⡝⢝⣬⣿⣦⡀⠁⣶⣷⣴⣿⣌⣿⠁⢻⡇           y (yes) / n (no) \n" +
        "⠀⠸⠰⠀⢹⣿⣿⣿⠏⢸⢿⢿⠀⠀⠀⠀⢩⠿⣯⡟⠦⢸⠛⢹⣻⢿⣿⠀⢻⢿\n" +
        "⢀⠇⠀⢱⣾⣿⣿⣿⡆⠈⠁⠉⠀⠀⠀⠀⠀⠁⠘⠁⠀⠁⣾⠿⣽⣿⡟⡐⢸⠸\n" +
        "⢸⠀⠀⣾⣿⣿⣿⠏⡟⡗⡆⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⢀⣈⣥⡾⣿⣿⠀⠇⠘⡀\n" +
        "⠜⢆⣻⣿⣿⣿⣿⡄⢿⠐⣦⣀⠀⠀⠀⠀⠀⢠⠀⣠⣔⣻⣿⣿⣿⣿⣿⢰⠀⠀⠑\n" +
        "⣴⣿⣿⣿⣿⠏⡈⢈⠀⢍⣿⠇⠦⢊⣤⡔⢃⠾⢃⣼⣿⣿⣿⣿⣿⣿⣿⣼⠀⠀⠀\n" +
        "⣿⣿⣿⣻⡍⢂⢰⢸⣤⣾⣯⣶⣾⣿⡁⠄⠒⢭⣿⣿⡿⣿⣿⠭⠯⠿⣧⠀⠀⠀\n" +
        "⣿⣿⡇⠋⠀⢠⡏⠙⣿⣿⣿⣦⡄⠀⠘⣉⡵⣿⣿⣿⣧⠟⠁⢀⡨⠪⡀⠆⢀⠠\n" +
        "⡿⣃⡇⢀⣷⢾⣿⣾⣿⣿⣿⠟⣡⡄⢠⢋⡇⢹⣿⣿⡿⠙⢋⠉⠀⠀⠀⡉⠀⠀\n" +
        "⣿⣿⠀⣼⣯⣿⣿⣿⣿⡿⠁⣴⡿⣴⡟⣓⣳⣿⣵⡿⢁⠕⢁⠀⢀⠄⢀⣿⣉⠀";
    System.out.println(rules);
    System.out.println("y (yes) / n (no), please enter your choice: ");
  }

  public void printRule() {
    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠤⣄⠀⠀⠀⠀⠀ \n" +
        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡇⠀⠀⠀⠀\n" +
        "⠀⠀⠀⠀⠀⢀⣤⣴⣶⣶⣶⣶⣶⣦⣤⡄⠊⠀⠀⠀⠀⠀ \n" +
        "⠀⠦⣤⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀\n" +
        "⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀ \n" +
        "⠀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀\n" +
        "⣠⣿⣿⣿⡿⢻⣿⣿⣿⣿⣿⣿⢿⣿⣿⣿⣿⣿⣿⣿⡧⠀ Thanks for asking me, here are the rules!\n" +
        "⠉⢹⣿⣿⢓⣺⡿⠟⠛⠛⢻⣿⣼⣿⣿⣿⣿⣿⣿⣿⣇⠀ \n" +
        "⠀⢸⣿⣿⠉⠀⠀⠀⠀⠀⠀⠀⠈⢀⣿⡿⠿⢿⣿⣿⣿⠀\n" +
        "⡀⠈⣿⣿⡀⠀⠀⠠⠄⠀⠀⠀⠀⢸⣿⣗⠏⢪⣿⣿⣿⡇\n" +
        "⠀⢀⡈⣿⣷⣤⣄⣀⠀⠀⠀⢀⣤⣿⣿⣷⣾⣿⣿⣿⣿⣧ \n" +
        "⠀⠘⠉⢹⣿⣿⣿⣿⣿⡇⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
        "⠀⠀⠀⣾⣿⣿⠟⡿⠟⡁⠄⠚⠉⠀⠘⢿⣿⣿⣿⣿⣿⡇ \n" +
        "⠀⠀⠀⣿⠟⠁⠈⠉⠁⠀⠀⠀⠀⠀⠀⠀⠙⡿⠿⠏⠿⠃\n" +
        "⠀⠀⠀⡜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⡄⠀⠀⠀");

    System.out.println("1. Players take turns placing black and white pieces on the board (the position for placing must be empty).");
    System.out.println(
        "2. The goal is to be the first player to form an unbroken chain of 5 piece horizontally, vertically or diagonally.");
    System.out
        .println("3. If all positions are filled without either player forming a chain of 5, the game ends in a draw.");
    System.out.println("4. Pieces cannot be moved or removed once placed.");
    System.out.println("5. Play optimally, try to defeat your opponent!");
    System.out.println();
    System.out.println("Please note that you will input two letters at a time, the first one is for rows and the second one is for columns!");
    Sleep(4000);
    System.out.println("Press any key (ASCII) if you are ready");
  }

  public void printGame() {
    System.out.println("⠀⠀⠀⠀⠀⠀⢀⡠⠤⠔⢲⢶⡖⠒⠤⢄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⣠⡚⠁⢀⠀⠀⢄⢻⣿⠀⠀⠀⡙⣷⢤⡀⠀⠀⠀⠀⠀⠀");
    System.out.println("⠀⡜⢱⣇⠀⣧⢣⡀⠀⡀⢻⡇⠀⡄⢰⣿⣷⡌⣢⡀⠀⠀⠀⠀");
    System.out.println("⠸⡇⡎⡿⣆⠹⣷⡹⣄⠙⣽⣿⢸⣧⣼⣿⣿⣿⣶⣼⣆⠀⠀⠀");
    System.out.println("⣷⡇⣷⡇⢹⢳⡽⣿⡽⣷⡜⣿⣾⢸⣿⣿⣿⣿⣿⣿⣿⣷⣄⠀");
    System.out.println("⣿⡇⡿⣿⠀⠣⠹⣾⣿⣮⠿⣞⣿⢸⣿⣛⢿⣿⡟⠯⠉⠙⠛⠓ Yo competitor, Enter 1 for Player game");
    System.out.println("⣿⣇⣷⠙⡇⠀⠁⠀⠉⣽⣷⣾⢿⢸⣿⠀⢸⣿⢿      ");
    System.out.println("⡟⢿⣿⣷⣾⣆⠀⠀⠘⠘⠿⠛⢸⣼⣿⢖⣼⣿⠘⡆⠀⠀⠀⠀");
    System.out.println("⠃⢸⣿⣿⡘⠋⠀⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⡆⠇⠀⠀⠀⠀");
    System.out.println("⠀⢸⡿⣿⣇⠀⠈⠀⠤⠀⠀⢀⣿⣿⣿⣿⣿⣿⣧⢸⠀⠀⠀⠀");
    System.out.println("⠀⠈⡇⣿⣿⣷⣤⣀⠀⣀⠔⠋⣿⣿⣿⣿⣿⡟⣿⡞⡄⠀⠀⠀");
    System.out.println("⠀⠀⢿⢸⣿⣿⣿⣿⣿⡇⠀⢠⣿⡏⢿⣿⣿⡇⢸⣇⠇⠀⠀⠀");
    System.out.println("⠀⠀⢸⡏⣿⣿⣿⠟⠋⣀⠠⣾⣿⠡⠀⢉⢟⠷⢼⣿⣿⠀⠀⠀");
    System.out.println("⠀⠀⠈⣷⡏⡱⠁⠀⠊⠀⠀⣿⣏⣀⡠⢣⠃⠀⠀⢹⣿⡄⠀⠀");
    System.out.println("⠀⠀⠘⢼⣿⠀⢠⣤⣀⠉⣹⡿⠀⠁⠀⡸⠀⠀⠀⠈⣿⡇⠀⠀");
  }

  public void printCongrats(Player p) {
    System.out.println("Congrats to......");
    Sleep(500);
    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⢀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ");
    System.out.println("⠀⠀⠀⠀⠀⠀⠀⣠⠴⠒⡶⠛⡉⠈⠉⠻⣌⠉⠒⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⠀⠀⠀⣠⣾⠇⠀⡸⠁⠀⠘⣆⠀⠀⠈⠳⣄⠀⠈⢢⡀⠀⠀⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⠀⠀⡼⢱⠃⠀⠠⡇⠀⠀⡆⠘⡄⠀⠀⠀⠈⠳⡄⠀⠹⡄⠀⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⠀⡼⢣⠇⠀⠀⢀⣇⣀⣎⢹⣀⣹⣀⣀⣀⣀⣀⠘⡄⠀⢹⡀⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⢰⠃⣸⠒⠉⠉⠉⠉⠁⠘⠚⠀⠀⠀⠀⠀⠀⠀⢹⢻⡀⠀⢧⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⡜⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⣇⠀⢸⠀⠀⠀⠀⠀");
    System.out.println("⠀⠀⡇⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⡄⠸⠀⢰⡇⠀⠀⠀⠀");
    System.out.println("⠀⢸⡃⠸⠿⠀⠀⠀⢀⣀⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⡇⠀⠀⠈⡇⠀<---------- " + p.getName());
    System.out.println("⠀⢸⡇⠀⢻⠀⠀⠀⠘⠛⠀⠀⠉⠁⠀⠈⠟⠁⠀⠀⠀⡇⠀⡀⢠⡇⠀⠀⠀⠀");
    System.out.println("⠀⣼⢹⠀⢸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⡇⠈⡇⠀⠀⠀⠀");
    System.out.println("⠀⢹⣼⠇⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⡇⠐⡇⠀⠀⠀⠀");
    System.out.println("⠀⢸⣿⡄⣸⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣗⠀⡇⠀⡇⠀⠀⠀⠀");
    System.out.println("⠀⠀⢿⣧⣿⣿⠤⢤⠤⠤⡶⢶⣤⣔⣶⣶⣶⣶⢲⢶⠲⡏⠀⡇⢠⣿⠀⠀⠀⠀");
    System.out.println("⠀⠀⠈⢻⣹⡎⡁⢸⠀⣆⣷⣼⣿⣿⣿⣿⠏⢹⣜⣼⢠⠃⠀⡇⠈⣻⡀⠀⠀⠀");
    System.out.println("⠀⠀⠀⠀⡿⡇⢸⡈⣇⣿⣿⡿⠛⠟⠛⠁⠀⠸⢿⡿⣼⣀⡀⠁⠘⢿⡇⠀⠀⠀");
    System.out.println("⠀⠀⠀⠀⣧⣡⣴⡟⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⣼⣧⣿⣿⣿⣿⣶⣾⣿⣀⠀⠀");
    System.out.println("⠀⣠⣶⣿⣿⣟⣿⣿⣿⣿⣿⣿⣦⢖⣒⣒⡶⣴⣿⣿⣿⣻⣿⣿⣿⣿⣿⣿⣷⡀");
    System.out.println("⢠⣿⣿⣿⣿⣿⣿⣿⡟⠳⣯⣻⣿⣿⣿⣿⣿⣿⢟⣵⠟⠉⣼⣿⣿⣿⣿⣿⣿⡇");
    System.out.println("⠘⣿⣿⣿⣿⣿⣿⣿⣿⡀⠀⠙⠮⣿⣿⣿⣿⡿⠋⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⠁");
    System.out.println("⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣤⣀⡀⠈⠻⡿⠋⢀⣠⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⡆");
    System.out.println("⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡧");
    System.out.println("⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠈⠁⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡃");
    Sleep(3000);
  }

  public void printContinue(){
    System.out.println("Do you want to continue? y (yes) / n (no)");
  }

  public void printInfo(){
    System.out.println("Sources:");
    System.out.println("https://emojipedia.org/");
    System.out.println("https://docs.oracle.com/en/java/");
    System.out.println("https://emojicombos.com/anime-dot-art");
    System.out.println("Thanks for playing!");
  }

  public void Sleep(int num) {
    try{
      Thread.sleep(num);
    } catch(InterruptedException e) {
      System.out.println("");
    }
  }
}

class Gobang{
  private final int row = 13, col = 13;
  private String[][] board;
  private int opponent;
  private Scanner sc;

  /*
  Emojipedia. "Large Yellow Circle." Emojipedia, n.d. Web. Accessed Feb 11, 2024
  https://emojipedia.org/large-yellow-circle.
  */
  public Gobang(int opponent){
    this.sc = new Scanner(System.in);
    this.opponent = opponent;

    //initialize the Gobang board
    this.board = new String[row][col];
    for(int i = 0; i < row; i++){
      for(int j = 0; j < col; j++)
        board[i][j] = "🟡";
    }
  }

  public int GetOpponent(){
    return opponent;
  }

  public void SetOpponent(int opponent){
    this.opponent = opponent;
  }

  public Player Play(){
    System.out.println("Please enter Player 1's name: ");
    String name1 = sc.next();
    System.out.println("Please enter Player 2's name: ");
    String name2 = sc.next();
    System.out.print("\033[H\033[2J");
    System.out.flush();
    PlayerGame pg = new PlayerGame(board, name1, name2);
    return pg.Start();
  }
}

class Players{
  private String piece;
  private int moves;

  public Players(String piece){
    moves = 0;
    this.piece = piece;
  }

  public String getMove(){
    return "" + moves;
  }

  public String getPiece(){
    return piece;
  }
}

class Player extends Players{
  //Offset values for the direction of the move
  private ArrayList<char[]> steps;
  private String name, piece;
  private int moves;

  public Player(String piece, String name){
    super(piece);
    this.piece = piece;
    this.steps = new ArrayList<char[]>();
    this.name = name;
  }

  //Displays the player's moves from an ArrayList belonging to that player
  public void getAllSteps(){
    int cnt = 1;
    for(char[] step : steps){
      System.out.println(cnt + " : " + step[0] + " " + step[1]);
      cnt++;
    }
    System.out.println();
  }

  public String getName(){
    return name;
  }

  public String getMove(){
    return "" + moves;
  }

  public String getPiece(){
    moves++;
    return this.piece;
  }

  public void addStep(char[] ch){
    steps.add(ch);
  }
}

class PlayerGame extends Game {
  Player p1, p2;

  public PlayerGame(String[][] board) {
    super(board);
  }

  //Initialize player 1 and player 2
  public PlayerGame(String[][] board, String name1, String name2) {
    super(board);
    p1 = new Player(getPiece1(), name1);
    p2 = new Player(getPiece2(), name2);
  }

  public Player Start() {
    return PVP();
  }

  public Player PVP() {
    int x = 0, y = 0, turn = 0;
    String pc = "", s = "";
    Print();
      
    while (true) {
      boolean flag = false;
      if (turn % 2 == 0) {
        System.out.println("Now it's" + " " + p1.getName() + "'s turn, please enter a coordinate: ");
      } else {
        System.out.println("Now it's" + " " + p2.getName() + "'s turn, please enter a coordinate: ");
      }
      while (!flag) {
        s = super.sc.nextLine();

        //Check for valid inputs
        if(s.length() != 3){
          System.out.println("Please enter a valid coodinate in range [a - m]: ");
          continue;
        }
        x = s.charAt(0) - 'a';
        y = s.charAt(2) - 'a';
        if (x < 0 || y < 0 || x >= 13 || y >= 13 || s.charAt(1) != ' ') {
          System.out.println("Please enter a valid coodinate in range [a - m]: ");
          continue;
        }

        //If the input is valid, then update the board
        flag = (super.board[x][y].equals("🟡") ? true : false);
        if (!flag) {
          System.out.println("This position has been occupied, please re-enter: ");
        }
      }

      //If this is a valid move, record the current player's move
      char a = s.charAt(0), b = s.charAt(2);
      if(turn % 2 == 0){
        p1.addStep(new char[]{a, b});
        pc = super.board[x][y] = p1.getPiece();
      }
      else{
        p2.addStep(new char[]{a, b});
        pc = super.board[x][y] = p2.getPiece();
      }
      Print();

      //After each move, check if there is a five-in-a-row
      if(Check(x, y, pc)){
        break;
      }

      //Check if the board is full
      if(isFull()){
        return new Player("Tie", "It's a tie!");
      }
        
      //Switching players' turn 
      turn++;
    }
      
    System.out.println("Do you want to review " + p1.getName() + "'s steps? y (yes) / n (no): ");
    String input = sc.next();
    while(!input.equals("y") && !input.equals("n")){
      System.out.println("Please enter a valid input: ");
      input = sc.next();
    }
    if(input.equals("y")){
      p1.getAllSteps();
    }

    System.out.println("Do you want to review " + p2.getName() + "'s steps? y (yes) / n (no): ");
    input = sc.next();
    while(!input.equals("y") && !input.equals("n")){
      System.out.println("Please enter a valid input: ");
      input = sc.next();
    }
    if(input.equals("y")){
      p2.getAllSteps();
    }
    return turn % 2 == 0 ? p1 : p2;
  }
}

/*
Oracle. "Class BufferedWriter." Java™ Platform, Standard Edition 11 API Specification. Oracle, n.d. Web. Accessed March 07, 2024.
*/
class Game{
  //Offset values for the direction of the move
  final int[] dirx = {1, -1, 0, 0, 1, -1, 1, -1};
  final int[] diry = {0, 0, -1, 1, 1, -1, -1, 1};
  final char[] alp = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'};
  BufferedWriter ps = new BufferedWriter(new OutputStreamWriter(System.out));
  Scanner sc = new Scanner(System.in);
  String[][] board;

  private final String[] pieces;

  /*
  Emojipedia. "White Circle." Emojipedia, n.d. Web. Accessed Feb 11, 2024.
  https://emojipedia.org/white-circle

  Emojipedia. "Black Circle." Emojipedia, n.d. Web. Accessed Feb 11, 2024.
  https://emojipedia.org/black-circle
  */
  public Game(String[][] board) {
    this.pieces = new String[] { "⚪", "⚫" };
    this.board = board;
  }

  public String getPiece1() {
    return pieces[0];
  }

  public String getPiece2() {
    return pieces[1];
  }

  //Iterate in different directions to check whether there is a five-in-a-row
  public boolean Check(int x, int y, String p){
    int cnt = 1;
    for(int i = 0; i < 8; i++){
      if(i != 0 && i % 2 == 0) cnt = 1;
      int dx = x + dirx[i], dy = y + diry[i];
      while(dx >= 0 && dy >= 0 && dx < 13 && dy < 13){
        if(!board[dx][dy].equals(p) || cnt >= 5) break;
        cnt++;
        dx += dirx[i];
        dy += diry[i];
      }
      if(cnt >= 5) return true;
    }
    return false;
  }

  public boolean isFull(){
    int cnt = 0;
    for(int i = 0; i < 13; i++){
      for(int j = 0; j < 13; j++){
        if(!board[i][j].equals("🟡")) cnt++;
      }
    }
    return cnt == 13 * 13;
  }

  //For faster output and clearing the console
  public void Print() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    try{
      for(int i = 0; i < 13; i++){
        for(int j = 0; j < 13; j++){
          ps.write(board[i][j]);
          if(j == 12) ps.write(" " + alp[i]);
        }
        ps.newLine();
      }
      for(char a : alp) ps.write(" " + a);
      ps.flush();
    } catch (IOException e) {
      System.err.print(" ");
    }
    System.out.println();
  }
}