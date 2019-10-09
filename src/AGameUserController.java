import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class AGameUserController extends KeyAdapter implements KeyListener {



    public enum ViableControls {UP, DOWN, LEFT, RIGHT, NONE;};
    private ViableControls selected;

    public AGameUserController() {
        selected = ViableControls.NONE;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case('w'):
                selected = ViableControls.UP;
                break;
            case('s'):
                selected = ViableControls.DOWN;
                break;
            case('d'):
                selected = ViableControls.RIGHT;
                break;
            case('a'):
                selected = ViableControls.LEFT;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'w' || c == 's' || c == 'a' || c == 'd') {
            selected = ViableControls.NONE;
        }
    }

    public ViableControls getSelected() {
        ViableControls r = this.selected;
        this.selected = ViableControls.NONE;
        return r;
    }

}
