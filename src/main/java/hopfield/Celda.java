package hopfield;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Celda extends Pane {

	private int posx;
	private int posy;
    private int idd;
    private Color color=Color.WHITE;


    public Celda() {
        super();
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}
    
    
}
