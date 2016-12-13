package mainPackage;

import gameObjects.Ability;
import gameObjects.Entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
 * TODO: GUI improvements, handle more mechanics
 */
public class Interface extends JFrame
{
	private static final long serialVersionUID = 1L;
	//I/O systems
	@SuppressWarnings("unused")
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
	//instance variables
	public BattleHandler battleHandler;
	public EntityList mainList;
	public Interface(BattleHandler handler)

	{
		battleHandler = handler;
		//construct the main interface
		Container pane = getContentPane();
		//create entity list
		mainList = new EntityList();
		pane.add(mainList);
		//create sidebar
		JPanel sidebar = new JPanel();
		sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		sidebar.setPreferredSize(new Dimension(200,600));
		sidebar.add(new LoadButton());
		sidebar.add(new SaveButton());
		sidebar.add(new OptionsButton());
		sidebar.add(new AddEntityButton());
		sidebar.add(new ActionButton());
		sidebar.add(new StartRoundButton());
		pane.add(sidebar, BorderLayout.EAST);
		//show screen
		pack();
		setVisible(true);
	}
	
	//custom GUI elements
	
	//entity list class for the list of entity panels
	class EntityList extends JScrollPane
	{
		private static final long serialVersionUID = 1L;
		public Ability activeAbility = null; //holds the currently selected ability (if any)
		public Entity activeEntity = null; //holds the currently select entity (if any)
		public ArrayList<EntityPanel> entityPanelList = new ArrayList<EntityPanel>();
		JPanel list;
		EntityList() 
		{
			list = new JPanel();
			list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
			this.setViewportView(list);
			this.setPreferredSize(new Dimension(600,600));
		}
		//updates the entity list using the new list
		public void updateList(ArrayList<Entity> entityList) 
		{
			entityPanelList = new ArrayList<EntityPanel>();
			list.removeAll();
			this.repaint();
			for (int x = 0; x < entityList.size(); x++) 
			{
				Entity currentEntity = entityList.get(x);
				this.addEntityPanel(currentEntity);
			}
			this.revalidate();
		}
		//adds an entity to the list
		void addEntityPanel(Entity entity) 
		{
			EntityPanel e = new EntityPanel(entity);
			list.add(e);
			entityPanelList.add(e);
		}
		//removes a single entity from the list (currently broken)
		void removeEntity(Entity entity) 
		{
			boolean entityFound = false; int x = 0;
			while (entityFound == false) 
			{
				EntityPanel currentPanel = entityPanelList.get(x);
				if(entity == currentPanel.entity) 
				{
					this.remove(currentPanel);
					this.revalidate();
					entityFound = true;
				}
				x++;
				if (x >= entityPanelList.size()) 
				{
					System.out.println("Entity not found");
					break;
				}
			}
		}
		//implements the target selection system (needs a better name)
		void activateTargetting(Ability a) 
		{
			activeAbility = a;
			for (int x = 0; x < entityPanelList.size(); x++) 
			{
				EntityPanel currentPanel = entityPanelList.get(x);
				currentPanel.enableEntitySelectors();
				currentPanel.disableAbilitySelectors();
			}
			this.revalidate();
		}
		//disables target selection system (needs a better name)
		void deactivateTargetting() 
		{
			for (int x = 0; x < entityPanelList.size(); x++) 
			{
				EntityPanel currentPanel = entityPanelList.get(x);
				currentPanel.disableEntitySelectors();
				currentPanel.enableAbilitySelectors();
			}
			this.revalidate();
		}
		//triggers an ability onto a target, and then resets the target selection system
		void triggerAbility(Entity e) 
		{
			activeEntity = e;
			activeAbility.activateAbility(activeEntity);
			System.out.println("Target health: " + activeEntity.getHealth()); //test line
			this.revalidate();
			this.deactivateTargetting();
		}
	}
	//panel listing entity stats
	class EntityPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		Entity entity;
		//header objects
		JPanel header;
		JLabel nameLabel;
		JLabel healthLabel;
		EntitySelectListener targetButton;
		//traits objects
		JPanel traits;
		//status effects objects
		JPanel statuses;
		//abilities list objects
		JPanel abilitiesList;
		ArrayList<AbilityPanel> abilityPanelList = new ArrayList<AbilityPanel>();
		EntityPanel(Entity inEntity)
		{
			entity = inEntity;
			this.setPreferredSize(new Dimension(580, 100));
			this.setMaximumSize(new Dimension(600, 100));
			this.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.setLayout(new BorderLayout());
			//name and health fields
			header = new JPanel();
			header.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			nameLabel = new JLabel();
			header.add(nameLabel,BorderLayout.NORTH);
			healthLabel = new JLabel();
			header.add(healthLabel,BorderLayout.CENTER);
			targetButton = new EntitySelectListener(this.entity);
			header.add(targetButton);
			this.add(header, BorderLayout.NORTH);
			//traits
			traits = new JPanel();
			traits.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			traits.add(new JLabel("Traits"),BorderLayout.NORTH);
			this.add(traits,BorderLayout.WEST);
			//status effects
			statuses = new JPanel();
			statuses.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			statuses.add(new JLabel("Status Effects"),BorderLayout.EAST);
			this.add(statuses,BorderLayout.EAST);
			this.updateStats(entity);
			//abilities
			ArrayList<Ability> abilities = entity.getAbilities();
			abilitiesList = new JPanel();
			for (int x = 0; x < abilities.size(); x++)
			{
				AbilityPanel abilityPanel = new AbilityPanel(abilities.get(x));
				abilityPanelList.add(abilityPanel);
				abilitiesList.add(abilityPanel);
			}
			this.add(abilitiesList,BorderLayout.CENTER);
			/* Layout Notes:
			 * North: Entity name, health
			 * Center: Entity abilities
			 * West: Entity traits
			 * East: Entity status effects, charges
			 * South: Sub-entities (summons)
			 */
		}
		//updates the panel's stat fields (may be deprecated later)
		void updateStats(Entity entity) 
		{
			nameLabel.setText(entity.getName()+" "+entity.getTeam());
			healthLabel.setText(entity.getHealth()+"/"+entity.getMaximumHealth()+" HP");
		}
		//methods to enable and disable interface buttons
		void enableEntitySelectors() 
		{
			this.targetButton.setEnabled(true);
		}
		void disableEntitySelectors() 
		{
			this.targetButton.setEnabled(false);
		}
		void enableAbilitySelectors() 
		{
			for (int x = 0; x < abilityPanelList.size(); x++) 
			{
				AbilityPanel currentPanel = abilityPanelList.get(x);
				currentPanel.activateButton.setEnabled(true);
			}
		}
		void disableAbilitySelectors() 
		{
			for (int x = 0; x < abilityPanelList.size(); x++) 
			{
				AbilityPanel currentPanel = abilityPanelList.get(x);
				currentPanel.activateButton.setEnabled(false);
			}
		}
		
