import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AGameUserController extends KeyAdapter implements KeyListener {

    public enum ViableControls {UP, DOWN, LEFT, RIGHT, NONE;};
    private MoveDirection selected;

    public AGameUserController() {
        selected = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case('w'):
                selected = MoveDirection.UP_MOVE;
                break;
            case('s'):
                selected = MoveDirection.DOWN_MOVE;
                break;
            case('d'):
                selected = MoveDirection.RIGHT_MOVE;
                break;
            case('a'):
                selected = MoveDirection.LEFT_MOVE;
                break;
        }
    }

    public MoveDirection getSelected() {
        MoveDirection r = this.selected;
        this.selected = null;
        return r;
    }

}
