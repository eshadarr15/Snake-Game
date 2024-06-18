import javax.swing.JFrame;

public class GameFrame extends JFrame{

    GameFrame(){


        
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // takes JFrame and fits it around the components we add to frame
        this.setVisible(true); 
        this.setLocationRelativeTo(null); //appears in middle of computer 

    }

    
}
