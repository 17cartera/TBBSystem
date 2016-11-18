package mainPackage;

import gameObjects.Entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

/*
 * main game interface, sends commands back to battlehandler
 */
public class Interface extends JFrame
{
	private static Desktop desktop;
	private static JFileChooser fileSelector;
	static 
	{
		desktop = Desktop.getDesktop();
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			fileSelector = new JFileChooser(new File(System.getProperty("user.dir"))); //set directory here?
			UIManager.setLookAndFeel(previousLF);
		}
		catch (Exception e) {e.printStackTrace();}
	}
	public BattleHandler battleHandler;
	public Interface(BattleHandler handler)

	{
		battleHandler = handler;
		//construct the main interface
		Container pane = getContentPane();
		//create entity list
		JPanel list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		//list.setPreferredSize(new Dimension(600,600));
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(600,600));
		pane.add(listScroll, BorderLayout.CENTER);
		//test: create an entity listing
		Entity testEntity = new Entity("Bob",5000);
		list.add(new EntityPanel(testEntity));
		//create sidebar
		JPanel sidebar = new JPanel();
		sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		sidebar.setPreferredSize(new Dimension(200,600));
		//add controls to sidebar
		//buttons to save/load entities
		JButton loadButton = new JButton("Load Entities");
		loadButton.addActionListener(new LoadListener());
		sidebar.add(loadButton);
		JButton saveButton = new JButton("Save Entities");
		saveButton.addActionListener(new SaveListener());
		sidebar.add(saveButton);
		//options button
		JButton optionsButton = new JButton("  Options  ");
		sidebar.add(optionsButton);
		//button to generate entity
		JButton addEntityButton = new JButton("  Add Entity  ");
		addEntityButton.addActionListener(new AddEntityListener());
		sidebar.add(addEntityButton);
		//button to perform action
		JButton actionButton = new JButton("Perform Action");
		actionButton.addActionListener(new ActionButtonListener());
		sidebar.add(actionButton);
		//button to start round
		JButton startRoundButton = new JButton("Start Round");
		startRoundButton.addActionListener(new StartRoundListener());
		sidebar.add(startRoundButton);
		//add sidebar
		pane.add(sidebar, BorderLayout.EAST);
		//show screen
		pack();
		setVisible(true);
	}
	
	//actionlisteners
	
	//button that loads entities from a file
	class LoadListener extends JButton implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			int returnVal = fileSelector.showOpenDialog(this);
			File file = fileSelector.getSelectedFile();
		}
	}
	//button that saves entities to a file
	class SaveListener extends JButton implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			int returnVal = fileSelector.showSaveDialog(this);
		}
	}
	//add an entity to the entity list
	class AddEntityListener extends JButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			//create a popup window to input entity data
			EntityInputPanel popup = new EntityInputPanel();
			JOptionPane.showMessageDialog(null, popup);
		}
	}
	//perform a manual action
	class ActionButtonListener extends JButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}
	//button that starts round of actions
	class StartRoundListener extends JButton implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}
	
	//custom GUI elements
	
	//panel listing entity stats
	class EntityPanel extends JPanel
	{
		Entity entity;
		JLabel nameLabel;
		JLabel healthLabel;
		EntityPanel(Entity refEntity)
		{
			entity = refEntity;
			this.setPreferredSize(new Dimension(580, 100));
			this.setMaximumSize(new Dimension(600, 100));
			this.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.setLayout(new BorderLayout());
			//name and health fields
			JPanel header = new JPanel();
			header.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			nameLabel = new JLabel();
			header.add(nameLabel,BorderLayout.NORTH);
			healthLabel = new JLabel();
			header.add(healthLabel,BorderLayout.CENTER);
			this.add(header, BorderLayout.NORTH);
			//traits
			JPanel traits = new JPanel();
			traits.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			traits.add(new JLabel("Traits"),BorderLayout.NORTH);
			this.add(traits,BorderLayout.WEST);
			//status effects
			JPanel statuses = new JPanel();
			statuses.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			statuses.add(new JLabel("Status Effects"),BorderLayout.EAST);
			this.add(statuses,BorderLayout.EAST);
			this.updateStats(entity);
			
			/* Layout Notes:
			 * North: Entity name, health
			 * Center: Entity abilities
			 * West: Entity traits
			 * East: Entity status effects, charges
			 * South: Sub-entities (summons)
			 */
		}
		//updates the panel's stat fields
		void updateStats(Entity entity) 
		{
			nameLabel.setText(entity.getName()+" "+entity.getTeam());
			healthLabel.setText(entity.getHealth()+"/"+entity.getMaximumHealth()+" HP");
		}
	}
	
	//panel for entity data input
	class EntityInputPanel extends JPanel
	{
		EntityInputPanel() 
		{
			this.add(new JLabel("Hello World"));
		}
	}
	
}
