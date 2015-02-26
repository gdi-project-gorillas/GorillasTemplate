package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;
public class GameSetupState extends BasicTWLGameState {
	
	
	private int stateID;
	private StateBasedEntityManager entityManager;
	private Label s1Label;
	EditField s1Input;
	private Label s2Label;
	EditField s2Input;
	
	
	 public GameSetupState(int sid){
		
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Entity background = new Entity("menu"); 
		background.setPosition(new Vector2f(400, 300)); 
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/dropofwater/background.png"))); 
		entityManager.addEntity(stateID, background);
		
		Entity escListener = new Entity("ESC_Listener");
		KeyPressedEvent escPressed = new KeyPressedEvent(Input.KEY_ESCAPE);
		escPressed.addAction(new ChangeStateAction(Gorillas.MAINMENUSTATE));
		escListener.addComponent(escPressed);
		entityManager.addEntity(stateID, escListener);
		
		
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta); 
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	protected RootPane createRootPane(){
		
		RootPane rp = super.createRootPane();
		
		s1Label = new Label ("Spieler 1:");
		s1Input = new EditField();
		s1Input.addCallback(new Callback(){
			public void callback(int key){
				handleEditFieldInput(key,s1Input,this,15);
			}
		});
		
		s2Label = new Label ("Spieler 2:");
		s2Input = new EditField();
		s2Input.addCallback(new Callback(){
			public void callback (int key){
				handleEditFieldInput(key,s2Input,this,15);
			}
		});
		
		rp.add(s1Label);
		rp.add(s1Input);
		rp.add(s2Label);
		rp.add(s2Input);
		return rp;
	}
	
	protected void layoutRootPane(){
		int xPosition = 50;
		int yPosition = 200;
		int gap = 10;
		
		s1Label.adjustSize();
		s2Label.adjustSize();
		s1Input.adjustSize();
		s2Input.adjustSize();
		
		s1Label.setPosition(xPosition, yPosition);
		s1Input.setPosition(xPosition+s1Label.getWidth()+gap, yPosition);
		s2Label.setPosition(xPosition, yPosition+50);
		s2Input.setPosition(xPosition+s2Label.getWidth()+gap,yPosition+50 );
	}
	
	void handleEditFieldInput(int key, EditField editField, Callback callback, int maxLength){
	
		
		if(key == de.matthiasmann.twl.Event.KEY_NONE){
			String inputText = editField.getText();
			
			if (inputText.isEmpty()){
				return;
			}
			
			if(inputText.length()>maxLength){
				
				editField.removeCallback(callback);
				editField.setText(inputText.substring(0, inputText.length()-1));
				editField.addCallback(callback);
			}
		}
	}
	
	
	

}