		//panel depicting an ability
		class AbilityPanel extends JPanel 
		{
			private static final long serialVersionUID = 1L;
			Ability ability;
			AbilitySelectListener activateButton;
			AbilityPanel(Ability a) 
			{
				ability = a;
				this.setPreferredSize(new Dimension(450,30));
				this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				this.setLayout(new BorderLayout());
				//ability name
				this.add(new JLabel(a.abilityName));
				//button to activate ability if valid
				activateButton = new AbilitySelectListener(ability);
				this.add(activateButton,BorderLayout.EAST);
			}
		}
		//button that selects an ability
		//TODO: should not be enabled if the entity cannot take an action
		class AbilitySelectListener extends JButton implements ActionListener
		{
			private static final long serialVersionUID = 1L;
			Ability ability;
			AbilitySelectListener(Ability a) 
			{
				ability = a;
				this.setText("Activate Ability");
				this.setEnabled(false);
				this.addActionListener(this);
			}
			public void actionPerformed(ActionEvent arg0)
			{
				Interface.this.mainList.activateTargetting(ability);
				//activates selection buttons on all entity panels
			}
		}
		//button that selects an ability target
		public class EntitySelectListener extends JButton implements ActionListener
		{
			private static final long serialVersionUID = 1L;
			Entity entity;
			EntitySelectListener(Entity e) 
			{
				entity = e;
				this.setText("Select");
				this.setEnabled(false);
				this.addActionListener(this);
			}
			public void actionPerformed(ActionEvent arg0)
			{
				Interface.this.mainList.triggerAbility(entity);
				EntityPanel.this.updateStats(EntityPanel.this.entity);
			}
		}
	}

	//panel for entity data input
	class EntityInputInterface extends JPanel
	{
		private static final long serialVersionUID = 1L;
		EntityInputInterface() 
		{
			this.add(new JLabel("Hello World"));
		}
	}
	//sidebar buttons
	//button to load entities from a file
	class LoadButton extends JButton implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		LoadButton() 
		{
			this.setText("Load");
			this.addActionListener(this);
		}
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent arg0)
		{
			int returnVal = fileSelector.showOpenDialog(this);
			File file = fileSelector.getSelectedFile();
		}
	}
	//button that saves entities to a file
	class SaveButton extends JButton implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		SaveButton() 
		{
			this.setText("Save");
			this.addActionListener(this);
		}
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent arg0)
		{
			int returnVal = fileSelector.showSaveDialog(this);
		}
	}
	//button to open options menu
	class OptionsButton extends JButton implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		OptionsButton() 
		{
			this.setText("Options");
			this.addActionListener(this);
		}
		public void actionPerformed(ActionEvent arg0) 
		{
			
		}
	}
	//button to add an entity to the entity list
	class AddEntityButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		AddEntityButton() 
		{
			this.setText("Add Entity");
			this.addActionListener(this);
		}
		public void actionPerformed(ActionEvent arg0)
		{
			//create a popup window to input entity data
			EntityInputInterface popup = new EntityInputInterface();
			JOptionPane.showMessageDialog(null, popup);
		}
	}
	//button to perform a manual action
	class ActionButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		ActionButton() 
		{
			this.setText("Perform Action");
			this.addActionListener(this);
		}
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}
	//button that starts/ends round of actions
	class StartRoundButton extends JButton implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		StartRoundButton() 
		{
			this.setText("Start Round");
			this.addActionListener(this);
		}
		public void actionPerformed(ActionEvent arg0)
		{
			if (battleHandler.roundInProgress == false)
			{
				this.setText("End Round");
				battleHandler.startRound();
			}
			else
			{
				this.setText("Start Round");
				battleHandler.endRound();
			}
		}
	}
}
